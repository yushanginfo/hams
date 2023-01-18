[#ftl]
[@b.head/]
[#include "../change_nav.ftl"/]
[@b.toolbar title="零用金扣费流水"]
[/@]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="searchForm" action="!search" target="billList" title="ui.searchForm" theme="search"]
      [@b.textfields names="bill.wallet.inpatient.code;住院号"/]
      [@b.textfields names="bill.wallet.inpatient.name;姓名"/]
      <input type="hidden" name="orderBy" value="bill.wallet.inpatient.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="billList" href="!search?orderBy=bill.wallet.inpatient.code"/]</div>
</div>
[@b.foot/]
