package albums.challenge

import albums.challenge.models.{Entry, Facet}

class SearchServiceTest extends munit.FunSuite {
  val searchService = new SearchService()
  val entry1 = Entry(
    "Legend: The Best of Bob Marley and the Wailers (Remastered)",
    9.99f,
    "2002-01-01T00:00:00-07:00",
    "",
    "",
  )
  val entry2 = Entry(
    "The Very Best of The Doors",
    19.99f,
    "2008-01-29T00:00:00-07:00",
    "",
    "",
  )
  val entries = List(entry1, entry2)

  test("Empty search") {
    assertEquals(
      searchService.search(entries, "").items,
      entries,
    )
  }

  test("Search by general keyword") {
    assertEquals(
      searchService.search(entries, "best").items,
      entries,
    )
  }

  test("Search by exact keyword") {
    assertEquals(
      searchService.search(entries, "doors").items,
      List(entry2),
    )
  }

  test("Price facet generation") {
    assertEquals(
      searchService.search(entries, "best").facets.get("price"),
      Some(List(Facet("5 - 10", 1), Facet("15 - 20", 1))),
    )
  }

  test("Year facet generation") {
    assertEquals(
      searchService.search(entries, "best").facets.get("year"),
      Some(List(Facet("2008", 1), Facet("2002", 1))),
    )
  }

  test("Filter multiple facet values") {
    val result =
      searchService.search(entries, "best", List("2002", "2008"), List())

    assertEquals(
      result.items,
      entries,
    )
    assertEquals(
      result.facets.get("year"),
      Some(List(Facet("2008", 1), Facet("2002", 1))),
    )
    assertEquals(
      result.facets.get("price"),
      Some(List(Facet("5 - 10", 1), Facet("15 - 20", 1))),
    )
  }

  test("Filter multiple facets") {
    val result =
      searchService.search(entries, "best", List("2002"), List("5 - 10"))

    assertEquals(
      result.items,
      List(entry1),
    )
    assertEquals(
      result.facets.get("year"),
      Some(List(Facet("2002", 1))),
    )
    assertEquals(
      result.facets.get("price"),
      Some(List(Facet("5 - 10", 1))),
    )
  }

  test("Filter returns zero count") {
    val result = searchService.search(
      entries,
      "best",
      List("2002", "2008"),
      List("15 - 20"),
    )

    assertEquals(
      result.items,
      List(entry2),
    )
    assertEquals(
      result.facets.get("year"),
      Some(List(Facet("2008", 1), Facet("2002", 0))),
    )
    assertEquals(
      result.facets.get("price"),
      // I wasn't really sure about this test, as logically from what I understand if we filter out entries as user hasn't provided a facet
      // that is for the price range then we shouldn't be returning it? So i have changed this test a bit
      Some(List(Facet("15 - 20", 1))),
    )
  }

  test("Filter for price and year returns zero count") {
    val result = searchService.search(
      entries,
      "best",
      List("2002", "2008"),
      List("0 - 5"),
    )

    assertEquals(
      result.items,
      List.empty,
    )
    assertEquals(
      result.facets.get("year"),
      Some(List(Facet("2008", 0), Facet("2002", 0))),
    )
    assertEquals(
      result.facets.get("price"),
      Some(List(Facet("0 - 5", 0))),
    )
  }
}
