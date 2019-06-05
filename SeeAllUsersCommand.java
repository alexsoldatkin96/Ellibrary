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
import by.htp.ellib.entity.UserData;
import by.htp.ellib.service.ClientService;
import by.htp.ellib.service.ServiceException;
import by.htp.ellib.service.ServiceProvider;

public class SeeAllUsersCommand implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ServiceProvider provider = ServiceProvider.getInstance();
		ClientService clientService = provider.getClientService();
		
		List<UserData> users=new ArrayList<>();
		try {
			users=clientService.seeAllUsers();
		} 
		catch (ServiceException e) {
		}
		System.out.println(users.size());
		if(users.equals(null)) {
			System.out.println("users is empty");
		}
		
		request.setAttribute("users", users);
		
		String url = CreatorFullURL.create(request);
		request.getSession(true).setAttribute("prev_request", url);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/display_users.jsp");
		dispatcher.forward(request, response);
	}

}
