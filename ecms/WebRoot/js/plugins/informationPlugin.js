(function($) {  
  // 插件的定义  
  $.fn.informationInit = function() {
	  var $this = $(this);
	  var id = $(this).attr('id');
	  var rightId = $(this).attr('rightId');
	  var selectRow = null;
	  var width = $(document.body).width();
	  var height = $(document.body).height();
	
	  var editDialog = $('#editDialog_'+id,$this);
	  var editForm = $('#editForm_'+id,editDialog);
	  var viewList =  $('#viewList_'+id,$this);
	  var queryContent = $('#queryContent_'+id,$this);
	  
	  $('#categorySearch',$this).combobox({
		  width:150,
		  data:ECMS.getCategoryList(),
		  valueField:'category',
		  textField:'category'
	  });
	  
  	  var editor = CKEDITOR.replace( 'content' ,{ 
		  height:220,
		  width:627,
		  toolbar:[
				    { name: 'document', items : ['Preview','Print','SpellChecker', 'Scayt','Maximize'] },
				    { name: 'clipboard', items : [ 'Cut','Copy','Paste','PasteText','PasteFromWord','-','Undo','Redo' ] },
				    { name: 'insert', items : [ 'Image','Flash','Table','HorizontalRule','SpecialChar','Link','Unlink'] },
				    '/',
				    { name: 'basicstyles', items : [ 'Bold','Italic','Underline','Strike','Subscript','Superscript','-','RemoveFormat' ] },
				    { name: 'paragraph', items : [ 'NumberedList','BulletedList','-','Outdent','Indent','-',
				    '-','JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'] },
				    '/',
				    { name: 'styles', items : [ 'Styles','Format','Font','FontSize' ] },
				    { name: 'colors', items : [ 'TextColor','BGColor' ] }
				]
	  });
  	 CKFinder.setupCKEditor( editor, 'ckfinder/' ) ;
	  var getText = function(){		//获得编辑器中的纯文本
		  return CKEDITOR.instances.content.document.getBody().getText();
	  };
	  
	  var getContent = function(){
	     return CKEDITOR.instances.content.getData();    //获取编辑器的值
	  };
	  var setContent = function(data){
		CKEDITOR.instances.content.setData(data);	//赋值给编辑器
	  };
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
			{field:'informationTitle',title:'标题',width:300,align:"center"},
			{field:'category',title:'资讯类型',width:100,align:"center"},
			{field:'competitionName',title:'赛事',width:150,align:"center"}
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
	//上移
	  $('#moveUp_'+id,$this).click(function(){
		  onMove(-1);
	  });
	  //下移
	  $('#moveDown_'+id,$this).click(function(){
		  onMove(1);
	  });
	//编辑框
	$(editDialog).dialog({ 
		title:'编辑资讯',
		width:790,
		height:560,
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
			setContent('');
		}
	}); 
	var initCombobox = function(){
		 $('#category',editDialog).combobox({
			  width:250,
			  data:ECMS.getCategoryList(),
			  valueField:'category',
			  textField:'category'
		 });
		 $('#competition',editDialog).combobox({
			  width:250,
			  data:ECMS.getCompetitionList(),
			  valueField:'competitionId',
			  textField:'competitionName'
		  });
	}
	//添加
	var onAdd = function(){
		$(editForm).form('clear');
		initCombobox();
		var defaultInformationId = $('#competitionSearchMain').combobox('getValue');
		$('#competition',editDialog).combobox('setValue',defaultInformationId);
		$('#status',editDialog).combobox('setValue',1);
		$('#teacherName',editDialog).val(ECMS.getTeacherName());
		$(editDialog).dialog('open');
	}
	//保存前的赋值操作
	var setValue = function(){
		var informationTitle = $.trim($('#informationTitle',editForm).val());
		if(''==informationTitle){
			$.messager.alert('提示','请填写标题','warning');
			return false;
		}
		var category = $.trim($('#category',editForm).combobox('getValue'));
		if(''==category){
			$.messager.alert('提示','请填写资讯类型','warning');
			return false;
		}
		
		var competition = $.trim($('#competition',editForm).combobox('getValue'));
		if(''==competition){
			$.messager.alert('提示','请选择赛事','warning');
			return false;
		}
		var status = $.trim($('#status',editForm).combobox('getValue'));
		if(''==status){
			$.messager.alert('提示','请选择状态','warning');
			return false;
		}
		var content = getContent();
		if(''==content){
			$.messager.alert('提示','请填写资讯内容','warning');
			return false;
		}
		return true;
	}
	//保存
	var onSave = function(){
		$(editForm).form('submit',{
			url:'information/saveInformation.do',
			onSubmit: function(){
				return setValue();
			},
			success: function(data){
				var result = eval('('+data+')');
				if(result.isSuccess){
					var fn = function(){
						$(editDialog).dialog('close');
						search();
					};
					$.messager.alert('提示','保存成功','info',fn);
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
			initCombobox();
			$(editForm).form('clear');
			onOpen(selectRow.informationId);
			$(editDialog).dialog('open');
		}
	 }
	//打开
	var onOpen = function(informationId){
		var url = 'information/initInformation.do';
		var content = {informationId:informationId};
		asyncCallService(url,content,function(result){
			if(result.isSuccess){
				var data = result.data;
				var information = eval("("+data.information+")");
				$('#informationId',editForm).val(information.informationId);
				$('#informationTitle',editForm).val(information.informationTitle);
				$('#category',editForm).combobox('setValue',information.category);
				$('#teacherName',editForm).val(information.teacherName);
				$('#competition',editForm).combobox('setValue',information.competitionId);
				$('#status',editForm).combobox('setValue',information.status);
				setContent(information.content);
			}else{
				$.messager.alert('提示',result.message,'error');
			}
		});
	}
	//查询
	var search = function(){
		var informationTitle = $('#informationTitleSearch',queryContent).val();
		var category = $('#categorySearch',queryContent).combobox('getValue');
		var competitionId = $('#competitionSearchMain').combobox('getValue');
		var status = $('#statusSearch',queryContent).combobox('getValue');
		var content = {informationTitle:informationTitle,category:category,'competition.competitionId':competitionId,status:status};
		
		$(viewList).datagrid({
			url:"information/queryInformation.do",
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
			if(rows.length==0){
				 $.messager.alert('提示',"请选中要删除的纪录","warming");
				 return;	
			}
			var idArray = new Array();
			for(var i=0;i<rows.length;i++){
				idArray.push(rows[i].informationId);
			}
			var ids = idArray.join(CSIT.join);
			var url = "information/mulDeleteInformation.do";
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
			idArray.push(rows[i].informationId);
		}
		$.messager.confirm("提示","确定要"+msg+"记录?",function(t){ 
			if(t){
				var url = 'information/mulUpdateStatusInformation.do';
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
	
	//移动
	var onMove = function(direction){
		if(selectRow == null){
			$.messager.alert('提示',"请选中一条记录","warning");
			return;
		}
		var rows  = $(viewList).datagrid('getRows');
		if(direction==-1){
			if(selectIndex==0){
				$.messager.alert('提示',"已经是第一条记录了","warning");
				return;
			}
		}else if(direction==1){//下移
			if(selectIndex==rows.length-1){
				$.messager.alert('提示',"已经是最后一条记录了","warning");
				return;
			}
		}
		var updateRow = rows[selectIndex+direction];
		
		var informationId = selectRow.informationId;
		var informationTitle = selectRow.informationTitle;
		var competitionName = selectRow.competitionName;
		var status = selectRow.status;
		//后台更新排序
		var url = "information/updateArrayInformation.do";
		var content = {informationId:informationId,updateInformationId:updateRow.informationId};
		var result = syncCallService(url,content);
		if(result.isSuccess){
			$(viewList).datagrid('updateRow',{
				index: selectIndex,
				row: updateRow
			});
			$(viewList).datagrid('updateRow',{
				index: selectIndex+direction,
				row: {
					informationId:informationId,
					informationTitle:informationTitle,
					competitionName:competitionName,
					status:status
				}
			});
			$(viewList).datagrid('unselectAll');
			$(viewList).datagrid('selectRow',selectIndex+direction);
			selectIndex = selectIndex+direction;
			selectRow = $(viewList).datagrid('getSelected');
		}else{
			$.messager.alert('提示',result.message,"error");
		}
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
	}
	checkBtnRight();
  }
})(jQuery);   