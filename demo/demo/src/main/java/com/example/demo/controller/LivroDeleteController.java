package com.example.demo.controller;


import com.example.demo.repositories.LivroRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/livros/delete")
public class LivroDeleteController extends HttpServlet {

    LivroRepository repository = new LivroRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer idlivro = Integer.valueOf(req.getParameter("id"));

        repository.delete(idlivro);

        resp.sendRedirect("http://localhost:8080/biblioteca/livros");

    }
}
