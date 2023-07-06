<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>calendarOne</title>
<style>
	table{
		border-collapse: collapse;
	}
	td {
		border: 1px solid gray;
		padding: 3px;
		width: 120px;
		height: 60px;
	}
	th{
		background-color: #dddddd;
		font-weight: bold;
		border: 1px solid gray;
		height : 30px;
	}
</style>
</head>
<body>
	<h4>${targetYear}년 ${targetMonth+1}월 ${targetDate}일 가계부 목록</h4>
		<table border="1">
			<thead>
				<tr>
					<th>일시</th>
					<th>카테고리</th>
					<th>수입</th>
					<th>지출</th>
					<th>메모</th>
					<th>수정</th>
					<th>삭제</th>
				</tr>
			</thead>
			
			<!-- 총 지출과 총 수입에 대한 변수 정의 -->
			<c:set var="totalIncome" value="0" />
			<c:set var="totalExpense" value="0" />
			
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
						<td><a type="button" href="${pageContext.request.contextPath}/modifyCashbook?targetYear=${targetYear}&targetMonth=${targetMonth}&targetDate=${targetDate}">수정</a></td>
						<td>
							<form method="post" action="${pageContext.request.contextPath}/removeCashbook?targetYear=${targetYear}&targetMonth=${targetMonth}&targetDate=${targetDate}">
							  <input type="hidden" name="cashbookNo" value="${c.cashbookNo}">
							  <button type="submit">삭제</button>
							</form>
						</td>
					</tr>
				</tbody>
			</c:forEach>
		</table>
	
	
	<h4>수입/지출내역 추가</h4>
	<form method="post" action="${pageContext.request.contextPath}/addCashbook?targetYear=${targetYear}&targetMonth=${targetMonth}&targetDate=${targetDate}"><!-- 이게 el 임. -->
		<table border="1">
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
	
	<h4>요약</h4>
	<table>
		<tr>
			<td>총 수입</td>
			<td>
				<c:choose>
					<c:when test="${totalIncome != 0}">
						<span style="color:blue;">+${totalIncome}원</span>
					</c:when>
					<c:otherwise>
						0원
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td>총 지출</td>
			<td>
			<c:choose>
				<c:when test="${totalExpense != 0}">
					<span style="color:red;">-${totalExpense}원</span>
				</c:when>
				<c:otherwise>
					0원
				</c:otherwise>
			</c:choose>
		</td>
		</tr>
	</table>
</body>
</html>