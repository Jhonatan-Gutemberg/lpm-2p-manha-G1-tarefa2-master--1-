import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws Exception {
        int menu;
        Scanner sc = new Scanner(System.in);
        String arquivoequipes = "equipes.csv"; // caminho para o arquivo equipe
        int[][] placar = new int[2][5]; // 2 equipes e 5 tempos (4 regulares e 1 prorrogação)
        int equipeA = 0;
        int equipeB = 0;
        String dataDate = "";
        Jogo jogo = new Jogo(dataDate, equipeA, equipeB);
        Equipe equipe = new Equipe(null);
        Campeonato camp = new Campeonato(null, null);

        while (true) {
            System.out.println("\nInsira um valor para executar uma das seguintes ações:");

            System.out.println("1.Cadastrar uma Equipe");

            System.out.println("2.Consultar Equipes");

            System.out.println("3.Novo Jogo");

            System.out.println("4.Historico Jogo");

            System.out.println("5.Pontuar placar");
            
            System.out.println("9.Encerrar Programa");

            menu = sc.nextInt();
            switch (menu) {
                case 1:
                    // Cadastra Equipe
                   // Equipe equipe = new Equipe(null);
                    equipe.cadastrarEquipe();
                    break;

                case 2:
                    // Mostra Equipes
                    equipe.readEquipeFile();
                    equipe.showEquipe();
                    break;
                case 3:
                    equipe.readEquipeFile();
                    jogo.cadastrarJogo();

                    // Chame o método para inserir a pontuação do placar
                    // jogo.pontuacaoPlacar();

                    break;
                    case 4:
                    jogo.historicoJogos();
                    break;
                case 5:
                    jogo.pontuarJogo();
                    break;
                case 6: 
            
                    camp.obterpontuacao();
        
                    break;
                case 9:
                    System.out.println("Programa Finalizado.");
                    System.exit(0); // Encerre o programa

                default:
                    System.out.println("Opcao invalida");
            }

            System.out.println("\nPressione Enter para voltar ao menu...");
            sc.nextLine(); // Limpar o buffer do scanner
            sc.nextLine(); // Aguardar pressionar Enter para continuar

            // Limpar a tela
            clearScreen();
        }
    }

    private static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException e) {
            // Tratar exceções, se necessário
            e.printStackTrace();
        }
    }
}
