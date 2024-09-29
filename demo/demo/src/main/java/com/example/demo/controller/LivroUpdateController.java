package com.example.demo.controller;

import com.example.demo.model.Autor;
import com.example.demo.model.Livro;
import com.example.demo.model.StatusLivro;
import com.example.demo.repositories.AutorRepository;
import com.example.demo.repositories.LivroRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/livros/update")
public class LivroUpdateController extends HttpServlet {

    LivroRepository livroRepository = new LivroRepository();
    AutorRepository autorRepository = new AutorRepository();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer idlivro = Integer.valueOf(req.getParameter("id"));

        List<Autor> autors = autorRepository.getAll();
        req.setAttribute("autors", autors);

        Livro livro = livroRepository.getById(idlivro);
        req.setAttribute("livro", livro);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/livros-update.jsp");
        dispatcher.forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer idlivro = Integer.valueOf(req.getParameter("field_idlivro"));
        String name = req.getParameter("field_name");
        LocalDate date = LocalDate.parse(req.getParameter("field_date"));
        StatusLivro status = StatusLivro.valueOf(req.getParameter("field_status"));
        Integer idautor = Integer.valueOf(req.getParameter("field_autor"));

        Autor autor = new Autor();
        autor.setId(idautor);

        Livro livro = new Livro();

        livro.setId(idlivro);
        livro.setName(name);
        livro.setDate(date);
        livro.setStatus(status);
        livro.setAutor(autor);
        livroRepository.update(livro);

        resp.sendRedirect(req.getContextPath() + "/livros");

    }
}
