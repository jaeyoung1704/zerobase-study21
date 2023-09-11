<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>북마크 그룹 수정</title>
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
	<h1>북마크 그룹 수정</h1>

	<jsp:include page="../top.jsp" flush="false" />
	<table class="table table-bordered table-striped">
		<tbody>
			<tr>
			</tr>
			<tr>
				<th>북마크 이름</th>
				<td><input id="name" type="text" value="${name}"></td>
			</tr>
			<tr>
				<th>순서</th>
				<td><input id="order" type="text" value="${order}"></td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center;"><a
					href="javascript:history.back()">돌아가기</a> | <input type="button"
					value="수정" onclick="editBookmarkGroup(${id})" /></td>
			</tr>
		</tbody>
	</table>
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script>
		function editBookmarkGroup(id) {
			var oldName = [['${name}']];
			var oldOrder = [['${order}']];
			var form = document.hiddenForm;
			var newName = $('#name').val();
			var newOrder = $('#order').val();
			form.id.value = id;
			form.newName.value = newName;
			form.order.value = newOrder;
			if(newName==oldName && newOrder==oldOrder)
				alert("북마크 이름과 순서가 기존과 같습니다");
			else
				form.submit();
		}
	</script>
	<form name="hiddenForm" action="edit.do" method="post">
		<input type="hidden" id="id" name="id" /> <input type="hidden"
			id="oldName" name="oldName" value="${name}" /><input type="hidden"
			id="newName" name="newName" /> <input type="hidden" id="order"
			name="order" />
	</form>
</body>
</html>