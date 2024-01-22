package albums.challenge.models;

case class Results(
    items: List[Entry],
    facets: Map[String, List[Facet]],
    query: String
)
