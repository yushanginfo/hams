[#ftl]
[@b.head/]
[#include "../subsidy_nav.ftl"/]
[@b.toolbar title="养护补贴管理"]
  bar.addItem("每期汇总",function() {
    bg.form.submit(document.searchForm, "${b.url("subsidy-stat!index")}", "_blank");
  });
[/@]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="searchForm" action="!search" target="subsidyList" title="ui.searchForm" theme="search"]
      [@b.textfields names="subsidy.inpatient.code;住院号"/]
      [@b.textfields names="subsidy.inpatient.name;姓名"/]
      [@b.select name="subsidy.inpatient.ward.id" label="病区" items=wards empty="..."/]
      <input type="hidden" name="orderBy" value="subsidy.inpatient.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="subsidyList" href="!search?orderBy=subsidy.inpatient.code"/]</div>
</div>
[@b.foot/]
