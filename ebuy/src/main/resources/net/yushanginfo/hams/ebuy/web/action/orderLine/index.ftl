[#ftl]
[@b.head/]
[@b.toolbar title="随心E购订购明细"/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="orderLineSearchForm" action="!search" target="orderLinelist" title="ui.searchForm" theme="search"]
      [@b.select items=orders name="orderLine.order.id" required="true" label="订购批次"/]
      [@b.textfields names="orderLine.inpatient.code;住院号"/]
      [@b.textfields names="orderLine.inpatient.name;姓名"/]
      [@b.textfields names="orderLine.inpatient.bedNo;床位号"/]
      [@b.select name="orderLine.inpatient.ward.id" label="病区" items=wards empty="..."/]
      [@b.textfield name="orderLine.commodity.name" label="物品"/]
      <input type="hidden" name="orderBy" value="orderLine.inpatient.bedNo"/>
    [/@]
    </div>
    <div class="search-list">
      [@b.div id="orderLinelist"/]
    </div>
  </div>
  <script>
    $(document).ready(function() {
        bg.form.submit("orderLineSearchForm");
    });
  </script>
[@b.foot/]
