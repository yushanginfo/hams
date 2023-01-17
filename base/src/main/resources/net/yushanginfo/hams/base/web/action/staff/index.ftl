[#ftl]
[@b.head/]
[@b.toolbar title="职工信息"]
[/@]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="searchForm" action="!search" target="stafflist" title="ui.searchForm" theme="search"]
      [@b.textfields names="staff.code;工号"/]
      [@b.textfields names="staff.name;姓名"/]
      [@b.select name="staff.department.id" label="部门" items=departments empty="..." style="width:100px"/]
      [@b.select style="width:100px" name="active" label="是否有效" items={"1":"是", "0":"否"} empty="..." /]
      <input type="hidden" name="orderBy" value="staff.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="stafflist" href="!search?orderBy=staff.code"/]</div>
</div>
[@b.foot/]
