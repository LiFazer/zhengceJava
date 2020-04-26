package cn.gov.hebei.ylbzj.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class ClassificationVo implements Serializable {

    private static final long serialVersionUID = 1L;
    //主键自增
    //private Integer id;
    private Integer value;
    //父id
    private Integer pid;
    //分类名称
//    private String classificationName;
    private String title;
   /* //更新时间
    private Date updateTime;
    //创建时间
    private Date createTime;*/

    private List<ClassificationVo> children = new ArrayList<ClassificationVo>();

}
