<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!-- 체크박스 이미지화...... 안됨 이건 안되는데 혹시나해서 일단 살려둠 -->



 <style>

form{
	width:95%;
}
/* input[type=checkbox] { display:none; }

input[type=checkbox] + label { 

display: inline-block; 

cursor: pointer; 

line-height: 22px; 

padding-left: 22px; 

background: url('"https://via.placeholder.com/0x0.png"') left/22px no-repeat; 

}

input[type=checkbox]:checked + label { 
background-image: url("https://via.placeholder.com/80x110.png"); 
} */

</style>
<div class="page-main-style">
	<h2>선호장르 변경</h2>
	<form:form action="modifyPrefer.do" commandName="memberVO">
		<form:errors element="div" cssClass="error-color"/>
		 <div class="perfer_chk" id="prefer_chk">
		<p>보고싶은 공연을 골라보세요. 가장 많이 선택된 장르(1~5)하나를 member_detail 'prefer'에 저장</p>
<%-- 
		 
		<!-- 체크박스 이미지화...... 안됨 이건 안되는데 혹시나해서 일단 살려둠 -->
		<input type="checkbox" id="prefer" value="1">
		<label for="prefer">
		<img src="${pageContext.request.contextPath}/resources/post/" width="180" height="240">
		</label>
		
		<input type="checkbox" id="prefer" value="2">
		<label for="prefer">
		<img src="${pageContext.request.contextPath}/resources/post/" width="180" height="240">
		</label>
		
		<input type="checkbox" id="prefer" value="3">
		<label for="prefer">
		<img src="${pageContext.request.contextPath}/resources/post/" width="180" height="240">
		</label>
		
		<input type="checkbox" id="prefer4" value="4">s
		<label for="prefer4">
		<img src="${pageContext.request.contextPath}/resources/post/" width="180" height="240">
		</label>
		
		<input type="checkbox" id="prefer5" value="5">
		<label for="prefer5">
		<img src="${pageContext.request.contextPath}/resources/post/" width="180" height="240">
		</label>

		 <br><br><br>
		 	 --%>
		 	<br><br>
		 	<img src="${pageContext.request.contextPath}/resources/post/duet.jpeg" width="180" height="240">
		 	<input type="checkbox" id="prefer" name="prefer" value="1">
		 	<img src="${pageContext.request.contextPath}/resources/post/Cats.gif" width="180" height="240">
		 	<input type="checkbox" id="prefer" name="prefer" value="2">
		 	<img src="${pageContext.request.contextPath}/resources/post/thosedays.png" width="180" height="240">
		 	<input type="checkbox" id="prefer" name="prefer" value="3">
		 	<img src="${pageContext.request.contextPath}/resources/post/dodo.gif" width="180" height="240">
		 	<input type="checkbox" id="prefer" name="prefer" value="4">
		 	<img src="${pageContext.request.contextPath}/resources/post/fuerza.gif" width="180" height="240">
		 	<input type="checkbox" id="prefer" name="prefer" value="5">
		 	<br><br><br>
		 	<img src="${pageContext.request.contextPath}/resources/post/gloomyday.png" width="180" height="240">
		 	<input type="checkbox" id="prefer" name="prefer" value="3">
		 	<img src="${pageContext.request.contextPath}/resources/post/lapungel.GIF" width="180" height="240">
		 	<input type="checkbox" id="prefer" name="prefer" value="4">
		 	<img src="${pageContext.request.contextPath}/resources/post/Gentleman's_guide.jpeg" width="180" height="240">
		 	<input type="checkbox" id="prefer" name="prefer" value="1">
		 	<img src="${pageContext.request.contextPath}/resources/post/infinityFlying.gif" width="180" height="240">
		 	<input type="checkbox" id="prefer" name="prefer" value="5">
		 	<img src="${pageContext.request.contextPath}/resources/post/The Phantom Of The Opera.gif" width="180" height="240">
		 	<input type="checkbox" id="prefer" name="prefer" value="2">
		 	<br><br><br>
		 	<img src="${pageContext.request.contextPath}/resources/post/redhat.gif" width="180" height="240">
		 	<input type="checkbox" id="prefer" name="prefer" value="4">
		 	<img src="${pageContext.request.contextPath}/resources/post/circus.gif" width="180" height="240">
		 	<input type="checkbox" id="prefer" name="prefer" value="5">
		 	<img src="${pageContext.request.contextPath}/resources/post/Jesus_Christ_Super_Star.jpg" width="180" height="240">
		 	<input type="checkbox" id="prefer" name="prefer" value="1">
		 	<img src="${pageContext.request.contextPath}/resources/post/fanletter.png" width="180" height="240">
		 	<input type="checkbox" id="prefer" name="prefer" value="3">
		 	<img src="${pageContext.request.contextPath}/resources/post/The Lion King.gif" width="180" height="240">
		 	<input type="checkbox" id="prefer" name="prefer" value="2">
		 	<br><br><br>
		 	<img src="${pageContext.request.contextPath}/resources/post/Sweeney_Todd.jpg" width="180" height="240">
		 	<input type="checkbox" id="prefer" name="prefer" value="1">
		 	<img src="${pageContext.request.contextPath}/resources/post/sunemoone.gif" width="180" height="240">
		 	<input type="checkbox" id="prefer" name="prefer" value="4">
		 	<img src="${pageContext.request.contextPath}/resources/post/Jekyll and Hyde.gif" width="180" height="240">
		 	<input type="checkbox" id="prefer" name="prefer" value="2">
		 	<img src="${pageContext.request.contextPath}/resources/post/annie.gif" width="180" height="240">
		 	<input type="checkbox" id="prefer" name="prefer" value="5">
		 	<img src="${pageContext.request.contextPath}/resources/post/6oclock.png" width="180" height="240">
		 	<input type="checkbox" id="prefer" name="prefer" value="3">
		 	<br><br><br>
		 	<img src="${pageContext.request.contextPath}/resources/post/chef.gif" width="180" height="240">
		 	<input type="checkbox" id="prefer" name="prefer" value="5">
		 	<img src="${pageContext.request.contextPath}/resources/post/shadow.png" width="180" height="240">
		 	<input type="checkbox" id="prefer" name="prefer" value="3">
		 	<img src="${pageContext.request.contextPath}/resources/post/Notre Dame de Paris.gif" width="180" height="240">
		 	<input type="checkbox" id="prefer" name="prefer" value="2">
		 	<img src="${pageContext.request.contextPath}/resources/post/The_Man_of_La_Mancha.jpeg" width="180" height="240">
		 	<input type="checkbox" id="prefer" name="prefer" value="1">
		 	<img src="${pageContext.request.contextPath}/resources/post/alcandy.gif" width="180" height="240">
		 	<input type="checkbox" id="prefer" name="prefer" value="4">
			<br><br><br>		 	
		 </div>
	
		<div class="align-center">
			<input type="submit" value="변경하기">
			<input type="button" value="홈으로"
			    onclick="location.href='${pageContext.request.contextPath}/main/musMain.do'"> 
		</div>
	</form:form>
</div>