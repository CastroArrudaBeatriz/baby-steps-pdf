package template;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TemplatePagina1 implements Template {

    @Override
    public List<String> textosIdentificacao() {
        return new ArrayList<>(
                Arrays.asList(
                        "PDF PARA TESTE DE ASSINATURA",
                        "FOLHA 1"));
    }

    @Override
    public int posicaoX() {
        return 100;
    }

    @Override
    public int posicaoY() {
        return 355;
    }
}
