package by.htp.ellib.controller.command.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.ellib.controller.command.Command;
import by.htp.ellib.controller.command.util.CreatorFullURL;
import by.htp.ellib.entity.UserData;
import by.htp.ellib.service.ClientService;
import by.htp.ellib.service.ServiceException;
import by.htp.ellib.service.ServiceProvider;

public class RegistrationCommand implements Command{
	
	private static final String PARAMETER_LOGIN = "login";
	private static final String PARAMETER_PASSWORD = "password";
	private static final String PARAMETER_EMAIL = "email";
	private static final String PARAMETER_NAME = "name";
	private static final String PARAMETER_SURNAME = "surname";
	private static final String PARAMETER_SEX = "sex";
	private static final String PARAMETER_BIRTHYEAR = "birthYear";
	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String login;
		String password;
		String email;
		String name;
		String surname;
		String sex;
		long birthYear;

		login    = request.getParameter(PARAMETER_LOGIN);
		password = request.getParameter(PARAMETER_PASSWORD);
		email   = request.getParameter(PARAMETER_EMAIL);
		name = request.getParameter(PARAMETER_NAME);
		surname    = request.getParameter(PARAMETER_SURNAME);
		sex = request.getParameter(PARAMETER_SEX);
		birthYear   = Integer.parseInt(request.getParameter(PARAMETER_BIRTHYEAR));
	
		System.out.println("данные для регистрации приняты");

		UserData userRegistrating = new UserData(login, password, email, name,surname,sex,birthYear);
		
		ServiceProvider provider = ServiceProvider.getInstance();
		ClientService service = provider.getClientService();

		try {
			service.registration(userRegistrating);
			
		} catch (ServiceException e) {
			request.setAttribute("error", "Service Exception");
			
		}
	
	
			
		String url = CreatorFullURL.create(request);
		request.getSession(true).setAttribute("prev_request", url);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/default.jsp");
		dispatcher.forward(request, response);
	}
}
