[#ftl]
[@b.head/]
[#include "../meal_nav.ftl"/]
[@b.toolbar title="伙食费扣费流水"]
[/@]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="searchForm" action="!search" target="billList" title="ui.searchForm" theme="search"]
      [@b.textfields names="bill.wallet.inpatient.code;住院号"/]
      [@b.textfields names="bill.wallet.inpatient.name;姓名"/]
      [@b.select name="bill.wallet.inpatient.ward.id" label="病区" items=wards empty="..."/]
      [@b.date name="beginAt" label="从"  /]
      [@b.date name="endAt" label="到"  /]
      <input type="hidden" name="orderBy" value="bill.wallet.inpatient.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="billList" href="!search?orderBy=bill.wallet.inpatient.code"/]</div>
</div>
[@b.foot/]
