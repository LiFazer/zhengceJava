package cn.gov.hebei.ylbzj.dao;

import cn.gov.hebei.ylbzj.entity.ClassificationDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 分类表
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-01-13 12:42:35
 */
public interface ClassificationDao {

	ClassificationDO get(Integer id);
	
	List<ClassificationDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ClassificationDO classification);
	
	int update(ClassificationDO classification);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);

	List<ClassificationDO> findClassificationLikeQuestionName(@Param("questionName") String questionName);

	List<ClassificationDO> classificationById(@Param("id") Integer id);

	List<ClassificationDO> findAllClassification();

    List<String> getNameByIds(@Param("ids") List<Integer> ids);

	void batchRemoveByIdList(@Param("ids") List<Integer> ids);
}
