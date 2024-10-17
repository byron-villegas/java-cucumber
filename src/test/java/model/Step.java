package model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Step {
    private String name;
    private String status;
    private String description;
    private String image;
}