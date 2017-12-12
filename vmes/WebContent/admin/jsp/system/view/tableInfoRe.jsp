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
<style>
th{
 text-align: center;
}
</style>
<script>
    var data_str=[];
    function setData(){
    	var num=0;
    	$(".tr_info").each(function(){
    		var size=$(this).find("input[type='radio']:checked").size();
    		if(size>0){
    			num++;
    			var tableId=$(this).find("td:eq(0) input:eq(0)").val();
    			var tableName=$(this).find("td:eq(0) input:eq(1)").val();
    			var tableNameCn=$(this).find("td:eq(0) input:eq(2)").val();
    			var tableStr=tableId+"-"+tableName+"-"+tableNameCn;
    			
    			var col_re="";
    			$(this).find("td:eq(2) input:checked").each(function(){
    				var colId=$(this).parent().attr("colId");
    				var colName=$(this).parent().attr("colName");
    				var colNameCn=$(this).parent().attr("colNameCn");
    				var col_str=colId+"-"+colName+"-"+colNameCn;
    				col_re+="*"+col_str;
    			});
    			col_re=col_re.substring(1);
    			var str_re=tableStr+"#"+col_re;
    			data_str.push(str_re);
    		}
    	});
    	$("#str").val(data_str);
    	
    	if(num>1){
    		top.Dialog.alert("你只能选择一个表");
			return false;
    	}
    	
    	if($("#str").val()=="" || $("#str").val()==null){
			top.Dialog.alert("请选择表");
			return false;
		}
    	parent.frmright.setReTableInfo($("#str").val());
    	top.Dialog.close();
    }
</script>
</head>
<body>
<div id="scrollContent" class="border_gray">
<form method="post">
<input type="hidden" id="str"/>
<table class="tableStyle" id="checkboxTable">
	<tr>
		<th width="30">选择</th>
		<th>表名(表中文名)</th>
		<th>所有列</th>
	</tr>
	<c:if test="${fn:length(listRe) == 0}">
		<tr>
			<td colspan="3" align="center">没有找到符合条件的记录</td>
		</tr>
	</c:if>
	<s:iterator value="#request.listRe" status="st" var="cell">
		<tr class="tr_info">
			<td align="center">
			    <%-- ${st.index+1} --%>
				<input type="hidden" value="${cell[0]}"/><!-- 表id -->
				<input type="hidden" value="${cell[1]}"/><!-- 表名称 -->
				<input type="hidden" value="${cell[2]}"/><!-- 表中文名 -->
				<input type="radio" name="radio"/>
			</td>
			<td align="center">${cell[1]}(${cell[2] })</td>
			<td align="left">
			   <s:iterator value="#cell[3]" var="col">
				   <span colId="${col.id }" colName="${col.name}" colNameCn="${col.nameCn }">
				        ${col.name}(${col.nameCn})
				        <%-- <input type="checkbox" value="${col.id }"/> --%>
				   </span>
				   &nbsp;
			   </s:iterator>
			</td>
		</tr>
	</s:iterator>
</table>
</form>
</div>
</body>
</html>
