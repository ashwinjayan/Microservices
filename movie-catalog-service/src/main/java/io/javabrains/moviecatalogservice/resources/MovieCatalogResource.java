package io.javabrains.moviecatalogservice.resources;

import com.netflix.discovery.DiscoveryClient;
import io.javabrains.moviecatalogservice.models.CatalogItem;
import io.javabrains.moviecatalogservice.models.Movie;
import io.javabrains.moviecatalogservice.models.Rating;
import io.javabrains.moviecatalogservice.models.UserRating;
import io.javabrains.moviecatalogservice.services.CatalogItemService;
import io.javabrains.moviecatalogservice.services.UserRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@EnableCircuitBreaker
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private UserRatingService userRatingService;

    @Autowired
    private CatalogItemService catalogItemService;

    @RequestMapping("/{user}")
    public List<CatalogItem> getCatalog(@PathVariable("user") String userId) {
        UserRating ratings = userRatingService.getUserRating(userId);

        return ratings.getUserRatings().stream().map(rating -> catalogItemService.getCatalogItem(rating))
                .collect(Collectors.toList());
    }

}

//Using webclient instead of RestTemplate

/*@Autowired
    private WebClient.Builder webClientBuilder;*/

/*Movie movie = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8082/movies/" + rating.getMovieId())
                    .retrieve()
                    .bodyToMono(Movie.class)
                    .block();*/

    /*@Autowired
    private DiscoveryClient discoveryClient;*/ // To handle load balancing in a way other than using @LoadBalanced annotation