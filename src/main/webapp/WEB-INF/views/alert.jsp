<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>알림!</title>
<!-- alert 처리만을 위한 JSP -->
<script>
	var msg = [ [ '${msg}' ] ];
	var url = [ [ '${url}' ] ];
	alert(msg);
	location.href = url;
</script>
</head>
<body>


</body>


</html>