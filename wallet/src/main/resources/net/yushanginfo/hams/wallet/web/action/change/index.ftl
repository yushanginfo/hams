[#ftl]
[@b.head/]
[#include "../change_nav.ftl"/]
[@b.toolbar title="零用金管理"]
  bar.addItem("每期汇总",function() {
    bg.form.submit(document.searchForm, "${b.url("!stat")}", "_blank");
  });
[/@]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="searchForm" action="!search" target="walletList" title="ui.searchForm" theme="search"]
      [@b.textfields names="wallet.inpatient.code;住院号"/]
      [@b.textfields names="wallet.inpatient.name;姓名"/]
      <input type="hidden" name="orderBy" value="wallet.inpatient.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="walletList" href="!search?orderBy=wallet.inpatient.code"/]</div>
</div>
[@b.foot/]
