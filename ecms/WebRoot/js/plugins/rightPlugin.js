// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.rightInit = function() {
	  var $this = $(this);
	  var id = $(this).attr('id');
	  var rightId = $(this).attr('rightId');
	  
	  var selectRow = null;
	  var selectIndex = null;
	  
	  var editDialog = $('#editDialog_'+id,$this);
	  var editForm = $('#editForm_'+id,editDialog);
	  var rightTree =  $('#rightTree_'+id,$this);
	  var rightList =  $('#rightList_'+id,$this);
	  var queryContent = $('#queryContent_'+id,$this);
	  
	  //权限树  
	  $(rightTree).tree({
			url: 'system/getRootRight.do',
			onBeforeExpand:function(node,param){
				$(rightTree).tree('options').url = 'system/getTreeNodeRight.do?rightId='+node.id;  
	        },
			onClick:function(node){ 
				$(rightTree).tree('expand',node.target);
				var rightName = $('#rightNameSearch').val();
				var content = {rightId:node.id,rightName:rightName};
				$(rightList).datagrid({
					url:'system/getTreeNodeChildrenRight.do',
					queryParams:content
				});
			}
	  });
	  //列表
	  $(rightList).datagrid({
			fit:true,
			idField:'rightId',
			columns:[[{field:'ck',checkbox:true},
			    {field:'state',title:'状态',width:50,sortable:true,align:"center",
					formatter: function(value,row,index){
						if (value=='false'){
							return '<img src="style/v1/icons/warn.png"/>';
						} else if (value=='true'){
							return '<img src="style/v1/icons/info.png"/>';
						}
				 }},
			    {field:'rightCode',title:'权限编号',width:200,sortable:true,align:"center"},
			    {field:'rightName',title:'权限名称',width:100,sortable:true,align:"center"},
				{field:'rightUrl',title:'权限Url',width:200,sortable:true,align:"center"},
				{field:'kind',title:'类型',width:100,sortable:true,align:"center",formatter: function(value,row,index){
					if (value==1){
						return 'Url权限';
					} else if (value==2){
						return '界面按钮权限';
					}else if (value==3){
						return '数据显示权限';
					}
			  }}
			]],
			rownumbers:true,
			pagination:true,
			selectOnCheck:false,
			checkOnSelect:false,
			singleSelect:true,
			toolbar:[	
						{id:'add_'+id,text:'添加',iconCls:'icon-add',handler:function(){onAdd()}},'-',
						{id:'update_'+id,text:'修改',iconCls:'icon-edit',handler:function(){onUpdate()}},'-',
						{id:'delete_'+id,text:'删除',iconCls:'icon-remove',handler:function(){onMulDelete()}},'-',
						{id:'moveUp_'+id,text:'上移',iconCls:'icon-up',handler:function(){onMove(-1)}},'-',
						{id:'moveDown_'+id,text:'下移',iconCls:'icon-down',handler:function(){onMove(1)}},'-',
						{id:'open_'+id,text:'启用',iconCls:'icon-info',handler:function(){onMulUpdateState(true);}},'-',
						{id:'close_'+id,text:'禁用',iconCls:'icon-warn',handler:function(){onMulUpdateState(false);}},'-'
					],
			onDblClickRow:function(rowIndex, rowData){
				onUpdate();
			},
			onClickRow:function(rowIndex, rowData){
				selectRow = rowData;
				selectIndex = rowIndex;
			}
	 });
	//编辑框
	$(editDialog).dialog({  
	    title: '编辑系统权限',  
	    width:400,
	    height:350,
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
		var selectedNote = $(rightTree).tree('getSelected');
		if(selectedNote==null){
			$.messager.alert('提示','请选择父节点','warning');
			return false;
		}
		$(editForm).form('clear');
		$('#kind',editForm).combobox('setValue',1);
		$('#state',editForm).combobox('setValue','true');
		$(editDialog).dialog('open');
	}
	//保存前的赋值操作
	var setValue = function(){
		var rightCode = $.trim($('#rightCode',editForm).val());
		if(rightCode==''){
			$.messager.alert('提示','请填写权限编号','warning');
			return false;
		}
		var rightName = $.trim($('#rightName',editForm).val());
		if(rightName==''){
			$.messager.alert('提示','请填写权限名称','warning');
			return false;
		}
		var kind = $.trim($('#kind',editForm).combobox('getValue'));
		if(kind==''){
			$.messager.alert('提示','请选择权限类型','warning');
			return false;
		}
		var selectedNote = $(rightTree).tree('getSelected');
		if(selectedNote==null){
			var root = $(rightTree).tree('getRoot');
			if(root==null){
				$('#parentID',editForm).val(null);
			}else{
				$('#parentID',editForm).val(root.id);
			}
		}else{
			$('#parentID',editForm).val(selectedNote.id);
		}
		return true;
	}
	//保存
	var onSave = function(){
		 $(editForm).form('submit',{
			url: 'system/saveRight.do',
			onSubmit: function(){
				return setValue();
			},
			success: function(data){
				var result = eval('('+data+')');
				if(result.isSuccess){
					var fn = function(){
						var rightId = $('#rightId',editForm).val();
						//新增
						if(rightId==''){
							var node = $(rightTree).tree('getSelected');
							var rightName = $('#rightName',editForm).val();
							$(rightTree).tree('append',{
								parent: (node?node.target:null),
								data:[{
									id:result.data.rightId,
									text:rightName
								}]
							});
							search();
						}else{
							var row = $(editForm).serializeObject();
							$(rightList).datagrid('updateRow',{index:selectIndex,row:row});
							
							var rightId=$("#rightId",editForm).val();
							var rightName = $('#rightName',editForm).val();
							var updateNote=$(rightTree).tree('find',rightId);
							updateNote.text=rightName;
							$(rightTree).tree('update', updateNote);
						}
					}
					$.messager.alert('提示','保存成功','info',fn);
					$(editDialog).dialog('close');
				}else{
					$.messager.alert('提示',result.message,'error');
				}
				
			}
		 });
	}
	//修改
	var onUpdate = function(){
		var options = $('#update_'+id,$this).linkbutton('options');
		if(!options.disabled){
			if(selectRow==null){
				$.messager.alert("提示","请选择数据行","warning");
				return;
			}
			$(editForm).form('load',selectRow);
			$(editDialog).dialog('open');
		}
	 }
	//删除
	var onMulDelete = function(){
		var rows = $(rightList).datagrid('getChecked');
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
				idArray.push(rows[i].rightId);
			}
			var ids = idArray.join(CSIT.join);
			var url = "system/mulDeleteRight.do";
			var content = {ids:ids};
			$.post(url,content,
				function(result){
					if(result.isSuccess){
						var rows = $(rightList).datagrid('getChecked');
						for(var i=0;i<rows.length>0;i++){
							$(rightTree).tree('remove',$(rightTree).tree('find',rows[i].rightId).target);
						}
						search();
						$(rightList).datagrid('unselectAll');
					}else{
						$.messager.alert('提示',result.message,"error");
					}
				}, "json");
		});
	}
	//查询
	$('#search',$this).click(function(){
		search();
	})
	//分页操作
	var search = function(){
		var rightCodeSearch = $('#rightCodeSearch',queryContent).val();
		var rightNameSearch = $('#rightNameSearch',queryContent).val();
		var rightId = '';
		var selectedNote = $(rightTree).tree('getSelected');
		if(selectedNote==null){
			var root = $(rightTree).tree('getRoot');
			if(root!=null){
				rightId = root.id;
			}
		}else{
			rightId = selectedNote.id;
		}
		var content = {rightId:rightId,rightCode:rightCodeSearch,rightName:rightNameSearch};
		
		$(rightList).datagrid({
			url:'system/getTreeNodeChildrenRight.do',
			queryParams:content
		});
		selectRow = null;
	}
	//移动
	var onMove = function(direction){
		if(selectRow==null){
			$.messager.alert('提示',"请选中要移动的记录","warming");
			return;
		}
		var rows  = $(rightList).datagrid('getRows');
		if(direction==-1){
			if(selectIndex==0){
				$.messager.alert('提示',"已经是第一条记录了","warming");
				return;
			}
		}else if(direction==1){//下移
			var rows  = $(rightList).datagrid('getRows');
			if(selectIndex==rows.length-1){
				$.messager.alert('提示',"已经是最后一条记录了","warming");
				return;
			}
		}
		var updateRow = rows[selectIndex+direction];
		var rightCode = selectRow.rightCode;
		var rightName = selectRow.rightName;
		var rightUrl = selectRow.rightUrl;
		var rightId = selectRow.rightId;
		var kind = selectRow.kind;
		var state = selectRow.state;
		var note = selectRow.note;
		var array = selectRow.array;
		//后台更新排序
		var url = "system/updateArrayRight.do";
		var content = {rightId:rightId,updateRightId:updateRow.rightId};
		var result = syncCallService(url,content);
		if(result.isSuccess){
			//更新树节点
			var node = $(rightTree).tree('find',rows[selectIndex].rightId);
			var updateNode = $(rightTree).tree('find',rows[selectIndex+direction].rightId);
			$(rightTree).tree('update', {
				target: node.target,
				text: updateNode.text,
				id: updateNode.id
			});
			
			$(rightTree).tree('update', {
				target: updateNode.target,
				text: node.text,
				id: node.id
			});
			
			$(rightList).datagrid('updateRow',{
				index: selectIndex,
				row: updateRow
			});
			$(rightList).datagrid('updateRow',{
				index: selectIndex+direction,
				row: {
					rightId:rightId,
					rightCode:rightCode,
					rightName:rightName,
					kind:kind,
					state:state,
					note:note,
					rightUrl:rightUrl,
					array:array
				}
			});
			$(rightList).datagrid('unselectAll');
			$(rightList).datagrid('selectRow',selectIndex+direction);
			selectIndex = selectIndex+direction;
			selectRow = $(rightList).datagrid('getSelected');
		}else{
			$.messager.alert('提示',result.message,"error");
		}
	}
	
	//修改多个审核状态
	var onMulUpdateState = function(state){
		var rows =  $(rightList).datagrid('getChecked');
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
			idArray.push(rows[i].rightId);
		}
		$.messager.confirm("提示","确定要"+msg+"记录?",function(t){ 
			if(t){
				var url = 'system/mulUpdateStateRight.do';
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
  }
})(jQuery);   