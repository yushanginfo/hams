[#ftl]
[@b.head/]
[@b.grid items=inpatients var="inpatient"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
    bar.addItem("导入",action.method('importForm'));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="10%" property="code" title="住院号"]${inpatient.code}[/@]
    [@b.col property="name" title="姓名"]
      [@b.a href="!info?id=${inpatient.id}"]
        ${inpatient.name}
      [/@]
    [/@]
    [@b.col width="8%" property="gender.name" title="性别"/]
    [@b.col width="8%" property="ward.name" title="病区"/]
    [@b.col width="8%" property="status.name" title="状态"/]
  [/@]
[/@]
[@b.foot/]
