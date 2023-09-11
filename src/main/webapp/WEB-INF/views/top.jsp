<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Arrays"%>
<html>
<head>
<!-- [Bootstrap] -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
<!-- 부트스트랩 테이블 -->
<link rel="stylesheet"
	href="https://unpkg.com/bootstrap-table@1.15.5/dist/bootstrap-table.min.css">
<link rel="stylesheet" href="${path}/resources/css/style.css">
</head>
<body>
	<%
	request.setCharacterEncoding("utf-8");
	Cookie[] cookies = request.getCookies();
	double lat = 0, lnt = 0;
	boolean loaded = false;
	if (cookies != null)
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("lat")) {
		lat = Double.parseDouble(cookie.getValue());
		loaded = true;
			} else if (cookie.getName().equals("lnt"))
		lnt = Double.parseDouble(cookie.getValue());
		}
	%>
	<br>
	<!--쿠키가 있으면 홈으로 가지않고 loadedwifi로 가짐  -->
	<c:choose>
		<c:when test="<%=loaded%>">
			<a href="/loadFromDB?lat=<%=lat%>&lnt=<%=lnt%>">홈</a> |
		</c:when>
		<c:otherwise>
			<a href="/">홈</a> |
		</c:otherwise>
	</c:choose>
	<a href="/history">위치 히스토리 목록</a> |
	<a href="/bringAPI">Open API 와이파이 정보 가져오기</a> |
	<a href="/bookmark/list">북마크 보기</a> |
	<a href="/bookmark/groupList">북마크 그룹 관리 </a>
</body>
</html>