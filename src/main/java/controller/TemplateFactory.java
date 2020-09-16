package controller;

import template.*;

import java.util.ArrayList;
import java.util.List;

public class TemplateFactory {

    public Template factory(String conteudoPagina) {

        List<Template> templates = listaTemplates();

        for (Template template: templates) {
            if (verificarTextos(template.textosIdentificacao(), conteudoPagina) == template.textosIdentificacao().size()) {
                return template;
            }
        }
        return  null;
    }

    private List<Template> listaTemplates() {

        List<Template> templates = new ArrayList<>();

        templates.add(new TemplatePagina1());
        templates.add(new TemplatePagina2());

        return templates;
    }

    private int verificarTextos(List<String> lista, String conteudoPagina) {
        int contador = 0;
        for (String s: lista) if (conteudoPagina.contains(s)) contador++;
        return contador;
    }

}
