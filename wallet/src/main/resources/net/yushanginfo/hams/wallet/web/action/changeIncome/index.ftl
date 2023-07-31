[#ftl]
[@b.head/]
[#include "../change_nav.ftl"/]
[@b.toolbar title="零用金入账明细"]
[/@]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="searchForm" action="!search" target="incomeList" title="ui.searchForm" theme="search"]
      [@b.textfields names="income.wallet.inpatient.code;住院号"/]
      [@b.textfields names="income.wallet.inpatient.name;姓名"/]
      [@b.select name="wallet.inpatient.ward.id" label="病区" items=wards empty="..."/]
      [@b.date name="beginAt" label="从"  /]
      [@b.date name="endAt" label="到"  /]
      <input type="hidden" name="orderBy" value="income.payAt desc"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="incomeList" href="!search?orderBy=income.payAt desc"/]</div>
</div>
[@b.foot/]
