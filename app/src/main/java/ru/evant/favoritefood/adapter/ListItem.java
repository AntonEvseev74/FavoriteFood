package ru.evant.favoritefood.adapter;

/* Данные для таблицы my_table */

import java.io.Serializable;

public class ListItem implements Serializable {
    private int id = 0;
    private String title;
    private String recipe;
    private String description;
    private String uriImage = "empty";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUriImage() {
        return uriImage;
    }

    public void setUriImage(String uri_image) {
        this.uriImage = uri_image;
    }

}
