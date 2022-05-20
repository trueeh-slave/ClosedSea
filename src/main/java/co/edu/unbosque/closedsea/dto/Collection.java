package co.edu.unbosque.closedsea.dto;

import com.opencsv.bean.CsvBindByName;

public class Collection {

    @CsvBindByName
    private String username;

    @CsvBindByName
    private String collectionName;

    @CsvBindByName
    private String nftAmount;

    public Collection(String username, String collectionName, String nftAmount) {
        this.username = username;
        this.collectionName = collectionName;
        this.nftAmount = nftAmount;
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

    public String getNftAmount() {
        return nftAmount;
    }

    public void setNftAmount(String nftAmount) {
        this.nftAmount = nftAmount;
    }
}
