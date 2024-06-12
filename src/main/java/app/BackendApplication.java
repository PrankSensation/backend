package app;

import app.models.FillDatabase;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner {

  @Autowired
  private FillDatabase fillDatabase;

  public static void main(String[] args) {
    Dotenv dotenv = Dotenv.configure().directory("./").load();
    dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
    SpringApplication.run(BackendApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("Yeeh");
//    fillDatabase.fill();
  }

}
