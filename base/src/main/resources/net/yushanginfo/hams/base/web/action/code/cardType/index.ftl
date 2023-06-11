[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="cardTypeSearchForm" action="!search" target="cardTypelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="cardType.code;代码"/]
      [@b.textfields names="cardType.name;名称"/]
      [@b.textfields names="cardType.enName;英文名称"/]
      <input type="hidden" name="orderBy" value="cardType.name"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="cardTypelist" href="!search?orderBy=cardType.code"/]
    </div>
  </div>
[@b.foot/]
