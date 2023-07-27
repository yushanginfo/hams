[#ftl]
[@b.head/]
[@b.grid items=ebuyOrders var="ebuyOrder"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
    bar.addItem("输入价格",action.single('inputPrice',null,null,"_blank"));
    bar.addItem("汇总表",action.single('priceReport',null,null,"_blank"));
    bar.addItem("从零用金中扣款",action.single('generateBill',"确定开始扣款(多次点击仅会生成一次扣款流水)？"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col property="name" title="名称" /]
    [@b.col width="20%" property="beginOn" title="登记日期"]${ebuyOrder.beginOn!}~${ebuyOrder.endOn!}[/@]
    [@b.col width="15%" title="采购数量"]${ebuyOrder.lines?size}[/@]
    [@b.col width="15%" title="物品数量"]${ebuyOrder.prices?size}[/@]
    [@b.col width="10%" title="总金额"]${ebuyOrder.cost!}[/@]
    [@b.col width="10%" property="orderOn" title="采购日期" /]
    [@b.col width="10%" property="billGenerated" title="零用金扣款"]${ebuyOrder.billGenerated?string('完成','未完成')}[/@]
  [/@]
[/@]
[@b.foot/]
