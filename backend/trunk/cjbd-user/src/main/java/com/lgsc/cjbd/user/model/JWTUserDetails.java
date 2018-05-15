package com.lgsc.cjbd.user.model;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用于安全验证的用户对象
 * @author 罗天宇
 *
 */
public class JWTUserDetails implements UserDetails{
  
	private static final long serialVersionUID = 8014848488070083879L;
	private final Long userId;
	private final String roleName;
    private String password;
    private final String username;
    private final String vipStatus;
    private final String realName;
    private final Date tokenCreatedTime;
    private final Date bgTokenCreatedTime; //后台token的下发时间
    private final Collection<? extends GrantedAuthority> authorities;

    
    
	public JWTUserDetails(Long userId, String password, String username,String roleName,String vipStatus,String realName,Date tokenCreatedTime,Date bgTokenCreatedTime,
			Collection<? extends GrantedAuthority> authorities) {
		this.roleName = roleName;
		this.userId = userId;
		this.password = password;
		this.username = username;
		this.vipStatus = vipStatus;
		this.realName = realName;
		this.tokenCreatedTime = tokenCreatedTime;
		this.bgTokenCreatedTime = bgTokenCreatedTime;
		this.authorities = authorities;
		
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return authorities;
	}

	@Override
	public String getPassword() {
		
		return password;
	}

	@Override
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

	public Long getUserId() {
		return userId;
	}

	public String getRoleName() {
		return roleName;
	}

	public String getVipStatus() {
		return vipStatus;
	}

	public String getRealName() {
		return realName;
	}

	public Date getTokenCreatedTime() {
		return tokenCreatedTime;
	}

	public Date getBgTokenCreatedTime() {
		return bgTokenCreatedTime;
	}
	
 
}
