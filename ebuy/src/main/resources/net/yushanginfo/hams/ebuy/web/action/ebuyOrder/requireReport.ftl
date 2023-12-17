[@b.head/]
  <div class="container">
    [#list wards as ward]
      <h6 style="text-align: center;">上海民政第二精神卫生中心<br>随心E购买物品登记表</h6>
      <div class="row">
        <div class="col-6">病区：${ward.name}</div>
        <div class="col-6" style="text-align:right">时间：${ebuyOrder.orderOn?string("yyyy年MM月dd日")}</div>
      </div>
      [#assign inpatientOrderLines= wardInpatientOrderLines.get(ward)/]
      <table class="grid-table">
        <thead class="grid-head">
          <tr>
            <th width="50px">序号</th>
            <th width="50px">床号</th>
            <th width="100px">姓名</th>
            <th>物品</th>
            <th width="50px">数量</th>
            <th width="50px">单位</th>
            <th width="80px">品牌</th>
            <th width="100px">单价（元）</th>
            <th width="110px">购买金额（元）</th>
            <th width="110px">单项实收（元）</th>
            <th>签收</th>
            <th width="100px">应收（元）</th>
            <th width="100px">实收（元）</th>
          </tr>
        </thead>
        <tbody>
          [#list inpatientOrderLines?keys?sort_by('bedNo') as inpatient]
            [#assign iLines = inpatientOrderLines.get(inpatient)/]
            [#list iLines as line]
              <tr>
              [#if line_index==0]
                <td rowspan="${iLines?size}">${inpatient_index+1}</td>
                <td rowspan="${iLines?size}">${inpatient.bedNo}</td>
                <td rowspan="${iLines?size}">${inpatient.name}</td>
              [/#if]
                <td>${(line.commodity.name)!}</td>
                <td>${line.amount}</td>
                <td>${(line.unit.name)!}</td>
                <td>${(line.brand.name)!}</td>
                <td>${(line.price)!}</td>
                <td>${(line.payable)!}</td>
                <td>${(line.payment)!}</td>
                [#if line_index==0]
                <td rowspan="${iLines?size}"></td>
                <td rowspan="${iLines?size}">
                  [#assign payable_i=0/]
                  [#assign payment_i=0/]
                  [#list iLines as l]
                    [#assign payable_i=payable_i+(l.payable.value)!0/]
                    [#assign payment_i=payment_i+(l.payment.value)!0/]
                  [/#list]
                  ${payable_i/100.0}
                </td>
                <td rowspan="${iLines?size}">${payment_i/100.0}</td>
                [/#if]
              </tr>
            [/#list]
          [/#list]
        </tbody>
      </table>
      <div class="row">
        <div class="col-6"></div>
        <div class="col-6" style="text-align:right">总计：${ebuyOrder.payment!}</div>
      </div>
      [#if ward_has_next]<div style="page-break-after: always;"></div> [/#if]
    [/#list]
  </div>
[@b.foot/]
