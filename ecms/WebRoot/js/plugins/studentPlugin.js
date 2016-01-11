// 创建一个闭包  
(function($) {  
  // 插件的定义  
  $.fn.studentInit = function() {
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
	  
	  var enrollDialog = $('#enrollDialog_'+id,$this);
	  var enrollForm = $('#enrollForm_'+id,$this);
	  
	  var photoDialog = $('#photoDialog_'+id,$this);
	  var photoForm = $('#photoForm_'+id,photoDialog);
	  
	  var familyForm = $('#familyForm',$this);
	  
	  var isAdd = false;
	 
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
	          {field:'status',title:'状态',width:50,sortable:true,align:"center",
					formatter: function(value,row,index){
						if (value==0){
							return '<img src="style/v1/icons/warn.png"/>';
						} else if (value==1){
							return '<img src="style/v1/icons/info.png"/>';
						}
				 }},
			{field:'userCode',title:'登录名',width:100,align:"center"},
			{field:'studentName',title:'姓名',width:100,align:"center"},
			{field:'mobilePhone',title:'手机',width:150,align:"center"},
			{field:'position',title:'所在地区',width:150,align:"center",
				formatter:function(value,row,index){
					if(row.areaId==null){
						return '';
					}
					return row.provinceName+row.cityName+row.areaName;
				}
			},
			{field:'schoolGradeClazz',title:'学校年级班级',width:250,align:"center",
				formatter:function(value,row,index){
					if(row.schoolId==null){
						return '';
					}
					return row.schoolName+" "+row.gradeName+" "+row.clazzName;
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
	  
	//导入照片
	  $('#picture_'+id,$this).click(function(){
		  onImportPhoto();
	  });
	
	//查询
	var search = function(flag){
		var userCode = $('#codeSearch',queryContent).val();
		var studentName = $('#nameSearch',queryContent).val();
		var status = $('#statusSearch',queryContent).combobox('getValue');
		var content = {userCode:userCode,studentName:studentName,status:status};
		//取得列表信息
		$(viewList).datagrid({
			url:'enroll/queryStudent.do',
			queryParams:content
		});
		checkBtnRight();
	};
	//查询
	$('#search',$this).click(function(){
		search(true);
	});
	//初始化Combobox
	var initCombobox = function(){
		//民族
		$('#nation',editForm).combobox({
			width:200,
			data:ECMS.getNationList(),
			valueField:'nationId',
			textField:'nationName'
		});
		//省份
		$('#province',editForm).combobox({
			editable:false,
			width:200,
			url:'register/queryComboboxProvince.do',
			valueField:'provinceId',
			textField:'provinceName',
			onChange:function(newValue, oldValue){
				$('#city',editForm).combobox({
					editable:false,
					width:200,
					valueField:'cityId',
					textField:'cityName',
					url:'register/queryComboboxCity.do?province.provinceId='+newValue
				});
				$('#area',editForm).combobox('loadData',[]);
				$('#area',editForm).combobox('clear');
				
				$('#school',editForm).combobox('loadData',[]);
				$('#school',editForm).combobox('clear');
				
				$('#grade',editForm).combobox('loadData',[]);
				$('#grade',editForm).combobox('clear');
				
				$('#clazz',editForm).combobox('loadData',[]);
				$('#clazz',editForm).combobox('clear');
			}
		});
		//市
		$('#city',editForm).combobox({
			width:200,
			editable:false,
			onChange:function(newValue, oldValue){
				$('#area',editForm).combobox({
					editable:false,
					width:200,
					valueField:'areaId',
					textField:'areaName',
					url:'register/queryComboboxArea.do?city.cityId='+newValue
				});
				$('#school',editForm).combobox('loadData',[]);
				$('#school',editForm).combobox('clear');
				
				$('#grade',editForm).combobox('loadData',[]);
				$('#grade',editForm).combobox('clear');
				
				$('#clazz',editForm).combobox('loadData',[]);
				$('#clazz',editForm).combobox('clear');
			}
		});
		//区
		$('#area',editForm).combobox({
			width:200,
			editable:false,
			onChange:function(newValue, oldValue){
				$('#school',editForm).combobox({
					editable:false,
					valueField:'schoolId',
					textField:'schoolName',
					url:'register/queryComboboxSchool.do?area.areaId='+newValue
				});
				$('#grade',editForm).combobox('loadData',[]);
				$('#grade',editForm).combobox('clear');
				
				$('#clazz',editForm).combobox('loadData',[]);
				$('#clazz',editForm).combobox('clear');
			}
		});
		//学校
		$('#school',editForm).combobox({
			width:200,
			editable:false,
			onChange:function(newValue, oldValue){
				$('#grade',editForm).combobox({
					valueField:'schoolGradeId',
					textField:'gradeName',
					url:'register/queryComboboxSG.do?school.schoolId='+newValue
				});
				
				$('#clazz',editForm).combobox('loadData',[]);
				$('#clazz',editForm).combobox('clear');
			}
		});
		//年级
		$('#grade',editForm).combobox({
			width:200,
			editable:false,
			onChange:function(newValue, oldValue){
				$('#clazz',editForm).combobox({
					valueField:'schoolGradeClazzId',
					textField:'clazzName',
					url:'register/queryComboboxSGC.do?schoolGrade.schoolGradeId='+newValue
				});
			}
		});
		//班级
		$('#clazz',editForm).combobox({
			width:200,
			editable:false
		});
		//照片
		$('#photo',editDialog).attr('src','');
	};
	//编辑框 
	$(editDialog).dialog({  
	    title: '编辑学生',  
	    width:850,
	    height:500,
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
	//保存前的赋值操作
	var setValue = function(){
		var studentName = $('#studentName',editForm).val();
		if(''==studentName){
			$.messager.alert('提示','请填写学生姓名','warning');
			return false;
		}
		var studentPinYinName = $('#studentPinYinName',editForm).val();
		if(''==studentPinYinName){
			$.messager.alert('提示','请填写学生姓名拼音','warning');
			return false;
		}
		var nation = $('#nation',editForm).combobox('getValue');
		if(''==nation){
			$.messager.alert('提示','请选择民族','warning');
			return false;
		}
		var birthday = $('#birthday',editForm).val();
		if(''==birthday){
			$.messager.alert('提示','请选择出生日期','warning');
			return false;
		}
		var phone = $('#phone',editForm).val();
		if(''!=phone&&!(/(^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$)|(^((\(\d{3}\))|(\d{3}\-))?(1[358]\d{9})$)/.test(phone))){
			$.messager.alert('提示','家庭电话格式错误','warning');
			return false;
		}
		var idNumber = $('#idNumber',editForm).val();
		if(''!=idNumber&&!(/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(idNumber))){
			$.messager.alert('提示','身份证号格式错误','warning');
			return false;
		}
		
		var mobilePhone = $('#mobilePhone',editForm).val();
		if(''==mobilePhone){
			$.messager.alert('提示','请填写手机号码','warning');
			return false;
		}else if(!/(^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$)|(^((\(\d{3}\))|(\d{3}\-))?(1[358]\d{9})$)/.test(mobilePhone)){
			$.messager.alert('提示','手机号码格式错误','warning');
			return false;
		}
		var email = $('#email',editForm).val();
		if(''!=email&&(!/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(email))){
			$.messager.alert('提示','邮箱格式错误','warning');
			return false;
		}
		var province = $('#province',editForm).combobox('getValue');
		if(''==province){
			$.messager.alert('提示','请选择省份','warning');
			return false;
		}
		var city = $('#city',editForm).combobox('getValue');
		if(''==city){
			$.messager.alert('提示','请选择城市','warning');
			return false;
		}
		var area = $('#area',editForm).combobox('getValue');
		if(''==area){
			$.messager.alert('提示','请选择地区','warning');
			return false;
		}
		var school = $('#school',editForm).combobox('getValue');
		if(''==school){
			$.messager.alert('提示','请选择学校','warning');
			return false;
		}
		var grade = $('#grade',editForm).combobox('getValue');
		if(''==grade){
			$.messager.alert('提示','请选择年级','warning');
			return false;
		}
		var clazz = $('#clazz',editForm).combobox('getValue');
		if(''==clazz){
			$.messager.alert('提示','请选择班级','warning');
			return false;
		}
		var address = $('#address',editForm).val();
		if(''==address){
			$.messager.alert('提示','请填写家庭地址','warning');
			return false;
		}
		var userCode = $('#userCode',editForm).val();
		if(''==userCode){
			$.messager.alert('提示','请填写登录名','warning');
			return false;
		}
		if(isAdd){
			var userPwd = $('#userPwd',editForm).val();
			if(''==userPwd){
				$.messager.alert('提示','请填写密码','warning');
				return false;
			}
		}
		var familyMember = $('#familyMember',familyForm).val();
		if(''==familyMember){
			$.messager.alert('提示','请填写家庭成员','warning');
			return false;
		}
		var familyMemberName = $('#familyMemberName',familyForm).val();
		if(''==familyMemberName){
			$.messager.alert('提示','请填写家庭成员姓名','warning');
			return false;
		}
		var familyMemberPhone = $('#phone',familyForm).val();
		if(''==familyMemberPhone){
			$.messager.alert('提示','请填写家庭成员联系电话','warning');
			return false;
		}else if(!/(^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$)|(^((\(\d{3}\))|(\d{3}\-))?(1[358]\d{9})$)/.test(familyMemberPhone)){
			$.messager.alert('提示','家庭成员联系电话格式错误','warning');
			return false;
		}
		var familyMemberAge = $('#familyMemberAge',familyForm).val();
		var workUnitsAndPosition = $('#workUnitsAndPosition',familyForm).val();
		var familyMemberEmail = $('#email',familyForm).val();
		if(''!=familyMemberEmail&&(!/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(email))){
			$.messager.alert('提示','家庭成员邮箱格式错误','warning');
			return false;
		}
		var familyMemberList = new Array();
		familyMemberList.push(familyMember);
		familyMemberList.push(familyMemberName);
		if(''==familyMemberAge){
			familyMemberList.push(-1);
		}else{
			familyMemberList.push(familyMemberAge);
		}
		if(''==workUnitsAndPosition){
			familyMemberList.push(-1);
		}else{
			familyMemberList.push(workUnitsAndPosition);
		}
		familyMemberList.push(familyMemberPhone);
		if(''==familyMemberEmail){
			familyMemberList.push(-1);
		}else{
			familyMemberList.push(familyMemberEmail);
		}
		var familyMemberStr = familyMemberList.join('^');
		$('#familyMemberInfo',editForm).val(familyMemberStr);

		$('#kindSave',editForm).val('saveByAdmin');

		return true;
	};
	//保存
	var onSave = function(){
		$(editForm).form('submit',{
			url:'enroll/saveStudent.do',
			onSubmit: function(){
				return setValue();
			},
			success: function(data){
				var result = eval('('+data+')');
				if(result.isSuccess){
					var fn = function(){
						search(true);
					};
					$.messager.alert('提示','保存成功','info',fn);
					$(editDialog).dialog('close');
				}else{
					$.messager.alert('提示',result.message,"error");
				}
			}
		});
	};
	
	//添加
	var onAdd = function(){
		$(editForm).form('clear');
		$('input',familyForm).val('');
		initCombobox();
		$('#sex',editForm).combobox('setValue',0);
		isAdd = true;
		$(editDialog).dialog('open');
	};
	
	//修改
	var onUpdate = function(){
		if(!$('#update_'+id,$this).is(":hidden")){
			if(selectRow==null){
				$.messager.alert("提示","请选择数据行","warning");
				return;
			}
			$(editForm).form('clear');
			$('input',familyForm).val('');
			initCombobox();
			$(editForm).form('load',selectRow);
			$('#nation',editForm).combobox('setValue',selectRow.nationId);
			$('#province',editForm).combobox('setValue',selectRow.provinceId);
			$('#city',editForm).combobox('setValue',selectRow.cityId);
			$('#area',editForm).combobox('setValue',selectRow.areaId);
			$('#school',editForm).combobox('setValue',selectRow.schoolId);
			$('#grade',editForm).combobox('setValue',selectRow.schoolGradeId);
			$('#clazz',editForm).combobox('setValue',selectRow.schoolGradeClazzId);
			
			//获取照片
			var url = 'enroll/getPictureStudent.do';
			var content = {studentId:selectRow.studentId};
			var result = syncCallService(url,content);
			if(result.isSuccess){
				var picturePath = result.data.picturePath;
				$('#picture',editDialog).attr('src',picturePath);
			}else{
				$.messager.alert('提示',result.message,"error");
			}
			$(familyForm).form('clear');
			//获取家庭成员
			var result2 = syncCallService(
					'enroll/initStudentFamilyMember.do',
					{'student.studentId':selectRow.studentId}
					);
			$(familyForm).form('load',result2);
			isAdd = false;
			$(editDialog).dialog('open');
		}
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
				idArray.push(rows[i].studentId);
			}
			var ids = idArray.join(CSIT.join);
			var url = "enroll/mulDeleteStudent.do";
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
	//修改多个审核状态
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
			idArray.push(rows[i].studentId);
		}
		$.messager.confirm("提示","确定要"+msg+"记录?",function(t){ 
			if(t){
				var url = 'enroll/mulUpdateStateStudent.do';
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
	
	$(photoDialog).dialog({
		title: '导入照片',  
	    width:400,
	    height:200,
	    closed: true,  
	    cache: false,  
	    modal: true,
	    closable:false,
	    toolbar:[{
			text:'上传',
			iconCls:'icon-save',
			handler:function(){
				uploadPhoto();
			}
		},'-',{
			text:'退出',
			iconCls:'icon-cancel',
			handler:function(){
				$(photoDialog).dialog('close');
			}
		}]
	});
	
	var onImportPhoto = function(){
		if(selectRow==null){
			$.messager.alert("提示","请选择数据行","warning");
			return;
		}
		$(photoForm).form('clear');
		$(photoForm).form('load',selectRow);
		$('#pictureId',photoForm).val(selectRow.pictureId);
		$(photoDialog).dialog('open');
	};
	
	var uploadPhoto = function(){
		$(photoForm).form('submit',{
			url:'enroll/uploadPictureStudent.do',
			onSubmit: function(){
				var upload = $('#upload',photoForm).val();
				if(upload == ''){
					$.messager.alert('提示','请选择要导入的图片','warning');
					return false;
				}
				return true;
			},
			success: function(data){
				var result = eval('('+data+')');
				if(result.isSuccess){
					$.messager.alert('提示','导入成功','info');
					$(photoDialog).dialog('close');
				}else{
					$.messager.alert('提示',result.message,"error");
				}
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
		var pictureBtn = $('#picture_'+id,$this);
		checkArray.push(addBtn);
		checkArray.push(updateBtn);
		checkArray.push(deleteBtn);
		checkArray.push(tbStateBtn);
		checkArray.push(pictureBtn);
		checkRight(checkArray,rights);
	};
	checkBtnRight();
	$(this).mask('hide');
  };
})(jQuery);   