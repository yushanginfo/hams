[#ftl]
[@b.head/]
[#include "../meal_nav.ftl"/]
[@b.toolbar title="伙食费扣费设置"]
  bar.addItem("修改","editSetting()");
  function editSetting(){
    bg.form.submit(document.settingEditForm);
  }
[/@]
<table class="infoTable" id="setting_info">
  <tr>
    <td class="title" width="20%">伙食费低于多少时进行提醒</td>
    <td class="content">${setting.warningMealBalance}元</td>
  </tr>
  <tr>
    <td class="title" width="20%">每日伙食费金额</td>
    <td class="content">${setting.mealPricePerDay}元</td>
  </tr>
</table>

[@b.form name="settingEditForm" action="!edit?id=1"/]
[@b.foot/]
