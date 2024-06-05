[#ftl]
[@b.head/]
[@b.toolbar title="离院信息"]
[/@]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="searchForm" action="!search" target="applyList" title="ui.searchForm" theme="search"]
      [@b.textfields names="apply.inpatient.code;住院号,inpatient.name;姓名,inpatient.card;卡号"/]
      [@b.select name="apply.inpatient.ward.id" label="病区" items=wards empty="..."/]
      <input type="hidden" name="orderBy" value="apply.inpatient.leaveAt desc"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="applyList" href="!search?orderBy=apply.leaveAt desc"/]</div>
</div>
[@b.foot/]
