package com.pluralsight;

import jakarta.json.bind.annotation.JsonbNumberFormat;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "T_BOOK")
@Schema(description = "Book resource representation")
public class Book {
    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 200)
    @NotNull
    private String title;

    @Column(length = 50)
    private String isbn;

    @Column(length = 10000)
    @Size(min = 10, max = 10000)
    private String description;

    @JsonbNumberFormat(locale = "en_US", value="$#0.00")
    @Min(1)
    private BigDecimal price;

    @JsonbProperty("publication-date")
    @Column(name = "publication_date")
    @Past
    private LocalDate publicationDate;

    @JsonbProperty("nb-of-pages")
    @Min(10)
    @Column(name = "nb_of_pages")
    private Integer nbOfPages;

    @JsonbProperty("image-url")
    @Column(name = "image_url")
    private String imageUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Integer getNbOfPages() {
        return nbOfPages;
    }

    public void setNbOfPages(Integer nbOfPages) {
        this.nbOfPages = nbOfPages;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
