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
import org.w3c.dom.css.Rect;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import javax.sql.DataSource;

import java.security.spec.ECPublicKeySpec;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

@Controller
@SpringBootApplication
public class Main {
  private int i = 0;

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
      String sql = "SELECT * FROM rectangles";
      ResultSet rs = stmt.executeQuery(sql);
      ArrayList output = new ArrayList();
      while(rs.next()){
        Rectangle rectangle = new Rectangle();
        rectangle.setName(rs.getString("name"));
        rectangle.setHeight(rs.getString("height"));
        rectangle.setWidth(rs.getString("width"));
        rectangle.setBgcolor(rs.getString("color"));
        rectangle.setId(rs.getString("id"));
        ArrayList rect = new ArrayList();
        rect.add("Name: "+rectangle.getName()+"| Color: "+rectangle.getBgcolor());
        rect.add("rectangle/"+rs.getString("id"));
        output.add(rect);
      }
      model.put("rectangles", output);
      return "index";
      }catch (Exception e){
        model.put("Message", e.getMessage());
      return "error";
      }
  }

  @GetMapping(
    path = "/newrectangle"
  )
  public String getRectangleForm(Map<String, Object> model){
    Rectangle rectangle = new Rectangle();  
    model.put("rectangle", rectangle);
    return "newrectangle";
  }

  @PostMapping(
    path = "/newrectangle",
    consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE}
  )
  public String handleBrowsernewRectangleSubmit(Map<String, Object> model, Rectangle rectangle) throws Exception {
    try (Connection connection = dataSource.getConnection()) {
      Statement stmt = connection.createStatement();
      stmt.executeUpdate("CREATE TABLE IF NOT EXISTS rectangles (id serial, name varchar(20), height varchar(20), width varchar(20), color varchar(20))");
      String sql = "INSERT INTO rectangles (name, height, width, color) VALUES ('" + rectangle.getName() + "', '" 
      + rectangle.getHeight() + "', '" + rectangle.getWidth() + "', '" + rectangle.getBgcolor() + "');";
      stmt.executeUpdate(sql);
      return "redirect:/";
    } catch (Exception e) {
      model.put("message", e.getMessage());
      return "error";
    }
  }
  

  @GetMapping("/rectangle/{nid}")
  public String getRectangleSelected(Map<String, Object> model, @PathVariable String nid){
    try (Connection connection = dataSource.getConnection()) {
      Statement stmt = connection.createStatement();
      String sql = "SELECT * FROM rectangles WHERE Id = '"+nid+"'";
      Rectangle rectangle = new Rectangle();
      ResultSet rs = stmt.executeQuery(sql);
      rs.next();
      rectangle.setName(rs.getString("name"));
      rectangle.setHeight(rs.getString("height"));
      rectangle.setWidth(rs.getString("width"));
      rectangle.setBgcolor(rs.getString("color"));
      rectangle.setId(rs.getString("id"));
      model.put("rectangle", rectangle);
      model.put("recid", "delete/"+rs.getString("id"));
      return "rectangle";
      }catch (Exception e){
        model.put("Message", e.getMessage());
      return "error";
      }
  }
  
  @PostMapping(
    path = "rectangle",
    consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE}
  )
  public String handleBrowserRectangleSaving(Map<String, Object> model, Rectangle rectangle) throws Exception {
      try (Connection connection = dataSource.getConnection()) {
        Statement stmt = connection.createStatement();
        String sql = "UPDATE rectangles SET name = '" + rectangle.getName() + "', height = '" + rectangle.getHeight() 
        + "', width = '" + rectangle.getWidth() + "', color = '" + rectangle.getBgcolor() + "' where Id = '"+rectangle.getId()+"'";
        String deletesql = "DELETE FROM rectanlges WHERE id = '"+rectangle.getId()+"'";
        stmt.executeUpdate(sql);
        return "redirect:/";
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

