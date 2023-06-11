[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="maritalStatusSearchForm" action="!search" target="maritalStatuslist" title="ui.searchForm" theme="search"]
      [@b.textfields names="maritalStatus.code;代码"/]
      [@b.textfields names="maritalStatus.name;名称"/]
      [@b.textfields names="maritalStatus.enName;英文名称"/]
      <input type="hidden" name="orderBy" value="maritalStatus.name"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="maritalStatuslist" href="!search?orderBy=maritalStatus.code"/]
    </div>
  </div>
[@b.foot/]
