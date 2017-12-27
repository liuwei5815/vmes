<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/admin/jsp/common/common.jsp"%>
<%@ taglib uri="/tags/PageTag" prefix="pt"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctxAdmin}/js/jquery.tmpl.min.js"></script>
<script type="application/javascript" src="${ctxAdmin}/js/jstree/jstree.js?v=4.0"></script>
<link rel="stylesheet" href="${ctxAdmin}/js/jstree/themes/default/style.min.css" />
<script type="application/javascript" src="${ctxAdmin}/js/jstree/overmenu/jstree.plugin.overmenu.min.js"></script>
<link rel="stylesheet" href="${ctxAdmin}/js/jstree/overmenu/overmenu.min.css" />
<title>数据表</title>
<script >
	//修改部门
	function editDept(id) {
		console.log(id)
		var diag = new top.Dialog();
		diag.ID="model";
		diag.Title = "编辑子部门信息";
		diag.URL = "${ctx}/admin/departmentAction!preEditDepartment.action?id="+id;
		diag.Width=320;
		diag.Height=250;
		diag.show();
		return false;
	}

	/**
	 * 上移或下移部门
	 * 
	 * @param t  up/down
	 */
	function upOrDownDept(id, t) {
		$.ajax({
		     type: "POST",
		     url: "${ctx}/admin/departmentAction!upOrDownDepartment.action",
		     cache: false,
		     dataType:"json",
		     data : {
					"t" : t,
					"id" : id
			},
		     success: function(data){
	     		if(data.successflag=="1"){
	     			window.location.reload();
		     	}
	     		else{
	     			top.Dialog.alert(data.msg);
	     		}
		     }
		});	
	}

	//添加子部门
	function addDept(pid) {
		console.log(pid)
		var diag = new top.Dialog();
		diag.ID="model";
		diag.Title = "新增子部门";
		diag.URL = "${ctx}/admin/departmentAction!preAddDepartment.action?pid="+pid;
		diag.Width=320;
		diag.Height=250;
		diag.show();
		return false;
	}

	function delDept(id) {
		top.Dialog.confirm("确认删除部门?没有子部门且没有成员的部门才可以被删除",function(){
			$.ajax({
			     type: "POST",
			     url: "${ctx}/admin/departmentAction!delDepartment.action",
			     cache: false,
			     dataType:"json",
			     data:{"id":id},
			     success: function(data){
		     		if(data.successflag=="1"){
		     			window.location.reload();
			     	}
		     		else{
		     			top.Dialog.alert(data.msg);
		     		}
			     }
			});	
		});
	}
	
	//编辑公司信息
	function preEditCompany(){
		var id= $("#companyId").val();
		var diag = new top.Dialog();
		diag.ID="model";
		diag.Title = "编辑企业基本信息";
		diag.URL = "${ctx}/admin/departmentAction!preEditCompany.action?id="+id;
		diag.Width=530;
		diag.Height=550;
		diag.show();
		return false;
	}
/**
 * 定义右键菜单,根据不同的节点类型有选择性的去掉部分菜单
 */
var ctxmenus = function(o, cb) {
	var items = {
		"rename" : null,
		"ccp" : null,
		"editCompany" : {
			"separator_before" : false,
			"separator_after" : false,
			"label" : "编辑企业基本信息",
			"action" : function(data) {
				preEditCompany()
			}
		},
		"create" : {
			"separator_before" : false,
			"separator_after" : false,
			"label" : "添加子部门",
			"action" : function(data) {
				addDept(o.id)
			}
		},
		"edit" : {
			"separator_before" : false,
			"separator_after" : false,
			"label" : "修改部门",
			"action" : function(data) {
				editDept(o.id)
			}
		},
		"up" : {
			"separator_before" : false,
			"separator_after" : false,
			"label" : "上移",
			"action" : function(data) {
				upOrDownDept(o.id, "up");
			}
		},
		"down" : {
			"separator_before" : true,
			"separator_after" : false,
			"label" : "下移",
			"action" : function(data) {
				upOrDownDept(o.id, "down");
			}
		},
		"remove" : {
			"separator_before" : true,
			"separator_after" : false,
			"label" : "删除",
			"action" : function(data) {
				delDept(o.id)
			}
		}
	};
	console.log($(this))
	if(this.get_parent(o) == "0"){
		delete items.editCompany;
	}
	else if (this.get_parent(o) == "#") {// root节点
		delete items.edit;
		delete items.up;
		delete items.down;
		delete items.remove;
	} else {
		delete items.editCompany;
		if (this.is_parent(o)) {// 如果还有child不显示上移/下移
			delete items.up;
			delete items.down;
		} else {
			var childrens = this.get_node(this.get_parent(o)).children;
			if (childrens[0] == o.id) {// 如果是第一个元素不显示上移
				delete items.up;
			}
			if (childrens[childrens.length - 1] == o.id) {// 如果是最后一个元素不显示下移
				delete items.down;
			}
		}
	}
	return items;
};

$(function() {
	$("#departmentId").val(0);
	/* emp.loadEmps(); */

	$('#jstree_div').jstree({
		"core" : {
			"data" : eval($("#orgtree").val()),
			"themes" : {
				"variant" : "large",// 加大
				"ellipsis" : true
			// 文字多时省略
			},
			"check_callback" : true
		},
		"plugins" : [ "wholerow", "themes", "overmenu" ],
		"contextmenu" : {
			"items" : ctxmenus
		}
	}).on('select_node.jstree', function(event, data) {
		$("#departmentId").val(data.node.id);
		$("#currDeptName").text(data.node.text);
		emp.loadEmps();
	});

});

var emp = {
	loadEmps : function() {
		$("#frm").submit();
	}
};

function addEmpoyee(){
	var diag = new top.Dialog();
	var departmentId = $("#departmentId").val();
	if(departmentId==0){
		top.Dialog.alert("请选择部门");
		return false;
	}
	diag.ID="model";
	diag.Title = "新增员工";
	diag.URL = "${ctx}/admin/departmentAction!preAddEmployee.action?departmentId="+departmentId;
	diag.Width=500;
	diag.Height=660;
	diag.show();
	return false;
}

function importEmpoyee(){
	var diag = new top.Dialog();
	/* var departmentId = $("#departmentId").val();
	if(departmentId==0){
		top.Dialog.alert("请选择部门");
		return false;
	} */
	diag.ID="model";
	diag.Title = "批量导入员工";
	diag.URL = "${ctx}/admin/departmentAction!preImportEmpoyee.action?departmentId="+departmentId;
	diag.Width=500;
	diag.Height=300;
	diag.show();
	return false;
}

function doSubmit(){
	$("#frm").submit();	
}

function deleteEmpoyee(){
	var id =[];
	$(window.frames["querytable"].document).find("input[type=checkbox]:checked").each(function(i){
		var required = $(this).val();
		if(required){
			id.push(required);	
		}
	});
	$("#employeeId").val(id);
	console.log(id)
	if(id.length>1){
		top.Dialog.confirm("确认批量删除员工？",function(){
			var employeeId = $("#employeeId").val();
			$.ajax({
			     type: "POST",
			     url: "${ctx}/admin/departmentAction!batchDeleteEmployee.action",
			     cache: false,
			     dataType:"json",
			     data:{"employeeId":employeeId},
			     success: function(data){
		    		if(data.successflag=="1"){
		    			top.Dialog.alert("批量删除成功",function(){
		    				$("#frm").submit();	
		    	    	});
			     	}
		    		else{
		    			top.Dialog.alert(data.msg);
		    		}
			     }
			});
		});
	}else{
		top.Dialog.alert("请选择两个或两个以上的员工");
		return false;
	}
}
</script>

</head>
<body>
<div class="position">
	<div class="center">
		<div class="left">
			<div class="right"><span>当前位置：基础数据管理 >>企业基本信息配置</span></div>
		</div>
	</div>
</div>
<div class="box2" panelTitle="企业基本信息" roller="false">
 <form action="${ctx}/admin/departmentAction!queryEmployee.action" method="post" target="querytable" id="frm">
 	<input type="hidden" id="companyId" value="${company.id }" />
 	<input type="hidden" id="employeeId" value="" name="employeeId"/>
 	<input type="hidden" id="departmentId" value="0" name="departmentId"/>
 	<input type="hidden" id="orgtree" value='${orgtree }'/>
 	<input type="hidden" name="currPage" id="currPage" value="${currpage}" /> 
 	<input type="hidden" name="perPageRows" id="perPageRows" value="${perPageRows}" />
 	<input type="hidden" name="Parentid" id="Parentid" value="${Parentid}" />
 	<table>
			<tr>
				<td>员工号：</td>
				<td><input type="text" name="employeeSerialNo" class="validate[length[0,15]]"/></td>
				<td>姓名：</td>
				<td><input type="text" name="employeeName" class="validate[length[0,15]]"/></td>
				<td>手机号码：</td>
				<td><input type="text" name="employeePhoneNum" class="validate[length[0,11]]"/></td>
				<td>身份证号：</td>
				<td><input type="text" name="employeeIdcard" class="validate[length[0,18]]"/></td>
				<td>
					<button onclick='return doSubmit()' ><span class="icon_find">查询</span></button>
					<button onclick='return addEmpoyee()' type="button"><span class="icon_add">新增员工</span></button>
					<button onclick='return importEmpoyee()' type="button"><span class="icon_add">批量导入</span></button>
					<button onclick='return deleteEmpoyee()' type="button"><span class="icon_delete">批量删除</span></button>
				</td>
			</tr>
		</table>
 </form>
</div>
<div id="scrollContent">
	<table style="width: 100%;">
		<tr>
			<td style="width: 20%" valign="top">
				<div style="width: 100%; overflow: auto; height: 100%;">
					<div id="jstree_div"></div>
				</div>
			</td>
			<td style="width: 80%;" valign="top">
				<div style="overflow: auto;height: 100%;">
				<iframe scrolling="no" width="100%" frameBorder=0 id="querytable"
						name="querytable"
						src="${ctx}/admin/departmentAction!queryEmployee.action"
						onload="iframeHeight('querytable')" allowTransparency="true">
				</iframe>
				</div>
			</td>
		</tr>
	</table>
</div>
<pt:page action="${ctx}/admin/departmentAction!queryEmployee.action" target="querytable"></pt:page>
</body>
</html>





