[#ftl]
[@b.head/]
[@b.toolbar title="病人信息"]
[/@]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="searchForm" action="!search" target="inpatientList" title="ui.searchForm" theme="search"]
      [@b.textfields names="inpatient.code;住院号"/]
      [@b.textfields names="inpatient.name;姓名"/]
      <input type="hidden" name="orderBy" value="inpatient.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="inpatientList" href="!search?orderBy=inpatient.code"/]</div>
</div>
[@b.foot/]
