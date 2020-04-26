package cn.gov.hebei.ylbzj.service.impl;

import cn.gov.hebei.ylbzj.dao.AnswerSynDao;
import cn.gov.hebei.ylbzj.entity.AnswerSynDO;
import cn.gov.hebei.ylbzj.service.AnswerSynService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;



@Service
public class AnswerSynServiceImpl implements AnswerSynService {
	@Autowired
	private AnswerSynDao answerSynDao;
	
	@Override
	public AnswerSynDO get(Integer id){
		return answerSynDao.get(id);
	}
	
	@Override
	public List<AnswerSynDO> list(Map<String, Object> map){
		return answerSynDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return answerSynDao.count(map);
	}
	
	@Override
	public int save(AnswerSynDO answerSyn){
		return answerSynDao.save(answerSyn);
	}
	
	@Override
	public int update(AnswerSynDO answerSyn){
		return answerSynDao.update(answerSyn);
	}
	
	@Override
	public int remove(Integer id){
		return answerSynDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return answerSynDao.batchRemove(ids);
	}
	
}
