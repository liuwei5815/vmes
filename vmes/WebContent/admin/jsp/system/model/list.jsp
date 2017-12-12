<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新闻信息列表</title>
<%@ include file="/admin/jsp/common/common.jsp"%>
<script type='text/javascript' src="${ctx }/admin/js/page.js"></script>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
.tableStyle tr th,td{
 height:24px;
 text-align: center;
 white-space: nowrap;
 word-wrap: break-word;
 overflow: hidden;
}
</style>
<script>
/* function openEditWin(id,pkValue,width,height){
	if(id == ""){
		top.Dialog.alert("参数错误");
		return false;	
	}
	var diag = new top.Dialog();
	diag.ID="model";
	diag.Title = "修改";
	diag.URL = "${ctx}/admin/model!preedit.action?tableId="+id+"&pkValue="+pkValue;
	diag.Width=width;
	diag.Height=height;
	diag.OKEvent=function(){
		var winContent= diag.innerFrame.contentWindow.document;
		winContent.frm.submit();
		window.location.reload();
	}
	diag.show();
	return false;
} */
function openEditWin(id,pkValue,width,height,roleName){
	var diag = new top.Dialog();
	diag.ID="model";
	var title = "编辑 " + roleName;
	console.log(roleName);
	diag.Title = title;
	console.log("id = " + id);
	console.log("pkValue = " + pkValue);	
	diag.URL = "${ctx}/admin/model!preedit.action?tableId="+id+"&pkValue="+pkValue;
 	diag.Height=parseInt(height);
	diag.Width=parseInt(width); 
	diag.OKEvent = function(){
		var winContent= diag.innerFrame.contentWindow.document;
		diag.innerFrame.contentWindow.doSubmit();
	}
	diag.show();
	return false;
}
function del(tableId,id){
	if(tableId == "" || id == ""){
		top.Dialog.alert("参数错误");
		return false;
	}
	top.Dialog.confirm("确定要删除这条记录吗？",function(){
		$.ajax({
		     type: "POST",
		     url: "${ctx}/admin/model!del.action",
		     cache: false,
		     dataType:"json",
		     data:{"tableId":tableId,"id":id},
		     success: function(data){
	     		if(data.successflag=="1"){
	     			window.parent.doQuery(tableId);	
		     	}
	     		else{
	     			top.Dialog.alert("删除失败");}
		     }
		});	
	});
}
function bindMenuActions(){
	$(".menuActions").click(function(){
		var pkId = $(this).attr("pkId");
		var type = $(this).attr("type");
		var name =$(this).attr("name");
		var url =$(this).attr("url");
	
		if(type==='1'){
			var diag = new top.Dialog();
			diag.ID="model";
			diag.Title =name ;
			diag.URL = "${ctx}/"+url+"?id="+pkId,
		 	diag.Height=400;
			diag.Width=400; 
	
			diag.show();
		}
		
	});
}

$(document).ready(function(){ 
	
	loadPage();
	bindMenuActions();
});
</script>
</head>
<body>
<div id="scrollContent" class="border_gray">
<input type="hidden" name="totalCount" id="totalCount" value="${totalCount }" />
<input type="hidden" name="totalPage" id="totalPage" value="${totalPage }" />
<input type="hidden" name="currPage" id="currPage" value="${currPage }" />
<form method="post">
<table class="tableStyle" id="checkboxTable">
	<tr>
		<th width="30">序号</th>
		
		<s:iterator value="columnsList" var="col">
			<s:if test="viewId != null">
				<th>${col.nameCn }</th>
			</s:if>
			<s:else>
				<s:if test="#col.isShowInList()">
					<th>${col.nameCn }</th>
				</s:if>
			</s:else>
			
				
		</s:iterator>
		<th width="60">操作</th>
	</tr>
	<c:if test="${fn:length(list) == 0}">
		<tr>
			<td colspan="<s:property value="#request.columnsListLen+2"/>" align="center">没有找到符合条件的记录</td>
		</tr>
	</c:if>
	<s:iterator value="list" status="st" var="cell">
		 <tr>
			<td>${st.index+1 }</td>
			<s:iterator  value="columnsList" var="col" status="item">
				<s:if test="viewId != null">
					<s:if test="#item.index == 0" >
						<s:set var="pkValue" value="#cell[#item.index]" scope="request"/>
					</s:if>
					<td>${cell[item.index+1]}</td>
				</s:if>
				<s:else>
					<s:if test="#col.isShowInList()">
						<s:if test="#col.dataType==16 || #col.dataType==3">
							<td><fmt:formatDate value="${cell[item.index]}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
						</s:if>
						<s:elseif test="#col.dataType==11">
							<td><fmt:formatDate value="${cell[item.index]}" pattern="yyyy-MM-dd" /></td>
						</s:elseif>
						<s:elseif test="#col.dataType==19">
							<td>
								<img src="${cell[item.index]}"   href="${cell[item.index]}" width="30" height="30"/>
							</td>
						</s:elseif>
						<s:else>
							<td>${cell[item.index]}</td>
						</s:else> 
					</s:if>
					<s:if test="#col.isPk()">
						<s:set var="pkValue" value="#cell[#item.index]" scope="request"/>
					</s:if>
				</s:else>
				
			</s:iterator>
			<td width="60">
				 <s:iterator value="#request.menuActions" var="action">
				 	<span class="${action.icon}  hand menuActions" name="${action.name }" url="${action.url }" title="${action.name }" pkId="${pkValue }"  type="${action.type }"></span>
				 </s:iterator>
				 <!-- class=${action.icon} name=${action.name } url=${action.url } title=${action.name } pkId=${pkValue }  type=${action.type } -->
				 <span class="img_edit hand" title="编辑" onclick="openEditWin('${tableId}','${pkValue }','${menuConfig.layerWidth}','${menuConfig.layerHeight}','${cell[1] }');"></span>
				 <span class="img_delete hand" title="删除" onclick="del('${tableId}','${pkValue}');"></span>
			</td>
		</tr>
	</s:iterator>
</table>
</form>
</div>
</body>
</html>