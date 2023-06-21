[#ftl]
[@b.head/]
[#include "../pension_nav.ftl"/]
[@b.toolbar title="养老金余额管理"]
  bar.addItem("每期汇总",function() {
    bg.form.submit(document.searchForm, "${b.url("pension-stat!index")}", "_blank");
  });
[/@]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="searchForm" action="!search" target="pensionList" title="ui.searchForm" theme="search"]
      [@b.textfields names="pension.inpatient.code;住院号"/]
      [@b.textfields names="pension.inpatient.name;姓名"/]
      [@b.select name="pension.inpatient.ward.id" label="病区" items=wards empty="..."/]
      <input type="hidden" name="orderBy" value="pension.inpatient.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="pensionList" href="!search?orderBy=pension.inpatient.code"/]</div>
</div>
[@b.foot/]
