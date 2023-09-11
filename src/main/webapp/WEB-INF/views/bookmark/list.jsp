<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>북마크 리스트</title>
</head>
<body>
	<h1>북마크 목록</h1>
	<jsp:include page="../top.jsp" flush="false" />
	<table class="table table-bordered table-hover">
		<thead>
			<tr bgcolor="#04B45F">
				<th class="table-bordered border-light text-light">ID</th>
				<th class="table-bordered border-light text-light">북마크 이름</th>
				<th class="table-bordered border-light text-light">와이파이명</th>
				<th class="table-bordered border-light text-light">등록일자</th>
				<th class="table-bordered border-light text-light">비고</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${bookmarks}" var="bookmark">
				<tr class="table table-striped table-hover">
					<!-- ID -->
					<td>${bookmark.id}</td>
					<!-- 북마크 이름 -->
					<td>${bookmark.groupName}</td>
					<!--와이파이명  -->
					<td>${bookmark.wifiName}</td>
					<!-- 등록일자 -->
					<td>${bookmark.date}</td>
					<!--삭제는 post로 보내야하는데 a tag는 post형식이 안되므로 
					hidden form을 만들어 javascript를 이용해 submit하는 형태로 전달  -->
					<td><a
						href="list/delete?id=${bookmark.id}&groupName=${bookmark.groupName}&wifiName=${bookmark.wifiName}&date=${bookmark.date}">삭제</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>