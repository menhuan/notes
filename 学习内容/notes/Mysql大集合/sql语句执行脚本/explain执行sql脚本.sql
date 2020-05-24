CREATE TABLE `users` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `name` varchar(20) NOT NULL DEFAULT '' COMMENT '用户名',
    `sex` tinyint(4) NOT NULL DEFAULT '1' COMMENT '性别',
    `phone` varchar(11) NOT NULL COMMENT '手机号',
    `desc` varchar(200) NOT NULL DEFAULT '' COMMENT '介绍',
    primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '用户表';

CREATE TABLE `order`(
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `phone` varchar(11) NOT NULL COMMENT '手机号',
    `name` varchar(20) NOT NULL COMMENT '用户名',
    primary key (`id`)
) ENGINE =InnoDB default CHARSET=utf8 COMMENT '订单';

create procedure insert_user_data()

begin

declare i int ;
declare name varchar(20);
declare phone_num varchar(11);

set @SURNAME = '王李张刘陈杨黄赵吴周徐孙马朱胡郭何高林罗郑梁谢宋唐位许韩冯邓曹彭曾萧田董潘袁于蒋蔡余杜叶程苏魏吕丁任沈姚卢姜崔钟谭陆汪范金石廖贾夏韦傅方白邹孟熊秦邱江尹薛阎段雷侯龙史陶黎贺顾毛郝龚邵万钱严覃武戴莫孔向汤';
set @NAME = '丹举义之乐书乾云亦从代以伟佑俊修健傲儿元光兰冬冰冷凌凝凡凯初力勤千卉半华南博又友同向君听和哲嘉国坚城夏夜天奇奥如妙子存季孤宇安宛宸寒寻尔尧山岚峻巧平幼康建开弘强彤彦彬彭心忆志念怀怜恨惜慕成擎敏文新旋旭昊明易昕映春昱晋晓晗晟景晴智曼朋朗杰松枫柏柔柳格桃梦楷槐正水沛波泽洁洋济浦浩海涛润涵渊源溥濮瀚灵灿炎烟烨然煊煜熙熠玉珊珍理琪琴瑜瑞瑶瑾璞痴皓盼真睿碧磊祥祺秉程立竹笑紫绍经绿群翠翰致航良芙芷苍苑若茂荣莲菡菱萱蓉蓝蕊蕾薇蝶觅访诚语谷豪赋超越轩辉达远邃醉金鑫锦问雁雅雨雪霖霜露青靖静风飞香驰骞高鸿鹏鹤黎';

set i =1;

while  i < 100000 do
    SET phone_num = concat('1',
                    substring(cast(3 + (rand() * 10) % 7 AS char(50)), 1, 1),
                    right(left(trim(cast(rand() AS char(50))), 11), 9));

    set name = concat(substr(@surname,floor(rand()*length(@surname)/3+1),1), substr(@NAME,floor(rand()*length(@NAME)/3+1),1), substr(@NAME,floor(rand()*length(@NAME)/3+1),1));
    insert into users(create_time,name, sex,phone ,`desc`) values(now(),name,rand()*1,phone_num ,'test');
    insert into `order`(phone,name) values(phone_num,name);

    set i=i+1;
end while;

end

call insert_user_data();
drop  procedure if exists insert_user_data ;
select  count(*) from users;
delete from users where id >=1;
select * from users;

explain  select * from users where phone in ('14534491349','16750267018') and name = '罗力友';

explain  select * from users where id =105662  ;

explain select * from users
left join `order` o on users.phone = o.phone
where users.id =105662 ;

explain select * from users where phone=(select phone from `order` where id = 10);

explain select * from users where id = 10
union
select * from users where id = 20;

explain select * from users where phone=(select phone from `order` where id = users.id ) and id =10;

explain select * from users left join `order` o on users.phone = o.phone where users.id =10；

explain select * from users;

explain select phone from users where id > 10 and id < 20 ;

explain select * from users as t
left join `order` o on t.phone = o.phone
where t.id > 10 and t.id <20;

ALTER TABLE order add index PHONE(`phone`);

create INDEX test on `order`(`phone`);

explain select * from `order` where phone ='16485461071';

explain select * from users, `order` where users.id=`order`.id;

explain select * from `order` where id =10;

explain select * from users,`order` where users.name=`order`.name



