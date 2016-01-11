// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.arrangeInit = function() {
	  var $this = $(this);
	  var height = $(document.body).height();
	  var id = $(this).attr('id');
	  var rightId = $(this).attr('rightId');
	  var width = $(document.body).width();
	  var height = $(document.body).height();
	  var selectRow = null;
	  var selectIndex = null;
	  var editDialog = $('#editDialog_'+id,$this);
	  var editForm = $('#editForm_'+id,editDialog);
	  var viewList =  $('#viewList_'+id,$this);
	  var queryContent = $('#queryContent_'+id,$this);
	  var selectRowPaper = null;
	  var selectIndexPaper = null;
	  var paperDialog = $('#paperDialog_'+id,$this);
	  var paperList =  $('#paperList',paperDialog);
	  var queryContentPaper = $('#queryContentPaper_'+id,$this);
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
		      {field:'arrangeId',title:'编号',width:50,align:"center"},
		      {field:'examType',title:'考试类型',width:100,align:"center"},
		      {field:'resultType',title:'结果类型',width:100,align:"center"},
		      {field:'paperName',title:'试卷',width:150,align:"center"},
		      {field:'beginDateTime',title:'开考时间',width:150,align:"center"},
		      {field:'endDateTime',title:'截止时间',width:150,align:"center"},
		      {field:'roomName',title:'考场',width:80,align:"center"},
		      {field:'monitorName',title:'监考老师',width:80,align:"center"},
		      {field:'correctName',title:'改卷老师',width:80,align:"center"},
		      {field:'note',title:'备注',width:100,align:"center"},
	          {field:'status',title:'状态',width:50,sortable:true,align:"center",
					formatter: function(value,row,index){
						if (value==0){
							return '未确认';
						} else if (value==1){
							return '已确认';
						} else if (value==2){
							return '已改卷';
						} else if (value==3){
							return '完成改卷';
						} else if (value==4){
							return '提交完成改卷';
						} else if (value==5){
							return '归档';
						}
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
	  //编辑框
	$(editDialog).dialog({  
	    title: '编辑考务',  
	    width:915,
	    height:height-3,
	    closed: true,  
	    cache: false,  
	    modal: true,
	    closable:false,
	    toolbar:[{
	    	id:'saveArrangeBtn',
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
				var url = "arrange/clearTempArrange.do";
				  var result = syncCallService(url,{arrangeId:selectRow.arrangeId});
				  if(result.isSuccess){
					  
				  }else{
					  $.messager.alert('提示',result.message,'error');
				  }
			}
		}]
	});
	 //编辑框
	$(paperDialog).dialog({  
	    title: '选择试卷',  
	    width:1000,
	    height:height-5,
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
			text:'查看试卷',
			iconCls:'icon-view',
			handler:function(){
				onPaperView();
			}
		},'-',{
			text:'退出',
			iconCls:'icon-cancel',
			handler:function(){
				$(paperDialog).dialog('close');
			}
		}]
	});
	//试卷列表列表
	  $(paperList).datagrid({
		singleSelect:true,
		method:"POST",
		nowrap:true,
		striped: true,
		collapsible:true,
		pagination:true,
		rownumbers:true,
		selectOnCheck:false,
		checkOnSelect:false,
		fit:true,
		columns:[[
			  {field:'paperId',title:'编号',width:50,align:"center"},
		      {field:'lessonName',title:'课程',width:100,align:"center"},
		      {field:'teacherName',title:'出题老师',width:80,align:"center"},
		      {field:'paperType',title:'试卷类型',width:100,align:"center"},
		      {field:'paperName',title:'试卷名称',width:250,align:"center"},
		      {field:'limits',title:'限时（分钟）',width:80,align:"center",  
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
	  //-----------------查看试卷----------------
    var paperViewDialog = $('#paperViewDialog_'+id,$this); 
	//编辑框
	$(paperViewDialog).dialog({  
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
		 					$(paperViewDialog).dialog('close');
		 				}
		 			}
		 	  ]
	});
	//查看试卷
	var onPaperView = function(){
		if(selectRowPaper==null){
			$.messager.alert("提示","请选择数据行","warning");
			return;
		}
		$(paperViewDialog).dialog({   
		    href: 'paper/getViewPanelPaper.do?paperId='+selectRowPaper.paperId  
		});  
		$(paperViewDialog).dialog('open');
	 }
	var onPrintPaper = function(){
		window.open('paper/getViewHtmlPaper.do?paperId='+selectRowPaper.paperId);
	}
	  //------------------考生信息-------------------
	var stuList = $('#stuList',editDialog);
	var selectRowStu = null;
	var selectIndexStu = null;
	var stuDialog = $('#stuDialog_'+id,$this);
	var unCheckList = $('#unCheckList',stuDialog);
	var queryContentStu = $('#queryContentStu_'+id,$this);
	//年级
	  $('#gradeSearch',queryContentStu).combobox({
		  width:120,
		  data:EXAM.getGradeList(),
		  valueField:'dataDictionaryId',
		  textField:'dataDictionaryName'
	  });
	  //专业
	  $('#majorSearch',queryContentStu).combobox({
		  width:170,
		  data:EXAM.getMajorList(),
		  valueField:'dataDictionaryId',
		  textField:'dataDictionaryName'
	  });
	  //班级
	  $('#clazzSearch',queryContentStu).combobox({
		  width:120,
		  data:EXAM.getClassList(),
		  valueField:'dataDictionaryId',
		  textField:'dataDictionaryName'
	  });
	//加载已选考生列表
	$(stuList).datagrid({
		title:'考生信息',
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
		toolbar:[	
					{id:'addStu_'+id,text:'添加',iconCls:'icon-add',handler:function(){onAddStu()}},'-',
					{id:'deleteStu_'+id,text:'删除',iconCls:'icon-remove',handler:function(){onDeleteStu()}}
				],
		columns:[[
			  {field:'ck',checkbox:true},
			  {field:'studentId',hidden:true},
			  {field:'studentCode',title:'学号',width:150,align:"center"},
		      {field:'studentName',title:'姓名',width:100,align:"center"},
		      {field:'grade',title:'年级',width:100,align:"center"},
		      {field:'major',title:'专业',width:100,align:"center"},
		      {field:'clazz',title:'班级',width:100,align:"center"}
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
	 //编辑框
	$(stuDialog).dialog({  
	    title: '选择考生',  
	    width:900,
	    height:height-5,
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
				var arrangeId=$('#arrangeId',editForm).val();
				$(stuList).datagrid({
						url:"arrange/queryTempArrangeStudent.do",
						queryParams:{arrangeId:arrangeId}
					});
			}
		}]
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
				{field:'studentCode',title:'学号',width:150,align:"center"},
				{field:'studentName',title:'姓名',width:100,align:"center"},
				{field:'grade',title:'年级',width:100,align:"center"},
				{field:'major',title:'专业',width:200,align:"center"},
				{field:'clazz',title:'班级',width:100,align:"center"},
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
	  var onAddStu = function(){
		  $('#gradeSearch',stuDialog).combobox('setValue','');
		  $('#majorSearch',stuDialog).combobox('setValue','');
		  $('#clazzSearch',stuDialog).combobox('setValue','');
		  $('#codeSearch',stuDialog).attr('value','');
		  $(unCheckList).datagrid('loadData', { total: 0, rows: [] });
		  $(stuDialog).dialog('open');
	  }
	  var onDeleteStu = function(){
		  var rows=$(stuList).datagrid('getSelections');
		  if(rows.length==0){
			  $.messager.alert("提示","请选择考生","warning");
			  return;
		  }
		  var idArray = new Array();
		  for(var i=0;i<rows.length;i++){
			  idArray.push(rows[i].studentId);
		  }
		  var ids = idArray.join(CSIT.join);
		  var arrangeId=$('#arrangeId',editForm).val();
		  var url = "arrange/deleteTempArrangeStudent.do";
		  var content = {ids:ids,arrangeId:arrangeId};
		  $.post(url,content,
			  function(result){
				  if(result.isSuccess){
					  //$.messager.alert('提示','添加成功',"info");
					  //searchStu();
					  var arrangeId=$('#arrangeId',editForm).val();
					  $(stuList).datagrid({
							url:"arrange/queryTempArrangeStudent.do",
							queryParams:{arrangeId:arrangeId}
						});
					  
				  }else{
					  $.messager.alert('提示',result.message,"error");
				  }
			  }, "json");
	  }
	  
	  //查询学生
		var searchStu = function(flag){
			var arrangeId=$('#arrangeId',editForm).val();
			//年级
			var gradeId = $.trim($('#gradeSearch',queryContentStu).combobox('getValue'));
			if(gradeId==''){
				gradeId = -1;
			}
			//专业
			var majorId = $.trim($('#majorSearch',queryContentStu).combobox('getValue'));
			if(majorId==''){
				majorId = -1;
			}
			//班级
			var clazzId = $.trim($('#clazzSearch',queryContentStu).combobox('getValue'));
			if(clazzId==''){
				clazzId = -1;
			}
			var studentCode = $('#codeSearch',queryContentStu).val();
			//取得列表信息
			var content = {'grade.dataDictionaryId':gradeId,'major.dataDictionaryId':majorId,'clazz.dataDictionaryId':clazzId,
					state:true,arrangeId:arrangeId,studentCode:studentCode};
			$(unCheckList).datagrid({
				url:'dict/queryArrangeStudent.do',
				queryParams:content
			});
		};
		//查询试卷
		$('#searchStu',stuDialog).click(function(){
			searchStu();
		});
		//选择考生到 临时表
	  var onSelectStu = function(){
		  var rows=$(unCheckList).datagrid('getSelections');
		  if(rows.length==0){
			  $.messager.alert("提示","请选择考生","warning");
			  return;
		  }
		  var idArray = new Array();
		  for(var i=0;i<rows.length;i++){
			  idArray.push(rows[i].studentId);
		  }
		  var ids = idArray.join(CSIT.join);
		  var arrangeId=$('#arrangeId',editForm).val();
		  var url = "arrange/saveTempArrangeStudent.do";
		  var content = {ids:ids,arrangeId:arrangeId};
		  $.post(url,content,
			  function(result){
				  if(result.isSuccess){
					  $.messager.alert('提示','添加成功',"info");
					  searchStu();
					  
				  }else{
					  $.messager.alert('提示',result.message,"error");
				  }
			  }, "json");
	  }
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
	  $('#status_'+id).menu({  
		    onClick:function(item){
		    	if(item.name=='true'){
		    		onUpdateStatus(1);
		    	}else{
		    		onUpdateStatus(0);
		    	}
		    }
	  });
	  //添加试卷
	  $('#paperAdd',editForm).click(function(){
		  $('#lessonSearch',paperDialog).combobox('setValue','');
		  $('#teacherSearch',paperDialog).combobox('setValue','');
		  $(paperList).datagrid('loadData', { total: 0, rows: [] });
		  $(paperDialog).dialog('open');
	  });
	  //添加试卷
	  $('#paperView',editForm).click(function(){
		  if($('#paper',editForm).val()==null||$('#paper',editForm).val()==''){
			  $.messager.alert("提示","请选择试卷","warning");
			  return;
		  }
		  $(paperViewDialog).dialog({   
		    href: 'paper/getViewPanelPaper.do?paperId='+$('#paper',editForm).val() 
		});  
		$(paperViewDialog).dialog('open');
	  });
	  $('#teacherSearch',queryContentPaper).combobox({
		  width:150,
		  data:EXAM.getTeacherList(),
		  valueField:'teacherId',
		  textField:'teacherName'
	  });
	  $('#lessonSearch',queryContentPaper).combobox({
		  width:150,
		  data:EXAM.getLessonList() ,
		  valueField:'dataDictionaryId',
		  textField:'dataDictionaryName'
	  });
	  //查询试卷
		var searchPaper = function(flag){
			var lessonId = $('#lessonSearch',queryContentPaper).combobox('getValue');
			var teacherId = $('#teacherSearch',queryContentPaper).combobox('getValue');
			var content = {'lesson.dataDictionaryId':lessonId,'publishTeacher.teacherId':teacherId,state:true};
			$(paperList).datagrid({
				url:"paper/queryPaper.do",
				queryParams:content
			});
		};
		//查询试卷
		$('#searchPaper',paperDialog).click(function(){
			searchPaper();
		});
		var onSelectPaper = function(){
			if(selectRowPaper==null){
				$.messager.alert("提示","请选择数据行","warning");
				return;
			}
			$('#paperName',editForm).val(selectRowPaper.paperName+'——'+selectRowPaper.limits/60+'分钟');
			$('#paper',editForm).val(selectRowPaper.paperId);
			$(paperDialog).dialog('close');
		}
	  var init = function(){
		  $('#examType',editForm).combobox('setValue','指定时间点开考');
		  $('#resultType',editForm).combobox('setValue','考完立即出成绩');
		  
		  $('#correct',editForm).combobox({
			  data:EXAM.getTeacherList(),
			  valueField:'teacherId',
			  textField:'teacherName'
		  });
		  $('#monitor',editForm).combobox({
			  data:EXAM.getTeacherList(),
			  valueField:'teacherId',
			  textField:'teacherName'
		  });
		  $('#room',editForm).combobox({
			  data:EXAM.getRoomList(),
			  valueField:'dataDictionaryId',
		  	  textField:'dataDictionaryName'
		  });
		  var url = "arrange/initArrange.do";
		  var result = syncCallService(url,{type:0});
		  if(result.isSuccess){
			  var data = result.data;
			  $('#arrangeId',editForm).val(data.arrangeId);
		  }else{
			  $.messager.alert('提示',result.message,'error');
		  }
	  };
	//添加
	var onAdd = function(){
		$(editForm).form('clear');
		$(viewList).datagrid('unselectAll');
		$(stuList).datagrid('loadData', { total: 0, rows: [] });
		selectIndex = null;
		selectRow = null;
		$('#addStu_'+id).linkbutton('enable');
		$('#deleteStu_'+id).linkbutton('enable');
		$('#saveArrangeBtn',editDialog).linkbutton('enable');
		init();
		$(editDialog).dialog('open');
	};
	//保存前的赋值操作
	var setValue = function(){
		
		var examType = $('#examType',editForm).combobox('getValue');
		if(''==examType){
			$.messager.alert('提示','请选择考试类型','warning');
			return false;
		}
		var resultType = $('#resultType',editForm).combobox('getValue');
		if(''==resultType){
			$.messager.alert('提示','请选择答卷类型','warning');
			return false;
		}
		var correct = $('#correct',editForm).combobox('getValue');
		if(''==correct){
			$.messager.alert('提示','请选择改卷老师','warning');
			return false;
		}
		var paper=$('#paper',editForm).val();
		if(''==paper){
			$.messager.alert('提示','请选择试卷','warning');
			return false;
		}
		var monitor = $('#monitor',editForm).combobox('getValue');
		if(''==monitor){
			$.messager.alert('提示','请选择监考老师','warning');
			return false;
		}
		var beginDateTime = $('#beginDateTime',editForm).val();
		if(''==beginDateTime){
			$.messager.alert('提示','请填写开考时间','warning');
			return false;
		}
		var endDateTime = $('#endDateTime',editForm).val();
		if(''==endDateTime){
			$.messager.alert('提示','请填写截止时间','warning');
			return false;
		}
		var room = $('#room',editForm).combobox('getValue');
		if(''==room){
			$.messager.alert('提示','请选择考场','warning');
			return false;
		}
		var rows=$(stuList).datagrid('getRows');
		if(rows.length==0){
			$.messager.alert('提示','请选择考生','warning');
			return false;
		}
		return true;
	};
	//保存
	var onSave = function(){
		$(editForm).form('submit',{
			url:'arrange/saveArrange.do',
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
	};
	var onOpen = function(){
		$('#correct',editForm).combobox({
			  data:EXAM.getTeacherList(),
			  valueField:'teacherId',
			  textField:'teacherName'
		 });
		 $('#monitor',editForm).combobox({
			  data:EXAM.getTeacherList(),
			  valueField:'teacherId',
			  textField:'teacherName'
		 });
		 $('#room',editForm).combobox({
			  data:EXAM.getRoomList(),
			  valueField:'dataDictionaryId',
		  	  textField:'dataDictionaryName'
		 });
		 $('#correct',editForm).combobox('setValue',selectRow.correctId);
		 $('#monitor',editForm).combobox('setValue',selectRow.monitorId);
		 $('#room',editForm).combobox('setValue',selectRow.roomId);
		 $('#paper',editForm).val(selectRow.paperId);
		 var url = "arrange/initArrange.do";
		  var result = syncCallService(url,{arrangeId:selectRow.arrangeId,type:1});
		  if(result.isSuccess){
			  $(stuList).datagrid({
				  url:"arrange/queryTempArrangeStudent.do",
			      queryParams:{arrangeId:selectRow.arrangeId}
			  });
		  }else{
			  $.messager.alert('提示',result.message,'error');
		  }
		 
	};
	
	//修改
	var onUpdate = function(){
		if(!$('#update_'+id,$this).is(":hidden")){
			if(selectRow==null){
				$.messager.alert("提示","请选择数据行","warning");
				return;
			}
			$(editForm).form('clear');
			onOpen();
			$(editForm).form('load',selectRow);
			$('#paperName',editForm).val(selectRow.paperName+'——'+selectRow.limits/60+'分钟');
			if(selectRow.status==0){
				$('#addStu_'+id).linkbutton('enable');
				$('#deleteStu_'+id).linkbutton('enable');
				$('#saveArrangeBtn',editDialog).linkbutton('enable');
			}else if(selectRow.status!=0){
				$('#addStu_'+id).linkbutton('disable');
				$('#deleteStu_'+id).linkbutton('disable');
				$('#saveArrangeBtn',editDialog).linkbutton('disable');
			}
			$(editDialog).dialog('open');
		}
	 };
	//查询
	var search = function(flag){
		var examType = $('#examTypeSearch',queryContent).combobox('getValue');
		var resultType = $('#resultTypeSearch',queryContent).combobox('getValue');
		var content = {'examType':examType,'resultType':resultType};
		
		$(viewList).datagrid({
			url:"arrange/queryArrange.do",
			queryParams:content
		});
		checkBtnRight();
	};
	//查询
	$('#search',$this).click(function(){
		search();
	});
	//批量删除
	var onMulDelete = function(){
		var rows = $(viewList).datagrid('getSelections');
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
				idArray.push(rows[i].arrangeId);
			}
			var ids = idArray.join(CSIT.join);
			var url = "arrange/mulDeleteArrange.do";
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
	//修改状态
	var onUpdateStatus = function(status){
		var selectRow =  $(viewList).datagrid('getSelected');
		var msg = '';
		if(status==1){
			msg = '确认';
		}else{
			msg = '未确认';
		}
		if(selectRow == null){
			$.messager.alert("提示","请选择要"+msg+"的数据行","warning");
			return;
		}
		var confirmMsg = "确定要"+msg+"记录?";
		if(status==1){
			confirmMsg += '确认后会生成考生答卷？';
		}else{
			confirmMsg +='未确认后会删除已生成的考生答卷？';
		}
		
		$.messager.confirm("提示",confirmMsg,function(t){ 
			if(t){
				$($this).mask({maskMsg:'正在修改状态'});
				var url = 'arrange/updateStatusArrange.do';
				var content ={arrangeId:selectRow.arrangeId,status:status};
				asyncCallService(url,content,function(result){
					if(result.isSuccess){
						var fn = function(){
							search(true);
						};
						$.messager.alert('提示',msg+'成功','info',fn);
					}else{
						$.messager.alert('提示',result.message,'error');
					}
					$($this).mask('hide');
				});
			}
		});
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
		var tbStatusBtn = $('#tbStatus_'+id,$this);
		var dataDictionaryRightBtn = $('#dataDictionaryRight_'+id,$this);
		
		checkArray.push(addBtn);
		checkArray.push(updateBtn);
		checkArray.push(deleteBtn);
		checkArray.push(tbStatusBtn);
		checkArray.push(dataDictionaryRightBtn);
		
		checkRight(checkArray,rights);
	};
	checkBtnRight();
  };
})(jQuery);   