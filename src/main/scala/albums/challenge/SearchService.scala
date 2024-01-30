package albums.challenge

import albums.challenge.models.{Entry, Results}
import org.springframework.stereotype.Service

@Service
class SearchService {
  def search(
      entries: List[Entry],
      query: String,
      year: List[String] = List.empty,
      price: List[String] = List.empty,
  ): Results = ???
}
