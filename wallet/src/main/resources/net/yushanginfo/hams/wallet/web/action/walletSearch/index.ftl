[#ftl]
[@b.head/]
[#include "../wallet_nav.ftl"/]
[@b.toolbar title="账户余额查询"]
  bar.addItem("零用金每期汇总",function() {
    bg.form.submit(document.searchForm, "${b.url("change-stat")}", "_blank");
  });
[/@]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="searchForm" action="!search" target="walletList" title="ui.searchForm" theme="search"]
      [@b.textfields names="wallet.inpatient.code;住院号"/]
      [@b.textfields names="wallet.inpatient.name;姓名"/]
      [@b.select name="wallet.inpatient.ward.id" label="病区" items=wards empty="..."/]
      [@b.select name="wallet.walletType" label="账户" empty="..."]
        <option value="">...</option>
        [#list walletTypes as wt]
        <option value="${wt}">${wt.name}</option>
        [/#list]
      [/@]
      <input type="hidden" name="orderBy" value="wallet.inpatient.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="walletList" href="!search?orderBy=wallet.inpatient.code"/]</div>
</div>
[@b.foot/]
