// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.prizeInit = function() {
	  var $this = $(this);
	  var id = $(this).attr('id');
	  var rightId = $(this).attr('rightId');
	  var selectRow = null;
	  var selectIndex = null;
	  
	  var queryContent = $('#queryContent_'+id,$this);
	  var viewList =  $('#viewList_'+id,$this);
	  var editDialog = $('#editDialog_'+id,$this);
	  var editForm = $('#editForm_'+id,editDialog);
	  //加载列表
	  $(viewList).datagrid({
		singleSelect:true,
		method:"POST",
		nowrap:true,
		striped: true,
		pagination:true,
		rownumbers:true,
		selectOnCheck:false,
		checkOnSelect:false,
		fit:true,
		toolbar:'#tb_'+id,
		columns:[[
		      {field:'ck',checkbox:true},
	          {field:'status',title:'状态',width:50,sortable:true,align:'center',
					formatter: function(value,row,index){
						if (value==0){
							return '<img src="style/v1/icons/warn.png"/>';
						} else if (value==1){
							return '<img src="style/v1/icons/info.png"/>';
						}
				 }},
			{field:'prizeName',title:'奖项',width:150,align:'center'}
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
	  });
	  //修改
	  $('#update_'+id,$this).click(function(){
		  onUpdate();
	  });
	  //删除
	  $('#delete_'+id,$this).click(function(){
		  onMulDelete();
	  });
	  //上移
	  $('#moveUp_'+id,$this).click(function(){
		  onMove(-1);
	  });
	  //下移
	  $('#moveDown_'+id,$this).click(function(){
		  onMove(1);
	  });
	  //状态
	  $('#state_'+id).menu({  
		    onClick:function(item){
		    	if(item.name=='0'){
		    		onMulUpdateState(0);
		    	}else{
		    		onMulUpdateState(1);
		    	}
		    }
		    	
	  });

	  //查询
	  	var search = function(flag){
			var prizeName=$('#nameSearch',queryContent).val();
			var status = $('#statusSearch',queryContent).combobox('getValue');
			//取得列表信息
			var content = {prizeName:prizeName,status:status};
			$(viewList).datagrid({
				url:"dict/queryPrize.do",
				queryParams:content
			});
			checkBtnRight();
		};
	//查询
	$('#search',$this).click(function(){
		search(true);
	});
	
	//编辑框 
	$(editDialog).dialog({  
	    title: '编辑奖项',  
	    width:400,
	    height:200,
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
		$('#status',editForm).combobox('setValue',1);
		$(editDialog).dialog('open');
	};
	//保存前的赋值操作
	var setValue = function(){
		var prizeName = $.trim($('#prizeName',editForm).val());
		if(''==prizeName){
			$.messager.alert('提示','请填写奖项','warning');
			return false;
		}
		//状态
		var status = $('#status',editForm).combobox('getValue') ;
		if(''==status){
			$.messager.alert('提示','请选择状态','warning');
			return false;
		}
		return true;
	};
	//保存
	var onSave = function(){
		$(editForm).form('submit',{
			url:'dict/savePrize.do',
			onSubmit: function(){
				return setValue();
			},
			success: function(data){
				var result = eval('('+data+')');
				if(result.isSuccess){
					var fn = function(){
						search(true);
					};
					$.messager.alert('提示','保存成功','info',fn);
					$(editDialog).dialog('close');
				}else{
					$.messager.alert('提示',result.message,"error");
				}
			}
		});
	};
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
	 };
	//批量删除
	var onMulDelete = function(){
		var rows = $(viewList).datagrid('getChecked');
		if(rows.length==0){
			 $.messager.alert('提示',"请选中要删除的纪录","warning");
			 return;	
		}
		$.messager.confirm("提示！","确定要删除选中的记录?",function(t){ 
			if(!t) return;
			if(rows.length==0){
				 $.messager.alert('提示',"请选中要删除的纪录","warning");
				 return;	
			}
			var idArray = new Array();
			for(var i=0;i<rows.length;i++){
				idArray.push(rows[i].prizeId);
			}
			var ids = idArray.join(CSIT.join);
			var url = "dict/mulDeletePrize.do";
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
	};
	//修改多个状态
	var onMulUpdateState = function(status){
		var rows =  $(viewList).datagrid('getChecked');
		var msg = '';
		if(status){
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
			idArray.push(rows[i].prizeId);
		}
		$.messager.confirm("提示","确定要"+msg+"记录?",function(t){ 
			if(t){
				var url = 'dict/mulUpdateStatePrize.do';
				var content ={ids:idArray.join(CSIT.join),status:status};
				asyncCallService(url,content,function(result){
					if(result.isSuccess){	
						var fn = function(){
							search(true);
						};
						$.messager.alert('提示',msg+'成功','info',fn);
					}else{
						$.messager.alert('提示',result.message,"error");
					}
				});
			}
		});
	};
	
	//移动
	var onMove = function(direction){
		if(selectRow == null){
			$.messager.alert('提示',"请选中一条记录","warning");
			return;
		}
		var rows  = $(viewList).datagrid('getRows');
		if(direction==-1){
			if(selectIndex==0){
				$.messager.alert('提示',"已经是第一条记录了","warning");
				return;
			}
		}else if(direction==1){//下移
			if(selectIndex==rows.length-1){
				$.messager.alert('提示',"已经是最后一条记录了","warning");
				return;
			}
		}
		var updateRow = rows[selectIndex+direction];
		
		var prizeId = selectRow.prizeId;
		var prizeName = selectRow.prizeName;
		var status = selectRow.status;
		//后台更新排序
		var url = "dict/updateArrayPrize.do";
		var content = {prizeId:prizeId,updatePrizeId:updateRow.prizeId};
		var result = syncCallService(url,content);
		if(result.isSuccess){
			$(viewList).datagrid('updateRow',{
				index: selectIndex,
				row: updateRow
			});
			$(viewList).datagrid('updateRow',{
				index: selectIndex+direction,
				row: {
					prizeId:prizeId,
					prizeName:prizeName,
					status:status
				}
			});
			$(viewList).datagrid('unselectAll');
			$(viewList).datagrid('selectRow',selectIndex+direction);
			selectIndex = selectIndex+direction;
			selectRow = $(viewList).datagrid('getSelected');
		}else{
			$.messager.alert('提示',result.message,"error");
		}
	};
	
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
		var moveUpBtn = $('#moveUp_'+id,$this);
		var moveDownBtn = $('#moveDown_'+id,$this);
		checkArray.push(addBtn);
		checkArray.push(updateBtn);
		checkArray.push(deleteBtn);
		checkArray.push(tbStateBtn);
		checkArray.push(moveUpBtn);
		checkArray.push(moveDownBtn);
		checkRight(checkArray,rights);
	};
	checkBtnRight();
  };
})(jQuery);   