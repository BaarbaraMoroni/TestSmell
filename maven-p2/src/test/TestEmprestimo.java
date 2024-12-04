package test;

import part2.barbara.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;

class TestEmprestimo {

    @Test
    void testEmprestimoComLivroValido() {
        Aluno aluno = new Aluno("123", false);  // Aluno válido
        List<Livro> livros = new ArrayList<>();
        livros.add(new Livro(1));  // Livro válido
        Emprestimo emprestimo = new Emprestimo();
        
        boolean sucesso = emprestimo.emprestar(livros, aluno);
        assertTrue("O empréstimo deve ser realizado com sucesso para livros válidos", sucesso);
    }

     @Test
    void testEmprestimoComLimiteDeLivros() {
        Aluno aluno = new Aluno("123", false);  // Aluno válido
        List<Livro> livros = List.of(new Livro(1));  // Apenas um livro
        Emprestimo emprestimo = new Emprestimo();
        
        boolean sucesso = emprestimo.emprestar(livros, aluno);
        assertTrue("O empréstimo deve ser realizado com sucesso", sucesso);
    }

     @Test
    void testCalculaDataDeDevolucao() {
        LocalDate fixedDate = LocalDate.of(2024, 12, 1);  // Data fixa
        Clock clock = Clock.fixed(fixedDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        
        Emprestimo emprestimo = new Emprestimo(clock);
        List<Livro> livros = new ArrayList<>();
        livros.add(new Livro(1));  // Livro com prazo de 2 dias
        
        Date dataDevolucao = emprestimo.calculaDataDevolucao(livros);
        assertEquals(LocalDate.of(2024, 12, 3), dataDevolucao.toLocalDate());
    }

    @Test
    void testEmprestimoParaAlunoComRaInvalido() {
        Aluno aluno = new Aluno(null, false);  // RA nulo
        List<Livro> livros = new ArrayList<>();
        livros.add(new Livro(1));  // Livro válido
        
        Emprestimo emprestimo = new Emprestimo();
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            emprestimo.emprestar(livros, aluno);
        });

        assertEquals("RA do aluno não pode ser nulo", thrown.getMessage());
    }

    @Test
    void testEmprestimoParaAlunoComDebito() {
        Aluno aluno = new Aluno("123", true);  // RA de aluno com débito
        List<Livro> livros = new ArrayList<>();
        livros.add(new Livro(1));  // Livro válido
        
        Emprestimo emprestimo = new Emprestimo();
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            emprestimo.emprestar(livros, aluno);
        });

        assertEquals("Aluno com débito não pode pegar emprestado", thrown.getMessage());
    }

    @Test
    void testEmprestimoComMaisDeCincoLivros() {
        Aluno aluno = new Aluno("123", false);  // Aluno válido
        List<Livro> livros = new ArrayList<>();
        for (int i = 0; i < 6; i++) {  // Tentativa de pegar 6 livros
            livros.add(new Livro(i));
        }

        Emprestimo emprestimo = new Emprestimo();
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            emprestimo.emprestar(livros, aluno);
        });

        assertEquals("O limite máximo de livros que pode ser emprestado é 5", thrown.getMessage());
    }
}
