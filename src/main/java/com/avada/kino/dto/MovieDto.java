package com.avada.kino.dto;

import com.avada.kino.models.Image;
import com.avada.kino.models.Seo;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class MovieDto {
    private Integer id;
    private String name;
    private String description;
    private Image image;
    private List<Image> gallery;
    private Seo seo;
    private String videoLink;
    private List<Integer> typeIds;
    private LocalDate startDate;
    private LocalDate endDate;
}
