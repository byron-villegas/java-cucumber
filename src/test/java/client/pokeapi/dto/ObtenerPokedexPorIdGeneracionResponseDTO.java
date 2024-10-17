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
public class ObtenerPokedexPorIdGeneracionResponseDTO {
    private int id;
    private String name;
    private List<PokemonEntryDTO> pokemon_entries;
}