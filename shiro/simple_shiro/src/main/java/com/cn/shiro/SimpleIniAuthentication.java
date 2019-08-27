package com.cn.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
/**
 * ��ʵ�ֵ�¼��֤ ����ȡ���ݿ� ����ȡ ini�ļ�
 * @author Administrator
 *
 */
public class SimpleIniAuthentication {
	public static void main(String[] args) {
		// 1.��������
		DefaultSecurityManager securityManager = new DefaultSecurityManager();
		// 2.�����û���Ϣ
		SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();
		simpleAccountRealm.addAccount("god", "god");
		securityManager.setRealm(simpleAccountRealm);
		// ��ʼ��SecurityUtils ������
		SecurityUtils.setSecurityManager(securityManager);
		// 3.ͨ���������ȡsubject
		Subject subject = SecurityUtils.getSubject();
		// ����token����
		AuthenticationToken authenticationToken = new UsernamePasswordToken(
				"god", "god");
		// 4.��¼
		subject.login(authenticationToken);
		// 5.�ж��Ƿ��¼�ɹ�
		System.err.println("��ǰ��¼��ʶ ��" + subject.isAuthenticated());
		// 6.�˳�
		subject.logout();
		// �鿴��Ӧ��ʶ
		System.err.println("��ǰ��¼��ʶ ��" + subject.isAuthenticated());
	}
}
