package cn.gov.hebei.ylbzj.service.impl;

import cn.gov.hebei.ylbzj.dao.ClassificationDao;
import cn.gov.hebei.ylbzj.dao.ClassificationQuestionDao;
import cn.gov.hebei.ylbzj.dao.QuestionDao;
import cn.gov.hebei.ylbzj.dao.WordsDao;
import cn.gov.hebei.ylbzj.entity.ClassificationDO;
import cn.gov.hebei.ylbzj.entity.ClassificationQuestionDO;
import cn.gov.hebei.ylbzj.entity.QuestionDO;
import cn.gov.hebei.ylbzj.entity.WordsDO;
import cn.gov.hebei.ylbzj.service.ClassificationService;
import cn.gov.hebei.ylbzj.service.QuestionService;
import cn.gov.hebei.ylbzj.vo.ClassificationVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;


@Service
public class ClassificationServiceImpl implements ClassificationService {
	@Autowired
	private ClassificationDao classificationDao;

	@Autowired
    private ClassificationQuestionDao classificationQuestionDao;

	@Autowired
    private QuestionDao questionDao;

	@Autowired
    private WordsDao wordsDao;

    private ExecutorService service = new ThreadPoolExecutor(14, 14, 120L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new QuestionServiceImpl.AsynchronousThreadFactory());

    public static class AsynchronousThreadFactory implements ThreadFactory {

        private static int threadInitNumber = 0;

        private static synchronized int nextThreadNum() {
            return threadInitNumber++;
        }

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "authToken-" + nextThreadNum());
        }
    }

    @Override
	public ClassificationDO get(Integer id){
		return classificationDao.get(id);
	}

	@Override
	public List<ClassificationDO> list(Map<String, Object> map){
		return classificationDao.list(map);
	}

	@Override
	public int count(Map<String, Object> map){
		return classificationDao.count(map);
	}

	@Override
	public int save(ClassificationDO classification){
        Date date = new Date();
        classification.setCreateTime(date);
        classification.setUpdateTime(date);
		return classificationDao.save(classification);
	}

	@Override
	public int update(ClassificationDO classification){
		return classificationDao.update(classification);
	}

	@Override
	public int remove(Integer id){
		return classificationDao.remove(id);
	}

	@Override
	public int batchRemove(Integer[] ids){
		return classificationDao.batchRemove(ids);
	}

	@Override
	public List<ClassificationDO> findClassificationLikeQuestionName(String keyWord,String questionName) {
        List<ClassificationDO> classificationList = classificationDao.findClassificationLikeQuestionName(keyWord);
        if (!CollectionUtils.isEmpty(classificationList)) {
            //异步存储分类 关键词
            syncKeyWords(keyWord, questionName, classificationList);
        }
		return classificationList;
	}

    @Override
    public List<ClassificationDO> findClassificationLikeQuestionName(String keyWord) {
        return classificationDao.findClassificationLikeQuestionName(keyWord);
    }

    @Async
    public void syncKeyWords(String keyWord, String questionName, List<ClassificationDO> list) {
        //存关键字match_or_not
        String[] split = keyWord.split("\\|");
        //需要拆词
        if (split.length > 0 && keyWord.contains("|")) {
            service.execute(() -> {
                for (String s : split) {
                    //查询库中是否存在该关键词，如果存在，增加查询次数，不存在，新增关键词
                    saveOrUpdate(s, list, 1);
                }
                //存储未拆分的词
                saveOrUpdate(questionName, list, 0);
            });
        } else {
            //不用拆词
            saveOrUpdate(questionName, list, 0);
        }
    }

    //判断是应该新增还是累加次数
    private void saveOrUpdate(String questionName, List<ClassificationDO> list, int i) {
        Map<String, Object> param = new HashMap<>();
        param.put("words", questionName);
        List<WordsDO> existList = wordsDao.list(param);
        if (CollectionUtils.isEmpty(existList)) {
            //存储未拆分的词
            WordsDO wordsDO = new WordsDO();
            wordsDO.setWords(questionName);
            if (CollectionUtils.isEmpty(list)) {
                wordsDO.setFeedback(0);
            } else {
                wordsDO.setFeedback(1);
            }
            wordsDO.setType(i);
            Date date = new Date();
            wordsDO.setInsertTime(date);
            wordsDO.setUpdateTime(date);
            wordsDao.save(wordsDO);
        } else {
            //累加搜索次数
            WordsDO wordsDO = existList.get(0);
            int countTimes = wordsDO.getCountTimes().intValue() + 1;
            wordsDO.setCountTimes(countTimes);
            wordsDao.update(wordsDO);
        }
    }

    @Override
	public List<ClassificationDO> classificationById(Integer id) {
        List<ClassificationDO> list = new ArrayList<>();
        //查询本级分类，把分类作为关键词保存
        ClassificationDO classificationDO = classificationDao.get(id);
        if (classificationDO != null) {
            list.add(classificationDO);
            syncKeyWords(classificationDO.getClassificationName().trim(), classificationDO.getClassificationName().trim(), list);
        }
        //查询下级分类
		return classificationDao.classificationById(id);
	}

	@Override
	public List<ClassificationVo> findAllClassification() {
        List<ClassificationDO> classificationDOList = classificationDao.findAllClassification();
        if (CollectionUtils.isEmpty(classificationDOList)) {
            return Collections.EMPTY_LIST;
        }
        List<ClassificationVo> dataList = new ArrayList<>();
        classificationDOList.stream().forEach(classificationDO -> {
            ClassificationVo classificationVo = new ClassificationVo();
            //BeanUtils.copyProperties(classificationDO,classificationVo);
			classificationVo.setTitle(classificationDO.getClassificationName());
			classificationVo.setValue(classificationDO.getId());
			classificationVo.setPid(classificationDO.getPid());
            dataList.add(classificationVo);
        });

        // 顶层list
        List<ClassificationVo> top = dataList.stream().filter(classificationDO1 -> classificationDO1.getPid().intValue() == 0).collect(Collectors.toList());

        //根据pid分组
        Map<Integer, List<ClassificationVo>> map = dataList.stream().collect(Collectors.groupingBy(ClassificationVo::getPid));

        List<ClassificationVo> classificationVos = treeCategoryData(top, map);
        return classificationVos;
	}

	@Override
	public List<String> getNameByIds(List<Integer> tagListOld) {

		return classificationDao.getNameByIds(tagListOld);
	}

	@Override
	public void delete(Integer id) {
		//根据分类id，查询所有子分类
        List<ClassificationDO> classificationDOList = classificationDao.findAllClassification();
        if (CollectionUtils.isEmpty(classificationDOList)) {
            return;
        }
        List<ClassificationVo> dataList = new ArrayList<>();
        classificationDOList.stream().forEach(classificationDO -> {
            ClassificationVo classificationVo = new ClassificationVo();
            classificationVo.setTitle(classificationDO.getClassificationName());
            classificationVo.setValue(classificationDO.getId());
            classificationVo.setPid(classificationDO.getPid());
            dataList.add(classificationVo);
        });

        // 顶层list
        List<ClassificationVo> top = dataList.stream().filter(classificationDO1 -> classificationDO1.getValue().intValue() == id).collect(Collectors.toList());
        //根据pid分组
        Map<Integer, List<ClassificationVo>> map = dataList.stream().collect(Collectors.groupingBy(ClassificationVo::getPid));
        //获取所有分类id
        List<Integer> idList = new ArrayList<>();
        List<Integer> integerList = getClassificationIdList(top, map,idList);
        idList.add(id);
        if (CollectionUtils.isEmpty(idList)) {
            return;
        }
        //根据分类id查询中间表对应的问题关系
        List<ClassificationQuestionDO> classificationQuestionDOList = classificationQuestionDao.listAllByClassificationIdList(idList);
        if (CollectionUtils.isEmpty(classificationQuestionDOList)) {
            //删除分类
            classificationDao.batchRemoveByIdList(idList);
        }
        List<Integer> ids = classificationQuestionDOList.stream().map(ClassificationQuestionDO::getId).collect(Collectors.toList());
        List<Integer> questionIdList = classificationQuestionDOList.stream().map(classificationQuestionDO -> classificationQuestionDO.getQuestionId()).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(questionIdList)) {
            return;
        }
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        delete(questionIdList,ids,idList);

    }
    @Transactional(transactionManager = "selfManager")
    public void delete(List<Integer> questionIdList, List<Integer> ids,List<Integer> idList) {
        //删除中间表关系
        classificationQuestionDao.batchRemoveByIdList(ids);
        //遍历问题id查询中间表，看该问题是否属于其他分类
        questionIdList.stream().forEach(integer -> {
            String existOrNot = classificationQuestionDao.getByQuestionId(integer);
            if ("N".equals(existOrNot)) {
                //不存在，修改该问题的match_or_not
                QuestionDO questionDO = new QuestionDO();
                questionDO.setId(integer);
                questionDO.setMatchOrNot(0);
                questionDao.update(questionDO);
            }
        });
        //删除分类
        classificationDao.batchRemoveByIdList(idList);
    }

    public List<Integer> getClassificationIdList(List<ClassificationVo> top, Map<Integer, List<ClassificationVo>> allMap,List<Integer> result) {
        //遍历
        top.forEach(category -> {
            //result.add(category.getValue());
            List<ClassificationVo> temp = allMap.get(category.getValue());
            if (temp != null && !temp.isEmpty()) {
                List<Integer> collect = temp.stream().map(ClassificationVo::getValue).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(collect)) {
                    result.addAll(collect);
                }
                category.setChildren(temp);
                getClassificationIdList(category.getChildren(), allMap,result);
            }
        });
        return result;
    }

	public List<ClassificationVo> treeCategoryData(List<ClassificationVo> top, Map<Integer, List<ClassificationVo>> allMap) {
        //遍历
        top.forEach(category -> {
            List<ClassificationVo> temp = allMap.get(category.getValue());
            if (temp != null && !temp.isEmpty()) {
                category.setChildren(temp);
                treeCategoryData(category.getChildren(), allMap);
            } else {
                category.setChildren(null);
            }
        });
        return top;
    }
}
