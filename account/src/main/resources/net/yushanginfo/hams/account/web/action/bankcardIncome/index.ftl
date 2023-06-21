[#ftl]
[@b.head/]
[#include "../bankcard_nav.ftl"/]
[@b.toolbar title="银行卡入账明细"]
[/@]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="searchForm" action="!search" target="incomeList" title="ui.searchForm" theme="search"]
      [@b.textfields names="income.account.inpatient.code;住院号"/]
      [@b.textfields names="income.account.inpatient.name;姓名"/]
      [@b.select name="income.account.inpatient.ward.id" label="病区" items=wards empty="..."/]
      <input type="hidden" name="orderBy" value="income.account.inpatient.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="incomeList" href="!search?orderBy=income.account.inpatient.code"/]</div>
</div>
[@b.foot/]
