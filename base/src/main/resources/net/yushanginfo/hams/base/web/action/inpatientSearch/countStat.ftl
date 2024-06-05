[@b.head/]
  [@b.card class="card-info card-primary card-outline"]
    [#assign title]<i class="fa-solid fa-user"></i>实时人数统计[/#assign]
    [@b.card_header class="border-transparent" title=title]
    [/@]
    [@b.card_body class="p-0"]
      <table class="table-sm table">
        <tr>
          <td>当天办理出院人数</td>
          <td>${dischargingCount}</td>
        </tr>
        <tr>
          <td>当天请假人数</td>
          <td>${leavingCount}</td>
        </tr>
        <tr>
          <td>会客人数</td>
          <td>0</td>
        </tr>
        <tr>
          <td>远程探视人数</td>
          <td>0</td>
        </tr>
      </table>
    [/@]
  [/@]
[@b.foot/]
