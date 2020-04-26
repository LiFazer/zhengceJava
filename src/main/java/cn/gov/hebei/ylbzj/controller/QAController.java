package cn.gov.hebei.ylbzj.controller;

import cn.gov.hebei.ylbzj.annotation.MethodLog;
import cn.gov.hebei.ylbzj.entity.*;
import cn.gov.hebei.ylbzj.param.QAOptionParam;
import cn.gov.hebei.ylbzj.service.*;
import cn.gov.hebei.ylbzj.utils.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/qa")
@Slf4j
public class QAController {

    @Autowired
    private QAService qaService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private ClassificationQuestionService classificationQuestionService;
    @Autowired
    private ClassificationService classificationService;

    @Autowired
    private TelephoneService telephoneService;

    @RequestMapping("/add")
    @MethodLog
    public R addQA(@RequestBody QAOptionParam qaOptionParam){
        if (!checkQAOptionParam(qaOptionParam)){
            return R.error(-1,"参数不完整，问题或答案均不能为空");
        }
        return qaService.addQA(qaOptionParam);
    }

    @RequestMapping("/update")
    @MethodLog
    public R updateQA(@RequestBody QAOptionParam qaOptionParam){
        if (!checkQAOptionParam(qaOptionParam)){
            return R.error(-1,"参数不完整，问题或答案均不能为空");
        }
        return qaService.updateQA(qaOptionParam);
    }

    @RequestMapping("/match")
    @MethodLog
    public R matchTag(@RequestBody QAOptionParam qaOptionParam){
        if (qaOptionParam == null){
            return R.error(-1,"参数不完整，问题或答案均不能为空");
        }
        if (qaOptionParam.getId() == null){
            return R.error(-1,"未提供问题id");
        }
        if (qaOptionParam.getTelId() == null) {
            return R.error(-1,"未选择电话");
        }
        if (CollectionUtils.isEmpty(qaOptionParam.getTagList())){
            return R.error(-1,"未选择标签");
        }
        return qaService.updateQA(qaOptionParam);
    }

    @RequestMapping("/del")
    @MethodLog
    public R delQA(@RequestParam("id") int id){
        try {
            qaService.delQA(id);
            return R.ok();
        } catch (Exception e) {
            log.error("取消匹配异常,e=", e);
            return R.error("取消匹配异常");
        }
    }

    /*public R delQA(@RequestParam("id") int id){
        int k = questionService.remove(id);
        if (k > 0){
            return R.ok();
        }
        return R.error(-1,"数据不存在，无法删除");
    }*/
    @RequestMapping("/notaglist")
    public R queryByFlag(@RequestParam(name = "page") int page, @RequestParam(name = "pageSize",required = false) Integer pageSize){
        if(pageSize == null){
            pageSize = 10;
        }
        Map<String,Object> map = new HashMap<>();
        map.put("matchOrNot",0);
        PageHelper.startPage(page, pageSize);
        List<QuestionDO> qaList = questionService.list(map);
        PageInfo<QuestionDO> pageInfo = new PageInfo<>(qaList, pageSize);
        //根据问题id查询问题关联的电话列表
        List<Integer> ids = qaList.stream().map(QuestionDO::getId).collect(Collectors.toList());
        List<TelDO> telDOList = qaService.findTelByIds(ids);
        List<TelephoneDO> telephoneDOList = new ArrayList<>();
        Map<Integer, Integer> telMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(telDOList)) {
            telMap = telDOList.stream().collect(Collectors.toMap(TelDO::getQuestionId, TelDO::getTelId));
            List<Integer> telIds = telDOList.stream().map(TelDO::getTelId).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(telIds)) {
                //根据电话id查询电话对象列表
                telephoneDOList = telephoneService.findByIds(telIds);
            }
        }
        ArrayList<QuestionDO> result = new ArrayList<>();
        Map<Integer, Integer> finalTelMap = telMap;
        List<TelephoneDO> finalTelephoneDOList = telephoneDOList;
        qaList.stream().forEach(questionDO -> {
            //根据问题id查找电话对象
            if (!CollectionUtils.isEmpty(finalTelMap)) {
                if (finalTelMap.containsKey(questionDO.getId().intValue())) {
                    //获取电话id
                    Integer integer = finalTelMap.get(questionDO.getId().intValue());
                    if (!CollectionUtils.isEmpty(finalTelephoneDOList)) {
                        finalTelephoneDOList.stream().forEach(telephoneDO -> {
                            if (telephoneDO.getId().intValue() == integer.intValue()) {
                                questionDO.setTel(telephoneDO.getTel());
                                questionDO.setTelId(telephoneDO.getId());
                                questionDO.setTelName(telephoneDO.getDepName());
                            }
                        });
                    }
                } else {
                    questionDO.setTel("");
                    questionDO.setTelName("");
                }
            } else {
                questionDO.setTel("");
                questionDO.setTelName("");
            }
            result.add(questionDO);
        });
        pageInfo.setList(result);
        return R.okData(pageInfo);
    }

    @RequestMapping("/taglist")
    public R queryMatchList(@RequestParam(name = "page") int page, @RequestParam(name = "pageSize",required = false) Integer pageSize,
                            @RequestParam(name = "keyWord",required = false) String keyWord,@RequestParam(name = "tag",required = false) Integer tag){
        if(pageSize == null){
            pageSize = 10;
        }
        List<Integer> list = new ArrayList<>();
        if(tag != null){
            List<ClassificationQuestionDO> listDo = classificationQuestionService.getByClassificationId(tag);
            if(CollectionUtils.isEmpty(listDo)){
                return R.ok("无数据");
            }
            list = listDo.stream().map(ClassificationQuestionDO::getQuestionId).
                    collect(Collectors.toList());
        }
        //keyWord = StringUtil.str2RegStr(keyWord);
        if (!StringUtils.isEmpty(keyWord)) {
            keyWord = StringUtil.iKSegmentMsg(keyWord);
        }

        PageHelper.startPage(page, pageSize);
        List<QuestionDO> qaList = questionService.queryMatchList(keyWord,list);

        if (CollectionUtils.isEmpty(qaList)) {
            return R.okData(null);
        }

        PageInfo<QuestionDO> pageInfo = new PageInfo<>(qaList, pageSize);

        //根据问题id查询问题关联的电话列表
        List<Integer> ids = qaList.stream().map(QuestionDO::getId).collect(Collectors.toList());
        List<TelDO> telDOList = qaService.findTelByIds(ids);
        List<TelephoneDO> telephoneDOList = new ArrayList<>();
        Map<Integer, Integer> telMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(telDOList)) {
            telMap = telDOList.stream().collect(Collectors.toMap(TelDO::getQuestionId, TelDO::getTelId));
            List<Integer> telIds = telDOList.stream().map(TelDO::getTelId).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(telIds)) {
                //根据电话id查询电话对象列表
                telephoneDOList = telephoneService.findByIds(telIds);
            }
        }
        List<TelephoneDO> finalTelephoneDOList = telephoneDOList;
        Map<Integer, Integer> finalTelMap = telMap;
        List<TelephoneDO> finalTelephoneDOList1 = telephoneDOList;

        ArrayList<QuestionDO> result = new ArrayList<>();
        qaList.stream().forEach(questionDO -> {
            Map<String,Object> map = new HashMap<>();
            map.put("questionId",questionDO.getId());
            List<ClassificationQuestionDO> tagDoList = classificationQuestionService.list(map);
            List<Integer> tagListOld = new ArrayList<>();
            if (!CollectionUtils.isEmpty(tagDoList)){
                tagListOld.addAll(tagDoList.stream().map(ClassificationQuestionDO::getClassificationId).
                        collect(Collectors.toList()));
            }
            questionDO.setTagList(tagListOld);
            if (!CollectionUtils.isEmpty(tagListOld)) {
                //tagListOld分类id
                List<String> listClassificationName = classificationService.getNameByIds(tagListOld);
                questionDO.setClassificationName(listClassificationName);
            }else{
                questionDO.setClassificationName(null);
            }
            //根据问题id查找电话对象
            if (!CollectionUtils.isEmpty(finalTelMap)) {
                if (finalTelMap.containsKey(questionDO.getId().intValue())) {
                    //获取电话id
                    Integer integer = finalTelMap.get(questionDO.getId().intValue());
                    if (!CollectionUtils.isEmpty(finalTelephoneDOList1)) {
                        finalTelephoneDOList.stream().forEach(telephoneDO -> {
                            if (telephoneDO.getId().intValue() == integer.intValue()) {
                                questionDO.setTel(telephoneDO.getTel());
                                questionDO.setTelId(telephoneDO.getId());
                                questionDO.setTelName(telephoneDO.getDepName());
                            }
                        });
                    }
                }else {
                    questionDO.setTel("");
                    questionDO.setTelName("");
                }
            }else {
                questionDO.setTel("");
                questionDO.setTelName("");
            }
            result.add(questionDO);
        });


        //PageInfo<QuestionDO> pageInfo = new PageInfo<>(qaList, pageSize);
        pageInfo.setList(result);
        return R.okData(pageInfo);
    }


    private boolean checkQAOptionParam(QAOptionParam q){
        if (q == null){
            return false;
        }
        if (StringUtils.isEmpty(q.getQuestionAnswer())){
            return false;
        }
        if (StringUtils.isEmpty(q.getQuestionName())){
            return false;
        }
        return true;
    }
}
