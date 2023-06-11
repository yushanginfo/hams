[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="diseaseTypeSearchForm" action="!search" target="diseaseTypelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="diseaseType.code;代码"/]
      [@b.textfields names="diseaseType.name;名称"/]
      [@b.textfields names="diseaseType.enName;英文名称"/]
      <input type="hidden" name="orderBy" value="diseaseType.name"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="diseaseTypelist" href="!search?orderBy=diseaseType.code"/]
    </div>
  </div>
[@b.foot/]
