<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>북마크 그룹 추가</title>
<style>
th {
	font-size: 17pt;
	color: white;
	background-color: #04B45F;
	text-align: center !important;
}
</style>
</head>
<body>
	<h1>북마크 그룹 추가</h1>

	<jsp:include page="../top.jsp" flush="false" />
	<table class="table table-bordered table-striped">
		<tbody>
			<tr>
			</tr>
			<tr>
				<th>북마크 이름</th>
				<td><input id="name" type="text"></td>
			</tr>
			<tr>
				<th>순서</th>
				<td><input id="order" type="text"></td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center;"><a
					href="javascript:history.back()">돌아가기</a> | <input type="button"
					value="추가" onclick="addBookmarkGroup()" /></td>
			</tr>
		</tbody>
	</table>
	<script src="http://code.jquery.com/jquery-latest.js"></script>
	<script>
		function addBookmarkGroup() {
			var form = document.hiddenForm;
			var name = $('#name').val();
			var order = $('#order').val();

			form.name.value = name;
			form.order.value = order;
			form.submit();
		}
	</script>
	<form name="hiddenForm" action="add.do" method="post">
		<input type="hidden" id="name" name="name" /> <input type="hidden"
			id="order" name="order" />
	</form>
</body>
</html>