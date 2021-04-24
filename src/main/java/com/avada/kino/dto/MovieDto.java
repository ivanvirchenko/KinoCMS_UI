package com.avada.kino.dto;

import com.avada.kino.models.Image;
import com.avada.kino.models.Seo;
import com.avada.kino.util.StringsConstant;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;

import static com.avada.kino.util.StringsConstant.*;

@Data
public class MovieDto {
    private Integer id;
    @NotEmpty(message = REQUIRED)
    private String name;
    @NotEmpty(message = REQUIRED)
    private String description;
    private Image image;
    private List<Image> gallery;
    @Valid
    private Seo seo;
    private String videoLink;
    @NotEmpty(message = MOVIE_TYPES)
    private List<Integer> typeIds;
    @FutureOrPresent(message = DATE_FUTURE_PRESENT)
    private LocalDate startDate;
    @Future(message = DATE_FUTURE)
    private LocalDate endDate;
}
