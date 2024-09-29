package com.example.demo.repositories;

import com.example.demo.connection.ConnectionFactory;
import com.example.demo.exceptions.DatabaseException;
import com.example.demo.exceptions.DatabaseIntegrityException;
import com.example.demo.model.Autor;
import com.example.demo.model.Livro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AutorRepository {
    Connection connection;

    public AutorRepository(){
        connection = ConnectionFactory.getConnection();
    }

    public Autor insert(Autor autor) {

        String sql = "INSERT INTO autor (name) " +
                "VALUES(?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, autor.getName());

            Integer rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                ResultSet id = statement.getGeneratedKeys();
                id.next();
                Integer idautor = id.getInt(1);

                System.out.println("Rows inserted: " + rowsInserted);
                System.out.println("Id: " + idautor);

                autor.setId(idautor);
            }

        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }

        return autor;
    }
    public List<Autor> getAll() {

        List<Autor> autors = new ArrayList<>();

        String sql = "SELECT * FROM autor";

        try {

            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {

                Autor autor = new Autor();
                autor.setId(result.getInt("idautor"));
                autor.setName(result.getString("name"));
                autors.add(autor);
            }


        }catch (SQLException e){
            throw new DatabaseException(e.getMessage());
        }

        return autors;

    }

    public void delete(Integer id){

        String sql = "DELETE FROM autor WHERE idAutor = ?";

        try {

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            Integer rowsDeleted = statement.executeUpdate();

            if(rowsDeleted > 0){
                System.out.println("Rows deleted: " + rowsDeleted);
            }

        } catch (Exception e){
            throw new DatabaseIntegrityException(e.getMessage());
        }

    }
}