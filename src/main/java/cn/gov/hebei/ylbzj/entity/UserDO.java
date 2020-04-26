package cn.gov.hebei.ylbzj.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;


/**
 * 管理员表
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2020-01-14 15:39:46
 */
public class UserDO implements Serializable, UserDetails {
	private static final long serialVersionUID = 1L;
	
	//id
	private Integer id;
	//管理员账号
	private String username;
	//管理员密码
	private String password;
	//管理员角色 1 系统管理员 
	private String role;
	//用户状态（0不可用1可用）
	private Integer state;
	//联系电话
	private String phone;
	//更新时间
	private Date updateTime;
	//创建时间
	private Date createTime;

	/**
	 * 设置：id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：管理员账号
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * 获取：管理员账号
	 */
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	/**
	 * 设置：管理员密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		return authorities;
	}

	/**
	 * 获取：管理员密码
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * 设置：管理员角色 1 系统管理员 
	 */
	public void setRole(String role) {
		this.role = role;
	}
	/**
	 * 获取：管理员角色 1 系统管理员 
	 */
	public String getRole() {
		return role;
	}
	/**
	 * 设置：用户状态（0不可用1可用）
	 */
	public void setState(Integer state) {
		this.state = state;
	}
	/**
	 * 获取：用户状态（0不可用1可用）
	 */
	public Integer getState() {
		return state;
	}
	/**
	 * 设置：联系电话
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 获取：联系电话
	 */
	public String getPhone() {
		return phone;
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
