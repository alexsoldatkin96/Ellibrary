package by.htp.ellib.controller.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.htp.ellib.controller.command.Command;
import by.htp.ellib.controller.command.util.CreatorFullURL;
import by.htp.ellib.entity.User;
import by.htp.ellib.service.ClientService;
import by.htp.ellib.service.ServiceException;
import by.htp.ellib.service.ServiceProvider;

public class AuthorizationCommand implements Command {

	private static final String PARAMETER_LOGIN = "login";
	private static final String PARAMETER_PASSWORD = "password";

	private static final String MAIN_PAGE = "/WEB-INF/jsp/main.jsp";
	private static final String INDEX_PAGE = "/WEB-INF/jsp/default.jsp";
	private static final String ADMIN_PAGE = "/WEB-INF/jsp/admin.jsp";
	private static final String ERROR_PAGE = "/WEB-INF/jsp/error.jsp";
	

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String login;
		String password;

		login    = request.getParameter(PARAMETER_LOGIN);
		password = request.getParameter(PARAMETER_PASSWORD);

		ServiceProvider provider = ServiceProvider.getInstance();
		ClientService service = provider.getClientService();

		
		String page;
		User user = null;
		
		System.out.println("данные для авторизации приняты");
		try {
			user = service.authorization(login, password);

			if (login.equals("admin")&&password.equals("admin")){
				request.setAttribute("admin", user);
				page = ADMIN_PAGE;
			}else if(user == null) {
				request.setAttribute("error", "Нет пользователя с таким логином и паролем, попробуйте ещё раз");
				page = INDEX_PAGE;
			} else {
				request.setAttribute("user", user);
				page = MAIN_PAGE;
			}
			System.out.println("AuthorizationCommand");
		} catch (ServiceException e) {
			request.setAttribute("error", "Service Exception");
			
			page = ERROR_PAGE;
		}
		
		
		String url = CreatorFullURL.create(request);
		request.getSession(true).setAttribute("prev_request", url);

		RequestDispatcher dispatcher = request.getRequestDispatcher(page);
		dispatcher.forward(request, response);

	}

}
