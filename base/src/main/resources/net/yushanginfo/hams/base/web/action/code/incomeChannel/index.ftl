[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="incomeChannelSearchForm" action="!search" target="incomeChannellist" title="ui.searchForm" theme="search"]
      [@b.textfields names="incomeChannel.code;代码"/]
      [@b.textfields names="incomeChannel.name;名称"/]
      [@b.textfields names="incomeChannel.enName;英文名称"/]
      <input type="hidden" name="orderBy" value="incomeChannel.name"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="incomeChannellist" href="!search?orderBy=incomeChannel.code"/]
    </div>
  </div>
[@b.foot/]
