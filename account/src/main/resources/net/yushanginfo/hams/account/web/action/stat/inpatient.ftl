[@b.head/]
<div class="container-fluid text-sm">
  <h6 style="text-align: center;">上海民政第二精神卫生中心<br>${yearMonth?string('yyyy')}年度${accountName}病区汇总表</h6>
  <div class="row">
    <div class="col-4">病区：${ward.name}</div>
    <div class="col-4" style="text-align:center">月份：${yearMonth?string('MM')}月份</div>
    <div class="col-4" style="text-align:right">单位：元</div>
  </div>
  [@b.grid items=stats var="stat" style="border:0.5px solid #006CB2" sortable="false"]
    [@b.row]
     [@b.col property="inpatient.code" title="住院号"/]
     [@b.col property="inpatient.bedNo" title="床号"/]
     [@b.col property="inpatient.name" title="姓名"/]
     [@b.col property="startBalance" title="期初结余"/]
     [@b.col property="incomes" title="本期收入"]
       [#if stat.incomes.value != 0]${stat.incomes}[/#if]
     [/@]
     [@b.col property="expenses" title="本期支出"]
       [#if stat.expenses.value != 0]${stat.expenses}[/#if]
     [/@]
     [@b.col property="endBalance" title="本期结余"/]
     [@b.col title="备注"/]
    [/@]
  [/@]
</div>
[@b.foot/]
