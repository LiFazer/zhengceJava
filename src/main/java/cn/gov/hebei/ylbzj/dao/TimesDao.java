package cn.gov.hebei.ylbzj.dao;

import cn.gov.hebei.ylbzj.entity.TimesDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 小程序打开次数记录表
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-02-07 09:53:39
 */
@Mapper
public interface TimesDao {

	TimesDO get(Integer id);
	
	List<TimesDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(TimesDO times);
	
	int update(TimesDO times);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
