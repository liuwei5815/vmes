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

/*效果开始*/
.selectbox{width:500px;height:220px;}
.selectbox div{float:left;}
.selectbox .select-bar{padding:0 20px;}
.selectbox .select-bar select{width:150px;height:200px;border:1px #A0A0A4 solid;padding:4px;font-size:14px;font-family:"microsoft yahei";}
.btn-bar{}
.btn-bar p{margin-top:16px;}
.btn-bar p .btn{width:50px;height:30px;cursor:pointer;font-family:simsun;font-size:14px;}
/*效果结束*/
</style>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>编辑视图</title>
<script type="text/javascript">
	jQuery(document).ready(function(){
		//移到右边
		$('#add').click(function(){
			//先判断是否有选中
			if(!$("#select1 option").is(":selected")){			
				top.Dialog.alert("请选择需要移动的选项");
			}
			//获取选中的选项，删除并追加给对方
			else{
				$('#select1 option:selected').appendTo('#select2');
			}	
		});
		
		//移到左边
		$('#remove').click(function(){
			//先判断是否有选中
			if(!$("#select2 option").is(":selected")){			
				top.Dialog.alert("请选择需要移动的选项")
			}
			else{
				$('#select2 option:selected').appendTo('#select1');
			}
		});
		
		//全部移到右边
		$('#add_all').click(function(){
			//获取全部的选项,删除并追加给对方
			$('#select1 option').appendTo('#select2');
		});
		
		//全部移到左边
		$('#remove_all').click(function(){
			$('#select2 option').appendTo('#select1');
		});
		
		//双击选项
		$('#select1').dblclick(function(){ //绑定双击事件
			//获取全部的选项,删除并追加给对方
			$("option:selected",this).appendTo('#select2'); //追加给对方
		});
		
		//双击选项
		$('#select2').dblclick(function(){
			$("option:selected",this).appendTo('#select1');
		});
	});
	
	function backToView(){
		window.location.href="${ctx}/admin/view!init.action";
	}
	
	function selectInfo(){
		var diag = new top.Dialog();
		diag.Title = "双击下面表格行--选择信息集";
		diag.URL = "${ctx}/admin/view!getTableInfo.action";
		diag.Height=900;
		diag.Width=900;
		diag.show();
	}
	
	function setTableInfo(id,nameCn){
		var html="<input type='radio' value="+id+" />"+nameCn;
		$("#tabInfo").html(html);
		//加载当前表的字段
		var sel_html="";
		$.ajax({
			url:"${ctx}/admin/sys_tables!loadColByTabId.action?tableId="+id,
			dataType:"json",
			type:"post",
			success:function(data){
				for(var i=0;i<data.length;i++){
					sel_html+="<option value="+data[i].id+">"+(data[i].name+'('+data[i].nameCn+')')+"</option>";
				}
				
				//条件
				$("#mytable_3 tr").each(function(){
					var select_obj=$(this).find("td:eq(1) select:eq(0)");
					select_obj.html("");
					select_obj.append("<option value='0'>--无--</option>"+sel_html);
				});
				
				//显示字段
				$("#select1").html(sel_html);
				
				//排序
				$(".order_tr").each(function(){
					var select_obj=$(this).find("td:eq(1) select:eq(0)");
					select_obj.html("");
					select_obj.append("<option value='0'>--无--</option>"+sel_html);
				});
			}
		});
		
		
	}
	
	function hideAdvanceTxt(){
		var sta=$(".a_class:eq(0)").attr("id");
		if(sta=="showSta"){
			$("#advanceTxt").hide();
			$(".a_class:eq(0)").text("显示高级条件");
			$(".a_class:eq(0)").attr("id","hideSta");
		}else{
			$("#advanceTxt").show();
			$(".a_class:eq(0)").text("隐藏高级条件(只作用于默认条件下)");
			$(".a_class:eq(0)").attr("id","showSta");
		}
	}
	
	function saveView(){
		//验证
		var viewName=$("#viewName").val();//视图名称
		var tabInfo_radio=$("#tabInfo").find("input:eq(0)");//选择信息集的radio标签
		var column_selected=$("#select2").find("option");//已选择列的集合
		if(viewName==""){
			top.Dialog.alert("请输入视图名称");
			return false;
		}
		if(tabInfo_radio.size()==0){
			top.Dialog.alert("请选择表");
			return false;
		}
		if(column_selected.size()==0){
			top.Dialog.alert("请选择列");
			return false;
		}
		
		//封装条件
		var condition_def=[];
		$(".tr_for_def").each(function(){
			var con_value=$(this).find("td:eq(0) input:eq(0)").val();//条件代号
			var column_id=$(this).find("td:eq(1) option:selected").val();//列id
			var rule=$(this).find("td:eq(2) option:selected").val();//运算符代号
			var rule_val=$(this).find("td:eq(3) input:eq(0)").val();//运算值
			var type=2;
			if(rule_val=="") rule_val=" ";
			var str=con_value+"#"+column_id+"#"+rule+"#"+rule_val+"#"+type;
			condition_def.push(str);
		});
		
		var condition_dt=[];
		$(".tr_for_dt").each(function(){
			var con_value=$(this).find("td:eq(0) input:eq(0)").val();//条件代号
			var column_id=$(this).find("td:eq(1) option:selected").val();//列id
			var rule=$(this).find("td:eq(2) option:selected").val();//运算符代号
			var rule_val=$(this).find("td:eq(3) input:eq(0)").val();//运算值
			var type=1;
			if(rule_val=="") rule_val=" ";
			var str=con_value+"#"+column_id+"#"+rule+"#"+rule_val+"#"+type;
			condition_dt.push(str);
		});
		
		$("#ruleHid_def").val(condition_def);
		$("#ruleHid_dt").val(condition_dt);
		//封装显示列
		var column=[];
		$("#select2 option").each(function(){
			column.push($(this).val());
		});
		$("#columnHid").val(column);
		
		//封装排序
		var order=[];
		$(".order_tr").each(function(){
			var columnId=$(this).find("td:eq(1) option:selected").val();
			var order_type=$(this).find("td:eq(2) option:selected").val();
			order.push(columnId+"#"+order_type);
		});
		$("#orderHid").val(order);
		$.ajax({
			url:"${ctx}/admin/view!saveView.action",
			type:"post",
			data:{
				"tableId":tabInfo_radio.val(),
				"searchRole":$("#advanceTxt").val(),
				"name":viewName,
				"ruleStr_def":$("#ruleHid_def").val(),
				"ruleStr_dt":$("#ruleHid_dt").val(),
				"columnStr":$("#columnHid").val(),
				"orderStr":$("#orderHid").val(),
				"flag":"edit",
				"viewName_old":$("#viewName_hid").val(),
				"viewId":$("#viewId_hid").val()
			},
			dataType:"json",
			success:function(data){
				if(data.code==1){
					top.Dialog.alert(data.msg);
					backToView();
				}else{
					top.Dialog.alert(data.msg);
				}
			}
		});
	}
</script>
</head>
<body>
<input type="hidden" id="ruleHid_def"/>
<input type="hidden" id="ruleHid_dt"/>
<input type="hidden" id="columnHid"/>
<input type="hidden" id="orderHid"/>
<div id="outDiv" style="height: 690px;overflow:scroll; ">
<div class="position">
		<div class="center">
		<div class="left">
		<div class="right">
			<span>当前位置：系统管理 >>视图管理>>编辑视图  </span>	</div>	
		</div>	
		</div>
	</div>
<div class="static_box1" style="height: 500px;overflow: scroll;">
<div class="box1_topcenter2"><div class="box1_topleft2"><div class="box1_topright2"></div></div></div>
<div class="box1_middlecenter"><div class="box1_middleleft2"><div class="box1_middleright2">
	<div style="padding:0 20px 0 20px;">
	  <div style="margin:0px auto;width: 200px;height: auto;">
		       <input type="button" value="保存" onclick="saveView()"/>
		       <input type="button" value="返回" onclick="backToView()"/>
	  </div>
      <fieldset>
	     <legend>步骤1。填写视图名称：</legend>
	     <table class="tableStyle" style="line-height:28px;">
	     	<tr>
	     		<td>名称：<span class="star">*</span>
	     		 <input type="text" id="viewName" name="viewName" value="${map['view'].name }" style="width: 30%;"/>
	     		 <input type="hidden" id="viewName_hid" value="${map['view'].name }"/>
	     		 <input type="hidden" id="viewId_hid" value="${map['view'].id }"/>
	     		</td>
	     	</tr>
	     </table>
	  </fieldset>
	  <fieldset>
	  		<legend>步骤2。选定表：</legend>
	  		<table class="tableStyle" style="line-height:28px;" id="mytable">
	  			
		  		<tr>
		  			 <td width="60px">
		  			    <button onclick="selectInfo()" type="button">
		  			    	<span class="icon_add">选定表<span class="star">*</span></span>
		  			    </button>
		  			    
		  			 </td>
		  			 <td>
			  			 <span id="tabInfo">
			  			    <input type='radio' value="${map['table'].id }" />${map['table'].nameCn}
			  			 </span>
		  			 </td>
		  		 </tr>
	  		</table>
	  </fieldset>
	  
	  <fieldset>
	          <legend>步骤3。选择搜索条件：</legend>
	          <table>
	                 <tr>
	                      <td>
	                      <center><h2 style="margin-bottom:10px;">默认条件</h2></center>
	                            <table class="tableStyle table_edit_tr" style="line-height:28px;width:500px;" id="mytable_3" >
	           
			  					  <s:iterator begin="0" end="4" var="var">
			  					    <s:iterator value="#request.map['list_search_def']" var="cell">
			  					    
			  					    <s:if test="#cell[5]==(#var+1)">
			  					      <tr class="tr_for_def">
					  			        <td align="center" width="10px">
					  			             ${cell[5]}
					  			             <input type="hidden" value="${cell[5]}"/>
					  			        </td>
					  			        <td align="center" width="100px">
					  			            <select class="default;" style="width: 95%;">
					  			                 <option value='0'>--无--</option>
					  			                 <s:iterator value="#request.map['list_colAll']" var="cell_col">
					  			                    <option value="${cell_col[0] }" <s:if test="#cell_col[0]==#cell[0]">selected</s:if> >${cell_col[1] }(${cell_col[2] })</option>
					  			                 </s:iterator> 
							  			     </select>
					  			        </td>
					  			        <td align="center" width="50px">
					  			            <select class="default;" style="width: 95%;">
					  			            		  <option value="0" <s:if test="#cell[3]==0">selected</s:if>>无</option>
							  			              <option value="1" <s:if test="#cell[3]==1">selected</s:if>>等于</option>
							  			              <option value="2" <s:if test="#cell[3]==2">selected</s:if>>大于</option>
							  			              <option value="3" <s:if test="#cell[3]==3">selected</s:if>>大于等于</option>
							  			              <option value="4" <s:if test="#cell[3]==4">selected</s:if>>小于</option>
							  			              <option value="5" <s:if test="#cell[3]==5">selected</s:if>>小于等于</option>
							  			              <option value="6" <s:if test="#cell[3]==6">selected</s:if>>不等于</option>
							  			              <option value="7" <s:if test="#cell[3]==7">selected</s:if>>包含</option>
							  			              <option value="8" <s:if test="#cell[3]==8">selected</s:if>>起始字符</option>
							  			              <option value="9" <s:if test="#cell[3]==9">selected</s:if>>不包含</option>
							  			     </select>
					  			        </td>
					  			        <td width="100px">
					  			             <input type="text" id="ruleValue" style="width: 95%;" value="${cell[4] }"/>
					  			        </td>
					  			    </tr>
			  					    </s:if>
			  					    <%-- <s:else>
			  					      <tr class="tr_for_def">
					  			        <td align="center" width="10px">
					  			             ${var+1}
					  			             <input type="hidden" value="${var+1}"/>
					  			        </td>
					  			        <td align="center" width="100px">
					  			            <select class="default;" style="width: 95%;">
					  			                 <option value='0'>--无--</option>
					  			                 <s:iterator value="#request.map['list_colAll']" var="cell_col">
					  			                    <option value="${cell_col[0] }">${cell_col[1] }(${cell_col[2] })</option>
					  			                 </s:iterator>
							  			     </select>
					  			        </td>
					  			        <td align="center" width="50px">
					  			            <select class="default;" style="width: 95%;">
					  			            		  <option value="0">无</option>
							  			              <option value="1">等于</option>
							  			              <option value="2">大于</option>
							  			              <option value="3">大于等于</option>
							  			              <option value="4">小于</option>
							  			              <option value="5">小于等于</option>
							  			              <option value="6">不等于</option>
							  			              <option value="7">包含</option>
							  			              <option value="8">起始字符</option>
							  			              <option value="9">不包含</option>
							  			     </select>
					  			        </td>
					  			        <td width="100px">
					  			             <input type="text" id="ruleValue" style="width: 95%;"/>
					  			        </td>
					  			    </tr>
			  					   </s:else> --%>
					  			   
					  			</s:iterator>
			  			    </s:iterator>
			          </table>
			          <!--  -->
	          </td>
	          <td>
	          <center><h2 style="margin-bottom:10px;">动态条件</h2></center>
	                          <table class="tableStyle table_edit_tr" style="line-height:28px;width:500px;" id="mytable_3" >
	                           <%--  <s:iterator begin="0" end="4" var="var"> --%>
			  					  <s:iterator value="#request.map['list_search_dt']" var="cell" status="sta">
			  					    <%-- <s:if test="#cell[5]==(#var+1)"> --%>
			  					      <tr class="tr_for_dt">
					  			        <td align="center" width="10px">
					  			             ${sta.index+1}
					  			             <input type="hidden" value="${cell[5]}"/>
					  			        </td>
					  			        <td align="center" width="100px">
					  			            <select class="default;" style="width: 95%;">
					  			                 <option value='0'>--无--</option>
					  			                 <s:iterator value="#request.map['list_colAll']" var="cell_col">
					  			                    <option value="${cell_col[0] }" <s:if test="#cell_col[0]==#cell[0]">selected</s:if> >${cell_col[1] }(${cell_col[2] })</option>
					  			                 </s:iterator> 
							  			     </select>
					  			        </td>
					  			        <td align="center" width="50px">
					  			            <select class="default;" style="width: 95%;">
					  			            		  <option value="0" <s:if test="#cell[3]==0">selected</s:if>>无</option>
							  			              <option value="1" <s:if test="#cell[3]==1">selected</s:if>>等于</option>
							  			              <option value="2" <s:if test="#cell[3]==2">selected</s:if>>大于</option>
							  			              <option value="3" <s:if test="#cell[3]==3">selected</s:if>>大于等于</option>
							  			              <option value="4" <s:if test="#cell[3]==4">selected</s:if>>小于</option>
							  			              <option value="5" <s:if test="#cell[3]==5">selected</s:if>>小于等于</option>
							  			              <option value="6" <s:if test="#cell[3]==6">selected</s:if>>不等于</option>
							  			              <option value="7" <s:if test="#cell[3]==7">selected</s:if>>包含</option>
							  			              <option value="8" <s:if test="#cell[3]==8">selected</s:if>>起始字符</option>
							  			              <option value="9" <s:if test="#cell[3]==9">selected</s:if>>不包含</option>
							  			     </select>
					  			        </td>
					  			        <td width="100px">
					  			             <input type="text" id="ruleValue" style="width: 95%;" value="${cell[4] }"/>
					  			        </td>
					  			    </tr>
			  					    <%-- </s:if> --%>
			  					     <%-- <s:else>
			  					          <tr class="tr_for_dt">
					  			        <td align="center" width="10px">
					  			             ${var+1}
					  			             <input type="hidden" value="${var+1}"/>
					  			        </td>
					  			        <td align="center" width="100px">
					  			            <select class="default;" style="width: 95%;">
					  			                 <option value='0'>--无--</option>
					  			                 <s:iterator value="#request.map['list_colAll']" var="cell_col">
					  			                    <option value="${cell_col[0] }">${cell_col[1] }(${cell_col[2] })</option>
					  			                 </s:iterator>
							  			     </select>
					  			        </td>
					  			        <td align="center" width="50px">
					  			            <select class="default;" style="width: 95%;">
					  			            		  <option value="0">无</option>
							  			              <option value="1">等于</option>
							  			              <option value="2">大于</option>
							  			              <option value="3">大于等于</option>
							  			              <option value="4">小于</option>
							  			              <option value="5">小于等于</option>
							  			              <option value="6">不等于</option>
							  			              <option value="7">包含</option>
							  			              <option value="8">起始字符</option>
							  			              <option value="9">不包含</option>
							  			     </select>
					  			        </td>
					  			        <td width="100px">
					  			             <input type="text" id="ruleValue" style="width: 95%;"/>
					  			        </td>
					  			    </tr>
			  					    </s:else> --%>
					  			   
					  			    </s:iterator>
			  			    <%-- </s:iterator> --%>
			          </table>
	               </td> 
	            </tr>
	            <tr>
	                <td colspan="1">
		                <table>
				                 <tr>
				                     <td width="300px">
				                       <a id="showSta" class="a_class" style="text-decoration: underline;" href="javascript:void(0)" onclick="hideAdvanceTxt()">隐藏高级条件(只作用于默认条件下)</a>
				                       <input type="text" id="advanceTxt" style="width: 90%;" value="${map['view'].searchRole }"/>
				                     </td>
				                 </tr>
				                 <tr>
				                     <td>可以使用OR,AND,NOT和英文符号将上列条件组成逻辑关系。例如：(1 AND 2 ) OR 3表示第一和第二条件与关系后，再和第三个条件或运算。</td>
				                 </tr>
				          </table>
			          </td>
			          <td>
			              
			          </td>            
	            </tr>
	  </table>
	          
	  </fieldset>
	  
	  <fieldset>
	    <legend>步骤4。选择要显示的列(双击选择)：<span class="star">*</span></legend>
	    <div class="selectbox" style="border: none;background: white;">
				<div class="select-bar">
				    <select multiple="multiple" id="select1">
				        <s:iterator value="#request.map['list_col_not']" var="cell">
				            <option value="${cell[0] }">${cell[1] }(${cell[2] })</option>
				        </s:iterator>
				    </select>
				</div>

				<div class="btn-bar">
				    <p><span id="add"><input type="button" class="btn" value=">" title="移动选择项到右侧"/></span></p>
				    <p><span id="add_all"><input type="button" class="btn" value=">>" title="全部移到右侧"/></span></p>
				    <p><span id="remove"><input type="button" class="btn" value="<" title="移动选择项到左侧"/></span></p>
				    <p><span id="remove_all"><input type="button" class="btn" value="<<" title="全部移到左侧"/></span></p>
				</div>
				
				<div class="select-bar">
				    <select multiple="multiple" id="select2">
				       <s:iterator value="#request.map['list_col_selected']" var="cell">
				            <option value="${cell[0] }">${cell[1] }(${cell[2] })</option>
				        </s:iterator>
				    </select>
				</div>	
       </div>
        <table id="mytable_4">
               <tr>
                   <td style="text-align: right;border: none;">默认选定搜索列:</td>
                   <td style=""><select id="default_all_column" class="default;"><option value="">--全部列--</option></select></td>
               </tr>
               <%-- <s:iterator begin="0" end="4" var="var"> --%>
                   <s:iterator value="#request.map['list_order']" var="cell" status="sta">
	                   <tr class="order_tr">
	                       <td style="text-align: right;">默认排序${sta.index+1 }:</td>
	                       <td width="" style="">
	                          <select class="default;">
	                            <option value="">--全部列--</option>
	                              <s:iterator value="#request.map['list_colAll']" var="cell_col">
					  			      <option value="${cell_col[0] }" <s:if test="#cell_col[0]==#cell[0]">selected</s:if> >${cell_col[1] }(${cell_col[2] })</option>
					  			  </s:iterator>
	                          </select>
	                       </td>
	                       <td style="">
	                          <select class="default;">
	                             <option value="1" <s:if test="#cell[3]==1">selected</s:if> >升序</option>
	                             <option value="2" <s:if test="#cell[3]==2">selected</s:if> >降序</option>
	                          </select>
	                       </td>
	                   </tr>
                   </s:iterator>
               <%-- </s:iterator> --%>
        </table>
	  </fieldset>
	  
	  <div style="margin:0px auto;width: 200px;height: auto;margin-top: 10px;">
		       <input type="button" value="保存" onclick="saveView()"/>
		       <input type="button" value="返回" onclick="backToView()"/>
	  </div>
</div>
</div></div></div>
<div class="box1_bottomcenter2">
<div class="box1_bottomleft2">
<div class="box1_bottomright2"></div>
</div>
</div>
</div>

</div>
</body>
</html>




