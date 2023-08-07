[#ftl]
[@b.head/]
[@b.grid items=deposits var="deposit"]
  [@b.gridbar]
    bar.addItem("入账",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    var m=bar.addMenu("${b.text("action.export")}",action.exportData("inpatient.code:住院号,inpatient.name:姓名,"+
                 "inpatient.gender.name:性别,inpatient.ward.name:病区,inpatient.bedNo:床号,"+
                 "amount:金额,inpatient.beginAt:入院时间,payAt:缴费时间,inpatient.refundAt:退款时间",null,'fileName=住院押金信息'));
    m.addItem("导入",action.method('importForm'));
    m.addItem("删除",action.remove());
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="10%" property="inpatient.code" title="住院号"]${deposit.inpatient.code}[/@]
    [@b.col property="inpatient.name" title="姓名"]
      [@b.a href="!info?id=${deposit.id}"]
        ${deposit.inpatient.name}
      [/@]
    [/@]
    [@b.col width="8%" property="inpatient.gender.name" title="性别"/]
    [@b.col width="12%" property="inpatient.feeOrigin.name" title="费用类别"/]
    [@b.col width="10%" property="amount" title="金额"/]
    [@b.col width="8%" property="inpatient.ward.name" title="病区"/]
    [@b.col width="5%" property="inpatient.bedNo" title="床号"/]
    [@b.col width="10%" property="payAt" title="缴费日期"]${deposit.payAt?string("yyyy-MM-dd")}[/@]
    [@b.col width="10%" property="refundAt" title="退回日期"]${(deposit.refundAt?string("yyyy-MM-dd"))!}[/@]
    [@b.col width="10%" property="inpatient.status.name" title="状态"/]
  [/@]
[/@]
[@b.foot/]
