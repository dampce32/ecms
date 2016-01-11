// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.contactListInit = function() {
	  var $this = $(this);
	  var id = $(this).attr('id');
	  var height = $(document.body).height();
	  var rightId = $(this).attr('rightId');
	  var selectRow = null;
	  var selectIndex = null;
	
	  var editDialog = $('#editDialog_'+id,$this);
	  var editForm = $('#editForm_'+id,editDialog);
	  var viewList = $('#viewList_'+id,$this);
	  var queryContent = $('#queryContent_'+id,$this);
	  
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
	  var onUpdate = function(){
		  if(selectRow==null){
			  $.messager.alert('提示','请选择需要修改的数据','warning');
			  return;
		  }
		  $(editForm).form('load',selectRow);
		  $(editDialog).dialog('open');
	  }
	  var onDelete = function(){
		  if(selectRow==null){
			  $.messager.alert('提示','请选择需要删除的数据','warning');
			  return;
		  }
		  $.messager.confirm('确认','确定删除该记录吗?',function(r){
			  if(r){
				  var url ='contacts/deleteContacts.do';
				  var content ={contactsId:selectRow.contactsId};
				  asyncCallService(url,content,function(result){
					  if(result.isSuccess){
							$.messager.alert('提示','删除成功','info',onReload);
							selectRow=null;
						}else{
							$.messager.alert('提示',result.message,"error");
						}
				  })
			  }
		  })
	  }
	  var onReload = function(){
		  $(viewList).datagrid('reload');
	  }
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
		toolbar:'#tb_'+id,
		columns:[[
		    {field:'contactsId',hidden:true},
			{field:'name',title:'姓名',width:100,align:"center"},
			{field:'mobilePhone',title:'手机号码',width:100,align:"center"},
			{field:'email',title:'Email',width:180,align:"center"},
			{field:'qqNumber',title:'QQ',width:100,align:"center"},
			{field:'msn',title:'MSN',width:100,align:"center"},
			{field:'note',title:'备注',width:300,align:"center"}
		]],
		onClickRow:function(rowIndex, rowData){
			selectRow = rowData;
			selectIndex = rowIndex;
		},
		onDblClickRow:function(rowIndex,rowData){
			onUpdate();
		}
	 });
	  //添加
	  $('#add_'+id,$this).click(function(){
		  onAdd();
	  });
	  //刷新
	  $('#reLoad_'+id,$this).click(function(){
		  onReload();
	  });
	  //修改
	  $('#update_'+id,$this).click(function(){
		  onUpdate();
	  });
	  //删除
	  $('#delete_'+id,$this).click(function(){
		  onMulDelete();
	  });
	var onSave = function(){
		var name = $('#name',editForm).val();
		if($.trim(name)==''){
			$.messager.alert('提示','姓名不能为空','warning');
			return;
		}
		var mobilePhone = $('#mobilePhone',editForm).val();
		if($.trim(mobilePhone)==''){
			$.messager.alert('提示','电话号码不能为空','warning');
			return;
		}
		$(editForm).form('submit',{
			url:'contacts/saveContacts.do',
			success: function(data){
				var result = eval('('+data+')');
				if(result.isSuccess){
					$.messager.alert('提示','保存成功','info',onReload);
					$(editDialog).dialog('close');
				}else{
					$.messager.alert('提示',result.message,"error");
				}
			}
		});
	};
	//编辑框
	$(editDialog).dialog({ 
		title:'编辑通讯录',
	    width:750,
	    height:300,
	    closed: true,  
	    cache: false,  
	    modal: true,
	    closable:false,
	    toolbar:[
	    		{text:'保存',iconCls:'icon-save',handler:function(){onSave();}},'-',
	    		{text:'退出',iconCls:'icon-cancel',handler:function(){$(editDialog).dialog('close');$(editForm).form('clear');}
		}]
	});
	//----------检查权限--------------
	var rights = null;
	var checkBtnRight = function(){
		if(rights==null){
			rights = getRights(rightId);
		}
		var checkArray = new Array();
		
		var addBtn = $('#add_'+id,$this);
		var updateBtn = $('#update_'+id,$this);
		var deleteBtn = $('#delete_'+id,$this);
		var reLoadBtn = $('#reLoad_'+id,$this);
		checkArray.push(addBtn);
		checkArray.push(updateBtn);
		checkArray.push(deleteBtn);
		checkArray.push(reLoadBtn);
		checkRight(checkArray,rights);
	}
	checkBtnRight();
  };
})(jQuery);   