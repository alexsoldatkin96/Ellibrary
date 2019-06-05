package by.htp.ellib.controller.command.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.ellib.controller.command.Command;
import by.htp.ellib.controller.command.util.CreatorFullURL;
import by.htp.ellib.dao.DAOProvider;
import by.htp.ellib.dao.impl.ConnectionPoolException;
import by.htp.ellib.entity.Book;
import by.htp.ellib.service.LibraryService;
import by.htp.ellib.service.ServiceException;
import by.htp.ellib.service.ServiceProvider;
import by.htp.ellib.controller.command.impl.AddNewBookCommand;
public class FindByAuthorCommand implements Command{

	private static final String PARAMETER_AUTHOR="author";
	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ConnectionPoolException {
		
		System.out.println("books");

		String author;
		author = request.getParameter(PARAMETER_AUTHOR);

		ServiceProvider provider = ServiceProvider.getInstance();
		LibraryService libService = provider.getLibraryService();
		
		List<Book> books=new ArrayList<>();
		try {
			books=libService.findByAuthor(author);
		} catch (ServiceException e) {
			
		}
		if(books.equals(null)) {
			System.out.println("books is empty");
		}
		else {
		for(int i=0; i<books.size(); i++){
			by.htp.ellib.entity.Book b = books.get(i);
			System.out.println(b.getTitle() +  " - " + b.getLongPrice());
		}
		}
		
		request.setAttribute("books", books);
		
		String url = CreatorFullURL.create(request);
		request.getSession(true).setAttribute("prev_request", url);
//		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/display_books.jsp");
		dispatcher.forward(request, response);
		
	}

}
