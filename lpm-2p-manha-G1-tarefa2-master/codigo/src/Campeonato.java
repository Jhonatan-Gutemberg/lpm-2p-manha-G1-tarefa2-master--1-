
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Campeonato {
    private Jogo[] jogos;
    private Equipe[] classificacao;

    public Campeonato(Jogo[] jogos, Equipe[] classificacao) {
        this.jogos = jogos;
        this.classificacao = classificacao;
    }

    public void ordenarClassificacao() {
        // Exemplo: Equipe.equipes.sort(null);
    }

    /*
     * public Jogo cadastrarJogo(){
     * Scanner sc = new Scanner(System.in);
     * 
     * System.out.println("Digite a data (dd/MM/yyyy): ");
     * String data = sc.nextLine();
     * 
     * Jogo.stringToDate(data);
     * 
     * System.out.println("Digite a primeira Equipe competidora: ");
     * String equipeA = sc.nextLine();
     * System.out.println("Digite a segunda Equipe competidora: ");
     * String equipeB = sc.nextLine();
     * 
     * return new Jogo(data, equipeA, equipeB, );//falta botar o placar em 0 e 0
     * }
     */
    public Equipe cadastrarEquipe() { // Talvez esse metodo nao possa retornar EQUIPE
        Scanner sc = new Scanner(System.in);

        System.out.println("Digite o nome da equipe: ");

        String name = sc.nextLine();

        equipeFile(name);
        return new Equipe(name);
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
            String fileName = "codigo/lib/Equipes.txt";
            boolean fileExists = fileExists(fileName);

            FileWriter writer = new FileWriter(fileName, true);

            if (!fileExists) {
                writer.write("Lista de Equipes:\n");
            }

            String equipeData = String.format("%s\n", name);

            writer.write(equipeData);
            writer.close();

            if (!fileExists) {
                System.out.println("O arquivo " + fileName + " foi criado e o cliente foi adicionado.");
            } else {
                System.out.println("Equipe adicionada com sucesso ao arquivo.");
            }
        } catch (IOException e) {
            System.err.println("Erro ao adicionar equipe ao arquivo.");
            e.printStackTrace();
        }
    }

    public static void readEquipeFile() {
        try {
            String fileName = "codigo/lib/Equipes.txt";
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // System.out.println(line);

                Equipe equipe = new Equipe(line);
                Equipe.equipes.add(equipe);
            }

            bufferedReader.close();
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de equipes.");
            e.printStackTrace();
        }
    }

    public static void showEquipe() {
        System.out.println("--------------------------------");

        System.out.println("Equipes cadastradas:");

        for (Equipe e : Equipe.equipes) {
            System.out.println(e.getName());
        }

        System.out.println("--------------------------------");

    }

    public void obterpontuacao() {
        String arquivojogos = "lib/jogos.csv";
        StringBuilder tabelaAcumulativa = new StringBuilder();
        String melhorAtaque;
        String melhorDefesa;
        int ptMarcado = 0;
        int ptSofrido = 0;

        try {
            FileReader arquivoReader = new FileReader(arquivojogos);
            try (BufferedReader bufferedLeitura = new BufferedReader(arquivoReader)) {
                String linha;
                int idLinha = 0;
                int p11, p12, p13, p14, p10, p21, p22, p23, p24, p20, t1, t2;

                while ((linha = bufferedLeitura.readLine()) != null) {
                    String[] dados = linha.split(",");
                    dados[3] = dados[3].replace(" ", "");
                    p11 = Integer.parseInt(dados[3]);
                    dados[4] = dados[4].replace(" ", "");
                    p12 = Integer.parseInt(dados[4]);
                    dados[5] = dados[5].replace(" ", "");
                    p13 = Integer.parseInt(dados[5]);
                    dados[6] = dados[6].replace(" ", "");
                    p14 = Integer.parseInt(dados[6]);
                    dados[7] = dados[7].replace(" ", "");
                    p10 = Integer.parseInt(dados[7]);
                    dados[8] = dados[8].replace(" ", "");
                    p21 = Integer.parseInt(dados[8]);
                    dados[9] = dados[9].replace(" ", "");
                    p22 = Integer.parseInt(dados[9]);
                    dados[10] = dados[10].replace(" ", "");
                    p23 = Integer.parseInt(dados[10]);
                    dados[11] = dados[11].replace(" ", "");
                    p24 = Integer.parseInt(dados[11]);
                    dados[12] = dados[12].replace(" ", "");
                    p20 = Integer.parseInt(dados[12]);
                    t1 = p11 + p12 + p13 + p14 + p10;
                    t2 = p21 + p22 + p23 + p24 + p20;

                    if (t1 > t2){
                        melhorAtaque = dados[1];
                        melhorDefesa = dados[1];
                        ptMarcado = t1;
                        ptSofrido = t2;
                    } else if (t2 > t1){
                        melhorAtaque = dados[2];
                        melhorDefesa = dados[2];
                        ptMarcado = t2;
                        ptSofrido = t1;
                    } else if (t1 == t2 || t2 == t1) {
                        melhorAtaque = "EMPATE";
                        melhorDefesa = "EMPATE";
                        ptMarcado = t1;
                        ptSofrido = t2;
                    }
                    else {
                        melhorAtaque = "";
                        melhorDefesa = "";
                        ptMarcado = 0;
                        ptSofrido = 0;
                    }

                    //String pontuacao = String.format("%s, %d, %s, %d", dados[1], t1, dados[2], t2);

                    String tabela = String.format("%d - %s: %d pts Marcados / %d pts Sofridos \n", idLinha + 1, melhorAtaque, ptMarcado, ptSofrido);

                    tabelaAcumulativa.append(tabela);

                    idLinha++;
                }
                tabelaFile(tabelaAcumulativa.toString());
                bufferedLeitura.close();
            }

            arquivoReader.close();
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de equipes: " + e.getMessage());
        }
    }

    public void tabelaFile(String tabela) {
        try {
            String fileName = "lib/tabela.csv";
            boolean fileExists = fileExists(fileName);

            FileWriter writer = new FileWriter(fileName, false);

            writer.write(tabela);
            writer.close();

            if (!fileExists) {
                System.out.println("A tabela foi criada com sucesso.");
            } else {
                System.out.println("Tabela atualizada com sucesso.");
            }
        } catch (IOException e) {
            System.err.println("Erro ao criar tabela.");
            e.printStackTrace();
        }
    }

}
