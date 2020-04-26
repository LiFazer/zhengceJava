package cn.gov.hebei.ylbzj.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 小程序打开次数记录表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-02-07 09:53:39
 */
public class TimesDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键自增
	private Integer id;
	//小程序打开次数
	private Integer openTimes;
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
	 * 设置：小程序打开次数
	 */
	public void setOpenTimes(Integer openTimes) {
		this.openTimes = openTimes;
	}
	/**
	 * 获取：小程序打开次数
	 */
	public Integer getOpenTimes() {
		return openTimes;
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
