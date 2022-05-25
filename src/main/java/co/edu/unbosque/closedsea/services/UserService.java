package co.edu.unbosque.closedsea.services;

import co.edu.unbosque.closedsea.dto.Nft;
import co.edu.unbosque.closedsea.dto.User;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserService {

    private static Connection conn;

    public UserService(Connection conn) {
        this.conn = conn;
    }

 /* public static List<User> getUsers() throws IOException {
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
    }*/

    public static List<User> getUsers() throws IOException {
        // Object for handling SQL statement
        Statement stmt = null;

        // Data structure to map results from database
        List<User> users = new ArrayList<User>();

        try {
            // Executing a SQL query
            System.out.println("=> Lista de usuarios...");
            stmt = conn.createStatement();
            //String sql = "SELECT * FROM user_table";
            String sql = "SELECT user_name, user_password, user_role, user_fcoins FROM user_table";
            ResultSet rs = stmt.executeQuery(sql);

            // Reading data from result set row by row
            while (rs.next()) {
                // Extracting row values by column name

                //String id_user = rs.getString("id_user");
                String user_name = rs.getString("user_name");
                String user_password = rs.getString("user_password");
                //String user_email = rs.getString("user_email");
                String user_role = rs.getString("user_role");
                String user_fcoins = rs.getString("user_fcoins");

                // Creating a new UserApp class instance and adding it to the array list
                users.add(new User(user_name, user_password, user_role, user_fcoins));
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

    public List<User> listUsers() {
        // Object for handling SQL statement
        Statement stmt = null;

        // Data structure to map results from database
        List<User> users = new ArrayList<User>();

        try {
            // Executing a SQL query
            System.out.println("=> Lista de usuarios...");
            stmt = conn.createStatement();
            //String sql = "SELECT * FROM user_table";
            String sql = "SELECT user_name, user_password, user_role, user_fcoins FROM user_table";
            ResultSet rs = stmt.executeQuery(sql);

            // Reading data from result set row by row
            while (rs.next()) {
                // Extracting row values by column name
                //String id_user = rs.getString("id_user");
                String user_name = rs.getString("user_name");
                String user_password = rs.getString("user_password");
                //String user_email = rs.getString("user_email");
                String user_role = rs.getString("user_role");
                String user_fcoins = rs.getString("user_fcoins");

                // Creating a new UserApp class instance and adding it to the array list
                users.add(new User(user_name, user_password, user_role, user_fcoins));
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
        return users;
    }

    public User newUser(User user) {
        // Object for handling SQL statement
        PreparedStatement stmt = null;

        // Data structure to map results from database
        if (user != null) {

            try {

                if (user.getRole().equals("artista")) {
                    stmt = this.conn.prepareStatement("insert into user_table (username, user_password, user_role, user_fcois ) \n" +
                            "VALUES (?,?,'artista','0')");
                } else if (user.getRole().equals("comprador")) {
                    stmt = this.conn.prepareStatement("insert into user_table (username, user_password, user_role, user_fcois )\n" +
                            "VALUES (?,?,'comprador','0')");
                }
                stmt.setString(1, user.getUsername());
                stmt.setString(2, user.getPassword());


                stmt.executeUpdate();
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
            return user;
        } else {
            return null;
        }
    }
}
