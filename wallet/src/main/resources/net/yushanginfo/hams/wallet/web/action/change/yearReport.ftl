[@b.head/]

<div class="container-fluid text-sm">
  <div class="notprint">
    [@b.form name="reportForm" action="!yearReport" theme="list" title="个人零用金收支明细表"]
      [#list inpatients as i]
      <input name="inpatient.id" type="hidden" value="${i.id}"/>
      [/#list]
      [@b.textfield name="year" label="年份" value=year/]
      [@b.formfoot]
        [@b.submit value="统计"/]
      [/@]
    [/@]
  </div>
  [#list inpatients?sort_by('bedNo') as inpatient]
  [#assign yearLogs = inpatientLogs.get(inpatient)?sort_by('payAt')/]
  <h6 style="text-align: center;">上海民政第二精神卫生中心<br>${year}年度个人零用金收支明细表</h6>
  <div class="row">
    <div class="col-2">姓名：${inpatient.name}</div>
    <div class="col-2" style="text-align:center">病区：${inpatient.ward.name}</div>
    <div class="col-2" style="text-align:center">床位号：${inpatient.bedNo}</div>
    <div class="col-3" style="text-align:center">期初余额：[#if yearLogs?size>0]${yearLogs?first.originBalance}[/#if]</div>
    <div class="col-3" style="text-align:right">期末余额：[#if yearLogs?size>0]${yearLogs?last.balance}[/#if]</div>
  </div>
  [@b.grid items=yearLogs var="log" style="border:0.5px solid #006CB2" sortable="false"]
    [@b.row]
     [@b.col property="payAt" title="日期"]${log.payAt?string('yyyy-MM-dd')}[/@]
     [@b.col title="存入金额"]
       [#if log.channel??]${log.amount}[#else]&nbsp;[/#if]
     [/@]
     [@b.col title="支出金额"]
       [#if !log.channel??]${log.amount}[#else]&nbsp;[/#if]
     [/@]
     [@b.col property="balance" title="结余"/]
     [@b.col title="备注"]
     [/@]
    [/@]
  [/@]
  [#if inpatient_has_next]<div style="page-break-after: always;"></div>[/#if]
  [/#list]
</div>
[@b.foot/]
