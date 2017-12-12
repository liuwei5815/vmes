<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/admin/jsp/common/common.jsp"%>
<%@ taglib uri="/tags/PageTag" prefix="pt"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>数据表</title>
<script>
function openAddWin(tableId,width,height){
	var diag = new top.Dialog();
	diag.ID="model";
	diag.Title = "添加";
	diag.URL = "model!preAdd.action?tableId=${tableId}&v=3";
	diag.Height=height;
	diag.Width=width;
	diag.OKEvent = function(){
		var winContent= diag.innerFrame.contentWindow.document;
		diag.innerFrame.contentWindow.doSubmit();
	}
	diag.show();
	return false;
}

function doQuery(tableId){
	document.forms[0].submit();
}

function changView(){
	var myViewId=$("#myViewId").val();
	location.href="${ctx}/admin/model!init.action?tableId="+$("#tableId").val()+"&viewId="+myViewId;	
}

function doQuery() {
	document.forms[0].action = "${ctx}/admin/model!query.action";
	document.forms[0].submit();
}
</script>
</head>
<body>
<div class="position">
<div class="center">
<div class="left">
<div class="right"><span>当前位置：业务管理 >>${menu.name } </span></div>
</div>
</div>
</div>
<div class="box2" panelTitle="${menu.name }" roller="false">
<form id="frm" method="post" target="querytable" action="admin/model!query.action">
	<input type="hidden" name="currPage" id="currPage" value="${currpage}" /> 
	<input type="hidden" name="perPageRows" id="perPageRows" value="${perPageRows}" />
	<s:hidden id="tableId" name="tableId"></s:hidden>
	<s:hidden id="viewId" name="viewId"></s:hidden>
<table>
	<tr>
		<%-- <td>
			视图&nbsp;:&nbsp;<select id="myViewId" onchange="changView()">
				<option value="">默认视图</option>
				<s:iterator value="list" var="view">
					<option value="${view.id }" <s:if test="#view.id == viewId">selected</s:if>>${view.name }</option>
				</s:iterator>
			</select>
		</td> --%>
		<s:if test="searchList.size() > 0">
			<s:iterator value="searchList" status="st" var="slist">
				<td>
				&nbsp;${slist.colunmsNameCn }&nbsp;: 
				<!-- 日期类型 -->
				<s:if test="#slist.colDataType == 3 || #slist.colDataType == 11">
					<input type="text" class="date" name="viewBeanList[${st.index }].ruleValue" value="${slist.ruleValue }"/>
				</s:if>
				<!-- 下拉框类型 -->
				<s:elseif test="#slist.colDataType == 7">
					<select name="viewBeanList[${st.index }].ruleValue">
						<s:iterator value="#slist.getOptvalueList()" var="stype">
							<option value="${stype }">${stype }</option>
						</s:iterator>
					</select>
				</s:elseif>
				
				<!-- 复选框 -->
				<s:elseif test="#slist.colDataType == 8">
					   <input type="hidden" name="viewBeanList[${st.index }].ruleValue"/>
						<s:iterator value="#slist.getOptvalueList()" var="stype">
							${stype }&nbsp;<input type="checkbox" value="${stype }"/>
						</s:iterator>
					
				</s:elseif>
				
				<!-- 单选框 -->
				<s:elseif test="#slist.colDataType == 9">
				<input type="hidden" name="viewBeanList[${st.index }].ruleValue"/>
						<s:iterator value="#slist.getOptvalueList()" var="stype">
						    ${stype }&nbsp;<input type="radio" value="${stype }" name="${st.index }"/>
						</s:iterator>
				</s:elseif>
				
				<s:else>
					<input type="text" name="viewBeanList[${st.index }].ruleValue" value="${slist.ruleValue }"/>
				</s:else>
				<input type="hidden" name="viewBeanList[${st.index }].rule" value="${slist.rule }"/>
				<input type="hidden" name="viewBeanList[${st.index }].optValue" value="${slist.optValue }"/>
				<input type="hidden" name="viewBeanList[${st.index }].colDataType" value="${slist.colDataType }"/>
				<input type="hidden" name="viewBeanList[${st.index }].colunmsNameCn" value="${slist.colunmsNameCn }"/>
				<input type="hidden" name="viewBeanList[${st.index }].colunmsName" value="${slist.colunmsName}"/>
				<input type="hidden" name="viewBeanList[${st.index }].colunmsId" value="${slist.colunmsId }"/>
				<input type="hidden" name="viewBeanList[${st.index }].tableName" value="${slist.tableName }"/>
				</td>
			</s:iterator>
			<td>
			<button onclick='return doQuery()' type="button"><span class="icon_find">搜索</span></button>
			</td>
		</s:if>
		<td>
			<button onclick='return openAddWin(${table.id },${menuConfig.layerWidth},${menuConfig.layerHeight});' type="button">
				<span class="icon_add">新增</span>
			</button>
		</td>
	</tr>
</table>
</form>
</div>

<div id="scrollContent"><iframe scrolling="no" width="100%"
	frameBorder=0 id="querytable" name="querytable"
	src="${ctx}/admin/model!query.action?tableId=${tableId}&viewId=${viewId}"
	onload="iframeHeight('querytable')" allowTransparency="true"></iframe>
</div>
<pt:page action="${ctx}/admin/model!query.action" target="querytable"></pt:page>
</body>
</html>





