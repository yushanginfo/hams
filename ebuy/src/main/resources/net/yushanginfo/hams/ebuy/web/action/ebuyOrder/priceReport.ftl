[@b.head/]
  <div class="container">
    [@b.toolbar title="${ebuyOrder.name} 物品汇总清单"/]
    <h6 style="text-align: center;">上海民政第二精神卫生中心<br>随心E购物品汇总清单</h6>
    <div class="row">
      <div class="col-4">病区：${ebuyOrder.ward.name}</div>
      <div class="col-4">时间：${ebuyOrder.orderOn?string("yyyy年MM月dd日")}</div>
      <div class="col-4" style="text-align:right">审核人：___________</div>
    </div>
     [@b.grid items=ebuyOrder.prices?sort_by('commodityDescription') var="price" class="border-1px border-blue" sortable="false"]
       [@b.row]
         [@b.col title="编号" width="10%"]${price_index+1}[/@]
         [@b.col title="名称"]${(price.brand.name)!}${(price.commodity.name)}[/@]
         [@b.col title="总数" width="15%" property="amount"]${price.amount}${price.unit.name}[/@]
         [@b.col title="单价" width="20%" property="price"/]
         [@b.col title="应收" width="20%" property="payable"/]
         [@b.col title="实收" width="20%" property="payment"/]
       [/@]
     [/@]
    <div class="row">
      <div class="col-8"></div>
      <div class="col-4" style="text-align:right">总计：${ebuyOrder.payment}</div>
    </div>
  </div>
[@b.foot/]
