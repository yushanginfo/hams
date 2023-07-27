[#ftl]
[@b.head/]
[@b.grid items=incomes var="income"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
    bar.addItem("${b.text("action.export")}",action.exportData("account.inpatient.code:住院号,account.inpatient.name:姓名,"+
                 "account.inpatient.gender.name:性别,account.inpatient.ward.name:病区,account.inpatient.bedNo:床号,"+
                 "amount:入账金额,account.bank:银行,account.cardNo:卡号,category.name:入账类别,payAt:入账时间",null,'fileName=银行卡入账流水'));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="10%" property="inpatient.code" title="住院号"]${income.account.inpatient.code}[/@]
    [@b.col property="inpatient.name" title="姓名"]
      [@b.a href="!info?id=${income.id}"]
        ${income.account.inpatient.name}
      [/@]
    [/@]
    [@b.col width="8%" property="account.inpatient.gender.name" title="性别"/]
    [@b.col width="8%" property="account.inpatient.ward.name" title="病区"/]
    [@b.col width="5%" property="account.inpatient.bedNo" title="床号"/]
    [@b.col width="8%" property="amount" title="入账金额"/]
    [@b.col width="15%" property="account.bank" title="银行卡"]
      <span style="font-size:0.8em">${income.account.bank} ${income.account.cardNo}</span>
    [/@]
    [@b.col width="10%" property="category.name" title="入账类别"/]
    [@b.col width="18%" property="payAt" title="入账时间"]
      ${income.payAt?string('yyyy-MM-dd HH:mm')}
    [/@]
  [/@]
[/@]
[@b.foot/]
