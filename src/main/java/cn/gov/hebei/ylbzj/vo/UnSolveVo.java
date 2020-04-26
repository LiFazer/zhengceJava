package cn.gov.hebei.ylbzj.vo;

import lombok.Data;

import java.util.List;

/**
 * @author SuSu
 * @description
 * @date 2020/2/10
 */
@Data
public class UnSolveVo<T> {
    //类型
    private String msgType;
    //title
    private String unsolvedTitle;
    //分类list数据
    List<T> unsolvedList;
    //是否有关联电话
    private boolean flag;
}
