package cn.gov.hebei.ylbzj.controller;

import cn.gov.hebei.ylbzj.entity.R;
import cn.gov.hebei.ylbzj.entity.TimesDO;
import cn.gov.hebei.ylbzj.service.TimesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author SuSu
 * @description 小程序打开次数
 * @date 2020/2/7
 */
@RestController
@RequestMapping("/system/opentimes")
@Slf4j
public class OpenTimesController {
    @Autowired
    private TimesService timesService;


    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        List<TimesDO> timesList = timesService.list(params);
        return R.okData(timesList);
    }


    @GetMapping("/edit/{id}")
    String edit(@PathVariable("id") Integer id, Model model) {
        TimesDO times = timesService.get(id);
        model.addAttribute("times", times);
        return "system/times/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    public R save(TimesDO times) {
        if (timesService.save(times) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    public R update(TimesDO times) {
        timesService.update(times);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    public R remove(Integer id) {
        if (timesService.remove(id) > 0) {
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
        timesService.batchRemove(ids);
        return R.ok();
    }

    /**
     * 统计小程序打开次数:小程序调用
     */
    @GetMapping("/countOpenTimes")
    public R countOpenTimes() {
        timesService.countOpenTimes();
        return R.ok();
    }

    /**
     * 统计小程序打开次数
     */
    @GetMapping("/countTimes")
    public R countTimes() {
        try {
            TimesDO timesDO = timesService.countTimes();
            if (timesDO == null) {
                return R.okData(0);
            } else {
                return R.okData(timesDO.getOpenTimes().intValue());
            }
        } catch (Exception e) {
            log.error("查询小程序打开次数异常,,e=", e);
            return R.error("查询异常");
        }
    }
}
