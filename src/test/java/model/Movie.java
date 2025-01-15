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
public class Movie {
    private String title;
    private String originalTitle;
    private String sypnosis;
    private String image;
    private int year;
    private int duration;
    private String durationType;
    private List<String> genres;
    private List<String> languages;
    private int resolutionWidth;
    private int resolutionHeight;
    private double size;
    private String sizeType;
    private String format;
}
