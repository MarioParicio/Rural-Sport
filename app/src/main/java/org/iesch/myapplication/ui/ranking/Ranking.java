package org.iesch.myapplication.ui.ranking;

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

    public String getSurname() {
        return surname;
    }

    public String getRanking() {
        return ranking;
    }

    public String getNationalityUrl() {
        return nationalityUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

}
