// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.roomInit = function() {
	  var $this = $(this);
	  var id = $(this).attr('id');
	  var rightId = $(this).attr('rightId');
	  var selectRow = null;
	  var selectIndex = null;
	
	  var editDialog = $('#editDialog_'+id,$this);
	  var editForm = $('#editForm_'+id,editDialog);
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
		toolbar:'#tb_'+id,
		columns:[[
		      {field:'ck',checkbox:true},
	          {field:'state',title:'状态',width:50,sortable:true,align:"center",
					formatter: function(value,row,index){
						if (value=='false'){
							return '<img src="style/v1/icons/warn.png"/>';
						} else if (value=='true'){
							return '<img src="style/v1/icons/info.png"/>';
						}
				 }},
			{field:'dataDictionaryCode',title:'考场编号',width:100,align:"center"},
			{field:'dataDictionaryName',title:'考场名称',width:200,align:"center"},
			{field:'roomComputerCount',title:'机器数',width:100,align:"center"},
			{field:'note',title:'备注',width:100,align:"center"}
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
		}
	  });
	  //添加
	  $('#add_'+id,$this).click(function(){
		  onAdd();
	  })
	  //修改
	  $('#update_'+id,$this).click(function(){
		  onUpdate();
	  })
	  //删除
	  $('#delete_'+id,$this).click(function(){
		  onMulDelete();
	  })
	  //状态
	  $('#state_'+id).menu({  
		    onClick:function(item){
		    	if(item.name=='true'){
		    		onMulUpdateState(true);
		    	}else{
		    		onMulUpdateState(false);
		    	}
		    }
		    	
	  });
	//考场权限
	  $('#dataDictionaryRight_'+id,$this).click(function(){
		  onDictRight();
	  })
	//编辑框
	$(editDialog).dialog({  
	    title: '编辑考场',  
	    width:400,
	    height:300,
	    closed: true,  
	    cache: false,  
	    modal: true,
	    closable:false,
	    toolbar:[{
			text:'保存',
			iconCls:'icon-save',
			handler:function(){
				onSave();
			}
		},'-',{
			text:'退出',
			iconCls:'icon-cancel',
			handler:function(){
				$(editDialog).dialog('close');
			}
		}]
	});    
	//添加
	var onAdd = function(){
		$(editForm).form('clear');
		$('#dataDictionaryType',editForm).val('Room');
		$('#state',editForm).combobox('setValue','true');
		$('#roomComputerCount',editForm).numberbox('setValue',0);
		$(editDialog).dialog('open');
	}
	//保存前的赋值操作
	var setValue = function(){
		var dataDictionaryCode = $.trim($('#dataDictionaryCode',editForm).val());
		if(''==dataDictionaryCode){
			$.messager.alert('提示','请填写考场编号','warning');
			return false;
		}
		var dataDictionaryName = $.trim($('#dataDictionaryName',editForm).val());
		if(''==dataDictionaryName){
			$.messager.alert('提示','请填写考场名称','warning');
			return false;
		}
		return true;
	}
	//保存
	var onSave = function(){
		$(editForm).form('submit',{
			url:'dict/saveDict.do',
			onSubmit: function(){
				return setValue();
			},
			success: function(data){
				var result = eval('('+data+')');
				if(result.isSuccess){
					var fn = function(){
						var dataDictionaryId = $.trim($('#dataDictionaryId',editForm).val());
						//新增
						if(dataDictionaryId==''){
							search();
						}else{
							var row = $(editForm).serializeObject();
							$(viewList).datagrid('updateRow',{index:selectIndex,row:row});
						}
						EXAM.RoomList = null;
					}
					$.messager.alert('提示','保存成功','info',fn);
					$(editDialog).dialog('close');
				}else{
					$.messager.alert('提示',result.message,"error");
				}
			}
		});
	}
	//修改
	var onUpdate = function(){
		if(!$('#update_'+id,$this).is(":hidden")){
			if(selectRow==null){
				$.messager.alert("提示","请选择数据行","warning");
				return;
			}
			$(editForm).form('load',selectRow);
			$(editDialog).dialog('open');
		}
	 }
	//查询
	var search = function(flag){
		var dataDictionaryCodeSearch = $('#dataDictionaryCodeSearch',queryContent).val();
		var dataDictionaryNameSearch = $('#dataDictionaryNameSearch',queryContent).val();
		var content = {dataDictionaryName:dataDictionaryNameSearch,dataDictionaryCode:dataDictionaryCodeSearch,dataDictionaryType:'Room'};
		
		$(viewList).datagrid({
			url:"dict/queryDict.do",
			queryParams:content
		});
		checkBtnRight();
	}
	//查询
	$('#search',$this).click(function(){
		search();
	})
	//批量删除
	var onMulDelete = function(){
		var rows = $(viewList).datagrid('getChecked');
		if(rows.length==0){
			 $.messager.alert('提示',"请选中要删除的纪录","warming");
			 return;	
		}
		$.messager.confirm("提示！","确定要删除选中的记录?",function(t){ 
			if(!t) return;
			if(rows.length==0){
				 $.messager.alert('提示',"请选中要删除的纪录","warming");
				 return;	
			}
			var idArray = new Array();
			for(var i=0;i<rows.length;i++){
				idArray.push(rows[i].dataDictionaryId);
			}
			var ids = idArray.join(CSIT.join);
			var url = "dict/mulDeleteDict.do";
			var content = {ids:ids};
			$.post(url,content,
				function(result){
					if(result.isSuccess){
						search();
					}else{
						$.messager.alert('提示',result.message,"error");
					}
				}, "json");
		});
	}
	//修改多个审核状态
	var onMulUpdateState = function(state){
		var rows =  $(viewList).datagrid('getChecked');
		var msg = '';
		if(state){
			msg = '启用';
		}else{
			msg = '禁用';
		}
		if(rows.length==0){
			$.messager.alert("提示","请选择要"+msg+"的数据行","warning");
			return;
		}
		var idArray = new Array();
		for ( var i = 0; i < rows.length; i++) {
			idArray.push(rows[i].dataDictionaryId);
		}
		$.messager.confirm("提示","确定要"+msg+"记录?",function(t){ 
			if(t){
				var url = 'dict/mulUpdateStateDict.do';
				var content ={ids:idArray.join(CSIT.join),state:state};
				asyncCallService(url,content,function(result){
					if(result.isSuccess){
						var fn = function(){
							search(true);
						}
						$.messager.alert('提示',msg+'成功','info',fn);
					}else{
						$.messager.alert('提示',result.message,'error');
					}
				});
			}
		});
	}
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
		var tbStateBtn = $('#tbState_'+id,$this);
		var dataDictionaryRightBtn = $('#dataDictionaryRight_'+id,$this);
		
		checkArray.push(addBtn);
		checkArray.push(updateBtn);
		checkArray.push(deleteBtn);
		checkArray.push(tbStateBtn);
		checkArray.push(dataDictionaryRightBtn);
		
		checkRight(checkArray,rights);
	}
	checkBtnRight();
  }
})(jQuery);   