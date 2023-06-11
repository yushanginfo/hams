[#ftl]
<style>
.list.infoTable tbody > tr.red {
  background-color: DarkSalmon;
}
</style>
[#assign person = inpatient.person/]
[#assign contact = inpatient.contact/]

  <table class="infoTable" align="center" width="100%">
    <tr>
      <td class="title" width="100px">住院号：</td>
      <td>${inpatient.code}</td>
      <td class="title" width="100px">卡号：</td>
      <td>${inpatient.card!}</td>
      <td class="title" width="100px">卡类型</td>
      <td>${(inpatient.cardType.name)!}</td>
    </tr>
    <tr>
      <td class="title" width="100px">姓名：</td>
      <td>${inpatient.name}</td>
      <td class="title" width="100px">性别：</td>
      <td>${inpatient.gender.name}</td>
      <td class="title" width="100px">拼音五笔</td>
      <td>${person.phoneticName!} ${person.wubiName!}</td>
    </tr>
    <tr>
      <td class="title" width="100px">费用类别：</td>
      <td>${inpatient.feeOrigin.name}</td>
      <td class="title" width="100px">入院~出院：</td>
      <td>${inpatient.beginAt?string('yyyy-MM-dd HH:mm')}~${(inpatient.endAt?string('yyyy-MM-dd HH:mm'))!}</td>
      <td class="title" width="100px">病人状态</td>
      <td>${inpatient.status.name}</td>
    </tr>
    <tr>
      <td class="title">婚姻状况：</td>
      <td>${(person.maritalStatus.name)!}</td>
      <td class="title">职业：</td>
      <td>${(person.profession)?if_exists}</td>
      <td class="title">民族：</td>
      <td>${(person.nation.name)?if_exists}</td>
    </tr>
    <tr>
      <td class="title">证件类型：</td>
      <td>${(person.idType.name)!}</td>
      <td class="title">证件号码：</td>
      <td>${(person.idcard)?if_exists}</td>
      <td class="title">出生年月：</td>
      <td>${(person.birthday?string('yyyy-MM-dd'))?if_exists}</td>
    </tr>
    <tr>
      <td class="title">籍贯：</td>
      <td>${person.homeTown!}</td>
      <td class="title">出生地(省)：</td>
      <td>${(person.birthProvince)!}</td>
      <td class="title">出生地(地区)：</td>
      <td>${(person.birthDistrict)!}</td>
    </tr>
  </table>
