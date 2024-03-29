[#ftl]
[@b.head/]
[@b.grid items=inpatients var="inpatient"]
  [@b.gridbar]
    bar.addItem("修改联系人",action.edit());
    bar.addItem("立即同步",action.method("manualSync"));
    bar.addItem("${b.text("action.export")}",action.exportData("code:住院号,name:姓名,"+
                 "gender.name:性别,ward.name:病区,bedNo:床号",null,'fileName=病人信息'));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="10%" property="code" title="住院号"]${inpatient.code}[/@]
    [@b.col property="name" title="姓名"]
      [@b.a href="!info?id=${inpatient.id}"]
        ${inpatient.name}
      [/@]
    [/@]
    [@b.col width="5%" property="gender.name" title="性别"/]
    [@b.col width="8%" property="person.birthday" title="出生年月"]
    ${(inpatient.person.birthday?string("yyyy-MM"))!}
    [/@]
    [@b.col width="10%" property="feeOrigin.name" title="费用类别"/]
    [@b.col width="10%" title="联系人"]
      [#list inpatient.relations as r]<span title="${r.phone!}">${r.name}</span>[#sep],[/#list]
    [/@]
    [@b.col width="8%" property="ward.name" title="病区"/]
    [@b.col width="5%" property="bedNo" title="床号"/]
    [@b.col width="8%" property="bedDoctor" title="床位医生"/]
    [@b.col width="16%" property="beginAt" title="入院~出院日期"]${inpatient.beginAt?string('yyyy-MM-dd')}~${(inpatient.endAt?string('yyyy-MM-dd'))!}[/@]
    [@b.col width="8%" property="status.name" title="状态"/]
  [/@]
[/@]
[@b.foot/]
