[#ftl]
[@b.head/]
[@b.grid items=bills var="bill"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    var m = bar.addMenu("${b.text("action.export")}",action.exportData("account.inpatient.code:住院号,account.inpatient.name:姓名,"+
                 "account.inpatient.gender.name:性别,account.inpatient.ward.name:病区,account.inpatient.bedNo:床号,"+
                 "amount:支出金额,expenses:消费明细,payAt:支出时间",null,'fileName=养护补贴支出流水'));
    m.addItem("导入",action.method('importForm'));
    m.addItem("删除",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="10%" property="account.inpatient.code" title="住院号"]${bill.account.inpatient.code}[/@]
    [@b.col property="account.inpatient.name" title="姓名"]
      [@b.a href="!info?id=${bill.id}"]
        ${bill.account.inpatient.name}
      [/@]
    [/@]
    [@b.col width="10%" property="account.inpatient.feeOrigin.name" title="费用类别"/]
    [@b.col width="8%" property="account.inpatient.gender.name" title="性别"/]
    [@b.col width="8%" property="account.inpatient.ward.name" title="病区"/]
    [@b.col width="5%" property="account.inpatient.bedNo" title="床号"/]
    [@b.col width="8%" property="amount" title="支出金额"/]
    [@b.col width="8%" property="expenses" title="消费明细"/]
    [@b.col width="18%" property="payAt" title="支出时间"]
      <span title="余额：${bill.balance}">${bill.payAt?string('yyyy-MM-dd HH:mm')}</span>
    [/@]
  [/@]
[/@]
[@b.foot/]
