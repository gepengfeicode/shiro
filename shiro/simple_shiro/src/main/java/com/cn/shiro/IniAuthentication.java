package com.cn.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

/**
 * ͨ����ȡIni�ļ�����û���Ϣ�ĳ�ʼ��
 * 
 * @author Administrator
 * 
 */
public class IniAuthentication {
	public static void main(String[] args) {
		// 1.������������ ��ȡ�����ļ�
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
		// 2.��ȡSecurityManager��ʾ��
		SecurityManager securityManager = factory.getInstance();
		// 3.��SecurityManager������ȡ����SecurityManagerʵ����ֹ�����л�����
		SecurityUtils.setSecurityManager(securityManager);
		// 4.ͨ��SecurityUtils��ȡsubject ����
		Subject subject = SecurityUtils.getSubject();
		// 5.������¼����
		UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("wqq", "wqq");
		// 6.����subject.log����������֤
		subject.login(usernamePasswordToken);
		// 7.����    isAuthenticated�����ж��Ƿ��½�ɹ�
		System.err.println("��¼�Ƿ�ɹ�" + subject.isAuthenticated());
		// 8.ִ���˳�����
		subject.logout();
		System.err.println("ִ��logout������,��¼�Ƿ�ɹ�" + subject.isAuthenticated());
	}
}
