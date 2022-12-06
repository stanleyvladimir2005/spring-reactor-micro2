package com.mitocode.serviceImpl;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mitocode.dto.FiltroDTO;
import com.mitocode.model.Factura;
import com.mitocode.repo.IFacturaRepo;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.service.IFacturaService;
import reactor.core.publisher.Flux;
/*import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;*/
import reactor.core.publisher.Mono;

@Service
public class FacturaServiceImpl extends CRUDImpl<Factura, String> implements IFacturaService {
	
	@Autowired
	private IFacturaRepo repo;
	

	@Override
	protected IGenericRepo<Factura, String> getRepo() {
		return repo;
	}

	//Metodo para buscar facturas
	@Override
	public Flux<Factura> obtenerFacturasPorFiltro(FiltroDTO filtro) {
		String criterio = filtro.getIdCliente() != null ? "C" : "O";
		
		if (criterio.equalsIgnoreCase("C")) { //si la busqueda viene el cliente
			return repo.obtenerFacturasPorCliente(filtro.getIdCliente());
		} else { //si la busqueda viene por fecha de inicio y fin
			return repo.obtenerFacturasPorFecha(filtro.getFechaInicio(), filtro.getFechaFin());
		}
	}
	
	/*Metodo para generar el reporte cargando el jr
	@Override
	public Mono<byte[]> generarReporte(String idFactura) {
		return repo.findById(idFactura) //Mono<Factura>
					//Obteniendo Cliente
					.flatMap(f -> {
						return Mono.just(f)
								.zipWith(clienteRepo.findById(f.getCliente().getId()), (fa, cl) -> {
									fa.setCliente(cl);								
									return fa;
								});
					})
					//Obteniendo cada Plato
					.flatMap(f -> {						
						return Flux.fromIterable(f.getItems()).flatMap(it -> {					
							return platoRepo.findById(it.getPlato().getId())
									.map(p -> {
										it.setPlato(p);									
										return it;
									});						
						}).collectList().flatMap(list -> {	
							//Seteando la nueva lista a factura
							f.setItems(list);
							return Mono.just(f); //devolviendo factura para el siguiente operador (doOnNext)
							});						
					})
					.map(f -> {
						InputStream stream;
						try {									
							Map<String, Object> parametros = new HashMap<String, Object>();		//obteniendo los parametros de entrada del reporte				
							parametros.put("txt_cliente", f.getCliente().getNombres() + " " + f.getCliente().getApellidos()); //llenamos el parametro de cliente del reporte
							stream = getClass().getResourceAsStream("/facturas.jrxml"); //obteniendo reporte y compilando en memoria
							JasperReport report = JasperCompileManager.compileReport(stream); //compilamos reporte
							JasperPrint print = JasperFillManager.fillReport(report, parametros, new JRBeanCollectionDataSource(f.getItems())); //Pasando parametros al reporte
							return JasperExportManager.exportReportToPdf(print); //exportando reporte a pdf
						} catch (Exception e) {
							e.printStackTrace();
						}
						return new byte[0];					
					});
	}*/
}