[#ftl]
[@b.head/]
[@b.grid items=incomes var="income"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    var m = bar.addMenu("${b.text("action.export")}",action.exportData("wallet.inpatient.code:住院号,wallet.inpatient.name:姓名,"+
                 "wallet.inpatient.gender.name:性别,wallet.inpatient.ward.name:病区,wallet.inpatient.bedNo:床号,"+
                 "amount:入账金额,channel.name:入账渠道,payAt:入账时间",null,'fileName=零用金入账流水'));
    m.addItem("导入",action.method('importForm'));
    m.addItem("删除",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="10%" property="wallet.inpatient.code" title="住院号"]${income.wallet.inpatient.code}[/@]
    [@b.col property="wallet.inpatient.name" title="姓名"]
      [@b.a href="!info?id=${income.id}"]
        ${income.wallet.inpatient.name}
      [/@]
    [/@]
    [@b.col width="8%" property="wallet.inpatient.gender.name" title="性别"/]
    [@b.col width="8%" property="wallet.inpatient.ward.name" title="病区"/]
    [@b.col width="5%" property="wallet.inpatient.bedNo" title="床号"/]
    [@b.col width="8%" property="amount" title="入账金额"/]
    [@b.col width="8%" property="balance" title="余额"/]
    [@b.col width="10%" property="channel.name" title="入账渠道"/]
    [@b.col width="18%" property="payAt" title="入账时间"]
      <span title="余额：${income.balance}">${income.payAt?string('yyyy-MM-dd HH:mm')}</span>
    [/@]
  [/@]
[/@]
[@b.foot/]
