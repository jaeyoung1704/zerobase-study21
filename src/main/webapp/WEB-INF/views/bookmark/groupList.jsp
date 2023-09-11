<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>북마크 그룹 리스트</title>
</head>
<body>
	<h1>북마크 그룹 목록</h1>
	<jsp:include page="../top.jsp" flush="false" />
	<br>
	<input type="button" value="북마크 그룹 이름 추가"
		onclick="location.href='groupList/add'">
	<table class="table table-bordered table-hover">
		<thead>
			<tr bgcolor="#04B45F">
				<th class="table-bordered border-light text-light">ID</th>
				<th class="table-bordered border-light text-light">북마크 이름</th>
				<th class="table-bordered border-light text-light">순서</th>
				<th class="table-bordered border-light text-light">등록일자</th>
				<th class="table-bordered border-light text-light">수정일자</th>
				<th class="table-bordered border-light text-light">비고</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${groups}" var="group">
				<tr class="table table-striped table-hover">
					<!-- ID -->
					<td>${group.id}</td>
					<!-- 그룹이름 -->
					<td>${group.name}</td>
					<!--순서  -->
					<td>${group.order}</td>
					<!-- 등록일자 -->
					<td>${group.addDate}</td>
					<!-- 수정일자 -->
					<td>${group.editDate}</td>
					<!--삭제는 post로 보내야하는데 a tag는 post형식이 안되므로 
					hidden form을 만들어 javascript를 이용해 submit하는 형태로 전달  -->
					<td><a
						href="groupList/edit?id=${group.id}&name=${group.name}
						&order=${group.order}">수정</a>
						<a
						href="groupList/delete?id=${group.id}&name=${group.name}
						&order=${group.order}">삭제</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>