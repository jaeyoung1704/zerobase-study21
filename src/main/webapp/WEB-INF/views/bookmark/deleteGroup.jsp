<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>북마크 그룹 삭제</title>
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
	<h1>북마크 그룹 삭제</h1>

	<jsp:include page="../top.jsp" flush="false" />
	<table class="table table-bordered table-striped" sytle="width=100px;">
		<tbody>
			<tr>
			</tr>
			<tr>
				<th>북마크 이름</th>
				<td><input type="text" value="${name}" readonly></td>
			</tr>
			<tr>
				<th>순서</th>
				<td><input type="text" value="${order}" readonly></td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center;"><a
					href="javascript:history.back()">돌아가기</a> | <input type="button"
					value="삭제" onclick="deleteBookmarkGroup(${id})"></td>
			</tr>
		</tbody>
	</table>
	<script>
		function deleteBookmarkGroup(id) {
			if (confirm("그룹을 삭제하면 속해있는 북마크들은 전부 제거됩니다. 그렇게 하시겠습니까?")) {
				var form = document.hiddenForm;
				form.id.value = id;
				form.submit();
			}
		}
	</script>
	<form name="hiddenForm" action="delete.do" method="post">
		<input type="hidden" name="id">
	</form>
</body>
</html>