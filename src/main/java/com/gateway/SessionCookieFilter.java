package com.gateway;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;

@Component
public class SessionCookieFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Récupérer le cookie de session (JSESSIONID)
        HttpCookie sessionCookie = exchange.getRequest().getCookies().getFirst("JSESSIONID");

        if (sessionCookie != null) {
            // Ajouter le cookie à la requête sortante vers le frontend
            exchange.getRequest().mutate().headers(headers -> {
                headers.add(HttpHeaders.COOKIE, "JSESSIONID=" + sessionCookie.getValue());
            });
        }

        // Continuer le traitement de la requête
        return chain.filter(exchange);
    }
}
