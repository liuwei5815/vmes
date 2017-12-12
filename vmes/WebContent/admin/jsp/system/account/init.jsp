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
	var type=$("#type").val();
	if(type=="web"){
		if($("#webNum").attr("v")<1){
			top.Dialog.alert("剩余可创建Web端账号数量为0");
			return false;
		}
	}else{
		if($("#appNum").attr("v")<1){
			top.Dialog.alert("剩余可创建终端账号数量为0");
			return false;
		}
	}
	var diag = new top.Dialog();
	diag.Title = "新增账号";
	diag.URL = "admin/accout_setting!preAdd.action?type="+type;
	diag.Height=300;
	diag.show();
	return false;
}

function delAll(){
	return querytable.delAll();
}

function doSubmit(){
	//$("#currPage").val("1");
	$("#frm").submit();
}

$(document).ready(function() {
	//document.frm.action = "${ctx}/admin/accout_setting!query.action";
	doSubmit();
});


function vary(){
	var type=$("#type").val();
	if(type=="web"){
		$("#webNum").show();
		$("#appNum").hide();
	}else{
		$("#webNum").hide();
		$("#appNum").show();
	}
	$("#frm").submit();
}
</script>
</head>
<body>
	<div class="position">
		<div class="center">
		<div class="left">
		<div class="right">
			<span>当前位置：系统信息管理>>系统账号管理</span></div>	
		</div>	
		</div>
	</div>	
	<div class="box2" panelTitle="系统账号管理" roller="false">
     <form id="frm" action="admin/accout_setting!query.action" method="post" target="querytable" name="frm">
      	<s:hidden name="currPage" id="currPage" />
		<s:hidden name="perPageRows" id="perPageRows"/>
	  <table>
        <tr>
        <td>账户类型：
        <select name="type" id="type"  class="chosen-select-deselect"  onchange="vary()" style="width: 122px;font-size: 12px;border-color: #cccccc;border-style: solid;border-width: 1px;color: #336699;height: 24px;line-height: 20px;">
        	<option value="web" selected="selected">web端</option>
        	<option value="app">终端</option>
        </select>
        </td>
         <td>账号：</td>
		 <td><input name="cusName" type="text" class="validate[length[0,15]]"/></td>
         <td><button type="button" onclick="doSubmit();"><span class="icon_find">查询</span></button></td>
         <td><button onclick='return openAddWin()' type="button"/><span class="icon_add">新增</span></button></td>
         <td>
         	<!-- 第一次进来,数据可能为空 -->
         	<c:if test="${ empty license }">
         		<span id="webNum" v="0" style="display: show">剩余可创建Web账号数量:0个</span>
         		<span id="appNum" v="0" style="display: none">剩余可创建终端账号数量:0个</span>
         	</c:if>
         	<c:if test="${ not empty  license}">
         		<span id="webNum" v="${license.webNum-webNum}" style="display: show">剩余可创建Web账号数量:${license.webNum-webNum}个</span>
         		<span id="appNum" v="${license.appNum-appNum}" style="display: none">剩余可创建终端账号数量:${license.appNum-appNum}个</span>
         	</c:if>
         </td>
        </tr>
      </table>
      </form>
    </div>
      
<div id="scrollContent">
	<iframe scrolling="no" width="100%"  frameBorder=0 id="querytable" name="querytable" src="${ctx}/admin/accout_setting!query.action" onload="iframeHeight('querytable')"  allowTransparency="true"></iframe>
</div>
<pt:page action="${ctx}/admin/accout_setting!query.action" target="querytable"></pt:page>
</body>

</html>





