<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<title>Erro!</title>
</head>
<body>
	<div>
		<h1>Ocorreu um erro!</h1>
		<p>
			<%
				out.println((String) request.getAttribute("erro"));
			%>
		</p>
	</div>
	<div>
		<a href="<%= request.getContextPath() %>/index.html">Voltar para a página principal</a>
	</div>
</body>
</html>