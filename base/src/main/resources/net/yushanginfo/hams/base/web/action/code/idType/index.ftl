[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="idTypeSearchForm" action="!search" target="idTypelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="idType.code;代码"/]
      [@b.textfields names="idType.name;名称"/]
      [@b.textfields names="idType.enName;英文名称"/]
      <input type="hidden" name="orderBy" value="idType.name"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="idTypelist" href="!search?orderBy=idType.code"/]
    </div>
  </div>
[@b.foot/]
