package com.example.demo.controller;

import com.example.demo.model.Livro;
import com.example.demo.model.StatusLivro;
import com.example.demo.repositories.LivroRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"", "/livros"})
public class LivroController extends HttpServlet {

    private LivroRepository repository;

    public LivroController(){
        repository = new LivroRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Livro> livros = repository.getLivros();

        RequestDispatcher dispatcher = req.getRequestDispatcher("/livros.jsp");
        req.setAttribute("livros", livros);

        dispatcher.forward(req, resp);

    }
}
