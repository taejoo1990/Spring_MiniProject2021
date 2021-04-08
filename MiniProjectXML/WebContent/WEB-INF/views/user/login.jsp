<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %> <!-- 모델어트리븃쓰기위해. -->

<c:set var="root" value="${pageContext.request.contextPath }/"/><!-- 여기에 안줄거면,  -->
    
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

<!-- 본문 -->
<div class="container" style="margin-top:100px">
	<div class="row">
		<div class="col-sm-3"></div>
		<div class="col-sm-6">
			<div class="card shadow">
				<div class="card-body">
				
				<!-- 로그인 실패시, 즉 fail이 true일때만 작동하도록 조건문태그 작성 -->
					<c:if test="${fail==true}">
					<div class="alert alert-danger">
					
						<h3>로그인 실패</h3>
						<p>아이디 비밀번호를 확인해주세요</p>
					</div>
						</c:if>
					
					<!-- 세션에서 임시로 받아서 가지고있다가 브라우저에서 사용하는 모델. 폼폼 사용 -->
					<!-- 컨트롤러단에서 빈껍데기+fail(fail)을 데리고 들어온것을, 여기서 입력받은 다음 다시 login_pro로 보낸다 -->
					<form:form action="${root }user/login_pro" method="post" modelAttribute="temploginUserBean">
						<div class="form-group">
							<form:label path="user_id">아이디</form:label>
							<form:input  path="user_id" class="form-control"/>
							<form:errors path="user_id" style="color:red"></form:errors>
						</div>
						<div class="form-group">
							<form:label path="user_pw">비밀번호</form:label>
							<form:password path="user_pw"  class="form-control"/>
						</div>
						<div class="form-group text-right">
							<form:button class="btn btn-primary">로그인</form:button>
							<a href="${root }user/join" class="btn btn-danger">회원가입</a>
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








    