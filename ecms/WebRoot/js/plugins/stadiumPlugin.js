// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.stadiumInit = function() {
	  var $this = $(this);
	  var id = $(this).attr('id');
	  var rightId = $(this).attr('rightId');
	  var selectRow = null;
	  var selectIndex = null;
	
	  var editDialog = $('#editDialog_'+id,$this);
	  var editForm = $('#editForm_'+id,editDialog);
	  var viewList =  $('#viewList_'+id,$this);
	  var queryContent = $('#queryContent_'+id,$this);
//	  $('#competitionGroupSearch',queryContent).combobox({
//		  width:250,
//		  data:ECMS.getCompetitionGroupList(record.competitionId),
//		  valueField:'competitionGroupId',
//		  textField:'groupName'
//	  });
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
			  {field:'stadiumName',title:'赛场名称',width:150,align:"center"},
			  {field:'stadiumAddr',title:'比赛地点',width:100,align:"center"},
			  {field:'competitionDate',title:'比赛时间',width:150,align:"center"},
			  {field:'arrangeNo',title:'申请',width:100,align:"center"},
			  {field:'limit',title:'上限',width:100,align:"center"},
			  {field:'competitionName',title:'赛事名称',width:200,align:"center"},
			  {field:'groupName',title:'参赛组别',width:100,align:"center"},
			  {field:'note',title:'备注',width:150,align:"center"}
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
		title:'编辑赛场',
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
		$('#competition',editForm).combobox({
		  width:250,
		  data:ECMS.getCompetitionList(),
		  valueField:'competitionId',
		  textField:'competitionName',
		  onSelect: function(record){  
			  $('#competitionGroup',editForm).combobox({
				  data:ECMS.getCompetitionGroupList(record.competitionId)
			  });
	      }
	  });
	  $('#competitionGroup',editForm).combobox({
		  width:250,
		  valueField:'competitionGroupId',
		  textField:'groupName'
	  });
	}
	//添加
	var onAdd = function(){
		$(editForm).form('clear');
		init();
		var defaultInformationId = $('#competitionSearchMain').combobox('getValue');
		$('#competition',editForm).combobox('select',defaultInformationId);
		$(editDialog).dialog('open');
	}
	//保存前的赋值操作
	var setValue = function(){
		var stadiumName = $.trim($('#stadiumName',editForm).val());
		if(''==stadiumName){
			$.messager.alert('提示','请填写赛场名称','warning');
			return false;
		}
		var stadiumAddr = $.trim($('#stadiumAddr',editForm).val());
		if(''==stadiumAddr){
			$.messager.alert('提示','请填写比赛地点','warning');
			return false;
		}
		return true;
	}
	//保存
	var onSave = function(){
		$(editForm).form('submit',{
			url:'stadium/saveStadium.do',
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
			$('#competition',editForm).combobox('select',selectRow.competitionId);
			$('#competitionGroup',editForm).combobox('select',selectRow.competitionGroupId);
			$(editDialog).dialog('open');
		}
	 }
	//查询
	var search = function(){
		var stadiumName = $('#nameSearch',queryContent).val();
		var competition = $('#competitionSearch',queryContent).val();
		var competitionId = $('#competitionSearchMain').combobox('getValue');
		var content = {stadiumName:stadiumName,'competitionGroup.competition.competitionId':competitionId};
		
		$(viewList).datagrid({
			url:"stadium/queryStadium.do",
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
				idArray.push(rows[i].stadiumId);
			}
			var ids = idArray.join(CSIT.join);
			var url = "stadium/mulDeleteStadium.do";
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