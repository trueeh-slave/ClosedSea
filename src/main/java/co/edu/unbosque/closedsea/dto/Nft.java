package co.edu.unbosque.closedsea.dto;

import com.opencsv.bean.CsvBindByName;
import jakarta.servlet.ServletContext;

public class Nft {
    @CsvBindByName
    private String title;

    @CsvBindByName
    private String author;

    @CsvBindByName
    private String coins;

    @CsvBindByName
    private String path;

    @CsvBindByName
    private String collectionName;

    public Nft(String title, String author, String coins, String path) {
        this.title = title;
        this.author = author;
        this.coins = coins;
        this.path = path;
    }

    public Nft(String title, String author, int price, String collection, ServletContext context) {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCoins() {
        return coins;
    }

    public void setCoins(String coins) {
        this.coins = coins;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Title: " + title + ", Author: " + author + ", Coins: " + coins + ", Path: " + path;
    }
}
