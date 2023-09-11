<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>북마크 삭제</title>

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
	<h1>북마크 삭제</h1>
	<jsp:include page="../top.jsp" flush="false" />
	<br>북마크를 삭제하시겠습니까?
	<br>
	<table class="table table-bordered table-striped" sytle="width=100px;">
		<tbody>
			<tr>
			</tr>
			<tr>
				<th>북마크 이름</th>
				<td>${groupName}</td>
			</tr>
			<tr>
				<th>와이파이명</th>
				<td>${wifiName}</td>
			</tr>
			<tr>
				<th>등록일자</th>
				<td>${date}</td>
			</tr>
			<tr>
				<form action="delete.do" method="post">
					<td colspan="2" style="text-align: center;"><a
						href="javascript:history.back()">돌아가기</a> | <input type="submit"
						value="삭제"> <br> <input type="hidden" name="id"
						value="${id}"></td>
				</form>

			</tr>
		</tbody>
	</table>

	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
</body>
</html>