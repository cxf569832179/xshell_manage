/***
 * 判断是否是以1开头的11为数字
 * @param mobiles
 * @return
 */
function checkMobileNum(mobileNum){
    //var   re =new RegExp("/^1[3|4|5|8][0-9]\d{4,8}$/");
    var result= mobileNum.match(/^1[0-9]{10}$/); 
    return result;
}
//验证url
function isURL(str_url) {
	var strRegex = "^((https|http|ftp|rtsp|mms)?://)"
	+ "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" // ftp的user@
	+ "(([0-9]{1,3}\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
	+ "|" // 允许IP和DOMAIN（域名）
	+ "([0-9a-z_!~*'()-]+\.)*" // 域名- www.
	+ "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\." // 二级域名
	+ "[a-z]{2,6})" // first level domain- .com or .museum
	+ "(:[0-9]{1,4})?" // 端口- :80
	+ "((/?)|" // a slash isn't required if there is no file name
	+ "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
	var re = new RegExp(strRegex);
	return re.test(str_url);
}

		/* 
		 * 定义List
		 * version: 1.0 
		 */  
		function List() {  
		    this.list = new Array();  
		};  
		  
		/** 
		 * 将指定的元素添加到此列表的尾部。 
		 * @param object 指定的元素 
		 */  
		List.prototype.add = function(object) {  
		    this.list[this.list.length] = object;  
		};  
		  
		/** 
		 * 将List添加到此列表的尾部。 
		 * @param listObject 一个列表 
		 */  
		List.prototype.addAll = function(listObject) {  
		    this.list = this.list.concat(listObject.list);  
		};  
		  
		/** 
		 *  返回此列表中指定位置上的元素。 
		 * @param index 指定位置 
		 * @return 此位置的元素 
		 */  
		List.prototype.get = function(index) {  
		    return this.list[index];  
		};  
		  
		/** 
		 * 移除此列表中指定位置上的元素。 
		 * @param index 指定位置 
		 * @return 此位置的元素 
		 */  
		List.prototype.removeIndex = function(index) {  
		    var object = this.list[index];  
		    this.list.splice(index, 1);   
		    return object;  
		};  

		/**
		*  获取元素在数组中的坐标,不存在则返回-1
		* @return true or false
		*/
		List.prototype.getObjectIndex = function(object) {    
		 var i = 0;    
		 for(; i < this.list.length; i++) {
		     if( this.list[i] === object) {
		        return i;     
		     }            
		 } 
		 return -1;
		};

		  
		/** 
		 * 移除此列表中指定元素。 
		 * @param object 指定元素 
		 * @return 此位置的元素 
		 */  
		List.prototype.remove = function(object) {
		    var i = this.getObjectIndex(object);
		    if(i==-1) {
		        return null;
		    } else {
		        return this.removeIndex(i);
		    }
		};  
		  
		/** 
		 * 移除此列表中的所有元素。 
		 */  
		List.prototype.clear = function() {  
		    this.list.splice(0, this.list.length);  
		};  
		  
		/** 
		 * 返回此列表中的元素数。 
		 * @return 元素数量 
		 */  
		List.prototype.size = function() {  
		    return this.list.length;  
		};  
		  
		/** 
		 * 返回列表中指定的 start（包括）和 end（不包括）之间列表。 
		 * @param start 开始位置 
		 * @param end   结束位置 
		 * @return  新的列表 
		 */  
		List.prototype.subList = function(start, end) {   
		    var list = new List();  
		    list.list = this.list.slice(start, end);  
		    return list;  
		};  
		  
		/** 
		 *  如果列表不包含元素，则返回 true。 
		 * @return true or false 
		 */  
			List.prototype.isEmpty = function() {  
			    return this.list.length == 0;  
			}; 
			
		/** 
		 *  转为string
		 * @return str 
		 */  
		List.prototype.toString = function() { 
			var str="{";
			for(var i=0;i<this.list.length;i++){
				str+=this.list[i]+",";
			}
			str=str.substring(0, str.length-1)+"}";
			return  str;  
		}; 
		
			
			/**
			 * 设置Cookie
			 * @param name
			 * @param value
			 */
				function SetCookie(name,value)
				{
//				  document.cookie = name + "="+ escape (value) + ";expires=0" ;
					var exdate=new Date();
					exdate.setDate(exdate.getDate()+1);
					document.cookie=name+ "=" +escape(value)+
					((1==null) ? "" : ";expires="+exdate.toGMTString())+";path=/";
					
				}
			/**
			 * 读取Cookie
			 * @param name
			 * @returns
			 */
				function getCookie(name)   
				{
				  var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
				  if(arr != null) return unescape(arr[2]); return null;
				}
				/**
				 * 删除cookie
				 * @param name
				 */
				function delCookie(name){//为了删除指定名称的cookie，可以将其过期时间设定为一个过去的时间
					   var date = new Date();
					   date.setTime(date.getTime() - 10000);
					   document.cookie = name + "=a; expires=" + date.toGMTString();
					}
				//复制map
				function getMap(map){
					//返回的map
					var returnMap=new Map();
					 var rmobileMapKeys=map.getkey();
					 for(var i=0;i<rmobileMapKeys.length;i++){
						 var str=rmobileMapKeys[i];
						 var value=map.get(str);
						 returnMap.put(str,value);
					 }
					 return returnMap;
				}
			//复制List
				function getList(list){
					var returnList=new List();
				 	for(var i=0;i<list.size();i++){
				 		var rmobile=list.get(i);
				 		returnList.add(rmobile);
				 	}
				 	return returnList;
				}
				
				
				//js模拟map
				function Map() {
					 var struct = function(key, value) {
					  this.key = key;
					  this.value = value;
					 };
					 
					 var put = function(key, value){
					  for (var i = 0; i < this.arr.length; i++) {
					   if ( this.arr[i].key === key ) {
					    this.arr[i].value = value;
					    return;
					   }
					  }
					   this.arr[this.arr.length] = new struct(key, value);
					 };
					 
					 var get = function(key) {
					  for (var i = 0; i < this.arr.length; i++) {
					   if ( this.arr[i].key === key ) {
					     return this.arr[i].value;
					   }
					  }
					  return null;
					 };
					 
					 var remove = function(key) {
					  var v;
					  for (var i = 0; i < this.arr.length; i++) {
					   v = this.arr.pop();
					   if ( v.key === key ) {
					    continue;
					   }
					   this.arr.unshift(v);
					  }
					 };
					 
					 var size = function() {
						 return this.arr.length;
					 };
					 
					 var isEmpty = function() {
						 return this.arr.length <= 0;
					 };
					 
					 var getkey = function() {
						 var keyArr = new Array();
						 for (var i = 0; i < this.arr.length; i++) {
							 keyArr[i] = this.arr[i].key;
						 }
						 return keyArr;
					 };
					
					 //以json格式返回字符串
					var toJSONString = function() { 
							 var json = (this.arr.length > 0) ? '{"MyArray":[' : "";
							 for (var i = 0; i < this.arr.length; i++) { 
								 json += '{"key":"' + this.arr[i].key + '","value":"' + this.arr[i].value + '"},'; 
							 } 
							 if (this.arr.length > 0) { 
								 json = json.substring(0, json.length - 1);
								 json += ']}'; 
							 } 
						 	return json; 
						 };
						 //以json格式返回字符串
							var toJSONString2 = function() { 
									 var json = (this.arr.length > 0) ? '{':"";
									 for (var i = 0; i < this.arr.length; i++) { 
										 if(i==0){
											 json +="'"+this.arr[i].key +"':'"+this.arr[i].value+"'";
										 }else{
											 json +=",'"+this.arr[i].key +"':'"+this.arr[i].value+"'"; 
										 }
									 } 
									 if (this.arr.length > 0) { 
										 json = json.substring(0, json.length);
										 json += '}'; 
									 } 
								 	return json; 
								 };
					 this.arr = new Array();
					 this.get = get;
					 this.put = put;
					 this.remove = remove;
					 this.size = size;
					 this.isEmpty = isEmpty;
					 this.getkey = getkey;
					 this.toJSONString = toJSONString;
					 this.toJSONString2 = toJSONString2;
				}
				
				
				// 将一个js中模拟的Map对象转换成的jsonString  返回一个数组
				function jsonStringToAray(jsonString){
					var array;
					//将
					var obj2=eval("("+jsonString+")");
					for(var c in obj2){  //遍历
						array=obj2[c];
					}
					
					return array;
				}
				
				
				
				
				String.prototype.endWith=function(s){ 
					if(s==null||s==""||this.length==0||s.length>this.length) 
					return false; 
					if(this.substring(this.length-s.length)==s) 
					return true; 
					else 
					return false; 
					return true; 
					} 

					String.prototype.startWith=function(s){ 
					if(s==null||s==""||this.length==0||s.length>this.length) 
					return false; 
					if(this.substr(0,s.length)==s) 
					return true; 
					else 
					return false; 
					return true; 
					} 
					
					
					
					//功能：统计包含汉字的字符个数 
					 
					//说明：汉字占2个字符，非汉字占1个字符 
					 
					function checksum(chars) 
					 
					{ 
						var sum = 0;  
						for (var i=0; i<chars.length; i++) 
						{  
							var c = chars.charCodeAt(i);  
							if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) 
							{  
								sum++;  
							} else{    
								sum+=2;  
							}  
						} 
						return sum; 
					} 

					/**
					 * input输入框输入对象限制
					 */

					function CheckInputIntFloat(oInput) 
					{ 
//					if('' != oInput.value.replace(/\d{1,}\.{0,1}\d{0,}/,'')) 
//					{ 
//					oInput.value = oInput.value.match(/\d{1,}\.{0,1}\d{0,}/) == null ? '' :oInput.value.match(/\d{1,}\.{0,1}\d{0,}/); 
//					} 
//					
						if('' != oInput.value.replace(/\d{1,}\.{0,1}\d{0,}/,''))
					    {
					        oInput.value = oInput.value.match(/\d{1,}\.{0,1}\d{0,}/) == null ? '' :oInput.value.match(/\d{1,}\.{0,1}\d{0,}/);
					    }
//						if('' != oInput.value.replace(/\d/g,''))
//					    {
//					    	alert(2);
//						     oInput.value = oInput.value.replace(/\D/g,'');
//					    }
					}
					
					
					/**
					密码校验：6-18位数字或字母下划线
					*/
					function checkpwd(pwd){
						if(pwd==null||pwd==undefined){
							return false;
						}
						var myReg=/^[a-zA-Z]+\w{5,17}$/;
						if(!myReg.test(pwd)){
							return false;
						}
						return true;
					}
					
					
$(function(){
	$(".but_pagehide").click(function(){
		$(".pagehide_div").hide();
		$(".pageshow_div").show();
		
		$(".mk_addrg").css("margin-left","40px");
	});
	$(".but_pageshow").click(function(){
		$(".pagehide_div").show();
		$(".pageshow_div").hide();
		//$(".mk_addrg").css("margin-left","360px");
		$(".mk_addrg").attr("style","");
		
	});
});					


/**
 * 获取根目录
 * @returns
 */
function getRootPath() {
    /*获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp*/
    var curWwwPath = window.document.location.href;
    /*获取主机地址之后的目录，如： uimcardprj/share/meun.jsp*/
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    /*获取主机地址，如： http://localhost:8083 */
    var localhostPaht = curWwwPath.substring(0, pos);
    /*获取带"/"的项目名，如：/uimcardprj */
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    return (localhostPaht + projectName);
/*  return (localhostPaht); */
}
