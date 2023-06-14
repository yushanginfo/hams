[#ftl]
[@b.head/]
[@b.toolbar title="养护补贴入账信息"]bar.addBack();[/@]
  [@b.form action=b.rest.save(income) theme="list"]
    [#if income.persisted]
      [@b.field label="病人"]
        ${income.wallet.inpatient.code} ${income.wallet.inpatient.name}
      [/@]
    [#else]
      [@b.textfield name="income.wallet.inpatient.code" label="住院号" value="" required="true" maxlength="10"/]
    [/#if]
    [@b.textfield name="income.amount" label="入账金额" value=income.amount! required="true" maxlength="20" comment="元"/]
    [@b.date name="income.payAt" label="入账时间" value=income.payAt! format="datetime" required="true" /]
    [@b.select name="income.channel.id" label="入账渠道" items=channels required="true" value=income.channel! style="width:100px"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[@b.foot/]
