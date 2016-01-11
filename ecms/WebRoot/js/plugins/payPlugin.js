// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.payInit = function() {
	  var $this = $(this);
	  var id = $(this).attr('id');
	  var height = $(document.body).height();
	  var rightId = $(this).attr('rightId');
	  var selectRow = null;
	  var selectIndex = null;
	
	  var editDialog = $('#editDialog_'+id,$this);
	  var editForm = $('#editForm_'+id,editDialog);
	  var updateDialog = $('#updateDialog_'+id,$this);
	  var updateForm = $('#updateForm_'+id,updateDialog);
	  var viewList =  $('#viewList_'+id,$this);
	  var queryContent = $('#queryContent_'+id,$this);
	  $('#payTypeSearch',queryContent).combobox({
		  width:150,
		  data:ECMS.getPayTypeList(),
		  valueField:'payTypeId',
		  textField:'payTypeName'
	    });
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
			{field:'competitionName',title:'赛事',width:200,align:"center"},
			{field:'studentName',title:'缴费学生',width:100,align:"center"},
			{field:'payTypeName',title:'缴费类型',width:100,align:"center"},
			{field:'payDate',title:'缴费日期',width:150,align:"center"},
			{field:'fee',title:'缴费金额',width:100,align:"center"},
			{field:'teacherName',title:'经办人',width:100,align:"center"}
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
		title:'编辑缴费',
	    width:750,
	    height:height-10,
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
	 //------------------考生信息-------------------
	var stuList = $('#stuList',editDialog);
	var selectRowStu = null;
	var selectIndexStu = null;
	var stuDialog = $('#stuDialog_'+id,$this);
	var unCheckList = $('#unCheckList',stuDialog);
	var queryContentStu = $('#queryContentStu',stuDialog);
	//加载已选考生列表
	$(stuList).datagrid({
		title:'考生信息',
		singleSelect:false,
		method:"POST",
		nowrap:true,
		striped: true,
		collapsible:true,
		pagination:false,
		rownumbers:true,
		selectOnCheck:true,
		checkOnSelect:true,
		fit:true,
		toolbar:[	
					{id:'addStu_'+id,text:'添加',iconCls:'icon-add',handler:function(){onAddStu()}},'-',
					{id:'deleteStu_'+id,text:'删除',iconCls:'icon-remove',handler:function(){onDeleteStu()}}
				],
		columns:[[
			  {field:'ck',checkbox:true},
			  {field:'studentId',title:'学号',width:150,align:"center"},
		      {field:'studentName',title:'姓名',width:100,align:"center"}
		]],
		onClickRow:function(rowIndex, rowData){
			selectRowUnCheck = rowData;
			selectIndexUnCheck = rowIndex;
		},
		onLoadSuccess:function(){
			selectRowUnCheck = null;
	 		selectIndexUnCheck = null;
		}
	  });
	//加载未选考生列表
	  $(unCheckList).datagrid({
		singleSelect:false,
		method:"POST",
		nowrap:true,
		striped: true,
		collapsible:true,
		pagination:true,
		rownumbers:true,
		selectOnCheck:true,
		checkOnSelect:true,
		fit:true,
		columns:[[
		        {field:'ck',checkbox:true},
				{field:'studentId',title:'学号',width:150,align:"center"},
				{field:'studentName',title:'姓名',width:100,align:"center"},
				{field:'groupName',title:'参赛组别',width:100,align:"center"}
		]]
	  });
	$(stuDialog).dialog({  
	    title: '选择缴费考生',  
	    width:700,
	    height:height-20,
	    closed: true,  
	    cache: false,  
	    modal: true,
	    closable:false,
	    toolbar:[{
			text:'选择',
			iconCls:'icon-ok',
			handler:function(){
				onSelectStu();
			}
		},'-',{
			text:'退出',
			iconCls:'icon-cancel',
			handler:function(){
				$(stuDialog).dialog('close');
			}
		}]
	});
	var onAddStu = function(){
		  var competitionId = $('#competition',editForm).combobox('getValue');
		  if(''==competitionId){
			  $.messager.alert('提示','请选择赛事','warning');
			  return false;
		  }
		  $(unCheckList).datagrid('loadData', { total: 0, rows: [] });
		  $(stuDialog).dialog('open');
	  }
	var searchStu = function(){
		   var rows=$(stuList).datagrid('getRows');
		   var idArray = new Array();
		   if(rows.length>0){
			  for(var i=0;i<rows.length;i++){
				   idArray.push(rows[i].studentId);
			   }
		   }
		   
		   var stuIds = idArray.join(CSIT.join);
		   var studentCode = $('#codeSearch',queryContentStu).val();
		   var competitionId = $('#competition',editForm).combobox('getValue');
			//取得列表信息
		   var content = {studentCode:studentCode,competitionId:competitionId,stuIds:stuIds};
		   $(unCheckList).datagrid({
				url:'finance/queryByPayStudentCompetitionGroup.do',
				queryParams:content
		   });
		};
	$('#searchStu',stuDialog).click(function(){
		searchStu();
	});
	var onSelectStu = function(){
		var rows=$(unCheckList).datagrid('getChecked');
		if(rows.length==0){
			 $.messager.alert('提示',"请选择缴费考生","warming");
			 return;	
		}
		for(var i=0;i<rows.length;i++){
			$(stuList).datagrid('appendRow',{
				studentId:rows[i].studentId,
				studentName:rows[i].studentName
			});
		}
		$(stuDialog).dialog('close');
	}
	var init = function(){
		$('#competition',editForm).combobox({
		  width:250,
		  data:ECMS.getCompetitionList(),
		  valueField:'competitionId',
		  textField:'competitionName'
	    });
		$('#payType',editForm).combobox({
		  width:250,
		  data:ECMS.getPayTypeList(),
		  valueField:'payTypeId',
		  textField:'payTypeName'
	    });
	}
	//添加
	var onAdd = function(){
		$(editForm).form('clear');
		$(stuList).datagrid('loadData', {rows: [] });
		$('#payDate',editForm).val(nowDate33());
		init();
		var defaultInformationId = $('#competitionSearchMain').combobox('getValue');
		$('#competition',editForm).combobox('setValue',defaultInformationId);
		$('#teacherName',editForm).val(ECMS.getTeacherName());
		$(editDialog).dialog('open');
	}
	//保存前的赋值操作
	var setValue = function(){
		var payDate = $.trim($('#payDate',editForm).val());
		if(''==payDate){
			$.messager.alert('提示','请填写缴费日期','warning');
			return false;
		}
		var competition = $.trim($('#competition',editForm).combobox('getValue'));
		if(''==competition){
			$.messager.alert('提示','请选择赛事','warning');
			return false;
		}
		var payType = $.trim($('#payType',editForm).combobox('getValue'));
		if(''==payType){
			$.messager.alert('提示','请选择缴费类型','warning');
			return false;
		}
		var fee = $.trim($('#fee',editForm).val());
		if(''==fee){
			$.messager.alert('提示','请填写费用','warning');
			return false;
		}
		var rows=$(stuList).datagrid('getRows');
		if(rows.length==0){
			 $.messager.alert('提示',"请选择缴费考生","warming");
			 return false;	
		}
		var idArray = new Array();
	    for(var i=0;i<rows.length;i++){
		   idArray.push(rows[i].studentId);
	    }
		$('#stuIds',editForm).val(idArray.join(CSIT.join));  
		return true;
	}
	//保存
	var onSave = function(){
		$(editForm).form('submit',{
			url:'finance/savePay.do',
			onSubmit: function(){
				return setValue();
			},
			success: function(data){
				var result = eval('('+data+')');
				if(result.isSuccess){
					var fn = function(){
						search();
					};
					$.messager.alert('提示','保存成功','info',fn);
					$(editDialog).dialog('close');
				}else{
					$.messager.alert('提示',result.message,"error");
				}
			}
		});
	}
	//编辑框
	$(updateDialog).dialog({ 
		title:'编辑缴费',
	    width:430,
	    height:400,
	    closed: true,  
	    cache: false,  
	    modal: true,
	    closable:false,
	    toolbar:[{
			text:'保存',
			iconCls:'icon-save',
			handler:function(){
				onSaveUpdate();
			}
		},'-',{
			text:'退出',
			iconCls:'icon-cancel',
			handler:function(){
				$(updateDialog).dialog('close');
			}
		}]
	});  
	//修改
	var onUpdate = function(){
		if(!$('#update_'+id,$this).is(":hidden")){
			if(selectRow==null){
				$.messager.alert("提示","请选择数据行","warning");
				return;
			}
			$(updateForm).form('clear');
			$('#competition',updateForm).combobox({
			  width:250,
			  data:ECMS.getCompetitionList(),
			  valueField:'competitionId',
			  textField:'competitionName'
		    });
			$('#payType',updateForm).combobox({
			  width:250,
			  data:ECMS.getPayTypeList(),
			  valueField:'payTypeId',
			  textField:'payTypeName'
		    });
			$(updateForm).form('load',selectRow);
			$('#competition',updateForm).combobox('setValue',selectRow.competitionId);
			$('#payType',updateForm).combobox('setValue',selectRow.payTypeId);
			$(updateDialog).dialog('open');
		}
	 }
	//修改前的赋值操作
	var setValueUpdate = function(){
		var payDate = $.trim($('#payDate',updateForm).val());
		if(''==payDate){
			$.messager.alert('提示','请填写缴费日期','warning');
			return false;
		}
		var payType = $.trim($('#payType',updateForm).combobox('getValue'));
		if(''==payType){
			$.messager.alert('提示','请选择缴费类型','warning');
			return false;
		}
		var fee = $.trim($('#fee',updateForm).val());
		if(''==fee){
			$.messager.alert('提示','请填写费用','warning');
			return false;
		}
		return true;
	}
	//修改
	var onSaveUpdate = function(){
		$(updateForm).form('submit',{
			url:'finance/savePay.do',
			onSubmit: function(){
				return setValueUpdate();
			},
			success: function(data){
				var result = eval('('+data+')');
				if(result.isSuccess){
					var fn = function(){
						search();
					};
					$.messager.alert('提示','保存成功','info',fn);
					$(updateDialog).dialog('close');
				}else{
					$.messager.alert('提示',result.message,"error");
				}
			}
		});
	}
	//查询
	var search = function(){
		var payTypeId = $('#payTypeSearch',queryContent).combobox('getValue');
		var defaultInformationId = $('#competitionSearchMain').combobox('getValue');
		var content = {'payType.payTypeId':payTypeId,'competition.competitionId':defaultInformationId};
		
		$(viewList).datagrid({
			url:"finance/queryPay.do",
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
				idArray.push(rows[i].payId);
			}
			var ids = idArray.join(CSIT.join);
			var url = "finance/mulDeletePay.do";
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
			idArray.push(rows[i].payId);
		}
		$.messager.confirm("提示","确定要"+msg+"记录?",function(t){ 
			if(t){
				var url = 'finance/mulUpdateStatusPay.do';
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