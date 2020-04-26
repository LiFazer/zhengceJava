package cn.gov.hebei.ylbzj.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 关键词表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-02-08 11:53:12
 */
public class WordsDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键自增
	private Integer id;
	//关键词
	private String words;
	//搜索次数
	private Integer countTimes;
	//是否有反馈：0无 1有
	private Integer feedback;
	//类型：0 未拆分词，1 拆分词
	private Integer type;
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
	 * 设置：关键词
	 */
	public void setWords(String words) {
		this.words = words;
	}
	/**
	 * 获取：关键词
	 */
	public String getWords() {
		return words;
	}
	/**
	 * 设置：搜索次数
	 */
	public void setCountTimes(Integer countTimes) {
		this.countTimes = countTimes;
	}
	/**
	 * 获取：搜索次数
	 */
	public Integer getCountTimes() {
		return countTimes;
	}
	/**
	 * 设置：是否有反馈：0无 1有
	 */
	public void setFeedback(Integer feedback) {
		this.feedback = feedback;
	}
	/**
	 * 获取：是否有反馈：0无 1有
	 */
	public Integer getFeedback() {
		return feedback;
	}
	/**
	 * 设置：类型：0 未拆分词，1 拆分词
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：类型：0 未拆分词，1 拆分词
	 */
	public Integer getType() {
		return type;
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
