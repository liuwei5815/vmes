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
	});

	

	
	function selectTable(){
		var tableId=$("#c_table").val();
		$.ajax({
			url:"${ctx}/admin/sys_tables!loadColByTabId.action?tableId="+tableId,
			dataType:"json",
			type:"post",
			success:function(data){
				var option_html="";
				var dd=data.listColumn;
				for(var i=0;i<data.listColumn.length;i++){
					option_html+="<option value='"+dd[i].id+"'>"+dd[i].name+"</option>";
				}
				$("#c_column").html(option_html);
				$("#c_show_column").html(option_html);
				//$("#c_column").append(option_html);
				//$("#c_show_column").append(option_html);
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
	   var target_show_colId=$("#c_show_column").val();
	   var c_col_relation=$("#c_col_relation").val();//对应关系
	   var c_col_addType=$("#c_col_addType").val();//添加方式
	   
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
	   
	   if(c_col_relation=="" || c_col_relation==null){
		   top.Dialog.alert("请选择对应关系");
		   return false;
	   }
	   
	   if(c_col_addType=="" || c_col_addType==null){
		   top.Dialog.alert("请选择添加关系");
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
			   "ship.targetColumnId":target_fk_colId,
			   "ship.targetShowColumnId":target_show_colId,
			   "ship.type":c_col_relation,
			   "ship.fs":c_col_addType
		   },
		   success:function(data){
			   if(data.code=="1"){
				   top.Dialog.alert(data.msg);
			    	  $("#nodata_tr").remove();
			    	  $(".table_edit_tr tbody").append($("#tr_clone_model").clone());
			    	  
			    	  //外键名称
			    	  var td_html="<input type='text' value='"+fkname+"' style='width:95%;' name='"+fkname+"'/>";
			    	  td_html+="<input type='hidden' value='"+data.shipId+"' /><input type='hidden' value='"+fkname+"' />";
			    	  $(".table_edit_tr tbody").find("tr:last td:eq(0)").html(td_html);
			    	  
			    	  //外键表
			    	  td_html=$("#source_table_name").val()+"<input type='hidden' value='"+$("#source_table_name").val()+"'/><input type='hidden' value='"+source_tabId+"'/> ";
			    	  $(".table_edit_tr tbody").find("tr:last td:eq(1)").html(td_html);
			    	  
			    	  td_html="<input type='hidden' value='"+sourse_fk_colId+"'/><select class='default;' style='width: 95%;'>";
			    	  var listCol=data.listCol;
			    	  for(var i=0;i<listCol.length;i++){
			    		  if(listCol[i].id == sourse_fk_colId){
			    			  td_html+="<option value='"+listCol[i].id+"' selected='selected' >"+listCol[i].name+"</option>";
			    		  }else{
			    			  td_html+="<option value='"+listCol[i].id+"'>"+listCol[i].name+"</option>";
			    		  }
			    	  }
			    	  td_html+="</select>";
			    	  $(".table_edit_tr tbody").find("tr:last td:eq(2)").html(td_html);
			    	  
			    	  td_html="<input type='hidden' value='"+target_fk_colId+"'/>";
			    	  td_html+="<select class='default;' style='width: 95%;' onchange='chooseCol(this)'>";
			    	  var list=data.list;
			    	  for(var i=0;i<list.length;i++){
			    		  if(list[i].id == target_tabId){
			    			  td_html+="<option value='"+list[i].id+"' selected>"+list[i].name+"</option>";
			    		  }else{
			    			  td_html+="<option value='"+list[i].id+"'>"+list[i].name+"</option>";
			    		  }
			    	  }
			    	  td_html+="</select>";
			    	  $(".table_edit_tr tbody").find("tr:last td:eq(3)").html(td_html);
			    	  
			    	  td_html_f="<input type='hidden' value='"+target_tabId+"'/>";
			    	  td_html="<select class='default;' style='width: 95%;'>";
			    	  var map=data.tableColListMap;
			    	  var tab_list=map[target_tabId];
			    	  for(var i=0;i<tab_list.length;i++){
			    		  if(tab_list[i].id == target_fk_colId){
			    			  td_html+="<option value='"+tab_list[i].id+"' selected='selected' >"+tab_list[i].name+"</option>";
			    		  }else{
			    			  td_html+="<option value='"+tab_list[i].id+"'>"+tab_list[i].name+"</option>";
			    		  }
			    	  }
			    	  td_html+="</select>";
			    	  $(".table_edit_tr tbody").find("tr:last td:eq(4)").html(td_html_f+td_html);
			    	  
			    	  td_html="<select class='default;' style='width: 95%;'>";
			    	  for(var i=0;i<tab_list.length;i++){
			    		  if(tab_list[i].id == target_show_colId){
			    			  td_html+="<option value='"+tab_list[i].id+"' selected='selected' >"+tab_list[i].name+"</option>";
			    		  }else{
			    			  td_html+="<option value='"+tab_list[i].id+"'>"+tab_list[i].name+"</option>";
			    		  }
			    	  }
			    	  td_html+="</select>";
			    	  $(".table_edit_tr tbody").find("tr:last td:eq(5)").html(td_html);
			    	  
			    	  
			    	  //type 对应关系
			    	  var type_html="<select class='default;' style='width: 95%;'>";
			    	  if(c_col_relation==1){
			    		  type_html+="<option value='1' selected='selected'>1对1</option>";
				    	  type_html+="<option value='2'>1对多</option>";
				    	  type_html+="<option value='3'>多对多</option>";
			    	  }else if(c_col_relation==2){
			    		  type_html+="<option value='1'>1对1</option>";
				    	  type_html+="<option value='2' selected='selected'>1对多</option>";
				    	  type_html+="<option value='3'>多对多</option>";
			    	  }else if(c_col_relation==3){
			    		  type_html+="<option value='1'>1对1</option>";
				    	  type_html+="<option value='2'>1对多</option>";
				    	  type_html+="<option value='3' selected='selected'>多对多</option>";
			    	  }
			    	  
			    	  type_html+="</select>";
			    	  $(".table_edit_tr tbody").find("tr:last td:eq(6)").html(type_html);
			    	  
			    	  //fs 添加方式 
			    	  var fs_html="<select class='default;' style='width: 95%;'>";
			    	  if(c_col_addType==1){
			    		  fs_html+="<option value='1' selected='selected'>在从表中选择主表 </option>";
			    		  fs_html+="<option value='2'>在主表中添加从表</option>";
			    	  }else if(c_col_addType==2){
			    		  fs_html+="<option value='1'>在从表中选择主表 </option>";
			    		  fs_html+="<option value='2' selected='selected'>在主表中添加从表</option>";
			    	  }
			    	  $(".table_edit_tr tbody").find("tr:last td:eq(7)").html(fs_html);
			    	  
			    	  $("#c_table").val("");
			    	  $("#fkName").val("");
			    	  $("#c_col").val("");
			    	  $("#c_column").find("option").remove();
			    	  $("#c_show_column").find("option").remove();
			      }else{
			    	  top.Dialog.alert(data.msg);
			      }
		   }
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
				
				var obj_show=$(e).parents("tr").find("td:eq(5) select:eq(0)");
				obj_show.find("option").remove();
				obj_show.append(option_html);
			}
		});
   }
   
   function saveData(e){
	   
	   var str=[];
	   $(e).parents("tr").each(function(){
			var relation_id=$(this).find("td:eq(0) input:eq(1)").val();//关系表主键ID
			var fk_name=$(this).find("td:eq(0) input:eq(0)").val();//外键名称new
			var fk_tabName=$(this).find("td:eq(1) input:eq(0)").val();//外键表名称
			var fk_id=$(this).find("td:eq(2) select:eq(0)").val();//外键字段ID
			var pk_tabId=$(this).find("td:eq(3) select:eq(0)").val();//主键表ID
			var pk_colId=$(this).find("td:eq(4) select:eq(0)").val();//主键字段ID
			
			var show_colId=$(this).find("td:eq(5) select:eq(0)").val();
			var fk_nameOld=$(this).find("td:eq(0) input:eq(2)").val();//外键名称old
			var relation_type=$(this).find("td:eq(6) select:eq(0)").val();//对应关系
			var relation_fs=$(this).find("td:eq(7) select:eq(0)").val();//添加方式
			var paramStr=relation_id+"#"+fk_name+"#"+fk_tabName+"#"+fk_id+"#"+pk_tabId+"#"+pk_colId+"#"+fk_nameOld+"#"+show_colId+"#"+relation_type+"#"+relation_fs;
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
					//top.Dialog.close();
				}else{
					top.Dialog.alert(data.msg);
				}
			}
		});
   }
   
   function delData(e){
	   var pri_tabId=$(e).parents("tr").find("td:eq(1) input:eq(1)").val();//外键表ID
	   var relation_id=$(e).parents("tr").find("td:eq(0) input:eq(1)").val();//关系表主键ID
	   $.ajax({
			url:"${ctx}/admin/sys_tables!delRelation.action",
			dataType:"json",
			type:"post",
			data:{
				"tableId":pri_tabId,
				"relationId":relation_id
			},
			success:function(data){
				if(data.code==1){
					$(e).parents("tr").remove();
					top.Dialog.alert(data.msg);
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
	<div style="padding:0 20px 0 20px;">
    <input type="hidden" id="param_str" name="param_str"/>
    <input type="hidden" id="parStr" value=""/>
    <input type="hidden" id="source_table_name" value="${tables.name }"/>
      <fieldset>
	     <legend>表</legend>
	     <table class="tableStyle" style="line-height:28px;">
	     	<tr>
	     		<td>当前表(外键表)</td>
	     		<td>主键表</td>
	     	</tr>
	     	<tr>
	     		<td>${tables.name }<input type="hidden" value="${tables.id }" id="current_tabId"/></td>
	     		<td>
		     		<select id="c_table" name="c_table" class="default;" onchange="selectTable()" style="width: 95%;">
		     		        <option value=""></option>
			  		 	    <s:iterator value="list" var="cell">
			  			      <option value="${cell.id}">${cell.name }</option>
			  			    </s:iterator>
				    </select>
				</td>
	     	</tr>
	     	<tr>
	     		<td>外键名称</td>
	     		<td><input type="text" id="fkName" name="fkName" style="width:95%;"/></td>
	     	</tr>
	     </table>
	  </fieldset>
	  <fieldset>
	  		<legend>字段</legend>
	  		<table class="tableStyle" style="line-height:28px;" id="mytable">
	  			<tr>
	  				<th class="th">当前字段</th>
	  				<th class="th">目标字段</th>
	  				<th class="th">显示字段</th>
	  				<th class="th">对应关系</th>
	  				<th class="th">添加方式</th>
	  			</tr>
		  		<tr>
		  			 <td>
		  			    <select id="c_col" name="c_col" class="default;" style="width: 95%;">
		  			           <option value=""></option>
		  			        <s:iterator value="listCol" var="cell">
		  			           <option value="${cell.id }">${cell.name }</option>
		  			        </s:iterator>
						</select>
						</td>
		  			 <td>
		  			     <select id="c_column" name="c_column" class="default;" style="width: 95%;">
						 </select>
		  			 </td>
		  			 <td>
		  			     <select id="c_show_column" name="c_show_column" class="default;" style="width: 95%;">
						 </select>
		  			 </td>
		  			 <td>
		  			      <select id="c_col_relation" name="c_col_relation" class="default;" style="width: 95%;">
		  			           <option value=""></option>
		  			           <option value="1">1对1</option>
		  			           <option value="2">1对多</option>
		  			           <option value="3">多对多</option>
						</select>
		  			 </td>
		  			 <td>
		  			      <select id="c_col_addType" name="c_col_addType" class="default;" style="width: 95%;">
		  			           <option value=""></option>
		  			           <option value="1">在从表中选择主表</option>
		  			           <option value="2">在主表中添加从表</option>
						</select>
		  			 </td>
		  		 </tr>
		  		 <tr>
		  		 	<td colspan="5">
		  		 		<div style="float: right;">
		  		 		<input type="button" value="提交" onclick="saveRelation()"/>&nbsp;&nbsp;
		  		 		<input type="button" value="取消" onclick="javascript:top.Dialog.close();"/>
		  		 		</div>
		  		 	</td>
		  		 </tr>
	  		</table>
	  </fieldset>
	  
	  <fieldset>
	          <legend>关系编辑</legend>
	          <table class="tableStyle table_edit_tr" style="line-height:28px;" id="mytable">
	            <tr>
	  				<th class="th">外键名称</th>
	  				<th class="th">外键表</th>
	  				<th class="th">外键字段</th>
	  				<th class="th">主键表</th>
	  				<th class="th">主键字段</th>
	  				<th class="th">显示字段</th>
	  				<th class="th">对应关系</th>
	  				<th class="th">添加方式</th>
	  				<th class="th">操作</th>
	  			</tr>
	  			 <c:if test="${fn:length(listRelation) == 0}">
					<tr id="nodata_tr">
						<td colspan="9" align="center">还没添加任何外键</td>
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
	  			             <%--  ${cell[9] } --%>
	  			              <input type="hidden" value="${cell[8] }"/><!--主键字段id  -->
	  			              <select class="default;" style="width: 95%;" onchange="chooseCol(this)">
	  			                 <s:iterator value="list" var="tab_cell">
	  			                      <option value="${tab_cell.id }" <s:if test="#tab_cell.id==#cell[6]">selected</s:if>>${tab_cell.name }</option>
	  			                 </s:iterator>
	  			              </select>
	  			        </td>
	  			        <td align="center">
	  			             <%--  ${cell[7] } --%>
	  			              <input type="hidden" value="${cell[6] }"/><!--主键表id -->
	  			              
	  			              <s:iterator value="tableColListMap.keySet()" var="key">
	  			              	<s:if test="#key == #cell[6]">
	  			              		<select class="default;" style="width: 95%;">
			  			                 <s:iterator value="tableColListMap.get(#key)" var="tab_cell">
			  			                      <option value="${tab_cell.id }" <s:if test="#tab_cell.id==#cell[8]">selected</s:if>>${tab_cell.name }</option>
			  			                 </s:iterator>
			  			              </select>
	  			              	</s:if>
	  			              </s:iterator>
	  			        </td>
	  			        <td align="center">
	  			             <%--  ${cell[7] } --%>
	  			              <s:iterator value="tableColListMap.keySet()" var="key">
	  			              	<s:if test="#key == #cell[6]">
	  			              		<select class="default;" style="width: 95%;">
			  			                 <option value=""></option>
			  			                 <s:iterator value="tableColListMap.get(#key)" var="tab_cell">
			  			                      <option value="${tab_cell.id }" <s:if test="#tab_cell.id==#cell[10]">selected</s:if>>${tab_cell.name }</option>
			  			                 </s:iterator>
			  			              </select>
	  			              	</s:if>
	  			              </s:iterator>
	  			        </td>
	  			        
	  			        <td align="center" >
	  			               <select class="default;" style="width: 95%;">
	  			                     <!-- <option value=""></option> -->
		  			           		 <option value="1" <s:if test="#cell[13]==1">selected</s:if> >1对1</option>
		  			                 <option value="2" <s:if test="#cell[13]==2">selected</s:if> >多对1</option>
		  			                 <option value="3" <s:if test="#cell[13]==3">selected</s:if> >多对多</option>
	  			               </select>
	  			        </td>
	  			        
	  			        <td align="center" >
	  			               <select class="default;" style="width: 95%;">
	  			                    <!-- <option value=""></option> -->
		  			           		<option value="1" <s:if test="#cell[14]==1">selected</s:if> >在从表中选择主表</option>
		  			                <option value="2" <s:if test="#cell[14]==2">selected</s:if> >在主表中添加从表</option>
	  			               </select>
	  			        </td>
	  			        
	  			        <td align="center">
		  			        <a href="javascript:void(0)" style="color: blue;text-decoration: underline;" onclick="saveData(this)">保存</a>
			  			    <a href="javascript:void(0)" style="color: blue;text-decoration: underline;" onclick="delData(this)">删除</a>
		  			    </td>
	  			    </tr>
	  			</s:iterator>
	          </table>
	  </fieldset>
</div>
</div></div></div>
<div class="box1_bottomcenter2">
<div class="box1_bottomleft2"><div class="box1_bottomright2"></div></div></div>
</div>

<div style="display: none;">
	<table>
		<tr id="tr_clone_model" class="tr_for">
			<td align="center"></td>
			<td align="center"></td>
			<td align="center"></td>
			<td align="center"></td>
			<td align="center"></td>
			<td align="center"></td>
			<td align="center"></td>
			<td align="center"></td>
			<td align="center">
				<a href="javascript:void(0)" style="color: blue;text-decoration: underline;" onclick="saveData(this)">保存</a>
  			    <a href="javascript:void(0)" style="color: blue;text-decoration: underline;" onclick="delData(this)">删除</a>
			</td>
		</tr>
	</table>
</div>
	
</body>
</html>




