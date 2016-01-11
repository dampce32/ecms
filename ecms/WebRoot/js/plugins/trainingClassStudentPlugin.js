// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.trainingClassStudentInit = function() {
	  var $this = $(this);
	  var id = $(this).attr('id');
	  var rightId = $(this).attr('rightId');
	  var selectRow = null;
	  var selectIndex = null;
	  var viewList =  $('#viewList_'+id,$this);
	  var queryContent = $('#queryContent_'+id,$this);
	  var editDialog = $('#editDialog_'+id,$this);
	  var editForm = $('#editForm_'+id,editDialog);
	  var competitionSearchMainId = $('#competitionSearchMain').combobox('getValue');
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
		    {field:'groupName',title:'组别',width:100,align:"center"},
		    {field:'trainingClassName',title:'班级名称',width:100,align:"center"},
		    {field:'studentName',title:'学生',width:100,align:"center"}
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
	  //编辑框
	$(editDialog).dialog({ 
		title:'编辑培训班级学生',
	    width:430,
	    height:250,
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
	//初始化
	var init = function (){
		competitionSearchMainId = $('#competitionSearchMain').combobox('getValue');
		$('#trainingClass',editForm).combobox({
		  width:250,
		  url:'trainingClass/queryComboboxTrainingClass.do?competitionId='+competitionSearchMainId,
		  valueField:'trainingClassId',
		  textField:'trainingClassName'
	  });
	  $('#student',editForm).combogrid({
			width:250,
			panelWidth:450,  
            idField:'studentId',  
            textField:'studentName',  
            url:'enroll/queryComboboxStudent.do',
            columns:[[  
                {field:'studentName',title:'姓名',width:100},  
                {field:'userCode',title:'登录名',width:100}
            ]],
            rownumbers:true,
            pagination:true,
            pageSize: 10,			//每页显示的记录条数，默认为10   
            pageList: [10,20,30],	//可以设置每页记录条数的列表
            mode:'remote',			//实时查询
            onSelect:function(rowIndex, rowData){
            	$('#province',editForm).combobox('select',rowData.provinceId);
            	$('#city',editForm).combobox('select',rowData.cityId);
            	$('#area',editForm).combobox('select',rowData.areaId);
            }
		});
	}
	//添加
	var onAdd = function(){
		$(editForm).form('clear');
		init();
		$(editDialog).dialog('open');
	}
	//保存前的赋值操作
	var setValue = function(){
		var trainingClass = $.trim($('#trainingClass',editForm).combobox('getValue'));
		if(''==trainingClass){
			$.messager.alert('提示','请选择培训班级','warning');
			return false;
		}
		var student = $.trim($('#student',editForm).combogrid('getValue'));
		if(''==student){
			$.messager.alert('提示','请选择学生','warning');
			return false;
		}
		return true;
	}
	//保存
	var onSave = function(){
		$(editForm).form('submit',{
			url:'trainingClass/saveByAdminTrainingClassStudent.do',
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
			$('#trainingClass',editForm).combobox('setValue',selectRow.trainingClassId);
			$('#student',editForm).combogrid('setValue',selectRow.studentId);
			$(editDialog).dialog('open');
		}
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
			var idArray = new Array();
			for(var i=0;i<rows.length;i++){
				idArray.push(rows[i].trainingClassStudentId);
			}
			var ids = idArray.join(CSIT.join);
			var url = "trainingClass/mulDeleteTrainingClassStudent.do";
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
	//按培训班级查询
	  $('#trainingClassSearch_'+id,queryContent).combobox({
		  width:200,
		  url:'trainingClass/queryComboboxTrainingClass.do?competitionId='+competitionSearchMainId,
		  valueField:'trainingClassId',
		  textField:'trainingClassName'
	  });
	//查询
	var search = function(){
		var trainingClassId = $('#trainingClassSearch_'+id,queryContent).combobox('getValue');
		var competitionId = $('#competitionSearchMain').combobox('getValue');
		var content = {'trainingClass.trainingClassId':trainingClassId,competitionId:competitionId};
		
		$(viewList).datagrid({
			url:"trainingClass/queryTrainingClassStudent.do",
			queryParams:content
		});
	}
	//查询
	$('#search_'+id,$this).click(function(){
		search();
	})
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