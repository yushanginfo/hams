[#ftl]
[@b.head/]
[@b.grid items=ebuyOrders var="ebuyOrder"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("登记表",action.single('requireReport',null,null,"_blank"),"action-print");
    bar.addItem("汇总表",action.single('priceReport',null,null,"_blank"),"action-print");
    bar.addItem("输入价格",action.single('inputPrice',null,null,"_blank"));
    var m = bar.addMenu("批量...");
    m.addItem("设置扣款日期",action.multi("batchUpdateSetting"));
    m.addItem("从零用金中扣款",action.multi('generateBills',"确定开始扣款(多次点击仅会生成一次扣款流水)？"));
    m.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col property="name" title="名称" /]
    [@b.col width="12%" title="病区" property="ward.name"/]
    [@b.col width="16%" property="beginOn" title="登记日期"]${ebuyOrder.beginOn!}~${ebuyOrder.endOn!}[/@]
    [@b.col width="9%" title="采购数量"]${ebuyOrder.lines?size}[/@]
    [@b.col width="9%" title="物品数量"]${ebuyOrder.prices?size}[/@]
    [@b.col width="9%" title="总金额"]${ebuyOrder.payment!}[/@]
    [@b.col width="10%" property="orderOn" title="采购日期" /]
    [@b.col width="10%" property="billOn" title="入账日期" /]
    [@b.col width="10%" property="billGenerated" title="零用金扣款"]${ebuyOrder.billGenerated?string('完成','未完成')}[/@]
  [/@]
[/@]
[@b.foot/]
