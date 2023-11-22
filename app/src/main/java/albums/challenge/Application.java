package albums.challenge;

import albums.challenge.models.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@SpringBootApplication
@RestController
@EnableCaching
public class Application {
    @Autowired
    DataService dataService;

    @Autowired
    SearchService searchService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping("/")
    public ModelAndView index() {
        var modelAndView = new ModelAndView();
        modelAndView.setViewName("index.html");
        return modelAndView;
    }

    @GetMapping("/api/search")
    public Results search(
            @RequestParam String query,
            @RequestParam(defaultValue = "") List<String> year,
            @RequestParam(defaultValue = "") List<String> price
    ) {
        return searchService.search(
                dataService.fetch(),
                query,
                year,
                price
        );
    }
}
