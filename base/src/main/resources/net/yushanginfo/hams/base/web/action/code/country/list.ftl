[#ftl]
[@b.head/]
[@b.grid items=countries var="country"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除？"));
  [/@]
  [@b.row]
    [@b.boxcol/]
    [@b.col width="10%" property="code" title="代码"]${country.code}[/@]
    [@b.col width="10%" property="name" title="名称"][@b.a href="!info?id=${country.id}"]${country.name}[/@][/@]
    [@b.col property="enName" title="英文名称"]${country.enName!}[/@]
    [@b.col width="15%" property="beginOn" title="生效时间"]${country.beginOn!}[/@]
    [@b.col width="15%" property="endOn" title="失效时间"]${country.endOn!}[/@]
  [/@]
[/@]
[@b.foot/]
