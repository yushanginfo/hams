[@b.head/]
<div class="container-fluid">
<style>
  .number{
    text-align:right;
    mso-number-format:"#,##0.00"
  }
</style>
[#list wards?sort_by("code") as ward]
  [#if !wardStats.get(ward)??][#continue/][/#if]
  [#assign stats = wardStats.get(ward)]
  <h5 style="text-align:center">上海市民政第二精神卫生中心</h5>
  <h5 style="text-align:center">${year}年零用金病区汇总表</h5>
  <div class="row">
    <div class="col-4">病区:${ward.name}</div>
    <div class="col-4" style="text-align:center">月份:${month}月份</div>
    <div class="col-4" style="text-align:right">单位:元</div>
  </div>
  <table class="table table-hover table-sm">
    <thead>
       <th>住院号</th>
       <th>姓名</th>
       <th style="text-align:right;">期初结余</th>
       <th style="text-align:right;">本期收入</th>
       <th style="text-align:right;">本期支出</th>
       <th style="text-align:right;">本期结余</th>
       <th>备注</th>
    </thead>
    <tbody>
    [#list stats as stat]
     <tr>
      <td>${stat.wallet.inpatient.code}</td>
      <td>${stat.wallet.inpatient.name}</td>
      <td class="number">${stat.startBalance}</td>
      <td class="number">[#if stat.incomes.value!=0]${stat.incomes}[/#if]</td>
      <td class="number">[#if stat.expenses.value!=0]${stat.expenses}[/#if]</td>
      <td class="number">${stat.endBalance}</td>
      <td></td>
     </tr>
     [/#list]
    </tbody>
   </table>
[/#list]

</div>
[@b.foot/]
