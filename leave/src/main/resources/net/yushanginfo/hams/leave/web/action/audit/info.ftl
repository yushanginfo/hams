[@b.head/]
<style>
  .border_underline{
     border-bottom: 1px solid black;
  }
</style>
<div class="container">
  <div class="card card-info card-outline">
    <div class="card-header">
      <h3 class="card-title">出门单</h3>
    </div>
    <div class="card-body">
      <p>姓名：<span class="border_underline">${apply.inpatient.name}</span> <span class="border_underline">${apply.inpatient.ward.name}</span> ${apply.leaveAt?string("yyyy年MM月DD日")}</p>
      <p>理由：
          ${apply.reasons}
      </p>
      <p>家属：</p>
      <p>经办人：</p>
    </div>
  </div>
</div>
[@b.foot/]
