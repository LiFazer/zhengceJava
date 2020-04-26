package cn.gov.hebei.ylbzj.entity;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;



/**
 * 联络方式表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-02-12 16:35:55
 */
public class TelephoneDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键自增
	private Integer id;
	//科室名称
	private String depName;
	//手机电话
	private String phone;
	//座机电话
	private String tel;
	//添加时间
	private Date insertTime;
	//修改时间
	private Date updateTime;
	//夏季上午开始时间
	private Time summerAmStart;
	//夏季上午结束时间
	private Time summerAmEnd;
	//夏季下午开始时间
	private Time summerPmStart;
	//夏季下午结束时间
	private Time summerPmEnd;
	//冬季上午开始时间
	private Time winterAmStart;
	//冬季上午结束时间
	private Time winterAmEnd;
	//冬季下午开始时间
	private Time winterPmStart;
	//冬季下午结束时间
	private Time winterPmEnd;


	//夏季上午开始时间
	private String sas ;
	//夏季上午结束时间
	private String sae;
	//夏季下午开始时间
	private String sps;
	//夏季下午结束时间
	private String spe;
	//冬季上午开始时间
	private String was;
	//冬季上午结束时间
	private String wae;
	//冬季下午开始时间
	private String wps;
	//冬季下午结束时间
	private String wpe;

	public String getSas() {
		return sas;
	}

	public void setSas(String sas) {
		this.sas = sas;
	}

	public String getSae() {
		return sae;
	}

	public void setSae(String sae) {
		this.sae = sae;
	}

	public String getSps() {
		return sps;
	}

	public void setSps(String sps) {
		this.sps = sps;
	}

	public String getSpe() {
		return spe;
	}

	public void setSpe(String spe) {
		this.spe = spe;
	}

	public String getWas() {
		return was;
	}

	public void setWas(String was) {
		this.was = was;
	}

	public String getWae() {
		return wae;
	}

	public void setWae(String wae) {
		this.wae = wae;
	}

	public String getWps() {
		return wps;
	}

	public void setWps(String wps) {
		this.wps = wps;
	}

	public String getWpe() {
		return wpe;
	}

	public void setWpe(String wpe) {
		this.wpe = wpe;
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
	 * 设置：科室名称
	 */
	public void setDepName(String depName) {
		this.depName = depName;
	}
	/**
	 * 获取：科室名称
	 */
	public String getDepName() {
		return depName;
	}
	/**
	 * 设置：手机电话
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 获取：手机电话
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * 设置：座机电话
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}
	/**
	 * 获取：座机电话
	 */
	public String getTel() {
		return tel;
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
	/**
	 * 设置：夏季上午开始时间
	 */
	public void setSummerAmStart(Time summerAmStart) {
		this.summerAmStart = summerAmStart;
	}
	/**
	 * 获取：夏季上午开始时间
	 */
	public Time getSummerAmStart() {
		return summerAmStart;
	}
	/**
	 * 设置：夏季上午结束时间
	 */
	public void setSummerAmEnd(Time summerAmEnd) {
		this.summerAmEnd = summerAmEnd;
	}
	/**
	 * 获取：夏季上午结束时间
	 */
	public Time getSummerAmEnd() {
		return summerAmEnd;
	}
	/**
	 * 设置：夏季下午开始时间
	 */
	public void setSummerPmStart(Time summerPmStart) {
		this.summerPmStart = summerPmStart;
	}
	/**
	 * 获取：夏季下午开始时间
	 */
	public Time getSummerPmStart() {
		return summerPmStart;
	}
	/**
	 * 设置：夏季下午结束时间
	 */
	public void setSummerPmEnd(Time summerPmEnd) {
		this.summerPmEnd = summerPmEnd;
	}
	/**
	 * 获取：夏季下午结束时间
	 */
	public Time getSummerPmEnd() {
		return summerPmEnd;
	}
	/**
	 * 设置：冬季上午开始时间
	 */
	public void setWinterAmStart(Time winterAmStart) {
		this.winterAmStart = winterAmStart;
	}
	/**
	 * 获取：冬季上午开始时间
	 */
	public Time getWinterAmStart() {
		return winterAmStart;
	}
	/**
	 * 设置：冬季上午结束时间
	 */
	public void setWinterAmEnd(Time winterAmEnd) {
		this.winterAmEnd = winterAmEnd;
	}
	/**
	 * 获取：冬季上午结束时间
	 */
	public Time getWinterAmEnd() {
		return winterAmEnd;
	}
	/**
	 * 设置：冬季下午开始时间
	 */
	public void setWinterPmStart(Time winterPmStart) {
		this.winterPmStart = winterPmStart;
	}
	/**
	 * 获取：冬季下午开始时间
	 */
	public Time getWinterPmStart() {
		return winterPmStart;
	}
	/**
	 * 设置：冬季下午结束时间
	 */
	public void setWinterPmEnd(Time winterPmEnd) {
		this.winterPmEnd = winterPmEnd;
	}
	/**
	 * 获取：冬季下午结束时间
	 */
	public Time getWinterPmEnd() {
		return winterPmEnd;
	}
}
