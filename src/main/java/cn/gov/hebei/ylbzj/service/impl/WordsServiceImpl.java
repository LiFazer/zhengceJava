package cn.gov.hebei.ylbzj.service.impl;

import cn.gov.hebei.ylbzj.dao.WordsDao;
import cn.gov.hebei.ylbzj.entity.WordsDO;
import cn.gov.hebei.ylbzj.service.WordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class WordsServiceImpl implements WordsService {
    @Autowired
    private WordsDao wordsDao;

    @Override
    public WordsDO get(Integer id) {
        return wordsDao.get(id);
    }

    @Override
    public List<WordsDO> list(Map<String, Object> map) {
        return wordsDao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return wordsDao.count(map);
    }

    @Override
    public int save(WordsDO words) {
        Date date = new Date();
        words.setUpdateTime(date);
        words.setInsertTime(date);
        return wordsDao.save(words);
    }

    @Override
    public int update(WordsDO words) {
        return wordsDao.update(words);
    }

    @Override
    public int remove(Integer id) {
        return wordsDao.remove(id);
    }

    @Override
    public int batchRemove(Integer[] ids) {
        return wordsDao.batchRemove(ids);
    }

}
