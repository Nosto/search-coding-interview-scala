package albums.challenge

import albums.challenge.models.Results
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.web.bind.annotation.{GetMapping, RequestParam, RestController}
import org.springframework.web.servlet.ModelAndView

import scala.jdk.CollectionConverters.CollectionHasAsScala

@SpringBootApplication
@RestController
@EnableCaching
class Application @Autowired() (
    dataService: DataService,
    searchService: SearchService,
) {

  @GetMapping(Array("/"))
  def index(): ModelAndView = {
    val modelAndView = new ModelAndView()
    modelAndView.setViewName("index.html")
    modelAndView
  }

  @GetMapping(Array("/api/search"))
  def search(
      @RequestParam query: String,
      @RequestParam(defaultValue = "") year: java.util.List[String],
      @RequestParam(defaultValue = "") price: java.util.List[String],
  ): Results = {
    searchService.search(
      dataService.fetch(),
      query,
      year.asScala.toList,
      price.asScala.toList,
    )
  }
}

object Application extends App {
  SpringApplication.run(classOf[Application], args: _*)
}
