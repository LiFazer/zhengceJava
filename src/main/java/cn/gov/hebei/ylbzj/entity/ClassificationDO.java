package cn.gov.hebei.ylbzj.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 分类表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-01-13 12:42:35
 */
public class ClassificationDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键自增
	private Integer id;
	//父id
	private Integer pid;
	//分类名称
	private String classificationName;
	//更新时间
	private Date updateTime;
	//创建时间
	private Date createTime;

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
	 * 设置：父id
	 */
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	/**
	 * 获取：父id
	 */
	public Integer getPid() {
		return pid;
	}
	/**
	 * 设置：分类名称
	 */
	public void setClassificationName(String classificationName) {
		this.classificationName = classificationName;
	}
	/**
	 * 获取：分类名称
	 */
	public String getClassificationName() {
		return classificationName;
	}
	/**
	 * 设置：更新时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：更新时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
}
