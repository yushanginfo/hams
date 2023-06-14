[@b.head/]
<div class="container-fluid text-sm">
  [@b.toolbar title="伙食费月度统计"/]
  <div class="row">
    <div class="col-2">
      <div class="card  card-info card-primary card-outline">
        <div class="card-header">
          月份列表
        </div>
        <div class="card-body" style="padding-top: 0px;">
           <table class="table table-hover table-sm">
            <tbody>
            [#list yearMonths as ym]
             <tr>
              <td>[@b.a href="!stat?yearMonth="+ym?string('yyyy-MM') target="month_info"]${ym?string('yyyy-MM')}[/@]</td>
             </tr>
             [/#list]
            </tbody>
          </table>
        </div>
      </div>
    </div>
    [@b.div href="!stat?yearMonth=${yearMonths?first?string('yyyy-MM')}" class="col-10" id="month_info"/]
  </div>
</div>
[@b.foot/]
