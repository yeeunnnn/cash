<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%><!-- jstl의 substring 호출 -->
<!-- JSP 컴파일 시 자바코드로 변환되는 c:..(제어문법코드) 으로 시작하는 태그를 사용할 수 있음(custom tag) -->
<!--
	int targetYear = (int)request.getAttribute("targetYear"); // CalendarController에서 보낸 session값
	int targetMonth = (int)request.getAttribute("targetMonth");
	int beginBlank = (int)request.getAttribute("beginBlank");
	int lastDate = (int)request.getAttribute("lastDate");
	int endBlank = (int)request.getAttribute("endBlank");
	int totalCell = (int)request.getAttribute("totalCell");
-->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>calendar</title>
<style>
	table{
		border-collapse: collapse;
	}
	td {
		border: 1px solid gray;
		padding: 3px;
		width: 120px;
		height: 120px;
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
	<!-- 변수값 or 반환값 : EL 사용 $ 표현식 -->
	<!-- 속성값 대신 EL 사용
		ex)
		int targetYear = (int)request.getAttribute("targetYear"); // CalendarController에서 보낸 session값 -- $와 함께 {requestScope.targetYear} 보통 {}(스코프) 생략가능
		(requestScope는 생략가능
		형변환연산이 필요없다(EL이 자동으로 처리)
	-->
	
	<!-- 자바코드(제어문) : JSTL 사용 -->
	
	<h1>${targetYear}년 ${targetMonth+1}월</h1>
	<a href="${pageContext.request.contextPath}/calendar?targetYear=${targetYear}&targetMonth=${targetMonth-1}">이전</a>
	<a href="${pageContext.request.contextPath}/calendar?targetYear=${targetYear}&targetMonth=${targetMonth+1}">다음</a>
	<div>
		<h2>이달의 해시태그</h2>
		<div>
			<c:forEach var="m" items="${htList}">
				<a href="${pageContext.request.contextPath}/cashbookList?word=${m.word}">${m.word}(${m.cnt})</a>
			</c:forEach>
		</div>
	</div>
	<table border="1">
		<thead>
			<tr>
				<th>일</th>
				<th>월</th>
				<th>화</th>
				<th>수</th>
				<th>목</th>
				<th>금</th>
				<th>토</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<c:forEach var="i" begin="0" end="${totalCell - 1}" step="1">
					<c:set var="d" value="${i-beginBlank+1}"></c:set>
					<c:if test="${i!=0 && i%7==0}">
						<tr></tr><!-- 개행 -->
					</c:if>
					
					<c:if test="${d < 1 || d > lastDate}">
						<td></td><!-- 범위 밖 날짜는 빈 셀을 출력 -->
					</c:if>
					<c:if test="${!( d < 1 || d > lastDate)}">
						<td>
							<div onclick="location.href='${pageContext.request.contextPath}/calendarOne?targetYear=${targetYear}&targetMonth=${targetMonth}&targetDate=${d}'">${d}</div>
							<c:forEach var="c" items="${list}">
								<c:if test="${d == fn:substring(c.cashbookDate, 8, 10)}">
									<div>
										<c:if test="${c.category == '수입'}">
											<span>+${c.price}</span>
										</c:if>
										<c:if test="${c.category == '지출'}">
											<span style="color:red;">-${c.price}</span>
										</c:if>
									</div>
								</c:if>
							</c:forEach>	
						</td><!-- 날짜가 1부터 lastDate까지만 출력될 수 있도록 -->
					</c:if>
				</c:forEach>
			</tr>
		</tbody>
	</table>
</body>
</html>