<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/core" prefix="c"%>
<%@ include file="/admin/jsp/common/common.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ModelTag.tld" prefix="m" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=eYf9sA6yVTFHlh9ytU4a0EYY"></script>


<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>建表</title>
<style type="text/css">
#checkboxTable{
	table-layout:fixed;
}
#checkboxTable tr td {
	width:100px;
	white-space: nowrap;
	overflow: hidden;
	text-align: center;
	text-overflow:ellipsis;
}
#frm{
	width:95%;
	margin:0 auto;
}
</style>
<script type="text/javascript" charset="utf-8"
	src="${ctxAdmin}/js/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${ctxAdmin}/js/ueditor.all.min.js"> </script>
	
<script type="text/javascript">


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
		var dom=$(winContent);
		if($(winContent).find("#querytable").size()>0){
	
			dom=dom.find("#querytable").contents();
		}
		
		var checked =   dom.find("[name=pkValue]:checked");
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


function openEditWindow(id,pkValue,fkid,fk){
	if(id == ""){
		top.Dialog.alert("参数错误");
		return false;	
	}
	var diag = new top.Dialog();
	diag.ID="mul_edit";
	diag.Title = "修改";
	diag.URL = "${ctx}/admin/model!preEdit.action?tableId="+id+"&pkValue="+pkValue + "&fkid="+fkid +"&fk=" +fk;
	diag.Width=980;
	diag.Height=900;
	diag.OKEvent=function(){
		var winContent= diag.innerFrame.contentWindow.document;
		winContent.frm.submit();
		window.location.reload();
	}
	diag.show();
}

function del(obj,tableId,id){
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
	     			var tr=obj.parentNode.parentNode;  
	     		    var tbody=tr.parentNode;  
	     		    tbody.removeChild(tr);
		     	}
	     		else{
	     			top.Dialog.alert("删除失败");}
		     }
		});	
	});
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
<div style=""><input type="hidden"
	name="message" id="message" value="${message}" /> <input type="hidden"
	name="successflag" id="successflag" value="${successflag}" /> 
		<s:form
	action="admin/model!edit.action" method="post" theme="simple" id="frm">
	<input type="hidden" id="fk" name="fk" value="${fk }" />
	<input type="hidden" id="fkid" name="fkid" value="${fkid }" />
	<s:hidden name="pkValue"></s:hidden>
	<s:hidden name="tableId"></s:hidden>
	<table width="100%" class="tableStyle" transmode="true">
		<s:iterator value="columnsList" var="cell">
			<s:set var="val" value="columnsMap[#cell.name]" scope="request"/>
			<!-- 如果是主键并且不在表单中显示那么应该是隐藏域 -->
			<s:if test="#cell.isPk() ">
					<input type="hidden" name="pkId"  value="${val }" />
			</s:if>
			<s:else>
			<tr class="validate">
				<td>${nameCn }：</td>
				
				<td><m:model name="columnsMap.${name }" id="${name }" notNull="${notnull }" lable="${nameCn }" dataType="${dataType }" defaults="${defaults }" value="${val }" optValue="${cell.optvalueList }" ship="${shipMap[id] }"  
					></m:model></td>
			</tr>
			</s:else>
		</s:iterator>
		
		<%-- <tr>
            <td>备注：</td>
            <td><s:textarea name="role.name" id="memo"/></td>
          </tr> --%>
		<tr>
			<td colspan="2">
			</td>
		</tr>
	</table>
	<!-- 处理在主表中添加从表的情况 -->
	
		<s:if test="#request.listTable!=null">
			  <s:set var="fk" value="pkValue" />
		      <s:iterator value="#request.listTable" var="cell">
		             <fieldset>
		             
		                 <input type="hidden" id="tabId" value="${cell.table.id }" class="fk_tabId"/>
		                 <legend>${cell.table.name }</legend>
		                 <div style="margin-bottom: 10px;float: right;"><input type="button" onclick="openAddWin('${cell.table.id}','${tableId}','${fk}')" value="添加"/></div>
				         <table class="tableStyle" id="checkboxTable">
				                <tr>
									<th width="30" align="center">序号</th>
									<s:iterator value="#cell.list" var="col">
									  <th align="center">${col.nameCn }</th>
									</s:iterator>
									
									<th width="100" align="center">操作</th>
							     </tr>
							     <s:iterator value="#cell.listData" status="sta" var="data">
							      <tr>
							          <td>${sta.index+1 }</td>
							          
								      <s:iterator value="#cell.list" var="col" status="da">
								      		
								      	  <s:if test='#col.dataType ==10 ' >
								          	<td align="center"><img class="image" src="picServlet.do?fileName=${data[da.index+1]}"></td> 
								          </s:if>
								          <s:else>
								          	<td>${data[da.index+1] }</td>
								          </s:else>
								      </s:iterator>
								      <td>
								      	 <span class="img_edit hand" title="编辑" onclick="openEditWindow('${cell.table.id }','${data[0] }','${tableId}','${fk}');"></span>
				 						 <span class="img_delete hand" title="删除" onclick="del(this,'${cell.table.id }','${data[0] }');"></span>
									 </td>
							      </tr>
							     </s:iterator>
				         </table>
				          <%-- </s:if>
				         <s:else> 
				         
							<table class="tableStyle one2one" id="fk_table" transMode="true" footer="normal" width="100%">
								<input type="hidden" id="tabId" value="${cell.table.id }" class="fk_tabId"/>
								<s:iterator value="#cell.listFormCol" var="ce" status="st">
									<tr>
										<td>${ce.nameCn }:</td>
										<td>
											<m:model name="attr_${ce.name}&${ce.id }_${st.index}" id="${ce.name }" notNull="${ce.notnull }" lable="${ce.nameCn }" dataType="${ce.dataType }" defaults="${ce.defaults }" value="" optValue="${ce.optvalueList }" ship="${shipMap[ce.id] }"></m:model>
										    
										</td>
									</tr>
								</s:iterator>
							</table>
							
				         </s:else> --%>
		             </fieldset>
		      </s:iterator>
		</s:if>
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




