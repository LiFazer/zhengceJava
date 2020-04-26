package cn.gov.hebei.ylbzj.dao;

import cn.gov.hebei.ylbzj.entity.TelephoneDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 联络方式表
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-02-07 11:02:18
 */
@Mapper
public interface TelephoneDao {

	TelephoneDO get(Integer id);
	
	List<TelephoneDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(TelephoneDO telephone);
	
	int update(TelephoneDO telephone);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);

    List<TelephoneDO> selectByIds(@Param("list") List<Integer> list);

	List<TelephoneDO> findByIds(@Param("list")List<Integer> list);
}
