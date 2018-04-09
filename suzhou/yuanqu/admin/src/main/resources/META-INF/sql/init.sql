

insert into trans_user (user_id,password,user_name,view_name,type,status,create_at) values
  ('0','c4ca4238a0b923820dcc509a6f75849b','admin','系统管理员',0,0,(select sysdate from dual));



insert into trans_material (MATERIAL_ID, MATERIAL_CODE, MATERIAL_NAME, MATERIAL_TYPE, REGION_CODE)
values ('1', 'GYGPCRJMSQS', '挂牌出让竞买申请书', 'GROUP', '320503001');

insert into trans_material (MATERIAL_ID, MATERIAL_CODE, MATERIAL_NAME, MATERIAL_TYPE, REGION_CODE)
values ('2', 'GYSQRDYXSFZM', '申请人的有效身份证明（详情见须知）', 'GROUP', '320503001');

insert into trans_material (MATERIAL_ID, MATERIAL_CODE, MATERIAL_NAME, MATERIAL_TYPE, REGION_CODE)
values ('3', 'GYSQWTSJSWTRDYXSFZM', '授权委托书及受托人的有效身份证明', 'GROUP', '320503001');

insert into trans_material (MATERIAL_ID, MATERIAL_CODE, MATERIAL_NAME, MATERIAL_TYPE, REGION_CODE)
values ('4', 'GYJFBMDXMSSSMYJHWJ', '经发部门的项目审核书面意见或备案文件', 'GROUP', '320503001');

insert into trans_material (MATERIAL_ID, MATERIAL_CODE, MATERIAL_NAME, MATERIAL_TYPE, REGION_CODE)
values ('5', 'GYDSTGCLZSXDSM', '对所提供材料真实性的申明', 'GROUP', '320503001');

insert into trans_material (MATERIAL_ID, MATERIAL_CODE, MATERIAL_NAME, MATERIAL_TYPE, REGION_CODE)
values ('6', 'GYQTGRP', '竞买须知要求的其他相关材料', 'GROUP', '320503001');


insert into trans_material (MATERIAL_ID, MATERIAL_CODE, MATERIAL_NAME, MATERIAL_TYPE, REGION_CODE)
values ('8', 'GPCRJMSQS', '挂牌出让竞买申请书', 'PERSONAL', '320503001');

insert into trans_material (MATERIAL_ID, MATERIAL_CODE, MATERIAL_NAME, MATERIAL_TYPE, REGION_CODE)
values ('9', 'SQRDYXSFZM', '申请人的有效身份证明（详情见须知）', 'PERSONAL', '320503001');

insert into trans_material (MATERIAL_ID, MATERIAL_CODE, MATERIAL_NAME, MATERIAL_TYPE, REGION_CODE)
values ('10', 'SQWTSJSWTRDYXSFZM', '授权委托书及受托人的有效身份证明', 'PERSONAL', '320503001');

insert into trans_material (MATERIAL_ID, MATERIAL_CODE, MATERIAL_NAME, MATERIAL_TYPE, REGION_CODE)
values ('11', 'JFBMDXMSSSMYJHWJ', '经发部门的项目审核书面意见或备案文件', 'PERSONAL', '320503001');

insert into trans_material (MATERIAL_ID, MATERIAL_CODE, MATERIAL_NAME, MATERIAL_TYPE, REGION_CODE)
values ('12', 'DSTGCLZSXDSM', '对所提供材料真实性的申明', 'PERSONAL', '320503001');

insert into trans_material (MATERIAL_ID, MATERIAL_CODE, MATERIAL_NAME, MATERIAL_TYPE, REGION_CODE)
values ('13', 'QTGRP', '竞买须知要求的其他相关材料', 'PERSONAL', '320503001');




insert into trans_material (MATERIAL_ID, MATERIAL_CODE, MATERIAL_NAME, MATERIAL_TYPE, REGION_CODE)
values ('18', 'GYJMSQS', '竞买申请书', 'GROUP', '320503002');

insert into trans_material (MATERIAL_ID, MATERIAL_CODE, MATERIAL_NAME, MATERIAL_TYPE, REGION_CODE)
values ('19', 'GYYYZZFB', '竞买人（非自然人）营业执照副本（境外申请人需要提供大使馆、领事馆认证的身份证明；港澳台需提供经认证的身份证明）', 'GROUP', '320503002');

insert into trans_material (MATERIAL_ID, MATERIAL_CODE, MATERIAL_NAME, MATERIAL_TYPE, REGION_CODE)
values ('20', 'GYFDDBRSFZMS', '法定代表人身份证明书', 'GROUP', '320503002');

insert into trans_material (MATERIAL_ID, MATERIAL_CODE, MATERIAL_NAME, MATERIAL_TYPE, REGION_CODE)
values ('21', 'GYFDDBRSFZJ', '法定代表人身份证件', 'GROUP', '320503002');

insert into trans_material (MATERIAL_ID, MATERIAL_CODE, MATERIAL_NAME, MATERIAL_TYPE, REGION_CODE)
values ('22', 'GYFDDBRSQS', '法定代表人授权书', 'GROUP', '320503002');

insert into trans_material (MATERIAL_ID, MATERIAL_CODE, MATERIAL_NAME, MATERIAL_TYPE, REGION_CODE)
values ('23', 'GYSTRSFZJ', '受托人身份证件', 'GROUP', '320503002');

insert into trans_material (MATERIAL_ID, MATERIAL_CODE, MATERIAL_NAME, MATERIAL_TYPE, REGION_CODE)
values ('24', 'GYLHJMXYS', '联合竞买协议书（联合竞买提供）', 'GROUP', '320503002');

insert into trans_material (MATERIAL_ID, MATERIAL_CODE, MATERIAL_NAME, MATERIAL_TYPE, REGION_CODE)
values ('25', 'QTGRP', '竞买须知要求的其他材料（有关资质证明扫描一起上传）', 'GROUP', '320503002');

insert into trans_material (MATERIAL_ID, MATERIAL_CODE, MATERIAL_NAME, MATERIAL_TYPE, REGION_CODE)
values ('26', 'GYPZWJ', '银行总部关于在该地块设立一级分行的批准文件或承诺函', 'GROUP', '320503002');

insert into trans_material (MATERIAL_ID, MATERIAL_CODE, MATERIAL_NAME, MATERIAL_TYPE, REGION_CODE)
values ('27', 'GYSQWJ', '在苏州工业园区设立或者迁建汽车4S品牌店的授权文件', 'GROUP', '320503002');


insert into trans_material (MATERIAL_ID, MATERIAL_CODE, MATERIAL_NAME, MATERIAL_TYPE, REGION_CODE)
values ('28', 'JMSQS', '竞买申请书', 'PERSONAL', '320503002');

insert into trans_material (MATERIAL_ID, MATERIAL_CODE, MATERIAL_NAME, MATERIAL_TYPE, REGION_CODE)
values ('29', 'JMRSFZJ', '竞买人（自然人）身份证件（境外申请人需要提供大使馆、领事馆认证的身份证明；港澳台需提供经认证的身份证明）', 'PERSONAL', '320503002');

insert into trans_material (MATERIAL_ID, MATERIAL_CODE, MATERIAL_NAME, MATERIAL_TYPE, REGION_CODE)
values ('30', 'STRSFZJ', '受托人身份证件', 'PERSONAL', '320503002');

insert into trans_material (MATERIAL_ID, MATERIAL_CODE, MATERIAL_NAME, MATERIAL_TYPE, REGION_CODE)
values ('31', 'LHJMXYS', '联合竞买协议书（联合竞买提供）', 'PERSONAL', '320503002');

insert into trans_material (MATERIAL_ID, MATERIAL_CODE, MATERIAL_NAME, MATERIAL_TYPE, REGION_CODE)
values ('32', 'PZWJ', '银行总部关于在该地块设立一级分行的批准文件或承诺函', 'PERSONAL', '320503002');

insert into trans_material (MATERIAL_ID, MATERIAL_CODE, MATERIAL_NAME, MATERIAL_TYPE, REGION_CODE)
values ('33', '4SSQWJ', '在苏州工业园区设立或者迁建汽车4S品牌店的授权文件', 'PERSONAL', '320503002');

insert into trans_material (MATERIAL_ID, MATERIAL_CODE, MATERIAL_NAME, MATERIAL_TYPE, REGION_CODE)
values ('34', 'QTPER', '竞买须知要求的其他材料（有关资质证明扫描一起上传）', 'PERSONAL', '320503002');


create sequence TRANS_APPLY_ID
minvalue 1
maxvalue 9999
start with 1
increment by 1
cache 10
cycle;

create sequence TRANS_PUBLIC_NO
minvalue 1
nomaxvalue
start with 1
increment by 1
cache 10
nocycle;





insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('1', '05', '商服用地', '2', '', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('2', '051', '批发零售用地', '3', '05', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('3', '052', '住宿餐饮用地', '3', '05', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('4', '053', '商务金融用地', '3', '05', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('5', '054', '其他商服用地', '3', '05', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('6', '06', '工矿仓储用地', '2', '', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('7', '061', '工业用地', '3', '06', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('8', '0611', '机械', '4', '061', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('9', '0612', '电子', '4', '061', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('10', '0613', '纺织', '4', '061', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('11', '0614', '化工', '4', '061', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('12', '0615', '汽车', '4', '061', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('13', '0616', '冶金', '4', '061', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('14', '0617', '食品', '4', '061', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('15', '0618', '建材', '4', '061', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('16', '0619', '工业用地', '4', '061', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('17', '062', '采矿用地', '3', '06', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('18', '063', '仓储用地', '3', '06', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('19', '07', '住宅用地', '2', '', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('20', '071', '高档住宅用地', '3', '07', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('21', '072', '中低价位、中小套型普通商品住房用地', '3', '07', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('22', '073', '其他普通商品住房用地', '3', '07', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('23', '074', '经济适用住房用地', '3', '07', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('24', '075', '廉租住房用地', '3', '07', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('74', '077', '公租房', '3', '07', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('72', '0772', '出让公租房', '4', '077', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('73', '079', '限价商品房', '3', '07', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('26', '08', '公共管理与公共服务用地', '2', '', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('27', '081', '机关团体用地', '3', '08', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('28', '082', '新闻出版用地', '3', '08', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('29', '083', '科教用地', '3', '08', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('30', '084', '医卫慈善用地', '3', '08', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('31', '085', '文体娱乐用地', '3', '08', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('32', '086', '公共设施用地', '3', '08', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('33', '087', '公园与绿地', '3', '08', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('34', '088', '风景名胜设施用地', '3', '08', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('89', '089', '医疗卫生用地', '3', '08', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('35', '09', '特殊用地', '2', '', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('36', '091', '军事设施用地 ', '3', '09', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('37', '092', '使领馆用地 ', '3', '09', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('38', '093', '监教场所用地 ', '3', '09', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('39', '094', '宗教用地 ', '3', '09', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('40', '095', '殡葬用地', '3', '09', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('41', '10', '交通运输用地', '2', '', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('42', '101', '铁路用地', '3', '10', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('43', '102', '公路用地', '3', '10', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('44', '103', '街巷用地', '3', '10', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('46', '105', '机场用地', '3', '10', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('47', '106', '港口码头用地', '3', '10', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('48', '107', '管道运输用地', '3', '10', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('49', '11', '水域及水利设施用地', '2', '', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('52', '113', '水库水面', '3', '11', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('57', '118', '水工建筑用地', '3', '11', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('59', '12', '其他土地', '2', '', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('60', '121', '空闲地', '3', '12', '1', '1');

--规划用途自定义
-- 其他类
insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('61', '122', '其他用地', '3', '12', '1', '1');

-- 住宅类
insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('75', '076', '城镇住宅用地', '3', '07', '1', '1');
insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('76', '078', '商住混合', '3', '07', '1', '1');

-- 商服类
insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('77', '055', '商服用地', '3', '05', '1', '1');

-- 工业类
insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('78', '064', '其他工业用地', '3', '06', '1', '1');

insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('81', '06110', '工业(标准厂房)', '4', '061', '1', '1');
insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('82', '06111', '工业(高标准厂房)', '4', '061', '1', '1');
insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('83', '06112', '工业(研发)', '4', '061', '1', '1');
insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('84', '06113', '工业(文化创意)', '4', '061', '1', '1');
insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('85', '06114', '工业(总部经济)', '4', '061', '1', '1');

--公共管理类
insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('86', '089', '科教(研发)', '3', '08', '1', '1');
insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('87', '0810', '科教(文化创意)', '3', '08', '1', '1');
insert into dict_land_use_muli (ID, CODE, NAME, GRADE, PARENT, IS_VALID, TYPE)
values ('88', '0811', '科教(总部经济)', '3', '08', '1', '1');


--目前 id 是89 -医疗卫生用地
