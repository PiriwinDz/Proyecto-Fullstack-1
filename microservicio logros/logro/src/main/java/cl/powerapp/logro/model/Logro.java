package cl.powerapp.logro.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Logro {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="El nombre no puede estar vacio")
    @Column(nullable= false)
    private String nombre;

    @NotBlank(message="La descripcion no puede estar vacia")
    private String descripcion;

    @Min(0)
    private Integer puntos;

    
}
