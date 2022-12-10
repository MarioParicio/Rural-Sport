package org.iesch.myapplication.ui.gallery;

import com.google.gson.annotations.SerializedName;

public class Ranking {

    private String name;
    private String surname;
    private String ranking;
    @SerializedName("nationality")
    private String nationalityUrl;
    private String imageUrl;

    public Ranking(String name, String surname, String ranking, String nationalityUrl, String imageUrl) {

        this.name = name;
        this.surname = surname;
        this.ranking = ranking;
        this.nationalityUrl = nationalityUrl;
        this.imageUrl = imageUrl;
    }

    public Ranking() {
    }

    @Override
    public String toString() {
        return "Ranking{" +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", ranking='" + ranking + '\'' +
                ", nationality='" + nationalityUrl + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public String getNationalityUrl() {
        return nationalityUrl;
    }

    public void setNationalityUrl(String nationalityUrl) {
        this.nationalityUrl = nationalityUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
