<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="root" value="${pageContext.request.contextPath }/" />


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>미니 프로젝트</title>
<!-- Bootstrap CDN -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
</head>

<!-- ajax 통신을 위한 스크립트함수 작성하기 -->
<!-- ajax : HTML 페이지 전체가 아닌 일부분만 갱신할 수 있도록, XMLHttpRequest객체를 통해 서버에 request한다.
           쉽게 말하자면 자바스크립트를 통해서, 클라이언트가 서버에 데이터를 요청하는 것  -->

<script>


function checkUserIdExist() {
	//변수선언 : 사용자가 입력한 id값 가져오기(value) --우항을 좌항에 할당
   var user_id = $("#user_id").val()	
    //입력한 id값이 없을시 , 재입력 요청
    if(user_id.length==0){
    	alert('아이디를 입력하세요')
    	return //경고메시지띄우고 원래위치로 돌아가라
    }
    
    //ajax통신 시작! el로 표현해주면 시작한다.
    // type: 통신방식
    //dataType : 서버가 요청 URL을 통해서 응답하는 내용의 타입
    // 주소가 상대주소가 되지않도록, ${root} 설정해주자
    //-------중복확인버튼을 누르면 , DB에 중복확인한 결과가 반환되고, 중복확인자체를 했는지의 여부도 함께 바뀐다.
    $.ajax({
        url:'${root}user/checkUserIdExist/'+ user_id, //요청할 페이지주소. 마치 주소에 값붙이는것과 같음
        type: 'get', //요청메소드타입
        dataType: 'text', //문자열 데이터 취급. 공백으로 인한 오류우려때문에, trim처리를 아래에서 해준다
    
        //설정한 url과 통신 성공시 호출되는 함수(= 서버의 응답데이터가 클라이언트에게 도착하면 자동으로 실행되는함수(콜백함수))
        //result - 응답데이터
        success : function (result) {
			//통신에 성공했는데 true이면
        	if(result.trim()=='true'){
        		alert('사용할 수 있는 아이디 입니다')
        		//userIdExist(id중복체크여부)의 값을 true로 변경(원래는 false.) cf) checkuserIdExist : id사용가능여부
        		$("#userIdExist").val('true')
        	} else{
        		alert('사용할 수 없는 아이디 입니다')
        		//userIdExist의 값을 false로 유지
        		$("#userIdExist").val('false')
        	}
			
		}
        
        
        //cf)통신 실패시 호출되는 함수(일종의 예외처리) : errors
        
    })//통신 종료
    
    function resetUserIdExist() {
       // 사용자 아이디란에 무조건 false. 처음에는 유효성 검사후 사용할 수 없는 상태를 불러온다
    	$("#userIdExist").val('false')
    	
	}
    
    
    
    
    
    
}





</script>














<body>

	<!-- 상단 메뉴 부분 -->
	<c:import url="/WEB-INF/views/include/top_menu.jsp" />

	<div class="container" style="margin-top: 100px">
		<div class="row">
			<div class="col-sm-3"></div>
			<div class="col-sm-6">
				<div class="card shadow">
					<div class="card-body">
						<!-- view에서 폼폼으로 모델을 받을때는 반드시 modelAttribute로 어떤모델인지 명시-->
						<!-- join으로 먼저 넘어온 다음에  -->
						<form:form action="${root }user/join_pro" method="post" modelAttribute="joinUserBean">
							<!-- 안보이게 히든으로 userIdExist를 초기값 false로 UserBean으로부터 끌고왔고, 이 값을 이용해서 중복확인버튼에 들어가는 checkUserIdExist메서드 작동-->
							<form:hidden path="userIdExist"/>  
							<!-- 이름 : 입력박스 (유효성검사는 빨간색으로. 폼:에러) . id+name=path-->
							<div class="form-group">					
								<form:label path="user_name">이름</form:label> 
								<form:input path="user_name" class="form-control"/> 
								<form:errors path="user_name" style="color:red"/>
							</div>
							
							<!-- 아이디입력후 중복확인을 해야하므로, input-group과 input-group-append를 다른그룹으로 지정 -->
							<div class="form-group">
								<form:label path="user_id">아이디</form:label>
								<div class="input-group"> 
									<form:input path="user_id" class="form-control" onkeypress="resetUserIdExist()"/> 
								
								<!-- 아이디:입력박스 옆에 있는 ajax통신 이용하는 중복확인 버튼이므로 다른그룹으로 지정. form:input이 버튼감싸고있으므로 form:button 안해도됨-->	
									<!-- 클릭하면, 메서드가 돌면서 ajax통신시작! 재료는 hidden으로 숨겨온 userIdExist. 결과물은 54의 resetUserIdExist()의 재료가 될것이다. -->
									<div class="input-group-append">
										<button type="button" class="btn btn-primary" onclick="checkUserIdExist()">중복확인</button>
									</div>
								</div>
								<form:errors path="user_id" style="color:red"/>
							</div>
							
							
							
							<div class="form-group">
								<form:label path="user_pw">비밀번호</form:label>
								 <form:password path="user_pw"  class="form-control" />
								 <form:errors path="user_pw" style="color:red"/>
							</div>
							
							
							<div class="form-group">
								<form:label path="user_pw2">비밀번호 확인</form:label> 
								<form:password path="user_pw2" class="form-control" />
								<form:errors path="user_pw2" style="color:red"/>
							</div>
							
							
							<div class="form-group">
								<div class="text-right">
									<form:button class="btn btn-primary">회원가입</form:button>
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
	<c:import url="/WEB-INF/views/include/bottom_info.jsp" />

</body>
</html>









