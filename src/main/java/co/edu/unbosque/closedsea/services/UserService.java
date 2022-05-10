package co.edu.unbosque.closedsea.services;

import co.edu.unbosque.closedsea.dto.Nft;
import co.edu.unbosque.closedsea.dto.User;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

public class UserService {

    public static List<User> getUsers() throws IOException {
        List<User> users;
        try (var inputStream = UserService.class.getClassLoader().getResourceAsStream("users.csv")) {
            HeaderColumnNameMappingStrategy<User> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(User.class);

            assert inputStream != null;
            try (var bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                CsvToBean<User> csvToBean = new CsvToBeanBuilder<User>(bufferedReader).withType(User.class).withMappingStrategy(strategy).withIgnoreLeadingWhiteSpace(true).build();
                users = csvToBean.parse();
            }
        }
        return users;
    }

    public static User createUser(String username, String password, String role, String path) throws IOException {
        String newLine = "\n" + username + "," + password + "," + role + ",0";
        var outputStream = new FileOutputStream(path + "WEB-INF/classes/" + "users.csv", true);
        outputStream.write(newLine.getBytes());
        outputStream.close();
        return new User(username, password, role, "0");
    }

    public static Optional<List<Nft>> getUserNfts() throws IOException {
        List<Nft> nfts;
        try (InputStream inputStream = UserService.class.getClassLoader().getResourceAsStream("pieces.csv")) {
            if (inputStream == null) {
                return Optional.empty();
            }
            HeaderColumnNameMappingStrategy<Nft> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(Nft.class);
            //Buffered reader try catch
            try (BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                CsvToBean<Nft> csvToBean = new CsvToBeanBuilder<Nft>(bf).withType(Nft.class).withMappingStrategy(strategy).withIgnoreLeadingWhiteSpace(true).build();
                nfts = csvToBean.parse();
            }
        }
        return Optional.of(nfts);
    }
}