// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.enrollInit = function() {
	  $(this).mask({maskMsg:'正在加载界面'});
	  var $this = $(this);
	  var id = $(this).attr('id');
	  var rightId = $(this).attr('rightId');
	  var selectRow = null;
	  var selectIndex = null;
	  
	  var queryContent = $('#queryContent_'+id,$this);
	  var viewList =  $('#viewList_'+id,$this);
	  
	  var editDialog = $('#editDialog_'+id,$this);
	  var editForm = $('#editForm_'+id,editDialog);
	  
	  $('#groupSearch_'+id,queryContent).combobox({
		  width:200,
		  url:'competition/queryComboboxCompetitionGroup.do?competition.competitionId='+$('#competitionSearchMain').combobox('getValue'),
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
		      {field:'examCode',title:'准考证号',width:170,align:"center"},
		      {field:'studentName',title:'学生',width:100,align:"center"},
		      {field:'competitionName',title:'赛事',width:200,align:"center"},
		      {field:'groupName',title:'参赛组别',width:200,align:"center"},
		      {field:'address',title:'参赛地区',width:200,align:"center",
		    	  formatter:function(value,row,index){
		    		  return row.provinceName+row.cityName+row.areaName;
		    	  }
		      },
	          {field:'status',title:'审核状态',width:100,align:"center",
					formatter: function(value,row,index){
						if (value==0){
							return '未审核';
						} else if (value==1){
							return '审核通过';
						} else if (value==2){
							return '审核不通过';
						}
				 }
		      }
		      
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
	  //分页条
	  $(viewList).datagrid('getPager').pagination({   
		  onSelectPage: function(page, rows){
			  var options = $(viewList).datagrid('options');
			  options.pageNumber = page;
			  options.pageSize = rows;
			  search();
		  }
	  });
	  //内部报名
	  $('#add_'+id,$this).click(function(){
		  onAdd();
	  });
	  //修改
	  $('#update_'+id,$this).click(function(){
		  onUpdate();
	  });
//	  //删除
//	  $('#delete_'+id,$this).click(function(){
//		  onMulDelete();
//	  });
//	  //上移
//	  $('#moveUp_'+id,$this).click(function(){
//		  onMove(-1);
//	  });
//	  //下移
//	  $('#moveDown_'+id,$this).click(function(){
//		  onMove(1);
//	  });
	  //状态
	  $('#state_'+id).menu({  
		    onClick:function(item){
		    	if(item.name==0){
		    		onMulUpdateState(0);
		    	}else if(item.name==1){
		    		onMulUpdateState(1);
		    	}else if(item.name==2){
		    		onMulUpdateState(2);
		    	}
		    }
		    	
	  });

	  //查询
	var search = function(flag){
		var studentName = $('#studentSearch',queryContent).val();
		var groupId = $('#groupSearch_'+id,queryContent).combobox('getValue');
		var status = $('#statusSearch',queryContent).combobox('getValue');
		var competitionId = $('#competitionSearchMain').combobox('getValue');
		var content = {'student.studentName':studentName,'competitionGroup.group.groupId':groupId,status:status,
				'competitionGroup.competition.competitionId':competitionId};
		//取得列表信息
		$(viewList).datagrid({
			url:"enroll/queryStudentCompetitionGroup.do",
			queryParams:content
		});
		checkBtnRight();
	};
	//查询
	$('#search_'+id,$this).click(function(){
		search(true);
	});
	
	//编辑框 
	$(editDialog).dialog({  
	    title:'编辑报名',  
	    width:400,
	    height:270,
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
	
	var initCombobox = function(){
		//省份
		$('#province',editForm).combobox({
			width:250,
			data:ECMS.getProvinceList(),
			valueField:'provinceId',
			textField:'provinceName',
			onSelect:function(record){
				$('#city',editForm).combobox({
					data:ECMS.getCityList(record.provinceId)
				});
			}
		});
		//市
		$('#city',editForm).combobox({
			width:250,
			valueField:'cityId',
			textField:'cityName',
			onSelect:function(record){
				$('#area',editForm).combobox({
					data:ECMS.getAreaList(record.cityId)
				});
			}
		});
		//区
		$('#area',editForm).combobox({
			width:250,
			valueField:'areaId',
			textField:'areaName'
		});
		//赛事
		$('#competition',editForm).combobox({
			width:250,
			data:ECMS.getCompetitionList(),
            valueField:'competitionId',  
            textField:'competitionName',
            onSelect:function(record){
            	$('#competitionGroup',editForm).combobox({
            		url:'competition/queryComboboxCompetitionGroup.do?competition.competitionId='+record.competitionId
        		});
            }
		});
		//参赛组别
		$('#competitionGroup',editForm).combobox({
			width:250,
            valueField:'competitionGroupId',  
            textField:'groupName'
		});
		//学生
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
	};
	
	//添加
	var onAdd = function(){
		$(editForm).form('clear');
		initCombobox();
		var competitionId = $('#competitionSearchMain').combobox('getValue');
		$('#competition',editForm).combobox('setValue',competitionId);
		$('#competition',editForm).combobox('select',competitionId);
		$('#status',editForm).val(1);
		$(editDialog).dialog('open');
	};
	//保存前的赋值操作
	var setValue = function(){
		var student = $('#student',editForm).combogrid('getValue');
		if(''==student){
			$.messager.alert('提示','请选择学生','warning');
			return false;
		}
		var area = $('#area',editForm).combogrid('getValue');
		if(''==area){
			$.messager.alert('提示','请选择地区','warning');
			return false;
		}
		var competitionGroup = $('#competitionGroup',editForm).combogrid('getValue');
		if(''==competitionGroup){
			$.messager.alert('提示','请选择赛事组别','warning');
			return false;
		}
		return true;
	};
	//保存
	var onSave = function(){
		$(editForm).form('submit',{
			url:'enroll/saveStudentCompetitionGroup.do',
			onSubmit: function(){
				return setValue();
			},
			success: function(data){
				var result = eval('('+data+')');
				if(result.isSuccess){
					var fn = function(){
						var studentCompetitionGroupId = $('#studentCompetitionGroupId',editForm).val();
						//新增
						if(studentCompetitionGroupId==''){
							search(true);
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
	//修改
	var onUpdate = function(){
		if(!$('#update_'+id,$this).is(":hidden")){
			if(selectRow==null){
				$.messager.alert("提示","请选择数据行","warning");
				return;
			}
			if(selectRow.status==1){
				$.messager.alert("提示","该报名信息已审核通过，不可修改","warning");
				return;
			}
			initCombobox();
			$(editForm).form('clear');
			$('#studentCompetitionGroupId',editForm).val(selectRow.studentCompetitionGroupId);
			$('#student',editForm).combogrid('setValue',selectRow.studentId);
			$('#competition',editForm).combobox('setValue',selectRow.competitionId);
			$('#competition',editForm).combobox('select',selectRow.competitionId);
			$('#competitionGroup',editForm).combobox('setValue',selectRow.competitionGroupId);
			$(editDialog).dialog('open');
		}
	 };
//	//批量删除
//	var onMulDelete = function(){
//		var rows = $(viewList).datagrid('getChecked');
//		if(rows.length==0){
//			 $.messager.alert('提示',"请选中要删除的纪录","warning");
//			 return;	
//		}
//		$.messager.confirm("提示！","确定要删除选中的记录?",function(t){ 
//			if(!t) return;
//			if(rows.length==0){
//				 $.messager.alert('提示',"请选中要删除的纪录","warning");
//				 return;	
//			}
//			var idArray = new Array();
//			for(var i=0;i<rows.length;i++){
//				idArray.push(rows[i].nationId);
//			}
//			var ids = idArray.join(CSIT.join);
//			var url = "dict/mulDeleteNation.do";
//			var content = {ids:ids};
//			$.post(url,content,
//				function(result){
//					if(result.isSuccess){
//						search();
//					}else{
//						$.messager.alert('提示',result.message,"error");
//					}
//				}, "json");
//		});
//	};
	//修改多个状态
	var onMulUpdateState = function(status){
		var rows =  $(viewList).datagrid('getChecked');
		if(rows.length==0){
			$.messager.alert("提示","请选择要修改状态的数据行","warning");
			return;
		}
		var idArray = new Array();
		for ( var i = 0; i < rows.length; i++) {
			idArray.push(rows[i].studentCompetitionGroupId);
		}
		$.messager.confirm("提示","确定要修改审核状态?",function(t){ 
			if(t){
				var url = 'enroll/mulUpdateStatusStudentCompetitionGroup.do';
				var content ={ids:idArray.join(CSIT.join),status:status};
				asyncCallService(url,content,function(result){
					if(result.isSuccess){	
						var fn = function(){
							search(true);
						};
						$.messager.alert('提示','修改审核状态成功','info',fn);
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
	$(this).mask('hide');
  };
})(jQuery);   