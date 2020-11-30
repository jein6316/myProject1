<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/style.css">
<title>한줄평 쓰기</title>
</head>
<body>
<%-- <c:if test="${!empty member&& member.mem_num==commentsVO.mem_num}">	<!-- 로그인이 되어 있고 작성자 아이디와 로그인 아이디가 일치하면 수정으로 리다이렉트 -->
<%=	response.sendRedirect("reviewModify.do")
%>
</c:if> --%>
	<form:form action="write.do" commandName="commentsVO">
		<ul>
			<li>
				${member.mem_image }
			</li>
			<li>${member.nickname }</li>
			<li class="content_info_star">
				<div class="star_box star1" value="0.5"></div>
				<i class="far fa-star real-star1 real-star"></i>
				<div class="star_box star2" value="1"></div>
				<div class="star_box star3" value="1.5"></div>
				<i class="far fa-star real-star2 real-star"></i>
				<div class="star_box star4" value="2"></div>
				<div class="star_box star5" value="2.5"></div>
				<i class="far fa-star real-star3 real-star"></i>
				<div class="star_box star6" value="3"></div>
				<div class="star_box star7" value="3.5"></div>
				<i class="far fa-star real-star4 real-star"></i>
				<div class="star_box star8" value="4"></div>
				<div class="star_box star9" value="4.5"></div>
				<i class="far fa-star real-star5 real-star"></i>
				<div class="star_box star10" value="5"></div>
			</li>
			<li>
				<label for=review></label>
				<form:textarea path="review"/>
				<form:errors path="review" cssClass="error-color"/>
			</li>
		</ul>
		<div class="align-center">
			<input type="submit" value="등록">
			<input type="button" value="홈으로"
			       onclick="location.href='main/musMain.do'">
		</div>
	</form:form>
    <script src="${pageContext.request.contextPath }/resources/js/index.js"></script>
    <script src="${pageContext.request.contextPath }/resources/js/star.js"></script>
</body>
</html>