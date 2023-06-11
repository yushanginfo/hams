[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="nationSearchForm" action="!search" target="nationlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="nation.code;代码"/]
      [@b.textfields names="nation.name;名称"/]
      [@b.textfields names="nation.enName;英文名称"/]
      <input type="hidden" name="orderBy" value="nation.name"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="nationlist" href="!search?orderBy=nation.code"/]
    </div>
  </div>
[@b.foot/]
