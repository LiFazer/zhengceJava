package cn.gov.hebei.ylbzj.controller;

import cn.gov.hebei.ylbzj.entity.AnswerSynDO;
import cn.gov.hebei.ylbzj.entity.R;
import cn.gov.hebei.ylbzj.service.AnswerSynService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 问答同步数据表
 *
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-01-13 12:42:27
 */

@RestController
@RequestMapping("/system/answerSyn")
public class AnswerSynController {
    @Autowired
    private AnswerSynService answerSynService;


    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        List<AnswerSynDO> answerSynList = answerSynService.list(params);
        return R.ok();
    }


    @GetMapping("/edit/{id}")
    String edit(@PathVariable("id") Integer id, Model model) {
        AnswerSynDO answerSyn = answerSynService.get(id);
        model.addAttribute("answerSyn", answerSyn);
        return "system/answerSyn/edit";
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(AnswerSynDO answerSyn) {
        if (answerSynService.save(answerSyn) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(AnswerSynDO answerSyn) {
        answerSynService.update(answerSyn);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    public R remove(Integer id) {
        if (answerSynService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    public R remove(@RequestParam("ids[]") Integer[] ids) {
        answerSynService.batchRemove(ids);
        return R.ok();
    }

}
