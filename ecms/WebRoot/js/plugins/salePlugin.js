// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.saleInit = function() {
	  var $this = $(this);
	  var id = $(this).attr('id');
	  var rightId = $(this).attr('rightId');
	  var selectRow = null;
	  var selectIndex = null;
	
	  var editDialog = $('#editDialog_'+id,$this);
	  var editForm = $('#editForm_'+id,editDialog);
	  var viewList =  $('#viewList_'+id,$this);
	  var queryContent = $('#queryContent_'+id,$this);
	  $('#goodsSearch',queryContent).combobox({
		  width:250,
		  data:ECMS.getGoodsList(),
		  valueField:'goodsId',
		  textField:'goodsName'
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
		      {field:'ck',checkbox:true},
			  {field:'recrejDate',title:'日期',width:200,align:"center"},
			  {field:'goodsName',title:'教材',width:150,align:"center"},
			  {field:'qty',title:'数量',width:100,align:"center"},
			  {field:'price',title:'售价',width:100,align:"center"},
			  {field:'totalPrice',title:'合计',width:100,align:"center"},
			  {field:'teacherName',title:'操作员',width:100,align:"center"}
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
	  //添加入库单
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
		title:'编辑出库单',
	    width:430,
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
	//初始化
	var init = function (){
		$('#goods',editForm).combobox({
		  width:250,
		  data:ECMS.getGoodsList(),
		  valueField:'goodsId',
		  textField:'goodsName',
		  onSelect: function(record){  
			  if($('#recrejType',editForm).val()==1){
				  $('#price',editForm).numberbox('setValue',record.sellingPrice);
			  }else{
				  $('#price',editForm).numberbox('setValue',record.purchasePrice);
			  }
	      }
	  });
	}
	//添加入库
	var onAdd = function(){
		$(editForm).form('clear');
		$('#recrejDate',editForm).val(nowDate33());
		$('#teacher',editForm).val(ECMS.getTeacherName());
		$('#recrejType',editForm).val(1);
		init();
		$(editDialog).dialog('open');
	}
	//保存前的赋值操作
	var setValue = function(){
		var recrejDate = $.trim($('#recrejDate',editForm).val());
		if(''==recrejDate){
			$.messager.alert('提示','请填写日期','warning');
			return false;
		}
		var goods = $.trim($('#goods',editForm).combobox('getValue'));
		if(''==goods){
			$.messager.alert('提示','请选择教材','warning');
			return false;
		}
		var price = $.trim($('#price',editForm).val());
		if(''==price){
			$.messager.alert('提示','请填写单价','warning');
			return false;
		}
		var qty = $.trim($('#qty',editForm).val());
		if(''==qty){
			$.messager.alert('提示','请填写数量','warning');
			return false;
		}
		return true;
	}
	//保存
	var onSave = function(){
		$(editForm).form('submit',{
			url:'goods/saveRecrej.do',
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
			$('#goods',editForm).combobox('disable');
			$('#teacher',editForm).val(ECMS.getTeacherName());
			$('#goods',editForm).combobox('setValue',selectRow.goodsId);
			$(editDialog).dialog('open');
		}
	 }
	//查询
	var search = function(){
		var goodsId = $('#goodsSearch',queryContent).combobox('getValue');;
		var content = {'goods.goodsId':goodsId,recrejType:1};
		
		$(viewList).datagrid({
			url:"goods/queryRecrej.do",
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
			var idArray = new Array();
			for(var i=0;i<rows.length;i++){
				idArray.push(rows[i].recrejId);
			}
			var ids = idArray.join(CSIT.join);
			var url = "goods/mulDeleteRecrej.do";
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