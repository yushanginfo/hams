[#ftl]
[@b.head/]
[@b.toolbar title="银行卡入账信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
<table class="infoTable">
  <tr>
    <td class="title" width="20%">住院号</td>
    <td class="content">${income.bankcard.inpatient.code}</td>
  </tr>
  <tr>
    <td class="title" width="20%">姓名</td>
    <td class="content">${income.bankcard.inpatient.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">账户余额</td>
    <td class="content">${income.bankcard.balance}</td>
  </tr>
</table>

[@b.foot/]
