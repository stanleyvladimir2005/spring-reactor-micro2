package com.mitocode.service;

import com.mitocode.dto.FiltroDTO;
import com.mitocode.model.Factura;
import reactor.core.publisher.Flux;

public interface IFacturaService extends ICRUD<Factura, String> {
	
	Flux<Factura> obtenerFacturasPorFiltro(FiltroDTO filtro);
	
	//Mono<byte[]> generarReporte(String idFactura);
}