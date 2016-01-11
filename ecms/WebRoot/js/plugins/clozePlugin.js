// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.clozeInit = function() {
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
		  width:1008,
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
		title:'问题选择',
		singleSelect:true,
		method:"POST",
		nowrap:true,
		rownumbers:true,
		striped: true,
		collapsible:true,
		selectOnCheck:false,
		checkOnSelect:false,
		toolbar:[	
					{text:'添加',iconCls:'icon-add',handler:function(){onAddOption();}},'-',
					{text:'删除',iconCls:'icon-remove',handler:function(){onDeleteOption();}}
				],
		columns:[[
			  {field:'answer0',width:55,align:"center",title:'是否答案',  
	                formatter:function(value,row,index){ 
	                	if('A'==row.answer||'true'==value){
	                		row.answer0 ='true';
	                		row.answer = '';
	                		return '是';
	                	}else{
	                		return '否';
	                	}
	                	
	                },  editor:{type:'checkbox',options:{on:'true',off:''}}},
		      {field:'options0',width:30,title:'选项',align:"center",  
	                formatter:function(value,row,index){ 
	                	return 'A';
	                }},
		      {field:'option0',width:150,title:'内容',align:"center",editor:'text'},
		      {field:'answer1',width:55,align:"center",title:'是否答案',  
	                formatter:function(value,row,index){ 
	                	if('B'==row.answer||'true'==value){
	                		row.answer1 ='true';
	                		row.answer = '';
	                		return '是';
	                	}else{
	                		return '否';
	                	}
	                },  editor:{type:'checkbox',options:{on:'true',off:''}}},
		      {field:'options1',width:30,title:'选项',align:"center",  
	                formatter:function(value,row,index){ 
	                	return 'B';
	                }},
		      {field:'option1',width:150,title:'内容',align:"center",editor:'text'},
		      {field:'answer2',width:55,align:"center",title:'是否答案',
	                formatter:function(value,row,index){ 
	                	if('C'==row.answer||'true'==value){
	                		row.answer2 ='true';
	                		row.answer = '';
	                		return '是';
	                	}else{
	                		return '否';
	                	}
	                },  editor:{type:'checkbox',options:{on:'true',off:''}}},
		      {field:'options2',width:30,title:'选项',align:"center",  
	                formatter:function(value,row,index){ 
	                	return 'C';
	                }},
		      {field:'option2',width:150,title:'内容',align:"center",editor:'text'},
		      {field:'answer3',width:55,align:"center",title:'是否答案',
	                formatter:function(value,row,index){ 
	                	if('D'==row.answer||'true'==value){
	                		row.answer3 ='true';
	                		row.answer = '';
	                		return '是';
	                	}else{
	                		return '否';
	                	}
	                },  editor:{type:'checkbox',options:{on:'true',off:''}}},
		      {field:'options3',width:30,title:'选项',align:"center",  
	                formatter:function(value,row,index){ 
	                	return 'D';
	                }},
		      {field:'option3',width:150,title:'内容',align:"center",editor:'text'}
		]],
		onClickRow:function(rowIndex, rowData){
		  if (selectIndexOption != rowIndex){
				$(optionsDG).datagrid('endEdit', selectIndexOption);
				$(optionsDG).datagrid('beginEdit', rowIndex);
				setEditing(rowIndex);
			}
			selectRowOption = rowData;
			selectIndexOption = rowIndex;
		}
	  });
	  //联动改变总金额的值
		function setEditing(rowIndex){  
			
		    var answer0Editor = $(optionsDG).datagrid('getEditor', {index:rowIndex,field:'answer0'});
		    var answer1Editor = $(optionsDG).datagrid('getEditor', {index:rowIndex,field:'answer1'});
		    var answer2Editor = $(optionsDG).datagrid('getEditor', {index:rowIndex,field:'answer2'});
		    var answer3Editor = $(optionsDG).datagrid('getEditor', {index:rowIndex,field:'answer3'});
		    answer0Editor.target.bind('change', function(){ 
		    	$(answer1Editor.target).attr('checked',false);
		    	$(answer2Editor.target).attr('checked',false);
		    	$(answer3Editor.target).attr('checked',false);
		    });  
		    answer1Editor.target.bind('change', function(){  
		    	$(answer0Editor.target).attr('checked',false);
		    	$(answer2Editor.target).attr('checked',false);
		    	$(answer3Editor.target).attr('checked',false);
		    });
		    answer2Editor.target.bind('change', function(){  
		    	$(answer1Editor.target).attr('checked',false);
		    	$(answer0Editor.target).attr('checked',false);
		    	$(answer3Editor.target).attr('checked',false);
		    });  
		    answer3Editor.target.bind('change', function(){  
		    	$(answer1Editor.target).attr('checked',false);
		    	$(answer2Editor.target).attr('checked',false);
		    	$(answer0Editor.target).attr('checked',false);
		    });
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
	  $('#state_'+id).menu({  
		    onClick:function(item){
		    	if(item.name==1){
		    		onMulUpdateState(1);
		    	}else{
		    		onMulUpdateState(0);
		    	}
		    }
		    	
	  });
	//编辑框
	$(editDialog).dialog({  
	    title: '编辑试题',  
	    width:1024,
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
		  $('#subjectType',editForm).val('完型填空');
		  $('#difficulty',editForm).combobox('setValue','中');
		  $('#publishTeacher',editForm).val(ECMS.getTeacherName());
		  setContent(null);
		  $(optionsDG).datagrid('loadData',[{},{},{}]);
	  };
	  var onAddOption = function(){
		  var rows=$(optionsDG).datagrid('getRows');
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
		var optionsStr=new Array();
		var answerStr=new Array();
		for ( var i = 0; i < rows.length; i++){
			var count=0;
			var row=rows[i];
			if(row.option0==""||row.option0==null||row.option1==""||row.option1==null||row.option2==""||row.option2==null||row.option3==""||row.option3==null){
				$.messager.alert('提示','第'+(i+1)+'行有选项没有填写内容','error');
				return false;
			}
			if(row.answer0=='true'){
				answerStr.push("A");
				count+=1;
			}
			if(row.answer1=='true'){
				answerStr.push("B");
				count+=1;
			}
			if(row.answer2=='true'){
				answerStr.push("C");
				count+=1;
			}
			if(row.answer3=='true'){
				answerStr.push("D");
				count+=1;
			}
			if(count==0){
				$.messager.alert('提示','第'+(i+1)+'行选项没有选择正确答案','error');
				return false;
			}
			if(count!=1){
				$.messager.alert('提示','第'+(i+1)+'行选项只能选择正确一个答案','error');
				return false;
			}
			optionsStr.push(row.option0+"!@#"+row.option1+"!@#"+row.option2+"!@#"+row.option3);
		}
		$('#optionsStr',editForm).val(optionsStr.join(CSIT.join));
		$('#answer',editForm).val(answerStr.join(CSIT.join));
		$('#descriptPlain',editForm).val(getText());
		return true;
	};
	//保存
	var onSave = function(){
		$(editForm).form('submit',{
			url:'subject/saveSubject.do',
			onSubmit: function(){
				return setValue();
			},
			success: function(data){
				var result = eval('('+data+')');
				if(result.isSuccess){
					var fn = function(){
						var subjectId = $.trim($('#subjectId',editForm).val());
						//新增
						if(subjectId==''){
							search();
						}else{
							var row = $(editForm).serializeObject();
							$(viewList).datagrid('updateRow',{index:selectIndex,row:row});
						}
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
		$(optionsDG).datagrid({
			url:"subject/querySubjectSub.do",
			queryParams:{'subject.subjectId':selectRow.subjectId}
		});
	};
	
	//修改
	var onUpdate = function(){
		if(!$('#update_'+id,$this).is(":hidden")){
			if(selectRow==null){
				$.messager.alert("提示","请选择数据行","warning");
				return;
			}
			$(editForm).form('clear');
			$(editDialog).mask({maskMsg:'正在加载界面'});
			onOpen();
			$(editForm).form('load',selectRow);
			$(editDialog).mask('hide');
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
				subjectType:'完型填空',status:status,difficulty:difficulty};
		
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
		
		checkArray.push(addBtn);
		checkArray.push(updateBtn);
		checkArray.push(deleteBtn);
		checkArray.push(tbStateBtn);
		
		checkRight(checkArray,rights);
	};
	checkBtnRight();
  };
})(jQuery);   