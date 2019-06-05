package by.htp.ellib.controller.command.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.ellib.controller.command.Command;
import by.htp.ellib.controller.command.util.CreatorFullURL;
import by.htp.ellib.dao.impl.ConnectionPoolException;
import by.htp.ellib.entity.Book;
import by.htp.ellib.service.LibraryService;
import by.htp.ellib.service.ServiceException;
import by.htp.ellib.service.ServiceProvider;

public class ShowAllBooksCommand implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ConnectionPoolException {

		ServiceProvider provider = ServiceProvider.getInstance();
		LibraryService libService = provider.getLibraryService();
		
		List<Book> books=new ArrayList<>();
		try {
			books=libService.showAllBooks();
		} catch (ServiceException e) {
		}
		
		
		if(books.equals(null)) {
			System.out.println("books is empty");
		}
		
		request.setAttribute("books", books);
		
		String url = CreatorFullURL.create(request);
		request.getSession(true).setAttribute("prev_request", url);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/display_books.jsp");
		dispatcher.forward(request, response);
	}

}
