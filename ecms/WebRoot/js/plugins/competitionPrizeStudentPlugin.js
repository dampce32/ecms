// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.competitionPrizeStudentInit = function() {
	  var $this = $(this);
	  var id = $(this).attr('id');
	  var rightId = $(this).attr('rightId');
	  var width = $(document.body).width();
	  var height = $(document.body).height();
	
	  var editDialog = $('#editDialog_'+id,$this);
	  var viewList =  $('#viewList_'+id,$this);
	  var queryContent = $('#queryContent_'+id,$this);
	  var competitionSearchMainId = $('#competitionSearchMain').combobox('getValue');
	  //赛事组别
	  $('#competitionGroupSearch_'+id,queryContent).combobox({
		  width:150,
		  url:'competition/queryComboboxCompetitionGroup.do?competition.competitionId='+competitionSearchMainId,
		  valueField:'competitionGroupId',
		  textField:'groupName',
		  onSelect:function(record){
			  $('#competitionPrizeSearch_'+id,queryContent).combobox({
				  url:'competition/queryComboboxCompetitionPrize.do?competitionGroup.competitionGroupId='+record.competitionGroupId
			  });
		  }
	  });
	  //赛事奖项
	  $('#competitionPrizeSearch_'+id,queryContent).combobox({
		  width:150,
		  valueField:'competitionPrizeId',
		  textField:'prizeName'
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
		singleSelect:true,
		fit:true,
		toolbar:'#tb_'+id,
		columns:[[
		    {field:'ok',checkbox:true},  
			{field:'competitionName',title:'赛事名称',width:200,align:"center"},
			{field:'groupName',title:'赛事组别',width:150,align:"center"},
			{field:'prizeName',title:'赛事奖项',width:150,align:"center"},
			{field:'studentName',title:'学生姓名',width:100,align:"center"}
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
	  //录入
	  $('#update_'+id,$this).click(function(){
		  onUpdate();
	  });
	//删除
	  $('#delete_'+id,$this).click(function(){
		  onMulDelete();
	  });
	//编辑框
	$(editDialog).dialog({ 
		title:'录入赛事获奖',
	    width:800,
	    height:600,
	    closed: true,  
	    cache: false,  
	    modal: true,
	    closable:false,
	    onClose:function(){
	    	$(competitionGroupList).datagrid('loadData',{total:0,rows:[]});
	    	$(competitionPrizeList).datagrid('loadData',{total:0,rows:[]});
	    	$(competitionPrizeStudentList).datagrid('loadData',{total:0,rows:[]});
	    }
	});    
	//修改
	var onUpdate = function(){
		if(!$('#update_'+id,$this).is(":hidden")){
			var competitionSearchMainId =  $('#competitionSearchMain').combobox('getValue');
			if(''==competitionSearchMainId){
				$.messager.alert('提示','请选择赛事','warning');
				return;
			}
			$(competitionGroupList).datagrid({
				url:'competition/queryDatagridCompetitionGroup.do?competition.competitionId='+competitionSearchMainId
			});
			$(editDialog).dialog('open');
		}
	 }
	//查询
	var search = function(){
		var competitionGroupId = $('#competitionGroupSearch_'+id,queryContent).combobox('getValue');
		var competitionPrizeId = $('#competitionPrizeSearch_'+id,queryContent).combobox('getValue');
		var studentName = $.trim($('#studentNameSearch',queryContent).val());
		var competitionSearchMainId =  $('#competitionSearchMain').combobox('getValue');
		var content = {'competitionPrize.competitionGroup.competition.competitionId':competitionSearchMainId,'competitionPrize.competitionGroup.competitionGroupId':competitionGroupId,
				'competitionPrize.competitionPrizeId':competitionPrizeId,'student.studentName':studentName};
		
		$(viewList).datagrid({
			url:"competition/queryCompetitionPrizeStudent.do",
			queryParams:content
		});
		checkBtnRight();
	}
	//查询
	$('#search_'+id,$this).click(function(){
		search();
	})
	
	var competitionGroupList = $('#competitionGroupList_'+id,editDialog);
	var competitionPrizeList = $('#competitionPrizeList_'+id,editDialog);
	var competitionPrizeStudentList = $('#competitionPrizeStudentList_'+id,editDialog);
	$(competitionGroupList).datagrid({
		singleSelect:true,
		method:"POST",
		nowrap:true,
		striped: true,
		collapsible:true,
		rownumbers:true,
		singleSelect:true,
		fit:true,
		fitColumns:true,
		columns:[[
			{field:'groupName',title:'赛事组别',width:100,align:"center"}
		]],
		onSelect:function(rowIndex, rowData){
			$(competitionPrizeList).datagrid({
				url:'competition/queryDatagridCompetitionPrize.do?competitionGroup.competitionGroupId='+rowData.competitionGroupId
			});
			$(competitionPrizeStudentList).datagrid('loadData',{total:0,rows:[]});
		},
		onLoadSuccess:function(data){
			var rows = $(competitionGroupList).datagrid('getRows');
			if(rows.length!=0){
				$(competitionGroupList).datagrid('selectRow',0);
			}
		}
	  });
	$(competitionPrizeList).datagrid({
		singleSelect:true,
		method:"POST",
		nowrap:true,
		striped: true,
		collapsible:true,
		rownumbers:true,
		singleSelect:true,
		fit:true,
		fitColumns:true,
		columns:[[
			{field:'prizeName',title:'赛事奖项',width:100,align:"center"}
		]],
		onSelect:function(rowIndex, rowData){
			var content = {'competitionPrize.competitionPrizeId':rowData.competitionPrizeId};
			$(competitionPrizeStudentList).datagrid({
				url:"competition/queryCompetitionPrizeStudent.do",
				queryParams:content
			});
		},
		onLoadSuccess:function(data){
			var rows = $(competitionPrizeList).datagrid('getRows');
			if(rows.length!=0){
				$(competitionPrizeList).datagrid('selectRow',0);
			}
		}
	  });
	$(competitionPrizeStudentList).datagrid({
		singleSelect:true,
		method:"POST",
		nowrap:true,
		striped: true,
		collapsible:true,
		rownumbers:true,
		pagination:true,
		fit:true,
		fitColumns:true,
		selectOnCheck:false,
		checkOnSelect:false,
		toolbar:[{text:'添加',iconCls:'icon-add',handler:function(){onAddSelect();}},'-',
		         {text:'删除',iconCls:'icon-remove',handler:function(){onDeletePrizeStudent();}},'-',
		         {text:'退出',iconCls:'icon-cancel',handler:function(){ $(editDialog).dialog('close'); }},'-'],
		columns:[[
		    {field:'ok',checkbox:true},
			{field:'studentName',title:'学生',width:100,align:"center"}
		]],
		onClickRow:function(rowIndex, rowData){
		}
	  });
	//----------选择赛事获奖学生名单-----
	var selectCompetitionGroupPrizeStudentDialog = $('#selectCompetitionGroupPrizeStudentDialog_'+id);
	var selectCompetitionGroupPrizeStudentList = $('#selectCompetitionGroupPrizeStudentList_'+id,selectCompetitionGroupPrizeStudentDialog);
	//编辑框
	$(selectCompetitionGroupPrizeStudentDialog).dialog({ 
		title:'选择赛事获奖学生名单',
	    width:800,
	    height:500,
	    closed: true,  
	    cache: false,  
	    modal: true,
	    closable:false,
	    onClose:function(){
	    	$(selectCompetitionGroupPrizeStudentDialog).form('clear');
	    	$(selectCompetitionGroupPrizeStudentList).datagrid('loadData',{total:0,rows:[]});
	    }
	});   
	$(selectCompetitionGroupPrizeStudentList).datagrid({
		singleSelect:true,
		method:"POST",
		nowrap:true,
		striped: true,
		collapsible:true,
		rownumbers:true,
		fit:true,
		fitColumns:true,
		pagination:true,
		selectOnCheck:false,
		checkOnSelect:false,
		toolbar:[{text:'选择',iconCls:'icon-ok',handler:function(){onAddPrizeStudent();}},'-',
		         {text:'退出',iconCls:'icon-cancel',handler:function(){ $(selectCompetitionGroupPrizeStudentDialog).dialog('close'); }},'-'],
		columns:[[
		    {field:'ok',checkbox:true},       
			{field:'studentName',title:'学生姓名',width:100,align:"center"},
			{field:'competitionName',title:'赛事',width:100,align:"center"},
			{field:'groupName',title:'赛事分组',width:100,align:"center"},
			{field:'score',title:'在线考试成绩',width:100,align:"center"}
		]],
		onClickRow:function(rowIndex, rowData){
		}
	  });
	//选择赛事获奖学生查询
	$('#searchBtnSelectCompetitionGroupPrizeStudentDialog_'+id,selectCompetitionGroupPrizeStudentDialog).click(function(){
		var studentName = $('#studentNameSelectCompetitionGroupPrizeStudentDialog',selectCompetitionGroupPrizeStudentDialog).val();
		var competitionGroupRow =  $(competitionGroupList).datagrid('getSelected');
		var queryParams = {'competitionPrize.competitionGroup.competitionGroupId':competitionGroupRow.competitionGroupId,'student.studentName':studentName};
		$(selectCompetitionGroupPrizeStudentList).datagrid({
			url:'competition/selectQueryCompetitionPrizeStudent.do',
			queryParams:queryParams
		});
	})
	
	var onAddSelect = function(){
		$(selectCompetitionGroupPrizeStudentDialog).form('clear');
		$(selectCompetitionGroupPrizeStudentDialog).dialog('open');
	}
	//添加获奖学生
	var onAddPrizeStudent = function(){
		var rows = $(selectCompetitionGroupPrizeStudentList).datagrid('getChecked');
		if(rows.length==0){
			$.messager.alert('提示','请选择要添加的获奖学生','warning');
			return;
		}
		var studentIdArray = new Array();
		for ( var i = 0; i < rows.length; i++) {
			studentIdArray.push(rows[i].studentId);
		}
		var studentIds = studentIdArray.join(CSIT.join);
		var competitionPrizeRow = $(competitionPrizeList).datagrid('getSelected');
		var url ='competition/mulSaveCompetitionPrizeStudent.do';
		var content = {'competitionPrize.competitionPrizeId':competitionPrizeRow.competitionPrizeId,ids:studentIds};
		asyncCallService(url,content,function(result){
			if(result.isSuccess){
				$(selectCompetitionGroupPrizeStudentDialog).dialog('close');
				$(competitionPrizeStudentList).datagrid('reload');
			}else{
				$.messager.alert('提示',result.message,"error");
			}
		});
	}
	//删除获奖学生
	var onDeletePrizeStudent = function(){
		var rows = $(competitionPrizeStudentList).datagrid('getChecked');
		if(rows.length==0){
			$.messager.alert('提示','请选择要删除的获奖学生','warning');
			return;
		}
		$.messager.confirm("提示！","确定要删除选中的获奖学生?",function(t){ 
			if(!t) return;
			var competitionPrizeStudentIdArray = new Array();
			for ( var i = 0; i < rows.length; i++) {
				competitionPrizeStudentIdArray.push(rows[i].competitionPrizeStudentId);
			}
			var competitionPrizeStudentIds = competitionPrizeStudentIdArray.join(CSIT.join);
			var url ='competition/mulDeleteCompetitionPrizeStudent.do';
			var content = {ids:competitionPrizeStudentIds};
			asyncCallService(url,content,function(result){
				if(result.isSuccess){
					$(selectCompetitionGroupPrizeStudentDialog).dialog('close');
					$(competitionPrizeStudentList).datagrid('reload');
				}else{
					$.messager.alert('提示',result.message,"error");
				}
			});
		});
	}
	//批量删除
	var onMulDelete = function(){
		var rows = $(viewList).datagrid('getChecked');
		if(rows.length==0){
			$.messager.alert('提示','请选择要删除的获奖学生','warning');
			return;
		}
		$.messager.confirm("提示！","确定要删除选中的获奖学生?",function(t){ 
			if(!t) return;
			var competitionPrizeStudentIdArray = new Array();
			for ( var i = 0; i < rows.length; i++) {
				competitionPrizeStudentIdArray.push(rows[i].competitionPrizeStudentId);
			}
			var competitionPrizeStudentIds = competitionPrizeStudentIdArray.join(CSIT.join);
			var url ='competition/mulDeleteCompetitionPrizeStudent.do';
			var content = {ids:competitionPrizeStudentIds};
			asyncCallService(url,content,function(result){
				if(result.isSuccess){
					$(viewList).datagrid('reload');
				}else{
					$.messager.alert('提示',result.message,"error");
				}
			});
		});
	}
	//----------检查权限--------------
	var rights = null;
	var checkBtnRight = function(){
		if(rights==null){
			rights = getRights(rightId);
		}
		var checkArray = new Array();
		var updateBtn = $('#update_'+id,$this);
		checkArray.push(updateBtn);
		checkRight(checkArray,rights);
	}
	checkBtnRight();
  }
})(jQuery);   