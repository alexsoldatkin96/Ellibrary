package by.htp.ellib.controller.command.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.ellib.controller.command.Command;
import by.htp.ellib.controller.command.util.CreatorFullURL;
import by.htp.ellib.dao.DaoException;
import by.htp.ellib.dao.impl.ConnectionPoolException;
import by.htp.ellib.dao.impl.SqlDao;
import by.htp.ellib.entity.Book;
import by.htp.ellib.service.ClientService;
import by.htp.ellib.service.LibraryService;
import by.htp.ellib.service.ServiceException;
import by.htp.ellib.service.ServiceProvider;

public class AddNewBookCommand extends SqlDao implements Command  {
	private static final String MAIN_PAGE = "/WEB-INF/jsp/main.jsp";
	private static final String INDEX_PAGE = "/WEB-INF/jsp/default.jsp";
	private static final String ADMIN_PAGE = "/WEB-INF/jsp/admin.jsp";
	private static final String ERROR_PAGE = "/WEB-INF/jsp/error.jsp";
	private static final String ADD_NEW_BOOK_PAGE = "/WEB-INF/jsp/add_new_book.jsp";
	
	List<Book> books=new ArrayList<Book>();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ConnectionPoolException {
		
		
			String title;
			String author;
			String price;
			long longPrice;
			String genre;
			String releaseYear;
			long longReleaseYear;
			
			
			title = request.getParameter("title");
			author = request.getParameter("author");
			price =request.getParameter("price");
			longPrice= Long.parseLong(price);
			genre= request.getParameter("genre");
			releaseYear=request.getParameter("release_year");
			longReleaseYear=Long.parseLong(releaseYear);
			
			System.out.println("Данные о новой книге приняты");
			
			Book book=new Book(title,author,longPrice, genre, longReleaseYear);
			
			System.out.println("информация о книге записана");
			
			ServiceProvider provider = ServiceProvider.getInstance();
			LibraryService libService = provider.getLibraryService();

			
//			con.commit();
		try {
			libService.addingNewBook(book);
		}
		catch (ServiceException e) {
			request.setAttribute("error", "Service Exception");
		}

		
		String url = CreatorFullURL.create(request);
		request.getSession(true).setAttribute("prev_request", url);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/add_new_book.jsp");
		dispatcher.forward(request, response);
	}

}
