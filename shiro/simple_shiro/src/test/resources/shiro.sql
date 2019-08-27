##创建用户表
create table users(
username varchar(30) not null comment '用户名',
password varchar(30) not null comment '密码'
);

##插入用户数据
insert into users values ('god','god');
insert into users values ('www','www');

##创建角色表
create table user_roles
(
username varchar(30) comment '用户名',
role_name varchar(30) comment '角色名称'
);
##增加数据
insert into user_roles values ('god','admin');
insert into user_roles values ('www','user');

##创建权限表
create table roles_permissions
(
role_name varchar(30) comment '角色名称', 
permission varchar(30) comment '权限名称'
);

insert into roles_permissions values ('admin','addmecher');
insert into roles_permissions values ('admin','delmecher');
insert into roles_permissions values ('admin','editmecher');
insert into roles_permissions values ('admin','selectmecher');

insert into roles_permissions values ('user','selectmecher');

##创建自定义SQL的用户表
create table test_user(
username varchar(20) comment '用户名',
password varchar(20) comment '密码'
);
##插入用户表
insert into test_user values ('ll','ll');
##创建角色表
create table test_role(
username varchar(30) comment '用户名',
role_name varchar(30) comment '角色名称'
);
#插入角色信息
insert into test_role values ('ll','max');
#创建角色与权限表
create table test_roles_permissions(
role_name varchar(30) comment '角色名称', 
permission varchar(30) comment '权限名称'
);

insert into test_roles_permissions values('max','a1');
insert into test_roles_permissions values('max','a2');
insert into test_roles_permissions values('max','a3');



