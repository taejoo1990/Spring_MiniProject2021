<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="root" value="${pageContext.request.contextPath }/"/>    
    

 <!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>미니 프로젝트</title>
<!-- Bootstrap CDN -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>

</head>
<body>


<!-- 상단 메뉴 부분 -->
<c:import url="/WEB-INF/views/include/top_menu.jsp"/>


<div class="container" style="margin-top:100px">
	<div class="row">
		<div class="col-sm-3"></div>
		<div class="col-sm-6">
			<div class="card shadow">
				<div class="card-body">
				
				<!-- 폼폼 시작. 세팅해서 modify_pro로 보낼것이다. -->
				
				<!-- UserBean에 대한 유효성 검사를 진행하면, 채워지도록 해놓았던 이름,id 부분이 컴파일 시 날아가는 문제가 생긴다. 
				disabled가 아니라, readonly(disabled="true"+read)해야한다.(disabled만 해놓으면 값이 사라지므로, read도 가능하게해야함) -->
				
					<form:form action="${root }user/modify_pro" method="post" modelAttribute="modifyUserBean">
					<div class="form-group">
						<form:label path="user_name">이름</form:label>
						<form:input path="user_name" class="form-control" readonly="true"/>
					</div>
					<div class="form-group">
						<form:label path="user_id">아이디</form:label>
						<form:input path="user_id" class="form-control" readonly="true"/>
					</div>
					<div class="form-group">
						<form:label path="user_pw">비밀번호</form:label>
						<form:password path="user_pw" class="form-control" />
						<form:errors path="user_pw" style="color:red"/>
					</div>
					<div class="form-group">
						<form:label path="user_pw2">비밀번호 확인</form:label>
						<form:password path="user_pw2" class="form-control"/>
						<form:errors path="user_pw2" style="color:red"/>
					</div>
					<div class="form-group">
						<div class="text-right">
							<form:button  class="btn btn-primary">정보수정</form:button>
						</div>
					</div>
					
					</form:form>
					
				</div>
			</div>
		</div>
		<div class="col-sm-3"></div>
	</div>
</div>

<!-- 하단 -->
<c:import url="/WEB-INF/views/include/bottom_info.jsp"/>


</body>
</html>
    