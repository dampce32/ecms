// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.paperInit = function() {
	  $(this).mask({maskMsg:'正在加载界面'});
	  var $this = $(this);
	  var id = $(this).attr('id');
	  var rightId = $(this).attr('rightId');
	  var width = $(document.body).width();
	  var height = $(document.body).height();
	  var selectRow = null;
	  var editDialog = $('#editDialog_'+id,$this);
	  var editForm = $('#editForm_'+id,editDialog);
	  var viewList =  $('#viewList_'+id,$this);
	  var queryContent = $('#queryContent_'+id,$this);
	  var saveType = 'Save_New';
	  
	  $('#teacherSearch',queryContent).combobox({
		  width:150,
		  data:ECMS.getTeacherList(),
		  valueField:'teacherId',
		  textField:'teacherName'
	  });
	  $('#groupSearch',queryContent).combobox({
		  width:150,
		  data:ECMS.getGroupList() ,
		  valueField:'groupId',
		  textField:'groupName'
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
		fit:true,
		toolbar:'#tb_'+id,
		columns:[[
		      {field:'state',title:'状态',width:50,sortable:true,align:"center",
					formatter: function(value,row,index){
						if (value=='false'){
							return '<img src="style/v1/icons/warn.png"/>';
						} else if (value=='true'){
							return '<img src="style/v1/icons/info.png"/>';
						}
				 }},
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
			selectRow = rowData;
			selectIndex = rowIndex;
			checkPaperBtn();
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
	  $('#m_add_'+id).menu({  
		    onClick:function(item){
		    	if(item.name=='addNew'){
		    		onNewAdd();
		    	}else if(item.name=='addCopy'){
		    		onCopyAdd(false);
		    	}
		    }
		    	
	  });
	  //修改
	  $('#update_'+id,$this).click(function(){
		  onUpdate();
	  });
	  //删除
	  $('#delete_'+id,$this).click(function(){
//		  onMulDelete();
		  onDelete();
	  });
	  //查看试卷
	  $('#view_'+id,$this).click(function(){
		  if(!$('#view_'+id,$this).linkbutton("options").disabled){
			  onView();
		  }
	  });
	  //查看学生端试卷
	  $('#viewStu_'+id,$this).click(function(){
		  if(!$('#view_'+id,$this).linkbutton("options").disabled){
			  onStuView();
		  }
	  });
	  //状态
	  $('#state_'+id).menu({  
		    onClick:function(item){
		    	if(item.name=='true'){
//		    		onMulUpdateState(true);
		    		onUpdateState(true);
		    	}else{
//		    		onMulUpdateState(false);
		    		onUpdateState(false);
		    	}
		    }
		    	
	  });
	  //查询
	  var search = function(flag){
		  var groupId = $('#groupSearch',queryContent).combobox('getValue');
		  var teacherId = $('#teacherSearch',queryContent).combobox('getValue');
		  var state = $('#statusSearch',queryContent).combobox('getValue');
		  var content = {'group.groupId':groupId,'publishTeacher.teacherId':teacherId,state:state};
			$(viewList).datagrid({
				url:"paper/queryPaper.do",
				queryParams:content
			});
			checkBtnRight();
		};
		//查询
		$('#search',$this).click(function(){
			search();
		});
	//参赛组别权限
	  $('#groupRight_'+id,$this).click(function(){
		  onDictRight();
	  });
	//编辑框
	$(editDialog).dialog({  
	    title: '编辑试卷',  
	    width:1024,
	    height:height-10,
	    closed: true,  
	    cache: false,  
	    modal: true,
	    closable:false,
	    toolbar:[{
	    	id:'paperSave_'+id,
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
			onCloseEditDialog();
		},
		onOpen:function(){
			isLoadingEditDialog = true;
		}
	});
	//关闭编辑框	
	var onCloseEditDialog = function(){
		//情况临时表中的数据
		var url = 'paper/clearTempPaper.do';
		var paperId = $('#paperId',editDialog).val();
		var content ={paperId:paperId};
		asyncCallService(url,content,function(result){
			if(!result.isSuccess){
				$.messager.alert('提示',result.message,'error');
			}
			$(editForm).form('clear');
			$(paperBigSmallList).datagrid('loadData',{rows:[],total:0});
			$(paperBigList).datagrid('loadData',{rows:[],total:0});
			lastIndexBig=null;
			isLoadingBig = false;
			lastIndexSmall=null;
			search();
		});
	};
	var initChoose = function(){
		 $('#publishTeacher',editDialog).combobox({
			  data:ECMS.getTeacherList(),
			  valueField:'teacherId',
			  textField:'teacherName'
		  });
		  $('#group',editDialog).combobox({
			  data:ECMS.getGroupList(),
			  valueField:'groupId',
			  textField:'groupName'
		  });
		  $('#paperType',editDialog).combobox({
			  valueField:'paperType',
				textField:'paperType',
				data:[
		    		    {paperType:'统一试题打乱生成'},
		    		    {paperType:'统一难度随机生成'}
		    		],
				onChange:function(newValue, oldValue){
					onChangePaperType(newValue,oldValue);
				}
		  });
	};
	//改变试卷类型
	var onChangePaperType = function(newValue,oldValue){
		var paperType = $.trim(newValue);
		if('统一试题打乱生成'==paperType){
			$(paperBigList).datagrid('showColumn','isSmallMix');
			
			$(paperBigSmallList).datagrid({
				columns:[[
					  {field:'subjectId',title:'试题编号',width:80,align:"center"},
				      {field:'isOptionMix',title:'选项打乱',width:70,align:"center",  
			                formatter:function(value,row,index){ 
			                	if(row.isOptionMix=='true'){
			                		return '是';
			                	}else{
			                		return '';
			                	}
			                	return value;
			                },  editor:{type:'checkbox',options:{on:'true',off:''}}},
				      {field:'score',title:'分数',width:100,align:"center",  editor:{type:'numberbox',options:{min:0,precision:1}}}
				]]
			  });
			$('#addMulBigSmall_'+id).hide();
		}else if('统一难度随机生成'==paperType){
			$(paperBigList).datagrid('hideColumn','isSmallMix');
			
			$(paperBigSmallList).datagrid({
				columns:[[
					  {field:'groupId',title:'参赛组别',width:160,align:"center",
							editor:{
								type:'combobox',
								options:{
									data:ECMS.getGroupList(),
									valueField:'groupId',
									textField:'groupName'
								}
							},formatter: function(value,row,index){
								var groups = ECMS.getGroupList();
								for(var i=0; i<groups.length; i++){
									if (groups[i].groupId == value) return groups[i].groupName;
								}
								return value;
							}},
					 {field:'difficulty',title:'难度',width:60,align:"center",
							editor:{
								type:'combobox',
								options:{
									valueField:'difficulty',
									textField:'difficulty',
									data:[
							    		    {difficulty:'易'},
							    		    {difficulty:'中'},
							    		    {difficulty:'难'}
							    		]
								}
							}},
					   {field:'isOptionMix',title:'选项打乱',width:70,align:"center",  
			                formatter:function(value,row,index){ 
			                	if(row.isOptionMix=='true'){
			                		return '是';
			                	}else{
			                		return '';
			                	}
			                	return value;
			                },  editor:{type:'checkbox',options:{on:'true',off:''}}},
				      {field:'score',title:'分数',width:100,align:"center",  editor:{type:'numberbox',options:{min:0,precision:1}}}
				]]
			  });
			$('#addMulBigSmall_'+id).show();
		}
		var rows = $(paperBigSmallList).datagrid('getRows');
		if(rows.length>0){
			var data = $(paperBigSmallList).datagrid('getData');
			$(paperBigSmallList).datagrid('loadData',data);
		}
	};
	//添加
	var onNewAdd = function(){
		$(editDialog).mask({maskMsg:'正在加载界面'});
		$(editForm).form('clear');
		saveType = 'Save_New';
		initChoose();
		viewBtns(true,'newAdd');
		var url = 'paper/newAddPaper.do';
		asyncCallService(url,function(result){
			if(result.isSuccess){
				var data = result.data;
				onShowData(data);
			}else{
				$.messager.alert('提示',result.message,'error');
			}
		});
		$(editDialog).mask('hide');
		$(editDialog).dialog('open');
	};
	//显示数据
	var onShowData = function(data,isShowSmall,kind){
		var tempPaper = eval("("+data.tempPaper+")");
		$('#paperId',editDialog).val(tempPaper.paperId);
		$('#paperName',editDialog).val(tempPaper.paperName);
		$('#group',editDialog).combobox('setValue',tempPaper.groupId);
		$('#paperType',editDialog).combobox('setValue',tempPaper.paperType);
		$('#publishTeacher',editDialog).combobox('setValue',tempPaper.publishTeacherId);
		
		$('#publishTeacherName',editDialog).val(ECMS.getTeacherName());
		$('#publishDate',editDialog).val(tempPaper.publishDate);
		$('#score',editDialog).val(tempPaper.score);
		$('#note',editDialog).val(tempPaper.note);
		$('#limits',editDialog).numberbox('setValue',tempPaper.limits);
		if(tempPaper.isBigMix=='false'){
			$('#isBigMix',editDialog).attr('checked',false);
		}else{
			$('#isBigMix',editDialog).attr('checked',true);
		}
		if(tempPaper.isSmallContinue=='false'){
			$('#isSmallContinue',editDialog).attr('checked',false);
		}else{
			$('#isSmallContinue',editDialog).attr('checked',true);
		}
		
		viewBtns(tempPaper.state,kind);
		
		if(data.tempPaperBig){
			var tempPaperBig = eval("("+data.tempPaperBig+")");
			$(paperBigList).datagrid('loadData',tempPaperBig);
		}
		
		if(isShowSmall){
			//取得第一道大题下的第一小题
			var rows = $(paperBigList).datagrid('getRows');
			if(rows.length>0){
				$(paperBigList).datagrid('selectRow',0);
				var rowData = rows[0];
				var rowIndex = 0;
				onViewTempSmalls(rowData.bigId);
				$(paperBigList).datagrid('beginEdit', rowIndex);
				lastIndexBig = rowIndex;
				onSelectBig(rowIndex,rowData);
			}
		}
	};
	//修改界面上的按钮
	var viewBtns = function(state,kind){
		if(kind=='modify'){
			if(state=='true'){
				$.messager.alert('提示','试卷已启用，不能修改','warning');
				$('#paperSave_'+id).linkbutton('disable');
				$('#addBig_'+id).linkbutton('disable');
				$('#deleteBig_'+id).linkbutton('disable');
				$('#moveUpBig_'+id).linkbutton('disable');
				$('#moveDownBig_'+id).linkbutton('disable');
				
				$('#addBigSmall_'+id).linkbutton('disable');
				$('#addMulBigSmall_'+id).linkbutton('disable');
				$('#deleteBigSmall_'+id).linkbutton('disable');
				$('#moveUpBigSmall_'+id).linkbutton('disable');
				$('#moveDownBigSmall_'+id).linkbutton('disable');
			}else if(state=='false'){
				$('#paperSave_'+id).linkbutton('enable');
				$('#addBig_'+id).linkbutton('enable');
				$('#deleteBig_'+id).linkbutton('enable');
				$('#moveUpBig_'+id).linkbutton('enable');
				$('#moveDownBig_'+id).linkbutton('enable');
				
				$('#addBigSmall_'+id).linkbutton('enable');
				$('#addMulBigSmall_'+id).linkbutton('enable');
				$('#deleteBigSmall_'+id).linkbutton('enable');
				$('#moveUpBigSmall_'+id).linkbutton('enable');
				$('#moveDownBigSmall_'+id).linkbutton('enable');
			}
		}else{
				$('#paperSave_'+id).linkbutton('enable');
				$('#addBig_'+id).linkbutton('enable');
				$('#deleteBig_'+id).linkbutton('enable');
				$('#moveUpBig_'+id).linkbutton('enable');
				$('#moveDownBig_'+id).linkbutton('enable');
				$('#addBigSmall_'+id).linkbutton('enable');
				$('#addMulBigSmall_'+id).linkbutton('enable');
				$('#deleteBigSmall_'+id).linkbutton('enable');
				$('#moveUpBigSmall_'+id).linkbutton('enable');
				$('#moveDownBigSmall_'+id).linkbutton('enable');
		}
	}
	//赋值添加
	var onCopyAdd = function(){
		var selectRow = $(viewList).datagrid('getSelected');
		if(selectRow==null){
			$.messager.alert('提示','请选择要复制的试卷','warning');
			return;
		}
		$(editForm).form('clear');
		saveType = 'Save_New';
		initChoose();
		viewBtns(true,'copyAdd');
		$(editDialog).mask({maskMsg:'正在加载界面'});
		var content ={paperId:selectRow.paperId};
		var url = 'paper/copyAddPaper.do';
		asyncCallService(url,content,function(result){
			if(result.isSuccess){
				var data = result.data;
				onShowData(data,true);
				$(editDialog).mask('hide');
			}else{
				$.messager.alert('提示',result.message,'error');
			}
		});
		$(editDialog).dialog('open');
	};
	//保存前的赋值操作
	var setValue = function(){
		var paperName = $.trim($('#paperName',editDialog).val());
		if(paperName==''){
			$.messager.alert('提示','请填写试卷名称','warning');
			return false;
		}
		//参赛组别
		var groupId = $('#group',editDialog).combobox('getValue') ;
		if(''==groupId){
			$.messager.alert('提示','请选择参赛组别','warning');
			return false;
		}
		if(isNaN(parseInt(groupId))){
			$.messager.alert('提示','请选择提供选择的参赛组别','warning');
			return false;
		}
		var limits = $.trim($('#limits',editDialog).numberbox('getValue'));
		if(limits==''){
			$.messager.alert('提示','请设置限时','warning');
			return false;
		}
		if(limits=='0'){
			$.messager.alert('提示','请设置限时，不能为0分钟','warning');
			return false;
		}
		//为更新临时表准备数据
		var rowsBig = $(paperBigList).datagrid('getRows');
		if(rowsBig.length==0){
			$.messager.alert('提示','大题为空，请添加大题','warning');
			return false;
		}
		var rowsSmall = $(paperBigSmallList).datagrid('getRows');
		//正在编辑的小题
		$(paperBigSmallList).datagrid('endEdit', lastIndexSmall);
		var smallIdArray = new Array();
		var isOptionMixArray = new Array();
		var scoreArray = new Array();
		var difficultyArray = new Array();
		
		for ( var i = 0; i < rowsSmall.length; i++) {
			var row = rowsSmall[i];
			smallIdArray.push(row.smallId);
			isOptionMixArray.push(row.isOptionMix);
			scoreArray.push(row.score);
			difficultyArray.push(row.difficulty);
		}
		if(rowsSmall.length!=0){
			var selectRowBig = $(paperBigList).datagrid('getSelected');
			$('#bigId',editForm).val(selectRowBig.bigId);
			$('#smallIds',editForm).val(smallIdArray.join(CSIT.join));
			$('#isOptionMixs',editForm).val(isOptionMixArray.join(CSIT.join));
			$('#scores',editForm).val(scoreArray.join(CSIT.join));
			$('#difficultys',editForm).val(difficultyArray.join(CSIT.join));
		}
		//正在编辑的大题
		$(paperBigList).datagrid('endEdit', lastIndexBig);
		
		var bigIdArray = new Array();
		var subjectTypeArray = new Array();
		var bigNameArray = new Array();
		var isSmallMixArray = new Array();
		var scoreBigArray = new Array();
		
		for ( var i = 0; i < rowsBig.length; i++) {
			var row = rowsBig[i];
			bigIdArray.push(row.bigId);
			subjectTypeArray.push(row.subjectType);
			bigNameArray.push(row.bigName);
			isSmallMixArray.push(row.isSmallMix);
			scoreBigArray.push(row.score);
		}
		
		$('#bigIds',editForm).val(bigIdArray.join(CSIT.join));
		$('#subjectTypes',editForm).val(subjectTypeArray.join(CSIT.join));
		$('#bigNames',editForm).val(bigNameArray.join(CSIT.join));
		$('#isSmallMixs',editForm).val(isSmallMixArray.join(CSIT.join));
		$('#scoreBigs',editForm).val(scoreBigArray.join(CSIT.join));
		
		if(saveType =='Save_New'){
			$('#saveType',editForm).val('Save_New');
		}else if(saveType =='Save_Modify'){
			$('#saveType',editForm).val('Save_Modify');
		}
		$(editDialog).mask({maskMsg:'正在保存'});
		return true;
	};
	//保存
	var onSave = function(){
		$(editForm).form('submit',{
			url:'paper/savePaper.do',
			onSubmit: function(){
				return setValue();
			},
			success: function(data){
				$(editDialog).mask('hide');
				var result = eval('('+data+')');
				if(result.isSuccess){
					var fn = function(){
						saveType = 'Save_Modify';
					}
					$.messager.alert('提示','保存成功','info',fn);
				}else{
					$.messager.alert('提示',result.message,"error");
				}
				$(paperBigList).datagrid('beginEdit', lastIndexBig);
				$(paperBigSmallList).datagrid('beginEdit', lastIndexSmall);
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
			onOpen(selectRow.paperId,'modify');
			$(editDialog).dialog('open');
		}
	 };
	var onOpen = function(paperId,kind){
		saveType = 'Save_Modify';
		initChoose();
		$(editDialog).mask({maskMsg:'正在加载界面'});
		var url = 'paper/modifyPaper.do?paperId='+paperId;
		asyncCallService(url,function(result){
			if(result.isSuccess){
				var data = result.data;
				onShowData(data,true,kind);
				$(editDialog).mask('hide');
			}else{
				$.messager.alert('提示',result.message,'error');
			}
		});
	};
	//删除
	var onDelete = function(){
		var selectRow = $(viewList).datagrid('getSelected');
		if(selectRow==null){
			 $.messager.alert('提示',"请选中要删除的纪录","warming");
			 return;	
		}
		$.messager.confirm("提示！","确定要删除选中的记录?",function(t){ 
			if(!t) return;
			var idArray = new Array();
			idArray.push(selectRow.paperId);
			var ids = idArray.join(CSIT.join);
			var url = "paper/mulDeletePaper.do";
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
				idArray.push(rows[i].paperId);
			}
			var ids = idArray.join(CSIT.join);
			var url = "paper/mulDeletePaper.do";
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
	//修改审核状态
	var onUpdateState = function(state){
		var selectRow =  $(viewList).datagrid('getSelected');
		var msg = '';
		if(state){
			msg = '启用';
		}else{
			msg = '禁用';
		}
		if(selectRow==null){
			$.messager.alert("提示","请选择要"+msg+"的数据行","warning");
			return;
		}
		$.messager.confirm("提示","确定要"+msg+"记录?",function(t){ 
			if(t){
				var url = 'paper/updateStatePaper.do';
				var content ={paperId:selectRow.paperId,state:state};
				asyncCallService(url,content,function(result){
					if(result.isSuccess){
						var fn = function(){
							var rowIndex =  $(viewList).datagrid('getRowIndex',selectRow);
							if(state){
								selectRow.state = 'true';
							}else{
								selectRow.state = 'false';
							}
							$(viewList).datagrid('updateRow',{
								index: rowIndex,
								row: selectRow
							});
							checkPaperBtn();
						}
						$.messager.alert('提示',msg+'成功','info',fn);
					}else{
						$.messager.alert('提示',result.message,'error');
					}
				});
			}
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
			idArray.push(rows[i].paperId);
		}
		$.messager.confirm("提示","确定要"+msg+"记录?",function(t){ 
			if(t){
				var url = 'paper/mulUpdateStatePaper.do';
				var content ={ids:idArray.join(CSIT.join),state:state};
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
	//------------------大题-------------------
	var paperBigList = $('#paperBigList_'+id,editDialog);
	var lastIndexBig=null;
	var isLoadingBig = false;
	//加载列表
	$(paperBigList).datagrid({
		title:'大题',
		singleSelect:true,
		method:"POST",
		nowrap:true,
		striped: true,
		collapsible:true,
		rownumbers:true,
		fitColumns:true,
		fit:true,
		toolbar:[	
					{id:'addBig_'+id,text:'添加',iconCls:'icon-add',handler:function(){onAddBig();}},'-',
					{id:'deleteBig_'+id,text:'删除',iconCls:'icon-remove',handler:function(){onDeleteBig();}},'-',
					{id:'moveUpBig_'+id,text:'上移',iconCls:'icon-up',handler:function(){onMoveBig(-1);}},'-',
					{id:'moveDownBig_'+id,text:'下移',iconCls:'icon-down',handler:function(){onMoveBig(1);}},'-'
				],
		columns:[[
			  {field:'subjectType',title:'大题题型',width:100,align:"center",
					editor:{
						type:'combobox',
						options:{
							valueField:'subjectType',
							textField:'subjectType',
							data:[
					    		    {subjectType:'单项选择'},
					    		    {subjectType:'填空'},
					    		    {subjectType:'完型填空'},
					    		    {subjectType:'阅读理解'}
					    		],
							onChange:function(newValue, oldValue){
								onChangeBigSubjectType(newValue);
								return newValue;
							}
						}
					}},
		      {field:'bigName',title:'大题名称',width:300,align:"center",editor:'text'},
		      {field:'isSmallMix',title:'小题打乱',width:100,align:"center",  
	                formatter:function(value,row,index){ 
	                	if(row.isSmallMix=='true'){
	                		return '是';
	                	}else{
	                		return '';
	                	}
	                },  editor:{type:'checkbox',options:{on:'true',off:''}}},
		      {field:'score',title:'分数',width:100,align:"center",editor:{type:'numberbox',options:{disabled:true,precision:1,
		    	  	onChange:function(newValue,oldValue){
 			    	 var totalScore = 0 ;
			    	 var row = $(paperBigList).datagrid('getSelected');
					 var rowIndex = $(paperBigList).datagrid('getRowIndex',row); 
					 
					 var rows =  $(paperBigList).datagrid('getRows');
					 for ( var i = 0; i < rows.length; i++) {
						 if(i!=rowIndex){
							row = rows[i];
							totalScore += parseFloat(row.score);
						 }
					}
					totalScore+=parseFloat(newValue); 
			    	$('#score',editDialog).val(totalScore);
			    }}}}
		]],
		onBeforeLoad:function(){
			$(this).datagrid('rejectChanges');
		},
		onClickRow:function(rowIndex, rowData){
			if (lastIndexBig != rowIndex){
				$(paperBigList).datagrid('endEdit', lastIndexBig);
				onViewTempSmalls(rowData.bigId);
				$(paperBigList).datagrid('beginEdit', rowIndex);
				isLoadingBig = false;
			}
			lastIndexBig = rowIndex;
			onSelectBig(rowIndex,rowData);
		},
		onBeforeEdit:function(rowIndex, rowData){
			isLoadingBig = true;
		}
	  });
	//选择大题
	var onSelectBig = function(selectRowIndex,selectRow){
		var subjectType = $.trim(selectRow.subjectType);
		if(subjectType=='简答'||subjectType=='填空'){
			$(paperBigSmallList).datagrid('hideColumn','isOptionMix');
		}else{
			$(paperBigSmallList).datagrid('showColumn','isOptionMix');
		}
		var data = $(paperBigSmallList).datagrid('getData');
		$(paperBigSmallList).datagrid('loadData',data);
	}
	//改变大题的大题类型
	var onChangeBigSubjectType = function(subjectType){
		if(isLoadingBig){
			isLoadingBig = false;
		}else{
			var selectRow = $(paperBigList).datagrid('getSelected');
			//清空小题数据
			var url = 'paper/clearSmallTempPaperBig.do';
			var content ={'id.paperId':selectRow.paperId,'id.bigId':selectRow.bigId};
			asyncCallService(url,content,function(result){
				if(result.isSuccess){
					$(paperBigSmallList).datagrid('loadData',{rows:[],total:0});
					var selectRowIndex = $(paperBigList).datagrid('getRowIndex',selectRow);
					selectRow.subjectType = subjectType;
					onSelectBig(selectRowIndex,selectRow);
					
					var editors = $(paperBigList).datagrid('getEditors', selectRowIndex);
					
					var bigNameEditor = editors[1];  
				    $(bigNameEditor.target).val(subjectType);
				}else{
					$.messager.alert('提示',result.message,'error');
				}
			});
		}
	}
	//添加大题
	var onAddBig = function(){
		var url = 'paper/saveTempPaperBig.do';
		var paperId = $('#paperId',editDialog).val();
		var rows = $(paperBigList).datagrid('getRows');
		var array = rows.length;
		var content ={'id.paperId':paperId,array:array};
		asyncCallService(url,content,function(result){
			if(result.isSuccess){
				var data = result.data;
				$(paperBigList).datagrid('appendRow',{
					paperId:paperId,
					bigId:data.bigId,
					subjectType:'',
					bigName:'',
					isSmallMix:false,
					score:0
				})
			}else{
				$.messager.alert('提示',result.message,'error');
			}
		});
	}
	//删除大题
	var onDeleteBig = function(){
		var selectRowBig = $(paperBigList).datagrid('getSelected');
		if(selectRowBig==null){
			$.messager.alert("提示","请选择要删除的大题","warning");
			return;
		}
		$.messager.confirm("提示","确定要删除大题吗?",function(t){ 
			if(t){
				var url = 'paper/deleteTempPaperBig.do';
				var paperId = $('#paperId',editDialog).val();
				var content ={'id.paperId':paperId,'id.bigId':selectRowBig.bigId};
				asyncCallService(url,content,function(result){
					if(result.isSuccess){
						var fn = function(){
							var rowIndexBig = $(paperBigList).datagrid('getRowIndex',selectRowBig);
							$(paperBigList).datagrid('deleteRow',rowIndexBig);
							lastIndexBig = null;
							$(paperBigList).datagrid('unselectAll');
							$(paperBigSmallList).datagrid('loadData',{rows:[],total:0});
							onSummaryScore();
						}
						$.messager.alert('提示','删除大题成功','info',fn);
					}else{
						$.messager.alert('提示',result.message,'error');
					}
				});
			}
		});
	}
	//统计分数
	var onSummaryScore = function(){
		var rows = $(paperBigList).datagrid('getRows');
		var totalScore = 0;
		for ( var i = 0; i < rows.length; i++) {
			var row = rows[i];
			totalScore+= parseFloat(row.score);
		}
		$('#score',editDialog).val(totalScore);
	}
	//移动
	var onMoveBig = function(direction){
		
		
		var rows  = $(paperBigList).datagrid('getRows');
		var selectRow = $(paperBigList).datagrid('getSelected');
		var selectRowIndex = $(paperBigList).datagrid('getRowIndex',selectRow);
		if(direction==-1){
			if(selectRowIndex==0){
				$.messager.alert('提示',"已经是第一条记录了","warming");
				return;
			}
		}else if(direction==1){//下移
			if(selectRowIndex==rows.length-1){
				$.messager.alert('提示',"已经是最后一条记录了","warming");
				return;
			}
		}
		$(paperBigList).datagrid('endEdit', lastIndexBig);
		var updateRow = rows[selectRowIndex+direction];
		
		var bigId = selectRow.bigId;
		var subjectType = selectRow.subjectType;
		var bigName = selectRow.bigName;
		var isSmallMix = selectRow.isSmallMix;
		var score = selectRow.score;
		var array = updateRow.array;
		
		updateRow.array = selectRow.array;
		
		$(paperBigList).datagrid('updateRow',{
			index: selectRowIndex,
			row: updateRow
		});
		
		$(paperBigList).datagrid('updateRow',{
			index: selectRowIndex+direction,
			row: {
				bigId:bigId,
				subjectType:subjectType,
				bigName:bigName,
				score:score,
				isSmallMix:isSmallMix,
				array:array
			}
		});
		$(paperBigList).datagrid('unselectAll');
		$(paperBigList).datagrid('selectRow',selectRowIndex+direction);
		lastIndexBig = selectRowIndex+direction;
		$(paperBigList).datagrid('beginEdit', lastIndexBig);
	}
	//------------------小题-------------------
	var paperBigSmallList = $('#paperBigSmallList_'+id,editDialog);
	var addMulBigSmallDialog = $('#addMulBigSmallDialog_'+id,$this);
	
	var lastIndexSmall=null;
	$(paperBigSmallList).datagrid({
		title:'小题',
		singleSelect:true,
		method:"POST",
		nowrap:true,
		striped: true,
		collapsible:true,
		rownumbers:true,
		fitColumns:true,
		fit:true,
		toolbar:[	
					{id:'addBigSmall_'+id,text:'添加',iconCls:'icon-add',handler:function(){onSelectSubject()}},
					{id:'addMulBigSmall_'+id,text:'批量添加',iconCls:'icon-add',handler:function(){onAddMulBigSmall()}},'-',
					{id:'deleteBigSmall_'+id,text:'删除',iconCls:'icon-remove',handler:function(){onDeleteSmall()}},'-',
					{id:'moveUpBigSmall_'+id,text:'上移',iconCls:'icon-up',handler:function(){onMoveSmall(-1)}},'-',
					{id:'moveDownBigSmall_'+id,text:'下移',iconCls:'icon-down',handler:function(){onMoveSmall(1)}},'-'
				],
		onClickRow:function(rowIndex, rowData){
			if (lastIndexSmall != rowIndex){
				$(paperBigSmallList).datagrid('endEdit', lastIndexSmall);
				$(paperBigSmallList).datagrid('beginEdit', rowIndex);
				setEditingSmall(rowIndex);
			}
			lastIndexSmall = rowIndex;
		},
		onAfterEdit:function(rowIndex, rowData, changes){
			if(getJsonLength(changes)>0){
				onUpdateTempSmall(rowIndex, rowData);
			}
		},
		onLoadSuccess:function(data){
			lastIndexSmall=null;
			onUpdateBigScore();
		}
	  });
	function setEditingSmall(rowIndex){  
	    var scoreEditor = $(paperBigSmallList).datagrid('getEditor', {index:rowIndex,field:'score'});
	    scoreEditor.target.bind('change', function(){  
	    	onUpdateBigScore('cellChange',scoreEditor.target.val(),rowIndex);  
	    });
	}
	//添加多个小题
	var onAddMulBigSmall = function(){
		var selectRowBig = $(paperBigList).datagrid('getSelected');
		if(selectRowBig==null){
			$.messager.alert('提示','请先选择大题','warning');
			return;
		}
		$(addMulBigSmallDialog).form('clear');
		$(addMulBigSmallDialog).dialog('open');
	}
	//选择试题
	var onSelectSubject = function(){
		var groupId = $('#group',editDialog).combobox('getValue');
		if(''==groupId){
			$.messager.alert("提示","请先选择参赛组别","warning");
			return;
		}
		var selectRowBig = $(paperBigList).datagrid('getSelected');
		if(null==selectRowBig){
			$.messager.alert("提示","请先选择大题","warning");
			return;
		}
		//大题已选择好了，大题题型
		var selectRowIndexBig = $(paperBigList).datagrid('getRowIndex',selectRowBig);
		var subjectTypeEditor = $(paperBigList).datagrid('getEditor', {index:selectRowIndexBig,field:'subjectType'});
		
		var subjectType = $(subjectTypeEditor.target).combobox('getValue');
		if(''==subjectType){
			$.messager.alert("提示","请先选择大题题型","warning");
			return;
		}
		//根据试卷类型
		var paperType = $.trim($('#paperType',editDialog).combobox('getValue'));
		if(paperType=='统一试题打乱生成'){
			$('#publishTeacherSelectSubjectDialog',selectSubjectDialog).combobox({
				  width:150,
				  data:ECMS.getTeacherList(),
				  valueField:'teacherId',
				  textField:'teacherName'
			  });
			$('#publishTeacherSelectSubjectDialog',selectSubjectDialog).combobox('setValue',ECMS.getTeacherId());
			$(selectSubjectDialog).dialog('open');
			$(searchSelectSubjectDialogBtn).click();
		}else if(paperType=='统一难度随机生成'){
			onAddPaperBigSmall('统一难度随机生成');
		}
	}
	//删除小题
	var onDeleteSmall = function(){
		var selectRowSmall = $(paperBigSmallList).datagrid('getSelected');
		if(selectRowSmall==null){
			$.messager.alert("提示","请选择要删除的小题","warning");
			return;
		}
		$.messager.confirm("提示","确定要删除小题吗?",function(t){ 
			if(t){
				var url = 'paper/deleteTempPaperBigSmall.do';
				var paperId = $('#paperId',editDialog).val();
				var selectRowBig = $(paperBigList).datagrid('getSelected');
				var content ={'id.paperId':paperId,'id.bigId':selectRowBig.bigId,'id.smallId':selectRowSmall.smallId};
				asyncCallService(url,content,function(result){
					if(result.isSuccess){
						var fn = function(){
							var rowIndexSmall = $(paperBigSmallList).datagrid('getRowIndex',selectRowSmall);
							$(paperBigSmallList).datagrid('deleteRow',rowIndexSmall);
							lastIndexSmall = null;
							onUpdateBigScore();
						}
						$.messager.alert('提示','删除小题成功','info',fn);
					}else{
						$.messager.alert('提示',result.message,'error');
					}
				});
			}
		});
	}
	//更新大题的分值
	var onUpdateBigScore = function(kind,newValue,rowIndex){
		var rows = $(paperBigSmallList).datagrid('getRows');
		var totalScore = 0;
		if(kind=='cellChange'){
			for ( var i = 0; i < rows.length; i++) {
				var row = rows[i];
				if(i==rowIndex){
					totalScore+= parseFloat(newValue);
				}else{
					totalScore+= parseFloat(row.score);
				}
			}
		}else{
			for ( var i = 0; i < rows.length; i++) {
				var row = rows[i];
				totalScore+= parseFloat(row.score);
			}
		}
		var selectRowBig =  $(paperBigList).datagrid('getSelected');
		var selectRowIndexBig = $(paperBigList).datagrid('getRowIndex',selectRowBig);
		
		var scoreEditor = $(paperBigList).datagrid('getEditor', {index:selectRowIndexBig,field:'score'});
	    $(scoreEditor.target).numberbox('setValue',totalScore);
	}
	//移动
	var onMoveSmall = function(direction){
		$(paperBigSmallList).datagrid('endEdit', lastIndexSmall);
		lastIndexSmall = null;
		
		var rows  = $(paperBigSmallList).datagrid('getRows');
		var selectRowSmall = $(paperBigSmallList).datagrid('getSelected');
		var selectRowIndex = $(paperBigSmallList).datagrid('getRowIndex',selectRowSmall);
		if(direction==-1){
			if(selectRowIndex==0){
				$.messager.alert('提示',"已经是第一条记录了","warming");
				return;
			}
		}else if(direction==1){//下移
			if(selectRowIndex==rows.length-1){
				$.messager.alert('提示',"已经是最后一条记录了","warming");
				return;
			}
		}
		
		var updateRow = rows[selectRowIndex+direction];
		
		var smallId = selectRowSmall.smallId;
		var subjectId = selectRowSmall.subjectId;
		var isOptionMix = selectRowSmall.isOptionMix;
		var score = selectRowSmall.score;
		var difficulty = selectRowSmall.difficulty;
		
		//后台更新排序
		var url = 'paper/moveArrayTempPaperBigSmall.do';
		var content ={'paperId':selectRowSmall.paperId,'bigId':selectRowSmall.bigId,
				'smallIdFrom':selectRowSmall.smallId,'arrayFrom':selectRowSmall.array,
				'smallIdTo':updateRow.smallId,'arrayTo':updateRow.array};
		asyncCallService(url,content,function(result){
			if(result.isSuccess){
				updateRow.array = selectRowIndex;
				$(paperBigSmallList).datagrid('updateRow',{
					index: selectRowIndex,
					row: updateRow
				});
				
				$(paperBigSmallList).datagrid('updateRow',{
					index: selectRowIndex+direction,
					row: {
						smallId:smallId,
						subjectId:subjectId,
						isOptionMix:isOptionMix,
						score:score,
						array:selectRowIndex+direction,
						difficulty:difficulty
					}
				});
				lastIndexSmall = selectRowIndex+direction;
				$(paperBigSmallList).datagrid('selectRow',lastIndexSmall);
				$(paperBigSmallList).datagrid('beginEdit', lastIndexSmall);
			}else{
				$.messager.alert('提示',result.message,'error');
			}
		});
	}
	//批量添加小题
	$(addMulBigSmallDialog).dialog({  
	    title: '配置批量添加小题',  
	    width:400,
	    height:200,
	    closed: true,  
	    cache: false,  
	    modal: true,
	    closable:false,
	    toolbar:[	
		 			{id:'addPaperBigSmall'+id,text:'确定',iconCls:'icon-ok',handler:function(){onOkAddMulBigSmall();}},'-',
		 			{text:'退出',iconCls:'icon-cancel',handler:function(){
		 					$(addMulBigSmallDialog).dialog('close');
		 				}
		 			}
		 	  ]
	});
	var onOkAddMulBigSmall = function(){
		var countSmall = $.trim($('#countSmall',addMulBigSmallDialog).numberbox('getValue'));
		var scoreSmall = $.trim($('#scoreSmall',addMulBigSmallDialog).numberbox('getValue'));
		if(''==countSmall){
			$.messager.alert('提示','请填写题数','warning');
			return;
		}
		if(''==scoreSmall){
			$.messager.alert('提示','请填写分数','warning');
			return;
		}
		var selectRowBig = $(paperBigList).datagrid('getSelected');
		if(selectRowBig==null){
			$.messager.alert('提示','请先选择大题','warning');
			return;
		}
		var groupId = $('#group',editDialog).combobox('getValue');
		if(groupId==''){
			$.messager.alert('提示','请选择参赛组别','warning');
			return;
		}
		var rowsSmall = $(paperBigSmallList).datagrid('getRows');
		var array = 0;
		if(rowsSmall.length>0){
			array = parseInt(rowsSmall[rowsSmall.length-1].array)+1;
		}
		//后台更新排序
		var url = 'paper/mulAddTempPaperBigSmall.do';
		var content ={'paperId':selectRowBig.paperId,'bigId':selectRowBig.bigId,
				'countSmall':countSmall,'scoreSmall':scoreSmall,array:array,groupId:groupId};
		asyncCallService(url,content,function(result){
			if(result.isSuccess){
				var data = result.data;
				var smallIds = eval("("+data.smallIds+")");
				for ( var i = 0; i < smallIds.length; i++) {
					$(paperBigSmallList).datagrid('appendRow',{
						paperId:selectRowBig.paperId,
						bigId:selectRowBig.bigId,
						smallId:smallIds[i].smallId,
						subjectId:'',
						groupId:groupId,
						isOptionMix:'true',
						score:scoreSmall,
						array:array,
						difficulty:'中'
					})
					array = array+1;
				}
				$(paperBigSmallList).datagrid('endEdit', lastIndexSmall);
				$(paperBigSmallList).datagrid('unselectAll');
				lastIndexSmall = null;
				onUpdateBigScore(null,scoreSmall,array+1);
				$(addMulBigSmallDialog).dialog('close');
			}else{
				$.messager.alert('提示',result.message,'error');
			}
		});
	}
	//-------------选择试题---------------
	var selectSubjectDialog = $('#selectSubjectDialog_'+id,$this); 
	var selectSubjectList = $('#selectSubjectList_'+id,selectSubjectDialog); 
	var searchSelectSubjectDialogBtn = $('#searchSelectSubjectDialogBtn',selectSubjectDialog); 
	//编辑框
	$(selectSubjectDialog).dialog({  
	    title: '选择试题',  
	    width:1000,
	    height:height-10,
	    closed: true,  
	    cache: false,  
	    modal: true,
	    closable:false,
	    toolbar:[	
		 			{id:'addPaperBigSmall'+id,text:'选择',iconCls:'icon-ok',handler:function(){onMulAddPaperBigSmall();}},'-',
		 			{text:'退出',iconCls:'icon-cancel',handler:function(){
		 					$(selectSubjectDialog).dialog('close');
		 				}
		 			}
		 	  ],
	    onClose:function(){
	    	$(selectSubjectList).datagrid('loadData',{rows:[],total:0});
	    }
	});
	$(selectSubjectList).datagrid({
		  fit:true,
		  autoRowHeight:true,
		  columns:[[
		        {field:'ck',checkbox:true},
			    {field:'subjectId',title:'编号',width:40,align:"center"},
				{field:'subjectType',title:'题型',width:80,align:"center"},
				{field:'descript',title:'试题描述',width:302,align:"center"},
				{field:'difficulty',title:'难度',width:40,align:"center"},
				{field:'groupName',title:'参赛组别',width:100,align:"center"},
				{field:'score',title:'默认分值',width:70,align:"center"},
				{field:'publishTeacherName',title:'出题老师',width:90,align:"center"},
				{field:'publishDate',title:'出题时间',width:145,align:"center"}
		  ]],
		  rownumbers:true,
		  pagination:true,
		  onBeforeLoad:function(){
				$(this).datagrid('rejectChanges');
		  } 
		 });
	//查询试题
	$(searchSelectSubjectDialogBtn).click(function(){
		var groupId = $('#group',editDialog).combobox('getValue');
		if(''==groupId){
			$.messager.alert("提示","请先选择参赛组别","warning");
			return;
		}
		var selectRowBig = $(paperBigList).datagrid('getSelected');
		if(null==selectRowBig){
			$.messager.alert("提示","请先选择大题","warning");
			return;
		}
		var selectRowIndexBig = $(paperBigList).datagrid('getRowIndex',selectRowBig);
		var subjectTypeEditor = $(paperBigList).datagrid('getEditor', {index:selectRowIndexBig,field:'subjectType'});
		
		var subjectType = $(subjectTypeEditor.target).combobox('getValue');
		if(''==subjectType){
			$.messager.alert("提示","请先选择大题题型","warning");
			return;
		}
		
		var publishTeacherId = $('#publishTeacherSelectSubjectDialog',selectSubjectDialog).combobox('getValue');
		//已添加的试题
		var rows = $(paperBigSmallList).datagrid('getRows');
		var subjectIdArray = new Array();
		for ( var i = 0; i < rows.length; i++) {
			subjectIdArray.push(rows[i].subjectId);
		}
		var subjectIds = subjectIdArray.join(CSIT.join);
		var difficulty = $('#difficultySearch',selectSubjectDialog).combobox('getValue');
		var content = {'group.groupId':groupId,'publishTeacher.teacherId':publishTeacherId,
				subjectIds:subjectIds,subjectType:subjectType,difficulty:difficulty};
		
		$(selectSubjectList).datagrid({
			url:"subject/selectPaperBigSmallSubject.do",
			queryParams:content
		});
	})
	//批量添加多试题到试题临时小题表，并更新小题列表
	var onMulAddPaperBigSmall = function(){
		var url = 'paper/saveMulMixTempPaperBigSmall.do';
		var paperId = $('#paperId',editDialog).val();
		var selectRowBig = $(paperBigList).datagrid('getSelected');
		
		var rows = $(paperBigSmallList).datagrid('getRows');
		var array = rows.length;
		var groupId = $('#group',editDialog).combobox('getValue');
		var subjectIdArray = new Array();
		
		var selectRows = $(selectSubjectList).datagrid('getChecked');
		for ( var i = 0; i < selectRows.length; i++) {
			subjectIdArray.push(selectRows[i].subjectId);
		}
		var ids = subjectIdArray.join(CSIT.join);
		
		var content ={'id.paperId':paperId,'id.bigId':selectRowBig.bigId,ids:ids,array:array,groupId:groupId};
		asyncCallService(url,content,function(result){
			if(result.isSuccess){
				onViewTempSmalls(selectRowBig.bigId);
				$(selectSubjectDialog).dialog('close');
			}else{
				$.messager.alert('提示',result.message,'error');
			}
		});
	}
	
	//添加选择的试题到试卷临时小题表中,并更新小题列表
	var onAddPaperBigSmall = function(paperType){
		var url = 'paper/saveMixTempPaperBigSmall.do';
		var paperId = $('#paperId',editDialog).val();
		var selectRowBig = $(paperBigList).datagrid('getSelected');
		var subjectId = '';
		var score = 0;
		
		var rows = $(paperBigSmallList).datagrid('getRows');
		var groupId = $('#group',editDialog).combobox('getValue');
		var array = rows.length;
		
		if(paperType!='统一难度随机生成'){
			var selectRow = $(selectSubjectList).datagrid('getSelected');
			subjectId = selectRow.subjectId;
			score = selectRow.score;
		}
		
		var content ={'id.paperId':paperId,'id.bigId':selectRowBig.bigId,subjectId:subjectId,score:score,array:array,groupId:groupId};
		asyncCallService(url,content,function(result){
			if(result.isSuccess){
				var data = result.data;
				$(paperBigSmallList).datagrid('appendRow',{
					paperId:paperId,
					bigId:selectRowBig.bigId,
					smallId:data.smallId,
					subjectId:subjectId,
					groupId:groupId,
					isOptionMix:'true',
					score:score,
					array:array,
					difficulty:'中'
				})
				$(paperBigSmallList).datagrid('endEdit', lastIndexSmall);
				$(paperBigSmallList).datagrid('unselectAll');
				lastIndexSmall = null;
				onUpdateBigScore(null,score,array+1);
				$(selectSubjectDialog).dialog('close');
			}else{
				$.messager.alert('提示',result.message,'error');
			}
		});
	}
    //更新小题
    var onUpdateTempSmall = function(rowIndex, rowData){
		var url = 'paper/updateTempPaperBigSmall.do';
		var content ={'id.paperId':rowData.paperId,'id.bigId':rowData.bigId,
				'id.smallId':rowData.smallId,isOptionMix:rowData.isOptionMix,
				score:rowData.score,difficulty:rowData.difficulty,groupId:rowData.groupId};
		asyncCallService(url,content,function(result){
			if(!result.isSuccess){
				$.messager.alert('提示',result.message,'error');
			}
		});
    }
    
    //查看临时小题
    var onViewTempSmalls = function(bigId){
    	$(paperBigSmallList).datagrid('endEdit', lastIndexSmall);
		lastIndexSmall = null;
    	
    	var url = 'paper/viewTempPaperBigSmall.do';
    	var paperId = $('#paperId',editDialog).val();
		var content ={'id.paperId':paperId,'id.bigId':bigId};
		asyncCallService(url,content,function(result){
			if(result.isSuccess){
				var data = result.data;
				var tempPaperBigSmall = eval("("+data.tempPaperBigSmall+")");
				$(paperBigSmallList).datagrid('loadData',tempPaperBigSmall);
			}else{
				$.messager.alert('提示',result.message,'error');
			}
		});
    }
    //-----------------查看试卷----------------
    var viewDialog = $('#viewDialog_'+id,$this); 
	//编辑框
	$(viewDialog).dialog({  
	    title: '查看试卷',  
	    width:1000,
	    height:height,
	    closed: true,  
	    cache: false,  
	    modal: true,
	    closable:false,
	    maximizable:true,
	    resizable:true,
	    toolbar:[	
		 			{id:'addPaperBigSmall'+id,text:'打印预览',iconCls:'icon-print',handler:function(){onPrintPaper();}},'-',
		 			{text:'退出',iconCls:'icon-cancel',handler:function(){
		 					$(viewDialog).dialog('close');
		 				}
		 			}
		 	  ]
	});
	
	//查看试卷
	var onView = function(){
		if(selectRow==null){
			$.messager.alert("提示","请选择数据行","warning");
			return;
		}
		$(viewDialog).dialog({   
		    href: 'paper/getViewPanelPaper.do?paperId='+selectRow.paperId  
		});  
		$(viewDialog).dialog('open');
	 }
	var onPrintPaper = function(){
		window.open('paper/getViewHtmlPaper.do?paperId='+selectRow.paperId);
	}
	//检查查看试卷等按钮的权限
	var checkPaperBtn = function(){
		var selectRow  = $(viewList).datagrid('getSelected');
		var trueItem = $('#state_'+id).menu('findItem', '启用');
		var falseItem = $('#state_'+id).menu('findItem', '禁用');
		if(selectRow.state=='true'){
			$('#view_'+id,$this).linkbutton('enable');
			$('#viewStu_'+id,$this).linkbutton('enable');
			$('#state_'+id).menu('disableItem', trueItem.target);
			$('#state_'+id).menu('enableItem', falseItem.target);
		}else{
			$('#view_'+id,$this).linkbutton('disable');
			$('#viewStu_'+id,$this).linkbutton('disable');
			$('#state_'+id).menu('enableItem', trueItem.target);
			$('#state_'+id).menu('disableItem', falseItem.target);
		}
	}
	//查看学生端试卷
	var onStuView = function(){
		if(selectRow==null){
			$.messager.alert("提示","请选择数据行","warning");
			return;
		}
		window.open('paper/examView.do?paperId='+parseInt(selectRow.paperId),"_blank");
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
		var viewBtn = $('#view_'+id,$this);
		var viewStuBtn = $('#viewStu_'+id,$this);
		checkArray.push(addBtn);
		checkArray.push(updateBtn);
		checkArray.push(deleteBtn);
		checkArray.push(tbStateBtn);
		checkArray.push(viewBtn);
		checkArray.push(viewStuBtn);
		checkRight(checkArray,rights);
	}
	checkBtnRight();
	 $(this).mask('hide');
  }
})(jQuery);   