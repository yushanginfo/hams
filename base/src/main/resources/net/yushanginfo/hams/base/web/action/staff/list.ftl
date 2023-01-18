[#ftl]
[@b.head/]
[@b.grid items=staffs var="staff"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
    bar.addItem("导入",action.method('importForm'));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="10%" property="code" title="工号"]${staff.code}[/@]
    [@b.col width="10%" property="name" title="姓名"]
      [@b.a href="!info?id=${staff.id}"]
        <span title="${staff.beginOn!}~${staff.endOn!}">${staff.name}</span>
      [/@]
    [/@]
    [@b.col width="8%" property="gender.name" title="性别"/]
    [@b.col property="department.name" title="部门"]${(staff.department.name)!}[/@]
    [@b.col width="10%" property="staffType.name" title="类别"]${(staff.staffType.name)!}[/@]
    [@b.col width="8%" property="mobile" title="手机"/]
    [@b.col property="beginOn" title="在院时间"]${staff.beginOn?string('yyyy-MM')}~${(staff.endOn?string('yyyy-MM'))!'至今'}[/@]
  [/@]
[/@]
[@b.foot/]
