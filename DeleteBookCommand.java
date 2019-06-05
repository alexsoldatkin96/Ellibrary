package by.htp.ellib.controller.command.impl;

import java.io.IOException;

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

public class DeleteBookCommand implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ConnectionPoolException {
		
		String title;
		String idStr;
		int id;
		
		title = request.getParameter("title");
		idStr = request.getParameter("id");
		id=Integer.parseInt(idStr);
		
		System.out.println(id+"");
		
		ServiceProvider provider = ServiceProvider.getInstance();
		LibraryService libService = provider.getLibraryService();

//		con.commit();
	try {
		libService.deleteBook(id);
	}
	catch (ServiceException e) {
		request.setAttribute("error", "Service Exception");
	}
	
	String url = CreatorFullURL.create(request);
	request.getSession(true).setAttribute("prev_request", url);

	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/admin.jsp");
	dispatcher.forward(request, response);
	
	}

}
