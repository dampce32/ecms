<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="indexTitle.jsp"></jsp:include>
	
<div class="main">
  <div class="slider_top">
    <div class="header_text2">
      <h2>个人账户</h2>
     <div class="clr"></div>
    </div>
  </div>
  <div class="top_bg2">
   <div class="clr"></div>
  </div>
  <div class="clr"></div>
  	<div class="contentBd">
	<div class="contentBd" style="float:left;width: 910px;height: 100%">
		
        <div class="span2" style="width:170px;">
          <div class="well sidebar-nav">
          	<div class="cell" style="width:120px;height:150px;text-align:center;margin-bottom: 10px">
				<div style="border:solid 1px;text-align:center;">
					<img id="myPicture" alt="个人头像" src="" style="width:120px;height:120px;">
				</div>
				<div style="margin-top: 5px;">
					<input type="button" id="updatePicture" class="btn btn-primary btn-small" value="修改头像"/>
				</div>
			</div>
            <ul class="nav nav-list">
              <li class="infoType"><a href="student/initMyAccount.do?infoType=myAccount" id="base" infoType="myAccount">基本信息</a></li>
              <li class="infoType" ><a href="student/initMyEnroll.do?infoType=myEnroll" id="enroll" infoType="myEnroll">我的报名</a></li>
              <li class="infoType" ><a href="student/initExamAnswer.do?infoType=examAnswer" id="exam" infoType="examAnswer">我的在线考试</a></li>
              <li class="infoType" ><a href="student/initMyTrainingClass.do?infoType=myTrainingClass" id="train" infoType="myTrainingClass">我的培训班级</a></li>
              <li class="infoType" ><a href="student/initMyStadium.do?infoType=myStadium" id="stadium" infoType="myStadium">我的赛场</a></li>
            </ul>
          </div>
        </div>
		<div class="span8" style="width:700px;" >
			<c:if test="${infoType == 0}">
				<div style="text-align:center;">个人信息</div>
				<br/>
				<form id="baseInfoForm" method="post">
					<input type="hidden" name="familyMemberInfo" id="familyMemberInfo"/>
					<input type="hidden" name="studentId" value="${student.studentId}"/>
					<input type="hidden" name="userCode" value="${student.userCode}"/>
					<input type="hidden" name="kindSave" id="kindSave"/>
					<table id="myBaseInfoTable" class="baseInfoCSS" border="0" cellpadding="0" cellspacing="0" align="center">
						<tr>
				    		<td class="tdTitle">学生姓名：</td>
				    		<td class="tdContent">
				    			<input type="text" name="studentName" id="studentName" value="${student.studentName}" class="contentInput"/>
				    		</td>
				    		<td class="tdTitle">性别：</td>
				    		<td class="tdContent">
				    			<select name="sex" id="sex">
				    				<option value="0" <c:if test="${student.sex == 0}">selected</c:if>>男</option>
				    				<option value="1" <c:if test="${student.sex == 1}">selected</c:if>>女</option>
				    			</select>
				    		</td>
				    	</tr>
				    	<tr>
				    		<td class="tdTitle">出生日期：</td>
				    		<td class="tdContent">
				    			<input type="text" name="birthday" id="birthday" value="${student.birthday}" class="contentInput Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
				    		</td>
				    		<td class="tdTitle">民族：</td>
				    		<td class="tdContent">
					    		<select name="nation.nationId" id="nation">
					    			<c:forEach items="${nationList}" var="nation">
					    				<option value="${nation.nationId}" <c:if test="${student.nation.nationId == nation.nationId}">selected</c:if>>${nation.nationName}</option>
					    			</c:forEach>
					    		</select>
				    		</td>
				    	</tr>
				    	<tr>
				    		<td class="tdTitle">家庭电话：</td>
				    		<td class="tdContent">
				    			<input type="text" name="phone" id="phone" value="${student.phone}" class="contentInput"/>
				    		</td>
				    		<td class="tdTitle">身份证号：</td>
				    		<td class="tdContent">
				    			<input type="text" name="idNumber" id="idNumber" value="${student.idNumber}" class="contentInput"/>
				    		</td>
				    	</tr>
				    	<tr>
				    		<td class="tdTitle">手机号码：</td>
				    		<td class="tdContent">
				    			<input type="text" name="mobilePhone" id="mobilePhone" value="${student.mobilePhone}" style="border:0" readonly="readonly" class="contentInput"/>
				    		</td>
				    		<td class="tdTitle">E-Mail：</td>
				    		<td class="tdContent">
				    			<input type="text" name="email" id="email" value="${student.email}" style="border:0" readonly="readonly" class="contentInput"/>
				    		</td>
				    	</tr>
				    	<tr>
				    		<td class="tdTitle"></td>
				    		<td class="tdContent" style="padding-bottom:10px">
				    			<input type="button" class="btn btn-primary btn-small" value="更改绑定手机" id="mobilePhoneBinding"/></td>
				    		</td>
				    		<td class="tdTitle"></td>
				    		<td class="tdContent" style="padding-bottom:10px">
				    			<input type="button" class="btn btn-primary btn-small" value="更改绑定邮箱" id="emailBinding"/></td>
				    		</td>
				    	</tr>
				    	<tr>
				    		<td class="tdTitle">所在省份：</td>
				    		<td class="tdContent">
					    		<select id="province">
				    				<c:forEach items="${provinceList}" var="province">
					    				<option value="${province.provinceId}" <c:if test="${student.area.province.provinceId == province.provinceId}">selected</c:if>>${province.provinceName}</option>
					    			</c:forEach>
				    			</select>
				    		</td>
				    		<td class="tdTitle">所在市：</td>
				    		<td class="tdContent">
					    		<select id="city">
				    			</select>
				    		</td>
				    	</tr>
				    	<tr>
				    		<td class="tdTitle">所在区/县：</td>
				    		<td class="tdContent">
					    		<select name="area.areaId" id="area">
				    			</select>
				    		</td>
				    		<td class="tdTitle">学校：</td>
				    		<td class="tdContent">
				    			<select id="school">
				    			</select>
				    		</td>
				    	</tr>
				    	<tr>
				    		<td class="tdTitle">年级：</td>
				    		<td class="tdContent">
				    			<select id="grade">
				    			</select>
				    		</td>
				    		<td class="tdTitle">班级：</td>
				    		<td class="tdContent">
				    			<select name="schoolGradeClazz.schoolGradeClazzId" id="clazz">
				    			</select>
				    		</td>
				    	</tr>
				    	<tr>
				    		<td class="tdTitle">家庭地址：</td>
				    		<td colspan="3">
				    			<input type="text" name="address" id="address" value="${student.address}" style="width:554px;"/>
				    		</td>
				    	</tr>
				    	
					</table>
				</form>
				<div id="mobilePhoneBindingDialog" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="width:400px;height:200px">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
					<center>
						<div style="margin-top:50px" id="sendDiv">
							<div>
								新手机号码：
								<input type="text" id="newMobilePhone"/></td>
							</div>
							<div id="sendMsg" style="color:red"></div>
							<div style="margin-top:30px;">
								<input class="btn btn-primary" type="button" value="发送验证短信" id="mobilePhoneSend"/></td>
							</div>
						</div>
						<div style="margin-top:50px" id="validateDiv">
							<div>
								验证码：
								<input type="text" id="mobilePhoneCode"/></td>
							</div>
							<div id="validateMsg" style="color:red"></div>
							<div style="margin-top:30px;">
								<input class="btn btn-primary" type="button" value="确认" id="mobilePhoneValidate"/></td>
							</div>
						</div>
					</center>
				</div>
				
				<div id="emailBindingDialog" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="width:400px;height:200px">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
					<center>
						<div style="margin-top:50px" id="sendDiv">
							<div>
								新邮箱地址：
								<input type="text" id="newEmail"/></td>
							</div>
							<div id="sendMsg" style="color:red"></div>
							<div style="margin-top:30px;">
								<input class="btn btn-primary" type="button" value="发送验证邮件" id="emailSend"/></td>
							</div>
						</div>
						<div style="margin-top:50px" id="validateDiv">
							<div>
								验证码：
								<input type="text" id="emailCode"/></td>
							</div>
							<div id="validateMsg" style="color:red"></div>
							<div style="margin-top:30px;">
								<input class="btn btn-primary" type="button" value="确认" id="emailValidate"/></td>
							</div>
						</div>
					</center>
				</div>
				<script type="text/javascript">
					var initCity = function(provinceId){
						$("#city","#myBaseInfoTable").append("<option value=''>======请选择======</option>");
						var cityList = syncCallService("student/queryComboboxCity.do",{"province.provinceId":provinceId});
						for(var i=0;i<cityList.length;i++){
							$("#city","#myBaseInfoTable").append("<option value='"+cityList[i].cityId+"'>"+cityList[i].cityName+"</option>");
						}
					};
					var initArea = function(cityId){
						$("#area","#myBaseInfoTable").append("<option value=''>======请选择======</option>");
						var areaList = syncCallService("student/queryComboboxArea.do",{"city.cityId":cityId});
						for(var i=0;i<areaList.length;i++){
							$("#area","#myBaseInfoTable").append("<option value='"+areaList[i].areaId+"'>"+areaList[i].areaName+"</option>");
						}
					};
					var initSchool = function(areaId){
						$("#school","#myBaseInfoTable").append("<option value=''>======请选择======</option>");
						var schoolList = syncCallService("student/queryComboboxSchool.do",{"area.areaId":areaId});
						for(var i=0;i<schoolList.length;i++){
							$("#school","#myBaseInfoTable").append("<option value='"+schoolList[i].schoolId+"'>"+schoolList[i].schoolName+"</option>");
						}
					};
					var initSchoolGrade = function(schoolId){
						$("#grade","#myBaseInfoTable").append("<option value=''>======请选择======</option>");
						var gradeList = syncCallService("student/queryComboboxSG.do",{"school.schoolId":schoolId});
						for(var i=0;i<gradeList.length;i++){
							$("#grade","#myBaseInfoTable").append("<option value='"+gradeList[i].schoolGradeId+"'>"+gradeList[i].gradeName+"</option>");
						}
					};
					var initSchoolGradeClazz = function(gradeId){
						$("#clazz","#myBaseInfoTable").append("<option value=''>======请选择======</option>");
						var clazzList = syncCallService("student/queryComboboxSGC.do",{"schoolGrade.schoolGradeId":gradeId});
						for(var i=0;i<clazzList.length;i++){
							$("#clazz","#myBaseInfoTable").append("<option value='"+clazzList[i].schoolGradeClazzId+"'>"+clazzList[i].clazzName+"</option>");
						}
					};
					
					var provinceId = $("#province","#myBaseInfoTable").val();
					if(""!=provinceId){
						initCity(provinceId);
						$("#city option[value='${student.area.city.cityId}']","#myBaseInfoTable").attr("selected", true);
					}
					var cityId = $("#city","#myBaseInfoTable").val();
					if(""!=cityId){
						initArea(cityId);
						$("#area option[value='${student.area.areaId}']","#myBaseInfoTable").attr("selected", true);
					}
					var areaId = $("#area","#myBaseInfoTable").val();
					if(""!=areaId){
						initSchool(areaId);
						$("#school option[value='${student.schoolGradeClazz.schoolGrade.school.schoolId}']","#myBaseInfoTable").attr("selected", true);
					}
					var schoolId = $("#school","#myBaseInfoTable").val();
					if(""!=schoolId){
						initSchoolGrade(schoolId);
						$("#grade option[value='${student.schoolGradeClazz.schoolGrade.schoolGradeId}']","#myBaseInfoTable").attr("selected", true);
					}
					var schoolGradeId = $("#grade","#myBaseInfoTable").val();
					if(""!=schoolGradeId){
						initSchoolGradeClazz(schoolGradeId);
						$("#clazz option[value='${student.schoolGradeClazz.schoolGradeClazzId}']","#myBaseInfoTable").attr("selected", true);
					}
					
					$("#province","#myBaseInfoTable").change(function(){
						$("#city","#myBaseInfoTable").empty();
						$("#area","#myBaseInfoTable").empty();
						$("#school","#myBaseInfoTable").empty();
						$("#grade","#myBaseInfoTable").empty();
						$("#clazz","#myBaseInfoTable").empty();
						initCity($(this).val());
					});
					$("#city","#myBaseInfoTable").change(function(){
						$("#area","#myBaseInfoTable").empty();
						$("#school","#myBaseInfoTable").empty();
						$("#grade","#myBaseInfoTable").empty();
						$("#clazz","#myBaseInfoTable").empty();
						initArea($(this).val());
					});
					$("#area","#myBaseInfoTable").change(function(){
						$("#school","#myBaseInfoTable").empty();
						$("#grade","#myBaseInfoTable").empty();
						$("#clazz","#myBaseInfoTable").empty();
						initSchool($(this).val());
					});
					$("#school","#myBaseInfoTable").change(function(){
						$("#grade","#myBaseInfoTable").empty();
						$("#clazz","#myBaseInfoTable").empty();
						initSchoolGrade($(this).val());
					});
					$("#grade","#myBaseInfoTable").change(function(){
						$("#clazz","#myBaseInfoTable").empty();
						initSchoolGradeClazz($(this).val());
					});
					
					var mobilePhoneBindingDialog = $("#mobilePhoneBindingDialog");
					var emailBindingDialog = $("#emailBindingDialog");
					$("#mobilePhoneBinding").click(function(){
						$("#newMobilePhone",mobilePhoneBindingDialog).val("");
						$("#mobilePhoneCode",mobilePhoneBindingDialog).val("");
						$("#validateDiv",mobilePhoneBindingDialog).hide();
						$(mobilePhoneBindingDialog).modal("show");
					});
					$("#mobilePhoneSend",mobilePhoneBindingDialog).click(function(){
						var newMobilePhone = $("#newMobilePhone",mobilePhoneBindingDialog).val();
						var result = syncCallService("student/newMobilePhoneSend.do",{mobilePhone:newMobilePhone});
						alert(result.isSuccess);
						if(result.isSuccess){
							$("#sendDiv",mobilePhoneBindingDialog).hide();
							$("#validateDiv",mobilePhoneBindingDialog).show();
						}else{
							$("#sendMsg",mobilePhoneBindingDialog).html(result.message);
						}
					});
					$("#mobilePhoneValidate",mobilePhoneBindingDialog).click(function(){
						var mobilePhoneCode = $("#mobilePhoneCode",mobilePhoneBindingDialog).val();
						var result = syncCallService("student/newMobilePhoneConfirm.do",{validateCode:mobilePhoneCode});
						if(result.isSuccess){
							var newMobilePhone = $("#newMobilePhone",mobilePhoneBindingDialog).val();
							$("#mobilePhone","#myBaseInfoTable").val(newMobilePhone);
							$(mobilePhoneBindingDialog).modal("hide");
						}else{
							$("#validateMsg",mobilePhoneBindingDialog).html(result.message);
						}
					});
					$("#emailBinding").click(function(){
						$("#newEmail",emailBindingDialog).val("");
						$("#emailCode",emailBindingDialog).val("");
						$("#validateDiv",emailBindingDialog).hide();
						$(emailBindingDialog).modal("show");
					});
					$("#emailSend",emailBindingDialog).click(function(){
						var newEmail = $("#newEmail",emailBindingDialog).val();
						var result = syncCallService("student/newEmailSend.do",{email:newEmail});
						if(result.isSuccess){
							$("#sendDiv",emailBindingDialog).hide();
							$("#validateDiv",emailBindingDialog).show();
						}else{
							$("#sendMsg",emailBindingDialog).html(result.message);
						}
					});
					$("#emailValidate",emailBindingDialog).click(function(){
						var emailCode = $("#emailCode",emailBindingDialog).val();
						var result = syncCallService("student/newEmailConfirm.do",{validateCode:emailCode});
						if(result.isSuccess){
							var newEmail = $("#newEmail",emailBindingDialog).val();
							$("#email","#myBaseInfoTable").val(newEmail);
							$(emailBindingDialog).modal("hide");
						}else{
							$("#validateMsg",emailBindingDialog).html(result.message);
						}
					});
				</script>
				<br/>
				<div style="text-align:center;">家庭成员信息</div>
				<table id="myFamilyInfoTable" class="baseInfoCSS" border="0" cellpadding="0" cellspacing="0" align="center">
					<c:forEach items="${familyMemberList}" var="familyMember">
						<tr>
				    		<td class="tdTitle">家庭成员：</td>
				    		<td colspan="2" class="tdContent">
				    			<input type="text" name="familyMember" id="familyMember" value="${familyMember.familyMember}" class="contentInput"/>
				    		</td>
				    		<td class="tdTitle">姓名：</td>
				    		<td colspan="2" class="tdContent">
				    			<input type="text" name="familyMemberName" id="familyMemberName" value="${familyMember.familyMemberName}" class="contentInput"/>
				    		</td>
				    	</tr>
				    	<tr>
				    		<td class="tdTitle">年龄：</td>
				    		<td style="width: 70px">
				    			<input type="text" name="familyMemberAge" id="familyMemberAge" value="${familyMember.familyMemberAge}" style="height:15px;width:60px" class="easyui-numberbox" data-options="min:0"/>
				    		</td>
				    		<td style="width:200px;text-align: right;">工作单位及职务：</td>
				    		<td colspan="3" style="width: 320px">
				    			<input type="text" id="workUnitsAndPosition" value="${familyMember.workUnitsAndPosition}" style="width:305px" class="contentInput"/>
				    		</td>
				    	</tr>
				    	<tr>
				    		<td class="tdTitle">联系电话：</td>
				    		<td colspan="2" class="tdContent">
				    			<input type="text" name="phone" id="phone" value="${familyMember.phone}" style="height:15px" class="easyui-numberbox" data-options="min:0"/>
				    		</td>
				    		<td class="tdTitle">E-Mail：</td>
				    		<td colspan="2" class="tdContent">
				    			<input type="text" name="email" id="email" value="${familyMember.email}" class="contentInput"/>
				    		</td>
				    	</tr>
				    </c:forEach>
			    </table>
				<div style="text-align: center;margin-top: 20px">
			  		<input type="button" id="toUpdateBaseInfo" class="btn btn-primary btn-large" value="保存修改"/>
			  	</div>
			  	<script type="text/javascript">
			  		var baseInfoForm = $("#baseInfoForm");
			  		var myFamilyInfoTable = $("#myFamilyInfoTable");
				  	var setValue = function(){
						var studentName = $("#studentName",baseInfoForm).val();
						if(""==studentName){
							alert("请填写学生姓名");
							return false;
						}
						var sex = $("#sex",baseInfoForm).val();
						if(""==sex){
							alert("请选择性别");
							return false;
						}
						var birthday = $("#birthday",baseInfoForm).val();
						if(""==birthday){
							alert("请选择出生日期");
							return false;
						}
						var nation = $("#nation",baseInfoForm).val();
						if(""==nation){
							alert("请选择民族");
							return false;
						}
						var phone = $("#phone",baseInfoForm).val();
						if(""!=phone&&!(/(^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$)|(^((\(\d{3}\))|(\d{3}\-))?(1[358]\d{9})$)/.test(phone))){
							alert("家庭电话格式错误");
							return false;
						}
						var idNumber = $("#idNumber",baseInfoForm).val();
						if(""!=idNumber&&!(/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(idNumber))){
							alert("身份证号格式错误");
							return false;
						}
						var mobilePhone = $("#mobilePhone",baseInfoForm).val();
						if(""==mobilePhone){
							alert("请填写手机号码");
							return false;
						}
						if(""!=mobilePhone&&!/(^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$)|(^((\(\d{3}\))|(\d{3}\-))?(1[358]\d{9})$)/.test(mobilePhone)){
							alert("手机号码格式错误");
							return false;
						}
						var email = $("#email",baseInfoForm).val();
						if(""!=email&&(!/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(email))){
							alert("邮箱格式错误");
							return false;
						}
						var city = $("#city",baseInfoForm).val();
						if("======请选择======"==city){
							alert("请选择所在市");
							return false;
						}
						var area = $("#area",baseInfoForm).val();
						if("======请选择======"==area){
							alert("请选择所在区/县");
							return false;
						}
						var school = $("#school",baseInfoForm).val();
						if("======请选择======"==school){
							alert("请选择学校");
							return false;
						}
						var grade = $("#grade",baseInfoForm).val();
						if("======请选择======"==grade){
							alert("请选择年级");
							return false;
						}
						var clazz = $("#clazz",baseInfoForm).val();
						if("======请选择======"==clazz){
							alert("请选择班级");
							return false;
						}
						var address = $("#address",baseInfoForm).val();
						if(""==address){
							alert("请填写家庭地址");
							return false;
						}
						
						var familyMember = $("#familyMember",myFamilyInfoTable).val();
						if(""==familyMember){
							alert("请填写家庭成员");
							return false;
						}
						var familyMemberName = $("#familyMemberName",myFamilyInfoTable).val();
						if(""==familyMemberName){
							alert("请填写家庭成员姓名");
							return false;
						}
						var familyMemberPhone = $("#phone",myFamilyInfoTable).val();
						if(""==familyMemberPhone){
							alert("请填写家庭成员联系电话");
							return false;
						}
						var familyMemberAge = $("#familyMemberAge",myFamilyInfoTable).val();
						var workUnitsAndPosition = $("#workUnitsAndPosition",myFamilyInfoTable).val();
						var familyMemberEmail = $("#email",myFamilyInfoTable).val();
						var familyMemberList = new Array();
						familyMemberList.push(familyMember);
						familyMemberList.push(familyMemberName);
						if(""==familyMemberAge){
							familyMemberList.push(-1);
						}else{
							familyMemberList.push(familyMemberAge);
						}
						if(""==workUnitsAndPosition){
							familyMemberList.push(-1);
						}else{
							familyMemberList.push(workUnitsAndPosition);
						}
						familyMemberList.push(familyMemberPhone);
						if(""==familyMemberEmail){
							familyMemberList.push(-1);
						}else{
							familyMemberList.push(familyMemberEmail);
						}
						var familyMemberStr = familyMemberList.join("^");
						$("#familyMemberInfo",baseInfoForm).val(familyMemberStr);
						
						$("#kindSave",baseInfoForm).val("free");
						
						return true;
					};
					
					
			  		$("#toUpdateBaseInfo").click(function(){
			  			if(!setValue()){
							return;
						}
			  			$("#baseInfoForm").ajaxSubmit({
			  				url:"student/saveStudent.do",
			  				type:"post",
			  				dataType:"json",
			  				success:function(result){
			  					if(result.isSuccess){
			  						alert("修改成功");
			  					}else{
			  						alert(result.message);
			  					}
			  				}
			  			});
					});
				</script>
			</c:if>
			<c:if test="${infoType == 1}">
				<div style="text-align:center;">我的报名</div>
				<br/>
				<table class="table table-bordered  table-hover">
					<thead>
						<tr>
							<td style="width:150px" class="tableTitle tdThead">赛事</td>
							<td style="width:150px" class="tableTitle tdThead">参赛组别</td>
							<td style="width:150px" class="tableTitle tdThead">审核状态</td>
							<td style="width:150px" class="tableTitle tdThead">准考证号</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${studentCompetitionGroupList}" var="studentCompetitionGroup">
							<tr>
								<td class="td">${studentCompetitionGroup.competitionGroup.competition.competitionName}</td>
								<td class="td">${studentCompetitionGroup.competitionGroup.group.groupName}</td>
								<c:if test="${studentCompetitionGroup.status == 0}">
									<td class="td">未审核</td>
								</c:if>
								<c:if test="${studentCompetitionGroup.status == 1}">
									<td class="td">审核通过</td>
								</c:if>
								<c:if test="${studentCompetitionGroup.status == 2}">
									<td class="td">审核不通过</td>
								</c:if>
								<td class="td">${studentCompetitionGroup.examCode}</td>
							</tr>
					    </c:forEach>
				    </tbody>
			    </table>
			</c:if>
			
			<c:if test="${infoType == 2}">
				<div style="text-align:center;">我的线上考试</div>
				<br/>
				<table class="table table-bordered  table-hover">
					<thead>
						<tr>
							<td style="width:180px" class="tableTitle tdThead">赛事</td>
							<td style="width:150px" class="tableTitle tdThead">参赛组别</td>
							<td style="width:170px" class="tableTitle tdThead">试卷名称</td>
							<td style="width:100px" class="tableTitle tdThead">得分</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${examAnswerList}" var="examAnswer">
							<tr>
								<td class="td">${examAnswer.studentCompetitionGroup.competitionGroup.competition.competitionName}</td>
								<td class="td">${examAnswer.studentCompetitionGroup.competitionGroup.group.groupName}</td>
								<td class="td">${examAnswer.paperName}</td>
								<td class="td">${examAnswer.score}</td>
							</tr>
					    </c:forEach>
				    </tbody>
			    </table>
			</c:if>
			
			<c:if test="${infoType == 3}">
				<div style="text-align:center;">我的培训班级</div>
				<br/>
				<table class="table table-bordered  table-hover">
					<thead>
						<tr>
							<td style="width:100px" class="tableTitle tdThead">班级名称</td>
							<td style="width:100px" class="tableTitle tdThead">上课地点</td>
							<td style="width:100px" class="tableTitle tdThead">上课时间</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${trainingClassStudentList}" var="trainingClassStudent">
							<tr>
								<td class="td">${trainingClassStudent.trainingClass.trainingClassName}</td>
								<td class="td">${trainingClassStudent.trainingClass.address}</td>
								<td class="td">${trainingClassStudent.trainingClass.classDate}</td>
							</tr>
					    </c:forEach>
				    </tbody>
			    </table>
			</c:if>
			<c:if test="${infoType == 4}">
				<div style="text-align:center;">我的赛场</div>
				<br/>
				<table class="table table-bordered  table-hover">
					<thead>
						<tr>
							<td style="width:120px" class="tableTitle tdThead">赛事</td>
							<td style="width:120px" class="tableTitle tdThead">参赛组别</td>
							<td style="width:120px" class="tableTitle tdThead">赛场名称</td>
							<td style="width:120px" class="tableTitle tdThead">比赛地点</td>
							<td style="width:120px" class="tableTitle tdThead">比赛时间</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${stadiumStudentList}" var="stadiumStudent">
							<tr>
								<td class="td">${stadiumStudent.stadium.competitionGroup.competition.competitionName}</td>
								<td class="td">${stadiumStudent.stadium.competitionGroup.group.groupName}</td>
								<td class="td">${stadiumStudent.stadium.stadiumName}</td>
								<td class="td">${stadiumStudent.stadium.stadiumAddr}</td>
								<td class="td">${stadiumStudent.stadium.competitionDate}</td>
							</tr>
					    </c:forEach>
				    </tbody>
			    </table>
			</c:if>
		</div>
	</div>
	</div>
</div>

<div id="uploadPictrueDialog" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="width:400px;height:200px">
	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
	<center>
		<div style="margin-top:50px">
			<form id="uploadPictrueForm" method="post" enctype="multipart/form-data">
				选择照片:
				<input type="file" name="upload" id="upload"/></td>
			</form>
			<div style="margin-top:30px;">
				<input class="btn btn-primary" type="button" value="保存" id="uploadPictrueBtn"/></td>
			</div>
		</div>
	</center>
</div>

<jsp:include page="indexFooter.jsp"></jsp:include>
     
