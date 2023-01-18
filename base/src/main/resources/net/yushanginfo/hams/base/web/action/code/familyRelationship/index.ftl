[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="familyRelationshipSearchForm" action="!search" target="familyRelationshiplist" title="ui.searchForm" theme="search"]
      [@b.textfields names="familyRelationship.code;代码"/]
      [@b.textfields names="familyRelationship.name;名称"/]
      [@b.textfields names="familyRelationship.enName;英文名称"/]
      <input type="hidden" name="orderBy" value="familyRelationship.name"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="familyRelationshiplist" href="!search?orderBy=familyRelationship.code"/]
    </div>
  </div>
[@b.foot/]
