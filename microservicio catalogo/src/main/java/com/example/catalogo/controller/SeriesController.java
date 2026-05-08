package com.example.catalogo.controller;

@RestController
@RequestMapping("/api/series") 
@RequiredArgsConstructor
public class SeriesController {
    private final SerieService serieService; //

    @PostMapping
    public ResponseEntity<Serie> registrar(@Valid @RequestBody SerieDTO dto) {
    //  el código 201 CREATED
        return ResponseEntity.status(HttpStatus.CREATED).body(serieService.registrarSerie(dto));
    }

    @GetMapping("/ejercicio/{id}")
    public ResponseEntity<List<Serie>> historial(@PathVariable Long id) {
        List<Serie> series = serieService.obtenerHistorialPorEjercicio(id);
        
        //  204 NO CONTENT si la lista está vacía
        if (series.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        // 200 OK
        return ResponseEntity.ok(series);
    }
}
