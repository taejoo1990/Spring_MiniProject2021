<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="root" value="${pageContext.request.contextPath }/"/>

<!-- el식 닫고 태그닫기전에 /까지 해주고, 안해줬으면 아래에다가 "${root }/board/main"처럼 써야한다. -->

<!-- href를 그냥 main으로 처리하면, 상대경로로 인식해서 http://localhost:9021/MiniProjectJava/board/board/main 형태로 꼬일 수 있으므로,
	    절대경로를 정해주기위해 최상위 위치를 root라는 이름으로 잡고, 이 루트로 가라고 걸어줘야한다. c태그 이용함. -->

<!-- 여기서는 서블렛에서 /WEB-INF/views/ 를 레지스트리에 올려놨기 때문에, 여기가 루트가 된다.
 (pageContext.request.contextPath는, 레지스트리에 설정한 곳을 의미한다)-->


<!-- 상단 메뉴 부분 -->
<nav class="navbar navbar-expand-md bg-dark navbar-dark fixed-top shadow-lg">
	<!-- href를 그냥 main으로 처리하면, 상대경로로 인식해서 http://localhost:9021/MiniProjectJava/board/board/main 형태로 꼬일 수 있으므로,
	    절대경로를 정해주기위해 루트로 가서 main찾으라고 걸어줘야한다. c태그 이용함. -->
	
		
	<a class="navbar-brand" href="${root }main">SoftSoldesk</a><!-- SoftSoldesk버튼 클릭시 메인화면으로. 홈버튼의 역할. jsp생략 -->
	
	<button class="navbar-toggler" type="button" data-toggle="collapse"
	        data-target="#navMenu">
		<span class="navbar-toggler-icon"></span>        
	</button>
	<div class="collapse navbar-collapse" id="navMenu">
		
		<ul class="navbar-nav">
		
		
		<!-- DB에 있는 게시판정보관련 리스트 받아와서 forEach문으로 돌려서 세팅한다.(총 4개 세팅됨) -->
		<c:forEach var ='obj' items="${topMenuList }">
				<li class="nav-item">    
				<a href="${root }board/main?board_info_idx=${obj.board_info_idx}" class="nav-link">${obj.board_info_name }</a>
			</li>
			</c:forEach>
			
			</ul>
		
		
		<!-- 이부분을 실행할때, 로그인과 회원가입은 비로그인 상태에서만보이고, 정보수정과 로그아웃은 로그인 상태에서만 보이도록. -->
		
		<!-- 스위치문이용시에는 choose when otherwise로 잡는다 -->
				
		<!-- 인터셉터에 올라와있는 loginUserBean의 userLogin상태를 체크한다.  -->
		
		<ul class="navbar-nav ml-auto">
			<c:choose>
				<c:when test="${loginUserBean.userLogin == true }">
					
					<li class="nav-item">
						<a href="${root }user/modify_user" class="nav-link">정보수정</a>
					</li>
					<li class="nav-item">
						<a href="${root }user/logout" class="nav-link">로그아웃</a>
					</li>
				</c:when>
				
				<c:otherwise>
					
					<li class="nav-item">
						<a href="${root }user/login" class="nav-link">로그인</a>
					</li>
					<li class="nav-item">
						<a href="${root }user/join" class="nav-link">회원가입</a>
					</li>
				</c:otherwise>
			</c:choose>	
		</ul>
		
	</div>
</nav>