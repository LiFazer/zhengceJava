package cn.gov.hebei.ylbzj.constant;

import cn.gov.hebei.ylbzj.dao.QuestionDao;
import cn.gov.hebei.ylbzj.dao.WordsDao;
import cn.gov.hebei.ylbzj.entity.QuestionDO;
import cn.gov.hebei.ylbzj.entity.WordsDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author SuSu
 * @description
 * @date 2020/2/9
 */
@Component
public class InitData {

    @Autowired
    private WordsDao wordsDao;
    @Autowired
    private QuestionDao questionDao;

    private static Map<String, Object> map = new ConcurrentHashMap<>();

    public static Map<String, Object> getMap() {
        return map;
    }

    @PostConstruct
    public void init() {
        refreshData();
    }

    @Scheduled(cron = "0 0/2 * * * ? ")
    public void token() {
        refreshData();
    }

    private void refreshData() {
        Map<String, Object> params = new HashMap<>();
        //关键词
        List<WordsDO> wordsList = wordsDao.list(params);
        //问题
        Map<String, Object> params2 = new HashMap<>();
        List<QuestionDO> questionList = questionDao.list(params2);
        map.put("key_words", wordsList);
        //问题
        map.put("question_answer", questionList);
    }
}
