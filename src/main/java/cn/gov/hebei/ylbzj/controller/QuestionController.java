package cn.gov.hebei.ylbzj.controller;

import cn.gov.hebei.ylbzj.annotation.MethodLog;
import cn.gov.hebei.ylbzj.constant.InitData;
import cn.gov.hebei.ylbzj.entity.*;
import cn.gov.hebei.ylbzj.service.ClassificationQuestionService;
import cn.gov.hebei.ylbzj.service.ClassificationService;
import cn.gov.hebei.ylbzj.service.QuestionService;
import cn.gov.hebei.ylbzj.service.TelephoneService;
import cn.gov.hebei.ylbzj.utils.StringUtil;
import cn.gov.hebei.ylbzj.vo.*;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 问答表
 *
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-01-13 12:42:24
 */

@RestController
@Slf4j
@RequestMapping("/system/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private ClassificationService classificationService;

    @Autowired
    private ClassificationQuestionService classificationQuestionService;

    @Autowired
    private TelephoneService telephoneService;

    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据

        List<QuestionDO> questionList = questionService.list(params);
        return R.ok();
    }


    @GetMapping("/edit/{id}")
    String edit(@PathVariable("id") Integer id, Model model) {
        QuestionDO question = questionService.get(id);
        model.addAttribute("question", question);
        return "system/question/edit";
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(QuestionDO question) {
        if (questionService.save(question) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(QuestionDO question) {
        questionService.update(question);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    public R remove(Integer id) {
        if (questionService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    public R remove(@RequestParam("ids[]") Integer[] ids) {
        questionService.batchRemove(ids);
        return R.ok();
    }

    /**
     * 首次进入  默认请求的接口、换一换接口
     *
     * @return
     */
    @GetMapping("/changedata")
    @MethodLog
    public R first() {
        //从问题随机选择3个问题返回
        try {
            List<QuestionDO> list = questionService.findThreeQuestion();
            if (CollectionUtils.isEmpty(list)) {
                return R.ok("暂时没有相关问题");
            }
            List<String> interesList = list.stream().map(QuestionDO::getQuestionName).collect(Collectors.toList());
            ArrayList<Object> result = new ArrayList<>();
            list.stream().forEach(questionDO -> {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("context", questionDO.getQuestionName());
                jsonObject.put("id", questionDO.getId());
                result.add(jsonObject);
            });

            ChangeVo<Object> changeVo = new ChangeVo<>();
            changeVo.setInteresTitle("请问您是不是想咨询以下问题？");
            changeVo.setMsgType("interest");
            changeVo.setInteresList(result);
            return R.okData(changeVo);
        } catch (Exception e) {
            log.error("默认请求失败,e=", e);
            return R.error(1, "请求失败，请稍后重试");
        }
    }

    /**
     * 根据用户输入的问题，查找相关分类 、问题
     * 分页
     *
     * @return
     */
    @Deprecated
    @PostMapping("/question")
    public R question(@RequestBody Map<String, Object> params) {
        if (params == null) {
            return R.ok();
        }
        if (params.get("questionName") == null) {
            return R.ok();
        }
        if (params.get("page") == null) {
            params.put("page", "1");
        }
        if (params.get("limit") == null) {
            params.put("limit", "5");
        }
        //查询问题
        String questionName = params.get("questionName").toString().trim().replaceAll("[\\pP\\pS\\pZ]", "");
        //String keyWord = StringUtil.str2RegStr(questionName);
        String keyWord = StringUtil.iKSegmentMsg(questionName);
        List<QuestionDO> questionDOList = questionService.question(keyWord, questionName);
        Integer count = questionService.countByQuestionName(questionName);
        //每页记录数
        Integer pageSize = Integer.parseInt(params.get("limit").toString());
        //当前页数
        Integer currPage = Integer.parseInt(params.get("page").toString());
        //总页数
        Integer totalPage = (count + pageSize - 1) / pageSize;
        QuestionVo questionVo = new QuestionVo();
        questionVo.setCurrPage(currPage);
        questionVo.setPageSize(pageSize);
        questionVo.setTotalCount(count);
        questionVo.setTotalPage(totalPage);
        questionVo.setInteresList(questionDOList);
        //查询分类
        List<ClassificationDO> classificationDOList = classificationService.findClassificationLikeQuestionName(keyWord, questionName);
        if (!CollectionUtils.isEmpty(classificationDOList)) {
            questionVo.setAssociationList(classificationDOList);
        }

        return R.okData(questionVo);
    }

    /**
     * 根据用户输入的问题，查找相关分类 、问题
     *
     * @return
     */
    @PostMapping("/questiontwenty")
    @MethodLog
    public R questiontwenty(@RequestBody Map<String, Object> params) {
        try {
            if (params == null) {
                return R.error("您好，您的提问不够清晰，请您输入简要的问题。");
            }
            if (params.get("questionName") == null) {
                return R.error("您好，您的提问不够清晰，请您输入简要的问题。");
            }

            String questionName = params.get("questionName").toString().trim().replaceAll("[\\pP\\pS\\pZ]", "");
            if (StringUtils.isEmpty(questionName)) {
                return R.ok("您好，您的提问不够清晰，请您输入简要的问题。");
            }
            //查询分类
//        String keyWord = StringUtil.str2RegStr(questionName);
            String keyWord = StringUtil.iKSegmentMsg(questionName);
            keyWord = questionName +"|" +keyWord;
            List<ClassificationDO> classificationDOList = classificationService.findClassificationLikeQuestionName(keyWord);
            QuestionDataVo questionDataVo = new QuestionDataVo();
            if (!CollectionUtils.isEmpty(classificationDOList)) {
                questionDataVo.setMsgType("new");
                questionDataVo.setTypeProblemTitle("请问您是不是咨询以下问题？");
                ArrayList<Object> list = new ArrayList<>();
                classificationDOList.stream().forEach(classificationDO -> {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("typeName", classificationDO.getClassificationName());
                    jsonObject.put("id", classificationDO.getId());
                    list.add(jsonObject);
                });
                questionDataVo.setTypeProblemList(list);
            }
            //查询问题
            List<QuestionDO> questionDOList = questionService.question(keyWord, questionName);
            List<QuestionDO> result = new ArrayList<>();
            if (!CollectionUtils.isEmpty(questionDOList)) {
                //根据关键词排序
                String[] split = keyWord.split("\\|");
                //城乡居民两病门诊用药报销比例是多少|城乡居民|城乡|乡居|居民|两|病|门诊|用药|报销|比例|是多少|多少
                List<QuestionDO> finalResult = result;
                questionDOList.stream().forEach(questionDO -> {
                    String trim = questionDO.getQuestionName().trim().replaceAll("[\\pP\\pS\\pZ]", "");
                    int count = 0;
                    for (String s : split) {
                        if (trim.equals(s)) {
                            questionDO.setSort(999);
                        } else if (questionDO.getQuestionName().contains(s)) {
                            count = count + 1;
                            questionDO.setSort(count);
                        }
                    }
                    finalResult.add(questionDO);
                });
            }

            if (!CollectionUtils.isEmpty(result)) {
                Collections.sort(result, (a, b) -> b.getSort().compareTo(a.getSort()));
                if (result.size() >= 20) {
                    result = result.subList(0, 20);
                }
                questionDataVo.setMsgType("new");
                questionDataVo.setAssociation("请问您是不是想咨询以下问题？");
                ArrayList<Object> list = new ArrayList<>();
                result.stream().forEach(questionDO -> {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("associationName", questionDO.getQuestionName());
                    jsonObject.put("id", questionDO.getId());
                    list.add(jsonObject);
                });
                questionDataVo.setAssociationList(list);
            }
            if (CollectionUtils.isEmpty(questionDOList) && CollectionUtils.isEmpty(classificationDOList)) {
                questionDataVo.setMsgType("text");
                questionDataVo.setTypeProblemTitle("您好，您的提问不够清晰，请您输入简要的问题。");
            }
            return R.okData(questionDataVo);
        } catch (Exception e) {
            log.error("根据用户输入的问题，查找相关分类 、问题异常,p={},e=", params, e);
            return R.error("请求异常");
        }
    }


    /**
     * 根据分类向下检索或者获取问题答案
     *
     * @return
     */
    @PostMapping("/classificationorquestion")
    @MethodLog
    public R classification(@RequestBody Map<String, Object> params) {
        try {
            if (params == null) {
                return R.error("您好，您的提问不够清晰，请您输入简要的问题。");
            }
            if (params.get("questionName") == null) {
                return R.error("您好，您的提问不够清晰，请您输入简要的问题。");
            }
            String questionName = params.get("questionName").toString().trim().replaceAll("[\\pP\\pS\\pZ]", "");
            if (StringUtils.isEmpty(questionName)) {
                return R.error("您好，您的提问不够清晰，请您输入简要的问题。");
            }
            if (params.get("id") == null) {
                return R.error("未选择问题");
            }
            Integer id = Integer.parseInt(params.get("id").toString().trim());
            if (params.get("type") == null) {
                return R.error("未选择类型");
            }
            //分类
            AnswerVo answerVo = new AnswerVo();
            if ("1".equals(params.get("type").toString().trim())) {
                //根据pid 查找下级分类
                List<ClassificationDO> classificationDOList = classificationService.classificationById(id);
                //下级分类存在
                if (!CollectionUtils.isEmpty(classificationDOList)) {
                    ArrayList<Object> list = new ArrayList<>();
                    answerVo.setMsgType("new");
                    answerVo.setTypeProblemTitle("请问您想了解" + questionName + "的哪些问题？");
                    classificationDOList.stream().forEach(classificationDO -> {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("typeName", classificationDO.getClassificationName());
                        jsonObject.put("id", classificationDO.getId());
                        list.add(jsonObject);
                    });
                    answerVo.setTypeProblemList(list);
                    //封装本级分类问题  根据分类id查询和问题的中间表
                    List<ClassificationQuestionDO> classificationQuestionDOlist = classificationQuestionService.getByClassificationId(id);
                    //有问题关联分类
                    if (!CollectionUtils.isEmpty(classificationQuestionDOlist)) {
                        answerVo.setAssociation("请问您是不是想咨询以下问题？");
                        ArrayList<Object> list2 = new ArrayList<>();
                        classificationQuestionDOlist.stream().forEach(classificationQuestionDO -> {
                            //中间表
                            Integer questionId = classificationQuestionDO.getQuestionId();
                            //根据问题id查找
                            QuestionDO questionDO = questionService.get(questionId);
                            if (questionDO != null) {
                                //返回问题列表
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("associationName", questionDO.getQuestionName());
                                jsonObject.put("id", questionDO.getId());
                                list2.add(jsonObject);
                            }
                        });
                        answerVo.setAssociationList(list2);
                        return R.okData(answerVo);
                    }
                    return R.okData(answerVo);
                } else {
                    //不存在下级分类 根据分类id查询和问题的中间表
                    List<ClassificationQuestionDO> classificationQuestionDOlist = classificationQuestionService.getByClassificationId(id);
                    //有问题关联分类
                    if (!CollectionUtils.isEmpty(classificationQuestionDOlist)) {
                        answerVo.setMsgType("new");
                        answerVo.setAssociation("请问您是不是想咨询以下问题？");
                        ArrayList<Object> list = new ArrayList<>();
                        classificationQuestionDOlist.stream().forEach(classificationQuestionDO -> {
                            //中间表
                            Integer questionId = classificationQuestionDO.getQuestionId();
                            //根据问题id查找
                            QuestionDO questionDO = questionService.get(questionId);
                            if (questionDO != null) {
                                //返回问题列表
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("associationName", questionDO.getQuestionName());
                                jsonObject.put("id", questionDO.getId());
                                list.add(jsonObject);
                            }

                        });
                        answerVo.setAssociationList(list);
                        return R.okData(answerVo);
                    } else {
                        answerVo.setMsgType("text");
                        answerVo.setTypeProblemTitle("很抱歉，未找到相关信息。");
                        return R.okData(answerVo);
                    }
                }
                //根据问题id 查找答案
            } else if ("2".equals(params.get("type").toString().trim())) {
                QuestionDO questionDO = questionService.get(id);
                if (questionDO != null) {
                    //根据问题id保存问题关键字
                    questionService.saveKeyWords(questionDO);
                    answerVo.setMsgType("answer");
                    answerVo.setId(id);
                    answerVo.setAnswerTitle(questionDO.getQuestionName());
                    answerVo.setAnswer(questionDO.getQuestionAnswer());
                    return R.okData(answerVo);
                }

            }
            answerVo.setMsgType("text");
            answerVo.setTypeProblemTitle("很抱歉，未找到相关信息。");
            return R.okData(answerVo);
        } catch (NumberFormatException e) {
            log.error("请求分类或者答案接口错误,p={},e=", params, e);
            return R.error("请求分类或者答案接口错误");
        }

    }

    /**
     * 修改已解决次数或者为解决次数
     */
    @PostMapping("/updatetimes")
    @MethodLog
    public R updateTimes(@RequestBody Map<String, Object> params) {
        try {
            if (params == null) {
                return R.error("您好，您的提问不够清晰，请您输入简要的问题。");
            }
            if (params.get("id") == null) {
                return R.error("您好，您的提问不够清晰，请您输入简要的问题。");
            }
            if (params.get("type") == null) {
                return R.error("请选择类型");
            }

            Integer id = Integer.parseInt(params.get("id").toString().trim());
            String type = params.get("type").toString().trim();
            Integer integer = questionService.updateTimes(id, type);
            if (integer > 0) {
                if ("0".equals(type)) {
                    //如果是未解决，那么需要为用户推荐6个问题
                    UnSolveVo<JSONObject> vo = new UnSolveVo<>();
                    ArrayList<JSONObject> result = new ArrayList<>();
                    List<QuestionDO> list = questionService.findSixQuestionWithoutSelf(id);
                    if (!CollectionUtils.isEmpty(list)) {
                        list.forEach(questionDO -> {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("context", questionDO.getQuestionName());
                            jsonObject.put("id", questionDO.getId());
                            result.add(jsonObject);
                        });
                    }
                    //查询是否有电话
                    List<TelephoneDO> listphone = telephoneService.listphone(id);
                    if (CollectionUtils.isEmpty(listphone)) {
                        //没有电话设置标识
                        vo.setFlag(false);
                    } else {
                        vo.setFlag(true);
                    }
                    vo.setMsgType("unsolved");
                    vo.setUnsolvedTitle("您可能想要问的问题？");
                    vo.setUnsolvedList(result);
                    return R.okData(vo);
                }
                return R.ok();
            } else {
                return R.error("更新失败，请稍后再试");
            }
        } catch (NumberFormatException e) {
            log.error("更新次数失败,p={},e=", params, e);
            return R.error("更新失败，请稍后再试");
        }
    }

    /**
     * 功能描述 统计问题：已解决 未解决
     *
     * @return cn.gov.hebei.ylbzj.entity.R
     */
    @GetMapping("/listSolveOrNot")
    public R listSolveOrNot(@RequestParam Map<String, Object> params) {
        Map<String, Object> map = InitData.getMap();
        if (CollectionUtils.isEmpty(map)) {
            return R.okData("暂无数据");
        } else {
            List<QuestionDO> questionList = (List<QuestionDO>) map.get("question_answer");
            if (CollectionUtils.isEmpty(questionList)) {
                return R.okData("暂无数据");
            } else {
                //list按照已解决次数、为解决次数排序
                List<QuestionDO> solveList = questionList.stream().sorted(Comparator.comparingInt(QuestionDO::getSolveTimes).reversed()).collect(Collectors.toList());
                List<QuestionDO> unSolveList = questionList.stream().sorted(Comparator.comparingInt(QuestionDO::getUnSolveTimes).reversed()).collect(Collectors.toList());
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("solve", solveList);
                jsonObject.put("unsolve", unSolveList);
                return R.okData(jsonObject);
            }

        }
    }
}
