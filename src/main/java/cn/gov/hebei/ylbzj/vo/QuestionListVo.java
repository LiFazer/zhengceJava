package cn.gov.hebei.ylbzj.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class QuestionListVo implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键自增
    private Integer id;
    //问题
    private String questionName;
    //答案
    private String questionAnswer;
    //已解决次数
    private Integer solveTimes;
    //未解决次数
    private Integer unSolveTimes;
    //是否匹配：0不匹配，1匹配（默认0)
    private Integer matchOrNot;
    private String updateTime;


    private List<Integer> tagList;

    //分类名称
    private List<String> classificationName;

}
