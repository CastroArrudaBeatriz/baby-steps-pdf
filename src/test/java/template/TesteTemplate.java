package template;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class TesteTemplate implements Template {

    @Override
    public List<String> textosIdentificacao() {
        return new ArrayList<>(Arrays.asList("texto1", "texto2", "texto3"));
    }

    @Override
    public int posicaoX() {
        return 100;
    }

    @Override
    public int posicaoY() {
        return 200;
    }
}
