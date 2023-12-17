[@b.head/]
  [@b.card class="card-info card-primary card-outline"]
    [#assign title]<i class="fa-solid fa-dollar-sign"></i>住院押金提醒[/#assign]
    [@b.card_header class="border-transparent" title=title  minimal="true" closeable="true"]
    <a href="${b.base}/deposit" target="_blank" class="float-right">更多...</a>
    [/@]
    [@b.card_body class="p-0"]
      <div class="table-responsive">
        <table class="table no-margin m-0  compact">
          <thead>
            <tr>
              <td>住院号</td>
              <td>姓名</td>
              <td>病区</td>
              <td>床位号</td>
              <td>金额</td>
            </tr>
          </thead>
          <tbody>
          [#list deposits as deposit]
          <tr>
            <td>${deposit.inpatient.code}</td>
            <td>${deposit.inpatient.name}</td>
            <td>${deposit.inpatient.ward.name}</td>
            <td>${deposit.inpatient.bedNo}</td>
            <td><span class="text-muted">${deposit.amount}</span></td>
          </tr>
          [/#list]
          </tbody>
        </table>
      </div>
    [/@]
  [/@]
[@b.foot/]
