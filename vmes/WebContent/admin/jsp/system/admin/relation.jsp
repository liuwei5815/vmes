<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/core" prefix="c"%>
<%@ include file="/admin/jsp/common/common.jsp"%>
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
		unload();
	});

	function unload(){
	    if($('#successflag').val() == "1"){//执行成功
	    	top.Dialog.alert($("#message").val());
	    	parent.frmright.doQueryTab();
			top.Dialog.close();
		}else{
			if($("#message").val()!=""){
		    	top.Dialog.alert($("#message").val());
		    	$("#message").val("");
		    }
		}
		return false;
	}

	
	
	

   
</script>
</head>
<body>
<div class="static_box1">
<div class="box1_topcenter2"><div class="box1_topleft2"><div class="box1_topright2"></div></div></div>
<div class="box1_middlecenter"><div class="box1_middleleft2"><div class="box1_middleright2">
	<div style="padding:0 20px 0 20px;">
	<input type="hidden" name="message" id="message" value="${message}" />
	<input type="hidden" name="successflag" id="successflag" value="${successflag}"/>
    <s:form action="admin/sys_tables!edit.action" method="post" theme="simple" id="frm">
    <input type="hidden" id="param_str" name="param_str"/>
      <fieldset>
	     <legend>表</legend>
	     <table class="tableStyle" style="line-height:28px;">
	     	<tr>
	     		<td>当前表(外键表)</td>
	     		<td></td>
	     	</tr>
	     	<tr>
	     		<td>主键表</td>
	     		<td></td>
	     	</tr>
	     	<tr>
	     		<td>外键名称</td>
	     		<td><input type="text" id="fkName" name="fkName"/></td>
	     	</tr>
	     </table>
	  </fieldset>
	  <fieldset>
	  		<legend>字段</legend>
	  		<table class="tableStyle" style="line-height:28px;" id="mytable">
	  			<tr>
	  				<th class="th">字段名</th>
	  				<th class="th">数据类型</th>
	  				<th class="th">长度</th>
	  				<th class="th">默认值</th>
	  				<th class="th chk">主键</th>
	  				<th class="th chk">非空</th>
	  				<th class="th chk">自增</th>
	  				<th class="th chk">表单中显示</th>
	  				<th class="th chk">列表中显示</th>
	  				<th class="th">注释</th>
	  				<th class="th">排序</th>
	  				<th class="th">可选项</th>
	  				<th class="thOper" width="10px;">操作</th>
	  				<th id="addIcon" align="center"><span class="img_add2 hand" title="添加一行" onclick="addNewRow()"></span></th>
	  			</tr>
	  			<s:iterator value="list" var="cell">
		  		   <tr>
		  			 <td>
		  			     <input type="text" value="" style="width: 95%;"/>
		  			 </td>
		  			 <td>
		  			     <select id="c_dataTypes" name="c_dataTypes" class="default;" onchange="selectType(this)">
		  			        <option value=""></option>
						</select>
		  			 </td>
		  			 <td><input type="text" value="" style="width: 95%;"/></td>
		  			 <td><input type="text" value="" style="width: 95%;"/></td>
		  			 <td>
		  			     <input type="checkbox" value=""  onclick="val_click(this)"/>
		  			 </td>
		  			 <td>
		  			     <input type="checkbox" value=""  />
		  			 </td>
		  			 <td>
		  			     <input type="checkbox" value=""  onclick="zz_click(this)"/>
		  			 </td>
		  			 <td align='center'>
		  			      <input type="checkbox" value="" />
		  			 </td>
		  			 <td align='center'>
		  			      <input type="checkbox" value="" />
		  			 </td>
		  			 <td><input type="text" value="" style="width: 95%;"/></td>
		  			 <td><input type="text" value="" name="c_orderBy" style="width: 95%;"/></td>
		  			 <td><input type="text" value="" name="c_choose" attrTxt="" style="width: 95%;"/></td>
		  			 <td width="10px;" align="center">
		  			    <a href="javascript:void(0)" style="color: blue;text-decoration: underline;" onclick="saveData(this)">保存</a>
		  			    <a href="javascript:void(0)" style="color: blue;text-decoration: underline;" onclick="delData(this)">删除</a>
		  			 </td>
		  			 <td align='center'><span class="img_remove2 hand" title="删除该行" onclick="delRow(this)"></span></td>
		  		   </tr>
	  			</s:iterator>
	  		</table>
	  </fieldset>
	
</s:form>
</div>
</div></div></div>
<div class="box1_bottomcenter2">
<div class="box1_bottomleft2"><div class="box1_bottomright2"></div></div></div>
</div>	
</body>
</html>




