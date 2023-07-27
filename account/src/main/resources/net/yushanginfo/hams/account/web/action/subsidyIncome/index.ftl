[#ftl]
[@b.head/]
[#include "../subsidy_nav.ftl"/]
[@b.toolbar title="养护补贴入账明细"]
[/@]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="searchForm" action="!search" target="incomeList" title="ui.searchForm" theme="search"]
      [@b.textfields names="income.account.inpatient.code;住院号"/]
      [@b.textfields names="income.account.inpatient.name;姓名"/]
      [@b.select name="income.account.inpatient.ward.id" label="病区" items=wards empty="..."/]
      [@b.date name="beginAt" label="从"  /]
      [@b.date name="endAt" label="到"  /]
      <input type="hidden" name="orderBy" value="income.account.inpatient.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="incomeList" href="!search?orderBy=income.account.inpatient.code"/]</div>
</div>
[@b.foot/]
