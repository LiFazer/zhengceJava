package cn.gov.hebei.ylbzj.vo;

import lombok.Data;

import java.util.List;

@Data
public class TelephoneVo<T> {
    //类型
    private String msgType;
    //title
    private String phoneTitle;
    //分类list数据
    List<T> phoneList;

}
