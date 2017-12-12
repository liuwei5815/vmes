<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<script type="text/javascript">
function doSubmit(){
	try{
		validate();
	}catch(err){
		//top.Dialog.alert(110);
		return false;
	}
	
	var param=[];
	if(!$.trim($("#param").val()) == "")
		param.push($("#param").val())
	$(".one2one").each(function(){
		var fk_tabId=$(this).find("#tabId").val();
		var colParam="";
		$(this).find("tr").each(function(){
			$(this).find("td:eq(1)").children(".hid").each(function(){
				var nameStr="";
				nameStr=$(this).attr("name");
				if(nameStr.indexOf("attr_")==0){
					nameStr=nameStr.substring(5);
					colParam+="#"+nameStr;
				}
			});
		});
		colParam=colParam.substring(1);
		param.push(fk_tabId+"#"+colParam);
	});
	$("#param").val(param);
	$("#frm").submit();
	/* $("fieldset").each(function(){
		var fk_tabId=$(this).find("#tabId").val();
		var colParam="";
		$(this).find("#fk_table tr").each(function(){
			$(this).find("td:eq(1)").children().each(function(){
				var nameStr="";
				if($(this).attr("id")=="upfile"){
					nameStr=$(this).find("input[type='hidden']").eq(4).attr("name");
					top.Dialog.alert(nameStr+"_div");
				}else{
					nameStr=$(this).attr("name");
					top.Dialog.alert(nameStr+"_input");
				}
				
				if(nameStr.indexOf("attr_")==0){
					nameStr=nameStr.substring(5);
					colParam+="#"+nameStr;
				}
			});
		});
		colParam=colParam.substring(1);
		param.push(fk_tabId+"#"+colParam);
	}); */
	
	
}
function add_validate(tb,win){
	$(tb).find(".validate1").find(":input[dataType]").each(function(i,d){
		var data= $(d);
		var notnull = data.attr("not-null");
		var lable =data.attr("lable");
		var dataType = data.attr("dataType");
		var value = "";
	
		/**
		将大文本的值赋给其对应的隐藏域
		**/
		if(dataType=='4'){
			console.log(4)
			data.val(win.UE.getEditor(data.attr("editorId")).getContent());
			console.log(data.val())
		}
		if(data.is(":text,:hidden,:password")){
			value=data.val();
		}
		else if(data.is("select")){
			value=data.find("option:selected").val();
		}
		else if(data.is(":checkbox") || data.is(":radio")){
			/**
			如果是多选按钮和单选按钮 只用验证一次,放在最后一次验证
			**/
			var name  = data.attr("name");
			var dom  =  $("[name='"+name+"']");
			var len = dom.size();
			var index = data.index();
			var cv = dom.filter(":checked").val();
			if(index+1<len){
				return true;
			};
			var cv = dom.filter(":checked").val();
			if(cv!=null){
				value=cv;
			}
		
		}
		/* if(notnull==1 && value==""){
			top.Dialog.alert("请填写"+lable);
			throw e;
		} */
		/*
		验证类型  整形和浮点型需要验证 上传图片类型也需要验证
		*/

		if(value!=""){
			//z
			if(dataType=='1'){
				var reg =/^\d+$/;
				if(!reg.test(value)){
					top.Dialog.alert(lable+"必须为正整数");
					throw e;
				}
			}
			else if(dataType=='5'){
				var reg = /^(\d+|\d+\.\d{1,2})$/;
				if(!reg.test(value)){
					top.Dialog.alert(lable+"格式不正确(只能为数字或者小数点保留两位)");
					throw e;
				}
			}
		}
	});
}
function validate(){
	
	$(".validate").find(":input[dataType]").each(function(i,d){
		var data= $(d);
		var notnull = data.attr("not-null");
		var lable =data.attr("lable");
		var dataType = data.attr("dataType");
		var value = "";
	
		/**
		将大文本的值赋给其对应的隐藏域
		**/
		if(dataType=='4'){
			data.val(UE.getEditor(data.attr("editorId")).getContent());
		
		}
		if(data.is(":text,:hidden,:password")){
			value=data.val();
			
		}
		else if(data.is("select")){
			value=data.find("option:selected").val();
			
		}
		else if(data.is(":checkbox") || data.is(":radio")){
			/**
			如果是多选按钮和单选按钮 只用验证一次,放在最后一次验证
			**/
			var name  = data.attr("name");
			var dom  =  $("[name='"+name+"']");
			var len = dom.size();
			var index = data.index();
			var cv = dom.filter(":checked").val();
			if(index+1<len){
				return true;
			};
			var cv = dom.filter(":checked").val();
			if(cv!=null){
				value=cv;
			}
		
		}
		if(notnull==1 && value==""){
			top.Dialog.alert("请填写"+lable);
			throw e;
		}
		/*
		验证类型  整形和浮点型需要验证 上传图片类型也需要验证
		*/

		if(value!=""){
			//z
			if(dataType=='1'){
				var reg =/^\d+$/;
				if(!reg.test(value)){
					top.Dialog.alert(lable+"必须为正整数");
					throw e;
				}
			}
			else if(dataType=='5'){
				var reg = /^(\d+|\d+\.\d{1,2})$/;
				if(!reg.test(value)){
					top.Dialog.alert(lable+"格式不正确(只能为数字或者小数点保留两位)");
					throw e;
				}
			}
		}
	});
}

function showJcrop(id){
	var imgPath = $("#"+id+"").val();
	if(imgPath==""){
		top.Dialog.alert("请先上传图片");
		return false;
	};
	var height = $("#"+id+"_height").val();
	if(height==""){
		top.Dialog.alert("请填写裁剪高度");
		return false;
	}
	if(isNaN(height)){
		top.Dialog.alert("裁剪高度必须是数字");
		return false;
	}

	var width = $("#"+id+"_width").val();
	if(width==""){
		top.Dialog.alert("请填写裁剪宽度");
		return false;
	}
	
	if(isNaN(width)){
		top.Dialog.alert("裁剪宽度必须是数字");
		return false;
	}
	var swidth=$("#s_width_"+id).val();
	var sheight = $("#s_height_"+id).val();
	var diag = new top.Dialog();
	diag.Title = "裁剪图片";

	diag.URL = "${ctx}/admin/jcrop!init.action?imagePath="+$("#"+id).val()+"&height="+height+"&width="+width+"&swidth="+swidth+"&sheight="+sheight;
	diag.Width=1000;
	diag.Height=1000;
	diag.show();
	diag.OnLoad=function(){
		
		
		
		/*
		var  jcropCallBack  =" var win =top.document.getElementById(\"_DialogFrame_model\").contentWindow.document;";
		var  jcropCallBack = jcropCallBack+"win.getElementById(\""+id+"\").value=\"$picPath$\";";
		var jcropCallBack  = jcropCallBack+"win.getElementById(\""+id+"_view\").innerHTML= \"<image  class='image' src="+fwUrl+"/$picPath$>'\";";
		var jcropCallBack = jcropCallBack+"top.Dialog.close();";
		diag.innerFrame.contentWindow.setSuccessCallBack(jcropCallBack);*/
		/*裁剪成功的回调*/
		diag.innerFrame.contentWindow.setSuccessCallBack(function(param){
				var fwUrl = '<s:property  value="@com.xy.cms.common.CommonFunction@getStrValue(\'picUrl\')"/>';
				$("#"+id).val(param);
				if(param!=''){
					var index = param.lastIndexOf("/");
					var begin = param.substring(0, index+1);
					var end = "s_"+param.substring(index+1, param.length);
					param = begin+end;
				}
				$("#"+id+"_view").html("<image  class='image' src="+fwUrl+param+"/>");
				top.Dialog.close();
				//top.Dialog.alert("ok");
			});
		
	};
	return false;
}
$(function(){
	  if($('#successflag').val() == "1"){//执行成功
			$(window.parent.frmright.document).find("#frm").submit();
			top.Dialog.close();
		}else{
			if($("#message").val()!=""){
				top.Dialog.alert($("#message").val());
		    	$("#message").val("");	   
		    }
		}
});

</script>
