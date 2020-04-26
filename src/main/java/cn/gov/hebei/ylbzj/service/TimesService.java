package cn.gov.hebei.ylbzj.service;

import cn.gov.hebei.ylbzj.entity.TimesDO;

import java.util.List;
import java.util.Map;

/**
 * 小程序打开次数记录表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-02-07 09:53:39
 */
public interface TimesService {
	
	TimesDO get(Integer id);
	
	List<TimesDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(TimesDO times);
	
	int update(TimesDO times);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);

	void countOpenTimes();

	TimesDO countTimes();

}
