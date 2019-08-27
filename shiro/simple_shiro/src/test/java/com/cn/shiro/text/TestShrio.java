package com.cn.shiro.text;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JEditorPane;

import junit.framework.Assert;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Before;
import org.junit.Test;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.cn.shiro.utils.ConsumerRealm;

/**
 * Solr的简单使用
 * 
 * @author Administrator
 * 
 */
public class TestShrio {
	DruidDataSource dataSource = new DruidDataSource();

	@Before
	public void initPropertiesInfo() {
		System.err.println("初始化datasource");
		dataSource
				.setUrl("jdbc:mysql://127.0.0.1:3306/worke?serverTimezone=GMT&useUnicode=true&characterEncoding=UTF-8&useSSL=false");
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		// dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
	}

	/**
	 * 自定义Realm
	 */
	@Test
	public void consumerRealmShiroImpl(){
		DefaultSecurityManager securityManager = new DefaultSecurityManager();
		SecurityUtils.setSecurityManager(securityManager);
		securityManager.setRealm(new ConsumerRealm());
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken authenticationToken = new UsernamePasswordToken("wlm","wlm");
		System.err.println("host：" + authenticationToken.getHost());
		//记住密码
		authenticationToken.setRememberMe(true);
		
		subject.login(authenticationToken);
		System.err.println("登录状态为：" + subject.isAuthenticated());
		System.err.println("是否记住密码：" + subject.isRemembered());
		System.err.println("是否拥有Admin权限" + subject.hasRole("admin"));
		
		
	}
	/**
	 * 使用查询数据的方式实现权限的配置 resource目录下的shiro.sql为最简单实现权限的数据库表
	 */
	@Test
	public void jdbcRealmShiroImpl(){
		//1.创建环境
		DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
		//2.设置工具类
		SecurityUtils.setSecurityManager(defaultSecurityManager);
		//获取subject对象
		Subject subject = SecurityUtils.getSubject();
		//3.创建Rolem对象
		/**
		 * 源码中有自定的权限用户查询SQL例如
		 * 
		 */
		JdbcRealm jdbcRealm = new JdbcRealm();
		jdbcRealm.setDataSource(dataSource);
		jdbcRealm.setPermissionsLookupEnabled(true);
		
		
		
		//使用自定义的用户名校验   在表名与默认的数据库信息不一致 Begin
		/**
		 * JdbcRealm 源码内部有默认的查询SQL
		 * 查询登录信息:protected static final String DEFAULT_AUTHENTICATION_QUERY = "select password from users where username = ?";
		 * 查询角色信息:protected static final String DEFAULT_USER_ROLES_QUERY = "select role_name from user_roles where username = ?";
		 * 查询角色对应的权限信息：protected static final String DEFAULT_PERMISSIONS_QUERY = "select permission from roles_permissions where role_name = ?";
		 */
		//查询登录信息
		String authenticationSql = "select password from test_user where username = ?";
		jdbcRealm.setAuthenticationQuery(authenticationSql);
		
		//查询角色信息
		String userRolesSql = "select role_name from test_role where username = ?";
		jdbcRealm.setUserRolesQuery(userRolesSql);
		//查询角色对应的权限
		String permissionsSql = "select permission from test_roles_permissions where role_name = ?";
		jdbcRealm.setPermissionsQuery(permissionsSql);
		
		//使用自定义的用户名校验   在表名与默认的数据库信息不一致 End
		
		//4.放入集合内
		defaultSecurityManager.setRealm(jdbcRealm);
		//5.输入要登录的账户信息
		UsernamePasswordToken authenticationToken = new  UsernamePasswordToken("ll","ll");
		//登录
		subject.login(authenticationToken);
		//6.验证登录是否成功
		System.err.println("登录状态为：" + subject.isAuthenticated());
		//7.检查角色
//		System.err.println("归属的角色为" + subject.hasRole("admin"));
		//7.1检查自定的Sql对应的角色名称
		System.err.println("自定SQL归属角色" + subject.hasRole("max"));
		//8.检查权限
//		System.err.println("是否拥有权限 add " + subject.isPermitted("addmecher"));
//		System.err.println("是否拥有权限 update " + subject.isPermitted("delmecher"));
//		System.err.println("是否拥有权限 del " + subject.isPermitted("editmecher"));
//		System.err.println("是否拥有权限 query " + subject.isPermitted("selectmecher"));
		
		//8.1.自己定SQL查询角色
		System.err.println("是否拥有权限 add " + subject.isPermitted("a1"));
		System.err.println("是否拥有权限 update " + subject.isPermitted("a2"));
		System.err.println("是否拥有权限 del " + subject.isPermitted("a3"));
		
	}
	/**
	 * 简单的登录功能实现
	 */
	@Test
	public void simpleShrioImpl() {
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

	/**
	 * 通过ini realm读取登录信息
	 */
	@Test
	public void iniRealmShiroImpl() {
		// 1.创建环境
		DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
		// 创建Realm对象
		IniRealm iniRealm = new IniRealm("classpath:shiro.ini");
		defaultSecurityManager.setRealm(iniRealm);
		// 2.初始化securityUtils工具类
		SecurityUtils.setSecurityManager(defaultSecurityManager);
		// 3.创建用户信息
		// 4.获取subject
		Subject subject = SecurityUtils.getSubject();
		// 5.传入账号密码 token对象
		UsernamePasswordToken aToken = new UsernamePasswordToken("god", "god");
		subject.login(aToken);
		//
		System.err.println("当前登录用户是否拥有admin权限" + subject.hasRole("admin"));
		System.err.println("当前登录用户是否拥有user权限" + subject.hasRole("user"));
		// 调用api判断是否成功
		System.err.println("登录状态为：" + subject.isAuthenticated());
		// 调用api 判断对应的权限有哪些
		System.err.println("该用户拥有的权限是否包含 A： " + subject.isPermitted("a"));
		System.err.println("该用户拥有的权限是否包含 D： " + subject.isPermitted("d"));
		subject.logout();
		System.err.println("登录状态为：" + subject.isAuthenticated());
	}
}
