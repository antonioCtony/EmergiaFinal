package utilatery;

import java.util.regex.Pattern;

public class Validador {
    private static final String EMAIL_REGEX = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";

    public static boolean validarEmail(String email) {
        return Pattern.matches(EMAIL_REGEX, email);
    }

    public static boolean validarSenha(String senha) {
        return senha.length() >= 8 && senha.matches(".*[A-Z].*") && senha.matches(".*\\d.*");
    }
}
