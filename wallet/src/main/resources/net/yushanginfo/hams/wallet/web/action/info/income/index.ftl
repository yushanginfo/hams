[#ftl]
[@b.head/]
[#include "../wallet_nav.ftl"/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="searchForm" action="!search" target="incomeList" title="ui.searchForm" theme="search"]
      [@b.textfields names="income.wallet.inpatient.code;住院号"/]
      [@b.textfields names="income.wallet.inpatient.name;姓名"/]
      [@b.select name="income.wallet.inpatient.ward.id" label="病区" items=wards empty="..."/]
      [@b.select name="income.wallet.walletType" label="账户" empty="..."]
        <option value="">...</option>
        [#list walletTypes as wt]
        <option value="${wt}">${wt.name}</option>
        [/#list]
      [/@]
      [@b.date name="beginAt" label="从"  /]
      [@b.date name="endAt" label="到"  /]
      <input type="hidden" name="orderBy" value="income.wallet.inpatient.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="incomeList" href="!search?orderBy=income.wallet.inpatient.code"/]</div>
</div>
[@b.foot/]
