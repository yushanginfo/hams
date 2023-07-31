[@b.head/]
  <div class="container">
  [@b.toolbar title="${ebuyOrder.name} ${(ebuyOrder.ward.name)!} 购买品牌和价格登记"/]
   [@b.form name="requireForm" action="!savePrice"]
     <input name="ebuyOrder.id" type="hidden" value="${ebuyOrder.id}"/>
     <table class="table table-sm table-striped table-bordered">
       <thead>
         <tr>
           <th width="5%">序号</th>
           <th width="10%">名称</th>
           <th width="6%">单位</th>
           <th width="7%">品牌</th>
           <th width="15%">单价 折扣(7.5=七五折)</th>
           <th width="7%">折后价</th>

           <th width="5%" style="text-align:center;border-left: 2px solid rgb(60, 141, 188);">序号</th>
           <th width="10%">名称</th>
           <th width="6%">单位</th>
           <th width="7%">品牌</th>
           <th width="15%">单价 折扣(7.5=七五折)</th>
           <th width="7%">折后价</th>
         </tr>
       </thead>
       [#assign half=((ebuyOrder.prices?size+1)/2.0)?int /]
       [#if half<2][#assign half = 2/][/#if]
       [#assign cols = ebuyOrder.prices?sort_by('commodityDescription')?chunk(half)/]
       [#list  0..half-1 as i]
         <tr>
           [#assign price=cols[0][i]/]
           <td style="text-align:center">${i+1}</td>
           <td>${(price.commodity.name)}</td>
           <td>${price.amount}${price.unit.name}</td>
           <td>
              <input name="commodity_${price.commodity.id}_brand_${(price.brand.id)!}_unit_${price.unit.id}_brand"
                     value="${(price.brand.name)!}" style="width:80px"/>
           </td>
           <td>
              <input name="commodity_${price.commodity.id}_brand_${(price.brand.id)!}_unit_${price.unit.id}_price"
                     onchange="calc_by_price(this)" value="${price.price!}" placeholder="价格" style="width:75px" tabindex="${i+1}">
              <input name="commodity_${price.commodity.id}_brand_${(price.brand.id)!}_unit_${price.unit.id}_discount"
                     onchange="calc_by_discount(this)" value="[#if price.discount<=0]10[#else]${price.discount}[/#if]" style="width:40px">
           </td>
           <td>[#if price.discount<=0]${price.price}[#else]${price.discountPrice!}[/#if]</td>

           [#if cols[1][i]??]
           [#assign price=cols[1][i]/]
           <td style="text-align:center;border-left: 2px solid rgb(60, 141, 188);">${half+i+1}</td>
           <td>${(price.commodity.name)}</td>
           <td>${price.amount}${price.unit.name}</td>
           <td>
              <input name="commodity_${price.commodity.id}_brand_${(price.brand.id)!}_unit_${price.unit.id}_brand"
                     value="${(price.brand.name)!}" style="width:80px"/>
           </td>
           <td>
              <input name="commodity_${price.commodity.id}_brand_${(price.brand.id)!}_unit_${price.unit.id}_price"
                     onchange="calc_by_price(this)" value="${price.price!}" placeholder="价格" style="width:75px" tabindex="${half+i+1}">
              <input name="commodity_${price.commodity.id}_brand_${(price.brand.id)!}_unit_${price.unit.id}_discount"
                     onchange="calc_by_discount(this)" value="[#if price.discount<=0]10[#else]${price.discount}[/#if]" style="width:40px">
           </td>
           <td>[#if price.discount<=0]${price.price}[#else]${price.discountPrice!}[/#if]</td>
           [#else]
           <td style="text-align:center;border-left: 2px solid rgb(60, 141, 188);">${half+i+1}</td><td></td><td></td><td></td><td></td><td></td>
           [/#if]
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
