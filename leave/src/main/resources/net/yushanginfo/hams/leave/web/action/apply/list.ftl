[#ftl]
[@b.head/]
[@b.grid items=applies var="apply"]
  [@b.gridbar]
    bar.addItem("出院办理",action.method('dischargeForm'));
    bar.addItem("请假办理",action.add());
    bar.addItem("销假",action.edit());
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="10%" property="code" title="离院流水号"]${apply.code}[/@]
    [@b.col property="inpatient.name" title="姓名"]
      [@b.a href="!info?id=${apply.id}" target="_blank"]
        ${apply.inpatient.name}
      [/@]
    [/@]
    [@b.col width="5%" property="inpatient.gender.name" title="性别"/]
    [@b.col width="8%" property="inpatient.ward.name" title="病区"/]
    [@b.col width="5%" property="inpatient.bedNo" title="床号"/]
    [@b.col width="8%" property="inpatient.bedDoctor" title="床位医生"/]
    [@b.col width="16%" property="leaveAt" title="离院~预计销假"]${apply.leaveAt?string('yyyy-MM-dd')}~${(apply.predictBackAt?string('yyyy-MM-dd'))!}[/@]
    [@b.col width="8%" property="backAt" title="返院时间"]${(apply.endAt?string('yyyy-MM-dd'))!}[/@]
    [@b.col width="8%" property="category.name" title="分类"/]
  [/@]
[/@]
[@b.foot/]
