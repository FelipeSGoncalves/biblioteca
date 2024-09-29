package repositories;

import com.example.demo.model.Autor;
import com.example.demo.model.Livro;
import com.example.demo.model.StatusLivro;
import com.example.demo.repositories.AutorRepository;
import com.example.demo.repositories.LivroRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class LivroRepositoryTest{

    @Test
    public void deveExibirUmaListaDeLivros(){

        LivroRepository repository = new LivroRepository();

        List<Livro> livros = repository.getLivros();

        for (Livro s: livros) {
            System.out.println(s);
        }
    }

    @Test
    public void deveInserirUmRegistroNaTabelaLivro(){

        LivroRepository repository = new LivroRepository();
        AutorRepository autorRepository = new AutorRepository();

        Autor autorFake = new Autor();
        autorFake.setName("Felipe");

        Autor autor = autorRepository.insert(autorFake);

        Livro livroFake = new Livro();
        livroFake.setName("Frodo");
        livroFake.setDate(LocalDate.of(2024, 8, 5));
        livroFake.setStatus(StatusLivro.valueOf("DISPONIVEL"));
        livroFake.setAutor(autor);

        Livro livro = repository.insert(livroFake);

    }

    @Test
    public void deveDeletarUmLivro(){

        LivroRepository repository = new LivroRepository();
        repository.delete(9);

    }

    @Test
    public void deveRetornarUmLivroPeloId(){

        LivroRepository repository = new LivroRepository();
        Livro livro = repository.getById(1);

        System.out.println(livro);
        System.out.println("Autor: --------");
        System.out.println(livro.getAutor());
    }

    @Test
    public void deveRetornarUmaListaDeLivrosPeloIdDoAutor() {
        LivroRepository repository = new LivroRepository();
        List<Livro> livrosList = repository.findByAutor(1);

        for (Livro livro: livrosList) {
            System.out.println(livro);
            System.out.println("Autor: --------");
            System.out.println(livro.getAutor());
        }
    }
}
