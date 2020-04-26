package cn.gov.hebei.ylbzj.dao;

import cn.gov.hebei.ylbzj.entity.TelDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 问题和电话关联表
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-02-12 12:07:10
 */
@Mapper
public interface TelDao {

	TelDO get(Integer id);
	
	List<TelDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(TelDO tel);
	
	int update(TelDO tel);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);

    List<TelDO> findTelByIds(@Param("list") List<Integer> list);

	void batchRemoveByIdList(@Param("list") List<Integer> list);

	List<TelDO> findByTelId(@Param("id") Integer id);
}
