[#ftl]
[@b.head/]
[@b.grid items=incomes var="income"]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="10%" property="inpatient.code" title="住院号"]${income.inpatient.code}[/@]
    [@b.col property="inpatient.name" title="姓名"]${income.inpatient.name}[/@]
    [@b.col width="8%" property="inpatient.gender.name" title="性别"/]
    [@b.col width="8%" property="inpatient.ward.name" title="病区"/]
    [@b.col width="5%" property="inpatient.bedNo" title="床号"/]
    [@b.col width="8%" property="amount" title="入账金额"/]
    [@b.col width="8%" property="wallet.walletType" title="账户类型"]${income.wallet.walletType.name}[/@]
    [@b.col width="10%" property="channel.name" title="入账渠道"/]
    [@b.col width="18%" property="payAt" title="入账时间"]
      ${income.payAt?string('yyyy-MM-dd HH:mm')}
    [/@]
  [/@]
[/@]
[@b.foot/]
