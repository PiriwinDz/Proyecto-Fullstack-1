package cl.duocuc.demo.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
