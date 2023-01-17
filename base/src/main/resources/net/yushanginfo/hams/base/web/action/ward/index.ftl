[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="wardSearchForm" action="!search" target="wardlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="ward.code;代码"/]
      [@b.textfields names="ward.name;名称"/]
      [@b.textfields names="ward.enName;英文名称"/]
      <input type="hidden" name="orderBy" value="ward.name"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="wardlist" href="!search?orderBy=ward.code"/]
    </div>
  </div>
[@b.foot/]
