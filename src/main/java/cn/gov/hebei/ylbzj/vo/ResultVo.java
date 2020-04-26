package cn.gov.hebei.ylbzj.vo;

import lombok.Data;

import java.util.List;

@Data
public class ResultVo<T> {

    String typeProblemTitle;
    Integer msgType;
    List<T> typeProblemList;
}
