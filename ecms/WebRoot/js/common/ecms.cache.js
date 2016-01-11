var ECMS={};
ECMS.competitionPhotoPath = 'upload/image/competitionPhoto';
//教师列表
ECMS.TeacherList = null;
ECMS.getTeacherList = function(){
	if(ECMS.TeacherList==null){
		var url = 'system/queryComboboxTeacher.do';
		ECMS.TeacherList = syncCallService(url,null);
	}
	return ECMS.TeacherList;
};
//当前操作员
ECMS.TeacherId = null;
ECMS.getTeacherId = function(){
	if(ECMS.TeacherId==null){
		var url = 'system/getCurrTeacher.do';
		var result = syncCallService(url,null);
		if(result.isSuccess){
			var data = result.data;
			ECMS.TeacherId = data.teacherId;
			ECMS.TeacherName = data.teacherName;
		}else{
			$.messager.alert('提示',result.message,'error');
		}
	}
	return ECMS.TeacherId;
};
ECMS.TeacherName = null;
ECMS.getTeacherName = function(){
	if(ECMS.TeacherName==null){
		var url = 'system/getCurrTeacher.do';
		var result = syncCallService(url);
		if(result.isSuccess){
			var data = result.data;
			ECMS.TeacherId = data.teacherId;
			ECMS.TeacherName = data.teacherName;
		}else{
			$.messager.alert('提示',result.message,'error');
		}
	}
	return ECMS.TeacherName;
};
//参赛组别列表
ECMS.GroupList = null;
ECMS.getGroupList = function(){
	if(ECMS.GroupList==null){
		var url = 'dict/queryComboboxGroup.do';
		var content ={status:1};
		ECMS.GroupList = syncCallService(url,content);
	}
	return ECMS.GroupList;
};
//民族
ECMS.NationList = null;
ECMS.getNationList = function(){
	if(ECMS.NationList==null){
		var url = 'dict/queryComboboxNation.do';
		ECMS.NationList = syncCallService(url,null);
	}
	return ECMS.NationList;
};
//省份
ECMS.ProvinceList = null;
ECMS.getProvinceList = function(){
	if(ECMS.ProvinceList==null){
		var url = 'dict/queryComboboxProvince.do';
		ECMS.ProvinceList = syncCallService(url,null);
	}
	return ECMS.ProvinceList;
};
//省份下的市
ECMS.getCityList = function(provinceId){
	var url = 'dict/queryComboboxCity.do';
	var content = {'province.provinceId':provinceId};
	return syncCallService(url,content);;
};
//市下的区
ECMS.getAreaList = function(cityId){
	var url = 'dict/queryComboboxArea.do';
	var content = {'city.cityId':cityId};
	return syncCallService(url,content);
};
//赛事
ECMS.CompetitionList = null;
ECMS.getCompetitionList = function(){
	if(ECMS.CompetitionList==null){
		var url = 'competition/queryComboboxCompetition.do';
		var content ={status:1};
		ECMS.CompetitionList = syncCallService(url,content);
	}
	return ECMS.CompetitionList;
};
//赛事
ECMS.CompetitionGroupList = null;
ECMS.getCompetitionGroupList = function(competitionId){
	var url = 'competition/queryComboboxCompetitionGroup.do';
	var content ={'competition.competitionId':competitionId};
	ECMS.CompetitionGroupList = syncCallService(url,content);
	return ECMS.CompetitionGroupList;
};
//资讯类型
ECMS.CategoryList = [{"category":"大赛章程"},{"category":"大赛公告"}];
ECMS.getCategoryList = function(){
	return ECMS.CategoryList;
};
//赛事风采
ECMS.PhotoTypeList = [{"photoType":"选手风采"},{"photoType":"主持人风采"}];
ECMS.getPhotoTypeList = function(){
	return ECMS.PhotoTypeList;
};

//教材
ECMS.GoodsList = null;
ECMS.getGoodsList = function(){
	if(ECMS.GoodsList==null){
		var url = 'goods/queryComboboxGoods.do';
		ECMS.GoodsList = syncCallService(url,null);
	}
	return ECMS.GoodsList;
};
//培训班级
ECMS.TrainingClassList = null;
ECMS.getTrainingClassList = function(){
	if(ECMS.TrainingClassList==null){
		var url = 'trainingClass/queryComboboxTrainingClass.do';
		ECMS.TrainingClassList = syncCallService(url,null);
	}
	return ECMS.TrainingClassList;
};
//支出类型列表
ECMS.ExpendTypeList = null;
ECMS.getExpendTypeList = function(){
	if(ECMS.ExpendTypeList==null){
		var url = 'dict/queryComboboxExpendType.do';
		var content ={status:1};
		ECMS.ExpendTypeList = syncCallService(url,content);
	}
	return ECMS.ExpendTypeList;
};
//缴费类型列表
ECMS.PayTypeList = null;
ECMS.getPayTypeList = function(){
	if(ECMS.PayTypeList==null){
		var url = 'dict/queryComboboxPayType.do';
		var content ={status:1};
		ECMS.PayTypeList = syncCallService(url,content);
	}
	return ECMS.PayTypeList;
};
//考场
ECMS.StadiumList = null;
ECMS.getStadiumList = function(){
	if(ECMS.StadiumList==null){
		var url = 'stadium/queryComboboxStadium.do';
		ECMS.StadiumList = syncCallService(url,null);
	}
	return ECMS.StadiumList;
};
//学生
ECMS.StudentList = null;
ECMS.getStudentList = function(){
	if(ECMS.StudentList==null){
		var url = 'enroll/queryComboboxStudent.do';
		ECMS.StudentList = syncCallService(url,null);
	}
	return ECMS.StudentList;
};
