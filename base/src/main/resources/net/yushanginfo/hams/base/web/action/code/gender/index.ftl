[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="genderSearchForm" action="!search" target="genderlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="gender.code;代码"/]
      [@b.textfields names="gender.name;名称"/]
      [@b.textfields names="gender.enName;英文名称"/]
      <input type="hidden" name="orderBy" value="gender.name"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="genderlist" href="!search?orderBy=gender.code"/]
    </div>
  </div>
[@b.foot/]
