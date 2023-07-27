[@b.head/]
  <div class="container">
    [#list wards as ward]
      <h6 style="text-align: center;">上海民政第二精神卫生中心<br>随心E购买物品登记表</h6>
      <div class="row">
        <div class="col-6" style="text-align:center">病区：${ward.name}</div>
        <div class="col-6" style="text-align:right">时间：________年____月____日</div>
      </div>
      [#assign inpatientOrderLines= wardInpatientOrderLines.get(ward)/]
      [@b.grid items=inpatientOrderLines?keys?sort_by('bedNo') var="inpatient" style="border:0.5px solid #006CB2" sortable="false"]
        [@b.row]
         [@b.col title="序号" width="8%"]${inpatient_index+1}[/@]
         [@b.col title="床号" width="8%" property="bedNo"/]
         [@b.col title="姓名" width="12%" property="name"/]
         [@b.col title="物品与数量"]
           [#list inpatientOrderLines.get(inpatient) as line]
           ${(line.brand.name)!} ${(line.commodity.name)!} ${line.amount}${(line.unit.name)!}[#if line_has_next],[/#if]
           [/#list]
         [/@]
        [/@]
      [/@]
      [#if ward_has_next]<div style="page-break-after: always;"></div> [/#if]
    [/#list]
  </div>
[@b.foot/]
