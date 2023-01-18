[#ftl]
[@b.head/]
[@b.grid items=bills var="bill"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
    bar.addItem("导入",action.method('importForm'));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="10%" property="inpatient.code" title="住院号"]${bill.inpatient.code}[/@]
    [@b.col property="inpatient.name" title="姓名"]
      [@b.a href="!info?id=${bill.id}"]
        ${bill.inpatient.name}
      [/@]
    [/@]
    [@b.col width="8%" property="inpatient.gender.name" title="性别"/]
    [@b.col width="8%" property="inpatient.ward.name" title="病区"/]
    [@b.col width="8%" property="amount" title="支出金额"/]
    [@b.col width="8%" property="payAt" title="记账时间"]
      ${bill.payAt?string('yyyy-MM-dd HH:mm')}
    [/@]
  [/@]
[/@]
[@b.foot/]
