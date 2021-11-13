/* Copyright (C) Kite Tecnologia LTDA - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Volnei Munhoz <volnei.munhoz@gmail.com>, September 2021
 */
package gateway.filter;

import org.reactivestreams.Publisher;

import io.micronaut.core.async.publisher.Publishers;
import io.micronaut.core.util.StringUtils;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.client.ProxyHttpClient;
import io.micronaut.http.filter.HttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;
import lombok.RequiredArgsConstructor;

@Filter(Filter.MATCH_ALL_PATTERN)
@RequiredArgsConstructor
public class GatewayFilter implements HttpServerFilter {

        private final ProxyHttpClient client;

        @Override
        public Publisher<MutableHttpResponse<?>> doFilter(HttpRequest<?> request, ServerFilterChain chain) {
                return Publishers.map(
                                client.proxy(request.mutate().uri(b -> b.scheme("http").host("localhost").port(8080)
                                                .replacePath(StringUtils.prependUri("/",
                                                                request.getPath().substring("/api".length()))))
                                                .header("X-My-Request-Header", "XXX")),
                                response -> response.header("X-My-Response-Header", "YYY"));
        }
}