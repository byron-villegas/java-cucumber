package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private String sku;
    private String name;
    private String description;
    private String author;
    private String publisher;
    private Short yearCreated;
    private String country;
    private List<String> images;
    private List<String> categories;
    private List<Track> trackList;
    private boolean favorite;
    private String type;
    private boolean enabled;
}
