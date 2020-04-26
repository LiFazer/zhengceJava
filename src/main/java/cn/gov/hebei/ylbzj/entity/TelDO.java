package cn.gov.hebei.ylbzj.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 问题和电话关联表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-02-12 12:07:10
 */
public class TelDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键自增
	private Integer id;
	//问题id
	private Integer questionId;
	//电话id
	private Integer telId;
	//添加时间
	private Date insertTime;
	//修改时间
	private Date updateTime;

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
	 * 设置：问题id
	 */
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}
	/**
	 * 获取：问题id
	 */
	public Integer getQuestionId() {
		return questionId;
	}
	/**
	 * 设置：电话id
	 */
	public void setTelId(Integer telId) {
		this.telId = telId;
	}
	/**
	 * 获取：电话id
	 */
	public Integer getTelId() {
		return telId;
	}
	/**
	 * 设置：添加时间
	 */
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	/**
	 * 获取：添加时间
	 */
	public Date getInsertTime() {
		return insertTime;
	}
	/**
	 * 设置：修改时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：修改时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
}
