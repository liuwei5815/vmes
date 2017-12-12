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
	function doSubmit(){
		var t_name = $("#t_name").val();
		if(t_name == ""){
			top.Dialog.alert("请输入表名");
			return;
		}
		
		var name=document.getElementsByName("name");
		var dataType=document.getElementsByName("c_dataTypes");
		var len=document.getElementsByName("c_lens");
		var orderBy=document.getElementsByName("c_orderBy");
		var choose=document.getElementsByName("c_choose");
		
		for(var i=0;i<name.length;i++){
			if(name[i].value==""){
				top.Dialog.alert("第"+(i+1)+"行的字段名不能为空");
				return false;
			}
		}
		
		
		for(var i=0;i<orderBy.length;i++){
			if(orderBy[i].value==""){
				top.Dialog.alert("第"+(i+1)+"行的排序不能为空");
				return false;
			}
		}
		
		
		for(var i=0;i<len.length;i++){
			if(len[i].value==""){
				if($(len[i]).attr("disabled")!=true){
					top.Dialog.alert("第"+(i+1)+"行的长度不能为空");
					return false;
				}
				
			}
		}
		
		for(var i=0;i<choose.length;i++){
			if(choose[i].value==""){
				if($(choose[i]).attr("attrTxt")=="choose"){
					top.Dialog.alert("第"+(i+1)+"行的可选项不能为空");
					return false;
				}
				
			}
		}
		
		var vals = [];
		var n=0;
		$("#mytable").find("tr:gt(0)").each(function(){
			var c_name=$(this).find("td").eq(0).find("input:eq(0)").val();
			var type = $(this).find("td").eq(1).find("select:eq(0)").val();
			var len = $(this).find("td").eq(2).find("input:eq(0)").val();
			var defaults = $(this).find("td").eq(3).find("input:eq(0)").val();
			var prikey = $(this).find("td").eq(4).find("input:eq(0)").attr("checked")==true?"1":"0";
			var notnull = $(this).find("td").eq(5).find("input:eq(0)").attr("checked")==true?"1":"0";
			var zizeng = $(this).find("td").eq(6).find("input:eq(0)").attr("checked")==true?"1":"0";
			var showinform = $(this).find("td").eq(7).find("input:eq(0)").attr("checked")==true?"1":"0";
			var showinlist = $(this).find("td").eq(8).find("input:eq(0)").attr("checked")==true?"1":"0";
			var des = $(this).find("td").eq(9).find("input:eq(0)").val();
			var orderByText = $(this).find("td").eq(10).find("input:eq(0)").val();
			var chooseText = $(this).find("td").eq(11).find("input:eq(0)").val();
			if(chooseText==""){
				chooseText=" ";
			}
			var str=c_name+"#"+type+"#"+len+"#"+defaults+"#"+prikey+"#"+notnull+"#"+zizeng+"#"+showinform+"#"+showinlist+"#"+des+"#"+orderByText+"#"+chooseText;
			vals.push(str);
			n+=parseInt(prikey);
		});
		
		if(n>=2){
			top.Dialog.alert("你只能设置一个主键");
			return false;
		}
		
		$("#param_str").val(vals);
		$("#frm").submit();
		/*
		$.ajax({
		   url:"${ctx}/admin/sys_tables!add.action",
		   dataType:"json",
		   type:"post",
		   data:{
		     "tables.name":$("#t_name").val(),
		     "tables.nameCn":$("#t_namecn").val(),
		     "tables.des":$("#des").val(),
		     "par_str":vals
		   },
		   success:function(data){
		      if(data.code=="0"){
		    	  top.Dialog.alert(data.msg);
		    	  parent.frmright.doQueryTab();
		    	  top.Dialog.close();
		      }else{
		    	  top.Dialog.alert(data.msg);
		      }
		   }
		});*/
		
	}
	var i=0;
	function addNewRow(){
		//创建第一列文本框
		var $textname=$('<input type="text" id="c_names" class="textinput" name="name"/>');
		//创建第二列
		var selbegin='<select id="c_dataTypes" name="c_dataTypes" class="default;" onchange="selectType(this)"><option value=""></option>';
		var selend = '<c:forEach var="stu" items="${requestScope.columnTypeList}"><option value="${stu.code}">${stu.name}</option></c:forEach></select>';
		var $selecttype=$(selbegin+selend);
		//创建第三列
	    var $textlen =$('<input type="text" id="c_lens" class="textinput" name="c_lens"/>');
		//创建第四列
		var $textdefault = $('<input type="text" id="c_defaults" class="textinput" name="c_defaults" onblur="checkDef(e)"/>');
		//创建第五列
		var $checkprikey = $('<input type="checkbox" id="c_primarykeys" name="c_primarykeys" value="1" onclick="val_click(this)"/>');
		//创建第六列
		var $checknotnull = $('<input type="checkbox" id="c_notnulls"  name="c_notnulls" value="1"/>');
		//创建第七列
		var $checkzizeng = $('<input type="checkbox" id="c_zizengs"  name="c_zizengs" value="1" onclick="zz_click(this)"/>');
		//创建第八列
		var $checkshowinform = $('<input type="checkbox" id="c_showinforms" name="c_showinforms" value="1"/>');
		//创建第九列
		var $checkshowinlist = $('<input type="checkbox" id="c_showinlists" name="c_showinlists" value="1"/>');
		//创建第十列
		var $textdess = $('<input type="text" id="c_dess" name="c_dess" class="textinput"/> ');
		//创建第十一列
		var $textorderBy = $('<input type="text" id="c_orderBy" name="c_orderBy" class="textinput"/> ');
		//创建第十二列
		var $chooseTxt=$('<input type="text" id="c_choose" name="c_choose" attrTxt="" class="textinput"/> ');
		//创建按钮 第十三列
		var $delBtn=$('<span class="img_remove2 hand" title="删除该行"></span>');
		//创建表格行
		var $tr=$("<tr><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td align='center'></td><td align='center'></td><td></td><td></td><td></td><td align='center'></td></tr>");
		$tr.find("td").eq(0).append($textname);
		$tr.find("td").eq(1).append($selecttype);
		$tr.find("td").eq(2).append($textlen);
		$tr.find("td").eq(3).append($textdefault);
		$tr.find("td").eq(4).append($checkprikey);
		$tr.find("td").eq(5).append($checknotnull);
		$tr.find("td").eq(6).append($checkzizeng);
		$tr.find("td").eq(7).append($checkshowinform);
		$tr.find("td").eq(8).append($checkshowinlist);
		$tr.find("td").eq(9).append($textdess);
		$tr.find("td").eq(10).append($textorderBy);
		$tr.find("td").eq(11).append($chooseTxt);
		$tr.find("td").eq(12).append($delBtn);
		$("#mytable").append($tr);
		i++;
		$delBtn.click(function(){
			//将所在的行删除
			$(this).parents("tr").remove();
			i--;
		});
	}
	
	//勾选主键checkbox
	function val_click(e){
		if($(e).attr("checked")==true){
			$(e).parents("tr").find("td:eq(5) input:eq(0)").attr("checked",true);
		}
	}
	
	//勾选自增checkbox
	function zz_click(e){
		if($(e).attr("checked")==true){
			$(e).parents("tr").find("td:eq(4) input:eq(0)").attr("checked",true);
			$(e).parents("tr").find("td:eq(5) input:eq(0)").attr("checked",true);
		}
	}
	
	//选择数据类型下拉框
	function selectType(e){
		var txt=$(e).val();
		if(txt==3 || txt==11 || txt==4 || txt==5){
			$(e).parents("tr").find("td:eq(2) input:eq(0)").attr("disabled",true);
		}else{
			$(e).parents("tr").find("td:eq(2) input:eq(0)").attr("disabled",false);
		}
		
		if(txt==7 || txt==8 || txt==9 || txt==10){
			$(e).parents("tr").find("td:eq(11) input:eq(0)").attr("attrTxt","choose");
		}
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
    <s:form action="admin/sys_tables!add.action" method="post" theme="simple" id="frm">
    <input type="hidden" id="param_str" name="param_str"/>
      <fieldset>
	     <legend>表</legend>
	     <table class="tableStyle" style="line-height:28px;">
	     	<tr>
	     		<td width="10%">
	     			表名：<span class="star">*</span>
	     		</td>
	     		<td width="30%">
	     		  <input id="t_name" name="tables.name" style="width:98%;"  class="validate[required]"/>
	     	    </td>
	     	    <td width="10%">
	     			表名中文意思：
	     		</td>
	     		<td  width="30%">
	     		  <input id="t_namecn" name="tables.nameCn" style="width:98%;" />
	     	    </td>
	     	</tr>
	     	<tr>
	     		<td>说明：</td>
	     		<td colspan="3">
	     			<textarea rows="3" cols="4" name="tables.des" id="des"></textarea>
	     		</td>
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
	  				<th id="addIcon" align="center"><span class="img_add2 hand" title="添加一行" onclick="addNewRow()"></span></th>
	  			</tr>
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




