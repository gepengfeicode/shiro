package com.cn.shiro.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 自定义Realm
 * @author Administrator
 *
 */
public class ConsumerRealm extends AuthorizingRealm {
	/**
	 * 用户信息集合
	 */
	Map<String,String> userInfos = new HashMap<String, String>();
	{
		userInfos.put("wqq", "wqq");
		userInfos.put("wlm", "wlm");
	}
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		
//		String rolemName = (String) principals.getPrimaryPrincipal();
//		
//		System.err.println("角色名称为： " + rolemName);
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		//获取用户名称
		String userName = (String) token.getPrincipal();
		Object userPassword = token.getCredentials();
		System.err.println("传入的用户名：["+userName+"],密码:["+userPassword+"]");
		if(null == userName || "".equals(userName)){
			throw new RuntimeException("传入的用户名不允许为空");
		}
		//模拟数据库查询
		String password = userInfos.get(userName);
		System.err.println("用户名:["+userName+"] 对应的数据库密码为：["+password+"]");
		//实现密码验证
		/**
		 * 构造方法参数分别为
		 *  principal 	  用户名
		 *  credentials  密码
		 *  realmName    角色名称
		 */
		return new SimpleAuthenticationInfo(userName,password,"admin");
	}

}
