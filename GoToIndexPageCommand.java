package by.htp.ellib.controller.command.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.ellib.controller.command.Command;
import by.htp.ellib.controller.command.util.CreatorFullURL;
import by.htp.ellib.entity.Book;
import by.htp.ellib.service.LibraryService;
import by.htp.ellib.service.ServiceProvider;

public class GoToIndexPageCommand implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ServiceProvider provider = ServiceProvider.getInstance();
		LibraryService bookService = provider.getLibraryService();
		
		
		String url = CreatorFullURL.create(request);
		request.getSession(true).setAttribute("prev_request", url);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/default.jsp");
		dispatcher.forward(request, response);
		
	}

}
