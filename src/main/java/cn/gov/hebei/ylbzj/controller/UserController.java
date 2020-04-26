package cn.gov.hebei.ylbzj.controller;

import cn.gov.hebei.ylbzj.entity.R;
import cn.gov.hebei.ylbzj.entity.UserDO;
import cn.gov.hebei.ylbzj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 管理员表
 *
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-01-14 15:39:46
 */

@Controller
@RequestMapping("/system/user")
public class UserController {
    @Autowired
    private UserService userService;

    @ResponseBody
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据

        List<UserDO> userList = userService.list(params);
        return R.ok();
    }


    @GetMapping("/edit/{id}")
    String edit(@PathVariable("id") Integer id, Model model) {
        UserDO user = userService.get(id);
        model.addAttribute("user", user);
        return "system/user/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    public R save(UserDO user) {
        if (userService.save(user) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    public R update(UserDO user) {
        userService.update(user);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    public R remove(Integer id) {
        if (userService.remove(id) > 0) {
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
        userService.batchRemove(ids);
        return R.ok();
    }

    @PostMapping("/login")
    public void login(@RequestParam("username") String username, @RequestParam("password") String password){


    }
}
