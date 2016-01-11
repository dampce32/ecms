// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.nextCompetitionStudentInit = function() {
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
		singleSelect:true,
		fit:true,
		toolbar:'#tb_'+id,
		columns:[[
		    {field:'ok',checkbox:true},
			{field:'competitionName',title:'赛事名称',width:200,align:"center"},
			{field:'groupName',title:'赛事组别',width:150,align:"center"},
			{field:'studentName',title:'学生姓名',width:100,align:"center"},
			{field:'score',title:'分数',width:100,align:"center"}
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
		title:'录入赛事晋级',
	    width:800,
	    height:600,
	    closed: true,  
	    cache: false,  
	    modal: true,
	    closable:false,
	    onClose:function(){
	    	$(competitionGroupList).datagrid('loadData',{total:0,rows:[]});
	    	$(nextCompetitionStudentList).datagrid('loadData',{total:0,rows:[]});
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
		var studentName = $.trim($('#studentNameSearch',queryContent).val());
		var competitionSearchMainId =  $('#competitionSearchMain').combobox('getValue');
		var content = {'competitionGroup.competition.competitionId':competitionSearchMainId,'competitionGroup.competitionGroupId':competitionGroupId,
				'student.studentName':studentName};
		
		$(viewList).datagrid({
			url:"competition/queryNextCompetitionStudent.do",
			queryParams:content
		});
		checkBtnRight();
	}
	//查询
	$('#search_'+id,$this).click(function(){
		search();
	})
	
	var competitionGroupList = $('#competitionGroupList_'+id,editDialog);
	var nextCompetitionStudentList = $('#nextCompetitionStudentList_'+id,editDialog);
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
			var content = {'competitionGroup.competitionGroupId':rowData.competitionGroupId};
			$(nextCompetitionStudentList).datagrid({
				url:"competition/queryNextCompetitionStudent.do",
				queryParams:content
			});
		},
		onLoadSuccess:function(data){
			var rows = $(competitionGroupList).datagrid('getRows');
			if(rows.length!=0){
				$(competitionGroupList).datagrid('selectRow',0);
			}
		}
	  });
	$(nextCompetitionStudentList).datagrid({
		singleSelect:true,
		method:"POST",
		nowrap:true,
		striped: true,
		collapsible:true,
		rownumbers:true,
		pagination:true,
		singleSelect:true,
		fit:true,
		fitColumns:true,
		selectOnCheck:false,
		checkOnSelect:false,
		toolbar:[{text:'添加',iconCls:'icon-add',handler:function(){onAddSelect();}},'-',
		         {text:'删除',iconCls:'icon-remove',handler:function(){onDeletePrizeStudent();}},'-',
		         {text:'退出',iconCls:'icon-cancel',handler:function(){ $(editDialog).dialog('close'); }},'-'],
		columns:[[
		    {field:'ok',checkbox:true},
			{field:'studentName',title:'学生',width:100,align:"center"},
			{field:'score',title:'分数',width:100,align:"center"}
		]],
		onClickRow:function(rowIndex, rowData){
		}
	  });
	//----------选择赛事晋级学生名单-----
	var selectNextCompetitionStudentDialog = $('#selectNextCompetitionStudentDialog_'+id);
	var selectNextCompetitionStudentList = $('#selectNextCompetitionStudentList_'+id,selectNextCompetitionStudentDialog);
	//编辑框
	$(selectNextCompetitionStudentDialog).dialog({ 
		title:'选择赛事晋级学生名单',
	    width:800,
	    height:500,
	    closed: true,  
	    cache: false,  
	    modal: true,
	    closable:false,
	    onClose:function(){
	    	$(selectNextCompetitionStudentDialog).form('clear');
	    	$(selectNextCompetitionStudentList).datagrid('loadData',{total:0,rows:[]});
	    }
	});   
	$(selectNextCompetitionStudentList).datagrid({
		singleSelect:true,
		method:"POST",
		nowrap:true,
		striped: true,
		collapsible:true,
		rownumbers:true,
		singleSelect:true,
		fit:true,
		fitColumns:true,
		pagination:true,
		selectOnCheck:false,
		checkOnSelect:false,
		toolbar:[{text:'选择',iconCls:'icon-ok',handler:function(){onAddNextStudent();}},'-',
		         {text:'退出',iconCls:'icon-cancel',handler:function(){ $(selectNextCompetitionStudentDialog).dialog('close'); }},'-'],
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
	//选择赛事晋级学生查询
	$('#searchBtnSelectNextCompetitionStudentDialog_'+id,selectNextCompetitionStudentDialog).click(function(){
		var studentName = $('#studentNameSelectNextCompetitionStudentDialog',selectNextCompetitionStudentDialog).val();
		var competitionGroupRow =  $(competitionGroupList).datagrid('getSelected');
		var queryParams = {'competitionGroup.competitionGroupId':competitionGroupRow.competitionGroupId,'student.studentName':studentName};
		$(selectNextCompetitionStudentList).datagrid({
			url:'competition/selectQueryNextCompetitionStudent.do',
			queryParams:queryParams
		});
	})
	
	var onAddSelect = function(){
		$(selectNextCompetitionStudentDialog).form('clear');
		$(selectNextCompetitionStudentDialog).dialog('open');
	}
	//添加晋级学生
	var onAddNextStudent = function(){
		var rows = $(selectNextCompetitionStudentList).datagrid('getChecked');
		if(rows.length==0){
			$.messager.alert('提示','请选择要添加的晋级学生','warning');
			return;
		}
		var studentIdArray = new Array();
		var scoreArray = new Array();
		for ( var i = 0; i < rows.length; i++) {
			studentIdArray.push(rows[i].studentId);
			scoreArray.push(rows[i].score);
		}
		var studentIds = studentIdArray.join(CSIT.join);
		var competitionGroupRow = $(competitionGroupList).datagrid('getSelected');
		var url ='competition/mulSaveNextCompetitionStudent.do';
		var content = {'competitionGroup.competitionGroupId':competitionGroupRow.competitionGroupId,ids:studentIds,scores:scoreArray.join(CSIT.join)};
		asyncCallService(url,content,function(result){
			if(result.isSuccess){
				$(selectNextCompetitionStudentDialog).dialog('close');
				$(nextCompetitionStudentList).datagrid('reload');
			}else{
				$.messager.alert('提示',result.message,"error");
			}
		});
	}
	//删除晋级学生
	var onDeletePrizeStudent = function(){
		var rows = $(nextCompetitionStudentList).datagrid('getChecked');
		if(rows.length==0){
			$.messager.alert('提示','请选择要删除的晋级学生','warning');
			return;
		}
		$.messager.confirm("提示！","确定要删除选中的晋级学生?",function(t){ 
			if(!t) return;
			var nextCompetitionStudentIdArray = new Array();
			for ( var i = 0; i < rows.length; i++) {
				nextCompetitionStudentIdArray.push(rows[i].nextCompetitionStudentId);
			}
			var nextCompetitionStudentIds = nextCompetitionStudentIdArray.join(CSIT.join);
			var url ='competition/mulDeleteNextCompetitionStudent.do';
			var content = {ids:nextCompetitionStudentIds};
			asyncCallService(url,content,function(result){
				if(result.isSuccess){
					$(selectNextCompetitionStudentDialog).dialog('close');
					$(nextCompetitionStudentList).datagrid('reload');
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
			$.messager.alert('提示','请选择要删除的晋级学生','warning');
			return;
		}
		$.messager.confirm("提示！","确定要删除选中的晋级学生?",function(t){ 
			if(!t) return;
			var nextCompetitionStudentIdArray = new Array();
			for ( var i = 0; i < rows.length; i++) {
				nextCompetitionStudentIdArray.push(rows[i].nextCompetitionStudentId);
			}
			var nextCompetitionStudentIds = nextCompetitionStudentIdArray.join(CSIT.join);
			var url ='competition/mulDeleteNextCompetitionStudent.do';
			var content = {ids:nextCompetitionStudentIds};
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