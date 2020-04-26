package cn.gov.hebei.ylbzj.service.impl;

import cn.gov.hebei.ylbzj.dao.QuestionDao;
import cn.gov.hebei.ylbzj.dao.WordsDao;
import cn.gov.hebei.ylbzj.entity.R;
import cn.gov.hebei.ylbzj.entity.WordsDO;
import cn.gov.hebei.ylbzj.service.QuestionService;
import cn.gov.hebei.ylbzj.entity.QuestionDO;
import cn.gov.hebei.ylbzj.utils.StringUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.*;


@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private WordsDao wordsDao;

    private ExecutorService service = new ThreadPoolExecutor(14, 14, 120L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new AsynchronousThreadFactory());

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

    @Autowired
    private QuestionDao questionDao;

    @Override
    public QuestionDO get(Integer id) {
        return questionDao.get(id);
    }

    @Override
    public List<QuestionDO> list(Map<String, Object> map) {
        return questionDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return questionDao.count(map);
    }

    @Override
    public int save(QuestionDO question) {
        return questionDao.save(question);
    }

    @Override
    public int update(QuestionDO question) {
        return questionDao.update(question);
    }

    @Override
    public int remove(Integer id) {
        return questionDao.remove(id);
    }

    @Override
    public int batchRemove(Integer[] ids) {
        return questionDao.batchRemove(ids);
    }

    @Override
    public List<QuestionDO> findThreeQuestion() {
        return questionDao.findThreeQuestion();
    }

    @Override
    public List<QuestionDO> question(String keyWord, String questionName) {
        //模糊搜索问题
        List<QuestionDO> list = questionDao.question(keyWord);
        //存储关键词
        syncKeyWords(keyWord, questionName, list);
        return list;
    }

    @Async
    public void syncKeyWords(String keyWord, String questionName, List<QuestionDO> list) {
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
    private void saveOrUpdate(String questionName, List<QuestionDO> list, int i) {
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
    public List<QuestionDO> queryMatchList(String keyWord, List<Integer> list) {
        return questionDao.queryMatchList(keyWord, list);
    }

    @Override
    public Integer countByQuestionName(String msg) {
        return questionDao.countByQuestionName(msg);
    }

    @Override
    public Integer updateTimes(Integer id, String type) {
        //更新已解决
        Integer i = 0;
        if ("1".equals(type)) {
            i = questionDao.updateSolveTimes(id);
        } else if ("0".equals(type)) {
            i = questionDao.updateUnSolveTimes(id);
            //如果是未解决，那么需要为用户推荐6个问题
            List<QuestionDO> list = questionDao.findSixQuestionWithoutSelf(id);
        }
        return i;
    }

    @Override
    public List<QuestionDO> findSixQuestionWithoutSelf(Integer id) {
        return questionDao.findSixQuestionWithoutSelf(id);
    }

    @Override
    public void saveKeyWords(QuestionDO questionDO) {
        List<QuestionDO> list = new ArrayList<>();
        list.add(questionDO);
        //拆分问题关键词
        String questionName = questionDO.getQuestionName().trim().replaceAll("[\\pP\\pS\\pZ]", "");
        if (!StringUtils.isEmpty(questionName)) {
            String keyWord = StringUtil.iKSegmentMsg(questionName);
            syncKeyWords(keyWord, questionName, list);
        }
    }

    @Override
    public List<QuestionDO> questionAll(String keyWord, String questionName, Map<String, Object> params) {
        //模糊搜索问题
        String page = params.get("page").toString();
        String limit = params.get("limit").toString();
        int i = Integer.parseInt(page);
        int b = Integer.parseInt(limit);
        params.put("start", (i - 1) * b);
        params.put("questionName", keyWord);
        List<QuestionDO> list = questionDao.questionAll(params);
        //存储关键词
        syncKeyWords(keyWord, questionName, list);
        return list;
    }

}
