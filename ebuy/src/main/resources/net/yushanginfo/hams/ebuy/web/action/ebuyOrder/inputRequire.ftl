[@b.head/]
  <div class="container">
  [@b.toolbar title="${ebuyOrder.name} 采购登记"/]
   [@b.form name="requireForm" action="!saveRequire"]
     <input name="ebuyOrder.id" type="hidden" value="${ebuyOrder.id}"/>
   <table class="table">
     <thead>
       <tr>
         <td>病区</td>
         <td>床号</td>
         <td>姓名</td>
         <td>日用品 品牌 单位 数量</td>
       </tr>
     </thead>
     [#list inpatients as inpatient]
       <tr>
         <td>${inpatient.ward.name}</td>
         <td>${inpatient.bedNo}</td>
         <td>${inpatient.name}</td>
         <td>

           [#if orderLines.get(inpatient)??]
             [#list orderLines.get(inpatient) as line]
               <div id="require_line_${line.id}" style="display:inline-block">
               [@b.combobox name="commodity_line_${line.id}" id="commodity_line_${line.id}" href="!commodities.json?q={term}" label="" value=line.commodity! width="200px"/]
               [@b.combobox name="brand_line_${line.id}" id="brand_line_${line.id}" href="!brands.json?q={term}" label="" value=line.brand!  width="120px"/]
               [@b.combobox name="unit_line_${line.id}" id="unit_line_${line.id}" href="!units.json?q={term}" label="" value=line.unit width="80px"/]
               <input type="number" name="amount_line_${line.id}" id="amount_line_${line.id}" value="${line.amount!1}" style="width:100px"/>
               <button class="btn btn-sm btn-outline-primary" onclick="return removeLine(this);"><i class="fas fa-minus"></i></button>
               </div>
             [/#list]
           [#else]
               <div id="require_${inpatient.id}" style="display:inline-block">
               [@b.combobox name="commodity_${inpatient.id}" href="!commodities.json?q={term}" label=""  width="200px"/]
               [@b.combobox name="brand_${inpatient.id}" href="!brands.json?q={term}" label=""  width="120px"/]
               [@b.combobox name="unit_${inpatient.id}" href="!units.json?q={term}" label=""   width="80px"/]
               <input type="number" name="amount_${inpatient.id}" value="" style="width:100px"/>
               <button class="btn btn-sm btn-outline-primary" onclick="return removeRequire(this,'${inpatient.id}');"><i class="fas fa-minus"></i></button>
               </div>
           [/#if]
           <div style="display:inline-block">
             <input type="hidden" value="1" name="new_${inpatient.id}"/>
             <button class="btn btn-sm btn-outline-primary" onclick="return createRequire(this,'${inpatient.id}');"><i class="fas fa-plus"></i></button>
           </div>
         </td>
       </tr>
     [/#list]
   </table>
   [/@]
  </div>
  <script>
    function createRequire(elem,inpatientId){
      jQuery(elem).parent().find("div");
      return false;
    }
    function removeLine(elem){
      jQuery(elem).parent().remove();
      return false;
    }
    function removeRequire(elem){
      jQuery(elem).parent().remove();
      return false;
    }
  </script>
[@b.foot/]
