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

<script type="text/javascript" charset="utf-8"
	src="${ctxAdmin}/js/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${ctxAdmin}/js/ueditor.all.min.js"> </script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=eYf9sA6yVTFHlh9ytU4a0EYY"></script>
<script>
function chooseShip(tableId,showDomId,nameCn,gxId,pkId){
	//如果有主外键关系 则先选择主键
	var pkVal ="";
	if(gxId!=""){

		var dom = $("[targetId='"+gxId+"']");
	
		var pkVal = dom.val();
		if(pkVal==""){
			top.Dialog.alert("请先选择"+dom.attr("lable"));
			return false;
			
		}
	}
	
	var diag = new top.Dialog();
	diag.ID="choose";
	diag.Title = "选择"+nameCn;
	diag.URL = "shipAction!init.action?shipId="+tableId+"&pkVal="+pkVal+"&pkId="+pkId;
	diag.Height=400;
	diag.Width=900;
	diag.OKEvent=function(){
		var winContent= diag.innerFrame.contentWindow.document;
		var checked =   $(winContent).find("#querytable").contents().find(":checkbox['name'='checks']:checked");
		if(checked.size()==0){
			top.Dialog.alert("请至少选择一项");
			return false;
		}
		var type = $(winContent).find("#type").val();
			if(type!='3'){
				if(checked.size()>1){
					top.Dialog.alert("只能选择一项");
					return false;
				}
				
				$("#"+showDomId+"").val(checked.val());
				$("#"+showDomId+"_lable").val(checked.attr("lable"));
			}
			else{
				
					
			
				

			}
			
			diag.close();
		};
	diag.show();
	
	
}

</script>

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
<div style="padding: 0 20px 0 20px;">
	<input type="hidden" name="message" id="message" value="${message}" /> 
	<input type="hidden" name="successflag" id="successflag" value="${successflag}" />
	<s:form action="admin/model!add.action" method="post" theme="simple" id="frm">
	<s:hidden name="tableId"></s:hidden> 
	<table width="100%" class="tableStyle" transmode="true">
		<s:iterator value="columnsList" var="cell">
			<tr class="validate">
				<td>${nameCn }：</td>
				<td>
					<m:model name="columnsMap.${name }" id="${name }" notNull="${notnull }" lable="${nameCn }" dataType="${dataType }" defaults="${defaults }" value="" optValue="${cell.optvalueList }" ship="${shipMap[id] }"
				
					></m:model>
				</td>
			</tr>
		</s:iterator>
		<tr>
			<td colspan="2">&nbsp;</td>
		 </tr>
	</table>
		<!-- 处理在主表中添加从表的情况 -->
		<s:if test="#request.fkViews!=null"> 
			<s:iterator value="#request.fkViews" var="c">
			<fieldset> 
				<legend>
				    <s:property value="#c.pkTable.nameCn"/>
				</legend> 	
					<!-- 如果是多对多的情况 -->
					<table class="tableStyle" transMode="true" footer="normal">
						<s:iterator value="#c.pkTable.addColumns" var="ce">
							<tr>
								<td>${nameCn }</td>
								<td>
									<m:model name="columnsMap.${name}" id="${name }" notNull="${notnull }" lable="${nameCn }" dataType="${dataType }" defaults="${defaults }" value="" optValue="${cell.optvalueList }" ship="${shipMap[id] }"
									
									></m:model>
								</td>
							</tr>
						</s:iterator>
						<tr>
							<td colspan="2">&nbsp;</td>
						</tr>
					</table>
				</fieldset>
			</s:iterator>
		</s:if>

		<div class="padding_top10">
			<table class="tableStyle" transMode="true">
				<tr>
					<td>&nbsp;</td>
					<td>
						<input type="button" onclick="doSubmit()" value=" 提 交 "/>&nbsp;&nbsp;
					    <input type="button" onclick="top.Dialog.close()" value=" 关闭 "/>
					</td>
				</tr>
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr>
			</table>
		</div> 
</s:form></div>
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




