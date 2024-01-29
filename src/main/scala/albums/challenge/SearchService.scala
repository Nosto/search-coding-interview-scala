package albums.challenge

import albums.challenge.models.{Entry, Facet, Results}
import org.springframework.stereotype.Service

@Service
class SearchService {
  def search(entries: List[Entry], query: String): Results =
    search(entries, query, List.empty, List.empty)

  def search(
              entries: List[Entry],
              query: String,
              year: List[String],
              price: List[String],
            ): Results = Results(
    if (query.isBlank) entries else filterByQuery(entries, query),
    Map(
      "year" -> List(Facet("2002", 25), Facet("2008", 2)),
      "price" -> List(Facet("5 - 10", 25), Facet("10 - 15", 2)),
    ),
    query,
  )

  private def tokenizeToWords(query: String): Set[String] = query.toLowerCase.split("\\W+").toSet

  private def filterByQuery(entries: List[Entry], query: String): List[Entry] = {
    val words = tokenizeToWords(query)
    entries.filter(entry => tokenizeToWords(entry.title).exists(words.contains))
  }
}
