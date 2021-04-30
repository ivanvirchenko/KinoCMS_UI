package com.avada.kino.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.avada.kino.util.UtilConstant.*;

@Data
@Entity
@NoArgsConstructor
public class Seo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Pattern(
            regexp = "^(|https?:\\/\\/[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&amp;:/~\\+#]*[\\w\\-\\@?^=%&amp;/~\\+#])?)$",
            message = "HTTP:// или HTTPS:// должны присутствовать"
    )
    @NotEmpty(message = REQUIRED)
    @Size(max = 255, message = MAX_SIZE + 255)
    private String url;

    @NotEmpty(message = REQUIRED)
    @Size(max = 255, message = MAX_SIZE + 255)
    private String title;

    @NotEmpty(message = REQUIRED)
    @Size(max = 255, message = MAX_SIZE + 255)
    private String keyWords;

    @Column(length = MAX_SIZE_4000)
    @NotEmpty(message = REQUIRED)
    @Size(max = MAX_SIZE_4000, message = MAX_SIZE + MAX_SIZE_4000)
    private String description;

    public Seo(String url, String title, String keyWords, String description) {
        this.url = url;
        this.title = title;
        this.keyWords = keyWords;
        this.description = description;
    }
}
