<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>编辑账号</title>
<%@ include file="/admin/jsp/common/common.jsp"%>
<script type='text/javascript' src="${ctx }/admin/js/page.js"></script>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
.tableStyle tr th,td{
 height:24px;
 white-space: nowrap;
 word-wrap: break-word;
 overflow: hidden;
}
</style>
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
	<s:form id="frm" action="accout_setting!updatePassword.action" method="post" theme="simple"
							enctype="multipart/form-data">
		<input type="hidden" name="totalCount" id="totalCount" value="${totalCount }" />
		<input type="hidden" name="totalPage" id="totalPage" value="${totalPage }" />
		<input type="hidden" name="currPage" id="currPage" value="${currPage }" />
		<input type="hidden" name="message" id="message" value="${message }" />
		<input type="hidden" name="successflag" id="successflag" value="${successflag }" />
		<input type="hidden" name="type" id="type" value="${type }"/>
		<input type="hidden" name="id" id="id" value="${res[0] }"/>
		<table class="tableStyle" transmode="true">		
		  <tr>
            <td>账号：</td>
            <td><input type="text" value="${res[1] }" style="width: 118px; height: 100%" disabled="disabled"/></td>
          </tr>
		  <tr>
            <td>员工：</td>
            <td><input type="text" value="${res[2] }" style="width: 118px; height: 100%" disabled="disabled"/></td>
          </tr>
		  <tr>
            <td>新密码：</td>
            <td><input type="password" id="pwd" name="password" class="validate[length[3,15],custom[noSpecialCaracters]]"/></td>
          </tr>
          <tr>
            <td colspan="2"><p>
              <button onclick="doSubmit();" type="button"><span class="icon_save">保 存</span></button>
              <button onclick="cancel();" type="button"><span class="icon_no">关 闭</span></button>
          </p></td>
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
<script type="text/javascript">
	function doSubmit() {
		$('#frm').submit();
	}

	function cancel(){
		top.Dialog.close();
	}

	function unload(){
		if(document.frm.successflag.value == "1"){//执行成功
	    	top.Dialog.alert("保存成功",function(){
	    		cancel();
	    	});
		}else{
			if(document.forms[0].message.value !=""){
				top.Dialog.alert(document.forms[0].message.value);
		    	document.forms[0].message.value = "";	   
		    }
		}
	}

	$(document).ready(function(){ 
		unload();
	})

</script>
</body>
</html>