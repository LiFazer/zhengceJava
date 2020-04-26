package cn.gov.hebei.ylbzj.controller;

import cn.gov.hebei.ylbzj.annotation.MethodLog;
import cn.gov.hebei.ylbzj.constant.InitData;
import cn.gov.hebei.ylbzj.entity.R;
import cn.gov.hebei.ylbzj.entity.WordsDO;
import cn.gov.hebei.ylbzj.service.WordsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 关键词表
 *
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-02-08 11:53:12
 */

@RestController
@RequestMapping("/system/words")
@Slf4j
public class WordsController {
    @Autowired
    private WordsService wordsService;

    /**
     * 功能描述  热词排行统计
     *
     * @param params
     * @return cn.gov.hebei.ylbzj.entity.R
     */
    @GetMapping("/list")
    @MethodLog
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        //List<WordsDO> wordsList = wordsService.list(params);
        try {
            Map<String, Object> map = InitData.getMap();
            if (CollectionUtils.isEmpty(map)) {
                return R.okData("暂无搜索热词");
            } else {
                List<WordsDO> wordsList = (List<WordsDO>) map.get("key_words");
                if (CollectionUtils.isEmpty(wordsList)) {
                    return R.okData("暂无搜索热词");
                } else {
                    //list按照查询次数排序
                    Collections.sort(wordsList, (a, b) -> b.getCountTimes().compareTo(a.getCountTimes()));
                    return R.okData(wordsList);
                }
            }
        } catch (Exception e) {
            log.error("热词排行统计异常,e=", e);
            return R.error("热词排行统计异常");
        }
    }

    /**
     * 功能描述  热词无反馈统计
     *
     * @param params
     * @return cn.gov.hebei.ylbzj.entity.R
     */
    @GetMapping("/listNoAnswer")
    @MethodLog
    public R listNoAnswer(@RequestParam Map<String, Object> params) {
        try {
            Map<String, Object> map = InitData.getMap();
            if (CollectionUtils.isEmpty(map)) {
                return R.okData("暂无搜索热词");
            } else {
                List<WordsDO> wordsList = (List<WordsDO>) map.get("key_words");
                if (CollectionUtils.isEmpty(wordsList)) {
                    return R.okData("暂无搜索热词");
                } else {
                    //list按照查询次数排序
                    List<WordsDO> list = wordsList.stream().filter(wordsDO -> wordsDO.getFeedback().intValue() == 0).collect(Collectors.toList());
                    if (CollectionUtils.isEmpty(list)) {
                        return R.okData("暂无没有反馈信息的热词");
                    } else {
                        Collections.sort(list, (a, b) -> b.getCountTimes().compareTo(a.getCountTimes()));
                        return R.okData(list);
                    }
                }
            }
        } catch (Exception e) {
            log.error("热词无反馈统计异常,e=", e);
            return R.error("热词无反馈统计异常");
        }
    }


    @GetMapping("/edit/{id}")
    String edit(@PathVariable("id") Integer id, Model model) {
        WordsDO words = wordsService.get(id);
        model.addAttribute("words", words);
        return "system/words/edit";
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(WordsDO words) {
        if (wordsService.save(words) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(WordsDO words) {
        wordsService.update(words);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    public R remove(Integer id) {
        if (wordsService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    public R remove(@RequestParam("ids[]") Integer[] ids) {
        wordsService.batchRemove(ids);
        return R.ok();
    }

}
