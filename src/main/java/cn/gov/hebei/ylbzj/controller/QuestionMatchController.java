package cn.gov.hebei.ylbzj.controller;

import cn.gov.hebei.ylbzj.annotation.MethodLog;
import cn.gov.hebei.ylbzj.entity.ClassificationDO;
import cn.gov.hebei.ylbzj.entity.R;
import cn.gov.hebei.ylbzj.service.ClassificationQuestionService;
import cn.gov.hebei.ylbzj.service.ClassificationService;
import cn.gov.hebei.ylbzj.service.QuestionService;
import cn.gov.hebei.ylbzj.vo.ClassificationAddVo;
import cn.gov.hebei.ylbzj.vo.ClassificationVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 问题匹配
 */
@RestController
@Slf4j
@RequestMapping("/system/questionmatch")
public class QuestionMatchController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private ClassificationService classificationService;

    @Autowired
    private ClassificationQuestionService classificationQuestionService;

    /**
     * 问题详情
     *
     * @param id 问题id
     * @return 问题对象
     */
    @GetMapping("/detail")
    @MethodLog
    public R detail(@RequestParam("id") Integer id) {
        try {
            return R.okData(questionService.get(id));
        } catch (Exception e) {
            log.error("问题匹配，查询详情异常,p{},e=", id, e);
            return R.error("查询详情异常");
        }
    }

    /**
     * 类别增加
     * type = 0 主分类
     * type = 1 子分类
     * @return
     */
    @PostMapping("/addclassification")
    @MethodLog
    public R addClassification(@RequestBody ClassificationAddVo classificationAddVo) {
        try {
            if (classificationAddVo == null) {
                return R.error("参数为空");
            }
            if (classificationAddVo.getType() == null) {
                return R.error("未选择类别类型");
            }
            ClassificationDO classificationDO = new ClassificationDO();
            BeanUtils.copyProperties(classificationAddVo, classificationDO);
            //主分类 pid = 0
            if (classificationAddVo.getType().intValue() == 0) {
                classificationDO.setPid(0);
            } else if (classificationAddVo.getType().intValue() == 1) {
                classificationDO.setPid(classificationAddVo.getPid());
            }
            return classificationService.save(classificationDO) > 0 ? R.ok():R.error("添加失败");
        } catch (DuplicateKeyException e){
            log.error("添加分类触发唯一索引,e=", e);
            return R.error("该分类已经存在");
        }catch (NumberFormatException e) {
            log.error("添加分类异常,p{},e=", classificationAddVo, e);
            return R.error("添加分类异常");
        }
    }

    /**
     * 分类类型
     *
     * @return
     */
    @GetMapping("/classificationlist")
    @MethodLog
    public R classificationList() {
        try {
            List<ClassificationVo> classificationDOList = classificationService.findAllClassification();
            return R.okData(classificationDOList);
        } catch (Exception e) {
            log.error("获取分配异常,e=", e);
            return R.error("查询分类异常");
        }
    }

    /**
     * 类别名称修改
     * @return
     */
    @PostMapping("/updatename")
    @MethodLog
    public R updateClassificationName(@RequestBody ClassificationAddVo classificationAddVo) {
        try {
            if (classificationAddVo == null) {
                return R.error("参数为空");
            }
            ClassificationDO classificationDO = new ClassificationDO();
            BeanUtils.copyProperties(classificationAddVo, classificationDO);
            return classificationService.update(classificationDO) > 0 ? R.ok():R.error("修改失败");
        } catch (DuplicateKeyException e){
            log.error("添加分类触发唯一索引,e=", e);
            return R.error("该分类已经存在");
        }catch (NumberFormatException e) {
            log.error("添加分类异常,p{},e=", classificationAddVo, e);
            return R.error("添加分类异常");
        }

    }

    @GetMapping("/delete")
    @MethodLog
    public R delete(@RequestParam("id") Integer id) {
        try {
            classificationService.delete(id);
            return R.ok();
        } catch (Exception e) {
            log.error("删除分类异常,p{},e=", id, e);
            return R.error("删除分类异常");
        }
    }



}
