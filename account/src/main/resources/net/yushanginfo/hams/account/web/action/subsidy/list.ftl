[#ftl]
[@b.head/]
[@b.grid items=subsidies var="subsidy"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    var m=bar.addMenu("${b.text("action.export")}",action.exportData("inpatient.code:住院号,inpatient.name:姓名,"+
                 "inpatient.gender.name:性别,inpatient.ward.name:病区,inpatient.bedNo:床号,"+
                 "balance:余额",null,'fileName=养护补贴余额信息'));
    m.addItem("导入",action.method('importForm'));
    m.addItem("删除",action.remove("确认删除?"));
    m.addItem("统计余额",action.multi("adjustBalance"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="10%" property="inpatient.code" title="住院号"]${subsidy.inpatient.code}[/@]
    [@b.col property="inpatient.name" title="姓名"]
      [@b.a href="!info?id=${subsidy.id}"]
        ${subsidy.inpatient.name}
      [/@]
    [/@]
    [@b.col width="8%" property="inpatient.gender.name" title="性别"/]
    [@b.col width="10%" property="inpatient.feeOrigin.name" title="费用类别"/]
    [@b.col width="8%" property="balance" title="余额"/]
    [@b.col width="8%" property="inpatient.ward.name" title="病区"/]
    [@b.col width="5%" property="inpatient.bedNo" title="床号"/]
    [@b.col width="10%" property="inpatient.beginAt" title="住院日期"]${subsidy.inpatient.beginAt?string("yyyy-MM-dd")}[/@]
    [@b.col width="8%" property="inpatient.status.name" title="状态"/]
  [/@]
[/@]
[@b.foot/]
