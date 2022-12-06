package com.mitocode.controller;

import java.time.Duration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mitocode.model.Plato;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/backpressure")
public class BackPressureController {
	
	@GetMapping(path = "/stream", produces = MediaType.APPLICATION_JSON_VALUE)	
	public Flux<Plato> stream(){
		return Flux.interval(Duration.ofMillis(100))
				.map(t -> new Plato("1", "ARROZ", 20.0, true));		
	}
	
	@GetMapping(path = "/noStream", produces = "application/stream+json")	
	public Flux<Plato> noStream(){
		return Flux.interval(Duration.ofMillis(100))
				.map(t -> new Plato("1", "ARROZ", 20.0, true));						
	}
	
	@GetMapping(path = "/noStreamFinito", produces = MediaType.APPLICATION_JSON_VALUE)
	public Flux<Plato> fluxFinitonoStream() {
	    return Flux.range(0, 5000)
	             .map(t -> new Plato("1", "ARROZ", 20.0, true));
	}
	
	@GetMapping(path = "/streamFinito", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Plato> fluxFinitoStream() {
        return Flux.range(0, 5000)
                .map(t -> new Plato("1", "ARROZ", 20.0, true));
    }
	
	//JSON
	//[{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}] JSON.parse

	//STREAM+JSON
	//{}{}{}{}{}{}{}{}

	@GetMapping(value= "/buffer")
	public Flux<Integer> testContrapresion() {
		return Flux.range(1, 100)  
				.log()
				.limitRate(10, 2)
				//.limitRate(10) //75%
				//.limitRate(10, 0)
				.delayElements(Duration.ofMillis(1));
	}
}