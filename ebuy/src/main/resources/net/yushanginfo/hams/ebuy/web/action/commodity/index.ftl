[#ftl]
[@b.head/]
[#include "../commodity_nav.ftl"/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="commoditySearchForm" action="!search" target="commoditylist" title="ui.searchForm" theme="search"]
      [@b.textfields names="commodity.code;代码,commodity.name;名称"/]
      <input type="hidden" name="orderBy" value="commodity.name"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="commoditylist" /] [#-- href="!search?orderBy=commodity.name" --]
    </div>
  </div>
  <script>
  $(document).ready(function() {
        bg.form.submit("commoditySearchForm");
      });
  </script>
[@b.foot/]
