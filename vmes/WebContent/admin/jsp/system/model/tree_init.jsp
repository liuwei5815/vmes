<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/admin/jsp/common/common.jsp"%>
<%@ taglib uri="/tags/PageTag" prefix="pt"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script type="application/javascript" src="${ctxAdmin}/js/jstree/jstree.js?v=4.0"></script>
<link rel="stylesheet" href="${ctxAdmin}/js/jstree/themes/default/style.min.css" />
<script type="application/javascript" src="${ctxAdmin}/js/jstree/overmenu/jstree.plugin.overmenu.min.js"></script>
<link rel="stylesheet" href="${ctxAdmin}/js/jstree/overmenu/overmenu.min.css" />
<title>数据表</title>
<script>
function openAddWin(tableId,width,height){
	var diag = new top.Dialog();
	diag.ID="model";
	diag.Title = "添加";
	diag.URL = "model!preAdd.action?tableId=${tableId}&v=3";
	/* diag.Height=800;
	diag.Width=800; */
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
function loadScrollContent(){
	var document_height = $(document).height();
	$("#scrollContent").height(document_height-130);
}


$(function(){
	
	loadScrollContent();
	var ctxmenus = function(o, cb) {
		var items = {
	
			"edit" : {
				"separator_before" : false,
				"separator_after" : false,
				"label" : "修改",
				"action" : function(data) {
					openEditWin(o.id);
				}
			},
		
			"remove" : {
				"separator_before" : true,
				"separator_after" : false,
				"label" : "删除",
				"action" : function(data) {
					
					del(o.id);
				}
			}
		};
		
		return items;
	};
	$('#jstree_div').jstree({
		"core" : {
			"data" : eval($("#tree").val()),
			"themes" : {
				"variant" : "large",// 加大
				"ellipsis" : true
			
			},
			"check_callback" : true
		},
		"plugins" : [ "wholerow", "themes","overmenu"],
		"contextmenu" : {
			"items" : ctxmenus
		}
		
	}).on('select_node.jstree', function(event, data) {
		$("#departmentId").val(data.node.id);
		$("#currDeptName").text(data.node.text);
		
	});
	function openEditWin(pkValue){
		var width=$("#Mwidth").val();
		var height=$("#Mheight").val();
		var diag = new top.Dialog();
		diag.ID="model";
		diag.Title = "修改";
		diag.URL = "${ctx}/admin/model!preedit.action?tableId="+${table.id}+"&pkValue="+pkValue;
		diag.Width=parseInt(width);
		diag.Height=parseInt(height);
		diag.OKEvent=function(){
			var winContent= diag.innerFrame.contentWindow.document;
			diag.innerFrame.contentWindow.doSubmit();
			
		}
		diag.show();
	}
	function del(id){
		
		top.Dialog.confirm("确定要删除这条记录吗？",function(){
			$.ajax({
			     type: "POST",
			     url: "${ctx}/admin/model!del.action",
			     cache: false,
			     dataType:"json",
			     data:{"tableId":"${table.id}","id":id},
			     success: function(data){
		     		if(data.successflag=="1"){
		     			$("#frm").submit();
		     			top.Dialog.alert("删除成功")
		     		}
		     		
		     		else{
		     			top.Dialog.alert("删除失败");}
			     }
			});	
		});
	}
})
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
<form id="frm" method="post"  action="${ctx }/admin/model!init.action?tableId=${table.id }">
<input  type="hidden" value="${menuConfig.layerWidth}" id="Mwidth"/>
<input type="hidden" value="${menuConfig.layerHeight}" id="Mheight"/>
	

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

<div id="scrollContent" >

<input type="hidden" id="tree" value="${tree }" />
<div id="jstree_div" style="margin-left: 50px;width: 300px">
	
</div>
	
</div>

</body>
</html>





