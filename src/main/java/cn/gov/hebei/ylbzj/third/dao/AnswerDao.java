package cn.gov.hebei.ylbzj.third.dao;

import cn.gov.hebei.ylbzj.entity.AnswerDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AnswerDao {

    List<AnswerDO> select(@Param("offset") Integer offset,@Param("pageSize") Integer pageSize);
}
