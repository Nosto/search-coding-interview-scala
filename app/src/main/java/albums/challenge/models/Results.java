package albums.challenge.models;

import java.util.List;
import java.util.Map;

public record Results(
        List<Entry> items,
        Map<String, List<Facet>> facets,
        String query
) {
}
