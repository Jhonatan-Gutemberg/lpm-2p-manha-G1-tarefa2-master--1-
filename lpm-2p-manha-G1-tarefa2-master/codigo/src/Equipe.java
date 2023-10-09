import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Equipe {
    private String name;

    static public ArrayList<Equipe> equipes = new ArrayList<>();

    public Equipe(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void novoCampeonato(){
    }

    public void jogosDaEquipe(){
        
    }
        public void cadastrarEquipe() { // Talvez esse metodo nao possa retornar EQUIPE
        Scanner sc = new Scanner(System.in);
        DeletarArquivo();
        System.out.println("Para o novo campeonato, será nescessário 8 times");
            for (int i = 0; i < 8; i++) {
        System.out.printf("Digite o nome da equipe %d: ", i);
        String name = sc.nextLine();

        equipeFile(name);}
    }

    /*
     * public Equipe melhorAtaqueDefesa(){
     * 
     * }
     */

    public boolean fileExists(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    public void equipeFile(String name) {
        try {
            String fileName = "equipes.csv"; // "lib/equipes.csv"
            boolean fileExists = fileExists(fileName);

            FileWriter writer = new FileWriter(fileName, true);
            String equipeData = String.format("%s, 0, 0, 0\n", name);

            writer.write(equipeData);
            writer.close();

        } catch (IOException e) {
            System.err.println("Erro ao adicionar equipe ao arquivo.");
            e.printStackTrace();
        }
    }

    public static void readEquipeFile() throws IOException {
        
        System.out.println("--------------------------------");
        System.out.println("Equipes cadastradas:");

        try {
            String fileName = "equipes.csv";//"lib/equipes.csv"
            int idEquipe = 1;
            FileReader arquivoReader = new FileReader(fileName);
            BufferedReader bufferedLeitura = new BufferedReader(arquivoReader);

            String linha;

            while ((linha = bufferedLeitura.readLine()) != null) {
                String[] dados = linha.split(",");
                String equipe = dados[0];
                System.out.println("Equipe: " + equipe + "(" + idEquipe + ")");
                idEquipe++;
            }

            bufferedLeitura.close();
            arquivoReader.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("--------------------------------");
    }

    public static void showEquipe(){
                    
        for (Equipe e : Equipe.equipes) {
            System.out.println(e.getName());
        }


    }
    public void DeletarArquivo() {

        // Especifique o caminho do arquivo que você deseja deletar
        String caminhoDoArquivo = "equipes.csv"; //"lib/equipes.csv"

        // Crie um objeto File com o caminho do arquivo
        File arquivo = new File(caminhoDoArquivo);

        // Verifique se o arquivo existe antes de tentar deletá-lo
        if (arquivo.exists()) {
            // Tenta deletar o arquivo
            if (arquivo.delete()) {
                System.out.println("Arquivo deletado com sucesso.");
            } else {
                System.out.println("Não foi possível deletar o arquivo.");
            }
        } else {
            System.out.println("O arquivo não existe.");
        }
    }
}
