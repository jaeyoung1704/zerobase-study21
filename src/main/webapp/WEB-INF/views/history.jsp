<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>history</title>
</style>

</head>
<body>
	<h1>위치 히스토리 목록</h1>
	<jsp:include page="top.jsp" flush="false" />

	<table class="table table-bordered">
		<tr bgcolor="#04B45F">
			<th class="table-bordered border-light text-light">ID</th>
			<th class="table-bordered border-light text-light">X좌표</th>
			<th class="table-bordered border-light text-light">Y좌표</th>
			<th class="table-bordered border-light text-light">조회일자</th>
			<th class="table-bordered border-light text-light">비고</th>
		</tr>
		<c:forEach items="${posList}" var="pos" varStatus="index">
			<tr>
				<td>${pos.ID}</td>
				<td>${pos.lnt}</td>
				<td>${pos.lat}</td>
				<td>${pos.date}</td>
				<td style="height: 30px;">
					<form action=history/delete method="post">
						<input type=submit value="삭제">
						<!--  -->
						<input type=hidden name=ID value=${pos.ID}>
					</form>
				</td>
			</tr>
		</c:forEach>
	</table>
	<script>
		
	</script>
</body>
</html>