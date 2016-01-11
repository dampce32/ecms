// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.teacherInit = function() {
	  var $this = $(this);
	  var id =   $(this).attr('id');
	  var rightId = $(this).attr('rightId');
	  var viewList = $('#viewList_'+id,$this);
	  var editDialog = $('#editDialog_'+id,$this);
	  var editForm = $('#editForm_'+id,editDialog);
	  var queryContent = $('#queryContent_'+id,$this);
	  
	  var selectRow = null;
	//加载列表
	$(viewList).datagrid({
			fit:true,
			singleSelect:true,
			selectOnCheck:false,
			checkOnSelect:false,
			method:"POST",
			nowrap:true,
			striped: true,
			collapsible:true,
			rownumbers:true,
			pagination:true,
			remoteSort:false,
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
			    {field:'teacherCode',title:'教师代码',width:100,align:"center"},
				{field:'teacherName',title:'教师名称',width:100,align:"center"},
			    {field:'note',title:'备注',width:100,align:"center"}
			]],
			onDblClickRow:function(rowIndex, rowData){
				onUpdate();
			},
			onClickRow:function(rowIndex, rowData){
				selectRow = rowData;
				selectIndex = rowIndex;
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
	 //教师角色
	  $('#teacherRole_'+id,$this).click(function(){
		  onTeacherRole();
	  })
	  //教师权限
	  $('#teacherRight_'+id,$this).click(function(){
		  onTeacherRight();
	  })
	  //重置密码
	  $('#resetPwd_'+id,$this).click(function(){
		  onMulResetPwd();
	  })
	  //编辑框
	  $(editDialog).dialog({  
	    title: '编辑教师信息',  
	    width:400,
	    height:340,
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
	//添加信息
	var onAdd = function(){
		$(editForm).form('clear');
		$('#state',editForm).combobox('setValue','true');
		$(editDialog).dialog('open');
	}
	//修改
	var onUpdate = function(){
		if(selectRow==null){
			$.messager.alert("提示","请选择数据行","warning");
			return;
		}
		$(editForm).form('clear');
		$(editForm).form('load',selectRow);
		$(editDialog).dialog('open');
	 }
	//删除
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
				idArray.push(rows[i].teacherId);
			}
			var ids = idArray.join(CSIT.join);
			var url = "system/mulDeleteTeacher.do";
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
			idArray.push(rows[i].teacherId);
		}
		$.messager.confirm("提示","确定要"+msg+"记录?",function(t){ 
			if(t){
				var url = 'system/mulUpdateStateTeacher.do';
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
	//提交前验证
	var setValue = function(){
		
		var teacherCode = $.trim($('#teacherCode',editForm).val());
		if(''==teacherCode){
			$.messager.alert('提示','请填写教师代码','warning');
			return false;
		}
		var teacherName = $.trim($('#teacherName',editForm).val());
		if(''==teacherName){
			$.messager.alert('提示','请填写教师名称','warning');
			return false;
		}
		return true;
	}
	//保存
	var onSave = function(){
		$(editForm).form('submit',{
			url: 'system/saveTeacher.do',
			onSubmit: function(){
				return setValue();
			},
			success: function(data){
				var result = eval('(' + data + ')'); 
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
	//查询
	var search = function(){
		var teacherName = $('#teacherNameSearch',queryContent).val();
		var teacherCode = $('#teacherCodeSearch',queryContent).val();
		var content = {teacherCode:teacherCode,teacherName:teacherName};
		$(viewList).datagrid({
			url:"system/queryTeacher.do",
			queryParams:content
		});
	}
	//查询
	$('#search',$this).click(function(){
		search();
	})
	//--------------教师角色---------------
	var teacherRoleList = $('#teacherRoleList_'+id,$this);
	var teacherRoleDialog = $('#teacherRoleDialog_'+id,$this);
	var oldIdArray = new Array();
	var idArray = new Array();
	var deleteIdList = new Array();
	var addIdList = new Array();
	//编辑框
	$(teacherRoleDialog).dialog({  
	    title: '编辑教师角色信息',  
	    width:400,
	    height:500,
	    closed: true,  
	    cache: false,  
	    modal: true,
	    closable:false,
	    toolbar:[{
			text:'保存',
			iconCls:'icon-save',
			handler:function(){
				onSaveTeacherRole();
			}
		},'-',{
			text:'退出',
			iconCls:'icon-cancel',
			handler:function(){
				$(teacherRoleDialog).dialog('close');
			}
		}]
	});
	//教师角色
	var onTeacherRole = function(){
		if(selectRow==null){
			$.messager.alert("提示","请选择数据行","warning");
			return;
		}
		 //加载列表
		$(teacherRoleList).datagrid({
			url:"system/queryRoleTeacherRole.do?teacher.teacherId="+selectRow.teacherId,
			fit:true,
			method:"POST",
			nowrap:true,
			striped: true,
			collapsible:true,
			rownumbers:true,
			columns:[[
			    {field:'ck',checkbox:true},
				{field:'roleCode',title:'角色编号',width:100,align:"center"},
			    {field:'roleName',title:'角色名',width:100,align:"center"}
			]],
			onLoadSuccess:function(data){
				var rows = $(teacherRoleList).datagrid('getRows');
				oldIdArray = new Array();
				for(var i=0;i<rows.length;i++){
					if(rows[i].checked){
						oldIdArray.push(rows[i].roleId);
						$(teacherRoleList).datagrid('selectRow',i);
					}
				}
			}
		  });
		$(teacherRoleDialog).dialog('open');
	}
	//保存教师角色前验证
	var setValueTeacherRole = function(){
		deleteIdList = new Array();
		addIdList = new Array();
		idArray = new Array();
		var rows = $(teacherRoleList).datagrid('getSelections');
		for(var i =0;i<rows.length;i++){
			idArray.push(rows[i].roleId);
		}
		
		for (var i =0;i<oldIdArray.length;i++) {
			var oldId = oldIdArray[i];
			var isDel = true;
			for (var j =0;j<idArray.length;j++) {
				var id = idArray[j];
				if(oldId==id){
					isDel =false;
					break;
				}
			}
			if(isDel){
				deleteIdList.push(oldId);
			}
		}
		for (var i =0;i<idArray.length;i++) {
			var id = idArray[i];
			var isAdd = true;
			for (var j =0;j<oldIdArray.length;j++) {
				var oldId = idArray[j];
				if(oldId==id){
					isAdd =false;
					break;
				}
			}
			if(isAdd){
				addIdList.push(id);
			}
		}
		if(addIdList.length==0&&deleteIdList.length==0){
			$.messager.alert('提示','角色没有修改');
			return false;
		}
		return true;
	}
	//保存
	var onSaveTeacherRole = function(){
		if(setValueTeacherRole()){
			var teacherId = selectRow.teacherId;
			var content = {'teacher.teacherId':teacherId,ids: idArray.join(CSIT.join),oldIds:oldIdArray.join(CSIT.join)};
			var url = "system/updateRoleTeacherRole.do";
			$.post(url,content,
				function(result){
					if(result.isSuccess){
						oldIdArray = idArray;
						$.messager.alert('提示','保存成功');
					}else{
						$.messager.alert('提示',result.message,"warning");
					}
			},'json')
		}
	}
	
	//----------------------教师权限---------------------
	var teacherRightDialog = $('#teacherRightDialog_'+id,$this);
	var teacherRightTree = $('#teacherRightTree_'+id,$this);
	//编辑框
	$(teacherRightDialog).dialog({  
	    title: '教师权限',  
	    width:400,
	    height:500,
	    closed: true,  
	    cache: false,  
	    modal: true,
	    closable:false,
	    toolbar:[{
			text:'取消',
			iconCls:'icon-cancel',
			handler:function(){
				$(teacherRightDialog).dialog('close');
			}
		}]
	});
	//教师权限
	var onTeacherRight = function(){
		if(selectRow==null){
			$.messager.alert("提示","请选择数据行","warning");
			return;
		}
		$(teacherRightTree).tree({
			url: 'system/queryRootRightTeacher.do?teacherId='+selectRow.teacherId,
			checkbox:true,
			onBeforeExpand:function(node,param){
				$(teacherRightTree).tree('options').url = 'system/queryChildrenRightTeacher.do?teacherId='+selectRow.teacherId+'&rightId='+node.id;  
	        }, 
			onLoadSuccess:function(node, data){ 
				 $(this).find('span.tree-checkbox').unbind().click(function(){
			            return false;
		         });
			}
		});
		$(teacherRightDialog).dialog('open');
	}
	//重置密码
	var onMulResetPwd = function(){
		var rows = $(viewList).datagrid('getChecked');
		if(rows.length==0){
			 $.messager.alert('提示',"请选中要重置密码的纪录","warming");
			 return;	
		}
		$.messager.confirm("提示！","确定要重置密码勾选的记录?",function(t){ 
			if(!t) return;
			if(rows.length==0){
				 $.messager.alert('提示',"请选中要重置密码的纪录","warming");
				 return;	
			}
			var idArray = new Array();
			for(var i=0;i<rows.length;i++){
				idArray.push(rows[i].teacherId);
			}
			var ids = idArray.join(CSIT.join);
			var url = "system/mulResetPwdTeacher.do";
			var content = {ids:ids};
			$.post(url,content,
				function(result){
					if(result.isSuccess){
						$.messager.alert('提示','重置密码成功',"info");
					}else{
						$.messager.alert('提示',result.message,"error");
					}
				}, "json");
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
		var teacherRoleBtn = $('#teacherRole_'+id,$this);
		var teacherRightBtn = $('#teacherRight_'+id,$this);
		var resetPwdBtn = $('#resetPwd_'+id,$this);
		checkArray.push(addBtn);
		checkArray.push(updateBtn);
		checkArray.push(deleteBtn);
		checkArray.push(tbStateBtn);
		checkArray.push(teacherRoleBtn);
		checkArray.push(teacherRightBtn);
		checkArray.push(resetPwdBtn);
		checkRight(checkArray,rights);
	}
	checkBtnRight();
}	
})(jQuery);   