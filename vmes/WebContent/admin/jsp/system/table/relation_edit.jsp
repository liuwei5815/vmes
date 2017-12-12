<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/core" prefix="c"%>
<%@ include file="/admin/jsp/common/common.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<style>
.textinput {
width: 95%;
font-size: 12px;
background: url(${ctx}/admin/frame/images/textinput_bg.jpg) repeat-x scroll left top #ffffff;
border-color: #cccccc;
border-style: solid;
border-width: 1px;
color: #336699;
height: 20px;
line-height: 20px;
}
.tableStyle .th{
width: 100px;
text-align: center;
}
#addIcon{
width: 10px;
}
.tableStyle .chk{
width: 20px;
}

</style>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>建表</title>
<script type="text/javascript">
	jQuery(document).ready(function(){
		setPriCol();
	});

	

	
	function selectTable(){
		var tableId=$("#c_table").val();
		$.ajax({
			url:"${ctx}/admin/sys_tables!loadColByTabId.action?tableId="+tableId,
			dataType:"json",
			type:"post",
			success:function(data){
				var option_html="";
				for(var i=0;i<data.length;i++){
					option_html+="<option value='"+data[i].id+"'>"+data[i].name+"</option>";
				}
				$("#c_column").append(option_html);
			}
		});
	}
	
   //保存主外键关系
   function saveRelation(){
	   var fkname=$("#fkName").val();//外键名称
	   var source_tabId=$("#current_tabId").val();//source表id
	   var sourse_fk_colId=$("#c_col").val();//source表外键字段id
	   var target_tabId=$("#c_table").val();//target表id
	   var target_fk_colId=$("#c_column").val();//target表主键字段id
	   
	   if(target_tabId=="" || target_tabId==null){
		   top.Dialog.alert("请选择主键表");
		   return false;
	   }
	   
	   if(fkname=="" || fkname==null){
		   top.Dialog.alert("请输入外键名称");
		   return false;
	   }
	   if(sourse_fk_colId=="" || sourse_fk_colId==null){
		   top.Dialog.alert("请选择当前字段");
		   return false;
	   }
	   
	   if(target_fk_colId=="" || target_fk_colId==null){
		   top.Dialog.alert("请选择目标字段");
		   return false;
	   }
	   $.ajax({
		   url:"${ctx}/admin/sys_tables!addRelation.action",
		   dataType:"json",
		   type:"post",
		   data:{
			   "ship.name":fkname,
			   "ship.sourceTableId":source_tabId,
			   "ship.sourceColumnId":sourse_fk_colId,
			   "ship.targetTableId":target_tabId,
			   "ship.targetColumnId":target_fk_colId
		   },
		   success:function(data){
			   if(data.code=="1"){
				   	top.Dialog.alert(data.msg);
			    	  top.Dialog.close();
			      }else{
			    	  top.Dialog.alert(data.msg);
			      }
		   }
	   });
   }
   
   function setPriCol(){
	   $(".tr_for").each(function(){
		   var pri_tabId=$(this).find("td:eq(3) input:eq(0)").val();//主键表ID
		   var pri_colId=$(this).find("td:eq(4) input:eq(0)").val();//主键表字段ID
		   var option_html="";
		   $.ajax({
			   url:"${ctx}/admin/sys_tables!loadColByTabId.action?tableId="+pri_tabId,
			   dataType:"json",
			   type:"post",
			   async:false,
			   success:function(data){
				   var html="";
				   for(var i=0;i<data.length;i++){
					   if(data[i].id==pri_colId){
						   html+="<option value="+data[i].id+" selected='selected'>"+data[i].name+"</option>";
					   }else{
						   html+="<option value="+data[i].id+">"+data[i].name+"</option>";
					   }
					   
				   }
				   option_html=html;
				   
			   }
		   });
		    $(this).find("td:eq(4) select:eq(0)").append(option_html);
	   });
   }
   
   function chooseCol(e){
	   var pri_colId=$(e).val();
	   $.ajax({
			url:"${ctx}/admin/sys_tables!loadColByTabId.action?tableId="+pri_colId,
			dataType:"json",
			type:"post",
			success:function(data){
				var option_html="";
				for(var i=0;i<data.length;i++){
					option_html+="<option value='"+data[i].id+"'>"+data[i].name+"</option>";
				}
				var obj=$(e).parents("tr").find("td:eq(4) select:eq(0)");
				obj.find("option").remove();
				obj.append(option_html);
			}
		});
   }
   
 function editRelation(){
	var fkName=document.getElementsByName("fkName");
	for(var i=0;i<fkName.length;i++){
		if(fkName[i]==""){
			top.Dialog.alert("第"+(i+1)+"行外键名称不能为空");
			return false;
		}
	}
	
	var str=[];
	$(".tr_for").each(function(){
		var relation_id=$(this).find("td:eq(0) input:eq(1)").val();//关系表主键ID
		var fk_name=$(this).find("td:eq(0) input:eq(0)").val();//外键名称new
		var fk_tabName=$(this).find("td:eq(1) input:eq(0)").val();//外键表名称
		var fk_id=$(this).find("td:eq(2) select:eq(0)").val();//外键字段ID
		var pk_tabId=$(this).find("td:eq(3) select:eq(0)").val();//主键表ID
		var pk_colId=$(this).find("td:eq(4) select:eq(0)").val();//主键字段ID
		var fk_nameOld=$(this).find("td:eq(0) input:eq(2)").val();//外键名称old
		var paramStr=relation_id+"#"+fk_name+"#"+fk_tabName+"#"+fk_id+"#"+pk_tabId+"#"+pk_colId+"#"+fk_nameOld;
		str.push(paramStr);
	});
	
	$("#parStr").val(str);
	$.ajax({
		url:"${ctx}/admin/sys_tables!editRelation.action",
		dataType:"json",
		type:"post",
		data:{
			"parStr":$("#parStr").val()
		},
		success:function(data){
			if(data.code==1){
				top.Dialog.alert(data.msg);
				top.Dialog.close();
			}else{
				top.Dialog.alert(data.msg);
			}
		}
	});
 }
   
</script>
</head>
<body>
<div class="static_box1">
<div class="box1_topcenter2"><div class="box1_topleft2"><div class="box1_topright2"></div></div></div>
<div class="box1_middlecenter"><div class="box1_middleleft2"><div class="box1_middleright2">
    <input type="hidden" id="parStr"/>
	<div style="padding:0 20px 0 20px;">
	          <table class="tableStyle" style="line-height:28px;" id="mytable">
	          

	            <tr>
	  				<th class="th">外键名称</th>
	  				<th class="th">外键表</th>
	  				<th class="th">外键字段</th>
	  				<th class="th">主键表</th>
	  				<th class="th">主键字段</th>
	  			</tr>
	  			<c:if test="${fn:length(listRelation) == 0}">
					<tr>
						<td colspan="7" align="center">没查询到任何记录</td>
					</tr>
	            </c:if>
	  			<s:iterator value="listRelation" var="cell">
	  			    <tr class="tr_for">
	  			        <td align="center">
	  			           <input type="text" value="${cell[1] }" style="width:95%;" name="fkName"/>
	  			           <input type="hidden" value="${cell[0] }" />
	  			           <input type="hidden" value="${cell[1] }" />
	  			        </td>
	  			        <td align="center">
		  			         ${cell[3] }
		  			         <input type="hidden" value="${cell[3] }"/>
		  			         <input type="hidden" value="${cell[2] }"/><!--外键表id  -->
	  			        </td>
	  			        <td align="center">
	  			        	 <%-- ${cell[5] } --%>
	  			             <input type="hidden" value="${cell[4] }"/><!--外键字段id  -->
	  			             <select class="default;" style="width: 95%;">
		  			             <s:iterator value="listCol" var="col_cell">
		  			                 <option value="${col_cell.id }" <s:if test="#col_cell.id==#cell[4]">selected</s:if>>${col_cell.name }</option>
		  			             </s:iterator>
	  			             </select>
	  			        </td>
	  			        <td align="center">
	  			             <%--  ${cell[7] } --%>
	  			              <input type="hidden" value="${cell[6] }"/><!--主键表id -->
	  			              <select class="default;" style="width: 95%;" onchange="chooseCol(this)">
	  			                 <s:iterator value="list" var="tab_cell">
	  			                      <option value="${tab_cell.id }" <s:if test="#tab_cell.id==#cell[6]">selected</s:if>>${tab_cell.name }</option>
	  			                 </s:iterator>
	  			              </select>
	  			              
	  			        </td>
	  			        <td align="center">
	  			             <%--  ${cell[9] } --%>
	  			              <input type="hidden" value="${cell[8] }"/><!--主键字段id  -->
	  			              <select class="default;" style="width: 95%;">
	  			              </select>
	  			        </td>
	  			    </tr>
	  			</s:iterator>
	          </table>
</div>
</div></div></div>
<div class="box1_bottomcenter2">
<div class="box1_bottomleft2"><div class="box1_bottomright2"></div></div></div>
</div>	
</body>
</html>




