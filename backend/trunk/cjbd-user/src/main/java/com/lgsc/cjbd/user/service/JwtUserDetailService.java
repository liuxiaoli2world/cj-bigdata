package com.lgsc.cjbd.user.service;




import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lgsc.cjbd.user.mapper.AllowMapper;
import com.lgsc.cjbd.user.mapper.RoleMapper;
import com.lgsc.cjbd.user.mapper.UserMapper;
import com.lgsc.cjbd.user.mapper.UserVipMapper;
import com.lgsc.cjbd.user.model.Allow;
import com.lgsc.cjbd.user.model.JWTUserDetails;
import com.lgsc.cjbd.user.model.Role;
import com.lgsc.cjbd.user.model.User;

@Service
public class JwtUserDetailService implements UserDetailsService{
    
	public Logger logger = LogManager.getLogger(JwtUserDetailService.class);
	
	@Autowired
	private  UserMapper userMapper;
	
	@Autowired
	private  RoleMapper roleMapper;
	
	@Autowired
	private AllowMapper allowMapper;
	
	@Autowired
	private  UserVipMapper userVipMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userMapper.findUserByTag(username); 
		if(user==null){
			logger.error("can not find user: " + username);
            throw new UsernameNotFoundException("can not find user.");
		}
		List<Role> roles = roleMapper.selectRoleByUserId(user.getUserId());
		UserDetails userDetails;
		String vipStatus = userVipMapper.selectVipStatus(user.getUserId());
		if(roles!=null&&roles.size()!=0){
		    List<Allow> allows = allowMapper.findByUserId(user.getUserId());
		    List<GrantedAuthority> grantedAuthorities = new ArrayList <>();
            for (Allow allow : allows) {
				if(allow!=null&&allow.getAllowName()!=null){
					GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(allow.getAllowName());
	                grantedAuthorities.add(grantedAuthority);
				}
			}
		    userDetails = new JWTUserDetails(user.getUserId(), user.getPassword(), user.getUsername(),roles.get(0).getRoleName(),vipStatus,user.getRealName(),user.getTokenCreatedAt(),user.getBgTokenCreatedAt(),grantedAuthorities);
		}else{
		    userDetails = new JWTUserDetails(user.getUserId(), user.getPassword(), user.getUsername(),null,vipStatus,user.getRealName(),user.getTokenCreatedAt(),user.getBgTokenCreatedAt(),null);// 普通用户没有角色 ，没有权限。
		}
		
		return userDetails;
	}

}
