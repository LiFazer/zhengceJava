package cn.gov.hebei.ylbzj.vo;

import lombok.Data;

import java.util.List;

@Data
public class AnswerVo<T> {
    //类型
    private String msgType;
    //分类title
    private String typeProblemTitle;
    //分类list数据
    List<T> typeProblemList;
    //答案titile
    private String answerTitle;
    //答案
    private String answer;
    //问题title
    private String association;
    //问题list
    private List<T> associationList;
    //答案id
    private Integer id;
}
