package cn.gov.hebei.ylbzj.service;

import cn.gov.hebei.ylbzj.entity.TelephoneDO;

import java.util.List;
import java.util.Map;

/**
 * 联络方式
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-02-07 11:02:18
 */
public interface TelephoneService {
	
	TelephoneDO get(Integer id);
	
	List<TelephoneDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(TelephoneDO telephone);
	
	int update(TelephoneDO telephone);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);

	List<TelephoneDO> listphone(Integer id);

	List<TelephoneDO> findByIds(List<Integer> ids);
}
