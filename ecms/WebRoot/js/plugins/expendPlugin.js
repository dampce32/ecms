// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.expendInit = function() {
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
			{field:'competitionName',title:'赛事',width:100,align:"center"},
			{field:'expendName',title:'项目名称',width:100,align:"center"},
			{field:'expendTypeName',title:'支出类型',width:100,align:"center"},
			{field:'expendDate',title:'支出日期',width:150,align:"center"},
			{field:'expendCorporation',title:'收款人单位',width:100,align:"center"},
			{field:'fee',title:'支出金额',width:100,align:"center"},
			{field:'handler',title:'经手人',width:100,align:"center"},
			{field:'note',title:'备注',width:150,align:"center"}
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
	  //状态
	  $('#state_'+id).menu({  
		    onClick:function(item){
		    	if(item.name==0){
		    		onMulUpdateState(0);
		    	}else{
		    		onMulUpdateState(1);
		    	}
		    }
		    	
	  });
	//编辑框
	$(editDialog).dialog({ 
		title:'编辑支出',
	    width:430,
	    height:450,
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
	var init = function(){
		$('#competition',editForm).combobox({
		  width:250,
		  data:ECMS.getCompetitionList(),
		  valueField:'competitionId',
		  textField:'competitionName'
	    });
		$('#expendType',editForm).combobox({
		  width:250,
		  data:ECMS.getExpendTypeList(),
		  valueField:'expendTypeId',
		  textField:'expendTypeName'
	    });
	}
	//添加
	var onAdd = function(){
		$(editForm).form('clear');
		$('#expendDate',editForm).val(nowDate33());
		init();
		var defaultInformationId = $('#competitionSearchMain').combobox('getValue');
		$('#competition',editForm).combobox('setValue',defaultInformationId);
		$(editDialog).dialog('open');
	}
	//保存前的赋值操作
	var setValue = function(){
		var expendDate = $.trim($('#expendDate',editForm).val());
		if(''==expendDate){
			$.messager.alert('提示','请填写支出日期','warning');
			return false;
		}
		var expendName = $.trim($('#expendName',editForm).val());
		if(''==expendName){
			$.messager.alert('提示','请填写项目名称','warning');
			return false;
		}
		var competition = $.trim($('#competition',editForm).combobox('getValue'));
		if(''==competition){
			$.messager.alert('提示','请选择赛事','warning');
			return false;
		}
		var expendType = $.trim($('#expendType',editForm).combobox('getValue'));
		if(''==expendType){
			$.messager.alert('提示','请选择支出类型','warning');
			return false;
		}
		var fee = $.trim($('#fee',editForm).val());
		if(''==fee){
			$.messager.alert('提示','请填写费用','warning');
			return false;
		}
		return true;
	}
	//保存
	var onSave = function(){
		$(editForm).form('submit',{
			url:'finance/saveExpend.do',
			onSubmit: function(){
				return setValue();
			},
			success: function(data){
				var result = eval('('+data+')');
				if(result.isSuccess){
					var fn = function(){
						search();
						ECMS.ExpendTypeList = null;
					};
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
			$(editForm).form('clear');
			init();
			$(editForm).form('load',selectRow);
			$('#competition',editForm).combobox('setValue',selectRow.competitionId);
			$('#expendType',editForm).combobox('setValue',selectRow.expendTypeId);
			$(editDialog).dialog('open');
		}
	 }
	//查询
	var search = function(){
		var expendName = $('#nameSearch',queryContent).val();
		var competitionId = $('#competitionSearchMain').combobox('getValue');
		var content = {expendName:expendName,'competition.competitionId':competitionId};
		
		$(viewList).datagrid({
			url:"finance/queryExpend.do",
			queryParams:content
		});
		checkBtnRight();
	}
	//查询
	$('#search_'+id,$this).click(function(){
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
			var idArray = new Array();
			for(var i=0;i<rows.length;i++){
				idArray.push(rows[i].expendTypeId);
			}
			var ids = idArray.join(CSIT.join);
			var url = "dict/mulDeleteExpendType.do";
			var content = {ids:ids};
			$.post(url,content,
				function(result){
					if(result.isSuccess){
						search();
						ECMS.ExpendTypeList = null;
					}else{
						$.messager.alert('提示',result.message,"error");
					}
				}, "json");
		});
	}
	//修改多个审核状态
	var onMulUpdateState = function(status){
		var rows =  $(viewList).datagrid('getChecked');
		var msg = '';
		if(status==1){
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
			idArray.push(rows[i].expendTypeId);
		}
		$.messager.confirm("提示","确定要"+msg+"记录?",function(t){ 
			if(t){
				var url = 'dict/mulUpdateStatusExpendType.do';
				var content ={ids:idArray.join(CSIT.join),status:status};
				asyncCallService(url,content,function(result){
					if(result.isSuccess){
						var fn = function(){
							search();
							ECMS.ExpendTypeList = null;
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
		checkArray.push(addBtn);
		checkArray.push(updateBtn);
		checkArray.push(deleteBtn);
		checkRight(checkArray,rights);
	}
	checkBtnRight();
  }
})(jQuery);   