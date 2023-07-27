[#ftl]
[@b.head/]
[#include "../commodity_nav.ftl"/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="commoditySearchForm" action="!search" target="commoditylist" title="ui.searchForm" theme="search"]
      [@b.textfields names="commodityBrand.code;代码,commodityBrand.name;名称"/]
      <input type="hidden" name="orderBy" value="commodityBrand.name"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="commoditylist" href="!search?orderBy=commodityBrand.name"/]
    </div>
  </div>
[@b.foot/]
