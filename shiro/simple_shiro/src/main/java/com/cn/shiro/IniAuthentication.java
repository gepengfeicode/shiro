package com.cn.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

/**
 * 通过读取Ini文件完成用户信息的初始化
 * 
 * @author Administrator
 * 
 */
public class IniAuthentication {
	public static void main(String[] args) {
		// 1.创建工厂对象 读取配置文件
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
		// 2.获取SecurityManager的示例
		SecurityManager securityManager = factory.getInstance();
		// 3.将SecurityManager工厂获取到的SecurityManager实例防止到运行环境中
		SecurityUtils.setSecurityManager(securityManager);
		// 4.通过SecurityUtils获取subject 主体
		Subject subject = SecurityUtils.getSubject();
		// 5.创建登录对象
		UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("wqq", "wqq");
		// 6.调用subject.log方法进行认证
		subject.login(usernamePasswordToken);
		// 7.调用    isAuthenticated方法判断是否登陆成功
		System.err.println("登录是否成功" + subject.isAuthenticated());
		// 8.执行退出方法
		subject.logout();
		System.err.println("执行logout方法后,登录是否成功" + subject.isAuthenticated());
	}
}
