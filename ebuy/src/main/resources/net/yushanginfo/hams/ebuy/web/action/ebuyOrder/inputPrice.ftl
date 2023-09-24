[@b.head/]
  <div class="container">
  [@b.toolbar title="${ebuyOrder.name} ${(ebuyOrder.ward.name)!} 购买品牌和价格登记"/]
   [@b.form name="requireForm" action="!savePrice"]
     <input name="ebuyOrder.id" type="hidden" value="${ebuyOrder.id}"/>
     <table class="table table-sm table-striped table-bordered">
       <tr><td>
             统一折扣((7.5=七五折)):<input id="unify_discount" name="unify_discount"
                    onchange="calc_by_discount(this)" value="[#if ebuyOrder.prices?first.discount<=0]10[#else]${ebuyOrder.prices?first.discount}[/#if]" style="width:40px">
         </td>
         <td>实付：<span id="total_payment"></span></td>
       </tr>
     </table>
     <table class="table table-sm table-striped table-bordered">
       <thead>
         <tr>
           <th width="5%">序号</th>
           <th width="10%">名称</th>
           <th width="6%">单位</th>
           <th width="10%">品牌</th>
           <th width="12%">单价</th>
           <th width="7%">折后价</th>

           <th width="5%" style="text-align:center;border-left: 2px solid rgb(60, 141, 188);">序号</th>
           <th width="10%">名称</th>
           <th width="6%">单位</th>
           <th width="10%">品牌</th>
           <th width="12%">单价</th>
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
              <input class="commodity" name="commodity_${price.commodity.id}_brand_${(price.brand.id)!}_unit_${price.unit.id}_price"
                     onchange="calc_by_price(this,true)" value="${price.price!}" placeholder="价格" style="width:75px" tabindex="${i+1}">
           </td>
           <td class="line_payment" data-amount="${price.amount}">[#if price.discount<=0]${price.price}[#else]${price.discountPrice!}[/#if]</td>

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
              <input class="commodity" name="commodity_${price.commodity.id}_brand_${(price.brand.id)!}_unit_${price.unit.id}_price"
                     onchange="calc_by_price(this,true)" value="${price.price!}" placeholder="价格" style="width:75px" tabindex="${half+i+1}">
           </td>
           <td class="line_payment" data-amount="${price.amount}">[#if price.discount<=0]${price.price}[#else]${price.discountPrice!}[/#if]</td>
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
    function reserve2(num){
      num=Math.round(num*100)/100;
      return num.toFixed(2);
    }
    function calc_by_price(elem,calcTotal){
      var p = jQuery(elem).val();
      var discount = jQuery("#unify_discount").val()
      if(p && discount){
        jQuery(elem).parent().next().html(reserve2(parseFloat(p) * parseFloat(discount) /10.0));
      }
      if(calcTotal)calc_total_payment();
    }
    function calc_by_discount(elem){
      var discount = jQuery(elem).val();
      jQuery(".commodity").each(function(i,e){calc_by_price(e,false)});
      calc_total_payment();
    }
    function calc_total_payment(){
      var total=0
      jQuery(".line_payment").each(function(i,e){
         if(jQuery(e).html()){
            total += parseFloat(jQuery(e).html()*parseInt(jQuery(e).data("amount")))
         }
      });
      jQuery("#total_payment").html(reserve2(total));
    }
    calc_total_payment();
  </script>
[@b.foot/]
