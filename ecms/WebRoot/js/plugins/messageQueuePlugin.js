// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.messageQueueInit = function() {
	  var $this = $(this);
	  var id = $(this).attr('id');
	  var height = $(document.body).height();
	  var selectRow = null;
	  var selectIndex = null;
	
	  var editDialog = $('#editDialog_'+id,$this);
	  var viewList = $('#viewList_'+id,$this);
	  //显示接收对象
	  var onShow = function(){
		  if(selectRow==null){
			  $.messager.alert('提示','请选择数据行','warning');
			  return;
		  }
		  var url ='contacts/getReceiverMessageSend.do';
		  var content ={receiveIDs:selectRow.receiveIDs};
		 $('#detailList_'+id,editDialog).datagrid({
			 url:url,
			 queryParams:content
		 });
		  $(editDialog).dialog('open');
	  };
	  var onReload = function(){
		  $(viewList).datagrid('reload');
	  };
	  //加载列表
	  $(viewList).datagrid({
		url:'contacts/queryFailedMessageSend.do',
		queryParams:{errorCount:0},
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
		toolbar:[
	    		{text:'刷新',iconCls:'icon-reload',handler:function(){onReload();}
	    	}],
		columns:[[
			{field:'sendTime',title:'发送时间',width:150,align:"center"},
			{field:'messageContent',title:'内容',width:600,align:"center"},
			{field:'receiveIDs',title:'收件人',width:200,align:"center"}
		]],
		onClickRow:function(rowIndex, rowData){
			selectRow = rowData;
			selectIndex = rowIndex;
		},
		onDblClickRow:function(rowIndex,rowData){
			onShow();
		}
	 });
	//编辑框
	$(editDialog).dialog({ 
		title:'发送记录',
	    width:450,
	    height:height-30,
	    closed: true,  
	    cache: false,  
	    modal: true,
	    closable:false,
	    toolbar:[
	    		{text:'退出',iconCls:'icon-cancel',handler:function(){$(editDialog).dialog('close');}
		}]
	});
	//加载列表
	$('#detailList_'+id,editDialog).datagrid({
		singleSelect:true,
		method:"POST",
		nowrap:true,
		striped: true,
		collapsible:true,
		pagination:false,
		rownumbers:true,
		singleSelect:true,
		fit:true,
		columns:[[
			{field:'name',title:'姓名',width:100,align:"center"},
			{field:'mobilePhone',title:'电话号码',width:100,align:"center"}
		]]
	 });
  };
})(jQuery);   