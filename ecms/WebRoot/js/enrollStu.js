$(function(){
	var editForm = $('#editForm');
	$('#competitionId').val(currCompetitionId);
	
	//省份
	$('#province').combobox({
		width:250,
		url:'register/queryComboboxProvince.do',
		valueField:'provinceId',
		textField:'provinceName',
		onSelect:function(record){
			$('#city').combobox({
				url:'register/queryComboboxCity.do?province.provinceId='+record.provinceId
			});
		}
	});
	//市
	$('#city').combobox({
		width:250,
		valueField:'cityId',
		textField:'cityName',
		onSelect:function(record){
			$('#area').combobox({
				url:'register/queryComboboxArea.do?city.cityId='+record.cityId
			});
		}
	});
	//区
	$('#area').combobox({
		width:250,
		valueField:'areaId',
		textField:'areaName'
	});
	
	var url = 'student/initEnrollStu.do?competition.competitionId='+currCompetitionId;
	var result = syncCallService(url);
	if(result.isSuccess){
		var initData = eval('('+result.data.initData+')');;
		var groupData =  eval('('+result.data.groupData+')');;
		$('#competition').html(initData.competitionName);
		$('#province').combobox('select',initData.provinceId);
		$('#city').combobox({
			url:'register/queryComboboxCity.do?province.provinceId='+initData.provinceId
		});
		$('#city').combobox('setValue',initData.cityId);
		$('#area').combobox({
			url:'register/queryComboboxArea.do?city.cityId='+initData.cityId
		});
		$('#area').combobox('setValue',initData.areaId);
		
		//赛事组别
		$('#competitionGroup').combobox({
			width:250,
			data:groupData,
			valueField:'competitionGroupId',  
	        textField:'groupName'
		});
	}else{
		$.messager.alert('提示',result.message,"error");
	}
	
	$('#submit').click(function(){
		onSave();
	});
	
	var setValue = function(){
		var province = $('#province').combobox('getValue');
		var city = $('#city').combobox('getValue');
		var area = $('#area').combobox('getValue');
		if(''==province||''==city||''==area){
			$.messager.alert('提示','请选择参赛地区','warning');
			return false;
		}
		var competitionGroupId = $('#competitionGroup').combobox('getValue');
		if(''==competitionGroupId){
			$.messager.alert('提示','请填写参赛组别','warning');
			return false;
		}
		$('#status').val(0);
		return true;
	};
	
	var onSave = function(){
		$(editForm).form('submit',{
			url:'saveStudentCompetitionGroup.do',
			onSubmit: function(){
				return setValue();
			},
			success: function(data){
				var result = eval('('+data+')');
				if(result.isSuccess){
					var fn = function(){
						window.location.href = 'student/initMyEnroll.do?infoType=myEnroll&competitionId='+currCompetitionId;
					};
					$.messager.alert('提示',result.message,'info',fn);
				}else{
					$.messager.alert('提示',result.message,"error");
				}
			}
		});
	};
	
	$('#back').click(function(){
		window.history.back();
	});
});

