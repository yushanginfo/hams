[@b.head/]
 [#if walletType==1]
  [@b.card class="card-info card-primary card-outline"]
    [#assign title]<i class="fa-solid fa-dollar-sign"></i>伙食费不足提醒(${wallets.totalItems}人)[/#assign]
    [@b.card_header class="border-transparent" title=title  minimal="true" closeable="true"]
    <a href="${b.base}/meal" target="_blank" class="float-right">更多...</a>
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
              <td>余额</td>
            </tr>
          </thead>
          <tbody>
          [#list wallets as wallet]
          <tr>
            <td>${wallet.inpatient.code}</td>
            <td>${wallet.inpatient.name}</td>
            <td>${wallet.inpatient.ward.name}</td>
            <td>${wallet.inpatient.bedNo}</td>
            <td><span class="text-muted">${wallet.balance}</span></td>
          </tr>
          [/#list]
          </tbody>
        </table>
      </div>
    [/@]
  [/@]
  [#else]
    [@b.card class="card-info card-primary card-outline"]
      [#assign title]<i class="fa-solid fa-dollar-sign"></i>零用金不足提醒(${wallets.totalItems}人)[/#assign]
      [@b.card_header class="border-transparent" title=title  minimal="true" closeable="true"]
      <a href="${b.base}/change" target="_blank" class="float-right">更多...</a>
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
                <td>余额</td>
              </tr>
            </thead>
            <tbody>
            [#list wallets as wallet]
            <tr>
              <td>${wallet.inpatient.code}</td>
              <td>${wallet.inpatient.name}</td>
              <td>${wallet.inpatient.ward.name}</td>
              <td>${wallet.inpatient.bedNo}</td>
              <td><span class="text-muted">${wallet.balance}</span></td>
            </tr>
            [/#list]
            </tbody>
          </table>
        </div>
      [/@]
    [/@]
  [/#if]
[@b.foot/]
