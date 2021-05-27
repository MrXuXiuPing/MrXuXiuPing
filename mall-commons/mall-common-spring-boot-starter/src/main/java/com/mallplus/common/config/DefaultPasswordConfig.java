package com.mallplus.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/** 
* @author mall
* 密码工具类
*/
public class DefaultPasswordConfig {
	/**
	 * 装配BCryptPasswordEncoder用户密码的匹配
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder()	{
		return new BCryptPasswordEncoder();
	}

//	public static void main(String[] args) {
//		BCryptPasswordEncoder bb = new BCryptPasswordEncoder();
//		String encode = bb.encode("123456");
//		System.out.println(encode);
//	}
}
