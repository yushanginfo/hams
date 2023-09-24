
--删除相同住院号的过期数据
delete from tmp.brxx a where exists(select * from tmp.brxx b where a.zyh::bigint=b.zyh::bigint and a.begin_at<b.begin_at);
delete from tmp.brxx where name like '%test%';
delete from tmp.brxx where name like '%测试%';
delete from tmp.brxx where status_name like '%作废%';

--删除相同住院号的过期数据
delete from tmp.brxx a where exists(select * from tmp.brxx b where a.zyh::bigint=b.zyh::bigint and a.begin_at=b.begin_at and a.end_at<b.end_at);

alter table tmp.brxx add person_id bigint;
alter table tmp.brxx add id bigint;
alter table tmp.brxx add contact_id bigint;
alter table tmp.brxx add relation_id bigint;
alter table tmp.brxx add newer boolean;
alter table tmp.brxx add nation_id int4;
alter table tmp.brxx add status_id int4;
alter table tmp.brxx add fee_origin_id int4;
alter table tmp.brxx add marital_status_id int4;
alter table tmp.brxx add relationship_id int4;
alter table tmp.brxx add ward_id int4;

update tmp.brxx a set id=(select max(i.id) from hams.inpatients i where i.code::bigint=a.zyh::bigint);
update tmp.brxx a set person_id = (select i.person_id from hams.inpatients i where i.id=a.id);
update tmp.brxx a set contact_id = (select i.contact_id from hams.inpatients i where i.id=a.id);
update tmp.brxx a set relation_id = (select r.id from hams.relations r where r.inpatient_id=a.id and r.idx=1);

update tmp.brxx a set newer= case when id is null then true else false end;
update tmp.brxx a set nation_id=(select n.id from hams.c_nations n where n.name=a.nation_name);
update tmp.brxx a set marital_status_id=(select n.id from hams.c_marital_statuses n where n.name=a.matial_status_name);
update tmp.brxx a set relationship_id=(select n.id from hams.c_relationships n where n.name=a.relation_ship);
update tmp.brxx a set relationship_id=1 where relationship_id is null;
update tmp.brxx a set ward_id=(select n.id from hams.wards n where n.name=a.ward_name);


update tmp.brxx set person_id=datetime_id() where person_id is null;

--新增人员
insert into hams.people(id,name,idcard,id_type_id,gender_id) select person_id,name,idcard,1,case when gender_name ='男' then 1  else 2 end
 from tmp.brxx where id is null;

--更新姓名
update hams.people p set name=(select b.name from tmp.brxx b where b.person_id=p.id)
	where exists(select * from tmp.brxx b where b.person_id=p.id and b.name <> p.name);

--更新姓名拼音
update hams.people p set phonetic_name=(select b.phonetic_name from tmp.brxx b where b.person_id=p.id)
	where exists(select * from tmp.brxx b where b.person_id=p.id and coalesce(b.phonetic_name,'--') <> coalesce(p.phonetic_name,'--'));

--更新姓名五笔
update hams.people p set wubi_name=(select b.wubi_name from tmp.brxx b where b.person_id=p.id)
	where exists(select * from tmp.brxx b where b.person_id=p.id and coalesce(b.wubi_name,'--') <> coalesce(p.wubi_name,'--'));

--更新证件号码
update hams.people p set idcard=(select b.idcard from tmp.brxx b where b.person_id=p.id)
	where exists(select * from tmp.brxx b where b.person_id=p.id and coalesce(b.idcard,'--') <> coalesce(p.idcard,'--'));

--更新出生日期
update hams.people p set birthday=(select to_date(b.birthday,'yyyyMMdd') from tmp.brxx b where b.person_id=p.id)
	where exists(select * from tmp.brxx b where b.person_id=p.id and to_date(b.birthday,'yyyyMMdd') <> p.birthday);

--更新民族
update hams.people p set nation_id=(select b.nation_id from tmp.brxx b where b.person_id=p.id)
	where exists(select * from tmp.brxx b where b.person_id=p.id and b.nation_id <> coalesce(p.nation_id,0));

--更新出生地省
update hams.people p set birth_province=(select b.csd_s from tmp.brxx b where b.person_id=p.id)
	where exists(select * from tmp.brxx b where b.person_id=p.id and coalesce(b.csd_s,'--') <> coalesce(p.birth_province,'--'));

--更新出生地区
update hams.people p set birth_district=(select b.csd_q from tmp.brxx b where b.person_id=p.id)
	where exists(select * from tmp.brxx b where b.person_id=p.id and coalesce(b.csd_q,'--') <> coalesce(p.birth_district,'--'));

--更新职业
update hams.people p set profession=(select b.prefession from tmp.brxx b where b.person_id=p.id)
	where exists(select * from tmp.brxx b where b.person_id=p.id and coalesce(b.prefession,'--') <> coalesce(p.profession,'--'));

--更新籍贯
update hams.people p set home_town=(select b.home_town from tmp.brxx b where b.person_id=p.id)
	where exists(select * from tmp.brxx b where b.person_id=p.id and coalesce(b.home_town,'--') <> coalesce(p.home_town,'--'));

--更新病人姓名
update hams.inpatients i set name=(select p.name from hams.people p where i.person_id=p.id)
	where exists(select p.name from hams.people p where i.person_id=p.id and i.name <> p.name);

--更新民族
update hams.people p set nation_id=(select b.nation_id from tmp.brxx b where b.person_id=p.id)
	where exists(select * from tmp.brxx b where b.person_id=p.id and b.nation_id <> coalesce(p.nation_id,0));

--更新婚姻状况
update hams.people p set marital_status_id=(select b.marital_status_id from tmp.brxx b where b.person_id=p.id)
	where exists(select * from tmp.brxx b where b.person_id=p.id and coalesce(b.marital_status_id,0) <> coalesce(p.marital_status_id,0));

--更新新的联系人ID
update tmp.brxx set contact_id=datetime_id() where contact_id is null;

update tmp.brxx b set status_id=(select c.id from hams.c_inpatient_statuses c where c.name=b.status_name);
update tmp.brxx b set fee_origin_id=(select c.id from hams.c_fee_origins c where c.name=b.fee_origin_name);


--生成新的病人ID
update tmp.brxx set id=datetime_id() where newer =true;

--新增联系人
insert into hams.contacts(id) select contact_id from tmp.brxx where newer =true;

--插入新的病人信息
insert into hams.inpatients(id,code,name,gender_id,patient_id,ward_id,bed_no,status_id,fee_origin_id,begin_at,person_id,contact_id)
select id,zyh,name,case when gender_name ='男' then 1  else 2 end,
patid,(select w.id from hams.wards w where w.name=ward_name),bed_no,status_id,fee_origin_id,
to_timestamp(begin_at,'yyyyMMddHH:mi:ss'),person_id,contact_id from tmp.brxx
where newer =true;

--更新住院号
update hams.inpatients i set code=(select b.zyh from tmp.brxx b where b.id=i.id)
	where exists(select * from tmp.brxx b where b.id=i.id and i.code <> b.zyh);

--更新住院状态
update hams.inpatients i set status_id=(select b.status_id from tmp.brxx b where b.id=i.id)
	where exists(select * from tmp.brxx b where b.id=i.id and i.status_id <> b.status_id);

update tmp.brxx set begin_at='20'||trim(begin_at) where length(trim(begin_at))=14;

--更新入院时间
--update hams.inpatients i set begin_at=(select to_timestamp(b.begin_at,'yyyyMMddHH24:mi:ss') from tmp.brxx b where b.id=i.id)
--	where exists(select * from tmp.brxx b where b.id=i.id and to_char(i.begin_at,'yyyyMMddHH24:mi:ss') <> b.begin_at and length(b.begin_at)=16);

--更新出院时间为v->null
update hams.inpatients i set end_at =null
	where end_at is not null and not exists(select * from tmp.brxx b where b.id=i.id and b.end_at is not null and length(trim(b.end_at))>0);

--更新出院时间为null->v
update hams.inpatients i set end_at =(select to_timestamp(b.end_at,'yyyyMMddHH24:mi:ss') from tmp.brxx b where b.id=i.id)
	where end_at is null and exists(select * from tmp.brxx b where b.id=i.id and b.end_at is not null and length(trim(b.end_at))>0);

--更新出院时间v1->v2
update hams.inpatients i set end_at=(select to_timestamp(b.end_at,'yyyyMMddHH24:mi:ss') from tmp.brxx b where b.id=i.id)
	where exists(select * from tmp.brxx b where b.id=i.id and to_char(i.end_at,'yyyyMMddHH24:mi:ss') <> b.end_at and length(b.end_at)=16);

--更新卡号
update hams.inpatients i set card=(select b.card from tmp.brxx b where b.id=i.id)
	where exists(select * from tmp.brxx b where b.id=i.id and  coalesce(b.card,'--') <> coalesce(i.card,'--'));

--更新地址
update hams.contacts c set address=(select b.address from tmp.brxx b where b.contact_id=c.id)
	where exists(select * from tmp.brxx b where b.contact_id=c.id and  coalesce(b.address,'--') <> coalesce(c.address,'--'));

--更新电话
update hams.contacts c set phone=(select b.phone from tmp.brxx b where b.contact_id=c.id)
	where exists(select * from tmp.brxx b where b.contact_id=c.id and  coalesce(b.phone,'--') <> coalesce(c.phone,'--'));

--更新邮编
update hams.contacts c set postcode=(select b.postcode from tmp.brxx b where b.contact_id=c.id)
	where exists(select * from tmp.brxx b where b.contact_id=c.id and  coalesce(b.postcode,'--') <> coalesce(c.postcode,'--'));

--更新工作单位
update hams.contacts c set org_name=(select b.org_name from tmp.brxx b where b.contact_id=c.id)
	where exists(select * from tmp.brxx b where b.contact_id=c.id and  coalesce(b.org_name,'--') <> coalesce(c.org_name,'--'));

--更新工作单位邮编
update hams.contacts c set org_postcode=(select b.org_postcode from tmp.brxx b where b.contact_id=c.id)
	where exists(select * from tmp.brxx b where b.contact_id=c.id and  coalesce(b.org_postcode,'--') <> coalesce(c.org_postcode,'--'));

--更新工作单位电话
update hams.contacts c set org_phone=(select b.org_phone from tmp.brxx b where b.contact_id=c.id)
	where exists(select * from tmp.brxx b where b.contact_id=c.id and  coalesce(b.org_phone,'--') <> coalesce(c.org_phone,'--'));

--更新户籍地址
update hams.contacts c set residence_address=(select b.residence_address from tmp.brxx b where b.contact_id=c.id)
	where exists(select * from tmp.brxx b where b.contact_id=c.id and  coalesce(b.residence_address,'--') <> coalesce(c.residence_address,'--'));


update tmp.brxx set relation_id=datetime_id() where relation_id is null;

insert into hams.relations (id,inpatient_id,idx,relationship_id,name)
	select relation_id,id,1,coalesce(relationship_id,1),coalesce(b.relation_name,'--') from tmp.brxx b
	where not exists(select * from hams.relations r where r.id=b.relation_id);

--更新第一联系人姓名
update hams.relations r set name=(select b.relation_name from tmp.brxx b where b.relation_id=r.id)
	where exists(select * from tmp.brxx b where b.relation_id=r.id and  coalesce(b.relation_name,'--') <> coalesce(r.name,'--'));

--更新第一联系人关系
update hams.relations r set relationship_id=(select b.relationship_id from tmp.brxx b where b.relation_id=r.id)
	where exists(select * from tmp.brxx b where b.relation_id=r.id and  coalesce(b.relationship_id,0) <> coalesce(r.relationship_id,0));

--更新第一联系人电话
update hams.relations r set phone=(select b.relation_phone from tmp.brxx b where b.relation_id=r.id)
	where exists(select * from tmp.brxx b where b.relation_id=r.id and  coalesce(b.relation_phone,'--') <> coalesce(r.phone,'--'));

--更新第一联系人地址
update hams.relations r set address=(select b.relation_address from tmp.brxx b where b.relation_id=r.id)
	where exists(select * from tmp.brxx b where b.relation_id=r.id and  coalesce(b.relation_address,'--') <> coalesce(r.address,'--'));

--更新病人床位号
update hams.inpatients i set bed_no=(select coalesce(b.bed_no,'--') from tmp.brxx b where b.id=i.id)
	where exists(select * from tmp.brxx b where b.id=i.id and coalesce(b.bed_no,'--') <> coalesce(i.bed_no,'--'));

--更新病人床位医生
update hams.inpatients i set bed_doctor=(select coalesce(b.bed_doctor_name,'--') from tmp.brxx b where b.id=i.id)
	where exists(select * from tmp.brxx b where b.id=i.id and coalesce(b.bed_doctor_name,'--') <> coalesce(i.bed_doctor,'--'));

--更新病人病区
update hams.inpatients i set ward_id=(select coalesce(b.ward_id,1) from tmp.brxx b where b.id=i.id)
	where exists(select * from tmp.brxx b where b.id=i.id and coalesce(b.ward_id,1) <> coalesce(i.ward_id,1));

--增补住院押金
--insert into hams.deposits(id,inpatient_id,amount,pay_at)
--	select datetime_id(),i.id,0,i.begin_at from hams.inpatients i
--	where i.status_id in(1,2) and not exists(select * from hams.deposits d where d.inpatient_id=i.id);

--增补伙食费
--insert into hams.wallets(id,inpatient_id,balance,wallet_type,created_on,init_balance)
--	select datetime_id(),i.id,0,1,i.begin_at,0 from hams.inpatients i
--	where i.status_id in(1,2) and not exists(select * from  hams.wallets w where w.inpatient_id=i.id and w.wallet_type=1);

--增补零用金
--insert into hams.wallets(id,inpatient_id,balance,wallet_type,created_on,init_balance)
--	select datetime_id(),i.id,0,2,i.begin_at,0 from hams.inpatients i
--	where i.status_id in(1,2) and not exists(select * from  hams.wallets w where w.inpatient_id=i.id and w.wallet_type=2);
