package co.edu.unbosque.closedsea.dto;

import com.opencsv.bean.CsvBindByName;

public class Collection {

    @CsvBindByName
    private String username;

    @CsvBindByName
    private String collectionName;

    @CsvBindByName
    //private String nftAmount;
    private String collectionCategory;

    public Collection(String username, String collectionName, String collectionCategory) {
        this.username = username;
        this.collectionName = collectionName;
        this.collectionCategory = collectionCategory;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getCollectionCategory() {
        return collectionCategory;
    }

    public void setCollectionCategory(String collectionCategory) {
        this.collectionCategory = collectionCategory;
    }
}
