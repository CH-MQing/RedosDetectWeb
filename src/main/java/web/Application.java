package web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        String version = System.getProperty("java.version");
        if (!(version.startsWith("1.8") || version.startsWith("1.7") || version.startsWith("1.6"))) {
            System.out.println("Warning: The current Java version(" + version + ") is not support,please use Java(8)");
            System.exit(0);
        }
        SpringApplication.run(Application.class, args);
    }
}
