package test;

import part2.barbara.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class TestControler {

    @ParameterizedTest
    @CsvSource({
        "123, true",  // RA verificado
        "999, false"  // RA não verificado
    })
    void testEmprestimoParaAlunoVerificado(String ra, boolean esperado) {
        Controle controle = new Controle();
        int[] prazos = {2};  // 1 livro
        boolean retorno = controle.emprestar(ra, prazos.length, prazos);
        
        assertEquals(esperado, retorno);
    }

     @Test
    void testControleEmprestimoComRaNulo() {
        Controle controle = new Controle();
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            controle.emprestar(null, 3, new int[]{1, 2, 3});
        });
        
        assertEquals("RA do aluno não pode ser nulo", thrown.getMessage());
    }

   @Test
    void testControleEmprestimoComNumeroInvalidoDeLivros() {
        Controle controle = new Controle();
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            controle.emprestar("123", -1, new int[]{1, 2, 3});  // Número de livros inválido
        });
        
        assertEquals("Número de livros inválido", thrown.getMessage());
    }
}
