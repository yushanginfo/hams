[#ftl]
[@b.head/]
[@b.grid items=incomes var="income"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
    bar.addItem("导入",action.method('importForm'));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="10%" property="inpatient.code" title="住院号"]${income.inpatient.code}[/@]
    [@b.col property="inpatient.name" title="姓名"]
      [@b.a href="!info?id=${income.id}"]
        ${income.inpatient.name}
      [/@]
    [/@]
    [@b.col width="8%" property="inpatient.gender.name" title="性别"/]
    [@b.col width="8%" property="inpatient.ward.name" title="病区"/]
    [@b.col width="8%" property="amount" title="入账金额"/]
    [@b.col width="8%" property="channel" title="入账渠道"/]
    [@b.col width="8%" property="updatedAt" title="入账时间"]
      ${income.updatedAt?string('yyyy-MM-dd HH:mm')}
    [/@]
  [/@]
[/@]
[@b.foot/]
