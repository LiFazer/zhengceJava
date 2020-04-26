package cn.gov.hebei.ylbzj.vo;

import cn.gov.hebei.ylbzj.entity.ClassificationDO;
import cn.gov.hebei.ylbzj.entity.QuestionDO;
import lombok.Data;

import java.util.List;

@Data
public class QuestionVo {
    private Integer totalCount;
    private Integer pageSize;
    private Integer totalPage;
    private Integer currPage;

    private List<QuestionDO> interesList;
    private List<ClassificationDO> associationList;
}
