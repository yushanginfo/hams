[#ftl]
[@b.head/]
[@b.toolbar title="随心E购买修改"]bar.addBack();[/@]
  [@b.form action=b.rest.save(ebuyOrder) theme="list"]
    [@b.textfield name="ebuyOrder.name" label="名称" value=ebuyOrder.name! required="true" maxlength="80"/]
    [@b.date label="购买日期" name="ebuyOrder.orderOn" required="false" value=ebuyOrder.orderOn! format="date"/]
    [@b.startend label="采购登记日期"
      name="ebuyOrder.beginOn,ebuyOrder.endOn" required="true,false"
      start=ebuyOrder.beginOn end=ebuyOrder.endOn! format="date"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[@b.foot/]
