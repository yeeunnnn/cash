<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>addMember</title>
</head>
<body>
	<h1>회원가입</h1>
	<form method="post" action="${pageContext.request.contextPath}/addMember"><!-- 이게 el 임. -->
	<table border="1">
		<tr>
			<td>memberId</td>
			<td><input type="text" name="memberId"></td>
		</tr>
		<tr>
			<td>memberPw</td>
			<td><input type="password" name="memberPw"></td>
		</tr>
	</table>
	<button type="submit">가입</button>
	</form>
</body>
</html>