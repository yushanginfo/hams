[#ftl]
[@b.head/]
[@b.toolbar title="出院办理信息"]bar.addBack();[/@]
  [@b.form action="!discharge" name="applyForm" theme="list"]
    [#if apply.persisted]
      [@b.field label="病人"]
        ${apply.inpatient.code} ${apply.inpatient.name}
      [/@]
    [#else]
      [@b.textfield name="inpatient.code" label="住院号" onchange="loadInpatient(this.value)" value=(apply.inpatient.code)! required="true" maxlength="10"/]
    [/#if]
    [@b.field label="病人"]
      [#if inpatient??]${inpatient.name} ${inpatient.gender.name}[/#if]&nbsp;
    [/@]
    [@b.field label="出院时间"]
      [#if inpatient??]${inpatient.endAt?string("yyyy-MM-dd HH:mm")}[/#if]&nbsp;
    [/@]
    [#list meals! as meal]
      [#if meal.balance.value<0] [@b.field label="伙食费尚欠费" ]<span style="color:red">${meal.balance}</span> 需要不足欠费。[/@]
      [#else]
        [@b.textfield label="伙食费退款" name="meal_balance" value=meal.balance/]
      [/#if]
    [/#list]
    [#list changes! as change]
    [@b.textfield label="零用金退款" name="change_balance" value=change.balance/]
    [/#list]
    [#list attendFees! as attendFee]
    [@b.textfield label="陪护费退款" name="attendFee_balance" value=attendFee.balance/]
    [/#list]
    [@b.select name="apply.category.id" label="离院类别" required="true" value=apply.category! items=categories /]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
  <script>
    function loadInpatient(code){
      bg.form.submit(document.applyForm,"${b.url('!dischargeForm')}")
    }
  </script>
[@b.foot/]
