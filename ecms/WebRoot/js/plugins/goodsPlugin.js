// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.goodsInit = function() {
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
			  {field:'goodsName',title:'教材名称',width:150,align:"center"},
			  {field:'pressName',title:'出版社名称',width:150,align:"center"},
			  {field:'purchasePrice',title:'进价',width:100,align:"center"},
			  {field:'sellingPrice',title:'售价',width:100,align:"center"},
			  {field:'store',title:'库存',width:100,align:"center"},
			  {field:'price',title:'库存单价',width:100,align:"center"},
			  {field:'amount',title:'库存金额',width:100,align:"center"},
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
		title:'编辑教材',
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
	//添加
	var onAdd = function(){
		$(editForm).form('clear');
		$('#teacher',editForm).val(ECMS.getTeacherName());
		$(editDialog).dialog('open');
	}
	//保存前的赋值操作
	var setValue = function(){
		var goodsName = $.trim($('#goodsName',editForm).val());
		if(''==goodsName){
			$.messager.alert('提示','请填写教材名称','warning');
			return false;
		}
		return true;
	}
	//保存
	var onSave = function(){
		$(editForm).form('submit',{
			url:'goods/saveGoods.do',
			onSubmit: function(){
				return setValue();
			},
			success: function(data){
				var result = eval('('+data+')');
				if(result.isSuccess){
					var fn = function(){
						search();
						ECMS.GoodsList = null;
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
			$('#groupCode',editForm).attr('disabled',true);
			$(editForm).form('clear');
			$(editForm).form('load',selectRow);
			$(editDialog).dialog('open');
		}
	 }
	//查询
	var search = function(){
		var goodsName = $('#nameSearch',queryContent).val();
		var content = {goodsName:goodsName};
		
		$(viewList).datagrid({
			url:"goods/queryGoods.do",
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
				idArray.push(rows[i].goodsId);
			}
			var ids = idArray.join(CSIT.join);
			var url = "goods/mulDeleteGoods.do";
			var content = {ids:ids};
			$.post(url,content,
				function(result){
					if(result.isSuccess){
						search();
						ECMS.GoodsList = null;
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