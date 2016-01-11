// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.fillInit = function() {
	  var $this = $(this);
	  var height = $(document.body).height();
	  var id = $(this).attr('id');
	  var rightId = $(this).attr('rightId');
	  var width = $(document.body).width();
	  var height = $(document.body).height();
	  var selectRow = null;
	  var selectIndex = null;
	  var selectRowOption = null;
	  var selectIndexOption = null;
	  var editDialog = $('#editDialog_'+id,$this);
	  var editForm = $('#editForm_'+id,editDialog);
	  var optionsDG = $('#optionsDG',editForm);
	  var viewList =  $('#viewList_'+id,$this);
	  var queryContent = $('#queryContent_'+id,$this);
	  var descript = CKEDITOR.replace('descript',{
		  toolbar:'Paper',
		  width:899,
		  height:93
	  });
	  CKFinder.setupCKEditor(descript,'ckfinder/') ;
	  
	  var getText = function(){		//获得编辑器中的纯文本
		  return CKEDITOR.instances.descript.document.getBody().getText();
	  };
	  
	  var getContent = function(){
	     return CKEDITOR.instances.descript.getData();    //获取编辑器的值
	  };
	  var setContent = function(data){
		CKEDITOR.instances.descript.setData(data);	//赋值给编辑器
	  };
	  $('#teacherSearch',queryContent).combobox({
		  width:150,
		  data:ECMS.getTeacherList(),
		  valueField:'teacherId',
		  textField:'teacherName'
	  });
	  $('#groupSearch',queryContent).combobox({
		  width:150,
		  data:ECMS.getGroupList(),
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
		selectOnCheck:false,
		checkOnSelect:false,
		fit:true,
		toolbar:'#tb_'+id,
		columns:[[
			      {field:'ck',checkbox:true},
			      {field:'subjectId',title:'编号',width:50,align:"center"},
			      {field:'groupName',title:'参赛组别',width:80,align:"center"},
			      {field:'subjectType',title:'题型',width:80,align:"center"},
			      {field:'descript',title:'试题描述',width:250,align:"center"},
			      {field:'difficulty',title:'难度',width:50,align:"center"},
			      {field:'score',title:'默认分值',width:60,align:"center"},
			      {field:'publishDate',title:'出题时间',width:150,align:"center"},
			      {field:'publishTeacherName',title:'出题教师',width:100,align:"center"},
		          {field:'status',title:'状态',width:50,sortable:true,align:"center",
						formatter: function(value,row,index){
							if (value==0){
								return '<img src="style/v1/icons/warn.png"/>';
							} else if (value==1){
								return '<img src="style/v1/icons/info.png"/>';
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
	  //加载列表
	  $(optionsDG).datagrid({
		fit:true,
		title:'填空选项',
		width:900,
		height:205,
		singleSelect:true,
		method:"POST",
		nowrap:true,
		striped: true,
		collapsible:true,
		rownumbers:true,
		toolbar:[	
					{text:'添加',iconCls:'icon-add',handler:function(){onAddOption();}},'-',
					{text:'删除',iconCls:'icon-remove',handler:function(){onDeleteOption();}}
				],
		columns:[[
		      {field:'options',title:'填空选项（每个空若有多个答案，请用“/”隔开）',width:850,align:"left",
					editor:{
						type:'text'	
					}
		      }
		]],
		onClickRow:function(rowIndex, rowData){
		  if (selectIndexOption != rowIndex){
				$(this).datagrid('endEdit', selectIndexOption);
				$(this).datagrid('beginEdit', rowIndex);
			}
			selectRowOption = rowData;
			selectIndexOption = rowIndex;
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
		    	}else if(item.name==1){
		    		onMulUpdateState(1);
		    	}
		    }
		    	
	  });
	  
	//编辑框
	$(editDialog).dialog({  
	    title: '编辑试题',  
	    width:915,
	    height:height-3,
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
		  $('#group',editForm).combobox({
			  width:200,
			  data:ECMS.getGroupList(),
			  valueField:'groupId',
			  textField:'groupName'
		  });
		  
		  $('#publishDate',editForm).val(nowDate33());
		  
		  $('#subjectType',editForm).val('填空');
		  
		  $('#difficulty',editForm).combobox('setValue','中');
		  
		  $('#publishTeacher',editForm).val(ECMS.getTeacherName());
		  
		  setContent(null);
		  
		  $(optionsDG).datagrid('loadData',[]);
	  };
	  
	  var onAddOption = function(){
		  $(optionsDG).datagrid('appendRow',{});
	  };
	  var onDeleteOption = function(){
		  $(optionsDG).datagrid('deleteRow',selectIndexOption);
	  };
	  
	//添加
	var onAdd = function(){
		$(editForm).form('clear');
		init();
		$(editDialog).dialog('open');
	};
	//保存前的赋值操作
	var setValue = function(){
		var group = $('#group',editForm).combobox('getValue');
		if(''==group){
			$.messager.alert('提示','请选择参赛组别','warning');
			return false;
		}
		var score = $.trim($('#score',editForm).val());
		if(''==score){
			$.messager.alert('提示','请填写默认分值','warning');
			return false;
		}
		var publishDate = $.trim($('#publishDate',editForm).val());
		if(''==publishDate){
			$.messager.alert('提示','请填写出题时间','warning');
			return false;
		}
		var descript = getContent();
		if(''==descript){
			$.messager.alert('提示','请填写试题描述','warning');
			return false;
		}

		$(optionsDG).datagrid('endEdit', selectIndexOption);
		$(optionsDG).datagrid('unselectAll');
		selectIndexOption=null;
		var rows=$(optionsDG).datagrid('getRows');
		if(rows.length==0){
			$.messager.alert('提示','请添加选项','error');
			return false;
		}
		var optionsStr="";
		for ( var i = 0; i < rows.length; i++){
			var row=rows[i];
			if(row.options==""||row.options==null){
				$.messager.alert('提示','第'+(i+1)+'行选项没有填写内容','error');
				return false;
			}
			optionsStr=optionsStr+row.options+CSIT.join;
		}
		$('#optionsStr',editForm).val(optionsStr);
		
		$('#descriptPlain',editForm).val(getText());
		
		return true;
	};
	//保存
	var onSave = function(){
		$(optionsDG).datagrid('endEdit', selectIndexOption);
		$(editForm).form('submit',{
			url:'subject/saveSubject.do',
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
		$('#group',editForm).combobox({
			  width:200,
			  data:ECMS.getGroupList(),
			  valueField:'groupId',
			  textField:'groupName'
		  });
		$('#group',editForm).combobox('setValue',selectRow.groupId);
		
		$('#publishTeacher',editForm).val(selectRow.teacherName);
		
		setContent(selectRow.descript);
		
		$(optionsDG).datagrid('loadData', { total: 0, rows: [] }); 
		for(var i=0;i<selectRow.optionCount;i++){
			switch (i) {
				case 0:
					var str = selectRow.option0.replace(/\!@#/g,'/');
					str = str.substring(1,str.length-1);
					$(optionsDG).datagrid('appendRow',{
						options:str
					 });
					break;
				case 1:
					var str = selectRow.option1.replace(/\!@#/g,'/');
					str = str.substring(1,str.length-1);
					$(optionsDG).datagrid('appendRow',{
						options:str
					 });
					break;
				case 2:
					var str = selectRow.option2.replace(/\!@#/g,'/');
					str = str.substring(1,str.length-1);
					$(optionsDG).datagrid('appendRow',{
						options:str
					 });
					break;
				case 3:
					var str = selectRow.option3.replace(/\!@#/g,'/');
					str = str.substring(1,str.length-1);
					$(optionsDG).datagrid('appendRow',{
						options:str
					 });
					break;
				case 4:
					var str = selectRow.option4.replace(/\!@#/g,'/');
					str = str.substring(1,str.length-1);
					$(optionsDG).datagrid('appendRow',{
						options:str
					 });
					break;
				case 5:
					var str = selectRow.option5.replace(/\!@#/g,'/');
					str = str.substring(1,str.length-1);
					$(optionsDG).datagrid('appendRow',{
						options:str
					 });
					break;
				case 6:
					var str = selectRow.option6.replace(/\!@#/g,'/');
					str = str.substring(1,str.length-1);
					$(optionsDG).datagrid('appendRow',{
						options:str
					 });
					break;
				case 7:
					var str = selectRow.option7.replace(/\!@#/g,'/');
					str = str.substring(1,str.length-1);
					$(optionsDG).datagrid('appendRow',{
						options:str
					 });
					break;
				case 8:
					var str = selectRow.option8.replace(/\!@#/g,'/');
					str = str.substring(1,str.length-1);
					$(optionsDG).datagrid('appendRow',{
						options:str
					 });
					break;
				case 9:
					var str = selectRow.option9.replace(/\!@#/g,'/');
					str = str.substring(1,str.length-1);
					$(optionsDG).datagrid('appendRow',{
						options:str
					 });
					break;
				default:
					break;
			}
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
			$(editDialog).dialog('open');
		}
	 };
	//查询
	var search = function(flag){
		var groupId = $('#groupSearch',queryContent).combobox('getValue');
		var teacherId = $('#teacherSearch',queryContent).combobox('getValue');
		var status = $('#statusSearch',queryContent).combobox('getValue');
		var difficulty = $('#difficultySearch',queryContent).combobox('getValue');
		var content = {'group.groupId':groupId,'publishTeacher.teacherId':teacherId,
				subjectType:'填空',status:status,difficulty:difficulty};
		
		$(viewList).datagrid({
			url:"subject/querySubject.do",
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
				idArray.push(rows[i].subjectId);
			}
			var ids = idArray.join(CSIT.join);
			var url = "subject/mulDeleteSubject.do";
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
	//批量修改状态
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
			idArray.push(rows[i].subjectId);
		}
		$.messager.confirm("提示","确定要"+msg+"记录?",function(t){ 
			if(t){
				var url = 'subject/mulUpdateStateSubject.do';
				var content ={ids:idArray.join(CSIT.join),status:status};
				asyncCallService(url,content,function(result){
					if(result.isSuccess){
						var fn = function(){
							search(true);
						};
						$.messager.alert('提示',msg+'成功','info',fn);
					}else{
						$.messager.alert('提示',result.message,'error');
					}
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
		var tbStateBtn = $('#tbState_'+id,$this);
		var dataDictionaryRightBtn = $('#dataDictionaryRight_'+id,$this);
		
		checkArray.push(addBtn);
		checkArray.push(updateBtn);
		checkArray.push(deleteBtn);
		checkArray.push(tbStateBtn);
		checkArray.push(dataDictionaryRightBtn);
		
		checkRight(checkArray,rights);
	};
	checkBtnRight();
  };
})(jQuery);   