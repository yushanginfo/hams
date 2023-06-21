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
      <td class="title" width="100px">户籍地址：</td>
      <td>${contact.residenceAddress!}</td>
      <td class="title" width="100px">现住址：</td>
      <td>${contact.address!'--'} [#if contact.postcode??](${contact.postcode})[/#if]</td>
      <td class="title" width="100px">联系电话</td>
      <td>${contact.phone!}</td>
    </tr>
    <tr>
      <td class="title">单位名称：</td>
      <td>${(contact.orgName)!}</td>
      <td class="title">单位电话：</td>
      <td>${(contact.orgPhone)!}</td>
      <td class="title">单位邮编：</td>
      <td>${(contact.orgPostcode)!}</td>
    </tr>
    [#list inpatient.relations?sort_by('idx') as r]
    <tr>
      <td class="title">联系人：</td>
      <td>${r.name} (${r.relationship.name}) ${(r.phone)!}</td>
      <td class="title">联系人地址：</td>
      <td>${(r.address)!}</td>
      <td class="title">联系人街道：</td>
      <td>${(r.subdistrict)!}</td>
    </tr>
    [/#list]
  </table>
