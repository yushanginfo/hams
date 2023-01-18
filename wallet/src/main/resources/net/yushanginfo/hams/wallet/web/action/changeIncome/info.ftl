[#ftl]
[@b.head/]
[@b.toolbar title="零用金入账信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
<table class="infoTable">
  <tr>
    <td class="title" width="20%">住院号</td>
    <td class="content">${wallet.inpatient.code}</td>
  </tr>
  <tr>
    <td class="title" width="20%">姓名</td>
    <td class="content">${wallet.inpatient.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">账户余额</td>
    <td class="content">${wallet.balance}</td>
  </tr>
</table>

[@b.foot/]
