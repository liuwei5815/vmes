<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/admin/jsp/common/common.jsp"%>
<%@ taglib uri="/tags/PageTag" prefix="pt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Insert title here</title>
<style>	
	select,button{
		height: 30px; 
		margin: 0px 20px;
	}
	input::-ms-input-placeholder{text-align: center;} 		
	input::-webkit-input-placeholder{text-align: center;} 	
</style>
</head>
<body>
	<div class="position">
		<div class="center">
		<div class="left">
		<div class="right">
			<span>当前位置：基础数据配置>>客户信息配置</span>		</div>	
		</div>	
		</div>
	</div>	
	<div class="box2" panelTitle="角色信息" roller="false">
     <form action="admin/customer!query.action" method="post" target="querytable" name="frm">
      	<s:hidden name="currPage" id="currPage" />
		<s:hidden name="perPageRows" id="perPageRows"/>
	  <table>
        <tr>
		  <td><input name="cusName" type="text" class="validate[length[0,15]]" placeholder = "客户" style="width: 160px;height: 26px;margin-right: 50px;"/></td>
		  <td>
		  	  <select>
				  <option value ="allcutomer">全部客户类型</option>
				  <option value ="cutomer1">化工</option>
				  <option value="cutomer2">机械</option>
			  </select>
		  </td>
		  <td>
            <button type="button" onclick="doSubmit();"><span class="icon_find">查询</span></button>
            <button onclick='return openAddWin()' type="button"><span class="icon_add">新增</span></button>
            <button onclick='return importCustomer()' type="button"><span class="icon_add">批量导入</span></button>
		  </td>
        </tr>
        <tr>
        </tr>
      </table>
      </form>
    </div>
      
<div id="scrollContent">
	<iframe scrolling="no" width="100%"  frameBorder=0 id="querytable" name="querytable" src="${ctx}/admin/test!list.action" onload="iframeHeight('querytable')"  allowTransparency="true"></iframe>
</div>
<pt:page action="${ctx}/admin/customer!query.action" target="querytable"></pt:page>
</body>
<script>
/**
 * 添加
 */
function openAddWin(){
	var diag = new top.Dialog();
	diag.Title = "新增客户信息";
	diag.URL = "admin/test!preAdd.action";
	diag.Height=300;
	diag.show();
	return false;
}

function delAll(){
	return querytable.delAll();
}

function doSubmit(){
	//$("#currPage").val("1");
	document.frm.submit();
	document.frm.action = "${ctx}/admin/customer!query.action";
}
/**
 * 导入客户
 */
 function importCustomer(){
		var diag = new top.Dialog();
		diag.ID="model";
		diag.Title = "批量导入客户";
		diag.URL = "${ctx}/admin/customer!preImportCustomer.action";
		diag.Width=500;
		diag.Height=300;
		diag.show();
		return false;
}
</script>
</html>
  
     


