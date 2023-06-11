[#ftl]
[@b.head/]
[@b.grid items=diseaseTypes var="diseaseType"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="15%" property="code" title="代码"]${diseaseType.code}[/@]
    [@b.col property="name" title="名称"][@b.a href="!info?id=${diseaseType.id}"]${diseaseType.name}[/@][/@]
    [@b.col property="enName" width="20%" title="英文名称"]${diseaseType.enName!}[/@]
    [@b.col width="15%" property="beginOn" title="生效时间"]${diseaseType.beginOn!}[/@]
    [@b.col width="15%" property="endOn" title="失效时间"]${diseaseType.endOn!}[/@]
  [/@]
[/@]
[@b.foot/]
