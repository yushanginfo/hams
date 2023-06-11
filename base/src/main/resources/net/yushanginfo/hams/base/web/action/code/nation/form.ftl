[#ftl]
[@b.head/]
[@b.toolbar title="新建民族信息"]bar.addBack();[/@]
  [@b.form action=b.rest.save(nation) theme="list"]
    [@b.textfield name="nation.code" label="代码" value=nation.code! required="true" maxlength="20"/]
    [@b.textfield name="nation.name" label="名称" value=nation.name! required="true" maxlength="200"/]
    [@b.textfield name="nation.enName" label="英文名称" value=nation.enName! maxlength="200"/]
    [@b.startend label="有效期限"
      name="nation.beginOn,nation.endOn" required="true,false"
      start=nation.beginOn end=nation.endOn format="date"/]
    [@b.textfield name="nation.remark" label="备注" value="${nation.remark!}" maxlength="30"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[@b.foot/]
