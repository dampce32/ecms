// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.competitionInit = function() {
	  var $this = $(this);
	  var id = $(this).attr('id');
	  var rightId = $(this).attr('rightId');
	  var selectRow = null;
	  var height = $(document.body).height();
	
	  var editDialog = $('#editDialog_'+id,$this);
	  var editForm = $('#editForm_'+id,editDialog);
	  var editCopyDialog = $('#editCopyDialog_'+id,$this);
	  var editCopyForm = $('#editCopyForm_'+id,editCopyDialog);
	  var viewList =  $('#viewList_'+id,$this);
	  var queryContent = $('#queryContent_'+id,$this);
	  var copyItem =  $('#copyItem',editCopyForm);
	  var initCombobox = function(){
		  $('#competition',editForm).combobox({
			  width:300,
			  data:ECMS.getCompetitionList(),
			  valueField:'competitionId',
			  textField:'competitionName'
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
			{field:'competitionCode',title:'赛事编号',width:100,align:"center"},
			{field:'competitionName',title:'赛事名称',width:200,align:"center"},
			{field:'beginDate',title:'开始时间',width:150,align:"center"},
			{field:'endDate',title:'结束时间',width:150,align:"center"},
			{field:'parentCompetitionName',title:'上级赛事',width:200,align:"center"},
			{field:'competitionNote',title:'赛事简要说明',width:150,align:"center"},
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
	  //加载列表
	  $(copyItem).datagrid({
		method:"POST",
		nowrap:true,
		striped: true,
		collapsible:true,
		rownumbers:true,
		pagination:false,
		selectOnCheck:true,
		checkOnSelect:true,
		columns:[[
		    {field:'ck',checkbox:true},
		    {field:'copyId',hidden:true},
			{field:'copyName',title:'复制项名称',width:350,align:"center"}
		]]
	  });
	  //添加
	  $('#m_add_'+id).menu({  
		    onClick:function(item){
		    	if(item.name=='addNew'){
		    		onNewAdd();
		    	}else if(item.name=='addCopy'){
		    		onCopyAdd();
		    	}
		    }
		    	
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
	  //赛事配置
	  $('#configuration_'+id,$this).click(function(){
		  onConfiguration();
	  });
	//编辑框
	$(editDialog).dialog({ 
		title:'编辑赛事',
	    width:680,
	    height:540,
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
		}],
		onClose:function(){
			$(editForm).form('clear');
			$("#uploadCompetititionPicture",editForm).html('');
			$('#competititionPic',editForm).attr('src','#');
		}
	});  
	//编辑框
	$(editCopyDialog).dialog({ 
		title:'复制新建赛事',
	    width:680,
	    height:height-5,
	    closed: true,  
	    cache: false,  
	    modal: true,
	    closable:false,
	    toolbar:[{
			text:'保存',
			iconCls:'icon-save',
			handler:function(){
				onCopySave();
			}
		},'-',{
			text:'退出',
			iconCls:'icon-cancel',
			handler:function(){
				$(editCopyDialog).dialog('close');
			}
		}],
		onClose:function(){
			$(editCopyForm).form('clear');
			$("#uploadCopyCompetititionPicture",editCopyForm).html('');
			$('#competititionCopyPic',editCopyForm).attr('src','#');
		}
	});   
	//添加
	var onNewAdd = function(){
		$(editForm).form('clear');
		var uploadPicture;
		var property={
				multiple:false,
			    file_types:"*.jpg;*.gif",
			    btn_add_text:"添加",
			    btn_up_text:"上传",
			    btn_cancel_text:"放弃",
			    btn_clean_text:"清空",
			    op_del_text:"单项删除",
			    op_up_text:"单项上传",
			    op_fail_text:"上传失败",
			    op_ok_text:"上传成功",
			    op_no_text:"取消上传",
			upload_url:"information/mulUploadPhotoCompetitionPhoto.do;jsessionid="+sessionId,
			flash_url :"js/GooUploader/swfupload.swf"
		};
	    uploadPicture=$.createGooUploader($("#uploadCompetititionPicture",editForm),property);
	    uploadPicture.$swfUpload.uploadSuccess=function(file,msg){
	        var id=file.id;
	        uploadPicture.$fileList[id].span.html("100%");
	        var li=uploadPicture.$content.children("#"+id);
	        li.children(".op_no").css("display","none");
	        li.children(".op_ok").css("display","block");
	        $('#picId',editDialog).val(msg);
	        $('#competititionPic',editForm).attr('src',ECMS.competitionPhotoPath+'/t_'+msg+'.png');
	    };
		$('#competitionCode',editForm).attr('disabled',false);
		$('#status',editForm).combobox('setValue',1);
		initCombobox();
		$(editDialog).dialog('open');
	}
	//保存前的赋值操作
	var setValue = function(){
		var competitionCode = $.trim($('#competitionCode',editForm).val());
		if(''==competitionCode){
			$.messager.alert('提示','请填写赛事编号','warning');
			return false;
		}
		var competitionName = $.trim($('#competitionName',editForm).val());
		if(''==competitionName){
			$.messager.alert('提示','请填写赛事名称','warning');
			return false;
		}
		var beginDate = $.trim($('#beginDate',editForm).val());
		if(''==beginDate){
			$.messager.alert('提示','请填写开始时间','warning');
			return false;
		}
		var endDate = $.trim($('#endDate',editForm).val());
		if(''==endDate){
			$.messager.alert('提示','请填写结束时间','warning');
			return false;
		}
		var picId = $.trim($('#picId',editForm).val());
		if(''==picId){
			$.messager.alert('提示','请先上传图片','warning');
			return false;
		}
		return true;
	}
	//保存
	var onSave = function(){
		$(editForm).form('submit',{
			url:'competition/saveCompetition.do',
			onSubmit: function(){
				return setValue();
			},
			success: function(data){
				var result = eval('('+data+')');
				if(result.isSuccess){
					var fn = function(){
						search();
						ECMS.CompetitionList = null;
						$('#competitionSearchMain').combobox({
						  width:300,
						  data:ECMS.getCompetitionList(),
						  valueField:'competitionId',
						  textField:'competitionName'
					  });
					};
					$.messager.alert('提示','保存成功','info',fn);
					$(editDialog).dialog('close');
				}else{
					$.messager.alert('提示',result.message,"error");
				}
			}
		});
	}
	//赋值添加
	var onCopyAdd = function(){
		var selectRow = $(viewList).datagrid('getSelected');
		if(selectRow==null){
			$.messager.alert('提示','请选择要复制的赛事','warning');
			return;
		}
		$(editCopyForm).form('clear');
		var uploadPicture;
		var property={
				multiple:false,
			    file_types:"*.jpg;*.gif",
			    btn_add_text:"添加",
			    btn_up_text:"上传",
			    btn_cancel_text:"放弃",
			    btn_clean_text:"清空",
			    op_del_text:"单项删除",
			    op_up_text:"单项上传",
			    op_fail_text:"上传失败",
			    op_ok_text:"上传成功",
			    op_no_text:"取消上传",
			upload_url:"information/mulUploadPhotoCompetitionPhoto.do;jsessionid="+sessionId,
			flash_url :"js/GooUploader/swfupload.swf"
		};
	    uploadPicture=$.createGooUploader($("#uploadCopyCompetititionPicture",editCopyForm),property);
	    uploadPicture.$swfUpload.uploadSuccess=function(file,msg){
	        var id=file.id;
	        uploadPicture.$fileList[id].span.html("100%");
	        var li=uploadPicture.$content.children("#"+id);
	        li.children(".op_no").css("display","none");
	        li.children(".op_ok").css("display","block");
	        $('#picCopyId',editCopyForm).val(msg);
	        $('#competititionCopyPic',editCopyForm).attr('src',ECMS.competitionPhotoPath+'/t_'+msg+'.png');
	    };
		$('#competition',editCopyForm).combobox({
			  width:300,
			  data:ECMS.getCompetitionList(),
			  valueField:'competitionId',
			  textField:'competitionName'
		  });
		$(editCopyForm).form('load',selectRow);
		$('#competition',editCopyForm).combobox('setValue',selectRow.parentCompetitionId);
		$('#copyCompetitionId',editCopyForm).val(selectRow.competitionId);
		$('#picCopyId',editCopyForm).val(selectRow.pictureId);
		$('#competititionCopyPic',editCopyForm).attr('src',ECMS.competitionPhotoPath+'/t_'+selectRow.pictureId+'.png');
		var obj = {'rows':[{copyId:'1',copyName:'大赛章程'},{copyId:'2',copyName:'主持人风采'},{copyId:'3',copyName:'选手风采'},{copyId:'4',copyName:'赛事配置'}]};   
 		$(copyItem).datagrid('loadData',obj);  
		$(editCopyDialog).dialog('open');
	};
	//保存前的赋值操作
	var setCopyValue = function(){
		var competitionCode = $.trim($('#competitionCode',editCopyForm).val());
		if(''==competitionCode){
			$.messager.alert('提示','请填写赛事编号','warning');
			return false;
		}
		var competitionName = $.trim($('#competitionName',editCopyForm).val());
		if(''==competitionName){
			$.messager.alert('提示','请填写赛事名称','warning');
			return false;
		}
		var beginDate = $.trim($('#beginDate',editCopyForm).val());
		if(''==beginDate){
			$.messager.alert('提示','请填写开始时间','warning');
			return false;
		}
		var endDate = $.trim($('#endDate',editCopyForm).val());
		if(''==endDate){
			$.messager.alert('提示','请填写结束时间','warning');
			return false;
		}
		var picId = $.trim($('#picCopyId',editCopyForm).val());
		if(''==picId){
			$.messager.alert('提示','请先上传图片','warning');
			return false;
		}
		var rows=$(copyItem).datagrid('getChecked');
		var copyItemsArray = new Array();
		for(var i=0;i<rows.length;i++){
			copyItemsArray.push(rows[i].copyId);
		}
		$('#copyItems',editCopyForm).val(copyItemsArray.join(CSIT.join))
		return true;
	}
	//保存
	var onCopySave = function(){
		$(editCopyForm).form('submit',{
			url:'competition/saveCopyCompetition.do',
			onSubmit: function(){
				return setCopyValue();
			},
			success: function(data){
				var result = eval('('+data+')');
				if(result.isSuccess){
					var fn = function(){
						search();
						ECMS.CompetitionList = null;
						$('#competitionSearchMain').combobox({
						  width:300,
						  data:ECMS.getCompetitionList(),
						  valueField:'competitionId',
						  textField:'competitionName'
					  });
					};
					$.messager.alert('提示','保存成功','info',fn);
					$(editCopyDialog).dialog('close');
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
			var uploadPicture;
			var property={
					multiple:false,
				    file_types:"*.jpg;*.gif",
				    btn_add_text:"添加",
				    btn_up_text:"上传",
				    btn_cancel_text:"放弃",
				    btn_clean_text:"清空",
				    op_del_text:"单项删除",
				    op_up_text:"单项上传",
				    op_fail_text:"上传失败",
				    op_ok_text:"上传成功",
				    op_no_text:"取消上传",
				upload_url:"information/mulUploadPhotoCompetitionPhoto.do;jsessionid="+sessionId,
				flash_url :"js/GooUploader/swfupload.swf"
			};
		    uploadPicture=$.createGooUploader($("#uploadCompetititionPicture"),property);
		    uploadPicture.$swfUpload.uploadSuccess=function(file,msg){
		        var id=file.id;
		        uploadPicture.$fileList[id].span.html("100%");
		        var li=uploadPicture.$content.children("#"+id);
		        li.children(".op_no").css("display","none");
		        li.children(".op_ok").css("display","block");
		        $('#picId',editDialog).val(msg);
		        $('#competititionPic',editForm).attr('src',ECMS.competitionPhotoPath+'/t_'+msg+'.png');
		    };
			initCombobox();
			$('#competitionCode',editForm).attr('disabled',true);
			$(editForm).form('clear');
			$(editForm).form('load',selectRow);
			$('#competition',editForm).combobox('setValue',selectRow.parentCompetitionId);
			$('#picId',editForm).val(selectRow.pictureId);
			$('#competititionPic',editForm).attr('src',ECMS.competitionPhotoPath+'/t_'+selectRow.pictureId+'.png');
			$(editDialog).dialog('open');
		}
	 }
	//查询
	var search = function(){
		var competitionCode = $('#codeSearch',queryContent).val();
		var competitionName = $('#nameSearch',queryContent).val();
		var status = $('#statusSearch',queryContent).combobox('getValue');
		var content = {competitionCode:competitionCode,competitionName:competitionName,status:status};
		
		$(viewList).datagrid({
			url:"competition/queryCompetition.do",
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
				idArray.push(rows[i].competitionId);
			}
			var ids = idArray.join(CSIT.join);
			var url = "competition/mulDeleteCompetition.do";
			var content = {ids:ids};
			$.post(url,content,
				function(result){
					if(result.isSuccess){
						search();
						ECMS.CompetitionList = null;
						$('#competitionSearchMain').combobox({
						  width:300,
						  data:ECMS.getCompetitionList(),
						  valueField:'competitionId',
						  textField:'competitionName'
					  });
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
			idArray.push(rows[i].competitionId);
		}
		$.messager.confirm("提示","确定要"+msg+"记录?",function(t){ 
			if(t){
				var url = 'competition/mulUpdateStatusCompetition.do';
				var content ={ids:idArray.join(CSIT.join),status:status};
				asyncCallService(url,content,function(result){
					if(result.isSuccess){
						var fn = function(){
							search();
							ECMS.CompetitionList = null;
							$('#competitionSearchMain').combobox({
							  width:300,
							  data:ECMS.getCompetitionList(),
							  valueField:'competitionId',
							  textField:'competitionName'
						  });
						}
						$.messager.alert('提示',msg+'成功','info',fn);
					}else{
						$.messager.alert('提示',result.message,'error');
					}
				});
			}
		});
	}
	//====================赛事配置==========================================
	var competitionConfigurationDialog = $('#competitionConfigurationDialog_'+id,$this);
	$(competitionConfigurationDialog).dialog({ 
		title:'赛事配置',
	    width:800,
	    height:460,
	    closed: true,  
	    cache: false,  
	    modal: true,
	    closable:false
	});   
	var onConfiguration = function(){
		if(selectRow==null){
			$.messager.alert("提示","请选择数据行","warning");
			return;
		}
		var content = {'competition.competitionId':selectRow.competitionId};
		$(gapList).datagrid({
			url:"competition/queryCompetitionGroup.do",
			queryParams:content
		});
		$(competitionConfigurationDialog).dialog('open');
	}
	//------------------组别&试卷-------------------
	var gapList = $('#gapList',competitionConfigurationDialog);
	var selectRowGAP = null;
	var selectIndexGAP = null;
	//加载列表
	$(gapList).datagrid({
		title:'组别&试卷',
		singleSelect:true,
		method:"POST",
		nowrap:true,
		striped: true,
		collapsible:true,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		fit:true,
		toolbar:[	
					{id:'addGroup_'+id,text:'添加组别',iconCls:'icon-add',handler:function(){onAddGroup();}},'-',
					{id:'addPaper_'+id,text:'配置试卷',iconCls:'icon-add',handler:function(){onAddPaper();}},'-',
					{id:'deleteBig_'+id,text:'删除',iconCls:'icon-remove',handler:function(){onDeleteGroup();}},'-',
					{id:'moveUpGroup_'+id,text:'上移',iconCls:'icon-up',handler:function(){onMoveGroup(-1);}},'-',
					{id:'moveDownGroup_'+id,text:'下移',iconCls:'icon-down',handler:function(){onMoveGroup(1);}},'-',
					{text:'退出',iconCls:'icon-cancel',handler:function(){
						if(selectIndexPrize!=null){
							$(prizeList).datagrid('endEdit', selectIndexPrize);
						}
						$(prizeList).datagrid('loadData',{rows: [] });
						$(gapList).datagrid('loadData',{rows: [] });
						$(competitionConfigurationDialog).dialog('close');
						}
					}
				],
		columns:[[
			  {field:'groupId',hidden:true},
			  {field:'competitionId',hidden:true},
			  {field:'paperId',hidden:true},
			  {field:'groupName',title:'组别名称',width:150,align:"center"},
		      {field:'paperName',title:'试卷名称',width:250,align:"center"}
		]],
		onClickRow:function(rowIndex, rowData){
			selectRowGAP = rowData;
			selectIndexGAP = rowIndex;
		},
		onSelect:function(rowIndex, rowData){
			selectRowGAP = rowData;
			selectIndexGAP = rowIndex;
			if(selectIndexPrize!=null){
				$(prizeList).datagrid('endEdit', selectIndexPrize);
			}
			var content = {'competitionGroup.competitionGroupId':selectRowGAP.competitionGroupId};
			$(prizeList).datagrid({
				url:"competition/queryCompetitionPrize.do",
				queryParams:content
			});
		},
		onLoadSuccess:function(){
			selectRowGAP = null;
 			selectIndexGAP = null;
		}
	  });
	//------------------------组别----------------
	var groupDialog = $('#groupDialog_'+id,$this);
	var queryContentGroup = $('#queryContentGroup_'+id,groupDialog);
	$(groupDialog).dialog({ 
		title:'参赛组别',
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
				onSelectGroup();
			}
		},'-',{
			text:'退出',
			iconCls:'icon-cancel',
			handler:function(){
				if(selectIndexPrize!=null){
					$(prizeList).datagrid('endEdit', selectIndexPrize);
				}
				$(groupDialog).dialog('close');
			}
		}]
	});
		var groupList = $('#groupList',groupDialog);
		var selectRowGroup = null;
		var	selectIndexGroup = null;
		//参赛组别列表
	  $(groupList).datagrid({
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
			{field:'groupId',hidden:true},
			{field:'groupCode',title:'组别编号',width:100,align:"center"},
			{field:'groupName',title:'组别名称',width:200,align:"center"},
			{field:'note',title:'备注',width:100,align:"center"}
		]],
		onClickRow:function(rowIndex, rowData){
			selectRowGroup = rowData;
			selectIndexGroup = rowIndex;
		},
		onLoadSuccess:function(){
			selectRowGroup = null;
	 		selectIndexGroup = null;
		}
	  });
	  //查询
	$('#searchGroup',groupDialog).click(function(){
		var rows =  $(gapList).datagrid('getRows');
		var groupIds = new Array();
		for ( var i = 0; i < rows.length; i++) {
			var row=rows[i];
			groupIds.push(row.groupId);
		}
		var groupCode = $('#groupCodeSearch',queryContentGroup).val();
		var groupName = $('#groupNameSearch',queryContentGroup).val();
		var content = {groupCode:groupCode,groupName:groupName,status:1,groupIds:groupIds.join(CSIT.join)};
		$(groupList).datagrid({
			url:"dict/queryGroup.do",
			queryParams:content
		});
	})
	  var onSelectGroup = function(){
		  var rows=$(groupList).datagrid('getSelections');
		  if(rows.length==0){
			  $.messager.alert("提示","请选择参赛分组","warning");
			  return;
		  }
		  var idArray = new Array();
		  for(var i=0;i<rows.length;i++){
			  idArray.push(rows[i].groupId);
		  }
		  var groupIds = idArray.join(CSIT.join);
		  var url = "competition/saveCompetitionGroup.do";
		  var content = {groupIds:groupIds,'competition.competitionId':selectRow.competitionId};
		  $.post(url,content,
			  function(result){
				  if(result.isSuccess){
					  $.messager.alert('提示','添加参赛分组成功',"info");
					  var content = {'competition.competitionId':selectRow.competitionId};
						$(gapList).datagrid({
							url:"competition/queryCompetitionGroup.do",
							queryParams:content
						});
					  $(groupDialog).dialog('close');
						
				  }else{
					  $.messager.alert('提示',result.message,"error");
				  }
			  }, "json");
	}
	var onAddGroup = function(){
		$(groupDialog).dialog('open');
		$(groupList).datagrid('loadData',{ total: 0, rows: [] });
		$('#groupCodeSearch',groupDialog).val('');
		$('#groupNameSearch',groupDialog).val('');
	}
	//------------------------试卷--------------------
	var paperDialog = $('#paperDialog_'+id,$this);
	var queryContentPaper = $('#queryContentPaper_'+id,paperDialog);
	$(paperDialog).dialog({ 
		title:'试卷',
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
				onSelectPaper();
			}
		},'-',{
			text:'退出',
			iconCls:'icon-cancel',
			handler:function(){
				$(paperDialog).dialog('close');
			}
		}]
	});
		var paperList = $('#paperList',paperDialog);
		var selectRowPaper = null;
		var	selectIndexPaper = null;
		//试卷列表
	  $(paperList).datagrid({
		singleSelect:true,
		method:"POST",
		nowrap:true,
		striped: true,
		collapsible:true,
		pagination:true,
		rownumbers:true,
		fit:true,
		columns:[[
			  {field:'paperId',title:'编号',width:50,align:"center"},
		      {field:'groupName',title:'参赛组别',width:150,align:"center"},
		      {field:'teacherName',title:'出题老师',width:100,align:"center"},
		      {field:'paperType',title:'试卷类型',width:120,align:"center"},
		      {field:'paperName',title:'试卷名称',width:250,align:"center"},
		      {field:'limits',title:'限时（分钟）',width:100,align:"center",  
	                formatter:function(value,row,index){ 
	                	return parseInt(value/60);
	                }},
		      {field:'score',title:'总分',width:50,align:"center"},
		      {field:'publishDate',title:'出卷时间',width:150,align:"center"},
		      {field:'note',title:'备注',width:50,align:"center"}
	          
		]],
		onClickRow:function(rowIndex, rowData){
			selectRowPaper = rowData;
			selectIndexPaper = rowIndex;
		},
		onDblClickRow:function(rowIndex,rowData){
			onSelectPaper();
		},
		onLoadSuccess:function(){
			selectRowPaper = null;
	 		selectIndexPaper = null;
		}
	  });
	  //查询
	$('#searchPaper',paperDialog).click(function(){
		var groupId = $('#groupSearch',queryContentPaper).combobox('getValue');
		var teacherId = $('#teacherSearch',queryContentPaper).combobox('getValue');
		var content = {'group.groupId':groupId,'publishTeacher.teacherId':teacherId,state:true};
		$(paperList).datagrid({
			url:"paper/queryPaper.do",
			queryParams:content
		});
	})
	var onSelectPaper = function(){
		if(selectRowPaper==null){
			$.messager.alert("提示","请选择试卷","warning");
			return;
		}
		var url = "competition/saveCompetitionGroup.do";
		var content = {'competitionGroupId':selectRowGAP.competitionGroupId,'paper.paperId':selectRowPaper.paperId};
		$.post(url,content,
			function(result){
	    	  if(result.isSuccess){
					  $.messager.alert('提示','分配试卷成功',"info");
					  var content = {'competition.competitionId':selectRow.competitionId};
						$(gapList).datagrid({
							url:"competition/queryCompetitionGroup.do",
							queryParams:content
						});
					  $(paperDialog).dialog('close');
						
				  }else{
					  $.messager.alert('提示',result.message,"error");
				  }
			 }, "json");
	}
	var onAddPaper = function(){
		if(selectRowGAP==null){
			$.messager.alert("提示","请选择组别","warning");
			return;
		}
		$(paperDialog).dialog('open');
		$('#teacherSearch',queryContentPaper).combobox({
		  width:150,
		  data:ECMS.getTeacherList(),
		  valueField:'teacherId',
		  textField:'teacherName'
	  });
	  $('#groupSearch',queryContentPaper).combobox({
		  width:150,
		  data:ECMS.getGroupList() ,
		  valueField:'groupId',
		  textField:'groupName'
	  });
	  $(paperList).datagrid('loadData',{ total: 0, rows: [] });
	  $('#groupSearch',queryContentPaper).combobox('setValue','');
	  $('#teacherSearch',queryContentPaper).combobox('setValue','');
	}
	//----------------------删除分组--------------
	var onDeleteGroup = function(){
		if(selectRowGAP==null){
			 $.messager.alert('提示',"请选中要删除的纪录","warming");
			 return;	
		}
		$.messager.confirm("提示！","确定要删除选中的记录?",function(t){ 
			if(!t) return;
			var url = "competition/deleteCompetitionGroup.do";
			var content = {'competitionGroupId':selectRowGAP.competitionGroupId};
			$.post(url,content,
				function(result){
					if(result.isSuccess){
						var content = {'competition.competitionId':selectRow.competitionId};
						$(gapList).datagrid({
							url:"competition/queryCompetitionGroup.do",
							queryParams:content
						});
						//清空右边的奖项
						$(prizeList).datagrid('loadData',{totol:0,rows:[]});
					}else{
						$.messager.alert('提示',result.message,"error");
					}
				}, "json");
		});
	}
	//------------------移动------------------
	var onMoveGroup = function(direction){
		var selectRows =  $(gapList).datagrid('getSelections');
		if(selectRows.length==0){
			$.messager.alert('提示',"请选择行记录","warming");
			return;
		}
		var rows  = $(gapList).datagrid('getRows');
		if(direction==-1){
			if(selectIndexGAP==0){
				$.messager.alert('提示',"已经是第一条记录了","warming");
				return;
			}
		}else if(direction==1){//下移
			if(selectIndexGAP==rows.length-1){
				$.messager.alert('提示',"已经是最后一条记录了","warming");
				return;
			}
		}
		var updateRow = rows[selectIndexGAP+direction];
		var competitionGroupId = selectRowGAP.competitionGroupId;
		var groupName = selectRowGAP.groupName;
		var paperName = selectRowGAP.paperName;
		var uPaperName = updateRow.paperName;
		if(paperName==null){
			paperName='';
		}
		if(uPaperName==null){
			uPaperName='';
		}
		//后台更新排序
		var url = "competition/updateArrayCompetitionGroup.do";
		var content = {'competitionGroupId':competitionGroupId,updateCompetitionGroupId:updateRow.competitionGroupId};
		var result = syncCallService(url,content);
		if(result.isSuccess){
			$(gapList).datagrid('updateRow',{
				index: selectIndexGAP,
				row: {
					competitionGroupId:updateRow.competitionGroupId,
					groupName:updateRow.groupName,
					paperName:uPaperName
				}
			});
			$(gapList).datagrid('updateRow',{
				index: selectIndexGAP+direction,
				row: {
					competitionGroupId:competitionGroupId,
					groupName:groupName,
					paperName:paperName
				}
			});
			$(gapList).datagrid('selectRow',selectIndexGAP+direction);
		}else{
			$.messager.alert('提示',result.message,"error");
		}
	}
	//------------------奖项-------------------
	var prizeList = $('#prizeList',competitionConfigurationDialog);
	var selectRowPrize = null;
	var selectIndexPrize = null;
	$(prizeList).datagrid({
		title:'奖项',
		singleSelect:true,
		method:"POST",
		nowrap:true,
		striped: true,
		collapsible:true,
		rownumbers:true,
		fitColumns:true,
		fit:true,
		toolbar:[	
					{id:'addPrize_'+id,text:'添加',iconCls:'icon-add',handler:function(){onAddPrize()}},
					{id:'deletePrize_'+id,text:'删除',iconCls:'icon-remove',handler:function(){onDeletePrize()}},'-',
					{id:'moveUpPrize_'+id,text:'上移',iconCls:'icon-up',handler:function(){onMovePrize(-1)}},'-',
					{id:'moveDownPrize_'+id,text:'下移',iconCls:'icon-down',handler:function(){onMovePrize(1)}},'-'
				],
		columns:[[
			  {field:'prizeId',hidden:true},
			  {field:'prizeName',title:'奖项名称',width:150,align:"center"},
			  {field:'award',title:'奖品',width:150,align:"center",editor:'text'},
		]],
		onClickRow:function(rowIndex, rowData){
			if (selectIndexPrize != rowIndex){
				$(prizeList).datagrid('endEdit', selectIndexPrize);
				$(prizeList).datagrid('beginEdit', rowIndex);
			}
			selectRowPrize = rowData;
			selectIndexPrize = rowIndex;
		},
		onSelect:function(rowIndex, rowData){
			if (selectIndexPrize != rowIndex){
				$(prizeList).datagrid('endEdit', selectIndexPrize);
				$(prizeList).datagrid('beginEdit', rowIndex);
			}
			selectRowPrize = rowData;
			selectIndexPrize = rowIndex;
		},
		onLoadSuccess:function(){
			selectRowPrize = null;
	 		selectIndexPrize = null;
		},
		onAfterEdit:function(rowIndex, rowData,changes){
			var content = {'competitionPrizeId':rowData.competitionPrizeId,award:rowData.award};
			var url="competition/saveCompetitionPrize.do";
			$.post(url,content,
				function(result){
					if(result.isSuccess){
						
					}else{
						$.messager.alert('提示',result.message,"error");
					}
				}, "json");
		}
	  });
	var onAddPrize = function(){
		if(selectRowGAP==null){
			$.messager.alert('提示',"请选择组别","warming");
			return;
		}
		$(prizeViewList).datagrid('loadData',{ total: 0, rows: [] });
		$('#nameSearch',prizeDialog).val('');
		$(prizeDialog).dialog('open');
	}
	var onDeletePrize = function(){
		if(selectRowPrize==null){
			$.messager.alert('提示',"请选择要删除奖项","warming");
			return;
		}
		$.messager.confirm("提示！","确定要删除选中的记录?",function(t){ 
			if(!t) return;
			var url = "competition/deleteCompetitionPrize.do";
			var content = {'competitionPrizeId':selectRowPrize.competitionPrizeId};
			$.post(url,content,
				function(result){
					if(result.isSuccess){
						var content = {'competitionGroup.competitionGroupId':selectRowGAP.competitionGroupId};
						$(prizeList).datagrid({
							url:"competition/queryCompetitionPrize.do",
							queryParams:content
						});
					}else{
						$.messager.alert('提示',result.message,"error");
					}
				}, "json");
		});
	}
	var prizeDialog = $('#prizeDialog_'+id,$this);
	var queryContentPrize = $('#queryContentPrize_'+id,prizeDialog);
	var prizeViewList = $('#prizeViewList',prizeDialog);
	$(prizeDialog).dialog({ 
		title:'奖项',
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
				onSelectPrize();
			}
		},'-',{
			text:'退出',
			iconCls:'icon-cancel',
			handler:function(){
				$(prizeDialog).dialog('close');
			}
		}]
	});
	$(prizeViewList).datagrid({
		method:"POST",
		nowrap:true,
		striped: true,
		pagination:true,
		rownumbers:true,
		fit:true,
		columns:[[
		      {field:'ck',checkbox:true},
		      {field:'prizeId',hidden:true},
			  {field:'prizeName',title:'奖项',width:150,align:'center'}
		]]
	  });
	$('#searchPrize',prizeDialog).click(function(){
		var prizeName = $('#nameSearch',queryContentPrize).val();
		var content = {prizeName:prizeName,status:1,competitionGroupId:selectRowGAP.competitionGroupId};
		$(prizeViewList).datagrid({
			url:"dict/queryCompetitionPrize.do",
			queryParams:content
		});
	})
	var onSelectPrize = function(){
		  var rows=$(prizeViewList).datagrid('getSelections');
		  if(rows.length==0){
			  $.messager.alert("提示","请选择奖项","warning");
			  return;
		  }
		  var idArray = new Array();
		  for(var i=0;i<rows.length;i++){
			  idArray.push(rows[i].prizeId);
		  }
		  var prizeIds = idArray.join(CSIT.join);
		  var url = "competition/saveCompetitionPrize.do";
		  var content = {prizeIds:prizeIds,'competitionGroup.competitionGroupId':selectRowGAP.competitionGroupId};
		  $.post(url,content,
			  function(result){
				  if(result.isSuccess){
					  $.messager.alert('提示','添加参赛分组成功',"info");
					  var content = {'competitionGroup.competitionGroupId':selectRowGAP.competitionGroupId};
						$(prizeList).datagrid({
							url:"competition/queryCompetitionPrize.do",
							queryParams:content
						});
					  $(prizeDialog).dialog('close');
						
				  }else{
					  $.messager.alert('提示',result.message,"error");
				  }
			  }, "json");
	}
	//------------------移动奖项------------------
	var onMovePrize = function(direction){
		var selectRows =  $(prizeList).datagrid('getSelections');
		if(selectRows.length==0){
			$.messager.alert('提示',"请选择行记录","warming");
			return;
		}
		var rows  = $(prizeList).datagrid('getRows');
		if(direction==-1){
			if(selectIndexPrize==0){
				$.messager.alert('提示',"已经是第一条记录了","warming");
				return;
			}
		}else if(direction==1){//下移
			if(selectIndexPrize==rows.length-1){
				$.messager.alert('提示',"已经是最后一条记录了","warming");
				return;
			}
		}
		var updateRow = rows[selectIndexPrize+direction];
		var competitionPrizeId = selectRowPrize.competitionPrizeId;
		var prizeName = selectRowPrize.prizeName;
		var award = selectRowPrize.award;
		var uAward = updateRow.award;
		if(award==null){
			award='';
		}
		if(uAward==null){
			uAward='';
		}
		//后台更新排序
		var url = "competition/updateArrayCompetitionPrize.do";
		var content = {'competitionPrizeId':competitionPrizeId,updateCompetitionPrizeId:updateRow.competitionPrizeId};
		var result = syncCallService(url,content);
		if(result.isSuccess){
			$(prizeList).datagrid('updateRow',{
				index: selectIndexPrize,
				row: {
					competitionPrizeId:updateRow.competitionPrizeId,
					prizeName:updateRow.prizeName,
					award:uAward
				}
			});
			$(prizeList).datagrid('updateRow',{
				index: selectIndexPrize+direction,
				row: {
					competitionPrizeId:competitionPrizeId,
					prizeName:prizeName,
					award:award
				}
			});
			$(prizeList).datagrid('selectRow',selectIndexPrize+direction);
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