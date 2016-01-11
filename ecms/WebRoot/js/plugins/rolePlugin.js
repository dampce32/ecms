// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.roleInit = function() {
	  var $this = $(this);
	  var id = $(this).attr('id');
	  var rightId = $(this).attr('rightId');
	  var selectRow = null;
	
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
			{field:'roleCode',title:'角色编号',width:100,align:"center"},
			{field:'roleName',title:'角色名称',width:100,align:"center"},
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
			pageNumber = 1;
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
	//角色权限
	  $('#roleRight_'+id,$this).click(function(){
		  onRoleRight();
	  })
	//编辑框
	$(editDialog).dialog({  
	    title: '编辑系统角色',  
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
		$('#state',editForm).combobox('setValue','true');
		$(editDialog).dialog('open');
	}
	//保存前的赋值操作
	var setValue = function(){
		var roleCode = $.trim($('#roleCode',editForm).val());
		if(''==roleCode){
			$.messager.alert('提示','请填写角色编号','warning');
			return false;
		}
		var roleName = $.trim($('#roleName',editForm).val());
		if(''==roleName){
			$.messager.alert('提示','请填写角色名','warning');
			return false;
		}
		return true;
	}
	//保存
	var onSave = function(){
		$(editForm).form('submit',{
			url:'system/saveRole.do',
			onSubmit: function(){
				return setValue();
			},
			success: function(data){
				var result = eval('('+data+')');
				if(result.isSuccess){
					var fn = function(){
						search();
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
			if(selectRow.roleName=='超级管理员'){
				$.messager.alert('提示','超级管理员不能修改',"error");
				return false;
			}
			$(editForm).form('clear');
			$(editForm).form('load',selectRow);
			$(editDialog).dialog('open');
		}
	 }
	//查询
	var search = function(){
		var roleCodeSearch = $('#roleCodeSearch',queryContent).val();
		var roleNameSearch = $('#roleNameSearch',queryContent).val();
		var content = {roleName:roleNameSearch,roleCode:roleCodeSearch};
		
		$(viewList).datagrid({
			url:"system/queryRole.do",
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
				idArray.push(rows[i].roleId);
			}
			var ids = idArray.join(CSIT.join);
			var url = "system/mulDeleteRole.do";
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
			if(rows[i].roleName!="超级管理员"){
				idArray.push(rows[i].roleId);
			}
		}
		$.messager.confirm("提示","确定要"+msg+"记录?",function(t){ 
			if(t){
				var url = 'system/mulUpdateStateRole.do';
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
	//-------------------角色权限-----------------------
	var roleRightTree = $('#roleRightTree_'+id,$this);
	var roleRightDialog = $('#roleRightDialog_'+id,$this);
	var loading = false;
	//编辑框
	$(roleRightDialog).dialog({  
	    title: '角色权限',  
	    width:400,
	    height:500,
	    cache: false, 
	    closed: true,  
	    modal: true,
	    closable:false,
	    toolbar:[{
			text:'退出',
			iconCls:'icon-cancel',
			handler:function(){
				$(roleRightDialog).dialog('close');
			}
		}]
	}); 
	//角色权限
	var onRoleRight = function(){
		if(selectRow==null){
			$.messager.alert("提示","请选择数据行","warning");
			return;
		}
		if(selectRow.roleName=='超级管理员'){
			$.messager.alert('提示','超级管理员不能修改',"error");
			return false;
		}
		$(roleRightTree).tree({
			url: 'system/getRootRoleRight.do?role.roleId='+selectRow.roleId,
			checkbox:true,
			onBeforeExpand:function(node,param){
				var roleRightId = node.id;
				var rightId = roleRightId.split('_')[1];
				$(roleRightTree).tree('options').url = 'system/getChildrenRoleRight.do?role.roleId='+selectRow.roleId+'&right.rightId='+rightId;
	        },
	        onBeforeLoad:function(node, param){ 
				loading=true; 
			}, 
			onLoadSuccess:function(node, data){ 
			    loading=false; 
			},
			onClick:function(node){ 
				$(roleRightTree).tree('expand',node.target);
			},
            onCheck:function(node,checked){
            	if(!loading){
            		var roleRightId = node.id;
    				var rightId = roleRightId.split('_')[1];
    				var url = 'system/updateStateRoleRight.do?role.roleId='+selectRow.roleId+'&right.rightId='+rightId+'&state='+checked;
            		$.post(url,
					  function(result){
            			if(!result.isSuccess){
							$.messager.alert('提示',result.message,"error");
						}
            		},'json');
            	}
            }
	  });
		$(roleRightDialog).dialog('open');
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
		var roleRightBtn = $('#roleRight_'+id,$this);
		
		checkArray.push(addBtn);
		checkArray.push(updateBtn);
		checkArray.push(deleteBtn);
		checkArray.push(tbStateBtn);
		checkArray.push(roleRightBtn);
		
		checkRight(checkArray,rights);
	}
	checkBtnRight();
  }
})(jQuery);   