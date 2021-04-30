package com.avada.kino.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.avada.kino.util.UtilConstant.REQUIRED;
import static javax.persistence.CascadeType.MERGE;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Movie extends BasicEntity{
    @Pattern(
            regexp = "^(|https?:\\/\\/[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&amp;:/~\\+#]*[\\w\\-\\@?^=%&amp;/~\\+#])?)$",
            message = "HTTP:// или HTTPS:// должны присутствовать"
    )
    private String videoLink;

    @ManyToMany(cascade = {MERGE})
    @JoinTable(
            name = "movies_to_types",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "type_id")
    )
    private List<MovieType> types = new ArrayList<>();

    @NotNull(message = REQUIRED)
    @FutureOrPresent(message = "Фильм невозможно показывать в прошлом")
    private LocalDate startDate;

    @NotNull(message = REQUIRED)
    @Future(message = "Дата не может быть меньше даты старта")
    private LocalDate endDate;

    public void addType(MovieType type) {
        if (this.types == null) {
            this.types = new ArrayList<>();
        }
        types.add(type);
    }
}
