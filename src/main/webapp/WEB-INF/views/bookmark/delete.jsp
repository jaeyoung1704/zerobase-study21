<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>�ϸ�ũ ����</title>

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
	<h1>�ϸ�ũ ����</h1>
	<jsp:include page="../top.jsp" flush="false" />
	<br>�ϸ�ũ�� �����Ͻðڽ��ϱ�?
	<br>
	<table class="table table-bordered table-striped" sytle="width=100px;">
		<tbody>
			<tr>
			</tr>
			<tr>
				<th>�ϸ�ũ �̸�</th>
				<td>${groupName}</td>
			</tr>
			<tr>
				<th>�������̸�</th>
				<td>${wifiName}</td>
			</tr>
			<tr>
				<th>�������</th>
				<td>${date}</td>
			</tr>
			<tr>
				<form action="delete.do" method="post">
					<td colspan="2" style="text-align: center;"><a
						href="javascript:history.back()">���ư���</a> | <input type="submit"
						value="����"> <br> <input type="hidden" name="id"
						value="${id}"></td>
				</form>

			</tr>
		</tbody>
	</table>

	<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
</body>
</html>