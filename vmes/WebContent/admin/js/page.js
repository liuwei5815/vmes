/**
 * 此分独幕剧标签用在有iframe的情况下,请勿随意修改 
 */
function loadPage(){
	var par_totalcount = window.parent.document.getElementById("totalCountLable");  
	var par_currpage = window.parent.document.getElementById("currPageLable");
	var par_totalpage = window.parent.document.getElementById("totalPageLable");
	var par_text_currpage = window.parent.document.getElementById("currPage");
	var totalCount = document.getElementById("totalCount").value;
	var totalPage = document.getElementById("totalPage").value;
	var currPage = document.getElementById("currPage").value;
	/**alert("totalCount:" +totalCount);
	alert("totalPage:" +totalPage);
	alert("currPage:" +currPage);**/
	if(totalCount == ""){
		totalCount = "0";
		currPage = "0";
		totalPage = "0";
	}
	if(totalPage == ""){
		totalPage = "0";
	}
	if(currPage == ""){
		currPage = "0";
	}
	par_totalcount.innerHTML = totalCount;
	par_currpage.innerHTML = currPage;
	par_totalpage.innerHTML = totalPage;
	if(currPage=="0"){
		currPage="1";
	}
	par_text_currpage.value=currPage;
}


