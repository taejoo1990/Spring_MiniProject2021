<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    

<c:set var='root' value='${pageContext.request.contextPath}/'/>

<script>

alert('로그인에 실패하셨습니다')
location.href='${root}user/login?fail=true' //실패해서 재로그인 한다는것을 ,링크에 쿼리스트링을 붙여 알려주면된다. 


</script>