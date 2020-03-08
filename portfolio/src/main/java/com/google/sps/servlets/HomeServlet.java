package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class HomeServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h1>Message Box</h1>");

    UserService userService = UserServiceFactory.getUserService();
    if(userService.isUserLoggedIn()) {
        String userEmail = userService.getCurrentUser().getEmail();
        String urlToRedirectToAfterUserLogsOut = "/login";
        String logoutUrl = userService.createLogoutURL(urlToRedirectToAfterUserLogsOut);

        response.getWriter().println("<p>Hello " + userEmail + "!</p>");
        response.getWriter().println("<p>Logout <a href=\"" + logoutUrl + "\"here</a>.</p>");
        out.println("<p>Hello " + userService.getCurrentUser().getEmail() + "!</p>");
        out.println("<p>Type a message and click submit:</p>");
        out.println("<form method=\"POST\" action=\"/login\">");
        out.println("<label>Enter a message:</label><br>");
        out.println("<input type=\"text\" name=\"message-input\"><br>");
        out.println("<input type=\"submit\" />");
        out.println("</form>");
    } else {
        String urlToRedirectToAfterUserLogsIn = "/login";
        String loginUrl = userService.createLoginURL(urlToRedirectToAfterUserLogsIn);

        response.getWriter().println("<p>Hi There!<p>");
        response.getWriter().println("<p>Login <a href=\"" + loginUrl + "\">here</a>.</p>");
    }

    out.println("<ul>");
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Query query = new Query("Message").addSort("timestamp", SortDirection.DESCENDING);
    PreparedQuery results = datastore.prepare(query);
    for (Entity entity : results.asIterable()) {
      String message = (String) entity.getProperty("message");
      String email = (String) entity.getProperty("email");
      out.println("<li>" + email + ": " + message + "</li>");
    }
    out.println("</ul>");

  }
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
      UserService userService = UserServiceFactory.getUserService();

      if(!userService.isUserLoggedIn()) {
          response.sendRedirect("/login");
          return;
      }

      String message = request.getParameter("message");
      String email = userService.getCurrentUser().getEmail();

      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
      Entity messageEntity = new Entity("Message");
      messageEntity.setProperty("message", message);
      messageEntity.setProperty("email", email);
      messageEntity.setProperty("timestamp", System.currentTimeMillis());
      datastore.put(messageEntity);

      response.sendRedirect("/login");
  }
}