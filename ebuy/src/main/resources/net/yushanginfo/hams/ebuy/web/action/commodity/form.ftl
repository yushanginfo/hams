[#ftl]
[@b.head/]
<script type="text/javascript" crossorigin="anonymous"   src="${b.base}/static/js/chosen.js"></script>
[@b.toolbar title="日用品基本信息"]bar.addBack();[/@]
  [@b.form action=b.rest.save(commodity) theme="list"]
    [@b.textfield name="commodity.code" label="代码" value="${commodity.code!}" required="true" maxlength="10"/]
    [@b.textfield name="commodity.name" label="名称" value="${commodity.name!}" required="true" maxlength="80"/]
    [@b.combobox name="brand.id2" label="品牌2" href="!brands.json?q={term}" required="true"/]
    [@b.select name="brand.id" label="品牌" items=brands required="true" multiple="true" values=commodity.brands style="width:400px"/]
    [@b.select name="unit.id" label="单位" items=units required="true" multiple="true" values=commodity.units style="width:300px"/]
    [@b.startend label="有效期限"
      name="commodity.beginOn,commodity.endOn" required="true,false"
      start=commodity.beginOn end=commodity.endOn format="date"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
  [#list 1..10 as i]<br>[/#list]
[@b.foot/]
