[#ftl]
[@b.head/]
[@b.grid items=commodities var="commodity"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col property="code" title="代码" width="10%"/]
    [@b.col property="name" title="名称"][@b.a href="!info?id=${commodity.id}"]${commodity.name}[/@][/@]
    [@b.col title="品牌"][#list commodity.brands as b]${b.name}[#sep],[/#list][/@]
    [@b.col title="单位"][#list commodity.units as b]${b.name}[#sep],[/#list][/@]
    [@b.col width="10%" property="beginOn" title="生效时间"]${commodity.beginOn!}~${commodity.endOn!}[/@]
  [/@]
[/@]
[@b.foot/]
