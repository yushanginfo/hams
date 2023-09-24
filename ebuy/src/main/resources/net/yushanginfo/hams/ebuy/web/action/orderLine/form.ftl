[#ftl]
[@b.head/]
[@b.toolbar title="随心E购买明细"]bar.addBack();[/@]
  [@b.form action=b.rest.save(orderLine) theme="list"]
    [@b.select name="orderLine.inpatient.id" label="病人" href="!inpatients.json?q={term}" option="id,description" empty="..."
               value=orderLine.inpatient! required="true" maxlength="80"/]
    [@b.combobox name="commodity.id" label="日用品" href="!commodities.json?q={term}" value=orderLine.commodity! required="false" maxlength="80"/]
    [@b.combobox name="brand.id" label="品牌" href="!brands.json?q={term}" value=orderLine.brand! required="false" empty="..." maxlength="80"/]
    [@b.combobox name="unit.id" label="单位" href="!units.json?q={term}" value=orderLine.unit! required="false" maxlength="80"/]
    [@b.number name="orderLine.amount" label="数量" value= orderLine.amount required="true"/]
    [@b.textfield name="orderLine.payable" label="应付" value= orderLine.payable! required="true"/]
    [@b.textfield name="orderLine.payment" label="实付" value= orderLine.payment! required="true"/]

    [#if orderLine.cost??]
      [@b.textfield name="orderLine.cost" label="总计" value= orderLine.cost required="true"/]
    [/#if]
    [@b.formfoot]
      <input name="orderLine.order.id" value="${orderLine.order.id}" type="hidden"/>
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
  [#list 1..10 as i]<br>[/#list]
[@b.foot/]
