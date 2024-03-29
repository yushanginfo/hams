[#ftl]
[@b.head/]
[@b.toolbar title="修改病人状态"]bar.addBack();[/@]
  [@b.form action=b.rest.save(relationship) theme="list"]
    [@b.textfield name="relationship.code" label="代码" value="${relationship.code!}" required="true" maxlength="20"/]
    [@b.textfield name="relationship.name" label="名称" value="${relationship.name!}" required="true" maxlength="20"/]
    [@b.textfield name="relationship.enName" label="英文名称" value="${relationship.enName!}" maxlength="100"/]
    [@b.startend label="有效期限"
      name="relationship.beginOn,relationship.endOn" required="false,false"
      start=relationship.beginOn end=relationship.endOn format="date"/]
    [@b.textfield name="relationship.remark" label="备注" value="${relationship.remark!}" maxlength="3"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[@b.foot/]
