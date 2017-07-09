-- tb_company_info
delete from tb_company_info where id in (1,2);
INSERT INTO tb_company_info (id,company_name) VALUES (1, '深圳市较真技术有限公司');
INSERT INTO tb_company_info (id,company_name)  VALUES (2, '华为技术有限公司');

-- tb_linkman_info
delete from tb_linkman_info where id in (1,2,3,4,5);
INSERT INTO tb_linkman_info (id,present_company,chin_name,fuction_dep,position,per_phone,per_mail) VALUES (1, '深圳市较真技术有限公司', '李则意', '技术部', '技术经理','13008862251', 'terry@jiaozhentech.com');
INSERT INTO tb_linkman_info (id,present_company,chin_name,fuction_dep,position,per_phone,per_mail) VALUES (2, '深圳市较真技术有限公司', '尧志强', '技术部', '移动端前端工程师','15949598602', 'george@jiaozhentech.com');
INSERT INTO tb_linkman_info (id,present_company,chin_name,fuction_dep,position,per_phone,per_mail) VALUES (3, '深圳市较真技术有限公司', '何仕敬', '技术部', '移动端前端工程师','15013735996', 'tyler@jiaozhentech.com');
INSERT INTO tb_linkman_info (id,present_company,chin_name,fuction_dep,position,per_phone,per_mail) VALUES (4, '华为技术有限公司', '黄宗强', '技术部', 'JAVA工程师','15220228290', 'dotter@jiaozhentech.com');
INSERT INTO tb_linkman_info (id,present_company,chin_name,fuction_dep,position,per_phone,per_mail) VALUES (5, '深圳市较真技术有限公司', '盛闯', '技术部', '前端工程师','13972333765', 'sean@jiaozhentech.com');

-- tb_company_linkman_info
delete from tb_company_linkman_info where id in (1,2,3,4,5);
INSERT INTO `tb_company_linkman_info` (id,company_id,link_man_id,has_office,has_certification,has_activation,fuction_dep,position) VALUES ('1', '1', '1', '1', '1', '1', '技术部', '技术经理');
INSERT INTO `tb_company_linkman_info` (id,company_id,link_man_id,has_office,has_certification,has_activation,fuction_dep,position) VALUES ('2', '1', '2', '1', '1', '0', '技术部', '移动端前端工程师');
INSERT INTO `tb_company_linkman_info` (id,company_id,link_man_id,has_office,has_certification,has_activation,fuction_dep,position) VALUES ('3', '1', '3', '1', '0', '1', '技术部', '移动端前端工程师');
INSERT INTO `tb_company_linkman_info` (id,company_id,link_man_id,has_office,has_certification,has_activation,fuction_dep,position) VALUES ('4', '2', '4', '1', '0', '0', '技术部', 'JAVA工程师');
INSERT INTO `tb_company_linkman_info` (id,company_id,link_man_id,has_office,has_certification,has_activation,fuction_dep,position) VALUES ('5', '1', '5', '1', '1', '1', '技术部', '前端工程师');

-- tb_linkman_label_info
delete from tb_linkman_label_info where id in (1,2,3,4,5);
INSERT INTO `tb_linkman_label_info` (id,link_man_id,label_id) VALUES ('1', '1', '8');
INSERT INTO `tb_linkman_label_info` (id,link_man_id,label_id) VALUES ('2', '2', '8');
INSERT INTO `tb_linkman_label_info` (id,link_man_id,label_id) VALUES ('3', '3', '8');
INSERT INTO `tb_linkman_label_info` (id,link_man_id,label_id) VALUES ('4', '4', '8');
INSERT INTO `tb_linkman_label_info` (id,link_man_id,label_id) VALUES ('5', '5', '8');

-- bh_user_info
delete from tb_linkman_label_info where id in (14,15,13,24,28);
INSERT INTO `tb_linkman_label_info` (id,link_man_id,present_company,user_name,fuction_dep,position,mobile,email,has_reg,verify_status) VALUES ('15', '1','深圳市较真技术有限公司', '李则意', '技术部', '技术经理','13008862251', 'terry@jiaozhentech.com', '1','1');
INSERT INTO `tb_linkman_label_info` (id,link_man_id,present_company,user_name,fuction_dep,position,mobile,email,has_reg,verify_status) VALUES ('14', '2','深圳市较真技术有限公司', '尧志强', '技术部', '移动端前端工程师','15949598602', 'george@jiaozhentech.com' , '1','1');
INSERT INTO `tb_linkman_label_info` (id,link_man_id,present_company,user_name,fuction_dep,position,mobile,email,has_reg,verify_status) VALUES ('24', '3','深圳市较真技术有限公司', '何仕敬', '技术部', '移动端前端工程师','15013735996', 'tyler@jiaozhentech.com', '1','1');
INSERT INTO `tb_linkman_label_info` (id,link_man_id,present_company,user_name,fuction_dep,position,mobile,email,has_reg,verify_status) VALUES ('13', '4','华为技术有限公司', '黄宗强', '技术部', 'JAVA工程师','15220228290', 'dotter@jiaozhentech.com' , '1','1');
INSERT INTO `tb_linkman_label_info` (id,link_man_id,present_company,user_name,fuction_dep,position,mobile,email,has_reg,verify_status) VALUES ('28', '5','深圳市较真技术有限公司', '盛闯', '技术部', '前端工程师','13972333765', 'sean@jiaozhentech.com' , '1','1');