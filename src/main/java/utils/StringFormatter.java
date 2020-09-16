package utils;

public class StringFormatter {
    public static String validarStringParaVazio(String value){
        return value != null && !value.isEmpty() ? value.trim() : "";
    }
}
