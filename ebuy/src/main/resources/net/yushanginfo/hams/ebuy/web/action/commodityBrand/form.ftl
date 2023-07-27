[#ftl]
[@b.head/]
[@b.toolbar title="日用品单位基本信息"]bar.addBack();[/@]
  [@b.form action=b.rest.save(commodityBrand) theme="list"]
    [@b.textfield name="commodityBrand.code" label="代码" value=commodityBrand.code! required="true" maxlength="10"/]
    [@b.textfield name="commodityBrand.name" label="名称" value=commodityBrand.name! required="true" maxlength="80"/]
    [@b.startend label="有效期限"
      name="commodityBrand.beginOn,commodityBrand.endOn" required="true,false"
      start=commodityBrand.beginOn end=commodityBrand.endOn! format="date"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[@b.foot/]
