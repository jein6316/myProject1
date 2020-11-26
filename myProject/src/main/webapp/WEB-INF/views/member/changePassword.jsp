<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.5.1.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		//비밀번호 변경 체크
		$('#password').keyup(function(){
			if($('#confirm_passwd').val()!='' &&
					 $('#confirm_passwd').val()!=$(this).val()){
				$('#message_id').text('비밀번호 불일치').css('color','red');
			}else if($('#confirm_passwd').val()!='' &&
					$('#confirm_passwd').val()==$(this).val()){
				$('#message_id').text('비밀번호 일치').css('color','#000');
			}
		});
		
		$('#confirm_password').keyup(function(){
			if($('#password').val()!='' &&
					 $('#password').val()!=$(this).val()){
				$('#message_id').text('비밀번호 불일치').css('color','red');
			}else if($('#password').val()!='' &&
					$('#password').val()==$(this).val()){
				$('#message_id').text('비밀번호 일치').css('color','#000');
			}
		});
		
		$('#change_form').submit(function(){
			if($('#now_password').val()==''){
				alert('현재 비밀번호를 입력하세요');
				$('#now_password').focus();
				return false;
			}
			if($('#password').val()==''){
				alert('변경 비밀번호를 입력하세요');
				$('#password').focus();
				return false;
			}
			if($('#confirm_password').val()==''){
				alert('변경 비밀번호 확인을 입력하세요');
				$('#confirm_password').focus();
				return false;
			}
			if($('#password').val()!=$('#confirm_password').val()){
				alert('변경 비밀번호와 변경 비밀번호 확인 불일치');
				return false;
			}
		});
	});
</script>
<div class="page-main-style">
	<h2>비밀번호 변경</h2>
	<form:form id="change_form" action="changePassword.do" commandName="memberVO">
		<form:errors element="div" cssClass="error-color"/>
		<ul>
			<li>
				<label for="now_password">현재 비밀번호</label>
				<form:password path="now_password"/>
				<form:errors path="now_password" cssClass="error-color"/>
			</li>
			<li>
				<label for="password">변경할 비밀번호</label>
				<form:password path="password"/>
				<form:errors path="password" cssClass="error-color"/>
			</li>
			<li>
				<label for="confirm_password">변경할 비밀번호 확인</label>
				<input type="password" id="confirm_password">
				<span id="message_email" class="error-color"></span>
			</li>
		</ul>
		<div class="align-center">
			<input type="submit" value="전송">
			<input type="button" value="홈으로"
			    onclick="location.href='${pageContext.request.contextPath}/member/memberMain.do'">
		</div>
	</form:form>
</div>






