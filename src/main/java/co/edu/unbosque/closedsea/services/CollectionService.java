package co.edu.unbosque.closedsea.services;

import co.edu.unbosque.closedsea.dto.Collection;
import co.edu.unbosque.closedsea.dto.User;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CollectionService {

    private static Connection conn;

    public CollectionService(Connection conn) {
        this.conn = conn;
    }

/*    public List<Collection> getLastCollections() throws IOException {
        List<Collection> collectionsList;
        List<Collection> response = new ArrayList<>();

        try(InputStream is = CollectionService.class.getClassLoader().getResourceAsStream("Collections.csv")){
            HeaderColumnNameMappingStrategy<Collection> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(Collection.class);

            try(BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))){
                CsvToBean<Collection> csvToBean = new CsvToBeanBuilder<Collection>(br).withType(Collection.class).withMappingStrategy(strategy).withIgnoreLeadingWhiteSpace(true).build();
                collectionsList = csvToBean.parse();
                Collections.reverse(collectionsList);

                //Para retornar los ulitmos 5 nfts
                int number = 5;
                if(collectionsList.size()<number){
                    number = collectionsList.size();
                }
                for(int i=0; i<number; i++){
                    response.add(collectionsList.get(i));
                }
            }
        }
        return response;
    }

    public List<Collection> getCollectionByUsername(String username) throws IOException{
        List<Collection> collectionList;

        try(InputStream is = CollectionService.class.getClassLoader().getResourceAsStream("Collections.csv")){

            HeaderColumnNameMappingStrategy<Collection> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(Collection.class);

            try(BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))){
                CsvToBean<Collection> csvToBean = new CsvToBeanBuilder<Collection>(br).withType(Collection.class).withMappingStrategy(strategy).withIgnoreLeadingWhiteSpace(true).build();
                collectionList = csvToBean.parse().stream().filter(collection -> collection.getUsername().equals(username)).collect(Collectors.toList());
            }

        }
        return collectionList;
    }

    public Collection createCollection(String username, String collectionName, String size, String path) throws IOException{
        String ln = username+" , "+collectionName+" , "+size+"\n";
        String fullpath = path + "WEB-INF"+ File.separator+"classes"+File.separator+"Collections.csv";

        FileOutputStream os = new FileOutputStream(fullpath, true);
            os.write(ln.getBytes());
            os.close();

        return new Collection(username,collectionName,size);
    }*/


    public static List<Collection> listCollections() {
        // Object for handling SQL statement
        Statement stmt = null;

        // Data structure to map results from database
        List<Collection> collections = new ArrayList<Collection>();

        try {
            // Executing a SQL query
            System.out.println("=> Lista de colecciones...");
            stmt = conn.createStatement();
            String sql = "SELECT collection_author, collection_name, collection_category  FROM collection";
            ResultSet rs = stmt.executeQuery(sql);

            // Reading data from result set row by row
            while (rs.next()) {
                // Extracting row values by column name

                String collection_author = rs.getString("collection_author");
                String collection_name = rs.getString("collection_name");
                String collection_category = rs.getString("collection_category");


                // Creating a new UserApp class instance and adding it to the array list
                collections.add(new Collection(collection_author, collection_name, collection_category));
            }

            // Closing resources
            rs.close();
            stmt.close();
        } catch (SQLException se) {
            se.printStackTrace(); // Handling errors from database
        } finally {
            // Cleaning-up environment
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return collections;
    }

    public Collection createCollection(Collection collection) throws SQLException {
        PreparedStatement stmt = null;

        if(collection != null){
            try{
                stmt = this.conn.prepareStatement("insert into collection (collection_name, collection_category, collection_author)\n" + "values(?,?,?)");

                stmt.setString(1,collection.getCollectionName());
                stmt.setString(2,collection.getCollectionCategory());
                stmt.setString(3,collection.getUsername());

                stmt.executeUpdate();
                stmt.close();

            } catch(SQLException se){
                se.printStackTrace();
            } finally{
                try {
                    if (stmt != null) stmt.close();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
            }
            return collection;
        }
        else {
            return null;
        }
    }

    public Collection getCollection(String username, String category, String collection){
        PreparedStatement stmt = null;
        Collection collectionobj = null;
        try{
            stmt = this.conn.prepareStatement("SELECT * FROM collection WHERE collection_name = ? and collection_category = ? and collection_author = ?");
            stmt.setString(1, collection);
            stmt.setString(2, category);
            stmt.setString(3, username);
            ResultSet rs = stmt.executeQuery();

            rs.next();

            collectionobj = new Collection(rs.getString("collection_author"), rs.getString("collection_name"), rs.getString("collection_category"));
            rs.close();
            stmt.close();

        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            try{
                if (stmt != null ){
                    stmt.close();
                }
            } catch (SQLException se){
                se.printStackTrace();
            }
        }
        return collectionobj;
    }

}
