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
 * Solr�ļ�ʹ��
 * 
 * @author Administrator
 * 
 */
public class TestShrio {
	DruidDataSource dataSource = new DruidDataSource();

	@Before
	public void initPropertiesInfo() {
		System.err.println("��ʼ��datasource");
		dataSource
				.setUrl("jdbc:mysql://127.0.0.1:3306/worke?serverTimezone=GMT&useUnicode=true&characterEncoding=UTF-8&useSSL=false");
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		// dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
	}

	/**
	 * �Զ���Realm
	 */
	@Test
	public void consumerRealmShiroImpl(){
		DefaultSecurityManager securityManager = new DefaultSecurityManager();
		SecurityUtils.setSecurityManager(securityManager);
		securityManager.setRealm(new ConsumerRealm());
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken authenticationToken = new UsernamePasswordToken("wlm","wlm");
		System.err.println("host��" + authenticationToken.getHost());
		//��ס����
		authenticationToken.setRememberMe(true);
		
		subject.login(authenticationToken);
		System.err.println("��¼״̬Ϊ��" + subject.isAuthenticated());
		System.err.println("�Ƿ��ס���룺" + subject.isRemembered());
		System.err.println("�Ƿ�ӵ��AdminȨ��" + subject.hasRole("admin"));
		
		
	}
	/**
	 * ʹ�ò�ѯ���ݵķ�ʽʵ��Ȩ�޵����� resourceĿ¼�µ�shiro.sqlΪ���ʵ��Ȩ�޵����ݿ��
	 */
	@Test
	public void jdbcRealmShiroImpl(){
		//1.��������
		DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
		//2.���ù�����
		SecurityUtils.setSecurityManager(defaultSecurityManager);
		//��ȡsubject����
		Subject subject = SecurityUtils.getSubject();
		//3.����Rolem����
		/**
		 * Դ�������Զ���Ȩ���û���ѯSQL����
		 * 
		 */
		JdbcRealm jdbcRealm = new JdbcRealm();
		jdbcRealm.setDataSource(dataSource);
		jdbcRealm.setPermissionsLookupEnabled(true);
		
		
		
		//ʹ���Զ�����û���У��   �ڱ�����Ĭ�ϵ����ݿ���Ϣ��һ�� Begin
		/**
		 * JdbcRealm Դ���ڲ���Ĭ�ϵĲ�ѯSQL
		 * ��ѯ��¼��Ϣ:protected static final String DEFAULT_AUTHENTICATION_QUERY = "select password from users where username = ?";
		 * ��ѯ��ɫ��Ϣ:protected static final String DEFAULT_USER_ROLES_QUERY = "select role_name from user_roles where username = ?";
		 * ��ѯ��ɫ��Ӧ��Ȩ����Ϣ��protected static final String DEFAULT_PERMISSIONS_QUERY = "select permission from roles_permissions where role_name = ?";
		 */
		//��ѯ��¼��Ϣ
		String authenticationSql = "select password from test_user where username = ?";
		jdbcRealm.setAuthenticationQuery(authenticationSql);
		
		//��ѯ��ɫ��Ϣ
		String userRolesSql = "select role_name from test_role where username = ?";
		jdbcRealm.setUserRolesQuery(userRolesSql);
		//��ѯ��ɫ��Ӧ��Ȩ��
		String permissionsSql = "select permission from test_roles_permissions where role_name = ?";
		jdbcRealm.setPermissionsQuery(permissionsSql);
		
		//ʹ���Զ�����û���У��   �ڱ�����Ĭ�ϵ����ݿ���Ϣ��һ�� End
		
		//4.���뼯����
		defaultSecurityManager.setRealm(jdbcRealm);
		//5.����Ҫ��¼���˻���Ϣ
		UsernamePasswordToken authenticationToken = new  UsernamePasswordToken("ll","ll");
		//��¼
		subject.login(authenticationToken);
		//6.��֤��¼�Ƿ�ɹ�
		System.err.println("��¼״̬Ϊ��" + subject.isAuthenticated());
		//7.����ɫ
//		System.err.println("�����Ľ�ɫΪ" + subject.hasRole("admin"));
		//7.1����Զ���Sql��Ӧ�Ľ�ɫ����
		System.err.println("�Զ�SQL������ɫ" + subject.hasRole("max"));
		//8.���Ȩ��
//		System.err.println("�Ƿ�ӵ��Ȩ�� add " + subject.isPermitted("addmecher"));
//		System.err.println("�Ƿ�ӵ��Ȩ�� update " + subject.isPermitted("delmecher"));
//		System.err.println("�Ƿ�ӵ��Ȩ�� del " + subject.isPermitted("editmecher"));
//		System.err.println("�Ƿ�ӵ��Ȩ�� query " + subject.isPermitted("selectmecher"));
		
		//8.1.�Լ���SQL��ѯ��ɫ
		System.err.println("�Ƿ�ӵ��Ȩ�� add " + subject.isPermitted("a1"));
		System.err.println("�Ƿ�ӵ��Ȩ�� update " + subject.isPermitted("a2"));
		System.err.println("�Ƿ�ӵ��Ȩ�� del " + subject.isPermitted("a3"));
		
	}
	/**
	 * �򵥵ĵ�¼����ʵ��
	 */
	@Test
	public void simpleShrioImpl() {
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

	/**
	 * ͨ��ini realm��ȡ��¼��Ϣ
	 */
	@Test
	public void iniRealmShiroImpl() {
		// 1.��������
		DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
		// ����Realm����
		IniRealm iniRealm = new IniRealm("classpath:shiro.ini");
		defaultSecurityManager.setRealm(iniRealm);
		// 2.��ʼ��securityUtils������
		SecurityUtils.setSecurityManager(defaultSecurityManager);
		// 3.�����û���Ϣ
		// 4.��ȡsubject
		Subject subject = SecurityUtils.getSubject();
		// 5.�����˺����� token����
		UsernamePasswordToken aToken = new UsernamePasswordToken("god", "god");
		subject.login(aToken);
		//
		System.err.println("��ǰ��¼�û��Ƿ�ӵ��adminȨ��" + subject.hasRole("admin"));
		System.err.println("��ǰ��¼�û��Ƿ�ӵ��userȨ��" + subject.hasRole("user"));
		// ����api�ж��Ƿ�ɹ�
		System.err.println("��¼״̬Ϊ��" + subject.isAuthenticated());
		// ����api �ж϶�Ӧ��Ȩ������Щ
		System.err.println("���û�ӵ�е�Ȩ���Ƿ���� A�� " + subject.isPermitted("a"));
		System.err.println("���û�ӵ�е�Ȩ���Ƿ���� D�� " + subject.isPermitted("d"));
		subject.logout();
		System.err.println("��¼״̬Ϊ��" + subject.isAuthenticated());
	}
}
