[#ftl]
[@b.head/]
[@b.toolbar title="病人信息"]
[/@]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="searchForm" action="!search" target="inpatientList" title="ui.searchForm" theme="search"]
      [@b.textfields names="inpatient.code;住院号,inpatient.name;姓名,inpatient.card;卡号"/]
      [@b.select name="inpatient.ward.id" label="病区"   items=wards empty="..."/]
      [@b.select name="inpatient.feeOrigin.id" label="费用类别" items=feeOrigins/]
      [@b.select name="inpatient.status.id" label="状态" items=statuses value=statuses?first/]
      <input type="hidden" name="orderBy" value="inpatient.beginAt desc"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="inpatientList" href="!search?orderBy=inpatient.beginAt desc&inpatient.status.id="+statuses?first.id/]</div>
</div>
[@b.foot/]
