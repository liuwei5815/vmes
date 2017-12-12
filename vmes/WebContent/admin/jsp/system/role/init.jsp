<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/admin/jsp/common/common.jsp"%>
<%@ taglib uri="/tags/PageTag" prefix="pt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Insert title here</title>
<script>
/**
 * 添加
 */
function openAddWin(){
	var diag = new top.Dialog();
	diag.Title = "添加角色信息";
	diag.URL = "admin/role!preAdd.action";
	diag.Height=350;
	diag.show();
	return false;
}

function delAll(){
	return querytable.delAll();
}

function doQuery(){
	//$("#currPage").val("1");
	document.frm.action = "${ctx}/admin/role!query.action";
	document.frm.submit();
}
</script>
</head>
<body>
	<div class="position">
		<div class="center">
		<div class="left">
		<div class="right">
			<span>当前位置：系统管理  >> 角色管理</span>		</div>	
		</div>	
		</div>
	</div>	
	<div class="box2" panelTitle="角色信息" roller="false">
     <form action="admin/role!query.action" method="post" target="querytable" name="frm">
      	<s:hidden name="currPage" id="currPage" />
		<s:hidden name="perPageRows" id="perPageRows"/>
	  <table>
        <tr>
          <td>角色名称：</td>
          <td><input type="text"  id="name" name="role.name" class="validate[length[0,15]]"/></td>
          <td><button type="button" onclick="doQuery();"><span class="icon_find">查询</span></button></td>
          <td><button onclick='return openAddWin()' type="button"/><span class="icon_add">新增</span></button></td>
          <!-- <td><button onclick="return delAll();" type="button"><span class="icon_delete">删除</span></button></td>
           -->
        </tr>
      </table>
      </form>
    </div>
      
<div id="scrollContent">
	<iframe scrolling="no" width="100%"  frameBorder=0 id="querytable" name="querytable" src="${ctx}/admin/role!query.action" onload="iframeHeight('querytable')"  allowTransparency="true"></iframe>
</div>
<pt:page action="${ctx}/admin/role!query.action" target="querytable"></pt:page>
</body>
</html>





