<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>信息列表</title>
<%@ include file="/admin/jsp/common/common.jsp"%>
<script type='text/javascript' src="${ctx }/admin/js/page.js"></script>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
#div_card {
	float:left; 
	width:190px;
	border:#c0c0c0 2px solid;
	border-radius:5px;
	margin:0 10px 10px 0;
	text-align:center;
}

#div_text {
	 background:#c0c0c0;
	 text-align:left;
	 padding:5px;
	 color:white;
	 border-radius:3px 3px 0px 0px;
	 line-height:130%;
}

#div_img {
	width:190px;
	height:170px;
}

#img_img {
	max-width:190px;
	max-height:170px;
}

#div_state {
	text-align:center;
	padding:8px;
	font-size:14px;
	font-weight:bold;
	color:white;
	background:#c0c0c0;
	border-radius:0px 0px 3px 3px;
}
</style>
</head>
<body>
<form action="${ctx}/admin/equipment!base.action" method="post" target="querytable" id="frm">
<input type="hidden" name="totalCount" id="totalCount" value="${totalCount }" />
<input type="hidden" name="totalPage" id="totalPage" value="${totalPage }" />
<input type="hidden" name="currPage" id="currPage" value="${currPage }" />
<div id="div_content">
		<c:if test="${fn:length(list) == 0}">
			<p style="text-align:center;">没有找到符合条件的设备</p>
		</c:if>
		<s:iterator value="list" status="st" var="cell">
			<div id="div_card">
				<div id="div_text" <s:if test="#request.state[#cell[0].id] != 3">style="background:#30B2C2"</s:if>>
					系统设备编号：${cell[0].equipmentCode }<br/>
					用户设备编号：${cell[0].userEquipmentCode }<br/>
					设备名称：${cell[0].equipmentName }<br/>
					规格/型号：${cell[0].equipmentModel }<br/>
					所属部门：${cell[2].name }<br/>
			  	</div>
			  	<div id="div_img">
					<%-- <img id="img_img" src="<s:property value='@com.xy.cms.common.CommonFunction@getStrValue(\"picUrl\")'/>${cell[0].equipmentImg }" alt="无图片"/><br/> --%>
 					<img id="img_img" <c:if test="${empty cell[0].equipmentImg }">src="${ctx}/admin/image/Screenshot-3.png"</c:if><c:if test="${not empty cell[0].equipmentImg }">src="${rfile }/${cell[0].equipmentImg }"</c:if>/>
					<%-- <img id="img_img" src="${ctx}/admin/image/Screenshot-3.png" alt="无图片"/><br/> --%>
				</div>
				<!-- 状态 0-等待 1-异常 2-在线 3-停机 无值-离线 -->
				<div id="div_state"<s:if test="#request.state[#cell[0].id] != 3">style="background:#30B2C2"</s:if>>
					<s:if test="#request.state[#cell[0].id] != 3">在线</s:if>
					<s:else>离线</s:else>
				</div>
			</div>
		</s:iterator>
		<div style="clear:both;"></div>
</div>
</form>
<script>
function doSubmit(){
	$("#frm").submit();	
}

$(document).ready(function() {
	loadPage();
	$(".img_add2").click(function(){
		var height = $(document).height();
		window.parent.iframeHeight('querytable');
	}) 
});
</script>
</body>
</html>