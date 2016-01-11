// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.cityInit = function() {
	  var $this = $(this);
	  var id = $(this).attr('id');
	  var rightId = $(this).attr('rightId');
	  var selectRow = null;
	  var selectIndex = null;
	
	  var editDialog = $('#editDialog_'+id,$this);
	  var editForm = $('#editForm_'+id,editDialog);
	  var viewList =  $('#viewList_'+id,$this);
	  var queryContent = $('#queryContent_'+id,$this);
	  
	  $('#provinceSearch',queryContent).combobox({
		  width:150,
		  data:ECMS.getProvinceList(),
		  valueField:'provinceId',
		  textField:'provinceName'
	  });

	
	  var init=function(){
		  $('#province',editForm).combobox({
			  width:250,
			  data:ECMS.getProvinceList(),
			  valueField:'provinceId',
			  textField:'provinceName'
		  });
	  }
	  
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
			{field:'provinceName',title:'省份名称',width:200,align:"center"},
			{field:'cityCode',title:'城市编号',width:100,align:"center"},
			{field:'cityName',title:'城市名称',width:200,align:"center"}
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
	  })
	  //修改
	  $('#update_'+id,$this).click(function(){
		  onUpdate();
	  })
	  //删除
	  $('#delete_'+id,$this).click(function(){
		  onMulDelete();
	  })
	 //上移
	  $('#moveUp_'+id,$this).click(function(){
		  onMove(-1);
	  })
	  //下移
	  $('#moveDown_'+id,$this).click(function(){
		  onMove(1);
	  })
	//编辑框
	$(editDialog).dialog({  
	    width:400,
	    height:200,
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
	//添加
	var onAdd = function(){
		$(editForm).form('clear');
		$('#cityCode',editForm).attr('disabled',false);
		init();
		$(editDialog).dialog({title: '添加参赛城市'});	
		$(editDialog).dialog('open');
	}
	//保存前的赋值操作
	var setValue = function(){
		var cityCode = $.trim($('#cityCode',editForm).val());
		if(''==cityCode){
			$.messager.alert('提示','请填写城市编号','warning');
			return false;
		}
		var cityName = $.trim($('#cityName',editForm).val());
		if(''==cityName){
			$.messager.alert('提示','请填写城市名称','warning');
			return false;
		}
		return true;
	}
	//保存
	var onSave = function(){
		$(editForm).form('submit',{
			url:'dict/saveCity.do',
			onSubmit: function(){
				return setValue();
			},
			success: function(data){
				var result = eval('('+data+')');
				if(result.isSuccess){
					var fn = function(){
						search();
						ECMS.CityList = null;
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
			$('#province',editForm).combobox('setValue',selectRow.provinceId);
			$(editForm).form('load',selectRow);
			$(editDialog).dialog({title: '修改参赛城市'});	
			$(editDialog).dialog('open');
		}
	 }
	//查询
	var search = function(){
		var cityCode = $('#cityCodeSearch',queryContent).val();
		var cityName = $('#cityNameSearch',queryContent).val();
		var provinceId =  $('#provinceSearch',queryContent).combobox('getValue');
		var content = {cityCode:cityCode,cityName:cityName,'province.provinceId':provinceId};
		$(viewList).datagrid({
			url:"dict/queryCity.do",
			queryParams:content
		});
		checkBtnRight();
	}
	//查询
	$('#search',$this).click(function(){
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
				idArray.push(rows[i].cityId);
			}
			var ids = idArray.join(CSIT.join);
			var url = "dict/mulDeleteCity.do";
			var content = {ids:ids};
			$.post(url,content,
				function(result){
					if(result.isSuccess){
						search();
						ECMS.CityList = null;
					}else{
						$.messager.alert('提示',result.message,"error");
					}
				}, "json");
		});
	}
	//移动
	var onMove = function(direction){
		var selectRows =  $(viewList).datagrid('getSelections');
		if(selectRows.length==0){
			$.messager.alert('提示',"请选择行记录","warming");
			return;
		}
		var rows  = $(viewList).datagrid('getRows');
		if(direction==-1){
			if(selectIndex==0){
				$.messager.alert('提示',"已经是第一条记录了","warming");
				return;
			}
		}else if(direction==1){//下移
			var rows  = $(viewList).datagrid('getRows');
			if(selectIndex==rows.length-1){
				$.messager.alert('提示',"已经是最后一条记录了","warming");
				return;
			}
		}
		var updateRow = rows[selectIndex+direction];
		var cityName = selectRow.cityName;
		var cityCode = selectRow.cityCode;
		var cityId = selectRow.cityId;
		var provinceName = selectRow.provinceName;
		//后台更新排序
		var url = "dict/updateArrayCity.do";
		var content = {cityId:cityId,updateCityId:updateRow.cityId};
		var result = syncCallService(url,content);
		if(result.isSuccess){
			$(viewList).datagrid('updateRow',{
				index: selectIndex,
				row: updateRow
			});
			$(viewList).datagrid('updateRow',{
				index: selectIndex+direction,
				row: {
					cityId:cityId,
					cityCode:cityCode,
					cityName:cityName,
					provinceName:provinceName
				}
			});
			$(viewList).datagrid('unselectAll');
			$(viewList).datagrid('selectRow',selectIndex+direction);
			selectIndex = selectIndex+direction;
			selectRow = $(viewList).datagrid('getSelected');
		}else{
			$.messager.alert('提示',result.message,"error");
		}
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
		var moveUpBtn = $('#moveUp_'+id,$this);
		var moveDownBtn = $('#moveDown_'+id,$this);
		checkArray.push(addBtn);
		checkArray.push(updateBtn);
		checkArray.push(deleteBtn);
		checkArray.push(moveUpBtn);
		checkArray.push(moveDownBtn);
		checkRight(checkArray,rights);
	}
	checkBtnRight();
  }
})(jQuery);   