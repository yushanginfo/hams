[#ftl]
[@b.head/]
[@b.toolbar title="银行卡信息"]bar.addBack();[/@]
  [@b.form action=b.rest.save(bankcard) theme="list"]
    [#if bankcard.persisted]
      [@b.field label="病人"]
        ${bankcard.inpatient.code} ${bankcard.inpatient.name}
        [@b.field label="当前余额"]${bankcard.balance}元[/@]
      [/@]
      [@b.field label="身份证号"]${bankcard.inpatient.person.idcard}[/@]
    [#else]
      [@b.textfield name="bankcard.inpatient.code" label="住院号" onchange="fetchCardNo(this)" value="" required="true" maxlength="10"/]
      [@b.field label="身份证号"]<span id="idcard">&nbsp;</span>[/@]
    [/#if]

    [@b.textfield name="bankcard.bank" label="银行" value=bankcard.bank! required="true"/]
    [@b.textfield name="bankcard.cardNo" label="卡号" value=bankcard.cardNo! required="true"/]
    [@b.radios label="是否为存折" name="bankcard.bankbook"  items="1:是,0:否" value=bankcard.bankbook?string('1','0')/]
    [@b.date name="bankcard.createdOn" label="起始年月" value=bankcard.createdOn! format="date" required="true" /]
    [@b.textfield name="bankcard.initBalance" label="起始余额" value=bankcard.initBalance! required="true" comment="元"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
  <script>
  function fetchCardNo(elem){
     var inpatientCode= jQuery(elem).val();
     if(!inpatientCode) return;
     jQuery.ajax({
      url: "${b.url('!inpatients')}?inpatientCode="+inpatientCode,
      headers:{"Accept":"application/json"},
      success: function(obj){
        if(obj.person){
          var datas = obj.cards;
          $("#idcard").html(obj.person)
        }else{
          $("#idcard").html("住院号错误")
        }
      }
    });
  }
  </script>
[@b.foot/]
