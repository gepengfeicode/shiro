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
 * �Զ���Realm
 * @author Administrator
 *
 */
public class ConsumerRealm extends AuthorizingRealm {
	/**
	 * �û���Ϣ����
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
//		System.err.println("��ɫ����Ϊ�� " + rolemName);
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		//��ȡ�û�����
		String userName = (String) token.getPrincipal();
		Object userPassword = token.getCredentials();
		System.err.println("������û�����["+userName+"],����:["+userPassword+"]");
		if(null == userName || "".equals(userName)){
			throw new RuntimeException("������û���������Ϊ��");
		}
		//ģ�����ݿ��ѯ
		String password = userInfos.get(userName);
		System.err.println("�û���:["+userName+"] ��Ӧ�����ݿ�����Ϊ��["+password+"]");
		//ʵ��������֤
		/**
		 * ���췽�������ֱ�Ϊ
		 *  principal 	  �û���
		 *  credentials  ����
		 *  realmName    ��ɫ����
		 */
		return new SimpleAuthenticationInfo(userName,password,"admin");
	}

}
