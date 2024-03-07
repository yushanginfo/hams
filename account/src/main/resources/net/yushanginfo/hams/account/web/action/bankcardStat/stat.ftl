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
  [#list wardStats.get(ward)?sort_by(['account','inpatient','bedNo']) as stat]
    <tr>
      <td>${stat.account.inpatient.code}</td>
      <td>${stat.account.inpatient.bedNo!}</td>
      <td>${stat.account.inpatient.name}</td>
      <td>${stat.startBalance}</td>
      <td>[#if stat.incomes.value!=0]${stat.incomes}[/#if]</td>
      <td>[#if stat.expenses.value!=0]${stat.expenses}[/#if]</td>
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
    <table class="table table-sm table-bordered">
      <thead>
        <tr>
          <td style="text-align:center;">月份</td>
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
  [/@]
[/@]
[#else]
  <p> 无统计数据</p>
[/#if]
</div>
[@b.foot/]
