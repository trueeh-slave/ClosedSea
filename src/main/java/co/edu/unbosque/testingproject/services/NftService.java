package co.edu.unbosque.testingproject.services;

import co.edu.unbosque.testingproject.dto.Nft;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;

public class NftService {

    public static List<Nft> getPieces() throws IOException {
        List<Nft> pieces;
        try (var inputStream = NftService.class.getClassLoader().getResourceAsStream("pieces.csv")) {
            HeaderColumnNameMappingStrategy<Nft> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(Nft.class);

            try (var bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                CsvToBean<Nft> csvToBean = new CsvToBeanBuilder<Nft>(bufferedReader).withType(Nft.class).withMappingStrategy(strategy).withIgnoreLeadingWhiteSpace(true).build();
                pieces = csvToBean.parse();
            }
        }
        return pieces;
    }

    public static Nft createPiece(String title, String author, String coins, String pathName, String path) throws IOException {
        String newLine = "\n" + title + "," + author + "," + coins + "," + pathName;
        var outputStream = new FileOutputStream(path + "WEB-INF/classes/" + "pieces.csv", true);
        outputStream.write(newLine.getBytes());
        outputStream.close();
        return new Nft(title, author, coins, pathName);
    }

    public static String generateRandomString(int length) {
        var text = new StringBuilder();
        var random = new Random();
        for (int i = 0; i < length; i++) {
            char character = (char) ('a' + random.nextInt(26));
            text.append(character);
        }
        return text.toString();
    }
}