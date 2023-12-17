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
