<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<html>
<head>
<title>LoadedWifi</title>
<!-- [Bootstrap] -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
<link rel="stylesheet" href="${path}/resources/css/style.css">
</head>
<body>
	<h1>와이파이 정보 구하기</h1>
	<jsp:include page="top.jsp" flush="flase" />
	<form name="posForm" action="loadFromDB" method="get">
		LAT: <input type="TEXT" name="lat" size="10" value="${lat}" /> LNT: <input
			type="TEXT" name="lnt" size="10" value="${lnt}" /> <input
			type="button" value="내 위치 가져오기" onclick="pos();"> <input
			type="button" value="근처 WIFI 정보 보기" onclick="load();">
	</form>
	<table class="table table-bordered table-hover table-striped">
		<thead>
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
		</thead>
		<tbody>
			<c:forEach items="${wifiList}" var="wifi">
				<tr class="table table-striped table-hover">
					<!-- 거리 -->
					<td>${wifi.distance}</td>
					<!-- 관리번호 -->
					<td>${wifi.manageID}</td>
					<!-- 자치구 -->
					<td>${wifi.district}</td>
					<!-- 와이파이명 -->
					<td><a
						href="wifiDetail?manageID=${wifi.manageID}
						&lat=${lat}&lnt=${lnt}">${wifi.name}</a></td>
					<!-- 도로명주소 -->
					<td>${wifi.address1}</td>
					<!-- 상세주소 -->
					<td>${wifi.address1}</td>
					<!-- 설치위치 -->
					<td></td>
					<!-- 설치유형 -->
					<td>${wifi.installType}</td>
					<!-- 설치기관 -->
					<td>${wifi.installBy}</td>
					<!-- 서비스구분 -->
					<td>${wifi.serviceType}</td>
					<!-- 망종류 -->
					<td>${wifi.networkType}</td>
					<!-- 설치년도 -->
					<td>${wifi.year}</td>
					<!-- 실내외구본 -->
					<td>${wifi.inOut}</td>
					<!-- WIFI접속환경 -->
					<td></td>
					<!-- X좌표 -->
					<td>${wifi.lat}</td>
					<!-- Y좌표 -->
					<td>${wifi.lnt}</td>
					<!-- 작업일자 -->
					<td>${wifi.refreshDate}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<script src="${path}/resources/script/javascript.js"}></script>
</body>
</html>
