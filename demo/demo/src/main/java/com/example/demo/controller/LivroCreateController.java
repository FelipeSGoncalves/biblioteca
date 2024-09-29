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
import java.util.Date;
import java.util.List;

@WebServlet("/livros/create")
public class LivroCreateController extends HttpServlet {

    LivroRepository livroRepository = new LivroRepository();
    AutorRepository autorRepository = new AutorRepository();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Autor> autors = autorRepository.getAll();
        req.setAttribute("autors", autors);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/livros-create.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("field_name");
        LocalDate date = LocalDate.parse(req.getParameter("field_date"));
        StatusLivro status = StatusLivro.valueOf(req.getParameter("field_status"));
        Integer idautor = Integer.valueOf(req.getParameter("field_autor"));

        Autor autor = new Autor();
        autor.setId(idautor);

        Livro livro = new Livro();
        livro.setName(name);
        livro.setDate(date);
        livro.setStatus(status);
        livro.setAutor(autor);

        livroRepository.insert(livro);

        resp.sendRedirect(req.getContextPath() + "/livros");

    }
}
