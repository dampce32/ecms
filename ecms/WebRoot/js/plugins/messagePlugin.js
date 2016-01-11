// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.messageInit = function() {
	  var $this = $(this);
	  var id = $(this).attr('id');
	  var height = $(document.body).height();
	  var rightId = $(this).attr('rightId');
	  var selectRow = null;
	  var selectIndex = null;
	
	  var editDialog = $('#editDialog_'+id,$this);
	  var addDialog = $('#addDialog_'+id,$this);
	  var editForm = $('#editForm_'+id,$this);
	  var viewList = $('#viewList_'+id,editDialog);
	  var addList = $('#addList_'+id,addDialog);
	  var list = $('#list_'+id,$this);
	  var queryContent = $('#queryContent_'+id,$this);
	  
	  $('#timingCheckbox',editForm).attr('checked',false);
	  $('#sendTime',editForm).hide();
	  	
	  $('#timingCheckbox',editForm).click(function(){
		  if($(this).attr('checked')=='checked'){
			  $('#sendTime',editForm).show();
		  }else{
			  $('#sendTime',editForm).hide();
		  }
	  });
	  
	  $('#messageContent').height(396-587+height);
	  
	  var onSave = function(){
		  var messageContent = $('#messageContent',editForm).val();
		  if($.trim(messageContent)==''){
			  $.messager.alert('提示','发送短信不能为空','warning');
			  return;
		  }
		  var rows = $(list).datagrid('getRows');
		  if(rows.length==0){
			  $.messager.alert('提示','至少选择一个发送对象','warning');
			  return;
		  }
		  var array = new Array();
		  var nameArray = new Array();
		  var mobilePhoneArray = new Array();
		  $(rows).each(function(index,row){
			  array.push(row.contactsId);
			  nameArray.push(row.name==''?' ':row.name);
			  mobilePhoneArray.push(row.mobilePhone);
		  });
		  $('#receiveIDs',editForm).val(array.join(','));
		  $('#names',editForm).val(nameArray.join(','));
		  $('#mobilePhones',editForm).val(mobilePhoneArray.join(','));
		  $(editForm).form('submit',{
			  url:'contacts/sendMessageSend.do',
				success: function(data){
					var result = eval('('+data+')');
					if(result.isSuccess){
						$.messager.alert('提示','加入发送队列','info');
						$(editDialog).dialog('close');
					}else{
						$.messager.alert('提示',result.message,"error");
					}
				}
		  });
	  	}
	  $('#saveBtn_'+id,$this).click(function(){
		  onSave();
	  })
	  $('#search',queryContent).click(function(){
		  var name = $('#name',queryContent).val();
		  var mobilePhone = $('#mobilePhone',queryContent).val();
		  var url ='contacts/queryContacts.do';
		  var content = {name:name,mobilePhone:mobilePhone};
		  $(viewList).datagrid({
			  url:url,
			  queryParams:content,
			  pageNumber:1
		  })
	  })
	  
	  var onAdd = function(){
		  $(editDialog).dialog('open');
	  }
	  var onAddByInput = function(){
		   $(addDialog).dialog('open');
	  }
	  var onDelete = function(){
		var rows = $(list).datagrid('getSelections');
		if(rows.length==0){
			$.messager.alert('提示','至少选择一条数据','warning');
			return;
		}
		$(rows).each(function(index,row){
			var i = $(list).datagrid('getRowIndex',row);
			$(list).datagrid('deleteRow',i);
		})
	  }
	   //加载列表
	  $(list).datagrid({
		singleSelect:false,
		method:"POST",
		nowrap:true,
		fitColumns:true,
		fit:true,
		striped: true,
		collapsible:true,
		pagination:false,
		rownumbers:true,
		toolbar:[
	    		{text:'通讯录',iconCls:'icon-add',handler:function(){onAdd();}},'-',
	    		{text:'手动输入',iconCls:'icon-add',handler:function(){onAddByInput();}},'-',
	    		{text:'删除',iconCls:'icon-remove',handler:function(){onDelete()}
		}],
		columns:[[
		    {field:'contactsId',checkbox:true},
			{field:'name',title:'姓名',width:100,align:"center"},
			{field:'mobilePhone',title:'电话号码',width:100,align:"center"}
		]]
	 });
	  //加载列表
	  $(viewList).datagrid({
		method:"POST",
		nowrap:true,
		striped: true,
		collapsible:true,
		pagination:true,
		rownumbers:true,
		singleSelect:false,
		fitColumns:true,
		fit:true,
		columns:[[
		    {field:'contactsId',checkbox:true},
			{field:'name',title:'姓名',width:100,align:"center"},
			{field:'mobilePhone',title:'电话号码',width:100,align:"center"}
		]]
	 });
	var onSelect = function(){
		var rows = $(viewList).datagrid('getSelections');
		if(rows.length==0){
			$.messager.alert('提示','至少选择一条数据','warning');
			return;
		}
		$(rows).each(function(index,row){
			$(list).datagrid('appendRow',{contactsId:row.contactsId,name:row.name,mobilePhone:row.mobilePhone});
		})
		onExit();
	} 
	var onExit = function(){
		$(editDialog).dialog('close');
		$('#name',queryContent).val('');
		$('#mobilePhone',queryContent).val('');
		$(viewList).datagrid('loadData',CSIT.ClearData);
	}
	//编辑框
	$(editDialog).dialog({ 
		title:'通讯录',
	    width:750,
	    height:height-30,
	    closed: true,  
	    cache: false,  
	    modal: true,
	    closable:false,
	    toolbar:[
	    		{text:'选择',iconCls:'icon-ok',handler:function(){onSelect();}},'-',
	    		{text:'退出',iconCls:'icon-cancel',handler:function(){onExit();}
		}]
	});
	//保存
	var onAddSave = function(){
		var rows = $(addList).datagrid('getRows');
		if(rows.length==0){
			$.messager.alert('提示','至少添加一条数据','warning');
			return;
		}
		if(lastIndex!=null){
			$(addList).datagrid('endEdit', lastIndex);
			lastIndex=null;
		}
		$(rows).each(function(index,row){
			var contactsId = null;
			if(row.visible=='是'){
				contactsId=0;
			}else{
				contactsId=-1;
			}
			$(list).datagrid('appendRow',{
				contactsId:contactsId,
				name:row.name,
				mobilePhone:row.mobilePhone
			});
		})
		onAddExit();
	}
	//退出
	var onAddExit = function(){
		$(addDialog).dialog('close');
		$(addList).datagrid('loadData',CSIT.empty_row);
	}
	$(addDialog).dialog({ 
		title:'添加发送对象',
	    width:750,
	    height:height-30,
	    closed: true,  
	    cache: false,  
	    modal: true,
	    closable:false,
	    toolbar:[
	    		{text:'保存',iconCls:'icon-ok',handler:function(){onAddSave();}},'-',
	    		{text:'退出',iconCls:'icon-cancel',handler:function(){onAddExit();}
		}]
	});
	//添加发送对象
	var onAddSend = function(){
		if(lastIndex!=null){
			$(addList).datagrid('endEdit', lastIndex);
		}
		$(addList).datagrid('appendRow',{
			contactsId:'',
			name:'',
			visible:'是'
		});
	}
	//删除发送对象
	var onDeleteSend = function(){
		var rows = $(addList).datagrid('getSelections');
		if(rows.length==0){
			$.messager.alert('提示','至少选择一条数据','warning');
			return;
		}
		$(rows).each(function(index,row){
			var i = $(addList).datagrid('getRowIndex',row);
			$(addList).datagrid('deleteRow',i);
		})
		lastIndex=null;
	}
	var lastIndex = null;
	
	$(addList).datagrid({
		singleSelect:false,
		method:"POST",
		nowrap:true,
		fitColumns:true,
		fit:true,
		striped: true,
		collapsible:true,
		pagination:false,
		rownumbers:true,
		toolbar:[
	    		{text:'新增',iconCls:'icon-add',handler:function(){onAddSend();}},'-',
	    		{text:'删除',iconCls:'icon-remove',handler:function(){onDeleteSend()}
		}],
		columns:[[
		    {field:'contactsId',checkbox:true},
			{field:'name',title:'姓名',width:100,align:"center",editor:'text'},
			{field:'mobilePhone',title:'电话号码',width:100,align:"center",editor:{type:'numberbox',options:{precision:0}}},
			{field:'visible',title:'添加到通讯录',width:100,align:"center",editor:{type:'checkbox',options:{on:'是',off:'否'}}}
		]],
		onClickRow:function(rowIndex){
			if (lastIndex != rowIndex){
				$(addList).datagrid('endEdit', lastIndex);
				$(addList).datagrid('beginEdit', rowIndex);
			}
			lastIndex = rowIndex;
		}
	 });
  }
})(jQuery);   