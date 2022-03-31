package sk.ness.academy;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import sk.ness.academy.config.DatabaseConfig;
import sk.ness.academy.service.ArticleService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "sk.ness.academy", excludeFilters = {
    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = AssignmentApplication.class) })
@Import(DatabaseConfig.class)
public class ArticleIngester {

  public static void main(final String[] args) {

    StringBuilder sb = new StringBuilder();

    try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ArticleIngester.class)) {
      final ArticleService articleService = context.getBean(ArticleService.class);
      Stream<String> stream = Files.lines(Paths.get("articles_to_ingest.txt"));
      stream.forEach(s -> sb.append(s).append("\n"));
      articleService.ingestArticles(sb.toString());
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("Text file error");
    }
  }
}
