package com.arkfancy.sso.controller;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arkfancy.sso.entity.User;
import com.arkfancy.sso.service.UserService;
import com.arkfancy.sso.support.util.MD5Util;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.annotation.Login;
import com.baomidou.kisso.common.util.HttpUtil;
import com.baomidou.kisso.security.token.SSOToken;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;

/**
 * 登录控制器
 * 
 * @author arkfancy
 * @date 2019年9月11日 下午11:41:20
 */
@RestController
public class LoginController extends ApiController {

	@Autowired
	private UserService userService;

	@GetMapping("/login")
	public R<String> login(String username, String password, String returnUrl, HttpServletRequest request,
			HttpServletResponse response) {
		if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
			User user = userService.getOne(
					new QueryWrapper<User>().eq(User.USERNAME, username).eq(User.PASSWORD, MD5Util.crypt(password)));
			if (user != null) {
				// 登录成功 保存cookie
				SSOHelper.setCookie(request, response,
						SSOToken.create().setIp(request).setId(user.getId()).setIssuer(username), false);
				// 返回跳转页面链接
				return success(HttpUtil.decodeURL(returnUrl));
			}
		}
		// 登录失败 返回空链接
		return success(null);
	}

	@GetMapping("/logout")
	public R<Boolean> logout(HttpServletRequest request, HttpServletResponse response, String returnUrl)
			throws IOException {
		/* 清理当前登录状态 */
		SSOHelper.clearLogin(request, response);
		return success(true);
	}

	@GetMapping("/info")
	@Login
	public R<Map<String, Object>> info(String returnUrl, HttpServletRequest request, HttpServletResponse response) {
		SSOToken ssoToken = SSOHelper.attrToken(request);
		Map<String, Object> info = new HashMap<>();
		info.put("ip", ssoToken.getIp());
		info.put("id", ssoToken.getId());
		info.put("username", ssoToken.getIssuer());
		info.put("loginDate", LocalDateTime.ofInstant(Instant.ofEpochMilli(ssoToken.getTime()), ZoneId.systemDefault())
				.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		return success(info);
	}
}