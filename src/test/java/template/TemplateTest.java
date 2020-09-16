package template;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class TemplateTest {

    @Test
    @DisplayName("Template")
    void testeTemplate() {

        TesteTemplate testeTemplate = new TesteTemplate();
        Assertions.assertEquals(100, testeTemplate.posicaoX());
        Assertions.assertEquals(200, testeTemplate.posicaoY());
        Assertions.assertEquals(new ArrayList<>(Arrays.asList("texto1", "texto2", "texto3")), testeTemplate.textosIdentificacao());
    }


}

