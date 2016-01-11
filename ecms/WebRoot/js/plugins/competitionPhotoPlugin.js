(function($) {  
  // 插件的定义  
  $.fn.competitionPhotoInit = function() {
	  var $this = $(this);
	  var id = $(this).attr('id');
	  var rightId = $(this).attr('rightId');
	  var selectRow = null;
	  var width = $(document.body).width();
	  var height = $(document.body).height();
	
	  var editDialog = $('#editDialog_'+id,$this);
	  var editForm = $('#editForm_'+id,editDialog);
	  var viewList =  $('#viewList_'+id,$this);
	  var queryContent = $('#queryContent_'+id,$this);
	  
	  $('#photoTypeSearch',$this).combobox({
		  width:150,
		  data:ECMS.getPhotoTypeList(),
		  valueField:'photoType',
		  textField:'photoType'
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
			{field:'competitionName',title:'赛事',width:200,align:"center"},
			{field:'photoType',title:'类型',width:150,align:"center"},
			{field:'note',title:'图片详解',width:150,align:"center"}
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
	  //批量添加
	  $('#mulAdd_'+id,$this).click(function(){
		  onMulAdd();
	  });
	  //修改
	  $('#update_'+id,$this).click(function(){
		  onUpdate();
		  return false;
	  });
	  //删除
	  $('#delete_'+id,$this).click(function(){
		  onMulDelete();
		  return false;
	  });
	  //状态
	  $('#state_'+id).menu({  
		    onClick:function(item){
		    	if(item.name==0){
		    		onMulUpdateState(0);
		    	}else{
		    		onMulUpdateState(1);
		    	}
		    }
		    	
	  });
	//上移
	  $('#moveUp_'+id,$this).click(function(){
		  onMove(-1);
	  });
	  //下移
	  $('#moveDown_'+id,$this).click(function(){
		  onMove(1);
	  });
	//编辑框
	$(editDialog).dialog({ 
		title:'编辑赛事风采',
		width:660,
		height:330,
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
		}],
		onClose:function(){
			$(editForm).form('clear');
			$("#uploadPicture1").html('');
			$('#competititionPhoto',editForm).attr('src','#');
		}
	}); 
	var initCombobox = function(){
		 $('#competition',editDialog).combobox({
			  width:300,
			  data:ECMS.getCompetitionList(),
			  valueField:'competitionId',
			  textField:'competitionName'
		  });
		 $('#photoType',editDialog).combobox({
			  width:300,
			  data:ECMS.getPhotoTypeList(),
			  valueField:'photoType',
			  textField:'photoType'
		 });
	}
	//添加
	var onAdd = function(){
		$(editForm).form('clear');
		var uploadPicture;
		var property={
				multiple:false,
			    file_types:"*.jpg;*.gif;*.png",
			    btn_add_text:"添加",
			    btn_up_text:"上传",
			    btn_cancel_text:"放弃",
			    btn_clean_text:"清空",
			    op_del_text:"单项删除",
			    op_up_text:"单项上传",
			    op_fail_text:"上传失败",
			    op_ok_text:"上传成功",
			    op_no_text:"取消上传",
			upload_url:"information/mulUploadPhotoCompetitionPhoto.do;jsessionid="+sessionId,
			flash_url :"js/GooUploader/swfupload.swf"
		};
	    uploadPicture=$.createGooUploader($("#uploadPicture1"),property);
	    uploadPicture.$swfUpload.uploadSuccess=function(file,msg){
	        var id=file.id;
	        uploadPicture.$fileList[id].span.html("100%");
	        var li=uploadPicture.$content.children("#"+id);
	        li.children(".op_no").css("display","none");
	        li.children(".op_ok").css("display","block");
	        $('#pictureId',editDialog).val(msg);
	        $('#competititionPhoto',editForm).attr('src',ECMS.competitionPhotoPath+'/t_'+msg+'.png');
	    };
		initCombobox();
		var defaultCompetitionPhotoId = $('#competitionSearchMain').combobox('getValue');
		$('#competition',editDialog).combobox('setValue',defaultCompetitionPhotoId);
		$(editDialog).dialog('open');
	}
	//批量添加
	var onMulAdd = function(){
		$('#competition',mulAddDialog).combobox({
			  width:250,
			  data:ECMS.getCompetitionList(),
			  valueField:'competitionId',
			  textField:'competitionName'
		  });
		 $('#photoType',mulAddDialog).combobox({
			  width:250,
			  data:ECMS.getPhotoTypeList(),
			  valueField:'photoType',
			  textField:'photoType'
		 });
		var defaultCompetitionPhotoId = $('#competitionSearchMain').combobox('getValue');
		$('#competition',mulAddDialog).combobox('setValue',defaultCompetitionPhotoId);
		pictureIdArray = new Array();
		$(mulAddDialog).dialog('open');
		return false;
	}
	
	//保存前的赋值操作
	var setValue = function(){
		var competition = $.trim($('#competition',editForm).combobox('getValue'));
		if(''==competition){
			$.messager.alert('提示','请选择赛事','warning');
			return false;
		}
		var photoType = $.trim($('#photoType',editForm).combobox('getValue'));
		if(''==photoType){
			$.messager.alert('提示','请选择类型','warning');
			return false;
		}
		var pictureId = $.trim($('#pictureId',editForm).val());
		if(''==pictureId){
			$.messager.alert('提示','请先上传图片','warning');
			return false;
		}
		return true;
	}
	//保存
	var onSave = function(){
		$(editForm).form('submit',{
			url:'information/saveCompetitionPhoto.do',
			onSubmit: function(){
				return setValue();
			},
			success: function(data){
				var result = eval('('+data+')');
				if(result.isSuccess){
					var fn = function(){
						$(editDialog).dialog('close');
						search();
					};
					$.messager.alert('提示','保存成功','info',fn);
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
			var uploadPicture;
			var property={
					multiple:false,
				    file_types:"*.jpg;*.gif",
				    btn_add_text:"添加",
				    btn_up_text:"上传",
				    btn_cancel_text:"放弃",
				    btn_clean_text:"清空",
				    op_del_text:"单项删除",
				    op_up_text:"单项上传",
				    op_fail_text:"上传失败",
				    op_ok_text:"上传成功",
				    op_no_text:"取消上传",
				upload_url:"information/mulUploadPhotoCompetitionPhoto.do;jsessionid="+sessionId,
				flash_url :"js/GooUploader/swfupload.swf"
			};
		    uploadPicture=$.createGooUploader($("#uploadPicture1"),property);
		    uploadPicture.$swfUpload.uploadSuccess=function(file,msg){
		        var id=file.id;
		        uploadPicture.$fileList[id].span.html("100%");
		        var li=uploadPicture.$content.children("#"+id);
		        li.children(".op_no").css("display","none");
		        li.children(".op_ok").css("display","block");
		        $('#pictureId',editDialog).val(msg);
		        $('#competititionPhoto',editForm).attr('src',ECMS.competitionPhotoPath+'/t_'+msg+'.png');
		    };
			initCombobox();
			$(editForm).form('clear');
			$(editForm).form('load',selectRow);
			$('#competition',editForm).combobox('setValue',selectRow.competitionId);
			$('#photoType',editForm).combobox('setValue',selectRow.photoType);
			$('#pictureId',editForm).val(selectRow.pictureId);
			$('#competititionPhoto',editForm).attr('src',ECMS.competitionPhotoPath+'/t_'+selectRow.pictureId+'.png');
			$(editDialog).dialog('open');
		}
	 }
	//查询
	var search = function(){
		var photoType = $('#photoTypeSearch',queryContent).combobox('getValue');
		var competitionId = $('#competitionSearchMain').combobox('getValue');
		var content = {photoType:photoType,'competition.competitionId':competitionId};
		
		$(viewList).datagrid({
			url:"information/queryCompetitionPhoto.do",
			queryParams:content
		});
		checkBtnRight();
	}
	//查询
	$('#search_'+id,$this).click(function(){
		search();
		return false;
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
				idArray.push(rows[i].competitionPhotoId);
			}
			var ids = idArray.join(CSIT.join);
			var url = "information/mulDeleteCompetitionPhoto.do";
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
	//移动
	var onMove = function(direction){
		if(selectRow == null){
			$.messager.alert('提示',"请选中一条记录","warning");
			return;
		}
		var rows  = $(viewList).datagrid('getRows');
		if(direction==-1){
			if(selectIndex==0){
				$.messager.alert('提示',"已经是第一条记录了","warning");
				return;
			}
		}else if(direction==1){//下移
			if(selectIndex==rows.length-1){
				$.messager.alert('提示',"已经是最后一条记录了","warning");
				return;
			}
		}
		var updateRow = rows[selectIndex+direction];
		
		var competitionPhotoId = selectRow.competitionPhotoId;
		var competitionId = selectRow.competitionId;
		var photoType = selectRow.photoType;
		var pictureId = selectRow.pictureId;
		var note = selectRow.note;
		var array = selectRow.array;
		//后台更新排序
		var url = "information/updateArrayCompetitionPhoto.do";
		var content = {competitionPhotoId:competitionPhotoId,updateCompetitionPhotoId:updateRow.competitionPhotoId};
		var result = syncCallService(url,content);
		if(result.isSuccess){
			$(viewList).datagrid('updateRow',{
				index: selectIndex,
				row: updateRow
			});
			$(viewList).datagrid('updateRow',{
				index: selectIndex+direction,
				row: {
					competitionPhotoId:competitionPhotoId,
					competitionId:competitionId,
					photoType:photoType,
					pictureId:pictureId,
					note:note,
					array:array
				}
			});
			$(viewList).datagrid('unselectAll');
			$(viewList).datagrid('selectRow',selectIndex+direction);
			selectIndex = selectIndex+direction;
			selectRow = $(viewList).datagrid('getSelected');
		}else{
			$.messager.alert('提示',result.message,"error");
		}
	};
	//批量添加赛事风采
	var mulAddDialog = $('#mulAddDialog_'+id,$this);
	var mulAddForm = $('#mulAddForm_'+id,$this);
	var pictureIdArray = null;//总添加的图片Id
	var pictureIdNewArray = null;//新添加的图片Id
	//编辑框
	  $(mulAddDialog).dialog({ 
			title:'批量新建赛事风采',
			width:680,
			height:height-10,
		    closed: true,  
		    cache: false,  
		    modal: true,
		    closable:false,
		    toolbar:[{
				text:'保存',
				iconCls:'icon-save',
				handler:function(){
					onMulSave();
				}
			},'-',{
				text:'退出',
				iconCls:'icon-cancel',
				handler:function(){
					$(mulAddDialog).dialog('close');
				}
			}],
			onClose:function(){
				$(mulAddDialog).form('clear');
				$('#pictureArea',mulAddDialog).html('');
			}
		});  
	//上传图片
	$('#uploadCompetitionPhoto_'+id).click(function(){
		$(editForm).form('submit',{
			url:'information/uploadPhotoCompetitionPhoto.do',
			onSubmit: function(){
				var file = $('#upload',editForm).val();
				if(file == ''){
					$.messager.alert('提示','请选择要上传的图片','warning');
					return false;
				}
				return true;
			},
			success: function(data){
				var result = eval('('+data+')');
				if(result.isSuccess){
					var fn = function(){
						var pictureId = result.data.pictureId;
						$('#competititionPhoto',editForm).attr('src',ECMS.competitionPhotoPath+'/'+pictureId+'.png');
						$('#pictureId',editForm).val(pictureId);
					}
					$.messager.alert('提示','上传成功','info',fn);
					$('#uploadCompetitionPhoto_'+id).dialog('close');
				}else{
					$.messager.alert('提示',result.message,"error");
				}
			}
		});
	})
	Array.prototype.indexOf = function(val) {
        for (var i = 0; i < this.length; i++) {
            if (this[i] == val) return i;
        }
        return -1;
    };
	Array.prototype.remove = function(val) {
        var index = this.indexOf(val);
        if (index > -1) {
            this.splice(index, 1);
        }
    };
	//----------上传图片--------------
	var uploadPictureDialog = $('#uploadPictureDialog_'+id,$this);
	$(uploadPictureDialog).dialog({ 
		title:'上传图片',
		width:316,
		height:366,
	    closed: true,  
	    cache: false,  
	    modal: true,
	    closable:false,
	    toolbar:[{
			text:'退出',
			iconCls:'icon-cancel',
			handler:function(){
				$(uploadPictureDialog).dialog('close');
			}
		}],
		onClose:function(){
			for ( var i = 0; i < pictureIdNewArray.length; i++) {
				$("#pictureArea").append('<li id="pic_'+pictureIdNewArray[i]+'"><img alt="" src="'+ ECMS.competitionPhotoPath+'/t_'+pictureIdNewArray[i]+'.png" style="margin: 10px 0px 0px 10px;width: 200px;height: 130px"><div class="text"><a href="javascript:void(0)" pictureId="'+pictureIdNewArray[i]+'"><h4>点击删除</h4></a></div></li>');
				pictureIdArray.push(pictureIdNewArray[i]);
			}
			$("#pictureArea  li").hover(function(){
				$(this).find(".text").stop().animate({left:'10'}, {duration: 500})
			},function(){
				$(this).find(".text").stop().animate({left:'200'}, {duration: "fast"})
				$(this).find(".text").animate({left:'-200'}, {duration: 0})
			});
			$("#pictureArea a").click(function(){
				var pictureId = $(this).attr('pictureId');
				$("#pic_"+pictureId).remove();
				pictureIdArray.remove(pictureId);
				var url="information/deletePicCompetitionPhoto.do";
				content={'picture.pictureId':pictureId};
				var result = syncCallService(url,content);
				if(result.isSuccess){
					 
				}else{
					$.messager.alert('提示',result.message,'error');
				}
			});
		}
	});
	$('#mulBtn_'+id).click(function(){
		var uploadPicture;
		$("#uploadPicture").html('');
		pictureIdNewArray = new Array();
		var property={
				width:300,
				height:300,
				multiple:true,
			    file_types:"*.jpg;*.gif;*.png",
			    file_types_description: "Web Image Files",
			    btn_add_text:"添加",
			    btn_up_text:"上传",
			    btn_cancel_text:"放弃",
			    btn_clean_text:"清空",
			    op_del_text:"单项删除",
			    op_up_text:"单项上传",
			    op_fail_text:"上传失败",
			    op_ok_text:"上传成功",
			    op_no_text:"取消上传",
			upload_url:"information/mulUploadPhotoCompetitionPhoto.do;jsessionid="+sessionId,
			flash_url :"js/GooUploader/swfupload.swf"
		};
	    uploadPicture=$.createGooUploader($("#uploadPicture"),property);
	    uploadPicture.$swfUpload.uploadSuccess=function(file,msg){
	        var id=file.id;
	        uploadPicture.$fileList[id].span.html("100%");
	        var li=uploadPicture.$content.children("#"+id);
	        li.children(".op_no").css("display","none");
	        li.children(".op_ok").css("display","block");
	        pictureIdNewArray.push(msg);
	    };
		$(uploadPictureDialog).dialog('open');
		return false;
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
		var tbStateBtn = $('#tbState_'+id,$this);
		var mulAddBtn = $('#mulAdd_'+id,$this);
		var moveUpBtn = $('#moveUp_'+id,$this);
		var moveDownBtn = $('#moveDown_'+id,$this);
		checkArray.push(addBtn);
		checkArray.push(updateBtn);
		checkArray.push(deleteBtn);
		checkArray.push(tbStateBtn);
		checkArray.push(mulAddBtn);
		checkArray.push(moveUpBtn);
		checkArray.push(moveDownBtn);
		checkRight(checkArray,rights);
	}
	//保存前的赋值操作
	var setMulValue = function(){
		var competition = $.trim($('#competition',mulAddDialog).combobox('getValue'));
		if(''==competition){
			$.messager.alert('提示','请选择赛事','warning');
			return false;
		}
		var photoType = $.trim($('#photoType',mulAddDialog).combobox('getValue'));
		if(''==photoType){
			$.messager.alert('提示','请选择类型','warning');
			return false;
		}
		if(pictureIdArray.length==0){
			$.messager.alert('提示','请上传要上传的图片','warning');
			return false;
		}
		$('#pictureIds',mulAddDialog).val(pictureIdArray.join(CSIT.join));
		return true;
	}
	//保存
	var onMulSave = function(){
		$(mulAddForm).form('submit',{
			url:'information/mulSaveCompetitionPhoto.do',
			onSubmit: function(){
				return setMulValue();
			},
			success: function(data){
				var result = eval('('+data+')');
				if(result.isSuccess){
					var fn = function(){
						$(mulAddDialog).dialog('close');
						search();
					};
					$.messager.alert('提示','保存成功','info',fn);
				}else{
					$.messager.alert('提示',result.message,"error");
				}
			}
		});
	}
	checkBtnRight();
  }
})(jQuery);   