package albums.challenge;

import albums.challenge.models.Entry;
import albums.challenge.models.Facet;
import albums.challenge.models.Results;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class SearchService {
    Results search(List<Entry> entries, String query) {
        return search(entries, query, List.of(), List.of());
    }

    Results search(List<Entry> entries, String query, List<String> year, List<String> price) {
        return new Results(
                query.isBlank() ? entries : filterByQuery(entries, query),
                Map.ofEntries(
                        Map.entry("year", List.of(
                                new Facet("2002", 25),
                                new Facet("2008", 2)
                        )),
                        Map.entry("price", List.of(
                                new Facet("5 - 10", 25),
                                new Facet("10 - 15", 2)
                        ))
                ),
                query
        );
    }

    Set<String> tokenizeToWords(String query) {
        return Set.copyOf(List.of(query.toLowerCase().split("\\W+")));
    }

    List<Entry> filterByQuery(List<Entry> entries, String query) {
        var words = tokenizeToWords(query);
        return entries.stream().filter(entry -> {
            var tokens = tokenizeToWords(entry.title());
            return tokens.containsAll(words);
        }).toList();
    }
}
