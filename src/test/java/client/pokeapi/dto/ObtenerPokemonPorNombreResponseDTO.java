package client.pokeapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class ObtenerPokemonPorNombreResponseDTO {
    private int id;
    private String name;
    private int order;
    private List<NameDTO> names;
    private GenericDTO generation;
}
