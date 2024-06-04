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
      <td class="title" width="100px">病区：</td>
      <td>${inpatient.ward.name!}</td>
      <td class="title" width="100px">床位号：</td>
      <td>${inpatient.bedNo!}</td>
      <td class="title" width="100px">床位医生</td>
      <td>${inpatient.bedDoctor!}</td>
    </tr>
    <tr>
      <td class="title">门诊诊断：</td>
      <td>${(inpatient.diseaseType.name)!}</td>
      <td class="title">危重级别：</td>
      <td>${(inpatient.criticalLevel.name)!}</td>
      <td class="title">登记人：</td>
      <td>${(inpatient.createdBy)!}</td>
    </tr>
    <tr>
      <td class="title">特殊膳食类型：</td>
      <td colspan="5">${(inpatient.dietaryType)!}</td>
    </tr>
  </table>
