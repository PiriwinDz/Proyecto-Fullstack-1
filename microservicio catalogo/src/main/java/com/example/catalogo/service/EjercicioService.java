
@Service
@RequiredArgsContructor // lombok genera el constructor 
public class EjercicioService {
    private final EjercicioRepository ejercicioRepository;

    public Ejercicio guardarEjercicio(EjercicioDTO dto){

        Ejercicio ejercicio = new Ejercicio();
        ejercicio.setNombre(dto.getNombre());
        ejercicio.setGrupoMuscular(dto.getGrupoMuscular());
        ejercicio.setDescripcion(dto.getDescripcion());

        return ejercicioRepository.save(ejercicio);
    }

    public List<Ejercicio> listarTodo() {
        return ejercicioRepository.findAll();
    }
}
