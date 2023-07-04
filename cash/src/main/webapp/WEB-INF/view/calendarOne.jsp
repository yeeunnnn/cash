<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>calendarOne</title>
</head>
<body>
	<table border="1">
		<tr>
			<td colspan="2"><h4>가계부 목록</h4></td>
		</tr>
		<c:forEach var="c" items="${list}">
			<tr>
				<td>카테고리</td>
				<td>${c.category}</td>
			</tr>
			<tr>
				<td>가격</td>
				<td>
					<c:choose>
						<c:when test="${c.category == '수입'}">
							+${c.price}
						</c:when>
						<c:when test="${c.category == '지출'}">
							<span style="color:red;">-${c.price}</span>
						</c:when>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td>메모</td>
				<td>${c.memo}</td>
			</tr>
			<tr>
				<td>입력일</td>
				<td>${c.createdate}</td>
			</tr>
			<tr>
				<td>수정일</td>
				<td>${c.updatedate}</td>
			</tr>
		</c:forEach>
	</table>
	<form method="post" action="${pageContext.request.contextPath}/addCashbook?targetYear=${targetYear}&targetMonth=${targetMonth}&targetDate=${targetDate}"><!-- 이게 el 임. -->
		<table border="1">
			<tr>
				<td colspan="2"><h4>가계부 추가</h4></td>
			</tr>
			<tr>
				<td>카테고리</td>
				<td>
				 	<input type="radio" name="category" value="수입">수입
        			<input type="radio" name="category" value="지출">지출
				</td>
			</tr>
			<tr>
				<td>가격</td>
				<td><input type="text" name="price"></td>
			</tr>
			<tr>
				<td>메모</td>
				<td><input type="text" name="memo"></td>
			</tr>
		</table>
		<button type="submit">추가</button>
	</form>
</body>
</html>