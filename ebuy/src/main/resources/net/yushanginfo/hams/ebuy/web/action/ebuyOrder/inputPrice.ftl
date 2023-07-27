[@b.head/]
  <div class="container">
  [@b.toolbar title="${ebuyOrder.name} 价格登记"/]
   [@b.form name="requireForm" action="!savePrice"]
     <input name="ebuyOrder.id" type="hidden" value="${ebuyOrder.id}"/>
     <table class="table table-sm table-striped">
       <thead>
         <tr>
           <td width="10%">品牌</td>
           <td>名称</td>
           <td width="10%">单位</td>
           <td width="20%">单价(折扣  八五折，输入8.5)</td>
           <td width="15%">折后价</td>
         </tr>
       </thead>
       [#list ebuyOrder.prices?sort_by('commodityDescription') as price]
         <tr>
           <td>${(price.brand.name)!}</td>
           <td>${(price.commodity.name)}</td>
           <td>${price.unit.name}</td>
           <td>
              <input name="commodity_${price.commodity.id}_brand_${(price.brand.id)!}_unit_${price.unit.id}_price"  onchange="calc_by_price(this)" value="${price.price!}" placeholder="价格" style="width:100px">
              <input name="commodity_${price.commodity.id}_brand_${(price.brand.id)!}_unit_${price.unit.id}_discount" onchange="calc_by_discount(this)" value="[#if price.discount<=0]10[#else]${price.discount}[/#if]" style="width:60px">
           </td>
           <td>${(price.actual)!}</td>
         </tr>
       [/#list]
     </table>
     <div style="text-align:center">
       [@b.submit value="提交" theme="list"/]
     </div>
   [/@]
  </div>
  <script>
    function calc_by_price(elem){
      var p = jQuery(elem).val();
      var discount = jQuery(elem).next().val()
      if(p && discount){
        jQuery(elem).parent().next().html(parseFloat(p) * parseFloat(discount) /10.0)
      }
    }
    function calc_by_discount(elem){
      var discount = jQuery(elem).val();
      var p = jQuery(elem).prev().val()
      if(p && discount){
        jQuery(elem).parent().next().html(parseFloat(p) * parseFloat(discount) /10.0)
      }
    }
  </script>
[@b.foot/]
