package cn.gov.hebei.ylbzj.service.impl;

import cn.gov.hebei.ylbzj.dao.TelDao;
import cn.gov.hebei.ylbzj.entity.TelDO;
import cn.gov.hebei.ylbzj.entity.TelephoneDO;
import cn.gov.hebei.ylbzj.service.TelService;
import cn.gov.hebei.ylbzj.service.TelephoneService;
import org.apache.el.parser.JJTELParserState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class TelServiceImpl implements TelService {
	@Autowired
	private TelDao telDao;
	@Autowired
	private TelephoneService telephoneService;
	
	@Override
	public TelDO get(Integer id){
		return telDao.get(id);
	}
	
	@Override
	public List<TelDO> list(Map<String, Object> map){
		return telDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return telDao.count(map);
	}
	
	@Override
	public int save(TelDO tel){
		Date date = new Date();
		tel.setInsertTime(date);
		tel.setUpdateTime(date);
		return telDao.save(tel);
	}
	
	@Override
	public int update(TelDO tel){
		return telDao.update(tel);
	}
	
	@Override
	public int remove(Integer id){
		return telDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return telDao.batchRemove(ids);
	}

	@Override
	public List<TelDO> findTelByIds(List<Integer> list) {
		return telDao.findTelByIds(list);
	}

    @Override
    public void batchRemoveByIdList(List<Integer> collect) {
        telDao.batchRemoveByIdList(collect);
    }

	@Override
	public List<TelDO> findByTelId(Integer id) {
		return telDao.findByTelId(id);
	}

}
