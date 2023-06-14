[#ftl]
[@b.head/]
[#assign codes={}]
[#assign gbcodes={}]

[#assign gbcodes=gbcodes+{'性别':'/code/gender'}]
[#assign gbcodes=gbcodes+{'证件类型':'/code/id-type'}]
[#assign gbcodes=gbcodes+{'民族':'/code/nation'}]
[#assign gbcodes=gbcodes+{'国家':'/code/country'}]
[#assign gbcodes=gbcodes+{'婚姻状况':'/code/marital-status'}]
[#assign gbcodes=gbcodes+{'社会关系':'/code/relationship'}]

[#assign codes=codes+{'病区':'/ward'}]
[#assign codes=codes+{'病人状态':'/code/inpatient-status'}]
[#assign codes=codes+{'门诊诊断':'/code/disease-type'}]
[#assign codes=codes+{'费用来源':'/code/fee-origin'}]
[#assign codes=codes+{'收入渠道':'/code/income-channel'}]
[#assign codes=codes+{'危重级别':'/code/critical-level'}]
[#assign codes=codes+{'卡类型':'/code/card-type'}]
[#assign codes=codes+{'员工类型':'/code/staff-type'}]

[@b.nav class="nav nav-tabs nav-tabs-compact"  id="code_nav"]
  [#list codes?keys as code]
  [#if code_index<9]
  [#assign link_class]${(code_index==0)?string("nav-link active","nav-link")}[/#assign]
  <li role="presentation" class="nav-item">[@b.a href=codes[code] class=link_class target="codelist"]${code}[/@]</li>
  [/#if]
  [/#list]
  [#if codes?size>9]
  <li class="nav-item dropdown">
    <a href="#" class="nav-link dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">更多.. <span class="caret"></span></a>
    <div class="dropdown-menu">
        [#list codes?keys as code]
        [#if code_index >8]
        [@b.a href=codes[code] class="dropdown-item" target="codelist"]${code}[/@]
        [/#if]
        [/#list]
    </div>
  </li>
  [/#if]

  <li class="nav-item dropdown">
    <a href="#" class="nav-link dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">国标.. <span class="caret"></span></a>
    <div class="dropdown-menu">
        [#list gbcodes?keys as code]
        [@b.a href=gbcodes[code] class="dropdown-item" target="codelist"]${code}[/@]
        [/#list]
    </div>
  </li>
[/@]
[@b.div id="codelist" href="/ward"/]
<script>
  jQuery(document).ready(function(){
    jQuery('#code_nav>li>a').bind("click",function(e){
      jQuery("#code_nav>li>a").removeClass("active");
      jQuery(this).addClass("active");
    });
  });
</script>
[@b.foot/]
