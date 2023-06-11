[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="relationshipSearchForm" action="!search" target="relationshiplist" title="ui.searchForm" theme="search"]
      [@b.textfields names="relationship.code;代码"/]
      [@b.textfields names="relationship.name;名称"/]
      [@b.textfields names="relationship.enName;英文名称"/]
      <input type="hidden" name="orderBy" value="relationship.name"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="relationshiplist" href="!search?orderBy=relationship.code"/]
    </div>
  </div>
[@b.foot/]
