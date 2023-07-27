[#ftl]
[@b.head/]
[#include "../subsidy_nav.ftl"/]
[@b.toolbar title="养护补贴支出流水"]
[/@]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="searchForm" action="!search" target="billList" title="ui.searchForm" theme="search"]
      [@b.textfields names="bill.c.inpatient.code;住院号"/]
      [@b.textfields names="bill.account.inpatient.name;姓名"/]
      [@b.select name="bill.account.inpatient.ward.id" label="病区" items=wards empty="..."/]
      [@b.date name="beginAt" label="从"  /]
      [@b.date name="endAt" label="到"  /]
      <input type="hidden" name="orderBy" value="bill.account.inpatient.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="billList" href="!search?orderBy=bill.account.inpatient.code"/]</div>
</div>
[@b.foot/]
