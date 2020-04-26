package cn.gov.hebei.ylbzj.param;

import lombok.Data;

import java.util.List;

@Data
public class QAOptionParam {
    private Integer id;
    private String questionName;
    private String questionAnswer;
    private List<Integer> tagList;
    private Integer telId;
}
