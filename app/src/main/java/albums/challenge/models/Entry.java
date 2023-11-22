package albums.challenge.models;

public record Entry(
        String title,
        Float price,
        String release_date,
        String link,
        String image
) {
}