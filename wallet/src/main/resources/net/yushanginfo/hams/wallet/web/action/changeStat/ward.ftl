<table class="table table-sm table-striped table-bordered">
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
      [#if ward_index==0]<td rowspan="${wards?size+1}">${yearMonth?string("MM")}月份</td>[/#if]
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
