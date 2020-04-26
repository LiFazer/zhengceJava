package cn.gov.hebei.ylbzj.controller;

import cn.gov.hebei.ylbzj.entity.ClassificationQuestionDO;
import cn.gov.hebei.ylbzj.entity.R;
import cn.gov.hebei.ylbzj.service.ClassificationQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 分类和问答中间表
 *
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-01-13 12:42:20
 */

@RestController
@RequestMapping("/system/classificationquestion")
public class ClassificationQuestionController {
    @Autowired
    private ClassificationQuestionService classificationQuestionService;


    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        List<ClassificationQuestionDO> questionList = classificationQuestionService.list(params);
        return R.ok();
    }

    @GetMapping("/edit/{id}")
    String edit(@PathVariable("id") Integer id, Model model) {
        ClassificationQuestionDO question = classificationQuestionService.get(id);
        model.addAttribute("question", question);
        return "system/question/edit";
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(ClassificationQuestionDO question) {
        if (classificationQuestionService.save(question) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(ClassificationQuestionDO question) {
        classificationQuestionService.update(question);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    public R remove(Integer id) {
        if (classificationQuestionService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    public R remove(@RequestParam("ids[]") Integer[] ids) {
        classificationQuestionService.batchRemove(ids);
        return R.ok();
    }

}
