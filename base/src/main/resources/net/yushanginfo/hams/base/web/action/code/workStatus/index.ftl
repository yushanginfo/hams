[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="workStatusSearchForm" action="!search" target="workStatuslist" title="ui.searchForm" theme="search"]
      [@b.textfields names="workStatus.code;代码"/]
      [@b.textfields names="workStatus.name;名称"/]
      [@b.textfields names="workStatus.enName;英文名称"/]
      <input type="hidden" name="orderBy" value="workStatus.name"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="workStatuslist" href="!search?orderBy=workStatus.code"/]
    </div>
  </div>
[@b.foot/]
