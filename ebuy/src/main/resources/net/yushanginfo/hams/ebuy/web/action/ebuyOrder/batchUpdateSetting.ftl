[@b.head/]
[@b.toolbar title="批量修改扣费日期"]bar.addBack();[/@]
[@b.form name="orderForm" action="!batchUpdate" theme="list"]
  [@b.field label="批次"]
    <div style="width:80%;overflow:hidden">
      <ul>[#list orders?sort_by("name") as order]<li style="float: left;list-style-type: decimal;margin-left: 5px;margin-right: 20px;">${order.name}${order.ward.name}</li>[/#list]</ul>
    </div>
  [/@]
  [@b.date label="扣费日期" name="billOn" required="true" /]
  [@b.formfoot]
    <input name="ebuyOrderIds" type="hidden" value="[#list orders as a]${a.id}[#sep],[/#list]"/>
    [@b.submit value="提交"/]
  [/@]
[/@]
<br/><br/><br/><br/><br/><br/><br/>
[@b.foot/]
