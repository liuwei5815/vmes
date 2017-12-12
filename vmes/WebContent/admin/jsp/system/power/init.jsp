<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/admin/jsp/common/common.jsp"%>
<%@ taglib uri="/tags/PageTag" prefix="pt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Insert title here</title>
<script>

function doQuery(){
	$("#currPage").val("");
	$("#frm").submit();
	//document.forms[0].submit();
}
function openAddWin(){
	var diag = new top.Dialog();
	diag.Title = "新增角色信息";
	diag.URL = "admin/role!preAdd.action";
	diag.Height=350;
	diag.show();
	return false;
}

$(document).ready(function() {
	document.forms[0].action = "${ctx}/admin/power!query.action";
})
</script>
</head>
<body>
	<div class="position">
		<div class="center">
		<div class="left">
		<div class="right">
			<span>当前位置：系统信息管理 >>角色权限管理 </span>	</div>	
		</div>	
		</div>
	</div>	
	<div class="box2" panelTitle="角色权限管理" roller="false">
     <s:form id="frm" action="admin/power!query.action" name="initfrm" method="post" target="querytable" theme="simple">
     
  	<input type="hidden" name="totalCount" id="totalCount" value="${totalCount}"/>
  	<input type="hidden" name="totalPage" id="totalPage" value="${totalPage}"/>
  	<input type="hidden" name="currPage" id="currPage" value="${currPage}"/>
	 <input type="hidden" name="perPageRows" id="perPageRows" value="${perPageRows}"/>
	  <table>
        <tr>
        	<td nowrap="nowrap">角色名称: </td>
        	<td><s:textfield id="name" name="role.name" class="validate[length[0,15]]"/></td>
        	<td><button onclick='return doQuery()' type="button"><span class="icon_find">查询</span></button></td>
        	<td>
				<button onclick='return openAddWin()' type="button">
					<span class="icon_add">新增</span>
				</button>
			</td>
        </tr>
        
      </table>
      </s:form>
    </div>
      
<div id="scrollContent">
	<iframe scrolling="no" width="100%"  frameBorder=0 id="querytable" name="querytable" src="${ctx}/admin/power!query.action" onload="iframeHeight('querytable')"  allowTransparency="true"></iframe>
</div>
<pt:page action="${ctx}/admin/power!query.action" target="querytable"></pt:page>
</body>
</html>





