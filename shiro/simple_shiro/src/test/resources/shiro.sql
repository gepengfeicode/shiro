##�����û���
create table users(
username varchar(30) not null comment '�û���',
password varchar(30) not null comment '����'
);

##�����û�����
insert into users values ('god','god');
insert into users values ('www','www');

##������ɫ��
create table user_roles
(
username varchar(30) comment '�û���',
role_name varchar(30) comment '��ɫ����'
);
##��������
insert into user_roles values ('god','admin');
insert into user_roles values ('www','user');

##����Ȩ�ޱ�
create table roles_permissions
(
role_name varchar(30) comment '��ɫ����', 
permission varchar(30) comment 'Ȩ������'
);

insert into roles_permissions values ('admin','addmecher');
insert into roles_permissions values ('admin','delmecher');
insert into roles_permissions values ('admin','editmecher');
insert into roles_permissions values ('admin','selectmecher');

insert into roles_permissions values ('user','selectmecher');

##�����Զ���SQL���û���
create table test_user(
username varchar(20) comment '�û���',
password varchar(20) comment '����'
);
##�����û���
insert into test_user values ('ll','ll');
##������ɫ��
create table test_role(
username varchar(30) comment '�û���',
role_name varchar(30) comment '��ɫ����'
);
#�����ɫ��Ϣ
insert into test_role values ('ll','max');
#������ɫ��Ȩ�ޱ�
create table test_roles_permissions(
role_name varchar(30) comment '��ɫ����', 
permission varchar(30) comment 'Ȩ������'
);

insert into test_roles_permissions values('max','a1');
insert into test_roles_permissions values('max','a2');
insert into test_roles_permissions values('max','a3');



