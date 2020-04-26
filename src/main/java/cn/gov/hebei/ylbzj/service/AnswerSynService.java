package cn.gov.hebei.ylbzj.service;

import cn.gov.hebei.ylbzj.entity.AnswerSynDO;

import java.util.List;
import java.util.Map;

/**
 * 问答同步数据表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-01-13 12:42:27
 */
public interface AnswerSynService {
	
	AnswerSynDO get(Integer id);
	
	List<AnswerSynDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(AnswerSynDO answerSyn);
	
	int update(AnswerSynDO answerSyn);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
