[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="feeOriginsSearchForm" action="!search" target="feeOriginslist" title="ui.searchForm" theme="search"]
      [@b.textfields names="feeOrigin.code;代码"/]
      [@b.textfields names="feeOrigin.name;名称"/]
      [@b.textfields names="feeOrigin.enName;英文名称"/]
      <input type="hidden" name="orderBy" value="feeOrigin.name"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="feeOriginslist" href="!search?orderBy=feeOrigin.code"/]
    </div>
  </div>
[@b.foot/]
