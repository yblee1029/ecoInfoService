package com.ecoInfo.selection.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecoInfo.selection.domain.User;
import com.ecoInfo.selection.exception.UnauthorizedException;
import com.ecoInfo.selection.model.JwtDTO;
import com.ecoInfo.selection.model.UserDTO;
import com.ecoInfo.selection.repository.JwtRepository;
import com.ecoInfo.selection.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JwtServiceImpl implements JwtService {

	private static final String USER_ID_EXIST_EXCEPTION_MSG = "이미 계정이 존재합니다.";
	private static final String SIGNIN_EXCEPTION_MSG = "로그인정보가 일치하지 않습니다.";
	private static final String REFRESH_EXCEPTION_MSG = "refresh 토큰이 아닙니다.";
	
	private static final String SECRETKEY =  "ecoInfoServiceKey";
	
	private static final String REFRESH_TOKEN = "Bearer Token";
	
	@Autowired
	private JwtRepository jwtRepository;
	
	@Override
	@Transactional
	public JwtDTO signUp(UserDTO userDTO) {
		
		JwtDTO result = new JwtDTO(); 
		User member = jwtRepository.findByUserid(userDTO.getUserid());
		if(member != null){
			throw new IllegalStateException(USER_ID_EXIST_EXCEPTION_MSG);
		}	
		
		String password = userDTO.getPassword();
		String encodedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

		User user = User.builder()
				.userid(userDTO.getUserid())
				.password(encodedPassword)
				.build();
		
		jwtRepository.save(user);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userid", userDTO.getUserid());
		map.put("password", userDTO.getPassword());
		
		String token = Jwts.builder()
				 .setHeaderParam("typ", "JWT")
				 .setHeaderParam("regDate", System.currentTimeMillis())
				 .setSubject("user")
				 .claim("member", map)
//				 .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60))// 1시간만 토큰 유효
				 .signWith(SignatureAlgorithm.HS256, this.generateKey())
				 .compact();
		
		result.setToken(token);
		return result;
	}
	
	private byte[] generateKey(){
		byte[] key = null;
		try {
			key = SECRETKEY.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			if(log.isInfoEnabled()){
				e.printStackTrace();
			}else{
				log.error("Making JWT Key Error ::: {}", e.getMessage());
			}
		}
		
		return key;
	}
	
	@Override
	public JwtDTO signIn(UserDTO userDTO) {
		
		JwtDTO result = new JwtDTO(); 
		User member = jwtRepository.findByUserid(userDTO.getUserid());
		Objects.requireNonNull(member, SIGNIN_EXCEPTION_MSG);
		
		if(!BCrypt.checkpw(userDTO.getPassword(), member.getPassword())){
			throw new IllegalStateException(SIGNIN_EXCEPTION_MSG);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userid", userDTO.getUserid());
		map.put("password", userDTO.getPassword());
		
		String token = Jwts.builder()
				 .setHeaderParam("typ", "JWT")
				 .setHeaderParam("regDate", System.currentTimeMillis())
				 .setSubject("user")
				 .claim("member", map)
//				 .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60))// 1시간만 토큰 유효
				 .signWith(SignatureAlgorithm.HS256, this.generateKey())
				 .compact();
		
		result.setToken(token);		
		return result;
	}
	
	@Override
	public boolean isUsable(String token) {
		try {
			Jwts.parser()
				.setSigningKey(this.generateKey())
				.parseClaimsJws(token);
			return true;

		} catch (Exception e) {

			if (log.isInfoEnabled()) {
				e.printStackTrace();
			} else {
				log.error(e.getMessage());
			}
			throw new UnauthorizedException();

		}
	}

	@Override
	public JwtDTO refreshToken(HttpServletRequest request, JwtDTO jwtDTO) {
				 
		String refreshToken = request.getHeader("Authorization");
		if(!REFRESH_TOKEN.equals(refreshToken)) {
			throw new IllegalStateException(REFRESH_EXCEPTION_MSG);
		}
		
		JwtDTO result = new JwtDTO();
		Jws<Claims> claims = Jwts.parser()
								.setSigningKey(this.generateKey())
								.parseClaimsJws(jwtDTO.getToken());
		
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (LinkedHashMap<String, Object>) claims.getBody().get("member");
		
		String token = Jwts.builder()
				 .setHeaderParam("typ", "JWT")
				 .setHeaderParam("regDate", System.currentTimeMillis())
				 .setSubject("user")
				 .claim("member", map)
//				 .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60))// 1시간만 토큰 유효
				 .signWith(SignatureAlgorithm.HS256, this.generateKey())
				 .compact();
		
		result.setToken(token);		
		return result;

	}
}
