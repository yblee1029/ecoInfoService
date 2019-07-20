package com.ecoInfo.selection.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecoInfo.selection.model.JwtDTO;
import com.ecoInfo.selection.model.UserDTO;
import com.ecoInfo.selection.service.JwtService;

/**
  * @FileName : JwtController.java
  * @Project : ecoInfoService
  * @Date : 2019. 7. 20. 
  * @작성자 : yblee
  * @변경이력 :
  * @프로그램 설명 : API 인증을 위해 JWT(Json Web Token)를 이용해서 Token 기반 API 호출 기능
  */
@RestController
@RequestMapping("/jwt")
public class JwtController {

	@Autowired
	private JwtService jwtService;

	/**
	  * @Method Name : signUp
	  * @작성일 : 2019. 7. 20.
	  * @작성자 : yblee
	  * @변경이력 : 
	  * @Method 설명 : signup 계정 생성 API
	  * @param userDTO
	  * @return
	  */
	@PostMapping("/signUp")
	public JwtDTO signUp(@RequestBody @Valid UserDTO userDTO) {
		return jwtService.signUp(userDTO);
	}

	/**
	  * @Method Name : signIn
	  * @작성일 : 2019. 7. 20.
	  * @작성자 : yblee
	  * @변경이력 : 
	  * @Method 설명 : signin 로그인 API
	  * @param userDTO
	  * @return
	  */
	@PostMapping("/signIn")
	public JwtDTO signIn(@RequestBody @Valid UserDTO userDTO) {
		return jwtService.signIn(userDTO);
	}
	
	/**
	  * @Method Name : refreshToken
	  * @작성일 : 2019. 7. 20.
	  * @작성자 : yblee
	  * @변경이력 : 
	  * @Method 설명 : refresh 토큰 재발급 API
	  * @param request
	  * @param jwtDTO
	  * @return
	  */
	@PostMapping("/refresh")
	public JwtDTO refreshToken(HttpServletRequest request, @RequestBody @Valid JwtDTO jwtDTO) {
		return jwtService.refreshToken(request, jwtDTO);
	}
}
