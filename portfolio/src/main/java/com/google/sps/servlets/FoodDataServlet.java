package com.google.sps.servlets;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/food-data")
public class FoodDataServlet extends HttpServlet {

  private Map<String, Integer> foodVotes = new HashMap<>();

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("application/json");
    Gson gson = new Gson();
    String json = gson.toJson(foodVotes);
    response.getWriter().println(json);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String preference = request.getParameter("taste-preference");
    int currentVotes = foodVotes.containsKey(preference) ? foodVotes.get(preference) : 0;
    foodVotes.put(preference, currentVotes + 1);

    response.sendRedirect("/index.html");
  }
}