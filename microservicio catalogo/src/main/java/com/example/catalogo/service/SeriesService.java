@Service
@RequiredArgsConstructor
public class SeriesService {
    private final SeriesRepository seriesRepository;

    public Series registrarSerie(SeriesDTO dto){
        Series series = new Series();

        //pasalos los datos del DTO a la entidad
        serie.setEjercicioId(dto.getEjercicioId());
        serie.setPeso(dto.GetPeso());
        serie.setRepeticiones(dto.Repeticiones());

        // JPA hace el @Prepersist que definimos en el modelo para calcular el RM y poner la fecha/hora automatico
        return SeriesRepository.save(series);
    }

    public List<Series> obtenerHistorialPorEjercicio(Long ejercicioId){
        // Reutilizamos el metodo creado en repository
        return seriesRepository.findByEjercicioId(ejercicioId);
    }

    public List<Series> listarTodasLasSeries() {
        return seriesRepository.findAll();
    }

}
