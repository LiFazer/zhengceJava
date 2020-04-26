package cn.gov.hebei.ylbzj.vo;

import lombok.Data;

import java.util.List;

@Data
public class ChangeVo<T> {

    String msgType;
    String interesTitle;
    List<T> interesList;
}