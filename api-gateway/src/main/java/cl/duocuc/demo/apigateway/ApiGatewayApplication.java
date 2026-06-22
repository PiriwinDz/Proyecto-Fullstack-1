package cl.duocuc.demo.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ============================================================
 * ApiGatewayApplication
 * ============================================================
 *
 * Que hace el API Gateway?
 * Recibe TODAS las peticiones externas (Postman, frontend, etc.)
 * y las redirige al microservicio correcto segun la URL.
 *
 * Analogia: portero de edificio. El cliente no necesita saber
 * en que piso vive cada servicio; solo habla con el portero.
 *
 * Las rutas estan configuradas en application.properties.
 * Usa lb://nombre para resolver via Eureka (no IP fija).
 *
 * NOTA Spring Boot 4:
 * @EnableDiscoveryClient ya no es necesario. Con el starter
 * eureka-client en el classpath, el auto-registro es automatico.
 */
@SpringBootApplication
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
         System.out.println("================================================");
        System.out.println(" API Gateway iniciado correctamente");
        System.out.println(" URL: http://localhost:8090");
        System.out.println("------------------------------------------------");
        System.out.println(" /api/auth/** -> AUTENTICACION");
        System.out.println(" /api/catalogo/**  -> CATALOGO");
        System.out.println(" /api/pagos/** -> PAGOS");
        System.out.println(" /api/planes/** -> PLANES");
        System.out.println(" /api/sede/** -> SEDES");
        System.out.println(" /api/logro/** -> LOGROS");
        System.out.println(" /api/alerta/** -> ALERTAS");
        System.out.println(" /api/soporte/** -> SOPORTE");
        System.out.println(" /api/reseña/** -> RESEÑA");
        System.out.println("------------------------------------------------");
        System.out.println(" Eureka: http://localhost:8761");
        System.out.println("================================================");
    }

}
