package cn.gov.hebei.ylbzj.vo;

import lombok.Data;

import java.util.List;

@Data
public class QuestionDataVo<T> {
    private String msgType;
    private String typeProblemTitle;
    private List<T> typeProblemList;
    private String association;
    private List<T> associationList;
}
