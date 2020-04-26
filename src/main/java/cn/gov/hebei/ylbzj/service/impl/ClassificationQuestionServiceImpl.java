package cn.gov.hebei.ylbzj.service.impl;

import cn.gov.hebei.ylbzj.dao.ClassificationQuestionDao;
import cn.gov.hebei.ylbzj.entity.ClassificationQuestionDO;
import cn.gov.hebei.ylbzj.service.ClassificationQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;



@Service
public class ClassificationQuestionServiceImpl implements ClassificationQuestionService {
	@Autowired
	private ClassificationQuestionDao classificationQuestionDao;
	
	@Override
	public ClassificationQuestionDO get(Integer id){
		return classificationQuestionDao.get(id);
	}
	
	@Override
	public List<ClassificationQuestionDO> list(Map<String, Object> map){
		return classificationQuestionDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return classificationQuestionDao.count(map);
	}
	
	@Override
	public int save(ClassificationQuestionDO question){
		return classificationQuestionDao.save(question);
	}
	
	@Override
	public int update(ClassificationQuestionDO question){
		return classificationQuestionDao.update(question);
	}
	
	@Override
	public int remove(Integer id){
		return classificationQuestionDao.remove(id);
	}

	@Override
	public int remove(ClassificationQuestionDO question) {
		return classificationQuestionDao.removeByDO(question);
	}

	@Override
	public int batchRemove(Integer[] ids){
		return classificationQuestionDao.batchRemove(ids);
	}

	@Override
	public List<ClassificationQuestionDO> getByClassificationId(Integer id) {
		return classificationQuestionDao.getByClassificationId(id);
	}

	@Override
	public void batchRemoveByIds(List<Integer> idList) {
		classificationQuestionDao.batchRemoveByIdList(idList);
	}

}
