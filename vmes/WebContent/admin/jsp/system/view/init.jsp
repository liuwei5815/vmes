<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/admin/jsp/common/common.jsp"%>
<%@ taglib uri="/tags/PageTag" prefix="pt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Insert title here</title>
<script>
function addView(){
	window.location.href="${ctx}/admin/view!addView.action";
}

function delAll(){
	var chk_value =[];    
	$(querytable.document).find('input[name="checks"]:checked').each(function(){    
		chk_value.push($(this).val());    
	});    
	if(chk_value.length==0){
		top.Dialog.alert('您至少要选择一条信息!');
		return false;
	}
	if(confirm("您确定要删除选中的信息吗?")){
		document.forms[0].action = "${ctx}/admin/admin!batchDel.action?checks=" + chk_value;
		document.forms[0].submit();
	}
	return false;
}


function doQuery(){
	$("#currPage").val("");
	document.forms[0].action = "${ctx}/admin/view!query.action";
	document.forms[0].submit();
}
</script>
</head>
<body>
	<div class="position">
		<div class="center">
		<div class="left">
		<div class="right">
			<span>当前位置：系统管理 >>视图管理 </span>	</div>	
		</div>	
		</div>
	</div>	
<div class="box2" panelTitle="视图管理" roller="false">
    <s:form action="admin/view!query.action" name="initfrm" method="post" target="querytable" theme="simple">
	  	<input type="hidden" name="totalCount" id="totalCount" value="${totalCount}"/>
	  	<input type="hidden" name="totalPage" id="totalPage" value="${totalPage}"/>
	  	<input type="hidden" name="currPage" id="currPage" value="${currPage}"/>
		<input type="hidden" name="perPageRows" id="perPageRows" value="${perPageRows}"/>
		<table>
	        <tr>
	        	<td nowrap="nowrap">视图名称: </td>
	        	<td><input type="text" name="view.name" class="validate[length[0,15]]"/></td>
	        	<td nowrap="nowrap">创建时间：</td>
	          	<td nowrap="nowrap"><input class="date"  id="beginDate" name="beginDate"/></td>
	          	<td align="center"> 到 </td>
	          	<td><input class="date"  id="endDate" name="endDate"/></td>
	        	<td><button onclick='return doQuery()' type="button"><span class="icon_find">查询</span></button></td>
	            <td><button onclick='return addView()' type="button"><span class="icon_add">新增视图</span></button></td>
	        </tr>
	    </table>
    </s:form>
</div>
      
<div id="scrollContent">
	<iframe scrolling="no" width="100%"  frameBorder=0 id="querytable" name="querytable" src="${ctx}/admin/view!query.action" onload="iframeHeight('querytable')"  allowTransparency="true"></iframe>
</div>
<pt:page action="${ctx}/admin/view!query.action" target="querytable"></pt:page>
</body>
</html>





