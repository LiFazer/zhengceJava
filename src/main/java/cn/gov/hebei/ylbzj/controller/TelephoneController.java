package cn.gov.hebei.ylbzj.controller;

import cn.gov.hebei.ylbzj.annotation.MethodLog;
import cn.gov.hebei.ylbzj.entity.QuestionDO;
import cn.gov.hebei.ylbzj.entity.R;
import cn.gov.hebei.ylbzj.entity.TelDO;
import cn.gov.hebei.ylbzj.entity.TelephoneDO;
import cn.gov.hebei.ylbzj.service.TelService;
import cn.gov.hebei.ylbzj.service.TelephoneService;
import cn.gov.hebei.ylbzj.vo.TelephoneVo;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 联络方式表
 *
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-02-07 11:02:18
 */

@RestController
@RequestMapping("/system/telephone")
@Slf4j
public class TelephoneController {
    @Autowired
    private TelephoneService telephoneService;

    @Autowired
    private TelService telService;

    /**
     * 功能描述 查询库中所有的联络方式
     *
     * @return cn.gov.hebei.ylbzj.entity.R
     */
    @GetMapping("/list")
    @MethodLog
    public R list() {
        try {
            //查询列表数据
            Map<String, Object> params = new HashMap<>();
            List<TelephoneDO> telephoneList = telephoneService.list(params);
            if (CollectionUtils.isEmpty(telephoneList)) {
                return R.okData("暂无电话列表数据");
            } else {
                return R.okData(telephoneList);
            }
        } catch (Exception e) {
            log.error("联络方式查询异常,e=", e);
            return R.error("查询联络方式详情异常");
        }
    }

    @GetMapping("/detail")
    @MethodLog
    public R detail(@RequestParam("id") Integer id) {
        try {
            TelephoneDO telephone = telephoneService.get(id);
            return R.okData(telephone);
        } catch (Exception e) {
            log.error("电话详情查询你异常,p{},e=", id, e);
            return R.error("查询详情异常");
        }
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @MethodLog
    public R save(@RequestBody TelephoneDO telephone) {
        try {
            if (telephoneService.save(telephone) > 0) {
                return R.ok();
            }
            return R.error();
        } catch (Exception e) {
            log.error("保存电话异常,p{},e=", telephone, e);
            return R.error("保存电话异常");
        }
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @MethodLog
    public R update(@RequestBody TelephoneDO telephone) {
        try {
            telephoneService.update(telephone);
            return R.ok();
        } catch (Exception e) {
            log.error("修改电话异常,p{},e=", telephone, e);
            return R.error("修改电话异常");
        }
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    public R remove(Integer id) {
        //删除电话号码
        if (telephoneService.remove(id) > 0) {
            //删除电话号码关联的问题
            List<TelDO> telDOList = telService.findByTelId(id);
            if(!CollectionUtils.isEmpty(telDOList)){
                List<Integer> collect = telDOList.stream().map(TelDO::getId).collect(Collectors.toList());
                telService.batchRemoveByIdList(collect);
            }
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @MethodLog
    public R remove(@RequestBody Integer[] ids) {
        try {
            telephoneService.batchRemove(ids);
            return R.ok();
        } catch (Exception e) {
            log.error("删除电话异常,p{},e=", ids, e);
            return R.error("删除电话异常");
        }
    }

    @GetMapping("/query")
    public R query(@RequestParam(name = "page") int page, @RequestParam(name = "pageSize", required = false) Integer pageSize) {
        try {
            if (pageSize == null) {
                pageSize = 10;
            }
            Map<String, Object> map = new HashMap<>();
            PageHelper.startPage(page, pageSize);
            List<TelephoneDO> telephoneList = telephoneService.list(map);
            PageInfo<TelephoneDO> pageInfo = new PageInfo<>(telephoneList, pageSize);
            return R.okData(pageInfo);
        } catch (Exception e) {
            log.error("查询电话列表异常,p1{},p2{},e=", page, pageSize, e);
            return R.error("查询电话列表异常");
        }
    }

    /**
     * 为解决 提供用户联络电话
     */
    @PostMapping("/listphone")
    @MethodLog
    public R listphone(@RequestBody Map<String, Object> params) {
        try {
            if (params == null) {
                return R.error("参数为空");
            }
            if (params.get("id") == null) {
                return R.error("请传入未解决问题的id");
            }
            Integer id = Integer.parseInt(params.get("id").toString().trim());
            List<TelephoneDO> listphone = telephoneService.listphone(id);
            //封装结果集
            TelephoneVo<TelephoneDO> telephoneVo = new TelephoneVo<>();
            telephoneVo.setMsgType("phone");
            telephoneVo.setPhoneTitle("人工客服联系方式");
            if (CollectionUtils.isEmpty(listphone)) {
                telephoneVo.setPhoneList(null);
                return R.okData(telephoneVo);
            } else {
                telephoneVo.setPhoneList(listphone);
                return R.okData(telephoneVo);
            }
        } catch (Exception e) {
            log.error("查询电话异常,p1{},e=", params, e);
            return R.error("查询电话异常");
        }
    }
}
