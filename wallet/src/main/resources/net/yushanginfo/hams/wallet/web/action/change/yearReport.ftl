[@b.head/]
<div class="container-fluid text-sm">
  <h6 style="text-align: center;">上海民政第二精神卫生中心<br>${year}年度个人零用金收支明细表</h6>
  <div class="row">
    <div class="col-4">姓名：${inpatient.name}</div>
    <div class="col-4" style="text-align:center">病区：${inpatient.ward.name}</div>
    <div class="col-4" style="text-align:right">床位号：${inpatient.bedNo}</div>
  </div>
  [@b.grid items=logs var="log" style="border:0.5px solid #006CB2" sortable="false"]
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
</div>
[@b.foot/]
