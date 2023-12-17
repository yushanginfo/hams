[@b.head/]
[@b.toolbar title="退款设置"]bar.addBack();[/@]
[@b.form name="walletForm" action="!refund" theme="list"]
  [@b.field label="账户"]
    <div style="width:80%;overflow:hidden">
      <ul>[#list wallets?sort_by(["inpatient","name"]) as wallet]<li style="float: left;list-style-type: decimal;margin-left: 5px;margin-right: 20px;">${wallet.inpatient.name}${wallet.balance}</li>[/#list]</ul>
    </div>
  [/@]
  [@b.date label="退款日期" name="refundAt" required="true" /]
  [@b.formfoot]
    <input name="walletIds" type="hidden" value="[#list wallets as a]${a.id}[#sep],[/#list]"/>
    [@b.submit value="提交"/]
  [/@]
[/@]
<br/><br/><br/><br/><br/><br/><br/>
[@b.foot/]
