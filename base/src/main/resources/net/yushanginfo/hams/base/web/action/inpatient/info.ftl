[#ftl]
[@b.head/]
[@b.toolbar title='住院病人信息']
  bar.addBackOrClose();
[/@]
<div class="container text-sm">
[#macro panel title]
<div class="card card-info card-outline">
  <div class="card-header">
    <h3 class="card-title">${title}</h3>
  </div>
  [#nested/]
</div>
[/#macro]

[@panel title="基本信息"]
  [#include "info_basic.ftl"/]
[/@]

[@panel title="联系信息"]
  [#include "info_contact.ftl"/]
[/@]

[@panel title="临床信息"]
  [#include "info_disease.ftl"/]
[/@]

</div>
[@b.foot/]
