[#ftl]
[@b.head/]
[#assign codes={}]
[#assign codes=codes+{'病区':'/ward'}]
[#assign codes=codes+{'性别':'/code/gender'}]
[#assign codes=codes+{'员工类型':'/code/staff-type'}]
[#assign codes=codes+{'病人状态':'/code/inpatient-status'}]
[#assign codes=codes+{'在职状态':'/code/work-status'}]

<ul class="nav nav-tabs nav-tabs-compact" id="code_nav">
  [#list codes?keys as code]
    [#assign link_class]${(code_index==0)?string("nav-link active","nav-link")}[/#assign]
  <li role="presentation" class="nav-item">[@b.a href=codes[code] class=link_class target="codelist"]${code}[/@]</li>
  [/#list]
</ul>
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
