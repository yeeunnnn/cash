<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>cashbookListByTag</title>
</head>
<body>
	<table border="1">
		<thead>
			<tr>
				<th>일시</th>
				<th>카테고리</th>
				<th>수입</th>
				<th>지출</th>
				<th>메모</th>
			</tr>
		</thead>
		<c:forEach var="c" items="${list}">
			<tbody>
				<tr>
					<td>${c.cashbookDate}</td>
					<td>${c.category}</td>
					<td>
						<c:choose>
							<c:when test="${c.category == '수입'}">
							<!-- 계산된 총 수입에 현재 항목의 가격을 더함 -->
							<c:set var="totalIncome" value="${totalIncome + c.price}" />
								+${c.price}
							</c:when>
							<c:when test="${c.category != '수입'}">
								<span>0</span>
							</c:when>
						</c:choose>
					</td>
					<td>	
						<c:choose>
							<c:when test="${c.category == '지출'}">
								<!-- 계산된 총 지출에 현재 항목의 가격을 더함 -->
								<c:set var="totalExpense" value="${totalExpense + c.price}" />
								<span style="color:red;">-${c.price}</span>
							</c:when>
							<c:when test="${c.category != '지출'}">
								<span>0</span>
							</c:when>
						</c:choose>
					</td>
					<td>${c.memo}</td>
				</tr>
			</tbody>
		</c:forEach>
	</table>
<!-- 페이징 -->
	<div>
		<c:if test="${minPage > 1}">
			<a href="${pageContext.request.contextPath}/cashbookList?word=${m.word}&currentPage=${minPage - pagePerPage}">이전</a>
		</c:if>
	
		<c:forEach var="i" begin="${minPage}" end="${maxPage}">
			<c:choose>
				<c:when test="${i == currentPage}">
					<a><span>${i}&nbsp;</span></a>
				</c:when>
				<c:otherwise>
					<a href="${request.contextPath}/cashbookList?word=${m.word}&currentPage=${i}">${i}</a>
				</c:otherwise>
			</c:choose>
		</c:forEach>
	
		<c:if test="${maxPage != lastPage}">
			<a href="${request.contextPath}/cashbookList?word=${m.word}&currentPage=${minPage + pagePerPage}">다음</a>
		</c:if>
	</div>
</body>
</html>