package com.example.demo.repositories;

import com.example.demo.connection.ConnectionFactory;
import com.example.demo.exceptions.DatabaseException;
import com.example.demo.model.Autor;
import com.example.demo.model.Livro;
import com.example.demo.model.StatusLivro;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LivroRepository {

    private Connection connection;

    public LivroRepository() {
        connection = ConnectionFactory.getConnection();
    }

    public List<Livro> getLivros() {
        List<Livro> livros = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT livro.*, autor.name AS AutorName " +
                    "FROM livro " +
                    "JOIN autor " +
                    "ON livro.idautor = autor.idautor ");

            while (result.next()) {

                Autor autor = instantiateAutor(result);
                Livro livro = instantiateLivro(result, autor);

                livros.add(livro);
            }

            result.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionFactory.closeConnection();
        }
        return livros;
    }

    public Livro insert(Livro livro) {

        String sql = "INSERT INTO livro (name, date, status, idautor) " +
                "VALUES(?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, livro.getName());
            statement.setDate(2, Date.valueOf(livro.getDate()));
            statement.setString(3, String.valueOf(livro.getStatus()));
            statement.setInt(4, livro.getAutor().getId());

            Integer rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                ResultSet id = statement.getGeneratedKeys();
                id.next();
                Integer idLivro = id.getInt(1);

                System.out.println("Rows inserted: " + rowsInserted);
                System.out.println("Id: " + idLivro);

                livro.setId(idLivro);
            }

        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }

        return livro;
    }

    public void update(Livro livro) {

        String sql = "UPDATE livro SET " +
                "name = ?, " +
                "date = ?, " +
                "status = ?, " +
                "idautor = ? " +
                "WHERE livro.idLivro = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, livro.getName());
            statement.setDate(2, Date.valueOf(livro.getDate()));
            statement.setString(3, String.valueOf(livro.getStatus()));
            statement.setInt(4, livro.getAutor().getId());
            statement.setInt(5, livro.getId());

            int rowsAffected = statement.executeUpdate();

            System.out.println("Rows affected: " + rowsAffected);

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

    }

    public void delete(Integer id) {

        String sql = "DELETE FROM livro WHERE idlivro = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, id);
            Integer rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Rows deleted: " + rowsDeleted);
            }

        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
    }

    public Livro getById(Integer id) {

        Livro livro;
        Autor autor;

        String sql = "SELECT livro.*,autor.name as autorName " +
                "FROM livro " +
                "INNER JOIN autor " +
                "ON livro.idautor = autor.idautor " +
                "WHERE livro.idlivro = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                autor = this.instantiateAutor(resultSet);
                livro = this.instantiateLivro(resultSet, autor);

            } else {
                throw new DatabaseException("Livro não encontrado");
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        return livro;
    }

    public List<Livro> findByAutor(Integer id) {

        List<Livro> LivrosList = new ArrayList<>();

        Livro livro;
        Autor autor;

        String sql = "SELECT livro.*, autor.name as AutorName " +
                "FROM livro INNER JOIN autor " +
                "ON livro.idautor = autor.idautor " +
                "WHERE livro.idautor = ? " +
                "ORDER BY AutorName";


        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            Map<Integer, Autor> map = new HashMap<>();

            while (resultSet.next()) {

                autor = map.get(resultSet.getInt("idautor"));

                if (autor== null) {
                    autor = instantiateAutor(resultSet);
                    map.put(resultSet.getInt("idautor"), autor);
                }

                livro = this.instantiateLivro(resultSet, autor);

                LivrosList.add(livro);
            }

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }

        return LivrosList;

    }

    public Livro instantiateLivro(ResultSet resultSet, Autor autor) throws SQLException {

        Livro livro = new Livro();

        livro.setId(resultSet.getInt("idLivro"));
        livro.setName(resultSet.getString("name"));
        livro.setDate(resultSet.getDate("date").toLocalDate());
        livro.setStatus(StatusLivro.valueOf(resultSet.getString("status")));
        livro.setAutor(autor);

        return livro;
    }

    public Autor instantiateAutor(ResultSet resultSet) throws SQLException {

        Autor autor = new Autor();

        autor.setId(resultSet.getInt("idautor"));
        autor.setName(resultSet.getString("autorName"));

        return autor;
    }

}
