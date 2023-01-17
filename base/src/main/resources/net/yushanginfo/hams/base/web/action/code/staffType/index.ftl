[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="staffTypeSearchForm" action="!search" target="staffTypelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="staffType.code;代码"/]
      [@b.textfields names="staffType.name;名称"/]
      [@b.textfields names="staffType.enName;英文名称"/]
      <input type="hidden" name="orderBy" value="staffType.name"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="staffTypelist" href="!search?orderBy=staffType.code"/]
    </div>
  </div>
[@b.foot/]
