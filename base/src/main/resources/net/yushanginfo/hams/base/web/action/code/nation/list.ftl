[#ftl]
[@b.head/]
[@b.grid items=nations var="nation"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除？"));
  [/@]
  [@b.row]
    [@b.boxcol/]
    [@b.col width="10%" property="code" title="代码"]${nation.code}[/@]
    [@b.col width="10%" property="name" title="名称"][@b.a href="!info?id=${nation.id}"]${nation.name}[/@][/@]
    [@b.col property="enName" title="英文名称"]${nation.enName!}[/@]
    [@b.col width="15%" property="beginOn" title="生效时间"]${nation.beginOn!}[/@]
    [@b.col width="15%" property="endOn" title="失效时间"]${nation.endOn!}[/@]
  [/@]
[/@]
[@b.foot/]
