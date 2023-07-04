<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>memberOne</title>
</head>
<body>
	<h1>회원정보</h1>
	<table>
		<tr>
			<td>ID</td>
			<td>${member.memberId}</td>
		</tr>
		<tr>
			<td>PW</td>
			<td>${member.memberPw}</td>
		</tr>
		<tr>
			<td>가입일</td>
			<td>${member.createdate}</td>
		</tr>
		<tr>
			<td>수정일</td>
			<td>${member.updatedate}</td>
		</tr>
	</table>
	<a href="${pageContext.request.contextPath}/modifyMember">비밀번호 수정</a>
	<a href="${pageContext.request.contextPath}/removeMember">회원탈퇴</a>
	<!-- 
			id
			pw: 12*****
			가입일:
			수정일:  
	-->	
</body>
</html>