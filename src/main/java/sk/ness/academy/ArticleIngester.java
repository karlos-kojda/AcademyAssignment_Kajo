package sk.ness.academy;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import sk.ness.academy.config.DatabaseConfig;
import sk.ness.academy.domain.Article;
import sk.ness.academy.service.ArticleService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "sk.ness.academy", excludeFilters = {
    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = AssignmentApplication.class) })
@Import(DatabaseConfig.class)
public class ArticleIngester {

  public static void main(final String[] args) {
    ObjectMapper mapper = new ObjectMapper();
    try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ArticleIngester.class)) {
      context.registerShutdownHook();
      final ArticleService articleService = context.getBean(ArticleService.class);

      File jsonInputFile = new File("C:\\Users\\P3503408\\Documents\\GitHub\\AcademyAssignment\\articles_to_ingest.txt");
      Article article = mapper.readValue(jsonInputFile, Article.class);
      System.out.println(article);
      // Load file with articles and ingest

      articleService.ingestArticles(null);
    } catch (IOException e) {
    }
  }
}
