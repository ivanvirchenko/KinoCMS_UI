package com.avada.kino.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

import static com.avada.kino.util.UtilConstant.DATE_FUTURE_PRESENT;
import static com.avada.kino.util.UtilConstant.REQUIRED;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Promotion extends BasicEntity {

    private boolean enabled;

    @NotNull(message = REQUIRED)
    @FutureOrPresent(message = DATE_FUTURE_PRESENT)
    private LocalDate date;

    @Pattern(
            regexp = "^(|https?:\\/\\/[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&amp;:/~\\+#]*[\\w\\-\\@?^=%&amp;/~\\+#])?)$",
            message = "HTTP:// или HTTPS:// должны присутствовать"
    )
    private String videoLink;
}
