package cn.gov.hebei.ylbzj.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 问答表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-01-13 12:42:24
 */
public class QuestionDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键自增
	private Integer id;
	//问题
	private String questionName;
	//答案
	private String questionAnswer;
	//已解决次数
	private Integer solveTimes;
	//未解决次数
	private Integer unSolveTimes;
	//是否匹配：0不匹配，1匹配（默认0)
	private Integer matchOrNot;
	private String updateTime;

	private String telName;

	private String tel;

	private Integer telId;

	private Integer sort;

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	private List<Integer> tagList;

	//分类名称
	private List<String> classificationName;

	public List<Integer> getTagList() {
		return tagList;
	}

	public void setTagList(List<Integer> tagList) {
		this.tagList = tagList;
	}

	public List<String> getClassificationName() {
		return classificationName;
	}

	public void setClassificationName(List<String> classificationName) {
		this.classificationName = classificationName;
	}

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

	public Integer getSolveTimes() {
		return solveTimes;
	}

	public void setSolveTimes(Integer solveTimes) {
		this.solveTimes = solveTimes;
	}

	public Integer getUnSolveTimes() {
		return unSolveTimes;
	}

	public void setUnSolveTimes(Integer unSolveTimes) {
		this.unSolveTimes = unSolveTimes;
	}

	public Integer getMatchOrNot() {
		return matchOrNot;
	}

	public void setMatchOrNot(Integer matchOrNot) {
		this.matchOrNot = matchOrNot;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getTelName() {
		return telName;
	}

	public void setTelName(String telName) {
		this.telName = telName;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Integer getTelId() {
		return telId;
	}

	public void setTelId(Integer telId) {
		this.telId = telId;
	}
}
