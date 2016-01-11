// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.trainingClassInit = function() {
	  var $this = $(this);
	  var id = $(this).attr('id');
	  var rightId = $(this).attr('rightId');
	  var selectRow = null;
	  var selectIndex = null;
	
	  var editDialog = $('#editDialog_'+id,$this);
	  var editForm = $('#editForm_'+id,editDialog);
	  var viewList =  $('#viewList_'+id,$this);
	  var queryContent = $('#queryContent_'+id,$this);
	  
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
		    {field:'trainingClassName',title:'班级名称',width:100,align:"center"},
		    {field:'competitionName',title:'赛事名称',width:200,align:"center"},
		    {field:'groupName',title:'参赛组别',width:100,align:"center"},
			{field:'address',title:'培训地点',width:100,align:"center"},
			{field:'classDate',title:'培训时间',width:100,align:"center"},
			{field:'classTeacher',title:'任课老师',width:100,align:"center"},
			{field:'goodsName',title:'培训教材',width:100,align:"center"},
			{field:'goodsFee',title:'教材费用',width:80,align:"center"},
			{field:'fee',title:'培训费用',width:80,align:"center"},
			{field:'total',title:'合计费用',width:80,align:"center"},
			{field:'stuCount',title:'已申请人数',width:100,align:"center"},
			{field:'limit',title:'人数上限',width:80,align:"center"},
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
		    	if(item.name=='0'){
		    		onMulUpdateState(0);
		    	}else{
		    		onMulUpdateState(1);
		    	}
		    }
		    	
	  });
	//编辑框
	$(editDialog).dialog({  
		title: '编辑培训班级',
	    width:500,
	    height:550,
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
	//按教材查询
	  $('#goodsSearch',queryContent).combobox({
		  width:150,
		  data:ECMS.getGoodsList(),
		  valueField:'goodsId',
		  textField:'goodsName'
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
		  $('#goods',editForm).combobox({
			  width:250,
			  url:'goods/queryComboboxGoods.do',
			  valueField:'goodsId',
			  textField:'goodsName',
			  onSelect: function(record){   
			    	 $('#goodsFee').numberbox('setValue',record.sellingPrice);    
			   }
		  });
		};

	//添加
	var onAdd = function(){
		$(editForm).form('clear');
		init();
		var defaultInformationId = $('#competitionSearchMain').combobox('getValue');
		$('#competition',editForm).combobox('select',defaultInformationId);
		$('#status',editForm).combobox('setValue',1);
		$(editDialog).dialog('open');
	};
	//保存前的赋值操作
	var setValue = function(){
		//班级名称
		var trainingClassName = $.trim($('#trainingClassName',editForm).val());
		if(''==trainingClassName){
			$.messager.alert('提示','请填写班级名称','warning');
			return false;
		}

		//上课地点
		var address = $.trim($('#address',editForm).val());
		if(''==address){
			$.messager.alert('提示','请填写上课地点','warning');
			return false;
		}
		//所属赛事
		var competitionId = $('#competition',editForm).combobox('getValue');
		if(''==competitionId){
			$.messager.alert('提示','请填写所属赛事','warning');
			return false;
		}
		//参赛组别
		var competitionGroup = $('#competitionGroup',editForm).combobox('getValue');
		if(''==competitionGroup){
			$.messager.alert('提示','请填写参赛组别','warning');
			return false;
		}
		//上课时间
		var classDate = $.trim($('#classDate',editForm).val());
		if(''==classDate){
			$.messager.alert('提示','请填写上课时间','warning');
			return false;
		}
		//人数上限
		var limit = $.trim($('#limit',editForm).val());
		if(''==limit){
			$.messager.alert('提示','请填写人数上限','warning');
			return false;
		}
		//培训费用
		var fee = $.trim($('#fee',editForm).val());
		if(''==fee){
			$.messager.alert('提示','请填写培训费用','warning');
			return false;
		}
		//状态
		var status = $('#status',editForm).combobox('getValue') ;
		if(''==status){
			$.messager.alert('提示','请选择状态','warning');
			return false;
		}

		var address = $.trim($('#address',editForm).val());
		if(''==address){
			$.messager.alert('提示','请填写上课地点','warning');
			return false;
		}
		var classDate = $.trim($('#classDate',editForm).val());
		if(''==classDate){
			$.messager.alert('提示','请填写上课时间','warning');
			return false;
		}
		var limit = $.trim($('#limit',editForm).val());
		if(''==limit){
			$.messager.alert('提示','请填写人数上限','warning');
			return false;
		}
		var fee = $.trim($('#fee',editForm).val());
		if(''==fee){
			$.messager.alert('提示','请填写培训费用','warning');
			return false;
		}		var competitionId = $('#competition',editForm).combobox('getValue');

		return true;
	};
	//保存
	var onSave = function(){
		$(editForm).form('submit',{
			url:'trainingClass/saveTrainingClass.do',
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
			$('#competitionGroup',editForm).combobox('setValue',selectRow.competitionGroupId);
			$('#goods',editForm).combobox('select',selectRow.goodsId);
			$(editDialog).dialog('open');
		}
	 };
	//查询
	var search = function(){
		var trainingClassName = $('#trainingClassNameSearch',queryContent).val();
		var address = $('#addressSearch',queryContent).val();
		var goodsId =  $('#goodsSearch',queryContent).combobox('getValue');
		var defaultInformationId = $('#competitionSearchMain').combobox('getValue');
		var status = $('#statusSearch',queryContent).combobox('getValue');
		var content = {trainingClassName:trainingClassName,address:address,'goods.goodsId':goodsId,'competitionGroup.competition.competitionId':defaultInformationId,status:status};
		
		$(viewList).datagrid({
			url:"trainingClass/queryTrainingClass.do",
			queryParams:content
		});
		checkBtnRight();
	};
	//查询
	$('#search_'+id,$this).click(function(){
		search();
	});
	
	//修改多个状态
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
			idArray.push(rows[i].trainingClassId);
		}
		$.messager.confirm("提示","确定要"+msg+"记录?",function(t){ 
			if(t){
				var url = 'trainingClass/mulUpdateStateTrainingClass.do';
				var content ={ids:idArray.join(CSIT.join),status:status};
				asyncCallService(url,content,function(result){
					if(result.isSuccess){	
						var fn = function(){
							search(true);
						};
						$.messager.alert('提示',msg+'成功','info',fn);
					}else{
						$.messager.alert('提示',result.message,"error");
					}
				});
			}
		});
	};
	
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
				idArray.push(rows[i].trainingClassId);
			}
			var ids = idArray.join(CSIT.join);
			var url = "trainingClass/mulDeleteTrainingClass.do";
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