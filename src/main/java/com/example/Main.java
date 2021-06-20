package com.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

@Controller
@SpringBootApplication
public class Main {

  @Value("${spring.datasource.url}")
  private String dbUrl;

  @Autowired
  private DataSource dataSource;

  public static void main(String[] args) throws Exception {
    SpringApplication.run(Main.class, args);
  }

  @RequestMapping("/")
  String index(Map<String, Object> model) {
    try (Connection connection = dataSource.getConnection()) {
      Statement stmt = connection.createStatement();
      stmt.executeUpdate("CREATE TABLE IF NOT EXISTS rectangles (id serial, name varchar(20), height varchar(20), width varchar(20), color varchar(20))");
      String sql = "SELECT * FROM rectangles";
      ResultSet rectangles = stmt.executeQuery(sql);
      Rectangle rectangle = new Rectangle();
      ArrayList rectangles2 = new ArrayList();
      rectangle.setBgcolor("red");
      rectangle.setName("Bob");
      rectangle.setHeight("30");
      rectangle.setWidth("20");
      rectangles2.add(rectangle);
      while(rectangles.next()){
        Rectangles2.add(rectangles.getRow());
      }
      model.put("rectangles", rectangles2);
      return "index";
    } catch (Exception e) {
      model.put("message", e.getMessage());
      return "error";
    }
  }

  @GetMapping(
    path = "/newrectangle"
  )
  public String getRectangleForm(Map<String, Object> model){
    Rectangle rectangle = new Rectangle();  
    model.put("newrectangle", rectangle);
    return "newrectangle";
  }

  @PostMapping(
    path = "/newrectangle",
    consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE}
  )
  public String handleBrowserRectangleSubmit(Map<String, Object> model, Rectangle newrectangle) throws Exception {
    try (Connection connection = dataSource.getConnection()) {
      Statement stmt = connection.createStatement();
      stmt.executeUpdate("CREATE TABLE IF NOT EXISTS rectangles (id serial, name varchar(20), height varchar(20), width varchar(20), color varchar(20))");
      String sql = "INSERT INTO rectangles (name, height, width, color) VALUES ('" + newrectangle.getName() + "','" + newrectangle.getHeight() + "','" + newrectangle.getWidth() + "','" + newrectangle.getBgcolor() + "');";
      stmt.executeUpdate(sql);
      return "redirect:/newrectangle";
    } catch (Exception e) {
      model.put("message", e.getMessage());
      return "error";
    }

  }

  @GetMapping("/rectangle")
  public String getRectangleSelected(Map<String, Object> model){
    System.out.println(model.get("newrectangle"));
    Rectangle rectangle = new Rectangle();
    model.put("rectangle", rectangle);
    return "rectangle";
  }
  
  @PostMapping(
    path = "rectangle",
    consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE}
  )
  public String handleBrowserRectangleSaving(Map<String, Object> model, Rectangle rectangle) throws Exception {
      try (Connection connection = dataSource.getConnection()) {
        Statement stmt = connection.createStatement();
        String sql = "UPDATE rectangles SET name = " + rectangle.getName() + ", height = " + rectangle.getHeight() 
        + ", width = " + rectangle.getWidth() + ", color = " + rectangle.getBgcolor() + "where name =="+rectangle.getName()+";";
        stmt.executeUpdate(sql);
        return "redirect:/rectangle";
      } catch (Exception e) {
        model.put("message", e.getMessage());
        return "error";
      }
  }


  @RequestMapping("/db")
  String db(Map<String, Object> model) {
    try (Connection connection = dataSource.getConnection()) {
      Statement stmt = connection.createStatement();
      stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
      stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
      ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");

      ArrayList<String> output = new ArrayList<String>();
      while (rs.next()) {
        output.add("Read from DB: " + rs.getTimestamp("tick"));
      }

      model.put("records", output);
      return "db";
    } catch (Exception e) {
      model.put("message", e.getMessage());
      return "error";
    }
  }

  @Bean
  public DataSource dataSource() throws SQLException {
    if (dbUrl == null || dbUrl.isEmpty()) {
      return new HikariDataSource();
    } else {
      HikariConfig config = new HikariConfig();
      config.setJdbcUrl(dbUrl);
      return new HikariDataSource(config);
    }
  }

}

