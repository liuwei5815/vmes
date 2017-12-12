<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/admin/jsp/common/common.jsp"%>
<%@ taglib uri="/tags/PageTag" prefix="pt"%>
<%@taglib prefix="xtree" uri="/WEB-INF/tld/tree.tld" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Insert title here</title>
<script language="javascript" src="${ctx}/admin/js/xtree.js" ></script>
<script language="javascript" src="${ctx}/admin/js/map.js" ></script>
<script language="javascript" src="${ctx}/admin/js/checkboxTreeItem.js"></script>
<link href="${ctx}/admin/css/xtree.css" rel="stylesheet" type="text/css"></link>
<script>

function savePower(){
	var roleId=$('#roleId').val();
	if(roleId==""){
		top.Dialog.alert("参数无效");
		return;
	}
	var aa = getCheckValues(false);
	$.ajax({url: '${ctx}/admin/power!ajaxSavePower.action',
				data:{"role.id":roleId,"midStr":""+aa+""},
				dataType:'json',
				type:"POST",
				"success":function(data){
					if(data=="1"){
						top.Dialog.alert("权限保存成功");
					}else{
						top.Dialog.alert("权限保存失败");
					}
				}
			});
}
function goBack(){
	top.Dialog.confirm("确定返回?",function(){
		window.location.href="javascript:history.go(-1)";
	});
	
}
</script>
</head>
<body>
	<div class="position">
		<div class="center">
		<div class="left">
		<div class="right">
			<span>当前位置：系统设置 >>权限分配管理 </span>	</div>	
		</div>	
		</div>
	</div>	
	<div class="box2" panelTitle="权限信息" roller="false">
     
  	<input type="hidden" id="roleId" name="role.id" value="${role.id}"/>
	  <table>
        <tr>
        	<td nowrap="nowrap">角色名称:${role.name} </td>
        	<td width="50">&nbsp;</td>
        	<td><button type="button" onclick="savePower()"><span class="button">保存</span></button>&nbsp;
            <button type="button" onclick="goBack()"><span class="button">返回</span></button></td>
        </tr>
      </table>
    </div>
      
<div id="scrollContent">
	<table width="100%" border="0">
		<tr>
			<td>
      			 <xtree:tree key="tree1" rootName="角色权限"  list="listTree" imagePath="/admin/image/tree/" type="checkbox"/>
   			 </td>
  		</tr>
	</table>
</div>
</body>
</html>





