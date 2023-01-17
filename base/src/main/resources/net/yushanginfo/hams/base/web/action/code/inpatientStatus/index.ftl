[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="inpatientStatusSearchForm" action="!search" target="inpatientStatuslist" title="ui.searchForm" theme="search"]
      [@b.textfields names="inpatientStatus.code;代码"/]
      [@b.textfields names="inpatientStatus.name;名称"/]
      [@b.textfields names="inpatientStatus.enName;英文名称"/]
      <input type="hidden" name="orderBy" value="inpatientStatus.name"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="inpatientStatuslist" href="!search?orderBy=inpatientStatus.code"/]
    </div>
  </div>
[@b.foot/]
