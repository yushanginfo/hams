[#ftl]
[@b.head/]
[@b.toolbar title="养护补贴入账信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
<table class="infoTable">
  <tr>
    <td class="title" width="20%">住院号</td>
    <td class="content">${income.account.inpatient.code}</td>
  </tr>
  <tr>
    <td class="title" width="20%">姓名</td>
    <td class="content">${income.account.inpatient.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">入账时间</td>
    <td class="content">${income.payAt?string('yyyy-MM-dd HH:mm')}</td>
  </tr>
  <tr>
    <td class="title" width="20%">余额</td>
    <td class="content">${income.amount}</td>
  </tr>
</table>

[@b.foot/]
