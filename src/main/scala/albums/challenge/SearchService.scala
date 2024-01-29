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
            ): Results = {
    var filteredEntries = if (query.isBlank) entries else filterByQuery(entries, query)
    filteredEntries = if (year.isEmpty) filteredEntries else filterByYears(filteredEntries, year)
    filteredEntries = if (price.isEmpty) filteredEntries else filterByPrice(filteredEntries, price)

    Results(
      filteredEntries,
      createFacetsForEntries(filteredEntries, year, price),
      query,
    )
  }

  private def createPriceRangeString(price: Float): String = {
    val lowerBound = (price / 5).toInt * 5
    val upperBound = lowerBound + 5
    s"$lowerBound - $upperBound"
  }

  private def updateFacadeList(matchingValue: String, facetList: List[Facet]): List[Facet] = {
    var listToUse = facetList
    listToUse.find(y => y.value == matchingValue) match {
      case Some(foundObject) =>
        val updatedList = listToUse.map(obj => if (obj == foundObject) foundObject.copy(value = foundObject.value, count = foundObject.count + 1) else obj)
        listToUse = updatedList
      case None =>
        listToUse = listToUse :+ Facet(matchingValue, 1)
    }
    listToUse
  }

  private def createFacetsForEntries(entries: List[Entry], year: List[String], price: List[String]): Map[String, List[Facet]] = {
    var yearList = List.empty[Facet]
    var priceList = List.empty[Facet]
    if (year.nonEmpty) {
      year.foreach(y => {
        yearList = yearList :+ Facet(y, 0)
      })
    }
    if (price.nonEmpty) {
      price.foreach(p => {
        priceList = priceList :+ Facet(p, 0)
      })
    }
    entries.foreach(item => {
      val albumYear = item.releaseDate.take(4)
      val priceRange = createPriceRangeString(item.price)
      yearList = updateFacadeList(albumYear, yearList)
      priceList = updateFacadeList(priceRange, priceList)
    })
    Map(
      "year" -> yearList.sortBy(_.value).reverse,
      "price" -> priceList
    )
  }

  private def tokenizeToWords(query: String): Set[String] = query.toLowerCase.split("\\W+").toSet

  private def filterByQuery(entries: List[Entry], query: String): List[Entry] = {
    val words = tokenizeToWords(query)
    entries.filter(entry => tokenizeToWords(entry.title).exists(words.contains))
  }

  private def filterByYears(entries: List[Entry], years: List[String]): List[Entry] = {
    entries.filter(entry => years.contains(entry.releaseDate.take(4)))
  }

  private def filterByPrice(entries: List[Entry], price: List[String]): List[Entry] = {
    entries.filter(entry => price.exists(p => {
      val splitArray = p.split(" - ")
      if (splitArray.length == 2) {
        entry.price >= splitArray(0).toFloat && entry.price <= splitArray(1).toFloat
      } else {
        false
      }
    }))
  }
}
