// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.schoolInit = function() {
	  var $this = $(this);
	  var id = $(this).attr('id');
	  var rightId = $(this).attr('rightId');
	  var selectRow = null;
	  var selectIndex = null;
	
	  var editDialog = $('#editDialog_'+id,$this);
	  var editForm = $('#editForm_'+id,editDialog);
	  var viewList =  $('#viewList_'+id,$this);
	  var queryContent = $('#queryContent_'+id,$this);
	  var initCombobox = function(){
		    //省份
			$('#province',editForm).combobox({
				width:250,
				url:'dict/queryComboboxProvince.do',
				valueField:'provinceId',
				textField:'provinceName',
				onChange:function(newValue, oldValue){
					$('#city',editForm).combobox({
						url:'dict/queryComboboxCity.do?province.provinceId='+newValue
					});
					$('#area',editForm).combobox('setValue','');
				}
			});
			//市
			$('#city',editForm).combobox({
				width:250,
				valueField:'cityId',
				textField:'cityName',
				onChange:function(newValue, oldValue){
					$('#area',editForm).combobox({
						url:'dict/queryComboboxArea.do?city.cityId='+newValue
					});
				}
			});
			//区
			$('#area',editForm).combobox({
				width:250,
				valueField:'areaId',
				textField:'areaName'
			});
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
		      {field:'ck',checkbox:true},
	          {field:'status',title:'状态',width:50,sortable:true,align:"center",
					formatter: function(value,row,index){
						if (value==0){
							return '<img src="style/v1/icons/warn.png"/>';
						} else if (value==1){
							return '<img src="style/v1/icons/info.png"/>';
						}
				 }},
			{field:'schoolCode',title:'学校编号',width:100,align:"center"},
			{field:'schoolName',title:'学校名称',width:200,align:"center"},
			{field:'position',title:'所属地区',width:250,align:"center",
				formatter:function(value,row,index){
					if(row.areaId==null){
						return '';
					}
					return row.provinceName+row.cityName+row.areaName;
				}}
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
	  //学校配置
	  $('#configuration_'+id,$this).click(function(){
		  onConfiguration();
	  });
	//编辑框
	$(editDialog).dialog({ 
		title:'编辑学校',
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
	//保存前的赋值操作
	var setValue = function(){
		var schoolCode = $.trim($('#schoolCode',editForm).val());
		if(''==schoolCode){
			$.messager.alert('提示','请填写学校编号','warning');
			return false;
		}
		var schoolName = $.trim($('#schoolName',editForm).val());
		if(''==schoolName){
			$.messager.alert('提示','请填写学校名称','warning');
			return false;
		}
		var province = $.trim($('#province',editForm).combobox('getValue'));
		if(''==province){
			$.messager.alert('提示','请选择省份','warning');
			return false;
		}
		var city = $.trim($('#city',editForm).combobox('getValue'));
		if(''==city){
			$.messager.alert('提示','请选择城市','warning');
			return false;
		}
		var area = $.trim($('#area',editForm).combobox('getValue'));
		if(''==area){
			$.messager.alert('提示','请选择地区','warning');
			return false;
		}
		return true;
	}
	//保存
	var onSave = function(){
		$(editForm).form('submit',{
			url:'dict/saveSchool.do',
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
	//添加
	var onAdd = function(){
		$(editForm).form('clear');
		initCombobox();
		$('#status',editForm).combobox('setValue',1);
		$(editDialog).dialog('open');
	}
	//修改
	var onUpdate = function(){
		if(!$('#update_'+id,$this).is(":hidden")){
			if(selectRow==null){
				$.messager.alert("提示","请选择数据行","warning");
				return;
			}
			initCombobox();
			$(editForm).form('clear');
			$(editForm).form('load',selectRow);
			$('#province',editForm).combobox('setValue',selectRow.provinceId);
			$('#city',editForm).combobox('setValue',selectRow.cityId);
			$('#area',editForm).combobox('setValue',selectRow.areaId);
			$(editDialog).dialog('open');
		}
	 }
	//查询
	var search = function(){
		var schoolName = $('#nameSearch',queryContent).val();
		var status = $('#statusSearch',queryContent).combobox('getValue');
		var content = {schoolName:schoolName,status:status};
		
		$(viewList).datagrid({
			url:"dict/querySchool.do",
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
				idArray.push(rows[i].schoolId);
			}
			var ids = idArray.join(CSIT.join);
			var url = "dict/mulDeleteSchool.do";
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
			idArray.push(rows[i].schoolId);
		}
		$.messager.confirm("提示","确定要"+msg+"记录?",function(t){ 
			if(t){
				var url = 'dict/mulUpdateStatusSchool.do';
				var content ={ids:idArray.join(CSIT.join),status:status};
				asyncCallService(url,content,function(result){
					if(result.isSuccess){
						var fn = function(){
							search();
						}
						$.messager.alert('提示',msg+'成功','info',fn);
					}else{
						$.messager.alert('提示',result.message,'error');
					}
				});
			}
		});
	}
	//====================学校配置==========================================
	var schoolConfigurationDialog = $('#schoolConfigurationDialog_'+id,$this);
	$(schoolConfigurationDialog).dialog({ 
		title:'学校配置',
	    width:800,
	    height:460,
	    closed: true,  
	    cache: false,  
	    modal: true,
	    closable:false,
	    onClose:function(){
			$(gradeList).datagrid('loadData',{rows: [] });
			$(clazzList).datagrid('loadData',{rows: [] });
	    }
	});   
	var onConfiguration = function(){
		if(selectRow==null){
			$.messager.alert("提示","请选择数据行","warning");
			return;
		}
		var content = {'school.schoolId':selectRow.schoolId};
		$(gradeList).datagrid({
			url:"dict/querySG.do",
			queryParams:content
		});
		$(schoolConfigurationDialog).dialog('open');
	}
	//------------------年级-------------------
	var gradeList = $('#gradeList',schoolConfigurationDialog);
	var selectRowGrade = null;
	var selectIndexGrade = null;
	//加载列表
	$(gradeList).datagrid({
		title:'年级',
		singleSelect:true,
		method:"POST",
		nowrap:true,
		striped: true,
		collapsible:true,
		rownumbers:true,
		pagination:false,
		fitColumns:true,
		fit:true,
		toolbar:[	
					{id:'addGroup_'+id,text:'添加年级',iconCls:'icon-add',handler:function(){onAddGrade();}},'-',
					{id:'deleteBig_'+id,text:'删除',iconCls:'icon-remove',handler:function(){onDeleteGrade();}},'-',
					{id:'moveUpGroup_'+id,text:'上移',iconCls:'icon-up',handler:function(){onMoveGrade(-1);}},'-',
					{id:'moveDownGroup_'+id,text:'下移',iconCls:'icon-down',handler:function(){onMoveGrade(1);}},'-',
					{text:'退出',iconCls:'icon-cancel',handler:function(){
						$(schoolConfigurationDialog).dialog('close');
						}
					}
				],
		columns:[[
			  {field:'gradeId',hidden:true},
			  {field:'gradeName',title:'年级名称',width:150,align:"center"}
		]],
		onClickRow:function(rowIndex, rowData){
			selectRowGrade = rowData;
			selectIndexGrade = rowIndex;
		},
		onSelect:function(rowIndex, rowData){
			selectRowGrade = rowData;
			selectIndexGrade = rowIndex;
			var content = {'schoolGrade.schoolGradeId':selectRowGrade.schoolGradeId};
			$(clazzList).datagrid({
				url:"dict/querySGC.do",
				queryParams:content
			});
		},
		onLoadSuccess:function(){
			selectRowGrade = null;
 			selectIndexGrade = null;
		}
	  });
	//------------------------年级列表----------------
	var gradeDialog = $('#gradeDialog_'+id,$this);
	var queryContentGrade = $('#queryContentGrade_'+id,gradeDialog);
	$(gradeDialog).dialog({ 
		title:'年级列表',
	    width:700,
	    height:450,
	    closed: true,  
	    cache: false,  
	    modal: true,
	    closable:false,
	    toolbar:[{
			text:'选择',
			iconCls:'icon-ok',
			handler:function(){
				onSelectGrade();
			}
		},'-',{
			text:'退出',
			iconCls:'icon-cancel',
			handler:function(){
				$(gradeDialog).dialog('close');
			}
		}]
	});
		var gradeViewList = $('#gradeViewList',gradeDialog);
		var selectRowGradeView = null;
		var	selectIndexGradeView = null;
		//参赛组别列表
	  $(gradeViewList).datagrid({
		singleSelect:false,
		method:"POST",
		nowrap:true,
		striped: true,
		collapsible:true,
		pagination:true,
		rownumbers:true,
		fit:true,
		columns:[[
		      {field:'ck',checkbox:true},
			  {field:'gradeName',title:'年级',width:150,align:"center"}
		]],
		onClickRow:function(rowIndex, rowData){
			selectRowGradeView = rowData;
			selectIndexGradeView = rowIndex;
		},
		onLoadSuccess:function(){
			selectRowGradeView = null;
	 		selectIndexGradeView = null;
		}
	  });
	  //查询
	$('#searchGrade',gradeDialog).click(function(){
		var gradeName = $('#gradeNameSearch',queryContentGrade).val();
		var content = {gradeName:gradeName,schoolId:selectRow.schoolId};
		$(gradeViewList).datagrid({
			url:"dict/selectQueryGrade.do",
			queryParams:content
		});
	})
	  var onSelectGrade = function(){
		  var rows=$(gradeViewList).datagrid('getSelections');
		  if(rows.length==0){
			  $.messager.alert("提示","请选择年级","warning");
			  return;
		  }
		  var idArray = new Array();
		  for(var i=0;i<rows.length;i++){
			  idArray.push(rows[i].gradeId);
		  }
		  var gradeIds = idArray.join(CSIT.join);
		  var url = "dict/saveSG.do";
		  var content = {gradeIds:gradeIds,'school.schoolId':selectRow.schoolId};
		  $.post(url,content,
			  function(result){
				  if(result.isSuccess){
					  $.messager.alert('提示','添加年级成功',"info");
					  var content = {'school.schoolId':selectRow.schoolId};
						$(gradeList).datagrid({
							url:"dict/querySG.do",
							queryParams:content
						});
					  $(gradeDialog).dialog('close');
				  }else{
					  $.messager.alert('提示',result.message,"error");
				  }
			  }, "json");
	}
	var onAddGrade = function(){
		$(gradeViewList).datagrid('loadData',{ total: 0, rows: [] });
		$('#gradeNameSearch',gradeDialog).val('');
		$(gradeDialog).dialog('open');
		
	}
	//----------------------删除年级--------------
	var onDeleteGrade = function(){
		if(selectRowGrade==null){
			 $.messager.alert('提示',"请选中要删除的纪录","warming");
			 return;	
		}
		$.messager.confirm("提示！","确定要删除选中的记录?",function(t){ 
			if(!t) return;
			var url = "dict/deleteSG.do";
			var content = {'schoolGradeId':selectRowGrade.schoolGradeId};
			$.post(url,content,
				function(result){
					if(result.isSuccess){
						var content = {'school.schoolId':selectRow.schoolId};
						$(gradeList).datagrid({
							url:"dict/querySG.do",
							queryParams:content
						});
						//清空右边的班级
						$(clazzList).datagrid('loadData',{rows:[]});
					}else{
						$.messager.alert('提示',result.message,"error");
					}
				}, "json");
		});
	}
	//------------------移动------------------
	var onMoveGrade = function(direction){
		var selectRows =  $(gradeList).datagrid('getSelections');
		if(selectRows.length==0){
			$.messager.alert('提示',"请选择行记录","warming");
			return;
		}
		var rows  = $(gradeList).datagrid('getRows');
		if(direction==-1){
			if(selectIndexGrade==0){
				$.messager.alert('提示',"已经是第一条记录了","warming");
				return;
			}
		}else if(direction==1){//下移
			if(selectIndexGrade==rows.length-1){
				$.messager.alert('提示',"已经是最后一条记录了","warming");
				return;
			}
		}
		var updateRow = rows[selectIndexGrade+direction];
		var schoolGradeId = selectRowGrade.schoolGradeId;
		var gradeName = selectRowGrade.gradeName;
		//后台更新排序
		var url = "dict/updateArraySG.do";
		var content = {schoolGradeId:schoolGradeId,updateSchoolGradeId:updateRow.schoolGradeId};
		var result = syncCallService(url,content);
		if(result.isSuccess){
			$(gradeList).datagrid('updateRow',{
				index: selectIndexGrade,
				row: updateRow
			});
			$(gradeList).datagrid('updateRow',{
				index: selectIndexGrade+direction,
				row: {
					schoolGradeId:schoolGradeId,
					gradeName:gradeName
				}
			});
			$(gradeList).datagrid('selectRow',selectIndexGrade+direction);
		}else{
			$.messager.alert('提示',result.message,"error");
		}
	}
	//------------------班级-------------------
	var clazzList = $('#clazzList',schoolConfigurationDialog);
	var selectRowClazz = null;
	var selectIndexClazz = null;
	$(clazzList).datagrid({
		title:'班级',
		singleSelect:true,
		method:"POST",
		nowrap:true,
		striped: true,
		collapsible:true,
		rownumbers:true,
		fitColumns:true,
		fit:true,
		toolbar:[	
					{id:'addClazz_'+id,text:'添加',iconCls:'icon-add',handler:function(){onAddClazz()}},
					{id:'deleteClazz_'+id,text:'删除',iconCls:'icon-remove',handler:function(){onDeleteClazz()}},'-',
					{id:'moveUpClazz_'+id,text:'上移',iconCls:'icon-up',handler:function(){onMoveClazz(-1)}},'-',
					{id:'moveDownClazz_'+id,text:'下移',iconCls:'icon-down',handler:function(){onMoveClazz(1)}},'-'
				],
		columns:[[
			  {field:'clazzId',hidden:true},
			  {field:'clazzName',title:'班级名称',width:150,align:"center"}
		]],
		onClickRow:function(rowIndex, rowData){
			selectRowClazz = rowData;
			selectIndexClazz = rowIndex;
		},
		onLoadSuccess:function(){
			selectRowClazz = null;
	 		selectIndexClazz = null;
		}
	  });
	var onAddClazz = function(){
		if(selectRowGrade==null){
			$.messager.alert('提示',"请选择年级","warming");
			return;
		}
		$(clazzViewList).datagrid('loadData',{ total: 0, rows: [] });
		$('#nameSearch',clazzDialog).val('');
		$(clazzDialog).dialog('open');
	}
	var onDeleteClazz = function(){
		if(selectRowClazz==null){
			$.messager.alert('提示',"请选择要删除班级","warming");
			return;
		}
		$.messager.confirm("提示！","确定要删除选中的记录?",function(t){ 
			if(!t) return;
			var url = "dict/deleteSGC.do";
			var content = {'schoolGradeClazzId':selectRowClazz.schoolGradeClazzId};
			$.post(url,content,
				function(result){
					if(result.isSuccess){
						var content = {'schoolGrade.schoolGradeId':selectRowGrade.schoolGradeId};
						$(clazzList).datagrid({
							url:"dict/querySGC.do",
							queryParams:content
						});
					}else{
						$.messager.alert('提示',result.message,"error");
					}
				}, "json");
		});
	}
	var clazzDialog = $('#clazzDialog_'+id,$this);
	var queryContentClazz = $('#queryContentClazz_'+id,clazzDialog);
	var clazzViewList = $('#clazzViewList',clazzDialog);
	$(clazzDialog).dialog({ 
		title:'班级列表',
	    width:700,
	    height:450,
	    closed: true,  
	    cache: false,  
	    modal: true,
	    closable:false,
	    pagination:true,
	    toolbar:[{
			text:'选择',
			iconCls:'icon-ok',
			handler:function(){
				onSelectClazz();
			}
		},'-',{
			text:'退出',
			iconCls:'icon-cancel',
			handler:function(){
				$(clazzDialog).dialog('close');
			}
		}]
	});
	$(clazzViewList).datagrid({
		method:"POST",
		nowrap:true,
		striped: true,
		pagination:true,
		rownumbers:true,
		fit:true,
		columns:[[
		      {field:'ck',checkbox:true},
		      {field:'clazzId',hidden:true},
			  {field:'clazzName',title:'班级',width:150,align:'center'}
		]]
	  });
	$('#searchClazz',clazzDialog).click(function(){
		var clazzName = $('#nameSearch',queryContentClazz).val();
		var content = {clazzName:clazzName,schoolGradeId:selectRowGrade.schoolGradeId};
		$(clazzViewList).datagrid({
			url:"dict/selectQueryClazz.do",
			queryParams:content
		});
	})
	var onSelectClazz = function(){
		  var rows=$(clazzViewList).datagrid('getSelections');
		  if(rows.length==0){
			  $.messager.alert("提示","请选择班级","warning");
			  return;
		  }
		  var idArray = new Array();
		  for(var i=0;i<rows.length;i++){
			  idArray.push(rows[i].clazzId);
		  }
		  var clazzIds = idArray.join(CSIT.join);
		  var url = "dict/saveSGC.do";
		  var content = {clazzIds:clazzIds,'schoolGrade.schoolGradeId':selectRowGrade.schoolGradeId};
		  $.post(url,content,
			  function(result){
				  if(result.isSuccess){
					  $.messager.alert('提示','添加班级成功',"info");
					  var content = {'schoolGrade.schoolGradeId':selectRowGrade.schoolGradeId};
						$(clazzList).datagrid({
							url:"dict/querySGC.do",
							queryParams:content
						});
					  $(clazzDialog).dialog('close');
						
				  }else{
					  $.messager.alert('提示',result.message,"error");
				  }
			  }, "json");
	}
	//------------------移动班级------------------
	var onMoveClazz = function(direction){
		var selectRows =  $(clazzList).datagrid('getSelections');
		if(selectRows.length==0){
			$.messager.alert('提示',"请选择行记录","warming");
			return;
		}
		var rows  = $(clazzList).datagrid('getRows');
		if(direction==-1){
			if(selectIndexClazz==0){
				$.messager.alert('提示',"已经是第一条记录了","warming");
				return;
			}
		}else if(direction==1){//下移
			if(selectIndexClazz==rows.length-1){
				$.messager.alert('提示',"已经是最后一条记录了","warming");
				return;
			}
		}
		var updateRow = rows[selectIndexClazz+direction];
		var schoolGradeClazzId = selectRowClazz.schoolGradeClazzId;
		var clazzName = selectRowClazz.clazzName;
		//后台更新排序
		var url = "dict/updateArraySGC.do";
		var content = {schoolGradeClazzId:schoolGradeClazzId,updateSchoolGradeClazzId:updateRow.schoolGradeClazzId};
		var result = syncCallService(url,content);
		if(result.isSuccess){
			$(clazzList).datagrid('updateRow',{
				index: selectIndexClazz,
				row: updateRow
			});
			$(clazzList).datagrid('updateRow',{
				index: selectIndexClazz+direction,
				row: {
					schoolGradeClazzId:schoolGradeClazzId,
					clazzName:clazzName
				}
			});
			$(clazzList).datagrid('unselectAll');
			$(clazzList).datagrid('selectRow',selectIndexClazz+direction);
			selectIndexClazz = selectIndexClazz+direction;
			selectRowClazz = $(clazzList).datagrid('getSelected');
		}else{
			$.messager.alert('提示',result.message,"error");
		}
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
		var configurationBtn = $('#configuration_'+id,$this);
		checkArray.push(addBtn);
		checkArray.push(updateBtn);
		checkArray.push(deleteBtn);
		checkArray.push(tbStateBtn);
		checkArray.push(configurationBtn);
		checkRight(checkArray,rights);
	}
	checkBtnRight();
  }
})(jQuery);   