[#ftl]
[@b.head/]
[@b.grid items=commodityUnits var="commodityUnit"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col property="code" title="代码" width="15%"  /]
    [@b.col property="name" title="名称" /]
    [@b.col property="beginOn" title="生效时间" width="15%" ]${commodityUnit.beginOn!}~${commodityUnit.endOn!}[/@]
  [/@]
[/@]
[@b.foot/]
