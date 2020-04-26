package cn.gov.hebei.ylbzj.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ClassificationAddVo {
    //主键自增
    private Integer id;
    //父id
    private Integer pid;
    //分类名称
    private String classificationName;
    //更新时间
    private Date updateTime;
    //创建时间
    private Date createTime;

    private Integer type;
}
