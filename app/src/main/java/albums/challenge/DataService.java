package albums.challenge;

import albums.challenge.models.Data;
import albums.challenge.models.Entry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class DataService {
    Logger logger = LogManager.getLogger(DataService.class);
    String uri = "https://itunes.apple.com/us/rss/topalbums/limit=200/json";

    @Cacheable("entry")
    List<Entry> fetch() {
        logger.info("Fetching data");

        var restTemplate = new RestTemplate();
        var converters = restTemplate.getMessageConverters();
        converters.forEach(converter -> {
            if (converter instanceof MappingJackson2HttpMessageConverter jsonConverter) {
                jsonConverter.setSupportedMediaTypes(Arrays.asList(
                        new MediaType("application", "json", Charset.defaultCharset()),
                        new MediaType("text", "javascript", Charset.defaultCharset())
                ));
            }
        });

        return Objects.requireNonNull(restTemplate.getForObject(uri, Data.class), "failed to fetch").convert();
    }
}
