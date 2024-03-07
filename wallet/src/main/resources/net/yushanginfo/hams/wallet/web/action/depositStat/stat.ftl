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
     [@displayAllWards /]
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
    <tr>
      <td colspan="4">合计</td>
      <td class="yuan">${startBalances.get(ward)!}</td>
      <td class="yuan">${incomes.get(ward)!}</td>
      <td class="yuan">${expenses.get(ward)!}</td>
      <td class="yuan">${endBalances.get(ward)!}</td>
      <td></td>
    </tr>
  </tbody>
</table>
[/#macro]

[#macro displayAllWards]
<style>
  .yuan{
    text-align:right;
  }
</style>
<table class="table table-sm table-striped table-bordered">
  <thead>
    <tr>
      <td>月份</td>
      <td>部门</td>
      <td class="yuan">期初结余</td>
      <td class="yuan">本期收入</td>
      <td class="yuan">本期支出</td>
      <td class="yuan">本期结余</td>
      <td>备注</td>
    </tr>
  </thead>
  <tbody>
    [#list wards?sort_by('code') as ward]
    <tr>
      [#if ward_index==0]<td style="vertical-align: middle;text-align: center;" rowspan="${wards?size+1}">${yearMonth?string("MM")}月份</td>[/#if]
      <td>${ward.name}</td>
      <td class="yuan">${startBalances.get(ward)!}</td>
      <td class="yuan">${incomes.get(ward)!}</td>
      <td class="yuan">${expenses.get(ward)!}</td>
      <td class="yuan">${endBalances.get(ward)!}</td>
    </tr>
    [/#list]
    <tr>
      <td>合计</td>
      <td class="yuan">${startBalances_sum}</td>
      <td class="yuan">${incomes_sum}</td>
      <td class="yuan">${expenses_sum}</td>
      <td class="yuan">${endBalances_sum}</td>
    </tr>
  </tbody>
</table>
[/#macro]
[@b.foot/]
