package co.edu.unbosque.closedsea.services;

import co.edu.unbosque.closedsea.dto.Collection;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CollectionService {

    public List<Collection> getLastCollections() throws IOException {
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
    }

}
