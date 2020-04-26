package cn.gov.hebei.ylbzj.entity;

import java.io.Serializable;


/**
 * 分类和问答中间表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-01-13 12:42:20
 */
public class ClassificationQuestionDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键自增
	private Integer id;
	//分类表id
	private Integer classificationId;
	//问答表id
	private Integer questionId;

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
	 * 设置：分类表id
	 */
	public void setClassificationId(Integer classificationId) {
		this.classificationId = classificationId;
	}
	/**
	 * 获取：分类表id
	 */
	public Integer getClassificationId() {
		return classificationId;
	}
	/**
	 * 设置：问答表id
	 */
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}
	/**
	 * 获取：问答表id
	 */
	public Integer getQuestionId() {
		return questionId;
	}
}
