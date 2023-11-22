package albums.challenge.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Data(Feed feed) {
    public List<Entry> convert() {
        return feed.entry()
                .stream()
                .map(entry -> new Entry(
                        entry.title().label(),
                        Float.parseFloat(entry.price().label().substring(1)),
                        entry.releaseDate().label(),
                        entry.link().attributes().href(),
                        entry.images.get(0).label()
                ))
                .toList();
    }

    record Feed(List<Entry> entry) {
        record Entry(
                Label title,
                Link link,
                @JsonProperty("im:image") List<Label> images,
                @JsonProperty("im:price") Label price,
                @JsonProperty("im:releaseDate") Label releaseDate
        ) {
            record Label(String label) {
            }

            record Link(Attributes attributes) {
                record Attributes(String href) {
                }
            }
        }
    }
}
