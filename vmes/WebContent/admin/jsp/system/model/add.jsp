<!DOCTYPE html PUBLIC "
-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/tags/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/tld/ModelTag.tld" prefix="m" %>
<%@ include file="/admin/jsp/common/common.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>建表</title>

<script type="text/javascript" charset="utf-8"
	src="${ctxAdmin}/js/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8"
	src="${ctxAdmin}/js/ueditor.all.min.js"> </script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=eYf9sA6yVTFHlh9ytU4a0EYY"></script>
<script>
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

function openAdd(id){
	var diag = new top.Dialog();
	diag.ID="test";
	diag.Title = "添加";
	diag.URL = "model!preAdd1.action?tableId="+id;
	diag.Height=window.innerHeight;
	diag.Width=window.innerWidth;
	diag.OKEvent = function(){
		var table = diag.innerFrame.contentWindow.tbmodel;
		var $t = $(table);
		try{
			add_validate($t,diag.innerFrame.contentWindow);
		}catch(e){
			return;
		}
		var item = new Object();
		var postItem = new Object();
		var tds = $t.find(".hid");
		for(var i=0; i < tds.length;i++){
			postItem[$.trim($(tds[i]).attr('lable'))] = $.trim($(tds[i]).val());
			if($.trim($(tds[i]).attr('dataType'))==10){
				path = $.trim($(tds[i]).val());
				path = path.substring(0,path.lastIndexOf("/")+1) + "s_"+path.substring(path.lastIndexOf("/")+1);
				item[$.trim($(tds[i]).attr('lable'))] = '<img class="image" src="picServlet.do?fileName='+path+'">'
			}else{
				item[$.trim($(tds[i]).attr('lable'))] = $.trim($(tds[i]).val());
			}
		}
		
		var param=[];
		var names = new Object();
		var seq = checkboxTable.getElementsByTagName("tr").length;
		if(!$.trim($("#param").val()) == "")
			param.push($("#param").val())
		$(table).each(function(){
			var fk_tabId=$(this).find("#tabId").val();
			var colParam="";
			$(this).find("tr").each(function(i){
				$(this).find("td:eq(1)").children(".hid").each(function(){
					var nameStr="";
					nameStr=$(this).attr("name")+"_"+(seq-1);
					names[$(this).attr("lable")] = nameStr;
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
		console.log($("#param").val())
		var column = new Object();
		var head = checkboxTable.getElementsByTagName("tr")[0];
		
		item["序号"] = seq;
		item["操作"] = "<span class='img_delete hand' onclick='rm(this);'></span>";
		
		var cols = head.getElementsByTagName("th");
		for(var i=0;i<cols.length;i++){
			column[i] = cols[i].innerHTML;
		}
		var html = "<tr id= '"+ (seq -1)+"'>";
		
		for(var i=0;i<cols.length;i++){
			if(column[i] == '操作' || column[i] == '序号'){
				html += "<td>" + item[column[i]] + "</td>";
			}else{
				if(typeof(item[column[i]]) != 'undefined'){
					html += "<td>" + item[column[i]] + "</td>";
				}else{
					html += "<td></td>";
				}
			}
		}
		console.log(postItem.length)
		for(var i in postItem){
			html += "<input type='hidden' datatype='' name='" + names[i] +"' value='" +postItem[i]+"' />";
		}
		html+="</tr>";
		console.log(html)
		
		$("#checkboxTable").append(html);
		
		diag.close();
	}
	diag.show();
}

function rm(obj){
	var tr=obj.parentNode.parentNode;  
    var tbody=tr.parentNode;  
    tbody.removeChild(tr);
    var id = tr.id;
    
    $(tbody).find("tr").each(function(){
    	tid = $(this).attr("id");
    	if(tid > id){
    		$(this).attr("id",tid-1)  
    		$(this).find("td:eq(0)").each(function(){
    			$(this).text(tid);
    		})
    		
    		$(this).find("input").each(function(i,d){
    			var data = $(d);
    			nameStr=$(this).attr("name");
    			var start = nameStr.lastIndexOf("_");
    			index=nameStr.substring(start+1);
    			nameStr = nameStr.substring(0,start+1)+(index-1);
    			$(this).attr("name",nameStr);
    		});
    	}
    })
    
    var s = $("#param").val();
    var collection = s.split(",");
    for(var i = 0;i<collection.length;i++){
		var start = collection[i].lastIndexOf("_");
		var index = collection[i].substring(start+1);
		
		var first = collection[i].indexOf("_");
		var split = collection[i].lastIndexOf("#");
		
		if(index == id){
			collection.splice(i,1);
			i-=1;
		}
		
		if(index > id){
			collection[i] = collection[i].substring(0,start+1) + (index-1);
			collection[i] = collection[i].substring(0,first+1) + (index-1) + collection[i].substring(split);
		}
    }
    $("#param").val(collection)
    console.log($("#param").val())
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

<div style="">
	<input type="hidden" name="message" id="message" value="${message}" /> 
	<input type="hidden" name="successflag" id="successflag" value="${successflag}" />
	<s:form action="admin/model!add.action" method="post" theme="simple" id="frm">
	<s:hidden name="tableId"></s:hidden> 
	<input type="hidden" id="fk" name="fk" value="${fk }" />
	<input type="hidden" id="fkid" name="fkid" value="${fkid }" />
	<input type="hidden" id="param" name="param"/>
	<table width="100%" class="tableStyle" transmode="true">
		<s:iterator value="columnsList" var="cell">
			<s:if test="#cell.nameCn!='创建时间' && #cell.nameCn!='修改时间'">
			<tr class="validate">
				<td>${nameCn }：</td>
				<td>
					<m:model name="columnsMap.${name }" id="${name }" notNull="${notnull }" lable="${nameCn }" dataType="${dataType }" defaults="${defaults }" value="" optValue="${cell.optvalueList }" ship="${shipMap[id] }"
				      
					></m:model>
				</td>
			</tr>
			</s:if>
		</s:iterator>
		<tr>
			<td colspan="2">&nbsp;</td>
		 </tr>
	</table>
		<!-- 处理在主表中添加从表的情况 -->
		<s:if test="#request.listTable!=null">
		      <s:iterator value="#request.listTable" var="cell">
		             <fieldset>
		                 <input type="hidden" id="tabId" value="${cell.table.id }" class="fk_tabId"/>
		                 <legend>${cell.table.name }</legend>
		                 
		                 <s:if test="#cell.type==2">
		                 <div style="margin-bottom: 10px;float: right;"><input type="button" onclick="openAdd('${cell.table.id}')" value="添加"/></div>
				         <table class="tableStyle" id="checkboxTable">
				                <tr>
									<th width="30" align="center">序号</th>
									<s:iterator value="#cell.list" var="col">
									  <th align="center">${col.nameCn }</th>
									</s:iterator>
									<th width="100" align="center">操作</th>
							     </tr>
							     <%-- <s:iterator value="#cell.listData" status="sta" var="data">
							      <tr>
							          <td>${sta.index+1 }</td>
								      <s:iterator value="#cell.list" var="col" status="da">
								          <td>${data[da.index] }</td>
								      </s:iterator>
								      <td></td>
							      </tr>
							     </s:iterator> --%>
				         </table>
				         </s:if>
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
							
				         </s:else>
		             </fieldset>
		      </s:iterator>
		</s:if>
		
		
		<%-- <s:if test="#request.fkViews!=null"> 
			<s:iterator value="#request.fkViews" var="c">
			<fieldset> 
				<legend>
				    <s:property value="#c.pkTable.nameCn"/>
				</legend> 	
					<!-- 如果是多对多的情况 -->
					<table class="tableStyle" transMode="true" footer="normal">
						<s:iterator value="#c.pkTable.addColumns" var="ce">
							<tr>
								<td>${nameCn }</td>
								<td>
									<m:model name="columnsMap.${name}" id="${name }" notNull="${notnull }" lable="${nameCn }" dataType="${dataType }" defaults="${defaults }" value="" optValue="${cell.optvalueList }" ship="${shipMap[id] }"
									
									></m:model>
								</td>
							</tr>
						</s:iterator>
						<tr>
							<td colspan="2">&nbsp;</td>
						</tr>
					</table>
				</fieldset>
			</s:iterator>
		</s:if> --%>

		<div class="padding_top10">
			<table class="tableStyle" transMode="true">
				<tr>
					<td>&nbsp;</td>
					<td>
					</td>
				</tr>
				<tr>
					<td colspan="2">&nbsp;</td>
				</tr>
			</table>
		</div>
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
<%@include file="/admin/jsp/common/image_upload.jsp"%>
<%@include file="/admin/jsp/system/model/common/model.jsp" %>
</body>
</html>




