[#ftl]
[@b.head/]
[#include "../attendFee_nav.ftl"/]
[@b.toolbar title="陪护费余额管理"]
  bar.addItem("每期汇总",function() {
    bg.form.submit(document.searchForm, "${b.url("attend-fee-stat!index")}", "_blank");
  });
[/@]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="searchForm" action="!search" target="attendFeeList" title="ui.searchForm" theme="search"]
      [@b.textfields names="attendFee.inpatient.code;住院号"/]
      [@b.textfields names="attendFee.inpatient.name;姓名"/]
      [@b.select name="attendFee.inpatient.ward.id" label="病区" items=wards empty="..."/]
      <input type="hidden" name="orderBy" value="attendFee.inpatient.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="attendFeeList" href="!search?orderBy=attendFee.inpatient.code"/]</div>
</div>
[@b.foot/]
