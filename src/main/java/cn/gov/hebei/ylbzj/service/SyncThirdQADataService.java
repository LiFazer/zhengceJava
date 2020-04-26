package cn.gov.hebei.ylbzj.service;

import cn.gov.hebei.ylbzj.dao.QuestionDao;
import cn.gov.hebei.ylbzj.entity.AnswerDO;
import cn.gov.hebei.ylbzj.entity.QuestionDO;
import cn.gov.hebei.ylbzj.entity.SyncDataStatistics;
import cn.gov.hebei.ylbzj.third.dao.AnswerDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Slf4j
public class SyncThirdQADataService {
    @Autowired
    private AnswerDao answerDao;
    @Autowired
    private QuestionDao questionService;

    public void syncData(){
        log.info("开始执行问答数据同步");
        int offset = 0;
        int pageSize = 100;
        int page = 0;
        int maxLoop = 5000;
        List<AnswerDO> list = answerDao.select(offset,pageSize);
        if(CollectionUtils.isEmpty(list)){
            log.info("无问答数据，不执行同步");
            return;
        }
        SyncDataStatistics s = new SyncDataStatistics();
        while (!CollectionUtils.isEmpty(list) && page < maxLoop) {
            list.forEach(answerDO -> {
                s.addA(1);
                try {
                    QuestionDO q = questionService.getByQuestionName(answerDO.getQuestion());
                    if (needUpdate(q, answerDO)) {
                        //放弃修改
//                        q.setQuestionAnswer(answerDO.getAnswer());
//                        questionService.update(q);
                        s.addU(1);
                    } else if (q == null) {
                        questionService.save(a2q(answerDO));
                        s.addI(1);
                    } else {
                        s.addN(1);
                    }
                } catch (Exception e) {
                    s.addF(1);
                    log.error("同步问答数据异常，p{},e=", answerDO, e);
                }
            });
            page++;
            offset = pageSize * page;
            list = answerDao.select(offset,pageSize);
        }
        log.info("问答数据同步完成，概述：{},执行次数:{}",s,page);
    }

    private QuestionDO a2q(AnswerDO answerDO){
        QuestionDO questionDO = new QuestionDO();
        questionDO.setQuestionName(answerDO.getQuestion());
        questionDO.setQuestionAnswer(answerDO.getAnswer());
        return questionDO;
    }

    private boolean needUpdate(QuestionDO q,AnswerDO a){
        if(q == null){
            return false;
        }
        if(!q.getQuestionAnswer().equals(a.getAnswer())){
            return true;
        }
        return false;
    }
}
