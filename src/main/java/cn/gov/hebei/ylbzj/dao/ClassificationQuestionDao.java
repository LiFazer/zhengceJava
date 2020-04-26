package cn.gov.hebei.ylbzj.dao;

import cn.gov.hebei.ylbzj.entity.ClassificationQuestionDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 分类和问答中间表
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-01-13 12:42:20
 */
public interface ClassificationQuestionDao {

	ClassificationQuestionDO get(Integer id);
	
	List<ClassificationQuestionDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ClassificationQuestionDO question);
	
	int update(ClassificationQuestionDO question);
	
	int remove(Integer id);

	int removeByDO(ClassificationQuestionDO question);
	
	int batchRemove(Integer[] ids);

	List<ClassificationQuestionDO> getByClassificationId(@Param("id") Integer id);

	List<ClassificationQuestionDO> listAllByClassificationIdList(@Param("list") List<Integer> list);

	String getByQuestionId(@Param("id") Integer id);

	void batchRemoveByIdList(@Param("list")List<Integer> list);
}
