package cn.gov.hebei.ylbzj.dao;

import cn.gov.hebei.ylbzj.entity.WordsDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 关键词表
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-02-08 11:53:12
 */
@Mapper
public interface WordsDao {

	WordsDO get(Integer id);
	
	List<WordsDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(WordsDO words);
	
	int update(WordsDO words);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
}
