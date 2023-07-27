[#ftl]
[@b.head/]
[@b.toolbar title="随心E购维护"/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="ebuyOrderSearchForm" action="!search" target="ebuyOrderlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="ebuyOrder.name;名称"/]
      [@b.textfield name="ebuyOrerYear" label="采购年份"/]
      <input type="hidden" name="orderBy" value="ebuyOrder.orderOn desc"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="ebuyOrderlist" href="!search?orderBy=ebuyOrder.orderOn desc"/]
    </div>
  </div>
[@b.foot/]
