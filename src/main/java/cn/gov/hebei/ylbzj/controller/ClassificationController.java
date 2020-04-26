package cn.gov.hebei.ylbzj.controller;

import cn.gov.hebei.ylbzj.entity.ClassificationDO;
import cn.gov.hebei.ylbzj.entity.ClassificationQuestionDO;
import cn.gov.hebei.ylbzj.entity.QuestionDO;
import cn.gov.hebei.ylbzj.entity.R;
import cn.gov.hebei.ylbzj.service.ClassificationQuestionService;
import cn.gov.hebei.ylbzj.service.ClassificationService;
import cn.gov.hebei.ylbzj.service.QuestionService;
import cn.gov.hebei.ylbzj.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 分类表
 *
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-01-13 12:42:35
 */

@RestController
@RequestMapping("/system/classification")
public class ClassificationController {
    @Autowired
    private ClassificationService classificationService;
    @Autowired
    private ClassificationQuestionService classificationQuestionService;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        List<ClassificationDO> classificationList = classificationService.list(params);
        return R.ok();
    }

    @GetMapping("/edit/{id}")
    String edit(@PathVariable("id") Integer id, Model model) {
        ClassificationDO classification = classificationService.get(id);
        model.addAttribute("classification", classification);
        return "system/classification/edit";
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(ClassificationDO classification) {
        if (classificationService.save(classification) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(ClassificationDO classification) {
        classificationService.update(classification);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    public R remove(Integer id) {
        if (classificationService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    public R remove(@RequestParam("ids[]") Integer[] ids) {
        classificationService.batchRemove(ids);
        return R.ok();
    }

}
