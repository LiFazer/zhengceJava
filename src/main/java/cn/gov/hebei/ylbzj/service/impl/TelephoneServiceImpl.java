package cn.gov.hebei.ylbzj.service.impl;

import cn.gov.hebei.ylbzj.dao.TelDao;
import cn.gov.hebei.ylbzj.dao.TelephoneDao;
import cn.gov.hebei.ylbzj.entity.TelDO;
import cn.gov.hebei.ylbzj.entity.TelephoneDO;
import cn.gov.hebei.ylbzj.service.TelephoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class TelephoneServiceImpl implements TelephoneService {
	@Autowired
	private TelephoneDao telephoneDao;

	@Autowired
	private TelDao telDao;
	
	@Override
	public TelephoneDO get(Integer id){
		return telephoneDao.get(id);
	}
	
	@Override
	public List<TelephoneDO> list(Map<String, Object> map){
		return telephoneDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return telephoneDao.count(map);
	}
	
	@Override
	public int save(TelephoneDO telephone){
		Date date = new Date();
		telephone.setUpdateTime(date);
		telephone.setInsertTime(date);
		return telephoneDao.save(telephone);
	}
	
	@Override
	public int update(TelephoneDO telephone){
		return telephoneDao.update(telephone);
	}
	
	@Override
	public int remove(Integer id){
		return telephoneDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return telephoneDao.batchRemove(ids);
	}

	@Override
	public List<TelephoneDO> listphone(Integer id) {
		//根据问题id查询该问题对应的电话号码
		Map<String, Object> map = new HashMap<>();
		map.put("questionId",id);
		List<TelDO> list = telDao.list(map);
		if (!CollectionUtils.isEmpty(list)) {
			List<Integer> collect = list.stream().map(TelDO::getTelId).collect(Collectors.toList());
			//根据电话id查询电话列表
			if(!CollectionUtils.isEmpty(collect)){
				List<TelephoneDO> telephoneDOList = telephoneDao.selectByIds(collect);
				return telephoneDOList;
			}
		}
		return Collections.EMPTY_LIST;
	}

	@Override
	public List<TelephoneDO> findByIds(List<Integer> ids) {
		return telephoneDao.findByIds(ids);
	}

}
