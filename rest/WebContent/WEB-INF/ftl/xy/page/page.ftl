<#--
	分页标签：用于显示分页数据。
	formName:页面form的名称
	value:queryResult
	url:访问路径
	
-->
<#macro page value="" url=""  formName="">

	<div class="box-footer clearfix">
			  <ul class="pagination pagination-sm no-margin pull-left" >
			  	<li>共${value.totalCount}条数据,当前${value.currentPage}/${value.totalPage}页</li>
			  </ul>
              <ul class="pagination pagination-sm no-margin pull-right">
              	
              	<li><a href="javascript:void(0);" <#if 1<value.currentPage>onclick="turn('1');"</#if> >首页</a></li>
                <li><a href="javascript:void(0);" <#if 1<value.currentPage>onclick="turn('${value.currentPage-1}');"</#if> >&laquo;</a></li>
                <#if value.currentPage!=0>
                <#assign begin=1><!--默认从1开始到5结束-->
                <#assign end=5>
         		<#if 2<value.currentPage>
                	 <#assign begin=value.currentPage-2 >
                	 <#assign end=value.currentPage+2 >
                </#if>
               	<#if value.totalPage<end>
					 <#assign end =value.totalPage>
					 <#if end<5>
					 	<#assign begin=1>
					 	<#else>
					 	<#assign begin=end-4>
					 </#if>
               	</#if>
              
              	<#list begin..end as n>
              		 <li <#if value.currentPage=n>  </#if>><a href="javascript:void(0);"     onclick="turn('${n}')">${n}</a></li>
              	</#list>
              	</#if>
                <li><a href="javascript:void(0);" <#if value.currentPage<value.totalPage> onclick="turn('${value.currentPage+1}');"</#if>>&raquo;</a></li>
                <li><a href="javascript:void(0);" <#if value.currentPage<value.totalPage> onclick="turn('${value.totalPage}');"</#if>>尾页</a></li>
              </ul>
     </div>
    

		<script>
			function turn(currentPage){
		
				document.getElementById("currentPage").value=currentPage;
				doSubmit();
			}
			
			function doSubmit(){
				<#if formName=="">
					var dom=document.forms[0].submit();
				<#else>
					var dom = document.${formName}.submit();
				</#if>
				
				with(dom){
					<#if url!="">
					action=${url};
					</#if>
					submit();
				}
			
			}
		</script>
</#macro>