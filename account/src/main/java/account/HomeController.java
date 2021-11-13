package account;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import reactor.core.publisher.Mono;

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller
public class HomeController {

    @Get(uri = "/")
    public Mono<String> index() {
        return Mono.just("Request ok!");
    }
}