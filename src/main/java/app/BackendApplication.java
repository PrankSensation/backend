package app;

import app.models.FillDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner {

  @Autowired
  private FillDatabase fillDatabase;

  public static void main(String[] args) {
    SpringApplication.run(BackendApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("Yeeh");
//    fillDatabase.fill();
  }

}
