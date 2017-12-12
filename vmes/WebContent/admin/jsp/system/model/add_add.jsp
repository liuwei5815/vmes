<!DOCTYPE html PUBLIC "
-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/ModelTag.tld" prefix="m" %>
<%@ include file="/admin/jsp/common/common.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>建表</title>
<style type="text/css">
#frm{
	width:95%;
	margin:0 auto;
}
</style>
<script type="text/javascript" charset="utf-8"
	src="${ctxAdmin}/js/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${ctxAdmin}/js/ueditor.all.min.js"> </script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=eYf9sA6yVTFHlh9ytU4a0EYY"></script>

</head>
<body>
<div class="static_box1">
<div class="box1_topcenter2">
<div class="box1_topleft2">
<div class="box1_topright2"></div>
</div>
</div>
<div class="box1_middlecenter">
<div class="box1_middleleft2">
<div class="box1_middleright2">
<div style="">
	<input type="hidden" name="message" id="message" value="${message}" /> 
	<input type="hidden" name="successflag" id="successflag" value="${successflag}" />
	<s:form action="admin/model!add.action" method="post" theme="simple" id="frm">
	<s:hidden name="tableId"></s:hidden> 
	<input type="hidden" id="param" name="param"/>
	<table width="100%" id="tbmodel" class="tableStyle" transmode="true">
		<input type="hidden" id="tabId" value="${tableId }" class="fk_tabId"/>
		<s:iterator value="columnsList" var="cell">
			<tr class="validate1">
				<td>${nameCn }：</td>
				<td>
					<m:model name="attr_${name }&${id}" id="${name }" notNull="${notnull }" lable="${nameCn }" dataType="${dataType }" defaults="${defaults }" value="" optValue="${cell.optvalueList }" ship="${shipMap[id] }"
				      
					></m:model>
				</td>
			</tr>
		</s:iterator>
		<tr>
			<td colspan="2">&nbsp;</td>
		 </tr>
	</table>

</s:form>
</div>
</div>
</div>
</div>
<div class="box1_bottomcenter2">
<div class="box1_bottomleft2">
<div class="box1_bottomright2"></div>
</div>
</div>
</div>
<%@include file="/admin/jsp/common/image_upload.jsp"%>
<%@include file="/admin/jsp/system/model/common/model.jsp" %>
</body>
</html>




