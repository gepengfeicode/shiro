package com.cn.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
/**
 * 简单实现登录验证 不读取数据库 不读取 ini文件
 * @author Administrator
 *
 */
public class SimpleIniAuthentication {
	public static void main(String[] args) {
		// 1.创建环境
		DefaultSecurityManager securityManager = new DefaultSecurityManager();
		// 2.放入用户信息
		SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();
		simpleAccountRealm.addAccount("god", "god");
		securityManager.setRealm(simpleAccountRealm);
		// 初始化SecurityUtils 工具类
		SecurityUtils.setSecurityManager(securityManager);
		// 3.通过工具类获取subject
		Subject subject = SecurityUtils.getSubject();
		// 创建token对象
		AuthenticationToken authenticationToken = new UsernamePasswordToken(
				"god", "god");
		// 4.登录
		subject.login(authenticationToken);
		// 5.判断是否登录成功
		System.err.println("当前登录标识 ：" + subject.isAuthenticated());
		// 6.退出
		subject.logout();
		// 查看响应标识
		System.err.println("当前登录标识 ：" + subject.isAuthenticated());
	}
}
