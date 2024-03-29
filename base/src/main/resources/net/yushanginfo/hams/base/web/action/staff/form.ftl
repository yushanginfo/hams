[#ftl]
[@b.head/]
[@b.toolbar title="修改职工信息"]bar.addBack();[/@]
  [@b.form action=b.rest.save(staff) theme="list"]
    [@b.textfield name="staff.code" label="工号" value=staff.code! required="true" style="width:100px;" maxlength="20"/]
    [@b.textfield name="staff.name" label="姓名" value=staff.name! required="true" style="width:100px;" maxlength="20"/]

    [@b.select name="staff.gender.id" label="性别" value=staff.gender! required="true"  items=genders empty="..."/]

    [@b.select name="staff.department.id" label="所在部门" value=staff.department! required="true" style="width:200px;" items=departments empty="..."/]

    [@b.textfield label="移动电话"  name="staff.mobile" value=staff.mobile! required="false" /]
    [@b.startend label="在院时间" name="staff.beginOn,staff.endOn" required="true,false" start=staff.beginOn end=staff.endOn format="date"/]
    [@b.textfield name="staff.remark" label="备注" value=staff.remark! maxlength="30"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[@b.foot/]
