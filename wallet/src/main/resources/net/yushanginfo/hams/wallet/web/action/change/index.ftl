[#ftl]
[@b.head/]
[#include "../change_nav.ftl"/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="searchForm" action="!search" target="walletList" title="ui.searchForm" theme="search"]
      [@b.textfields names="wallet.inpatient.code;住院号"/]
      [@b.textfields names="wallet.inpatient.name;姓名"/]
      [@b.select name="wallet.inpatient.ward.id" label="病区" items=wards empty="..."/]
      <input type="hidden" name="orderBy" value="wallet.inpatient.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="walletList" href="!search?orderBy=wallet.inpatient.code"/]</div>
</div>
[@b.foot/]
