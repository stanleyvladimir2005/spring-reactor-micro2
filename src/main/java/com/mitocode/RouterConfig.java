package com.mitocode;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import com.mitocode.handler.BillHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {

	@Bean
	public RouterFunction<ServerResponse> rutasFacturas(BillHandler handler) {
		return route(GET("/v2/bills"), handler::findAll)
				.andRoute(GET("/v2/bills/{id}"), handler::findById)
				.andRoute(POST("/v2/bills"), handler::save)
				.andRoute(PUT("/v2/bills/{id}"), handler::update)
				.andRoute(DELETE("/v2/bills/{id}"), handler::delete);
	}
}