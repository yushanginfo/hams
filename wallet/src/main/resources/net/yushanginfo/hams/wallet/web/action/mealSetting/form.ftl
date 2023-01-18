[#ftl]
[@b.head/]
[@b.toolbar title="伙食费设置"]bar.addBack();[/@]
  [@b.form action=b.rest.save(walletSetting) theme="list"]
    [@b.textfield name="walletSetting.warningMealBalance" label="伙食费低于多少时进行提醒" value=walletSetting.warningMealBalance required="true" comment="元"/]
    [@b.textfield check="match('number')" name="walletSetting.mealPricePerDay" label="每日伙食费金额" value=walletSetting.mealPricePerDay! required="true" comment="元"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[@b.foot/]