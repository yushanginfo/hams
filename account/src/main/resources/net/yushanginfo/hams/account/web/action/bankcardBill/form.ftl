[#ftl]
[@b.head/]
[@b.toolbar title="银行卡支出信息"]bar.addBack();[/@]
  [@b.form action=b.rest.save(bill) theme="list"]
    [#if bill.persisted]
      [@b.field label="姓名"]
        ${bill.account.inpatient.code} ${bill.account.inpatient.name}
      [/@]
      [@b.field label="身份证号"]
        ${bill.account.inpatient.person.idcard}
      [/@]
      [@b.field label="银行卡"]
        ${bill.account.bank} ${bill.account.cardNo}
      [/@]
    [#else]
      [@b.textfield name="bill.account.inpatient.code" label="住院号" value="" onchange="fetchCardNo(this)" required="true" maxlength="10"/]
      [@b.field label="身份证号" ]<span id="idcard">&nbsp;</span>[/@]
      [@b.select name="bill.account.id" id="cardNo" label="银行卡号" value="" required="true"  style="width:400px"/]
    [/#if]
    [@b.textfield name="bill.amount" label="消费金额" value=bill.amount! required="true" maxlength="20" comment="元"/]
    [@b.date name="bill.payAt" label="支出时间" value=bill.payAt! format="datetime" required="true" /]
    [@b.radios label="消费明细" name="bill.expenses" items="转零用金:转零用金" value=bill.expenses! /]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
  <script>
  function fetchCardNo(elem){
     var inpatientCode= jQuery(elem).val();
     if(!inpatientCode) return;
    jQuery.ajax({
      url: "${b.url('!cardNos')}?inpatientCode="+inpatientCode,
      headers:{"Accept":"application/json"},
      success: function(obj){
        if(obj.person){
          var datas = obj.cards;
          var select = $("#cardNo")
          $("#idcard").html(obj.person)
          var cnt=0;
          for(var i in datas){
            cnt += 1;
            var data = datas[i], value = data.id;
            var title = data.bank+" "+ data.cardNo+" 余额 "+data.balance+"元";
            select.append('<option value="'+value+'" title="'+title+'">'+title+'</option>');
          }
        }else{
          $("#idcard").html("住院号错误")
        }
      }
    });
  }
  </script>
[@b.foot/]
