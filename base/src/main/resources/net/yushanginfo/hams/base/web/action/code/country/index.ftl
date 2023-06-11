[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="countrySearchForm" action="!search" target="countrylist" title="ui.searchForm" theme="search"]
      [@b.textfields names="country.code;代码"/]
      [@b.textfields names="country.name;名称"/]
      [@b.textfields names="country.enName;英文名称"/]
      <input type="hidden" name="orderBy" value="country.name"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="countrylist" href="!search?orderBy=country.code"/]
    </div>
  </div>
[@b.foot/]
