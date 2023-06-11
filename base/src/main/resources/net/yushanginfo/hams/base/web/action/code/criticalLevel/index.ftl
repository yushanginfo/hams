[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="criticalLevelSearchForm" action="!search" target="criticalLevellist" title="ui.searchForm" theme="search"]
      [@b.textfields names="criticalLevel.code;代码"/]
      [@b.textfields names="criticalLevel.name;名称"/]
      [@b.textfields names="criticalLevel.enName;英文名称"/]
      <input type="hidden" name="orderBy" value="criticalLevel.name"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="criticalLevellist" href="!search?orderBy=criticalLevel.code"/]
    </div>
  </div>
[@b.foot/]
