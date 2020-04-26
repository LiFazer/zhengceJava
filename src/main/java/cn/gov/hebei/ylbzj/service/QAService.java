package cn.gov.hebei.ylbzj.service;

import cn.gov.hebei.ylbzj.entity.*;
import cn.gov.hebei.ylbzj.param.QAOptionParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QAService {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private ClassificationQuestionService classService;

    @Autowired
    private TelService telService;

    public R addQA(QAOptionParam qaOptionParam) {
        QuestionDO questionDO = new QuestionDO();
        //1.数据模型转化
        QA2DO(qaOptionParam, questionDO);
        //2.执行数据插入
        addQA(questionDO, qaOptionParam.getTagList());
        //添加电话和问题关系
        //更新电话和问题的关系
        Integer telId = qaOptionParam.getTelId();
        Integer queId = questionDO.getId();
        //新增
        TelDO tel = new TelDO();
        tel.setQuestionId(queId);
        tel.setTelId(telId);
        telService.save(tel);
        return R.ok();
    }

    public R updateQA(QAOptionParam qaOptionParam) {
        QuestionDO questionDO = questionService.get(qaOptionParam.getId());
        if (questionDO == null) {
            return R.error(-2, "无可修改目标");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("questionId", qaOptionParam.getId());
        List<ClassificationQuestionDO> tagDoList = classService.list(map);
        List<Integer> tagListOld = new ArrayList<>();
        if (!CollectionUtils.isEmpty(tagDoList)) {
            tagListOld.addAll(tagDoList.stream().map(ClassificationQuestionDO::getClassificationId).
                    collect(Collectors.toList()));
        }
        //修改问题描述、问题答案
        questionDO.setQuestionName(qaOptionParam.getQuestionName());
        questionDO.setQuestionAnswer(qaOptionParam.getQuestionAnswer());
        updateQA(questionDO, qaOptionParam.getTagList(), tagListOld);
        //更新电话和问题的关系
        Integer telId = qaOptionParam.getTelId();
        Integer id = qaOptionParam.getId();
        Map<String, Object> parm = new HashMap<>();
        parm.put("questionId", id);
        List<TelDO> list = telService.list(parm);
        if (!CollectionUtils.isEmpty(list)) {
            List<Integer> collect = list.stream().map(TelDO::getId).collect(Collectors.toList());
            telService.batchRemoveByIdList(collect);

        }
        //新增
        TelDO tel = new TelDO();
        tel.setQuestionId(id);
        tel.setTelId(telId);
        telService.save(tel);
        return R.ok();
    }

    @Transactional(transactionManager = "selfManager")
    public void updateQA(QuestionDO questionDO, List<Integer> tagListNew, List<Integer> tagListOld) {
        //1.标签新增
        if (!CollectionUtils.isEmpty(tagListNew)) {
            tagListNew.stream().filter(integer -> CollectionUtils.isEmpty(tagListOld) || !tagListOld.contains(integer)).forEach(i -> {
                ClassificationQuestionDO cqDO = new ClassificationQuestionDO();
                cqDO.setClassificationId(i);
                cqDO.setQuestionId(questionDO.getId());
                classService.save(cqDO);
            });
            //标记已匹配
            questionDO.setMatchOrNot(1);
        } else {
            //取消标记
            questionDO.setMatchOrNot(0);
        }
        //2.标签删除
        if (!CollectionUtils.isEmpty(tagListOld)) {
            tagListOld.stream().filter(integer -> CollectionUtils.isEmpty(tagListNew) || !tagListNew.contains(integer)).forEach(i -> {
                ClassificationQuestionDO cqDO = new ClassificationQuestionDO();
                cqDO.setClassificationId(i);
                cqDO.setQuestionId(questionDO.getId());
                classService.remove(cqDO);
            });
        }
        questionService.update(questionDO);
    }

    @Transactional(transactionManager = "selfManager")
    public void addQA(QuestionDO questionDO, List<Integer> tagList) {
        questionService.save(questionDO);
        if (!CollectionUtils.isEmpty(tagList)) {
            tagList.forEach(i -> {
                ClassificationQuestionDO cqDO = new ClassificationQuestionDO();
                cqDO.setClassificationId(i);
                cqDO.setQuestionId(questionDO.getId());
                classService.save(cqDO);
            });
        }
    }

    private void QA2DO(QAOptionParam qaOptionParam, QuestionDO questionDO) {
        questionDO.setQuestionAnswer(qaOptionParam.getQuestionAnswer());
        questionDO.setQuestionName(qaOptionParam.getQuestionName());
        if (!CollectionUtils.isEmpty(qaOptionParam.getTagList())) {
            questionDO.setMatchOrNot(1);
        }
    }

    @Transactional(transactionManager = "selfManager")
    public void delQA(int id) {
        QuestionDO questionDO = questionService.get(id);
        if (questionDO != null) {
            //修改匹配状态
            /*questionDO.setMatchOrNot(0);
            questionService.update(questionDO);*/
            questionService.remove(id);
            //查询中间表关系
            Map<String, Object> map = new HashMap<>();
            map.put("questionId", id);
            List<ClassificationQuestionDO> tagDoList = classService.list(map);
            if (!CollectionUtils.isEmpty(tagDoList)) {
                List<Integer> idList = tagDoList.stream().map(ClassificationQuestionDO::getId).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(idList)) {
                    //批量删除
                    classService.batchRemoveByIds(idList);
                }
            }

            //根据问题id查询电话和问题的关系
            Map<String, Object> parm = new HashMap<>();
            parm.put("questionId", id);
            List<TelDO> list = telService.list(parm);
            if (!CollectionUtils.isEmpty(list)) {
                List<Integer> collect = list.stream().map(TelDO::getId).collect(Collectors.toList());
                telService.batchRemoveByIdList(collect);
            }
        }
    }

    public List<TelDO> findTelByIds(List<Integer> ids) {
        return telService.findTelByIds(ids);
    }
}
