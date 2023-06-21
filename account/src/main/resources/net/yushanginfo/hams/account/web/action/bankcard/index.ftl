[#ftl]
[@b.head/]
[#include "../bankcard_nav.ftl"/]
[@b.toolbar title="银行卡余额管理"]
  bar.addItem("每期汇总",function() {
    bg.form.submit(document.searchForm, "${b.url("bankcard-stat!index")}", "_blank");
  });
[/@]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="searchForm" action="!search" target="bankcardList" title="ui.searchForm" theme="search"]
      [@b.textfields names="bankcard.inpatient.code;住院号"/]
      [@b.textfields names="bankcard.inpatient.name;姓名"/]
      [@b.select name="bankcard.inpatient.ward.id" label="病区" items=wards empty="..."/]
      <input type="hidden" name="orderBy" value="bankcard.inpatient.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="bankcardList" href="!search?orderBy=bankcard.inpatient.code"/]</div>
</div>
[@b.foot/]
