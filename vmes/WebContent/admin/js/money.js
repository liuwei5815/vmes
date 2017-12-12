/**
 * 金额字符串转换为中文大写金额
 * @param money
 * @returns 
 */
function toChineseUppercase(money) {
	if (!/^(0|[1-9]\d*)(\.\d+)?$/.test(money))
		return "非法数据";
	var unit = "仟佰拾亿仟佰拾万仟佰拾元角分", str = "";
	money += "00";
	var p = money.indexOf('.');
	if (p >= 0)
		money = money.substring(0, p) + money.substr(p + 1, 2);
	unit = unit.substr(unit.length - money.length);
	for ( var i = 0; i < money.length; i++)
		str += '零壹贰叁肆伍陆柒捌玖'.charAt(money.charAt(i)) + unit.charAt(i);
	return str.replace(/零(仟|佰|拾|角)/g, "零").replace(/(零)+/g, "零").replace(
			/零(万|亿|元)/g, "$1").replace(/(亿)万|壹(拾)/g, "$1$2").replace(
			/^元零?|零分/g, "").replace(/元$/g, "元整");
}

/**
 * 
 */ 
   function onkeydownC_new(my_id,m_select_arr) {
            if (event.keyCode == 32) {
             var txtSpace="";
             var txtSpace = jQuery.trim(jQuery("#"+my_id).val());
             var m_arr=m_select_arr.split(",")
             for (i=0;i<m_arr.length;i++) {
                   if(txtSpace==m_arr[i])
                      {
                         if (m_arr.length-1==i) { 
                                 document.getElementById(my_id).value=m_arr[0];
                            }
                         else
                             {
                                 document.getElementById(my_id).value=m_arr[i+1];
                             }   
                         document.getElementById(my_id).select();
                         return false;
                      }
                 }             
              return true;
              
            }
           
           
        }
        
    function  onFoucs_new(my_id,m_select_arr)
        {    
             var txtSpace="";
             var txtSpace = jQuery.trim(jQuery("#" +my_id).val());
             var m_arr=m_select_arr.split(",")
             if(txtSpace=="")
             {
                 document.getElementById(my_id).value=m_arr[0];
             }
                document.getElementById(my_id).select();
        
        }
        
        
    // 数据框+增减
       
       function addOne(my_id)
        {
            var showControl = document.getElementById(my_id);
            var showControlValue=jQuery.trim(showControl.value);
            if(showControlValue=="")
            {
                showControl.value="0";
                showControlValue="0";
            }
            showControl.value = parseInt(showControlValue) + 1;
        }
        function removeOne(my_id)
        {
            var showControl = document.getElementById(my_id);
            var showControlValue=jQuery.trim(showControl.value);
            if(showControlValue=="")
            {
               showControl.value="0";
                showControlValue="0";
            }
            if(parseInt(showControlValue)>=1)
            {
             showControl.value = parseInt(showControlValue) - 1;
            }
        }
        function onKeyPress()
        {
            if ( !((window.event.keyCode >= 48) && (window.event.keyCode <= 57)))
           {
                 window.event.keyCode = 0;
           }
           
           
        }
        
        

// 选择处理

    function none_select(s_obj,i_obj) {
         document.getElementById(s_obj).style.display="none"    
         document.getElementById(i_obj).style.display="block"    
         document.getElementById(i_obj).value=document.getElementById(s_obj)(document.getElementById(s_obj).selectedIndex).innerText 
    }
    function show_select(s_obj,i_obj) {
         document.getElementById(i_obj).style.display="none"    
         document.getElementById(s_obj).style.display="block"    
         for (i=0;i<document.getElementById(s_obj).length;i++) {
             if (document.getElementById(i_obj).value==document.getElementById(s_obj).all(i).innerText)  {
                 document.getElementById(s_obj).all(i).selected=true 
             }
         }
         document.getElementById(s_obj).focus()
    }



// 自动录入科目


//    var source1 = new Array();
// source1[0] = new Array();
// source1[0][0] = '1|资产类|1';
// source1[0][1] ='2|负债类|0';
// source1[0][2] ='3|净资产类|0';
// source1[0][3] ='4|收支类|0';
//    
// source1[1] = new Array();
// source1[1][0] = '11|库存现金|0';
// source1[1][1] = '12|银行存款|0';
// source1[1][2] = '13|其他存款|0';
// source1[1][3] = '14|有价证券|0';
// source1[1][4] = '15|库存物资|1';
// source1[1][5] = '21|拨入维持性经费|0';
// source1[1][6] = '22|拨入建设性经费|0';
//    
// source1[2] = new Array();
// source1[2][0] = '151|上级调拨|1';
// source1[2][1] = '152|本级自筹|0';
//    
// source1[3] = new Array();
// source1[3][0] = '1511|军需物资|0';
// source1[3][1] = '1512|卫生物资|0';
// source1[3][2] = '1513|营房物资|0';
    
autoComplete={
    isSon:false,
    pop_len:20,
    pop_cn:'autoDis',
    hover_cn:'cur',
    source:source1,
    mytarget:'',
    myself:null,
    my_select:false ,
    init:function(){
        this.setDom();
        return this;
    },
    bind:function(x){
        if(x.getAttribute('type') != 'text' || x.nodeName != 'INPUT')
            return null;
        var self = this;
        myself = this;
        x.onkeyup = function(e)
        {
            e = e || window.event;
            var lis = self.pop.getElementsByTagName('li'),lens = self.pop.getElementsByTagName('li').length,n=lens,temp;
            if(e.keyCode == 38)// 向上键
            {                                        // 键盘up键被按下，向上键
                if(self.pop.style.display != 'none'  & self.pop.style.display != '')
                {
                    for(var i=0;i<lens;i++)
                    {                            // 遍历结果数据，判断是否被选中
                        if(lis[i].className)
                            temp = i;
                        else
                            n--;
                    }
                    if(n==0)
                    {                                                // 如果没有被选中的li元素，则选中最后一个
                        lis[lens-1].className = self.hover_cn;
                        var arrValue = lis[lens-1].innerHTML.split('|');
                        this.value = arrValue[0];
                    }
                    else
                    {                                                    // 如果有被选中的元素，则选择上一个元素并赋值给input
                        if(lis[temp] == lis[0])
                        {                        // 如果选中的元素是第一个孩子节点则跳到最后一个选中
                            lis[lens-1].className = self.hover_cn;
                            var arrValue = lis[lens-1].innerHTML.split('|');
                            this.value = arrValue[0];
                            lis[temp].className = '';
                        }
                        else
                        {                                                
                            lis[temp-1].className = self.hover_cn;
                            var arrValue = lis[temp-1].innerHTML.split('|');
                            this.value = arrValue[0];
                            lis[temp].className = '';
                        }
                    }
                 }
                 else  
                 {  
                   self.insert(this);
                 }
            }
            else if(e.keyCode == 40)
            {                     // down键被按下，原理同up键
                if(self.pop.style.display != 'none' & self.pop.style.display != '' )
                {
                    for(var i=0;i<lens;i++)
                    {
                        if(lis[i].className)
                            temp = i;
                        else
                            n--;
                    }
                    if(n==0)
                    {
                        lis[0].className = self.hover_cn;
                        var arrValue = lis[0].innerHTML.split('|');;
                        this.value = arrValue[0];
                    }
                    else
                    {
                        if(lis[temp] == lis[lens-1])
                        {
                            lis[0].className = self.hover_cn;
                            var arrValue = lis[0].innerHTML.split('|');;
                            this.value = arrValue[0];
                            lis[temp].className = '';
                        }else
                        {
                            lis[temp+1].className = self.hover_cn;
                            var arrValue = lis[temp+1].innerHTML.split('|');;
                            this.value = arrValue[0];
                            lis[temp].className = '';
                        }
                    }
                }
                else
                {
                  self.insert(this);
                }
            }
            else if(e.keyCode==13)
            {
                var isBreak = false;
                var val = this.value;
                
                 var kmstr=HaveKm(val,source1);
                if(kmstr.length>0)
                {
                    var av = kmstr.split('|');
                    if(av[2]=='1')
                    {
                       self.insert(this);
                     
                    }
                    isBreak = true;
                }
                self.insert(this);
           }
           else                                    // 如果按下的键既不是up又不是down那么直接去匹配数据并插入
           {    
                 self.insert(this);
           }
        };
        x.onblur = function(){                // 这个延迟处理是因为如果失去焦点的时候是点击选中数据的时候会发现先无法触发点击事件
            var val = this.value;
            try {
             
                    if (mytarget!='') {
// var my_val =mytarget.innerText
// var av = my_val.split('|');
// if(av.length>0) {
// val=av[0];
// }
                    }  
                     

                    var kmstr=HaveKm(val,source1);
                    if(kmstr.length>0)
                    {
                        var av = kmstr.split('|');
                        if(av[2]=='1')
                        {
                        // this.value='';
                       // self.pop.style.display='none';
                            this.value=val;
                            if (my_select==true ) {
                                self.insert(this);
                             }
                            else
                              {
                                  self.pop.style.display='none';
                                  setTimeout(function(){self.pop.style.display='none';},300);
                              
                              }    
                            
                        }
                        else 
                        {
                          this.value=val;
                          self.pop.style.display='none';
                          setTimeout(function(){self.pop.style.display='none';},300);
                        }
                     }
                    else 
                    {
               // this.value='';
                       self.pop.style.display='none';
                       setTimeout(function(){self.pop.style.display='none';},300);
                    }
                    
                 }
               catch(e) {
                   setTimeout(function(){self.pop.style.display='none';},300);
               
               }
            
// setTimeout(function(){self.pop.style.display='none';},300);
        };
        x.onkeydown = function(e){
            e = e || window.event;
            var val = this.value;
            var kmstr=HaveKm(val,source1);
            if(kmstr.length>0)
            {
                var av = kmstr.split('|');
                if(av[2]=='0')
                {
                    if(e.keyCode!=8)
                    {
                   // e.returnValue = false;
                    }
                }
            }
         };
        return this;
    },
    setDom:function(){
        var self = this;
        var dom = document.createElement('div'),frame=document.createElement('iframe'),ul=document.createElement('ul');
      document.body.appendChild(dom);

        with(frame){                                    // 用来在ie6下遮住select元素
            setAttribute('frameborder','0');
            setAttribute('scrolling','auto');
            style.cssText='z-index:29;position:absolute;left:0;top:0;display:none'
        }
        with(dom){                                        // 对弹出层li元素绑定onmouseover，onmouseover
            className = this.pop_cn;
            appendChild(frame);
            appendChild(ul);
            onmouseover  = function(e){            // 在li元素还没有加载的时候就绑定这个方法，通过判断target是否是li元素进行处理
                e = e || window.event;
                var target = e.srcElement || e.target;
                if(target.tagName == 'LI'){            // 添加样式前先把所有的li样式去掉，这里用的是一种偷懒的方式，没有单独写removeClass方法
                    for(var i=0,lis=self.pop.getElementsByTagName('li');i<lis.length;i++)
                        lis[i].className = '';
                    target.className=self.hover_cn;        // 也没有写addClass方法，直接赋值了
                    mytarget=target;
                    my_select=true; 
                }
            };
            onmouseout = function(e){
                e = e || window.event;
                var target = e.srcElement || e.target;
                if(target.tagName == 'LI')
                    target.className='';
                    my_select=false ; 
            };
        }
        this.pop = dom;
    },
    
    insert:function(self){
        var bak = [],s,li=[],left=0,top=0,val=self.value;
        var isBreak = false;
        var val = self.value;
        var isHave = false;
        if(HaveKm(val,source1).length>0)
        {
           isHave = true;
        }
        if(!isHave)
        {
         // self.value = self.value.substr(0,self.value.length-1);
        }
        
         isBreak = false;
        for(var iii=0;val.length > 0 && val.length < source1.length && iii < source1[val.length].length; iii++)
        { 
            var vvv = source1[val.length][iii];
            var av = vvv.split('|');
            if(vvv == '')
            {
                break;
            }
            else if(vvv.indexOf(val) == 0){
                if(av[2] == '1')
                {
                    bak.push(av[0] + '|' + av[1]);
                }
                else
                {
                    bak.push(av[0] + '|' + av[1]);
                }
                isBreak = true;
            }
            else if(isBreak)
            {
                break;
            }
        }
        if(bak.length == 0){                                                    // 如果没有匹配的数据则隐藏弹出层
            this.pop.style.display='none';
            return false;
        }
       // 这个弹出层定位方法之前也是用循环offsetParent，但发现ie跟ff下差别很大（可能是使用方式不当），所以改用这个getBoundingClientRect
        left=self.getBoundingClientRect().left+document.documentElement.scrollLeft+50;
        top=self.getBoundingClientRect().top+document.documentElement.scrollTop+self.offsetHeight;
        with(this.pop){
// style.cssText =
// 'width:'+self.offsetWidth+'px;'+'position:absolute;left:'+left+'px;top:'+top+'px;display:none;';
// getElementsByTagName('iframe')[0].setAttribute('width',self.offsetWidth);

        style.cssText = 'width:'+300+'px;'+'position:absolute;left:'+left+'px;top:'+top+'px;display:none;background: none repeat scroll 0 0 #F0F6E4;';
        getElementsByTagName('iframe')[0].setAttribute('width',300);
        getElementsByTagName('iframe')[0].setAttribute('height',self.offsetHeight);
        onclick = function(e)
        {
            e = e || window.event;
            var target = e.srcElement || e.target;
             if(target.tagName == 'LI')
             {
                var arrValue = target.innerHTML.split('|');
                if(arrValue.length>-1)
                {
                     var val =arrValue[0];
                     var kmstr=HaveKm(val,source1);
                     if(kmstr.length>0)
                     {
                        var av = kmstr.split('|');
                        if(av[2]=='1')
                        {
                            self.value = arrValue[0];
                              myself.insert(self);
    
                        }
                        else 
                        { 
                            self.value = arrValue[0];
                            this.style.display='none';
                        }
                     }
                   
                }
             }
         };
        
      }
        s = bak.length>this.pop_len?this.pop_len:bak.length;
        for(var i=0;i<s;i++)
            li.push( '<li>' + bak[i] +'</li>');
        this.pop.getElementsByTagName('ul')[0].innerHTML = li.join('');
        this.pop.style.display='block';
     }
    
   
    
};


 this.HaveKm=function(val,source1)
    {
       var haveKmStr='';
       for(var iii=0;val.length > 0 && val.length <= source1.length && iii < source1[val.length-1].length; iii++)
        { 
            var vvv = source1[val.length-1][iii];
            var av = vvv.split('|');
            if(vvv == '')
            {
                haveKmStr='';
                break;
            }
            else if(av[0] == val){
               haveKmStr=vvv;
              break;
            }
        }
        return haveKmStr;
    }




// 数字框录入


 String.prototype.left = function(nLength){
return this.substr(0, nLength);
};

String.prototype.right = function(nLength){
return this.substr(this.length - nLength, nLength);
};

String.prototype.mid = function(index, nLength){
return this.substr(index, nLength);
};

String.prototype.count = function(char){
    var ar = this.split(char);
    return (ar.length - 1);
}

// 查找指定数字是否在该数组中
Array.prototype.isInarray = function(v)
{
    for( var i=0; i < this.length; i++ )
     {
            if( v == this[i] )
            {
              return true;
            }
    }
    return false;
}
 // 查找指定数字是否在该数组中
Array.prototype.isInarray = function(v)
{
    for( var i=0; i < this.length; i++ )
     {
            if( v == this[i] )
            {
              return true;
            }
    }
    return false;
}
this.add = function(nLength){
 var sInValue='';
 for(var i=0; i <nLength; i++)
 {  
    sInValue=sInValue+'0';
 }
return sInValue;
};
this.bind=function(x,fNumber)
{   
     var FNumber=fNumber;// 保留小数部分个数
     var e=document.getElementById(x);
    
    if(e.value=='')
    {
       e.value='.'+add(FNumber);
    }
      e.onkeydown = function() 
        {  
                var pos = _getCaret(x);// 光标位置
                 var sValue=e.value;
               // 允许添加"-"在必须是在首位置,负号
                if( [109, 189].isInarray(event.keyCode))
                {
                     var fh= sValue.indexOf('-');
                     var stempValue=sValue.replace(/[^(\d|\.)]/g, '');
                    if(fh<0)
                    {
                       e.value='-'+e.value; 
                        _setCaret(e,1);// 设置光标的位置
                    } 
                    else 
                    {
                      e.value = stempValue;
                      _setCaret(e,0);// 设置光标的位置
                    }
                  
                }
                // 数字键
                if( (event.keyCode>47 && event.keyCode <58) || (event.keyCode > 95 && event.keyCode < 106 ) )
                {
                     // 处理小数部分
                     var str = sValue;
                    var ar;
                    ar = str.split('.');
                    if( ar.length ==2)
                    {
                        var strTemp;
                        strTemp = ar[ar.length-1];
                        var len = strTemp.length;
                        if(len>=FNumber&&str.length<=pos)
                        {
                           event.returnValue = false;
                        }
                    }
                    
                    // 处理开始第一个字符不为0
                    // 处理符号
                    var signal = str.left(1);	// 取左边的一位字符串， 如果是负号则从字符串中删除
                    if( signal == "-")
                    {
                       if(pos==1&&(event.keyCode==48||event.keyCode==96))// 第一位不能输入0
                       {
                          event.returnValue = false;
                       }
                    }
                    else 
                    {
                       if(pos==0&&(event.keyCode==48||event.keyCode==96))// 第一位不能输入0
                       {
                          event.returnValue = false;
                       }
                    }
                   return;
                } 
                // 处理输入小数点"."
                if( [190, 110].isInarray(event.keyCode))
                {
                   // 如果小数点输入过多则不让输入
                    var s= sValue.split('.');
                    if(s.length<2)
                    {
                      return;   
                    }
                    else if(s.length==2)
                    {
                      var newLgth=s[0].length+1;
                      _setCaret(e,newLgth);// 设置光标的位置
                      
                    } 
                }
                // 处理DELETE和方向键
                if( [46, 37, 38, 39, 40].isInarray(event.keyCode) )
                {
                      return;
                }
                // 处理退格键
                if( event.keyCode == 8 )
                {
                    return;
                }
                event.returnValue = false;
       };
       e.onkeyup=function()
       {
             var sValue=e.value;
           // 数字键
                if( (event.keyCode>47 && event.keyCode <58) || (event.keyCode > 95 && event.keyCode < 106 ) )
                {
                     // 处理小数部分
                     var str = sValue;
                    var ar;
                    ar = str.split('.');
                    if( ar.length ==2)
                    {
                        var strTemp;
                        strTemp = ar[ar.length-1];
                        var len = strTemp.length;
                        if(len>FNumber)
                        {
                           var dylength=len-fNumber;
                           e.value=sValue.substr(0,sValue.length-dylength);
                        }
                    }
                    
                   
                } 
       
       }
       e.onblur=function() 
       {
            var intPart = "", floatPart = "";// 整数部分与小数部分
               var FoaltStr="";
            var str = e.value;
            
            // 处理符号
            var signal = str.left(1);	// 取左边的一位字符串， 如果是负号则从字符串中删除
            if( signal != "-")
            {
                signal = "";
            }
            else
            {
                str = str.right(str.length-1);
            }
           FoaltStr=str;
            // 处理小数部分
            str = str.replace(/[^(\d|\.|,)]/g, '');
            var ar;
            ar = str.split('.');
            if( ar.length <2)
            {
                floatPart =add(FNumber);
            }
            else
            {
                var strTemp;
                strTemp = ar[ar.length-1].replace(/[^(\d)]/g, '');
                var len = strTemp.length;
                if(len>=0&&len<=FNumber)
                {
                 floatPart =add(FNumber-len);// 表示补零个数
                }
                floatPart = strTemp+floatPart;
            }
            // 处理整数部分
            var tint, str;
            var icount = 0;
            tint = ar[0];
            var prepos = tint.count(',');	// 删除","号前的","个数
            tint = tint.replace(/,/g, ''); // 删除","
            // 过滤左边的连续的0
            if( tint == "" ) tint == "0";
                // 用","分割千分位
                while( tint.length > 3 )
                {
                        intPart = "," + tint.right(3) + intPart;
                        icount++;
                        tint = tint.left( tint.length - 3 );
                }
              if( tint.length > 0 ) intPart = tint + intPart;
                // 组合数字
            str = intPart + "." + floatPart;
            
            // 赋值
            if(e.value=='')
            {
               e.value='';
            }
           else 
            {
               if(parseFloat(FoaltStr)==0)
               {
                   e.value='';
               }
               else 
               {
                e.value = str;
               }
            }
             
            
            
            if(signal=='-')
            {
              e.style.color='red';
            }
       }
       e.onfocus=function() 
        {   
            if(e.value=='')
            {
               e.value='.'+add(FNumber);
            }
            var str = e.value;
            // 消除分隔符部分
            str = str.replace(/[^(\d|\.)]/g, '');
           
         if(e.style.color=='red'&&e.value!='')
         {
            e.value='-'+str;
            e.style.color='Black';
         }
         else 
         {
            e.value=str;
         }
       }
       
};


/*-------------------------------------------------------------
$ 函数名:	_getCaret
$ 功能:	获得当前光标的位置
$ 参数:	element -> 输入控件句柄
---------------------------------------------------------------*/
this._getCaret = function (element)
{
    var e=document.getElementById(element);
    // 当控件是文本区或者文本框时并且可写才能有光标位置，否则直接返回
    // alert(this.display(window));
    if( !event )
    {
        return;
    }
    if( event.srcElement != e )return;
    if( e.readOnly == true || e.disabled == true )
    {
        return;
    }
    e.focus();
    var rang = document.selection.createRange();
    rang.setEndPoint("StartToStart", e.createTextRange())
    return rang.text.length;
};

/*-------------------------------------------------------------
$ 函数名:	_setCaret
$ 功能:	设置当前光标的位置
$ 参数:	element -> 输入控件句柄
---------------------------------------------------------------*/
this._setCaret = function(element, pos)
{
    if( !event )
    return;
    if( event.srcElement != element )
    return;
    try{
            // 当控件是文本区或者文本框时并且可写才能有光标位置，否则直接返回
            if( element.readOnly == true || element.disabled == true ){
            return;
            }
            var r =element.createTextRange();
            r.moveStart('character', pos);
            r.collapse(true);
            r.select();
        }
        catch(e)
        {
            // show error message at here
        }
};





// 金额处理

this.bindYj=function(x,sKeyCode,sFh)
{   
 var e=document.getElementById(x);
  e.onkeydown = function() 
        {  
           
        };
       e.onkeyup = function() 
        {  
                // enter键
                if(event.keyCode==sKeyCode)
                {
                   if(e.value.indexOf(sFh)>-1)
                   {
                   var sZf=sFh;
                     e.value=e.value.replace(sZf,'');
                   }
                   else 
                   {
                    e.value=sFh+e.value;
                   }
                } 
             
               
       };
       e.onblur=function() 
       {
           
        };
       
};



String.prototype.left = function(nLength){
return this.substr(0, nLength);
};

String.prototype.right = function(nLength){
return this.substr(this.length - nLength, nLength);
};

String.prototype.mid = function(index, nLength){
return this.substr(index, nLength);
};

String.prototype.count = function(char){
    var ar = this.split(char);
    return (ar.length - 1);
};

// 查找指定数字是否在该数组中
Array.prototype.isInarray = function(v)
{
    for( var i=0; i < this.length; i++ )
     {
            if( v == this[i] )
            {
              return true;
            }
    }
    return false;
}
 // 查找指定数字是否在该数组中
Array.prototype.isInarray = function(v)
{
    for( var i=0; i < this.length; i++ )
     {
            if( v == this[i] )
            {
              return true;
            }
    }
    return false;
}

String.prototype.SJFormat =function(FNumber)
{
 var intPart = "", floatPart = "";// 整数部分与小数部分
            var str=new String 
            str = this;
            
            // 处理符号
            var signal = str.left(1);	// 取左边的一位字符串， 如果是负号则从字符串中删除
            if( signal != "-")
            {
                signal = "";
            }
            else
            {
                str = str.right(str.length-1);
            }
            
            // 处理小数部分
            str = str.replace(/[^(\d|\.|,)]/g, '');
            var ar;
            ar = str.split('.');
            if( ar.length <2)
            {
                floatPart =add(FNumber);
            }
            else
            {
                var strTemp;
                strTemp = ar[ar.length-1].replace(/[^(\d)]/g, '');
                var len = strTemp.length;
                if(len>=0&&len<=FNumber)
                {
                 floatPart =add(FNumber-len);// 表示补零个数
                }
                floatPart = strTemp+floatPart;
            }
            
            // 处理整数部分
            var tint, str;
            var icount = 0;
            tint = ar[0];
            var prepos = tint.count(',');	// 删除","号前的","个数
            tint = tint.replace(/,/g, ''); // 删除","
            // 过滤左边的连续的0
            if( tint == "" ) tint == "0";
                // 用","分割千分位
                while( tint.length > 3 )
                {
                        intPart = "," + tint.right(3) + intPart;
                        icount++;
                        tint = tint.left( tint.length - 3 );
                }
              if( tint.length > 0 ) intPart = tint + intPart;
                // 组合数字
            str =signal + intPart + "." + floatPart;
            sVlaue = str;
            
           return sVlaue;

}

function SJFormat_new(my_SJ) {
   var t1=new String(my_SJ);
   
   // t1=my_SJ;
    return t1.SJFormat(2);
    
}



// 求两个字符串的相似度,返回差别字符数,Levenshtein Distance算法实现
function Levenshtein_Distance(s,t){
    var n=s.length;// length of s
    var m=t.length;// length of t
    var d=[];// matrix
    var i;// iterates through s
    var j;// iterates through t
    var s_i;// ith character of s
    var t_j;// jth character of t
    var cost;// cost
    // Step 1
    if (n == 0) return m;
    if (m == 0) return n;
    // Step 2
    for (i = 0; i <= n; i++) {
        d[i]=[];
        d[i][0] = i;
        }
    for (j = 0; j <= m; j++) {
        d[0][j] = j;
        }
    // Step 3
    for (i = 1; i <= n; i++) {
             s_i = s.charAt (i - 1);
            // Step 4
            for (j = 1; j <= m; j++) {
                 t_j = t.charAt (j - 1);
                    // Step 5
                    if (s_i == t_j) {
                          cost = 0;
                    }
                    else  {
                          cost = 1;
                    }
                    // Step 6
                    d[i][j] = Minimum (d[i-1][j]+1, d[i][j-1]+1, d[i-1][j-1] + cost);
            }
    }
    // Step 7
    return d[n][m];
}
// 求两个字符串的相似度,返回相似度百分比
function Levenshtein_Distance_Percent(s,t){
     var l=s.length>t.length?s.length:t.length;
     var d=Levenshtein_Distance(s,t);
     return (1-d/l).toFixed(4);
}
// 求三个数字中的最小值
function Minimum(a,b,c){
    return a<b?(a<c?a:c):(b<c?b:c);
}
