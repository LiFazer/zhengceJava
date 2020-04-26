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
 * 政策表
 *
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-01-13 12:42:35
 */

@RestController
@Slf4j
@RequestMapping("/system/zhengce")
public class PolicyController {
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
}
