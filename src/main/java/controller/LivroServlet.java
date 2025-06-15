package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Livro;

@WebServlet("/LivroServlet")
public class LivroServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private List<Livro> livros;
	
	public void init() {
		livros = new ArrayList<>();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acao = request.getParameter("acao");
		switch (acao) {
		case "cadastrarLivro": cadastrarLivro(request, response);
			break;
		case "excluirLivro": excluirLivro(request, response);
			break;
		}
	}
	
	private void cadastrarLivro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String titulo = request.getParameter("titulo");
		String autor = request.getParameter("autor");
		String anoDePublicacoStr = request.getParameter("anoDePublicacao");
		String isbn= request.getParameter("isbn");
		
		int anoDePublicacao = Integer.parseInt(anoDePublicacoStr);
		
		validarIsbn(isbn, request, response);
		
		livros.add(new Livro(titulo, autor, anoDePublicacao, isbn));
		
		response.sendRedirect(request.getContextPath() + "/index.html");
		
	}
	
	private void validarIsbn(String isbn, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!testarFormatacaoIsbn(isbn)) {
			request.setAttribute("erro", "ISBN inválido para cadastro. O ISBN deve conter apenas números.");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/view/erro.jsp");
			dispatcher.forward(request, response);
			return;
		}
		
		if (isbn.length() != 13) {
			request.setAttribute("erro", "ISBN inválido para cadastro. São necessários 13 dígitos. Tente novamente.");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/view/erro.jsp");
			dispatcher.forward(request, response);
			return;
		}
		
	}
	
	private boolean testarFormatacaoIsbn(String isbn) {
		for (char c : isbn.toCharArray()) {
			if(!Character.isDigit(c)) {
				return false;
			}
		}
		return true;
	}
	
	private void excluirLivro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String isbn = request.getParameter("isbn");
		
		validarIsbn(isbn, request, response);
		
		boolean removerLivro = removerLivro(isbn);
		
		validarLivro(removerLivro, isbn, request, response);
		
		response.sendRedirect(request.getContextPath() + "/index.html");
		
	}
	
	private boolean removerLivro(String isbn) {
		boolean livroRemovido = false;
		Iterator<Livro> livrosIterator = livros.iterator();
		while (livrosIterator.hasNext()) {
			Livro livro = livrosIterator.next();
			if (livro.getIsbn().equals(isbn)) {
				livrosIterator.remove();
				livroRemovido = true;
				break;
			}
			
		}
		return livroRemovido;
	}
	
	private void validarLivro(boolean removerLivro, String isbn, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!removerLivro) {
			request.setAttribute("erro", "Livro com ISBN " + isbn + " não encontrado no acervo para exclusão.");
	        RequestDispatcher dispatcher = request.getRequestDispatcher("/view/erro.jsp");
	        dispatcher.forward(request, response);
	        return;
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acao = request.getParameter("acao");
		switch (acao) {
		case "listarLivros": listarLivros(request, response);
			break;
		}	
	}
	
	private void listarLivros(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("livros", livros);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/view/listarLivros.jsp");
		dispatcher.forward(request, response);
		return;
	}
	
}
