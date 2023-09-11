<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>와이파이 상세보기</title>

<style>
th {
	font-size: 17pt;
	color: white;
	background-color: #04B45F;
	width: 500px;
	text-align: center !important;
}
</style>
</head>
<body>
	<script>
		function addBookmark() {
			var form = bookmarkForm;
			var size=[[${size}]];
			if (form.groupId.value == "null"){
				if(size==0)
					alert("먼저 북마크 그룹을 추가해주세요.");
				else
					alert("북마크 그룹을 선택해주세요.");
				}
			else
				form.submit(); 
		}
	</script>
	<h1>와이파이 정보 구하기</h1>
	<jsp:include page="top.jsp" flush="flase" />
	<form name="bookmarkForm" action="bookmark/add" method="post">
		<select name="groupId">
			<option value="null">그룹 이름 선택</option>
			<c:forEach items="${groups}" var="group">
				<option value="${group.id}">${group.name}</option>
			</c:forEach>
		</select> <input type="hidden" name="manageId" value="${wifi.manageID}">
		<input type="hidden" name="lat" value="${lat}"> <input
			type="hidden" name="lnt" value="${lnt}"> <input type="button"
			value="북마크 추가하기" onclick="addBookmark()">
	</form>

	<table class="table table-bordered table-striped" sytle="width=100px;">
		<tbody>
			<tr>
				<th>거리(Km)</th>
				<td>${wifi.distance}</td>
			</tr>
			<tr>
				<th>관리번호</th>
				<td>${wifi.manageID}</td>
			</tr>
			<tr>
				<th>자치구</th>
				<td>${wifi.district}</td>
			</tr>
			<tr>
				<th>와이파이명</th>
				<td>${wifi.name}</td>
			</tr>
			<tr>
				<th>도로명주소</th>
				<td>${wifi.address1}</td>
			</tr>
			<tr>
				<th>상세주소</th>
				<td>${wifi.address2}</td>
			</tr>
			<tr>
				<th>설치위치(층)</th>
				<td></td>
			</tr>
			<tr>
				<th>설치유형</th>
				<td>${wifi.installType}</td>
			</tr>
			<tr>
				<th>설치기관</th>
				<td>${wifi.installBy}</td>
			</tr>
			<tr>
				<th>서비스구분</th>
				<td>${wifi.serviceType}</td>
			</tr>
			<tr>
				<th>망종류</th>
				<td>${wifi.networkType}</td>
			</tr>
			<tr>
				<th>설치년도</th>
				<td>${wifi.year}</td>
			</tr>
			<tr>
				<th>실내외구분</th>
				<td>${wifi.inOut}</td>
			</tr>
			<tr>
				<th>WIFI접속환경</th>
				<td></td>
			</tr>
			<tr>
				<th>X좌표</th>
				<td>${wifi.lnt}</td>
			</tr>
			<tr>
				<th>Y좌표</th>
				<td>${wifi.lat}</td>
			</tr>
			<tr>
				<th>작업일자</th>
				<td>${wifi.refreshDate}</td>
			</tr>
		</tbody>
	</table>
</body>
</html>