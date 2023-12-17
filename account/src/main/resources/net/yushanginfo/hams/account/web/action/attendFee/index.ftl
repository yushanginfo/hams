[#ftl]
[@b.head/]
[#include "../attendFee_nav.ftl"/]
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
