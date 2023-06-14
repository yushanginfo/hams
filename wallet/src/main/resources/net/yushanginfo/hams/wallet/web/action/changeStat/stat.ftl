[@b.head/]

[#macro displayWardStat ward]
<p style="margin:0px" class="float-right">
  [@b.a href="!inpatient?ward.id="+ward.id+"&yearMonth="+Parameters['yearMonth'] target="_blank"]<i class="fa-solid fa-user"></i>打印月度汇总表[/@]
</p>
<table class="table table-sm table-striped table-bordered">
  <thead>
    <tr>
      <td>住院号</td>
      <td>床号</td>
      <td>姓名</td>
      <td>初期结余</td>
      <td>本期收入</td>
      <td>本期支出</td>
      <td>本期结余</td>
      <td>备注</td>
    </tr>
  </thead>
  <tbody>
  [#list wardStats.get(ward)?sort_by(['wallet','inpatient','bedNo']) as stat]
    <tr>
      <td>${stat.wallet.inpatient.code}</td>
      <td>${stat.wallet.inpatient.bedNo!}</td>
      <td>${stat.wallet.inpatient.name}</td>
      <td>${stat.startBalance}</td>
      <td>[#if stat.incomes.value>0]${stat.incomes}[/#if]</td>
      <td>[#if stat.expenses.value>0]${stat.expenses}[/#if]</td>
      <td>${stat.endBalance}</td>
      <td></td>
    </tr>
  [/#list]
  </tbody>
</table>
[/#macro]
<div class="container-fluid">
[#if wards?size>0]
[@b.tabs]
  [#list wards?sort_by('code') as ward]
  [@b.tab label=ward.name]
    [@displayWardStat ward/]
  [/@]
  [/#list]
  [@b.tab label="月末结算表"]
     [@b.div href="!ward?yearMonth="+Parameters['yearMonth'] /]
  [/@]
[/@]
[#else]
  <p> 无统计数据</p>
[/#if]
</div>
[@b.foot/]
