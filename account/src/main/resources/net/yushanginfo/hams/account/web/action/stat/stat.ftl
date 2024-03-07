[@b.head/]

[#macro displayWardStat ward]
<div>
<p style="margin:0px" class="float-left">${Parameters['yearMonth']}月度统计</p>
<p style="margin:0px" class="float-right">
  [@b.a href="!inpatient?ward.id="+ward.id+"&yearMonth="+Parameters['yearMonth'] target="_blank"]<i class="fa-solid fa-user"></i>打印月度汇总表[/@]
</p>
</div>
<table class="table table-sm table-striped table-bordered">
  <thead>
    <tr>
      <td>序号</td>
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
  [#list wardStats.get(ward)?sort_by(['inpatient','bedNo']) as stat]
    <tr>
      <td>${stat_index+1}</td>
      <td>${stat.inpatient.code}</td>
      <td>${stat.inpatient.bedNo!}</td>
      <td>${stat.inpatient.name}</td>
      <td>${stat.startBalance}</td>
      <td>[#if stat.incomes.value != 0]${stat.incomes}[/#if]</td>
      <td>[#if stat.expenses.value != 0]${stat.expenses}[/#if]</td>
      <td>${stat.endBalance}</td>
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
<table class="table table-sm table-bordered">
  <thead>
    <tr>
      <td>月份</td>
      <td>部门</td>
      <td>期初结余</td>
      <td>本期收入</td>
      <td>本期支出</td>
      <td>本期结余</td>
      <td>备注</td>
    </tr>
  </thead>
  <tbody>
    [#list wards?sort_by('code') as ward]
    <tr>
      [#if ward_index==0]<td rowspan="${wards?size+1}" style="text-align:center;vertical-align: middle;">${yearMonth?string("MM")}月份</td>[/#if]
      <td>${ward.name}</td>
      <td>${startBalances.get(ward)!}</td>
      <td>${incomes.get(ward)!}</td>
      <td>${expenses.get(ward)!}</td>
      <td>${endBalances.get(ward)!}</td>
    </tr>
    [/#list]
    <tr>
      <td>合计</td>
      <td>${startBalances_sum}</td>
      <td>${incomes_sum}</td>
      <td>${expenses_sum}</td>
      <td>${endBalances_sum}</td>
    </tr>
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
     [@displayAllWards/]
  [/@]
[/@]
[#else]
  <p> 无统计数据</p>
[/#if]
</div>
[@b.foot/]
