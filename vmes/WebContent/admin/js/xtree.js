this.imagePath = 'http://localhost:8080/ysjs/image/tree';    
var webFXTreeConfig = {		
	rootIcon        : this.imagePath + '/foldericon.png',
	openRootIcon    : this.imagePath + '/openfoldericon.png',
	folderIcon      :  this.imagePath + '/foldericon.png',
	openFolderIcon  :this.imagePath + '/openfoldericon.png',
	fileIcon        : this.imagePath + '/file.png',
	iIcon           : this.imagePath + '/I.png',
	lIcon           : this.imagePath + '/L.png',
	lMinusIcon      : this.imagePath + '/Lminus.png',
	lPlusIcon       : this.imagePath + '/Lplus.png',
	tIcon           : this.imagePath + '/T.png',
	tMinusIcon   : this.imagePath + '/Tminus.png',
	tPlusIcon       : this.imagePath + '/Tplus.png',
	blankIcon       : this.imagePath + '/blank.png',
	defaultText     : 'Tree Item',
	defaultAction   : 'javascript:void(0);',
	defaultBehavior : 'classic',
	usePersistence	: true,
    disableColor : "#9B9B9B",   // luohc [add] 2004-12-1. 不可用时的文本颜色。
	defaultColor : "black",   // luohc [add] 2006-4-30. 节点的文本颜色。
	cascadeCheck: true,    // luohc. 定义checkbox树是否级联选择。 2005.02.15
	//imagePath : "js/images",  // luohc. 图片路径.	2005.02.15  // 这个属性有点问题，请不要使用. 参见 setImagePath 方法.	
	setImagePath: function(path){  // 设置图片路径。直接设置 imagePath 属性不起作用。
		if(path == null) path = "js/images/";
		if(path.charAt(path.length-1) != '/') path += "/";
        //this.imagePath = path;
		this.rootIcon = path + "foldericon.png";
		this.openRootIcon = path + 'openfoldericon.png';
		this.folderIcon     =  path + 'foldericon.png';
		this.openFolderIcon  = path + 'openfoldericon.png';
		this.fileIcon       = path + 'file.png';
		this.iIcon           = path + 'I.png';
		this.lIcon           = path + 'L.png';
		this.lMinusIcon   =  path + 'Lminus.png';
		this.lPlusIcon     = path + 'Lplus.png';
		this.tIcon          = path + 'T.png';
		this.tMinusIcon  = path + 'Tminus.png';
		this.tPlusIcon     = path + 'Tplus.png';
		this.blankIcon     = path + 'blank.png';
	}	
	
};

var webFXTreeHandler = {
	idCounter : 0,
	idPrefix  : "webfx-tree-object-",
	all       : {},
	behavior  : null,
	selected  : null,
	onSelect  : null, /* should be part of tree, not handler */
	getId     : function() { return this.idPrefix + this.idCounter++; },
	toggle    : function (oItem) { this.all[oItem.id.replace('-plus','')].toggle(); },
	select    : function (oItem) { this.all[oItem.id.replace('-icon','')].select(); },
	focus     : function (oItem) { this.all[oItem.id.replace('-anchor','')].focus(); },
	blur      : function (oItem) { this.all[oItem.id.replace('-anchor','')].blur(); },
	keydown   : function (oItem, e) { return this.all[oItem.id].keydown(e.keyCode); },
	cookies   : new WebFXCookie(),
	insertHTMLBeforeEnd	:	function (oElement, sHTML) {
		if (oElement.insertAdjacentHTML != null) {
			oElement.insertAdjacentHTML("BeforeEnd", sHTML)
			return;
		}
		var df;	// DocumentFragment
		var r = oElement.ownerDocument.createRange();
		r.selectNodeContents(oElement);
		r.collapse(false);
		df = r.createContextualFragment(sHTML);
		oElement.appendChild(df);
	}
};

/*
 * WebFXCookie class
 */

function WebFXCookie() {
	this.cookieKey = "extreecookie";
	if (document.cookie.length) { 	        
		this.cookies = ' ' +  document.cookie; 
	}	
}

WebFXCookie.prototype.deleteCookie = function(){
	document.cookie = this.cookieKey + "=none";
	document.cookie = this.cookieKey + "=nothing; expires=Thu, 01-Jan-1970 00:00:01 GMT";
}

WebFXCookie.prototype.setCookie = function (key, value) {
	value = key + "=" + value + ";";
	this.cookies = ' ' +  document.cookie; 
	var values = this.getCookieValue(" " + this.cookieKey, this.cookies);
	if(values == null){		
		document.cookie = this.cookieKey + "=" + escape(value);
	}else{
		values = unescape(values);
		var start = values.indexOf(key + "=");
		if(start>=0){
			var end = values.indexOf(";", start);
			values = values.substring(0, start) + values.substring(end+1) + value;
		}else{
			values = values + value;
		}	
		document.cookie = this.cookieKey + "=" + escape(values);
	}
}

WebFXCookie.prototype.getCookie = function(key){
	this.cookies = ' ' +  document.cookie; 
	if(this.cookies == null) return null;
	var values = this.getCookieValue(" " + this.cookieKey, this.cookies);
    
	var value = null;
	if(values != null){
	   value = this.getCookieValue(key, unescape(values));
	}
	return value;
}

// 在 "key1=value1;key2=value2;" 这样的键值对字符串中获取某键值对应的值。
WebFXCookie.prototype.getCookieValue = function (key, values) {
	if(values == null) return null;
	var newKey = key + "=";
	var start = values.indexOf(newKey);
	if(start == -1) return null;
	start += newKey.length;
	var end = values.indexOf(";", start);
	if(end == -1) end = values.length;
	var value = values.substring(start, end); 
	return value;
}

/*
 * WebFXTreeAbstractNode class
 */

function WebFXTreeAbstractNode(sText, sAction) {
	this.childNodes  = [];
	this.id     = webFXTreeHandler.getId();
	this.text   = sText || webFXTreeConfig.defaultText;
	
	//this.action = sAction || webFXTreeConfig.defaultAction;
    
	if(sAction != null && sAction.length > 0){
		// luohc [add] 2004-12-07. 在new一个节点时可以省略javascript:前缀。
		var leftBracket = sAction.indexOf("(");   // 左括号.
		var rightBracket = sAction.indexOf(")");  // 右括号.
		var js = sAction.toLowerCase().indexOf("javascript:");  // 前缀 javascript: 。
		// 如果有左右括号(说明是方法)，但又没有javascript:前缀。
		if(leftBracket>0 && rightBracket>leftBracket && js!=0){
			sAction = "javascript:" + sAction;
		}
		this.action = sAction;
	}else{
		this.action = webFXTreeConfig.defaultAction;
	}

	this._last  = false;
	webFXTreeHandler.all[this.id] = this;
}



WebFXTreeAbstractNode.prototype.add = function (node, bNoIdent) {
	node.parentNode = this;
	this.childNodes[this.childNodes.length] = node;
	var root = this;
	if (this.childNodes.length >= 2) {
		this.childNodes[this.childNodes.length - 2]._last = false;
	}
	while (root.parentNode) { root = root.parentNode; }
	if (root.rendered) {
		if (this.childNodes.length >= 2) {
			document.getElementById(this.childNodes[this.childNodes.length - 2].id + '-plus').src = ((this.childNodes[this.childNodes.length -2].folder)?((this.childNodes[this.childNodes.length -2].open)?webFXTreeConfig.tMinusIcon:webFXTreeConfig.tPlusIcon):webFXTreeConfig.tIcon);
			this.childNodes[this.childNodes.length - 2].plusIcon = webFXTreeConfig.tPlusIcon;
			this.childNodes[this.childNodes.length - 2].minusIcon = webFXTreeConfig.tMinusIcon;
			this.childNodes[this.childNodes.length - 2]._last = false;
		}
		this._last = true;
		var foo = this;
		while (foo.parentNode) {
			for (var i = 0; i < foo.parentNode.childNodes.length; i++) {
				if (foo.id == foo.parentNode.childNodes[i].id) { break; }
			}
			if (i == foo.parentNode.childNodes.length - 1) { foo.parentNode._last = true; }
			else { foo.parentNode._last = false; }
			foo = foo.parentNode;
		}
		webFXTreeHandler.insertHTMLBeforeEnd(document.getElementById(this.id + '-cont'), node.toString());
		if (!this.folder) {
			this.icon = webFXTreeConfig.folderIcon;
			this.openIcon = webFXTreeConfig.openFolderIcon;
		}
		if (!this.folder) { this.folder = true; this.collapse(true); }
		if (!bNoIdent) { this.indent(); }
	}
	return node;
}

WebFXTreeAbstractNode.prototype.toggle = function() {
	if (this.folder) {
		if (this.open) { this.collapse(); }
		else { this.expand(); }
}	}

WebFXTreeAbstractNode.prototype.select = function() {
	document.getElementById(this.id + '-anchor').focus();
}

WebFXTreeAbstractNode.prototype.deSelect = function() {
	document.getElementById(this.id + '-anchor').className = '';
	webFXTreeHandler.selected = null;
}

WebFXTreeAbstractNode.prototype.focus = function() {
	if ((webFXTreeHandler.selected) && (webFXTreeHandler.selected != this)) { webFXTreeHandler.selected.deSelect(); }
	webFXTreeHandler.selected = this;
	if ((this.openIcon) && (webFXTreeHandler.behavior != 'classic')) { document.getElementById(this.id + '-icon').src = this.openIcon; }

	document.getElementById(this.id + '-anchor').className = 'selected';
	document.getElementById(this.id + '-anchor').focus();
	if (webFXTreeHandler.onSelect) { webFXTreeHandler.onSelect(this); }
}

WebFXTreeAbstractNode.prototype.blur = function() {
	if ((this.openIcon) && (webFXTreeHandler.behavior != 'classic')) { document.getElementById(this.id + '-icon').src = this.icon; }
	document.getElementById(this.id + '-anchor').className = 'selected-inactive';
}

WebFXTreeAbstractNode.prototype.doExpand = function() {
	_expand_node(this);		
}

//luohc add 2006-05-12.
function _expand_node(node){
	if (webFXTreeHandler.behavior == 'classic') { document.getElementById(node.id + '-icon').src = node.openIcon; }
	if (node.childNodes.length) {  document.getElementById(node.id + '-cont').style.display = 'block'; }
	node.open = true;
	if (webFXTreeConfig.usePersistence) {
		webFXTreeHandler.cookies.setCookie(node.id.substr(18,node.id.length - 18), '1');
    }
}

WebFXTreeAbstractNode.prototype.doCollapse = function() {
	if (webFXTreeHandler.behavior == 'classic') { document.getElementById(this.id + '-icon').src = this.icon; }
	if (this.childNodes.length) { document.getElementById(this.id + '-cont').style.display = 'none'; }
	this.open = false;
	if (webFXTreeConfig.usePersistence) {
		webFXTreeHandler.cookies.setCookie(this.id.substr(18,this.id.length - 18), '0');
}	}

WebFXTreeAbstractNode.prototype.expandAll = function() {
	this.expandChildren();
	if ((this.folder) && (!this.open)) { this.expand(); }
}

WebFXTreeAbstractNode.prototype.expandChildren = function() {
	for (var i = 0; i < this.childNodes.length; i++) {
		this.childNodes[i].expandAll();
} }

WebFXTreeAbstractNode.prototype.collapseAll = function() {
	this.collapseChildren();
	if ((this.folder) && (this.open)) { this.collapse(true); }
}

WebFXTreeAbstractNode.prototype.collapseChildren = function() {
	for (var i = 0; i < this.childNodes.length; i++) {
		this.childNodes[i].collapseAll();
} }

WebFXTreeAbstractNode.prototype.indent = function(lvl, del, last, level, nodesLeft) {

	if (lvl == null) { lvl = -2; }
	var state = 0;
	for (var i = this.childNodes.length - 1; i >= 0 ; i--) {
		state = this.childNodes[i].indent(lvl + 1, del, last, level);
		if (state) { return; }
	}
	if (del) {
		if ((level >= this._level) && (document.getElementById(this.id + '-plus'))) {
			if (this.folder) {
				document.getElementById(this.id + '-plus').src = (this.open)?webFXTreeConfig.lMinusIcon:webFXTreeConfig.lPlusIcon;
				this.plusIcon = webFXTreeConfig.lPlusIcon;
				this.minusIcon = webFXTreeConfig.lMinusIcon;
			}
			else if (nodesLeft) { document.getElementById(this.id + '-plus').src = webFXTreeConfig.lIcon; }
			return 1;
	}	}
	var foo = document.getElementById(this.id + '-indent-' + lvl);
	if (foo) {
		if ((foo._last) || ((del) && (last))) { foo.src =  webFXTreeConfig.blankIcon; }
		else { foo.src =  webFXTreeConfig.iIcon; }
	}
	return 0;
}

// 设置节点的id。luohc 2005-11-30.
WebFXTreeAbstractNode.prototype.setId = function (id) {
	this.oid = id;
	webFXTreeHandler.all[id] = this;	
}

// 获得节点的id。luohc 2005-11-30.
WebFXTreeAbstractNode.prototype.getId = function () {
	if(this.oid){
	    return this.oid;
	}else{
		return this.id;
	}
}

// 根据Id获得节点对象。 luohc 2005-11-30.
function getNodeById(id){
	return webFXTreeHandler.all[id];	
}

// 设置节点的名称。luohc 2005-7-28.
WebFXTreeAbstractNode.prototype.setText = function (txt) {
	var id = this.id + "-anchor";
	var anchor = document.getElementById(id);
	if(anchor != null){
		anchor.innerText = txt;
	}
}

// 设置节点的颜色。luohc 2005-7-28.
WebFXTreeAbstractNode.prototype.setColor = function (color) {
	var id = this.id + "-anchor";
	var anchor = document.getElementById(id);
	if(anchor != null){
		anchor.style.color = color;
	}
	this.color = color;
}

// 设置节点的颜色。luohc 2005-7-28.
WebFXTreeAbstractNode.prototype.getColor = function (color) {
	if(this.color){
		return this.color;		
	}else if(this._disabled){
		return webFXTreeConfig.disableColor;
	}else{
		return webFXTreeConfig.defaultColor;
	}
}

// luohc.
function getTreeRoot(){
   return tree_root;
}

/*
 * WebFXTree class
 */
var tree_root; // luohc
function WebFXTree(sText, sAction, sBehavior, sIcon, sOpenIcon) {
	this.base = WebFXTreeAbstractNode;
	this.base(sText, sAction);
	this.icon      =  webFXTreeConfig.rootIcon;
	this.openIcon  =  webFXTreeConfig.openRootIcon;
	if(sIcon){
		this.icon = sIcon;
		this.openIcon  = sIcon;
	}
	if(sOpenIcon){
		this.openIcon = sOpenIcon;
	}
	/* Defaults to open */
	if (webFXTreeConfig.usePersistence) {
		this.open  = (webFXTreeHandler.cookies.getCookie(this.id.substr(18,this.id.length - 18)) == '0')?false:true;
	} else { this.open  = true; }
	this.folder    = true;
	this.rendered  = false;
	this.onSelect  = null;
	if (!webFXTreeHandler.behavior) {  webFXTreeHandler.behavior = sBehavior || webFXTreeConfig.defaultBehavior; }

	tree_root = this; // luohc 2004-7-30.
}

WebFXTree.prototype = new WebFXTreeAbstractNode;

WebFXTree.prototype.setBehavior = function (sBehavior) {
	webFXTreeHandler.behavior =  sBehavior;
};

WebFXTree.prototype.getBehavior = function (sBehavior) {
	return webFXTreeHandler.behavior;
};

WebFXTree.prototype.getSelected = function() {
	if (webFXTreeHandler.selected) { return webFXTreeHandler.selected; }
	else { return null; }
}

WebFXTree.prototype.remove = function() { }

WebFXTree.prototype.expand = function() {
	this.doExpand();
}

WebFXTree.prototype.collapse = function(b) {
	if (!b) { this.focus(); }
	this.doCollapse();
}

WebFXTree.prototype.getFirst = function() {
	return null;
}

WebFXTree.prototype.getLast = function() {
	return null;
}

WebFXTree.prototype.getNextSibling = function() {
	return null;
}

WebFXTree.prototype.getPreviousSibling = function() {
	return null;
}

WebFXTree.prototype.keydown = function(key) {
	if (key == 39) {
		if (!this.open) { this.expand(); }
		else if (this.childNodes.length) { this.childNodes[0].select(); }
		return false;
	}
	if (key == 37) { this.collapse(); return false; }
	if ((key == 40) && (this.open) && (this.childNodes.length)) { this.childNodes[0].select(); return false; }
	return true;
}

WebFXTree.prototype.toString = function() {
	var str = "<div id=\"" + this.id + "\" ondblclick=\"webFXTreeHandler.toggle(this);\" class=\"webfx-tree-item\" onkeydown=\"return webFXTreeHandler.keydown(this, event)\">" +
		"<img id=\"" + this.id + "-icon\" class=\"webfx-tree-icon\" src=\"" + ((webFXTreeHandler.behavior == 'classic' && this.open)?this.openIcon:this.icon) + "\" onclick=\"webFXTreeHandler.select(this);\">" +
		"<a href=\"" + this.action + "\" id=\"" + this.id + "-anchor\" onfocus=\"webFXTreeHandler.focus(this);\" onblur=\"webFXTreeHandler.blur(this);\" onclick=\"webFXTreeHandler.focus(this);\"" + 
		(this.target ? " target=\"" + this.target + "\"" : "") + " style='color:" + this.getColor() + "'" +
		">" + this.text + "</a></div>" +
		"<div id=\"" + this.id + "-cont\" class=\"webfx-tree-container\" style=\"display: " + ((this.open)?'block':'none') + ";\">";
	var sb = [];
	for (var i = 0; i < this.childNodes.length; i++) {
		sb[i] = this.childNodes[i].toString(i, this.childNodes.length);
	}
	this.rendered = true;
	return str + sb.join("") + "</div>";
};

/*
 * WebFXTreeItem class
 */

function WebFXTreeItem(sText, sAction, eParent, sIcon, sOpenIcon) {
	this.base = WebFXTreeAbstractNode;
	this.base(sText, sAction);
	/* Defaults to close */
	if (webFXTreeConfig.usePersistence) {
		this.open = (webFXTreeHandler.cookies.getCookie(this.id.substr(18,this.id.length - 18)) == '1')?true:false;
	} else { this.open = false; }

	if (sIcon) { 
		this.icon = sIcon; 
		this.openIcon = sIcon; 
	}
	if (sOpenIcon) { this.openIcon = sOpenIcon; }
	if (eParent) { eParent.add(this); }
}

WebFXTreeItem.prototype = new WebFXTreeAbstractNode;

WebFXTreeItem.prototype.remove = function() {
	var iconSrc = document.getElementById(this.id + '-plus').src;
	var parentNode = this.parentNode;
	var prevSibling = this.getPreviousSibling(true);
	var nextSibling = this.getNextSibling(true);
	var folder = this.parentNode.folder;
	var last = ((nextSibling) && (nextSibling.parentNode) && (nextSibling.parentNode.id == parentNode.id))?false:true;
	this.getPreviousSibling().focus();
	this._remove();
	if (parentNode.childNodes.length == 0) {
		document.getElementById(parentNode.id + '-cont').style.display = 'none';
		parentNode.doCollapse();
		parentNode.folder = false;
		parentNode.open = false;
	}
	if (!nextSibling || last) { parentNode.indent(null, true, last, this._level, parentNode.childNodes.length); }
	if ((prevSibling == parentNode) && !(parentNode.childNodes.length)) {
		prevSibling.folder = false;
		prevSibling.open = false;
		iconSrc = document.getElementById(prevSibling.id + '-plus').src;
		iconSrc = iconSrc.replace('minus', '').replace('plus', '');
		document.getElementById(prevSibling.id + '-plus').src = iconSrc;
		document.getElementById(prevSibling.id + '-icon').src = webFXTreeConfig.fileIcon;
	}
	if (document.getElementById(prevSibling.id + '-plus')) {
		if (parentNode == prevSibling.parentNode) {
			iconSrc = iconSrc.replace('minus', '').replace('plus', '');
			document.getElementById(prevSibling.id + '-plus').src = iconSrc;
}	}	}

WebFXTreeItem.prototype._remove = function() {
	for (var i = this.childNodes.length - 1; i >= 0; i--) {
		this.childNodes[i]._remove();
 	}
	for (var i = 0; i < this.parentNode.childNodes.length; i++) {
		if (this == this.parentNode.childNodes[i]) {
			for (var j = i; j < this.parentNode.childNodes.length; j++) {
				this.parentNode.childNodes[j] = this.parentNode.childNodes[j+1];
			}
			this.parentNode.childNodes.length -= 1;
			if (i + 1 == this.parentNode.childNodes.length) { this.parentNode._last = true; }
			break;
	}	}
	webFXTreeHandler.all[this.id] = null;
	var tmp = document.getElementById(this.id);
	if (tmp) { tmp.parentNode.removeChild(tmp); }
	tmp = document.getElementById(this.id + '-cont');
	if (tmp) { tmp.parentNode.removeChild(tmp); }
}


WebFXTreeItem.prototype.expand = function() {
	// 增加展开父节点。 luohc 2006-05-12.	
	if(this.parentNode != null && this.parentNode.open == false) this.parentNode.expand();
    if(this.childNodes.length){
		this.doExpand();
		document.getElementById(this.id + '-plus').src = this.minusIcon;
	}
}

WebFXTreeItem.prototype.collapse = function(b) {
	if (!b) { this.focus(); }
	this.doCollapse();
	document.getElementById(this.id + '-plus').src = this.plusIcon;
}

WebFXTreeItem.prototype.getFirst = function() {
	return this.childNodes[0];
}

WebFXTreeItem.prototype.getLast = function() {
	if (this.childNodes[this.childNodes.length - 1].open) { return this.childNodes[this.childNodes.length - 1].getLast(); }
	else { return this.childNodes[this.childNodes.length - 1]; }
}

WebFXTreeItem.prototype.getNextSibling = function() {
	for (var i = 0; i < this.parentNode.childNodes.length; i++) {
		if (this == this.parentNode.childNodes[i]) { break; }
	}
	if (++i == this.parentNode.childNodes.length) { return this.parentNode.getNextSibling(); }
	else { return this.parentNode.childNodes[i]; }
}

WebFXTreeItem.prototype.getPreviousSibling = function(b) {
	for (var i = 0; i < this.parentNode.childNodes.length; i++) {
		if (this == this.parentNode.childNodes[i]) { break; }
	}
	if (i == 0) { return this.parentNode; }
	else {
		if ((this.parentNode.childNodes[--i].open) || (b && this.parentNode.childNodes[i].folder)) { return this.parentNode.childNodes[i].getLast(); }
		else { return this.parentNode.childNodes[i]; }
} }

WebFXTreeItem.prototype.keydown = function(key) {
	if ((key == 39) && (this.folder)) {
		if (!this.open) { this.expand(); }
		else { this.getFirst().select(); }
		return false;
	}
	else if (key == 37) {
		if (this.open) { this.collapse(); }
		else { this.parentNode.select(); }
		return false;
	}
	else if (key == 40) {
		if (this.open) { this.getFirst().select(); }
		else {
			var sib = this.getNextSibling();
			if (sib) { sib.select(); }
		}
		return false;
	}
	else if (key == 38) { this.getPreviousSibling().select(); return false; }
	return true;
}

WebFXTreeItem.prototype.toString = function (nItem, nItemCount) {
	var foo = this.parentNode;
	var indent = '';
	if (nItem + 1 == nItemCount) { this.parentNode._last = true; }
	var i = 0;
	while (foo.parentNode) {
		foo = foo.parentNode;
		indent = "<img id=\"" + this.id + "-indent-" + i + "\" src=\"" 
			+ ((foo._last)?webFXTreeConfig.blankIcon:webFXTreeConfig.iIcon) + "\">" + indent;
		i++;
	}
	this._level = i;
	if (this.childNodes.length) { this.folder = 1; }
	else { this.open = false; }
	if ((this.folder) || (webFXTreeHandler.behavior != 'classic')) {
		if (!this.icon) { this.icon = webFXTreeConfig.folderIcon; }
		if (!this.openIcon) { this.openIcon = webFXTreeConfig.openFolderIcon; }
	} else if (!this.icon) { this.icon = webFXTreeConfig.fileIcon; }

	if(!this.openIcon){ this.openIcon = webFXTreeConfig.fileIcon; }

	var label = this.text.replace(/</g, '&lt;').replace(/>/g, '&gt;');
	var str = "<div id=\"" + this.id + "\" ondblclick=\"webFXTreeHandler.toggle(this);\" class=\"webfx-tree-item\" onkeydown=\"return webFXTreeHandler.keydown(this, event)\">" +
		indent +
		"<img id=\"" + this.id + "-plus\" src=\"" + ((this.folder)?((this.open)?((this.parentNode._last)?webFXTreeConfig.lMinusIcon:webFXTreeConfig.tMinusIcon):((this.parentNode._last)?webFXTreeConfig.lPlusIcon:webFXTreeConfig.tPlusIcon)):((this.parentNode._last)?webFXTreeConfig.lIcon:webFXTreeConfig.tIcon)) + "\" onclick=\"webFXTreeHandler.toggle(this);\">" +
		"<img id=\"" + this.id + "-icon\" class=\"webfx-tree-icon\" src=\"" + ((webFXTreeHandler.behavior == 'classic' && this.open)?this.openIcon:this.icon) + "\" onclick=\"webFXTreeHandler.select(this);\">" +
		"<a href=\"" + this.action + "\" id=\"" + this.id + "-anchor\" onfocus=\"webFXTreeHandler.focus(this);\" onblur=\"webFXTreeHandler.blur(this);\"" + " onclick=\"webFXTreeHandler.focus(this);\"" +
		(this.target ? " target=\"" + this.target + "\"" : "") + " style='color:" + this.getColor() + "'" +
		">" + label + "</a></div>" +
		"<div id=\"" + this.id + "-cont\" class=\"webfx-tree-container\" style=\"display: " + ((this.open)?'block':'none') + ";\">";
	var sb = [];
	for (var i = 0; i < this.childNodes.length; i++) {
		sb[i] = this.childNodes[i].toString(i,this.childNodes.length);
	}
	this.plusIcon = ((this.parentNode._last)?webFXTreeConfig.lPlusIcon:webFXTreeConfig.tPlusIcon);
	this.minusIcon = ((this.parentNode._last)?webFXTreeConfig.lMinusIcon:webFXTreeConfig.tMinusIcon);
	return str + sb.join("") + "</div>";
}

// 2006-05-12 新增节点移动方法. luohc.
WebFXTreeItem.prototype.move = function (pNode, index) {	


    var node = _moveChildBeforeRemove(this);
	this.remove();
	_addChildTree(pNode, node);

};

function _moveChildBeforeRemove(node){
	if(node.childNodes.length){
		node.children = [];
		for(var i=0; i<node.childNodes.length; i++){
			node.children[node.children.length] = node.childNodes[i];
			_moveChildBeforeRemove(node.childNodes[i]);
		}
	}
	return node;
}

function _addChildTree(pNode, node){
	webFXTreeHandler.all[node.id] = node;
	pNode.add(node);
	if(node.children){
		for(var i=0; i<node.children.length; i++){
			_addChildTree(node, node.children[i]);
		}
	}
}