package cn.gov.hebei.ylbzj.dao;

import cn.gov.hebei.ylbzj.entity.ClassificationQuestionDO;
import cn.gov.hebei.ylbzj.entity.QuestionDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 问答表
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-01-13 12:42:24
 */
public interface QuestionDao {

	QuestionDO get(Integer id);
	
	List<QuestionDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(QuestionDO question);
	
	int update(QuestionDO question);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);

    List<QuestionDO> findThreeQuestion();

	List<QuestionDO> question(@Param("questionName") String questionName);

	Integer countByQuestionName(@Param("questionName") String questionName);

	QuestionDO getByQuestionName(@Param("questionName") String questionName);

	Integer updateSolveTimes(@Param("id") Integer id);

	Integer updateUnSolveTimes(@Param("id") Integer id);

	List<QuestionDO> queryMatchList(@Param("questionName") String questionName, @Param("idList") List<Integer> idList);

	List<QuestionDO> findSixQuestionWithoutSelf(@Param("id") Integer id);

	List<QuestionDO> questionAll(Map<String, Object> params);
}
