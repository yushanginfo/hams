[@b.head/]
  <div class="container">
    [#list wards as ward]
      <h6 style="text-align: center;">上海民政第二精神卫生中心<br>随心E购买物品登记表</h6>
      <div class="row">
        <div class="col-6">病区：${ward.name}</div>
        <div class="col-6" style="text-align:right">时间：${ebuyOrder.orderOn?string("yyyy年MM月dd日")}</div>
      </div>
      [#assign inpatientOrderLines= wardInpatientOrderLines.get(ward)/]
      <div class="grid" style="border:0.5px solid #006CB2">
      <div class="gridcontent">
      <table class="gridtable">
        <thead class="gridhead">
          <tr>
            <th width="8%">序号</th>
            <th width="8%">床号</th>
            <th width="12%">姓名</th>
            <th>物品</th>
            <th>数量</th>
            <th>单位</th>
            <th>品牌</th>
            <th>单价（元）</th>
            <th>购买金额（元）</th>
            <th>签收</th>
            <th>应收（元）</th>
            <th>实收（元）</th>
          </tr>
        </thead>
        <tbody>
          [#list inpatientOrderLines?keys?sort_by('bedNo') as inpatient]
            [#assign iLines = inpatientOrderLines.get(inpatient)/]
            [#list iLines as line]
              <tr>
                <td>${inpatient_index+1}</td>
                <td>${inpatient.bedNo}</td>
                <td>${inpatient.name}</td>
                <td>${(line.commodity.name)!}</td>
                <td>${line.amount}</td>
                <td>${(line.unit.name)!}</td>
                <td>${(line.brand.name)!}</td>
                <td>${(line.price)!}</td>
                <td>${(line.payable)!}</td>
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
      </div>
      </div>
      [#if ward_has_next]<div style="page-break-after: always;"></div> [/#if]
    [/#list]
  </div>
[@b.foot/]
