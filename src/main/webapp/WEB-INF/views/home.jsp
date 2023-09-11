<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<c:set var="path" value="${pageContext.request.contextPath}" />
<html>
<head>
<link rel="stylesheet" href="${path}/resources/css/style.css">
</head>
<body>
	<h1>와이파이 정보 구하기</h1>
	<jsp:include page="top.jsp" flush="false" />
	<form name=posForm action=loadFromDB method="get">
		LAT: <input type="TEXT" name="lat" size="10"> LNT: <input
			type="TEXT" name="lnt" size="10"> <input type="button"
			value="내 위치 가져오기" onclick="pos();"> <input type="button"
			value="근처 WIFI 정보 보기" onclick="load();">
	</form>

	<table class="table table-bordered">
		<tr bgcolor="#04B45F">
			<th class="table-bordered border-light text-light">거리(KM)</th>
			<th class="table-bordered border-light text-light">관리번호</th>
			<th class="table-bordered border-light text-light">자치구</th>
			<th class="table-bordered border-light text-light">와이파이명</th>
			<th class="table-bordered border-light text-light">도로명주소</th>
			<th class="table-bordered border-light text-light">상세주소</th>
			<th class="table-bordered border-light text-light">설치위치(층)</th>
			<th class="table-bordered border-light text-light">설치유형</th>
			<th class="table-bordered border-light text-light">설치기관</th>
			<th class="table-bordered border-light text-light">서비스구분</th>
			<th class="table-bordered border-light text-light">망종류</th>
			<th class="table-bordered border-light text-light">설치년도</th>
			<th class="table-bordered border-light text-light">실내외구분</th>
			<th class="table-bordered border-light text-light">WIFI접속환경</th>
			<th class="table-bordered border-light text-light">X좌표</th>
			<th class="table-bordered border-light text-light">Y좌표</th>
			<th class="table-bordered border-light text-light">작업일자</th>
		</tr>
	</table>
	<script src="${path}/resources/script/javascript.js"}></script>
</body>
</html>
