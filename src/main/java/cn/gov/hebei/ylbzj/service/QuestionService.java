package cn.gov.hebei.ylbzj.service;

import cn.gov.hebei.ylbzj.entity.QuestionDO;

import java.util.List;
import java.util.Map;

/**
 * 问答表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-01-13 12:42:24
 */
public interface QuestionService {
	
	QuestionDO get(Integer id);
	
	List<QuestionDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(QuestionDO question);
	
	int update(QuestionDO question);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);

	List<QuestionDO> findThreeQuestion();

	List<QuestionDO> question(String keyWord,String questionName);

	List<QuestionDO> queryMatchList(String keyWord,List<Integer> list);

	Integer countByQuestionName(String  msg);

	Integer updateTimes(Integer id, String type);

	List<QuestionDO> findSixQuestionWithoutSelf(Integer id);

	void saveKeyWords(QuestionDO questionDO);


	List<QuestionDO> questionAll(String keyWord, String questionName, Map<String, Object> params);
}
