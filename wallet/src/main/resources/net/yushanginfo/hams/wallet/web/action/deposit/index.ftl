[#ftl]
[@b.head/]
[@b.toolbar title="住院押金管理"]
[/@]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="searchForm" action="!search" target="depositList" title="ui.searchForm" theme="search"]
      [@b.textfields names="deposit.inpatient.code;住院号"/]
      [@b.textfields names="deposit.inpatient.name;姓名"/]
      [@b.select name="deposit.inpatient.ward.id" label="病区" items=wards empty="..."/]
      [@b.select name="deposit.inpatient.status.id" label="状态" items=statues empty="..."/]
      <input type="hidden" name="orderBy" value="deposit.payAt desc"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="depositList" href="!search?orderBy=deposit.payAt desc"/]</div>
</div>
[@b.foot/]
