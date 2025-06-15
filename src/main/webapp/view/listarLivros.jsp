<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, model.Livro" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<meta charset="UTF-8">
<title>Acervo de livros</title>
</head>
<body>
	<div>
		<h1>Acervo de livros</h1>
		<%
			List<Livro> livros = (List<Livro>) request.getAttribute("livros");
			
			if (livros == null || livros.isEmpty()) {
		%>
			<p>Nenhum livro cadastrado ainda.</p>
		<%
			} else {	
		%>
            <table>
                <thead>
                    <tr>
                        <th>Título</th>
                        <th>Autor</th>
                        <th>Ano de Publicação</th>
                        <th>ISBN</th>
                    </tr>
                </thead>
                <tbody>
                <%
                    for (Livro livro : livros) {		
                %>
                    <tr>
                        <td><%= livro.getTitulo() %></td>
                        <td><%= livro.getAutor() %></td>
                        <td><%= livro.getAnoDePublicacao() %></td>
                        <td><%= livro.getIsbn() %></td>
                    </tr>
                <%
                    }
                %>
                </tbody>
            </table>
		<%
			}
        %>
	</div>
	<div>
		<h2>Excluir Livro por ISBN</h2>
		<form action="<%= request.getContextPath() %>/LivroServlet" method="POST">
			<label for ="isbnExcluir">ISBN:</label>
			<input type="text" name="isbn" maxlength="13" required placeholder="Digite o ISBN">
			<button type="submit" name="acao" value="excluirLivro">Excluir</button>
		</form>
	</div>
	<br>
	<div>
		<a href="<%= request.getContextPath() %>/index.html">Voltar para a página principal</a>
	</div>
</body>
</html>