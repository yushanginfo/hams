[@b.head/]
<div class="container">
  <div class="row">
    <div class="col-2">
      <div class="card  card-info card-primary card-outline">
        <div class="card-header">
          <select name="year" id="stat_year">
            [#list years as y]
            <option value="${y}">${y}</option>
            [/#list]
          </select>
          月份列表
        </div>
        <div class="card-body" style="padding-top: 0px;">
           <table class="table table-hover table-sm">
            <tbody>
            [#list months as month]
             <tr>
              <td>[@b.a href="!stat" onclick="return statMonth('${month}')" target="month_info"]${month} 月[/@]</td>
             </tr>
             [/#list]
            </tbody>
          </table>
        </div>
      </div>
    </div>
    [@b.div href="!statMonth?year=${year}&month="+months?first class="col-10" id="month_info"/]
  </div>
</div>
<script>
   function statMonth(month){
     var year = document.getElementById("stat_year").value;
     bg.Go("${b.url('!statMonth')}?year="+year+"&month="+month,"month_info");
     return false;
   }
</script>
[@b.foot/]
