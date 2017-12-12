/**
 * 公用JavaScript(兼容IE5/NN6)--平台中心组控制，项目组不得修改，否则后果自负
 * 如果发现有问题或需求，请通知提供者  
 * 如果方法没有用private开头,则方法是公开的,且保证向后兼容.
 * 最后更新:2004-08-11
 */
/** 全局变量bCancel; */ 
var bCancel = false;
var DATE_DELIMITER = "-";
var DB_INT_LENGTH = 64; //数据库位数,即整数长度，默认为64位
var MAX_INTEGER  = Math.pow(2,DB_INT_LENGTH-1) - 1;
var MIN_INTEGER  = -Math.pow(2,DB_INT_LENGTH-1);
var MAX_SMALLINT = Math.pow(2,DB_INT_LENGTH/4-1) - 1;
var MIN_SMALLINT = -Math.pow(2,DB_INT_LENGTH/4-1);
var VERBOSE = false;//显示所有明细，开发环境中可以调用setVerbose(true)
var FIELD_SEPARATOR = "_FIELD_SEPARATOR_";   //字段之间的分割符
var GROUP_SEPARATOR = "_GROUP_SEPARATOR_";     //一组代码之间的分割符

/**
 * 设置日期分割符，默认为'/'
 * @param delimiter 日期分割符
 */
function setDateDelimiter(delimiter){
    DATE_DELIMITER = delimiter;
}
/**
 * 设置数据库整数长度，默认为64
 * @param len 整数长度
 */
function setDBIntLength(len){
    DB_INT_LENGTH = len;
    MAX_INTEGER  = Math.pow(2,DB_INT_LENGTH-1) - 1;
    MIN_INTEGER  = -Math.pow(2,DB_INT_LENGTH-1);
    MAX_SMALLINT = Math.pow(2,DB_INT_LENGTH/4-1) - 1;
    MIN_SMALLINT = -Math.pow(2,DB_INT_LENGTH/4-1);
}
 
/**
 * 设置是否显示明细，默认为不显示
 * @param verbose 日期分割符
 */
function setVerbose(verbose){
    VERBOSE = verbose;
}

/**
 * 检查是否显示明细
 * @return 是否显示明细
 */
function isVerbose(){
    return VERBOSE;

}

/**
 * 打印日志信息
 */
function log(value){
    if(isVerbose()){
        window.status=value;
    }
}

/**
 * 判断客户端浏览器是否为Netscape
 * @return 客户端浏览器为Netscape则返回true,否则返回false;
 */
function isNetscape(){
    if(navigator.appName=="Netscape"){
        return true;
    }else{
        return false;
    }
}
 
 
//var verbose = false;//显示所有明细，生产环境中应改为false

/**
 * 判断客户端浏览器是否为Netscape
 * @return 客户端浏览器为Netscape则返回true,否则返回false;
 */
function isNetscape()
{
  if(navigator.appName=="Netscape")
    return true;
  else
    return false;
}
/**
 * 展开“+”号内容或隐藏“—”号内容；
 * 
 */
function showPage(img,spanID)
{
 
  if(spanID.style.display=="")
  {
   //关闭
    spanID.style.display="none";
    img.src="/claim/images/butCollapseBlue.gif";
  }
  else
  {
   //展开
    spanID.style.display="";
    img.src="/claim/images/butExpandBlue.gif";
  }
}

/**
 * 得到传入element在Document中的name相同的elements中的顺序(从1开始)
 * @param field element
 * @return 传入element在Document中的name相同的elements中的顺序(从1开始)
 */
function getElementOrder(field)
{
    var i = 0;
    var order = 0;
    var elements = document.getElementsByName(field.name);
    for(i=0;i<elements.length;i++)
    {
        order++;
        if(elements[i]==field)
        {
            break;
        }
    }

    return order;
}
/**
 * 传入element是否是Document中的name相同的elements中的第0个 
 * @param field element
 * @return 是返回true，否则返回false
 */
function isFirstElement(field){
    var elements = document.getElementsByName(field.name);
    if(elements[0]==field){
    	return true;
    }
    else{
    	return false;
    }
}

/**
 * 查找在Document中的element的name属性等如传入值的element个数，没有则返回0
 * @param fieldName 元素名称
 * @return 在Document中的element的name属性等如传入值的element个数
 */
function getElementCount(fieldName)
{
    var count = 0;
    count = document.getElementsByName(fieldName).length;
    return count;
}

/**
 * 得到字符串的字节长度
 * @param value 字符串
 * @return 字符串的字节长度
 */
function getByteLength(value)
{
  var str;
  var count  = 0;

  for(var i=0;i<value.length;i++)
  {
    str = escape(value.charAt(i));
    if(str.length==6)
      count = count + 2;
    else
      count = count + 1;
  }

  return count;
}

/**
 * 得到Table的所有元素
 * @param tableId 表名称
 * @return table的所有元素
 */
function getTableElements(tableId)
{
  var i = 0; 
  var elements=new Array();
  var tempElements = null;
  var tbody;
  var index=0;
  var tbodies = document.getElementById(tableId).tBodies;
  for(i=0;i<tbodies.length;i++)
  {
    tbody=tbodies.item(i);
    tempElements=tbody.getElementsByTagName("INPUT");    //加入INPUT域

    for(i=0;i<tempElements.length;i++)
    {
      elements[index++]=tempElements[i];
    }

    tempElements=tbody.getElementsByTagName("SELECT");   //加入SELECT域
    for(i=0;i<tempElements.length;i++)
    {
      elements[index++]=tempElements[i];
    }

    tempElements=tbody.getElementsByTagName("TEXTAREA"); //加入TEXTAREA域
    for(i=0;i<tempElements.length;i++)
    {
      elements[index++]=tempElements[i];
    }

  }
  return elements;
}

/**
 * 去掉字符串头空格
 * @param value 传人字符串
 * @return 去掉头空格后的字符串
 */
function leftTrim(value)
{
  var re =/^\s*/;
  if(value==null)
    return null;

  return value.replace(re,"");
}

/**
 * 去掉字符串尾空格
 * @param value 传人字符串
 * @return 去掉尾空格后的字符串
 */
function rightTrim(value)
{
  var re =/\s*$/;
  if(value==null)
    return null;

  return value.replace(re,"");
}

/**
 * 去掉字符串头尾空格
 * @param value 传人字符串
 * @return 去掉头尾空格后的字符串
 */
function trim(value)
{
  return leftTrim(rightTrim(value));
}


/**
 * 正则表达式测试
 * @param source 传人字符串
 * @param re 正则表达式
 * @return 正则表达式测试结果
 */
function regExpTest(resource,re)
{
  var result = false;

  if(resource==null || resource=="")
    return false;

  if(resource==re.exec(resource))
    result = true;

  return result;
}

/**
 * 替换字符串函数
 * @param str 原串
 * @param strFind 查找串
 * @param strReplaceWith 替换串
 * @return 返回替换后的字符串
 */
function replace(str,strFind,strReplaceWith){
    var strReturn;
    var re = new RegExp(strFind,"g");
    if(str==null){
        return null;
    }
    strReturn = str.replace(re,strReplaceWith);
    return strReturn;
}

/**
 * 检查输入域是否为空
 * @param field 输入域
 * @return 如果输入域的值为null或空，则返回true，否则返回false
 */
function isEmptyField(field){
    if(field.value==null || trim(field.value)==""){
        return true;
    }
    return false;
}

//对数字四舍五入
//数值,精度
function round(number,precision)
{
  if(isNaN(number))
    number = 0;
  var prec = Math.pow(10,precision);
  var result = Math.round( number * prec) ;
  result = result/prec;
  return result;
}


//对数字进行格式化,保证precision位
function point(number,precision)
{
  if(isNaN(number))
    number = 0;
  var result = number.toString();
  if(result.indexOf(".")==-1)
    result = result + ".";

  result = result + newString("0",precision);
  result = result.substring(0,precision + result.indexOf(".") + 1);
  return result;
}

//对数字第三位四舍五入
function mathRound(number)
{
  return round(number,2);
}

//对数字按0.00格式化
function pointTwo( s )
{
  return point(s,2);
}

//对数字按0.0000 格式化
function pointFour( s )
{
  return point(s,4);
}

//对数字格式化，delimiterChar默认为"," precision默认为3
function numberFormat(ivalue,delimiterChar,precision)
{
  if((ivalue==null) || (ivalue==""))
    return "";

  if(delimiterChar==null || delimiterChar=="")
    delimiterChar = ",";

  if(precision==null || precision =="")
    precision = 3;

  var i = 0;
  var ovalue = "";
  var times;

  var avalue = "";
  if(ivalue.indexOf(".")>-1)
  {
    avalue = "." + ivalue.substring(ivalue.indexOf(".")+1);
    ivalue = ivalue.substring(0,ivalue.indexOf("."));
  }

  times = ivalue.length % precision;
  if(times!=0)
  {
    ovalue = ivalue.substring(0,times);
    ivalue = ivalue.substring(times);
  }

  for(i=0;i<ivalue.length;i++)
  {
    if(i%precision==0)
    {
      ovalue += delimiterChar;
    }
    ovalue += ivalue.substring(i,i+1)
  }

  if(ovalue.substring(0,1) == delimiterChar)
    ovalue = ovalue.substring(1);


  return ovalue + avalue;
}


/**
 * 格式化数字
 * @param value 值
 * @param count 分割位数 默认为3
 * @param precision 小数点保留位数 默认为2
 * @param delimiterChar 分割符 默认为','
 */
function formatFloat(value,count,precision,delimiterChar)
{
  count = count==null?3:count;
  precision = precision==null?2:precision;
  delimiterChar = delimiterChar==null?",":delimiterChar;


  var strReturn = ""; //返回值
  var strValue = point(round(value,precision),precision); //格式化成指定小数位数

  strReturn = strValue.substring(strValue.length-precision-1);
  strValue = strValue.substring(0,strValue.length-precision-1);
  while(strValue.length>count)
  {
    strReturn = delimiterChar + strValue.substring(strValue.length-count) + strReturn;
    strValue = strValue.substring(0,strValue.length-count);
  }

  strReturn = strValue + strReturn;
  return strReturn;
}


//分割代码并放在select域里
//串的格式: 值FIELD_SEPARATOR文本GROUP_SEPARATOR值FIELD_SEPARATOR文本...
function setOption(selectName,strValue)
{
  //查不到代码返回
  if(strValue==null || trim(strValue)=="")
  {
    return;
  }

  var arrayField=strValue.split(GROUP_SEPARATOR);
  var i=0;
  var j=0;
  var intCount = getElementCount(selectName);

  if(intCount>1)
  {
    for(j=0;j<intCount;j++)
    {
      fm.all(selectName)[j].options.length = 0;
    }
  }
  else
  {
    fm.all(selectName).options.length = 0;
  }

  while(i<arrayField.length)
  {
    if(intCount>1)
    {
      for(j=0;j<intCount;j++)
      {
        var option=document.createElement("option");
        var arrayTemp=arrayField[i].split(FIELD_SEPARATOR);
        var strFieldName=arrayTemp[0];
        var strFieldValue=unescape(arrayTemp[1]);
        option.value=strFieldName;
        option.text=strFieldValue;

        fm.all(selectName)[j].add(option);
      }
    }
    else
    {
        var option=document.createElement("option");
        var arrayTemp=arrayField[i].split(FIELD_SEPARATOR);
        var strFieldName=arrayTemp[0];
        var strFieldValue=unescape(arrayTemp[1]);
        option.value=strFieldName;
        option.text=strFieldValue;
      fm.all(selectName).add(option);
    }
    i++;
  }
}


/**
 * 将给定字符串复制ｎ遍
 * @param intLength 字符串长度
 * @return 字符串
 */
function newString(iString, iTimes)
{
  var str = "";
  for (var i = 0 ; i < iTimes; i++)
     str = str + iString;
  return str;
} 


/**
 * 功能：将输入域变成只读，同时将CSS的属性变成只读
 * return true/false
 */
function disableAllInput()
{	
  var testStr = "" ;
  var tempElements = null;
  // document.all Firefox下不支持
  for(i=0; i<document.getElementsByTagName("*").length; i++) 
  {
    //alert(document.getElementsByTagName("*")(i).tagName);
    if(document.getElementsByTagName("*")[i].tagName=="INPUT")
    {
        tempElements = document.getElementsByTagName("*")[i];
        //将输入域变为只读
        if(tempElements.type=="text")
        {
	    	/**tempElements.style.fontSize="9pt";
	    	tempElements.style.borderTop = "none";
	    	tempElements.style.borderBottom = "none";
	    	tempElements.style.borderRight= "none";
	    	tempElements.style.borderLeft = "none" ;**/
	    	//tempElements.style.width="80%";
	    	//tempElements.style.color = "#000000";
	    	tempElements.style.backgroundColor = "#E8EBF0";  
	    	// add by dinggang 2008/2/21 reasion:禁用输入域的
	    	tempElements.setAttribute("ondblclick","");  	
	    	tempElements.setAttribute("onkeyup","");  
	    	// end add ; 
	    	      	
        	tempElements.disabled=true;
        }
        //将输入域变为只读
        if(tempElements.type=="radio")
        {
        	tempElements.disabled=true;
        }
        if(tempElements.type=="checkbox")
        {
        	tempElements.disabled=true;
        }
        
        ////将输入域变为只读
        //if(tempElements.type=="button")
        //{
        //	tempElements.disabled=true;
        //}
        ////将输入域变为只读
        //if(tempElements.type=="submit")
        //{
        //	tempElements.disabled=true;
        //}
        
    }
    //将选择域变为只读
    if(document.getElementsByTagName("*")[i].tagName=="SELECT")
    {
    	tempElements = document.getElementsByTagName("*")[i];
    	tempElements.disabled = true;
    }
    //将选择域变为只读
    if(document.getElementsByTagName("*")[i].tagName=="TEXTAREA")
    {
    	tempElements = document.getElementsByTagName("*")[i];
    	tempElements.disabled = true;
    }
    
  }

}
 

/**
 * 功能：将输入域变成可写，同时将CSS的属性变成可写
 * return true/false
 */
function ableAllInput() 
{
  var testStr = "" ;
  var tempElements = null;
  
  for(i=0; i<document.getElementsByTagName("*").length; i++) 
  {
    //alert(document.getElementsByTagName("*")[i].tagName);
    if(document.getElementsByTagName("*")[i].tagName=="INPUT")
    {
        tempElements = document.getElementsByTagName("*")[i];
        //将输入域变为可写
        if(tempElements.type=="text")
        { 
  	    	//tempElements.style.fontSize="9pt";
  	    	//tempElements.style.borderTop = "none";
  	    	//tempElements.style.borderBottom = "none";
  	    	//tempElements.style.borderRight= "none";
  	    	//tempElements.style.borderLeft = "none" ;
  	    	//tempElements.style.width="80%";
  	    	//tempElements.style.color = "#000000";
        	var editable = tempElements.getAttribute('editable');
//        	alert(editable == "false");
        	if(editable == null || editable=="true"){//可编辑的
//        		alert(editable);
//        		alert(tempElements.name);
        		tempElements.disabled=false;
        		tempElements.style.backgroundColor = "#FFFFFF";
        	}
        }
        //将输入域变为可写 
        if(tempElements.type=="radio")
        {
        	tempElements.disabled=false;
        }                
        //将输入域变为可写
        if(tempElements.type=="checkbox")  
        { 
        	tempElements.disabled=false;
        }  
    }
    //将选择域变为只读
    if(document.getElementsByTagName("*")[i].tagName=="SELECT")
    {
    	tempElements = document.getElementsByTagName("*")[i];
    	tempElements.disabled = false; 
    }
    //将选择域变为只读
    if(document.getElementsByTagName("*")[i].tagName=="TEXTAREA")
    {
    	tempElements = document.getElementsByTagName("*")[i];
    	tempElements.disabled = false;
    }
    
  }

}

/**
 * 功能：将输入域变成只读，同时将CSS的属性变成只读
 * return true/false
 */
function readonlyAllInput()
{	
  var testStr = "" ;
  var tempElements = null;
  // document.all Firefox下不支持
  for(i=0; i<document.getElementsByTagName("*").length; i++) 
  {
    //alert(document.getElementsByTagName("*")(i).tagName);
    if(document.getElementsByTagName("*")[i].tagName=="INPUT")
    {
        tempElements = document.getElementsByTagName("*")[i];
        //将输入域变为只读
        if(tempElements.type=="text")
        {
	    	/**tempElements.style.fontSize="9pt";
	    	tempElements.style.borderTop = "none";
	    	tempElements.style.borderBottom = "none";
	    	tempElements.style.borderRight= "none";
	    	tempElements.style.borderLeft = "none" ;**/
        	tempElements.style.backgroundColor = "#E8EBF0";  
	    	//tempElements.style.width="80%";
	    	//tempElements.style.color = "#000000";
	    	// add by dinggang 2008/2/21 reasion:禁用输入域的
	    	tempElements.setAttribute("ondblclick","");  	
	    	tempElements.setAttribute("onkeyup","");  
	    	// end add ; 
        	tempElements.readOnly=true;
        }
        //将输入域变为只读
        if(tempElements.type=="radio")
        {
        	tempElements.readOnly=true;
        }
        if(tempElements.type=="checkbox")
        {
        	tempElements.readOnly=true;
        }
        
        ////将输入域变为只读
        //if(tempElements.type=="button")
        //{
        //	tempElements.disabled=true;
        //}
        ////将输入域变为只读
        //if(tempElements.type=="submit")
        //{
        //	tempElements.disabled=true;
        //}
        
    }
    //将选择域变为只读
    if(document.getElementsByTagName("*")[i].gName=="SELECT")
    {
    	tempElements.style.backgroundColor = "#E8EBF0";  
    	tempElements = document.getElementsByTagName("*")[i];
    	tempElements.readOnly = true;
    }
    //将选择域变为只读
    if(document.getElementsByTagName("*")[i].tagName=="TEXTAREA")
    {
    	tempElements = document.getElementsByTagName("*")[i];
    	tempElements.readOnly = true;
    }
    
  }

}

/**
 * 功能： 按钮域的按钮域变成可读
 * @param tableID 含有按钮的表ID
 */
 
 function disabledAllButton(tableId)
 {
   var elements = getTableElements(tableId);

   for(var i=0;i<elements.length;i++)
   {


       //将button设成不可用
       if(elements[i].type == "button")
       {
          elements[i].disabled = true;
       }
       //将submit设成不可用
       if(elements[i].type == "submit")
       {
          elements[i].disabled = true;
       }
       //将reset设成不可用
       if(elements[i].type == "reset")
       {
          elements[i].disabled = true;
       }

    }
 }
 
  /**
 * 控制一排checkbox,常用于查询结果中选择所有选择框,通常加在onpropertychange方法上
 * 例:<input type=checkbox name=selectButton onpropertychange="boundCheckBox(this,fm.checkboxSelect)" >
 * @since 2004-11-22
 */
function boundCheckBox(controlField, checkBoxField){
    var count=0;
    try{
        count = checkBoxField.length;
    }catch(E){
    }
    if(isNaN(count)){
        checkBoxField.checked=controlField.checked;
    }else{
        for(var i=0;i<count;i++){
            checkBoxField[i].checked=controlField.checked;
        }
    }
}

//返回两个日期之间的工作日的小时数目
//date1:开始时间 (yyyy-mm-dd) String
//date2:结束时间 (yyyy-mm-dd) String
//date1hour:2位 String
//date2hour:2位 String
//return 小时
function getWorkDayHours(date1,date1hour,date2,date2hour)
{
    //开始日期
    var strValue1=date1.split("-");
    var date1Temp=new Date(strValue1[0],parseInt(strValue1[1],10)-1,parseInt(strValue1[2],10));
    //结束日期
    var strValue2=date2.split("-");
    var date2Temp=new Date(strValue2[0],parseInt(strValue2[1],10)-1,parseInt(strValue2[2],10));
    //开始小时
    var d1hour=parseInt(date1hour,10);
    //结束小时
    var d2hour=parseInt(date2hour,10);
    
   // alert('开始时间'+date1Temp+d1hour);
   // alert('结束时间'+date2Temp+d2hour);
    
    
    var computeDate=date1Temp; //开始比较的日期
    //计算结果
    var resulthour=0;
    
    var i=1; //计算日期的
    
    //开始时间和结束时间为同一天或者开始时间比结束时间大
    if (date1Temp.valueOf()>=date2Temp.valueOf()){
    	resulthour=d2hour - d1hour;
    	return resulthour;
    }
    
    var daydiffs=(date2Temp.valueOf() -date1Temp.valueOf())/(24*60*60*1000);
    var intYear=0;
    var intMonth=0;
    var intDays=0;
    var intWeek=0;
    
    for (i=0;i<=daydiffs;i++){
    	computeDate = new Date(date1Temp.valueOf()+i*24*60*60*1000);
    	
    	intYear=computeDate.getFullYear() ; //年
      intMonth=computeDate.getMonth() ;   //月
      intDays=computeDate.getDate() ;     //一个月的天
      intWeek=computeDate.getDay();       //星期
       //(1)节假 0表示星期日，6表示星期六 4:表示5月 9:表示10月
       if(intWeek==0||intWeek==6||((intMonth==4||intMonth==9)&&intDays>=1&&intDays<=7)||(intMonth==0&&intDays==1))
       {
       	 continue; 
    	 }
    	 
    	 //(2)第一天
    	 if (computeDate.valueOf()==date1Temp.valueOf()){
    	 		resulthour=24-d1hour;
    	 		continue;	  
    	  }
    	 //(3)最后一天
    	 if (computeDate.valueOf()==date2Temp.valueOf()){
    	 		resulthour=resulthour+d2hour;
    	 		continue;
    	  }
    	 	//(4)中间的天
    	 	resulthour=resulthour+24;
    	}
       
      //返回小时结果：
      return resulthour;  	
    }

//返回两个日期之间的小时数目
//date1:开始时间 (yyyy-mm-dd) String
//date2:结束时间 (yyyy-mm-dd) String
//date1hour:2位 String
//date2hour:2位 String
//return 小时
function getDayHours(date1,date1hour,date2,date2hour)
{
    //开始日期
    var strValue1=date1.split("-");
    var date1Temp=new Date(strValue1[0],parseInt(strValue1[1],10)-1,parseInt(strValue1[2],10));
    //结束日期
    var strValue2=date2.split("-");
    var date2Temp=new Date(strValue2[0],parseInt(strValue2[1],10)-1,parseInt(strValue2[2],10));
    //开始小时
    var d1hour=parseInt(date1hour,10);
    //结束小时
    var d2hour=parseInt(date2hour,10);
    
   // alert('开始时间'+date1Temp+d1hour);
   // alert('结束时间'+date2Temp+d2hour);
    
    
    var resulthour=0;
    
    var i=1; //计算日期的
    
    //开始时间和结束时间为同一天或者开始时间比结束时间大
   
    //开始时间和结束时间为同一天或者开始时间比结束时间大
    if (date1Temp.valueOf()>date2Temp.valueOf()){
    	return resulthour;
    }
    
    if (date1Temp.valueOf()==date2Temp.valueOf()){
    	resulthour=d2hour - d1hour;
    	return resulthour;
    }
      
    resulthour=(date2Temp.valueOf() -date1Temp.valueOf())/(60*60*1000)+ d2hour - d1hour;
       
    //返回小时结果：
     return resulthour;  	
  }

//检查日期输入域,和checkFullDate的区别是允许输入两个日期,之间以":" 分割,
//例如 20030523:20040312,
//例如 2003/03/04:2004/09/12
function checkBetweenDate(field)
{
  field.value = trim(field.value);
  var strValue = field.value;
  var desc   = field.description;
  //如果description属性不存在，则用name属性
  if(desc==null)
    desc = field.name;
  if(strValue=="")
  {
    return false;
  }
  
  //不采用直接返回
  var index = strValue.indexOf(":");
  if (index < 0)
  {
    if(isNumeric(strValue ))
    {
      if(strValue.length>6)
      {
        strValue = strValue.substring(0,4) + DATE_DELIMITER + strValue.substring(4,6) + DATE_DELIMITER + strValue.substring(6);
        field.value = strValue;
      }

      if(!isDate(strValue,DATE_DELIMITER) && !isDate(strValue))
      {
       errorMessage("请输入合法的" + desc +"\n类型为日期，格式为YYYY-MM-DD 或者YYYYMMDD");
       field.value="";
       field.focus();
       field.select();
       return false;
      }
    }
    if( !isDate(strValue,DATE_DELIMITER) && !isDate(strValue)||strValue.substring(0,1)=="0")
    {
      errorMessage("请输入合法的" + desc +"\n类型为日期，格式为YYYY-MM-DD 或者YYYYMMDD");
      field.value="";
      field.focus();
      field.select();
      return false;
    }
    return true;
  }
    
  var beginDate = strValue.substring(0,index);
  var endDate  = strValue.substring(index + 1);

  if(isNumeric(beginDate ))
  {
    beginDate = beginDate.substring(0,4) + DATE_DELIMITER + beginDate.substring(4,6) + DATE_DELIMITER + beginDate.substring(6);
  }
  if(isNumeric(endDate ))
  {
    endDate = endDate.substring(0,4) + DATE_DELIMITER + endDate.substring(4,6) + DATE_DELIMITER + endDate.substring(6);
  }  

  if(!isDate(beginDate,DATE_DELIMITER))
  {
    errorMessage("输入的日期为非法日期,请重新输入");
    field.focus();
    field.select();
    return false;
  }
  if(!isDate(endDate,DATE_DELIMITER))
  {
    errorMessage("输入的日期为非法日期,请重新输入");
    field.focus();
    field.select();
    return false;
  }
   field.value = beginDate + ":" + endDate;  
   return true;
}

//对输入域按键时的Datetime校验
function pressDatetime(e)
{
  var value = String.fromCharCode(e.keyCode);
  if((value>=0 && value<=9) || value=="/" || value=="-" || value==":" || value==" ")
    return true;
  else
    return false;
}

//按键代码就按楼主的代码指定，这里是切换的函数
//可以修改为非jQuery这里就懒得动刀了，刚刚随便写的
//功能：回车自动切换输入框焦点，如果跟在后面的是button则自动点击
function enterEventHandler(e) {
	var event = $.event.fix(e); //修正event事件
	var element = event.target; //jQuery统一修正为target
	var buttons = "button,reset,submit"; //button格式
	if (element.nodeName == "INPUT" || element.nodeName == "SELECT") {
	   event.stopPropagation(); //取消冒泡
	   event.preventDefault(); //取消浏览器默认行为
	   var inputs = $("input[type!='hidden'][type!='checkbox'][type!='radio'],select"); //获取缓存的页面input集合
	   var index = inputs.index(element); //当前input位置     
	   if (buttons.indexOf(inputs[index + 1].type) >= 0) {
	       inputs[index + 1].focus();
	       inputs[index + 1].click();
	   }
	   else {
	       inputs[index + 1].focus();
	   }
	
	}
}
