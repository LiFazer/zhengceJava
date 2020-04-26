package cn.gov.hebei.ylbzj.service;

import cn.gov.hebei.ylbzj.entity.ClassificationDO;
import cn.gov.hebei.ylbzj.vo.ClassificationVo;

import java.util.List;
import java.util.Map;

/**
 * 分类表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-01-13 12:42:35
 */
public interface ClassificationService {
	
	ClassificationDO get(Integer id);
	
	List<ClassificationDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(ClassificationDO classification);
	
	int update(ClassificationDO classification);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);

    List<ClassificationDO> findClassificationLikeQuestionName(String keyWord,String questionName);

    List<ClassificationDO> findClassificationLikeQuestionName(String keyWord);

	List<ClassificationDO> classificationById(Integer id);

	List<ClassificationVo> findAllClassification();

	List<String> getNameByIds(List<Integer> tagListOld);

	void delete(Integer id);

}
