<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/admin/jsp/common/common.jsp"%>
<%@ taglib uri="/tags/PageTag" prefix="pt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Insert title here</title>
<script>
function openAddWin(){
	var diag = new top.Dialog();
	diag.Title = "添加管理员";
	diag.URL = "admin!preAdd.action";
	diag.Height=300;
	diag.show();
	return false;
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
	if(top.Dialog.confirm("您确定要删除选中的信息吗?")){
		document.forms[0].action = "${ctx}/admin/admin!batchDel.action?checks=" + chk_value;
		document.forms[0].submit();
	}
	return false;
}


function doQuery(){
	$("#currPage").val("");
	document.forms[0].action = "${ctx}/admin/admin!query.action";
	document.forms[0].submit();
}
</script>
</head>
<body>
	<div class="position">
		<div class="center">
		<div class="left">
		<div class="right">
			<span>当前位置：系统设置 >>操作人员管理 </span>	</div>	
		</div>	
		</div>
	</div>	
	<div class="box2" panelTitle="操作人员管理" roller="false">
     <s:form action="admin/admin!query.action" name="initfrm" method="post" target="querytable" theme="simple">
     
  	<input type="hidden" name="totalCount" id="totalCount" value="${totalCount}"/>
  	<input type="hidden" name="totalPage" id="totalPage" value="${totalPage}"/>
  	<input type="hidden" name="currPage" id="currPage" value="${currPage}"/>
	 <input type="hidden" name="perPageRows" id="perPageRows" value="${perPageRows}"/>
	  <table>
        <tr>
        	<td nowrap="nowrap">账号: </td>
        	<td><s:textfield id="name" name="admin.account" class="validate[length[0,15]]"/></td>
        	<td nowrap="nowrap">角色名称: </td>
        	<td>
				 <select id="sel" name="admin.roleId">
				 	<option value="">--请选择--</option>
				 	<s:iterator value="#request.rList" var="item">
				 		<option value="${item.id}">${item.name}</option>
				 	</s:iterator>
				 </select>
			</td> 
			<td><button onclick='return openAddWin()' type="button"><span class="icon_add">新增</span></button></td>
        	<td><button onclick='return doQuery()' type="button"><span class="icon_find">搜索</span></button></td>

        </tr>
      </table>
      </s:form>
    </div>
      
<div id="scrollContent">
	<iframe scrolling="no" width="100%"  frameBorder=0 id="querytable" name="querytable" src="${ctx}/admin/admin!query.action" onload="iframeHeight('querytable')"  allowTransparency="true"></iframe>
</div>
<pt:page action="${ctx}/admin/admin!query.action" target="querytable"></pt:page>
</body>
</html>





