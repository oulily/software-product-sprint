package com.google.sps.servlets;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Scanner;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Returns language data as a JSON object, e.g. {"English": 20, "Spanish": 10}] */
@WebServlet("/language-data")
public class LanguageDataServlet extends HttpServlet {

  private LinkedHashMap<String, Integer> mostSpokenLanguages = new LinkedHashMap<>();

  @Override
  public void init() {
    Scanner scanner = new Scanner(getServletContext().getResourceAsStream(
        "/WEB-INF/language-spoken-by-population.csv"));
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();
      String[] cells = line.split(",");

      String language = String.valueOf(cells[0]);
      Integer population = Integer.valueOf(cells[1]);

      mostSpokenLanguages.put(language, population);
    }
    scanner.close();
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("application/json");
    Gson gson = new Gson();
    String json = gson.toJson(mostSpokenLanguages);
    response.getWriter().println(json);
  }
}