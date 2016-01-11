// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.msgBlackListInit = function() {
	  var $this = $(this);
	  var id = $(this).attr('id');
	
	  var viewList =  $('#viewList_'+id,$this);
	  var queryContent = $('#queryContent_'+id,$this);
	  
	  //加载列表
	  $(viewList).datagrid({
		singleSelect:true,
		method:"POST",
		nowrap:true,
		striped: true,
		collapsible:true,
		pagination:true,
		rownumbers:true,
		selectOnCheck:false,
		checkOnSelect:false,
		singleSelect:true,
		fit:true,
		columns:[[
		      {field:'ck',checkbox:true},
			{field:'blackCode',title:'黑字',width:300,align:"center"}
		]],
		onClickRow:function(rowIndex, rowData){
			selectRow = rowData;
			selectIndex = rowIndex;
		},
		onDblClickRow:function(rowIndex,rowData){
			onUpdate();
		},
		onLoadSuccess:function(){
			selectRow = null;
	 		selectIndex = null;
			pageNumber = 1;
		}
	  });
	//查询
	var search = function(){
		var blackCodeSearch = $('#blackCodeSearch',queryContent).val();
		var content = {blackCode:blackCodeSearch};
		
		$(viewList).datagrid({
			url:"system/queryMsgBlackList.do",
			queryParams:content
		});
		checkBtnRight();
	}
	//查询
	$('#search',$this).click(function(){
		search();
	})
  }
})(jQuery);   