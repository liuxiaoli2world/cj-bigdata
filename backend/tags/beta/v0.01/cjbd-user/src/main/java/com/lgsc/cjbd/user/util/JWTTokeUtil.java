package com.lgsc.cjbd.user.util;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.lgsc.cjbd.user.model.JWTUserDetails;
import com.lgsc.cjbd.user.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * 下发验证JWTToekn工具类
 * 
 * @author 罗天宇
 *
 */
@Component
public class JWTTokeUtil {

	@Value("${jwt.secret}")
	private String secret = "LGSC@-2017";// 秘钥

	private Long access_token_expiration = (long) (14 * 24 * 60 * 60);// token
																		// 过期时间
																		// 2周

	private Long refresh_token_expiration; // 刷新token备用

	private final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;// 加密方式

	private static final String CLAIM_KEY_USER_ID = "user_id";// 用户id
	private static final String CLAIM_KEY_USER_NAME = "user_name";// 用户名
	private static final String CLAIM_KEY_AUTHORITIES = "allows";// 权限（暂时没有用）
	private static final String CLAIM_KEY_ROLE_NAME = "role";// (角色)
	private static final String CLAIM_KEY_VIP_STATUS = "vip_status";// 用户状态
	private static final String CLAIM_KEY_REAL_NAME = "real_name";// 真实姓名

	private Date generateExpirationDate(long expiration) {
		return new Date(System.currentTimeMillis() + expiration * 1000); // token过期时间
	}

	/**
	 * 将用户信息存入token之中
	 * 
	 * @param user
	 *            用于安全认证的token对象，区别于用户实体。
	 * @return
	 */
	private Map<String, Object> generateClaims(JWTUserDetails user) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(CLAIM_KEY_USER_ID, user.getUserId());// 用户id
		claims.put(CLAIM_KEY_USER_NAME, user.getUsername());// 用户名
		claims.put(CLAIM_KEY_ROLE_NAME, user.getRoleName());// 角色
		claims.put(CLAIM_KEY_VIP_STATUS, user.getVipStatus());// 会员状态
		claims.put(CLAIM_KEY_REAL_NAME, user.getRealName());// 真实姓名
		return claims;
	}

	/**
	 * 生成jwtToken
	 * 
	 * @param subject
	 * @param claims
	 *            上面方法生成的map
	 * @param expiration
	 *            Token过期时间
	 * @return
	 */
	private String generateToken(String subject, Map<String, Object> claims, long expiration) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setId(UUID.randomUUID().toString())
				.setIssuedAt(new Date()) // token 下发的时间！ 重要的字段 用于判断token是否有效
				.setExpiration(generateExpirationDate(expiration)) // 设置过期时间
				.signWith(SIGNATURE_ALGORITHM, secret) // 秘钥加密
				.compact();
	}

	/**
	 * 生成token方法 对上面Token方法的封装 方便调用
	 * 
	 * @param subject
	 * @param claims
	 * @return
	 */
	private String generateAccessToken(String subject, Map<String, Object> claims) {
		return generateToken(subject, claims, access_token_expiration);
	}

	/**
	 * 生成token方法 对上面Token方法的封装 方便调用
	 * 
	 * @param userDetails
	 * @return
	 */
	public String generateAccessToken(UserDetails userDetails) {
		JWTUserDetails user = (JWTUserDetails) userDetails;
		Map<String, Object> claims = generateClaims(user);
		return generateAccessToken(user.getUsername(), claims);
	}

	/**
	 * 从Token中获取用户名
	 * 
	 * @param token
	 * @return
	 */
	public String getUsernameFromToken(String token) {
		String username;
		try {
			final Claims claims = getClaimsFromToken(token);
			username = claims.getSubject();
		} catch (Exception e) {
			username = null;
		}
		return username;
	}

	/**
	 * 从Token中过去token的创建时间
	 * 
	 * @param token
	 * @return
	 */
	public Date getCreatedDateFromToken(String token) {
		Date created;
		try {
			final Claims claims = getClaimsFromToken(token);
			created = claims.getIssuedAt();
		} catch (Exception e) {
			created = null;
		}
		return created;
	}

	/**
	 * 获取Token的过期时间
	 * 
	 * @param token
	 * @return
	 */
	public Date getExpirationDateFromToken(String token) {
		Date expiration;
		try {
			final Claims claims = getClaimsFromToken(token);
			expiration = claims.getExpiration();
		} catch (Exception e) {
			expiration = null;
		}
		return expiration;
	}

	/**
	 * 获取Token中保存的信息
	 * 
	 * @param token
	 * @return
	 */
	private Claims getClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			e.printStackTrace();
			claims = null;
		}
		return claims;
	}

	/**
	 * 从Token之中获取用户id
	 * 
	 * @param token
	 * @return
	 */
	public Long getUserIdFromToken(String token) {
		Long userId;
		try {
			final Claims claims = getClaimsFromToken(token);
			userId = Long.parseLong(String.valueOf(claims.get(CLAIM_KEY_USER_ID)));
		} catch (Exception e) {
			userId = 0l;
		}
		return userId;
	}

	/**
	 * 生成刷新Token的信息（暂时不用）
	 * 
	 * @param userDetails
	 * @return
	 */
	public String generateRefreshToken(UserDetails userDetails) {
		JWTUserDetails user = (JWTUserDetails) userDetails;
		Map<String, Object> claims = generateClaims(user);
		// 只授于更新 token 的权限
		String roleName = "refresh_token";
		claims.put(CLAIM_KEY_ROLE_NAME, roleName);
		return generateRefreshToken(user.getUsername(), claims);
	}

	private String generateRefreshToken(String subject, Map<String, Object> claims) {
		return generateToken(subject, claims, refresh_token_expiration);
	}

	public JWTUserDetails getUserFromToken(String token) {
		JWTUserDetails user;
		try {
			final Claims claims = getClaimsFromToken(token);
			long userId = getUserIdFromToken(token);
			String username = claims.getSubject();
			String roleName = (String) claims.get(CLAIM_KEY_ROLE_NAME);
			Collection<? extends GrantedAuthority> authorities = (Collection<? extends GrantedAuthority>) claims
					.get(CLAIM_KEY_AUTHORITIES);
			String vipStatus = (String) claims.get(CLAIM_KEY_VIP_STATUS);
			String realName = (String) claims.get(CLAIM_KEY_REAL_NAME);
			Date tokenCreatedAt = claims.getIssuedAt();
			user = new JWTUserDetails(userId, "password", username, roleName, vipStatus, realName, tokenCreatedAt,
					tokenCreatedAt, authorities);
		} catch (Exception e) {
			user = null;
		}
		return user;
	}

	public Boolean validateToken(String from, String token, UserDetails userDetails) {
		JWTUserDetails user = (JWTUserDetails) userDetails;
		final Long userId = getUserIdFromToken(token);
		final String username = getUsernameFromToken(token);
		final Date created = getCreatedDateFromToken(token);
		if (user != null && !"ROLE_ADMIN".equals(user.getRoleName())) {
			if (from.equals("index")) {
				Date TokenCreatedTime = user.getTokenCreatedTime();
				if (TokenCreatedTime != null) {
					if (!TokenCreatedTime.equals(created)) {
						return false;
					}
				}
			} else {
				Date bgTokenCreatedTime = user.getBgTokenCreatedTime();
				if (bgTokenCreatedTime != null) {
					if (!bgTokenCreatedTime.equals(created)) {
						return false;
					}
				}

			}
		}
		return (userId.equals(user.getUserId()) && username.equals(user.getUsername()) && !isTokenExpired(token));
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

//	public static void main(String[] args) {
//		System.out.println(new JWTTokeUtil().getExpirationDateFromToken(
//				"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsdW90aWFueXUxOTk1Iiwicm9sZSI6IlJPTEVfQURNSU4iLCJ1c2VyX2lkIjoxMDUsInVzZXJfbmFtZSI6Imx1b3RpYW55dTE5OTUiLCJ2aXBfc3RhdHVzIjoi5Lya5ZGYIiwiZXhwIjoxNTAyMjYxMjQ0LCJpYXQiOjE1MDEwNTE2NDQsImp0aSI6IjMwOTAzYzVlLWU3NTItNGQ0MS1iODBmLTE2ZWI5YTQyYzNjNiJ9.xQUdX1qgdLr6MGBvKGiwgD0HjWS4h_kCm9x2UxtLE_E"));
//	}

}
