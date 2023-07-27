[#ftl]
[@b.head/]
[@b.toolbar title="日用品单位基本信息"]bar.addBack();[/@]
  [@b.form action=b.rest.save(commodityUnit) theme="list"]
    [@b.textfield name="commodityUnit.code" label="代码" value=commodityUnit.code! required="true" maxlength="10"/]
    [@b.textfield name="commodityUnit.name" label="名称" value=commodityUnit.name! required="true" maxlength="80"/]
    [@b.startend label="有效期限"
      name="commodityUnit.beginOn,commodityUnit.endOn" required="true,false"
      start=commodityUnit.beginOn end=commodityUnit.endOn! format="date"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[@b.foot/]
