package com.ecoInfo.selection.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ecoInfo.selection.exception.UnauthorizedException;
import com.ecoInfo.selection.service.JwtService;

@Component
public class JwtInterceptor implements HandlerInterceptor {

	@Autowired
	private JwtService jwtService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		final String token = request.getHeader("Authorization");

		if (token != null && jwtService.isUsable(token)) {
			return true;
		} else {
			throw new UnauthorizedException();
		}

	}
}
