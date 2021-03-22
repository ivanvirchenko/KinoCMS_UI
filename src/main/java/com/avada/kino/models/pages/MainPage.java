package com.avada.kino.models.pages;

import com.avada.kino.models.Phone;
import com.avada.kino.models.Seo;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class MainPage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ElementCollection
    private List<Phone> phones;
    private String banner_url;
    private String seoTest;
    private String commercial_url; //Temporal. May be i'll use Commercial class...
    @ManyToOne
    @JoinColumn(name = "seo_id")
    private Seo seo;

    public MainPage(List<Phone> phones, String banner_url, String seoTest, String commercial_url, Seo seo) {
        this.phones = phones;
        this.banner_url = banner_url;
        this.seoTest = seoTest;
        this.commercial_url = commercial_url;
        this.seo = seo;
    }
}
