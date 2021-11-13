package account;

import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import jakarta.inject.Singleton;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

@Singleton // <1>
public class AuthenticationProviderUserPassword implements AuthenticationProvider { // <2>

    @Override
    public org.reactivestreams.Publisher<AuthenticationResponse> authenticate(
            io.micronaut.http.HttpRequest<?> httpRequest, AuthenticationRequest<?, ?> authenticationRequest) {
        return Flux.create(emitter -> {
            if (authenticationRequest.getIdentity().equals("sherlock")
                    && authenticationRequest.getSecret().equals("password")) {
                emitter.next(AuthenticationResponse.success((String) authenticationRequest.getIdentity()));
                emitter.complete();
            } else {
                emitter.error(AuthenticationResponse.exception());
            }
        }, FluxSink.OverflowStrategy.ERROR);
    }
}
