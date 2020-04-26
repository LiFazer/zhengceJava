package cn.gov.hebei.ylbzj.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 问答同步数据表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-01-13 12:42:27
 */
public class AnswerSynDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键自增
	private Integer id;
	//问题
	private String questionName;
	//答案
	private String questionAnswer;

	/**
	 * 设置：主键自增
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：主键自增
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：问题
	 */
	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}
	/**
	 * 获取：问题
	 */
	public String getQuestionName() {
		return questionName;
	}
	/**
	 * 设置：答案
	 */
	public void setQuestionAnswer(String questionAnswer) {
		this.questionAnswer = questionAnswer;
	}
	/**
	 * 获取：答案
	 */
	public String getQuestionAnswer() {
		return questionAnswer;
	}

}
