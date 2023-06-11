[#ftl]
[@b.head/]
[@b.toolbar title="国家信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
<table class="infoTable">
  <tr>
    <td class="title" width="20%">名称</td>
    <td class="content">${country.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">代码</td>
    <td class="content">${country.code}</td>
  </tr>
  <tr>
    <td class="title" width="20%">英文名称</td>
    <td class="content" >${country.enName!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">生效时间</td>
    <td class="content" >${country.beginOn!}</td>
  </tr>
  <tr>
    <td class="title" width="10%">失效时间</td>
    <td class="content" width="40%">${country.endOn!}</td>
  </tr>
  <tr>
    <td class="title" width="10%">备注</td>
    <td class="content">${country.remark!}</td>
  </tr>
</table>

[@b.foot/]
