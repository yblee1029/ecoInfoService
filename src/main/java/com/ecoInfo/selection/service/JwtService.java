package com.ecoInfo.selection.service;

import javax.servlet.http.HttpServletRequest;

import com.ecoInfo.selection.model.JwtDTO;
import com.ecoInfo.selection.model.UserDTO;

public interface JwtService {

	JwtDTO signUp(UserDTO userDTO);
	
	JwtDTO signIn(UserDTO userDTO);
	
	boolean isUsable(String token);
	
	JwtDTO refreshToken(HttpServletRequest request, JwtDTO jwtDTO);
}
