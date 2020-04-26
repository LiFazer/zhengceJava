package cn.gov.hebei.ylbzj.service;

import cn.gov.hebei.ylbzj.entity.UserDO;

import java.util.List;
import java.util.Map;

/**
 * 管理员表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-01-14 15:39:46
 */
public interface UserService {
	
	UserDO get(Integer id);
	
	List<UserDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(UserDO user);
	
	int update(UserDO user);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);

	UserDO selectByName(String username);
}
