// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.readInit = function() {
	  var $this = $(this);
	  var id = $(this).attr('id');
	  var rightId = $(this).attr('rightId');
	  var width = $(document.body).width();
	  var height = $(document.body).height();
	  
	  var viewList =  $('#viewList_'+id,$this);
	  var queryContent = $('#queryContent_'+id,$this);
	  var selectRow = null;
	  var selectIndex = null;
	  
	  var editDialog = $('#editDialog_'+id,$this);
	  var editForm = $('#editForm_'+id,editDialog);
	  
	  var qSelectIndex = null;
	  var descript = CKEDITOR.replace('descript',{
		  toolbar:'Paper',
		  width:899,
		  height:93
	  });
	  CKFinder.setupCKEditor(descript,'ckfinder/') ;
	  var getText = function(){		//获得编辑器中的纯文本
		  return CKEDITOR.instances.descript.document.getBody().getText();
	  };
	  var getContent = function(){
	     return CKEDITOR.instances.descript.getData();    //获取编辑器的值
	  };
	  var setContent = function(data){
		CKEDITOR.instances.descript.setData(data);	//赋值给编辑器
	  };
	  $('#teacherSearch',queryContent).combobox({
		  width:150,
		  data:ECMS.getTeacherList(),
		  valueField:'teacherId',
		  textField:'teacherName'
	  });
	  $('#groupSearch',queryContent).combobox({
		  width:150,
		  data:ECMS.getGroupList(),
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
				      {field:'subjectId',title:'编号',width:50,align:"center"},
				      {field:'groupName',title:'参赛组别',width:80,align:"center"},
				      {field:'subjectType',title:'题型',width:80,align:"center"},
				      {field:'descript',title:'试题描述',width:250,align:"center"},
				      {field:'difficulty',title:'难度',width:50,align:"center"},
				      {field:'score',title:'默认分值',width:60,align:"center"},
				      {field:'publishDate',title:'出题时间',width:150,align:"center"},
				      {field:'publishTeacherName',title:'出题教师',width:100,align:"center"},
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
		    	if(item.name==0){
		    		onMulUpdateState(0);
		    	}else if(item.name==1){
		    		onMulUpdateState(1);
		    	}
		    }
		    	
	  });

	//编辑框
	$(editDialog).dialog({  
	    title: '编辑试题',  
	    width:915,
	    height:height-3,
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
	  var init = function(){
		  $('#group',editForm).combobox({
			  width:200,
			  data:ECMS.getGroupList(),
			  valueField:'groupId',
			  textField:'groupName'
		  });
		  
		  $('#publishDate',editForm).val(nowDate33());
		  
		  $('#subjectType',editForm).val('阅读理解');
		  
		  $('#difficulty',editForm).combobox('setValue','中');
		  
		  $('#publishTeacher',editForm).val(ECMS.getTeacherName());
		  
		  setContent(null);
		  
		  $('.questionTr',editDialog).remove();
		  for(var i=0;i<3;i++){
			  addSubTr(null,i);  
		  }
		  $('#qtDiv',editDialog).scrollTop(0);
	  };
	  
	  $('#addSubBtn',editDialog).click(function(){
		  var length = $('.questionTr',editDialog).length;
		  addSubTr(null,length);
	  });
	  
	  $('#delSubBtn',editDialog).click(function(){
		  //删除选择的行
		  $('.questionTr',editDialog).eq(qSelectIndex).remove();
		  //更新题号
		  var length = $('.questionTr',editDialog).length;
		  for(var i=qSelectIndex;i<length;i++){
			  //删除的行之后的行
			  var tr = $('.questionTr',editDialog).eq(i);
			  $('#rowNumber',tr).html(tr.index()+1+':');
		  }
		  $('.questionTd',editDialog).css({
			  'border-right':'1px dotted #ccc',
			  'border-bottom':'1px dotted #ccc'
		  });
	  });
	  
	  var addSubTr = function(row,index){
		  var question = 
				'<tr class="questionTr"><td class="questionTd" style="width:100%">'+	
					'<table id="inTb" style="width:100%" border="1" cellspacing="0" cellpadding="0">'+
						'<tr>'+
							'<td style="width:25px;text-align:center;" id="rowNumber"></td>'+
							'<td colspan="2">'+
								'<input type="text" style="width:100%;padding-left:5" id="descript"/>'+
							'</td>'+
						'</tr>'+
						'<tr>'+
							'<td style="width:25px;text-align:center;">'+
								'<input type="checkbox" name="cb" value="A"/>'+
							'</td>'+
							'<td style="width:25px;text-align:center;">A:</td>'+
							'<td>'+
								'<input type="text" style="width:100%;padding-left:5" class="option" id="option0"/>'+
							'</td>'+
						'</tr>'+
						'<tr>'+
							'<td style="width:25px;text-align:center;">'+
								'<input type="checkbox" name="cb" value="B"/>'+
							'</td>'+
							'<td style="width:25px;text-align:center;">B:</td>'+
							'<td>'+
								'<input type="text" style="width:100%;padding-left:5" class="option" id="option1"/>'+
							'</td>'+
						'</tr>'+
						'<tr>'+
							'<td style="width:25px;text-align:center;">'+
								'<input type="checkbox" name="cb" value="C"/>'+
							'</td>'+
							'<td style="width:25px;text-align:center;">C:</td>'+
							'<td>'+
								'<input type="text" style="width:100%;padding-left:5" class="option" id="option2"/>'+
							'</td>'+
						'</tr>'+
						'<tr>'+
							'<td style="width:25px;text-align:center;">'+
								'<input type="checkbox" name="cb" value="D"/>'+
							'</td>'+
							'<td style="width:25px;text-align:center;">D:</td>'+
							'<td>'+
								'<input type="text" style="width:100%;padding-left:5" class="option" id="option3"/>'+
							'</td>'+
						'</tr>'+
					'</table>'+
				'</td></tr>';
		    $('#questionList',editDialog).append(question);
		    var questionTr = $('.questionTr',editDialog).eq(index);
		    $('#rowNumber',questionTr).html(index+1+':');
		    $('.questionTr',editDialog).bind("click",function(){
				$('.questionTr',editDialog).eq(qSelectIndex).css('background','white');
				$(this).css('background','rgb(251,236,136)');
				qSelectIndex = $(this).index();
			});
		    $('input:checkbox[name="cb"]',questionTr).each(function(){
		        $(this).click(function(){
		            if($(this).attr('checked')){
		                $('input:checkbox[name="cb"]',questionTr).removeAttr('checked');
		                $(this).attr('checked','checked');
		            }
		        });
		    });
		    
		    if(row!=null){
		    	$('#descript',questionTr).val(row.descript);
		    	$('input:checkbox[value="'+row.answer+'"]',questionTr).attr('checked','checked');
				$('#option0',questionTr).val(row.option0);
				$('#option1',questionTr).val(row.option1);
				$('#option2',questionTr).val(row.option2);
				$('#option3',questionTr).val(row.option3);
		    }
		};

	//添加
	var onAdd = function(){
		$(editForm).form('clear');
		init();
		$(editDialog).dialog('open');
	};
	
	//保存前的赋值操作
	var setValue = function(){
		var group = $('#group',editForm).combobox('getValue');
		if(''==group){
			$.messager.alert('提示','请选择参赛组别','warning');
			return false;
		}
		var score = $.trim($('#score',editForm).val());
		if(''==score){
			$.messager.alert('提示','请填写默认分值','warning');
			return false;
		}
		var publishDate = $.trim($('#publishDate',editForm).val());
		if(''==publishDate){
			$.messager.alert('提示','请填写出题时间','warning');
			return false;
		}
		var descript = getContent();
		if(''==descript){
			$.messager.alert('提示','请填写试题描述','warning');
			return false;
		}
		var length = $('.questionTr',editDialog).length;
		if(0==length){
			$.messager.alert('提示','请添加问题','warning');
			return false;
		}
		$('#optionCount',editForm).val(length);
		
		var subjectSubList = new Array();
		for(var i=0;i<length;i++){
			var subjectSub = '';
			
			var tr = $('.questionTr',editForm).eq(i);
			
			var descriptSub = $('#descript',tr).val();
			if(''==descriptSub){
				$.messager.alert('提示','第'+(i+1)+'题没有填写问题','warning');
				return false;
			}
			subjectSub += descriptSub;
			
			var answer = $('input:checkbox[name="cb"]:checked',tr).val();
			if(null==answer){
				$.messager.alert('提示','第'+(i+1)+'题没有勾选答案','warning');
				return false;
			}
			subjectSub += '!@#'+answer;
			
			
			var option = $('.option',tr);
			var isEmpty = false;
			option.each(function(){
				if(''==$(this).val()){
					isEmpty = true;
				}else{
					subjectSub += '!@#'+$(this).val();
				}
			});
			if(isEmpty){
				$.messager.alert('提示','第'+(i+1)+'题有选项的内容为空','warning');
				return false;
			}
			subjectSubList.push(subjectSub);
		}
		var subjectSubStr = subjectSubList.join('^');
		$('#optionsStr',editForm).val(subjectSubStr);
		
		$('#descriptPlain',editForm).val(getText());
		
		return true;
	};
	//保存
	var onSave = function(){
		$(editForm).form('submit',{
			url:'subject/saveSubject.do',
			onSubmit: function(){
				return setValue();
			},
			success: function(data){
				var result = eval('('+data+')');
				if(result.isSuccess){
					var fn = function(){
						var subjectId = $.trim($('#subjectId',editForm).val());
						//新增
						if(subjectId==''){
							search();
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
	var onOpen = function(){
		$('#group',editForm).combobox({
			  width:200,
			  data:ECMS.getGroupList(),
			  valueField:'groupId',
			  textField:'groupName'
		  });
		$('#group',editForm).combobox('setValue',selectRow.groupId);
		
		$('#publishTeacher',editForm).val(selectRow.teacherName);
		
		setContent(selectRow.descript);
		
		$('.questionTr',editDialog).remove();
		var url = "subject/querySubjectSub.do";
		var content = {'subject.subjectId':selectRow.subjectId};
		$.post(url,content,
				function(data){
					var rows = data.rows;
					for(var i=0;i<rows.length;i++){
						addSubTr(rows[i],i);
				 	}
				}, "json");
		$('#qtDiv',editDialog).scrollTop(0);
	};
	
	//修改
	var onUpdate = function(){
		if(!$('#update_'+id,$this).is(":hidden")){
			if(selectRow==null){
				$.messager.alert("提示","请选择数据行","warning");
				return;
			}
			$(editForm).form('clear');
			onOpen();
			$(editForm).form('load',selectRow);
			$(editDialog).dialog('open');
		}
	 };
	//查询
	var search = function(flag){
		var groupId = $('#groupSearch',queryContent).combobox('getValue');
		var teacherId = $('#teacherSearch',queryContent).combobox('getValue');
		var status = $('#statusSearch',queryContent).combobox('getValue');
		var difficulty = $('#difficultySearch',queryContent).combobox('getValue');
		var content = {'group.groupId':groupId,'publishTeacher.teacherId':teacherId,
				subjectType:'阅读理解',status:status,difficulty:difficulty};
		
		$(viewList).datagrid({
			url:"subject/querySubject.do",
			queryParams:content
		});
		checkBtnRight();
	};
	//查询
	$('#search',$this).click(function(){
		search();
	});
	//批量删除
	var onMulDelete = function(){
		var rows = $(viewList).datagrid('getChecked');
		if(rows.length==0){
			 $.messager.alert('提示',"请勾选要删除的纪录","warming");
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
				idArray.push(rows[i].subjectId);
			}
			var ids = idArray.join(CSIT.join);
			var url = "subject/mulDeleteSubject.do";
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
	};
	//批量修改状态
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
			idArray.push(rows[i].subjectId);
		}
		$.messager.confirm("提示","确定要"+msg+"记录?",function(t){ 
			if(t){
				var url = 'subject/mulUpdateStateSubject.do';
				var content ={ids:idArray.join(CSIT.join),status:status};
				asyncCallService(url,content,function(result){
					if(result.isSuccess){
						var fn = function(){
							search(true);
						};
						$.messager.alert('提示',msg+'成功','info',fn);
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
		var dataDictionaryRightBtn = $('#dataDictionaryRight_'+id,$this);
		
		checkArray.push(addBtn);
		checkArray.push(updateBtn);
		checkArray.push(deleteBtn);
		checkArray.push(tbStateBtn);
		checkArray.push(dataDictionaryRightBtn);
		
		checkRight(checkArray,rights);
	};
	checkBtnRight();
  };
})(jQuery);   