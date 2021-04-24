package com.avada.kino.models;

import com.avada.kino.util.StringsConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static com.avada.kino.util.StringsConstant.DATE_FUTURE_PRESENT;
import static com.avada.kino.util.StringsConstant.REQUIRED;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class News extends BasicEntity {
    private boolean enabled;

    @NotNull(message = REQUIRED)
    @FutureOrPresent(message = DATE_FUTURE_PRESENT)
    private LocalDate date;
    private String videoLink;
}
