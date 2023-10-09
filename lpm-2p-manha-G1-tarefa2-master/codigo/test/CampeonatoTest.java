import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CampeonatoTest {
    
    @Test
    public void cadastrarEquipeSemNumeroNoNome() {
        String nome = "Taurinos"; // Nome sem número
        Equipe equipe = new Equipe(nome);

        for (int i = 0; i < nome.length(); i++) {
            char c = nome.charAt(i);
            if (Character.isDigit(c)) {
                Assertions.fail("O nome da equipe não deve conter números");
            }
        }
    }
}