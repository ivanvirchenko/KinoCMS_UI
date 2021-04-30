package com.avada.kino.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.avada.kino.util.UtilConstant.MAX_SIZE;
import static com.avada.kino.util.UtilConstant.REQUIRED;

@Data
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class BasicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = REQUIRED)
    @Size(max = 255, message = MAX_SIZE + 255)
    private String name;

    @Column(length = 5000)
    @NotBlank(message = REQUIRED)
    private String description;

    @Embedded
    @AttributeOverrides(
            {
                    @AttributeOverride(name = "name", column = @Column(name = "logo_name")),
                    @AttributeOverride(name = "url", column = @Column(name = "logo_url"))
            }
    )
    private Image logo;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Image> gallery;

    @Valid
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "seo_id")
    private Seo seo;

    public void addToGallery(Image image) {
        if (this.gallery == null) {
            this.gallery = new HashSet<>();
        }
        gallery.add(image);
    }
}
