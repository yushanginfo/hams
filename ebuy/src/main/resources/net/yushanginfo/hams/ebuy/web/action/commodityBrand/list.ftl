[#ftl]
[@b.head/]
[@b.grid items=commodityBrands var="commodityBrand"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col property="code" title="代码" width="10%"/]
    [@b.col property="name" title="名称"/]
    [@b.col width="10%" property="beginOn" title="生效时间"]${commodityBrand.beginOn!}~${commodityBrand.endOn!}[/@]
  [/@]
[/@]
[@b.foot/]
