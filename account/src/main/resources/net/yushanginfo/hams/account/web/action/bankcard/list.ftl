[#ftl]
[@b.head/]
[@b.grid items=bankcards var="bankcard"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
    //bar.addItem("导入",action.method('importForm'));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="10%" property="inpatient.code" title="住院号"]${bankcard.inpatient.code}[/@]
    [@b.col property="inpatient.name" title="姓名"]
      [@b.a href="!info?id=${bankcard.id}"]
        ${bankcard.inpatient.name}
      [/@]
    [/@]
    [@b.col width="8%" property="inpatient.gender.name" title="性别"/]
    [@b.col width="10%" property="inpatient.feeOrigin.name" title="费用类别"/]
    [@b.col width="15%" property="bank" title="银行卡"]
      <span style="font-size:0.8em">${bankcard.bank} ${bankcard.cardNo}</span>
    [/@]
    [@b.col width="8%" property="balance" title="余额"/]
    [@b.col width="8%" property="inpatient.ward.name" title="病区"/]
    [@b.col width="5%" property="inpatient.bedNo" title="床号"/]
    [@b.col width="10%" property="inpatient.beginAt" title="住院日期"]${bankcard.inpatient.beginAt?string("yyyy-MM-dd")}[/@]
    [@b.col width="8%" property="inpatient.status.name" title="状态"/]
  [/@]
[/@]
[@b.foot/]
