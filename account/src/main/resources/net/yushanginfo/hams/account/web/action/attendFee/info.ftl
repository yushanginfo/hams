[#ftl]
[@b.head/]
[@b.toolbar title="陪护费信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
<table class="infoTable">
  <tr>
    <td class="title" width="20%">住院号</td>
    <td class="content">${attendFee.inpatient.code}</td>
  </tr>
  <tr>
    <td class="title" width="20%">姓名</td>
    <td class="content">${attendFee.inpatient.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">起始年月</td>
    <td class="content">${attendFee.createdOn?string("yyyy-MM")}</td>
  </tr>
  <tr>
    <td class="title" width="20%">起始余额</td>
    <td class="content">${attendFee.initBalance}</td>
  </tr>
  <tr>
    <td class="title" width="20%">账户余额</td>
    <td class="content">${attendFee.balance}</td>
  </tr>
</table>

[@b.foot/]
