[#ftl]
[@b.head/]
[@b.toolbar title="随心E购维护"/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="commodityUnitSearchForm" action="!search" target="commodityUnitlist" title="ui.searchForm" theme="search"]
       [@b.textfields names="commodityUnit.code;代码,commodityUnit.name;名称"/]
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="commodityUnitlist" href="!search"/]
    </div>
  </div>
[@b.foot/]
