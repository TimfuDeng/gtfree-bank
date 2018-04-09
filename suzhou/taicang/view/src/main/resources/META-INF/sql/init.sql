

insert into trans_user (user_id,password,user_name,view_name,type,status,create_at) values
  ('0','c4ca4238a0b923820dcc509a6f75849b','admin','系统管理员',0,0,(select sysdate from dual));

create sequence TRANS_APPLY_ID
minvalue 1
maxvalue 9999
start with 1
increment by 1
cache 10
cycle;