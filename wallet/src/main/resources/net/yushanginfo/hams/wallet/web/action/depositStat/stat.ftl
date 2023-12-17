[@b.head/]
<style>
  .yuan{
    text-align:right;
  }
</style>
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

[#macro displayWardStat ward]
<p style="margin:0px" class="float-left">${Parameters['yearMonth']}月度统计</p>
<table class="table table-sm table-striped table-bordered">
  <thead>
    <tr>
      <td>序号</td>
      <td>住院号</td>
      <td>床号</td>
      <td>姓名</td>
      <td class="yuan">初期结余</td>
      <td class="yuan">本期收入</td>
      <td class="yuan">本期支出</td>
      <td class="yuan">本期结余</td>
      <td>备注</td>
    </tr>
  </thead>
  <tbody>
  [#list wardStats.get(ward)?sort_by(['inpatient','bedNo']) as stat]
    <tr>
      <td>${stat_index+1}</td>
      <td>${stat.inpatient.code}</td>
      <td>${stat.inpatient.bedNo!}</td>
      <td>${stat.inpatient.name}</td>
      <td class="yuan">${stat.startBalance}</td>
      <td class="yuan">[#if stat.incomes.value != 0]${stat.incomes}[/#if]</td>
      <td class="yuan">[#if stat.expenses.value != 0]${stat.expenses}[/#if]</td>
      <td class="yuan">${stat.endBalance}</td>
      <td></td>
    </tr>
  [/#list]
  </tbody>
</table>
[/#macro]

[@b.foot/]
