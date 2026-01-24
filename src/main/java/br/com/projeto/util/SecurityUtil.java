package br.com.projeto.util;

import org.mindrot.jbcrypt.BCrypt;

public class SecurityUtil {
    // Gera o Hash seguro da senha
    public static String hashPassword(String password_plaintext) {
        return BCrypt.hashpw(password_plaintext, BCrypt.gensalt(12));
    }

    // Verifica se a senha bate com o Hash
    public static boolean checkPassword(String password_plaintext, String stored_hash) {
        if(stored_hash == null || !stored_hash.startsWith("$2a$")) return false;
        return BCrypt.checkpw(password_plaintext, stored_hash);
    }
}