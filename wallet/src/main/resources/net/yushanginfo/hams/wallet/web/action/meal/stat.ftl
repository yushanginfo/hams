[@b.head/]
<div class="container-fluid">
  <div class="row">
    <div class="col-2">
      <div class="card  card-info card-primary card-outline">
        <div class="card-header">
          月份列表
        </div>
        <div class="card-body" style="padding-top: 0px;">
           <table class="table table-hover table-sm">
            <tbody>
            [#list months as month]
             <tr>
              <td>[@b.a href="!statMonth?id="+month target="month_info"]${month} 月[/@]</td>
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
[@b.foot/]
