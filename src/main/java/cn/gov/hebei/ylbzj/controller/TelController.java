package cn.gov.hebei.ylbzj.controller;

import cn.gov.hebei.ylbzj.entity.R;
import cn.gov.hebei.ylbzj.entity.TelDO;
import cn.gov.hebei.ylbzj.service.TelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 问题和电话关联表
 *
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-02-12 12:07:10
 */

@RestController
@RequestMapping("/system/tel")
public class TelController {
    @Autowired
    private TelService telService;


    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        List<TelDO> telList = telService.list(params);
        return R.okData(telList);
    }


    @GetMapping("/edit/{id}")
    String edit(@PathVariable("id") Integer id, Model model) {
        TelDO tel = telService.get(id);
        model.addAttribute("tel", tel);
        return "system/tel/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    public R save(TelDO tel) {
        if (telService.save(tel) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    public R update(TelDO tel) {
        telService.update(tel);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    public R remove(Integer id) {
        if (telService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    public R remove(@RequestParam("ids[]") Integer[] ids) {
        telService.batchRemove(ids);
        return R.ok();
    }

}
