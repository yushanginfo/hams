[#ftl]
[@b.head/]
[@b.grid items=departments var="department"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="10%" property="code" title="代码"]${department.code}[/@]
    [@b.col property="name" title="名称"][@b.a href="!info?id=${department.id}"]${department.name}[/@][/@]
    [@b.col width="10%" property="indexno" title="序号"]${department.indexno}[/@]
    [@b.col width="10%" property="beginOn" title="生效时间"]${department.beginOn!}[/@]
    [@b.col width="10%" property="endOn" title="失效时间"]${department.endOn!}[/@]
  [/@]
[/@]
[@b.foot/]
