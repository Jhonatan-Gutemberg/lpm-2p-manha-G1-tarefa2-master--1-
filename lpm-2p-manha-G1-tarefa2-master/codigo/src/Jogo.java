import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 
 * Classe que representa um jogo entre duas equipes.
 */
public class Jogo {
    int TAMANHO_DA_LINHA = 71;
    private String dataDoJogo;
    private int equipeA;
    private int equipeB;
    private int[][] placar;
    private Map<Integer, Integer> jogosPorEquipe;
    private List<Jogo> jogosCadastrados = new ArrayList<>();
    private Set<Jogo> jogosCadastrado = new HashSet<>();

    String arquivojogos = "jogos.csv";
    String arquivoequipes = "equipes.csv";
    int idEquipe = 1;
    int idJogos = 1;
    String duelo;
    String nomeEquipeA;
    String nomeEquipeB;
    Scanner scanner;
    private Date data;

    /**
     * Construtor da classe Jogo.
     *
     * @param dataDoJogo A data em que o jogo ocorreu.
     * @param equipeA    A equipe A que participou do jogo.
     * @param equipeB    A equipe B que participou do jogo.
     * @param placar     O placar do jogo, representando os pontos em diferentes
     *                   tempos.
     */
    public Jogo(String dataDoJogo, int equipeA, int equipeB) {
        this.dataDoJogo = dataDoJogo;
        this.equipeA = equipeA;
        this.equipeB = equipeB;
        this.placar = new int[2][5];
        this.jogosPorEquipe = new HashMap<>();

    }
    
   
    public String getDataDoJogo() {
        return dataDoJogo;
    }

    public void setDataDoJogo(String dataDoJogo) {
        this.dataDoJogo = dataDoJogo;
    }

    public int getEquipeA() {
        return equipeA;
    }

    public void setEquipeA(int equipeA) {
        this.equipeA = equipeA;
    }

    public int getEquipeB() {
        return equipeB;
    }

    public void setEquipeB(int equipeB) {
        this.equipeB = equipeB;
    }

    public int[][] getPlacar() {
        return placar;
    }

    public void setPlacar(int[][] placar) {
        this.placar = placar;
    }

    
    public int getIdEquipe() {
        return idEquipe;
    }

    public void setIdEquipe(int idEquipe) {
        this.idEquipe = idEquipe;
    }

    public int getIdJogos() {
        return idJogos;
    }

    public void setIdJogos(int idJogos) {
        this.idJogos = idJogos;
    }

    public String getNomeEquipeA() {
        return nomeEquipeA;
    }

    public void setNomeEquipeA(String nomeEquipeA) {
        this.nomeEquipeA = nomeEquipeA;
    }

    public String getNomeEquipeB() {
        return nomeEquipeB;
    }

    public void setNomeEquipeB(String nomeEquipeB) {
        this.nomeEquipeB = nomeEquipeB;
    }


    /**
     * Lê as equipes do arquivo CSV e exibe o nome de cada equipe com um
     * identificador.
     *
     * Este método lê as equipes a partir do arquivo CSV especificado e exibe o nome
     * de cada equipe
     * junto com um identificador único.
     *
     * @throws IOException Se ocorrer um erro de leitura do arquivo.
     */
    public void lerAquivoEquipes() throws IOException {
        try {
            FileReader arquivoReader = new FileReader(arquivoequipes);
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
    }

    /**
     * Permite ao usuário cadastrar um jogo, solicitando a data e os números das
     * equipes.
     * Após o cadastro, exibe os detalhes do jogo e salva no arquivo de jogos.
     */
    public void cadastrarJogo() {
        scanner = new Scanner(System.in);
    
        System.out.print("Digite a data do jogo (dd/MM/yyyy): ");
        dataDoJogo = scanner.nextLine();
        SimpleDateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            java.util.Date parsedDate = dataFormat.parse(dataDoJogo);
            this.data = new Date(parsedDate.getTime());
    
            Date dataAtual = new Date();
            if (data.before(dataAtual)) {
                System.out.println("A data do jogo deve ser no futuro.");
                return;
            }
        } catch (ParseException e) {
            System.out.println("Formato de data inválido. Use dd/MM/yyyy.");
            return;
        }
    
        int equipeA;
        int equipeB;
    
        System.out.print("Digite o número da equipe A: ");
        if (scanner.hasNextInt()) {
            equipeA = scanner.nextInt();
        } else {
            System.out.println("Número da equipe A inválido. Use apenas números inteiros.");
            return;
        }
    
        // Verificar se a entrada para a equipe B é um número
        System.out.print("Digite o número da equipe B: ");
        if (scanner.hasNextInt()) {
            equipeB = scanner.nextInt();
        } else {
            System.out.println("Número da equipe B inválido. Use apenas números inteiros.");
            return;
        }
    
        if (jogosCadastrado.contains(this)) {
            System.out.println("Esse jogo já foi cadastrado.");
            return;
        }
    
        if (equipeA == equipeB) {
            System.out.println("Uma equipe não pode jogar consigo mesma.");
            return;
        }
    
        if (jogoJaCadastrado(equipeA, equipeB)) {
            System.out.println("Esse jogo já foi cadastrado.");
            return;
        }
    
        nomeEquipeA = obterNomeEquipe(equipeA);
        nomeEquipeB = obterNomeEquipe(equipeB);
    
        if (nomeEquipeA != null && nomeEquipeB != null) {
            if (!equipesPodemJogar(equipeA) || !equipesPodemJogar(equipeB)) {
                System.out.println("Uma das equipes já jogou 7 vezes.");
                return;
            }
    
            System.out.println("Jogo cadastrado: " + dataDoJogo + ", " + nomeEquipeA + " , " + nomeEquipeB);
    
            try {
                escreverNovoJogo(dataDoJogo, equipeA, equipeB);
                atualizarContagemJogos(equipeA);
                atualizarContagemJogos(equipeB);
                jogosCadastrado.add(this);
            } catch (IOException e) {
                System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
            }
        } else {
            System.out.println("ID da equipe não encontrado.");
        }
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Jogo jogo = (Jogo) obj;
        return (equipeA == jogo.equipeA && equipeB == jogo.equipeB) ||
                (equipeA == jogo.equipeB && equipeB == jogo.equipeA);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Math.min(equipeA, equipeB), Math.max(equipeA, equipeB));
    }

    private boolean equipesPodemJogar(int equipe) {
        int jogosDaEquipe = jogosPorEquipe.getOrDefault(equipe, 0);
        return jogosDaEquipe < 7;
    }

    private void atualizarContagemJogos(int equipe) {
        int jogosDaEquipe = jogosPorEquipe.getOrDefault(equipe, 0);
        jogosPorEquipe.put(equipe, jogosDaEquipe + 1);
    }

    private boolean jogoJaCadastrado(int equipeA, int equipeB) {
        for (Jogo jogo : jogosCadastrados) {
            if ((jogo.equipeA == equipeA && jogo.equipeB == equipeB) ||
                    (jogo.equipeA == equipeB && jogo.equipeB == equipeA)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Obtém o nome de uma equipe com base no seu ID.
     *
     * @param idEquipe O ID da equipe desejada.
     * @return O nome da equipe correspondente ao ID, ou null se o ID não for
     *         encontrado.
     */
    public String obterNomeEquipe(int idEquipe) {
        try {
            FileReader arquivoReader = new FileReader(arquivoequipes);
            try (BufferedReader bufferedLeitura = new BufferedReader(arquivoReader)) {
                String linha;
                int idLinha = 1;

                while ((linha = bufferedLeitura.readLine()) != null) {
                    if (idLinha == idEquipe) {

                        return linha.split(",")[0];
                    }
                    idLinha++;
                }

                bufferedLeitura.close();
            }

            arquivoReader.close();
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de equipes: " + e.getMessage());
        }

        return null;
    }

   /**
 * Registra um novo jogo no arquivo de jogos.
 *
 * Este método permite o registro de um novo jogo no arquivo de jogos, fornecendo a data
 * do jogo, o número da equipe A e o número da equipe B. Os pontos do jogo são definidos
 * como zero por padrão. Se as equipes forem válidas, o jogo é adicionado ao arquivo.
 * 
 * @param dataDoJogo A data do jogo no formato "dd/MM/yyyy".
 * @param equipeA O número da equipe A.
 * @param equipeB O número da equipe B.
 * 
 * @throws IOException Se ocorrer um erro de leitura ou escrita do arquivo.
 */
public void escreverNovoJogo(String dataDoJogo, int equipeA, int equipeB) throws IOException {
    String nomeEquipeA = obterNomeEquipe(equipeA);
    String nomeEquipeB = obterNomeEquipe(equipeB);

    if (nomeEquipeA != null && nomeEquipeB != null) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date data = stringToDate(dataDoJogo);

        String novaLinha = String.format("%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s",
                dateFormat.format(data), nomeEquipeA, nomeEquipeB, "0", "0", "0", "0", "0", "0", "0", "0", "0",
                "0");

        FileWriter arquivoWriter = new FileWriter(arquivojogos, true);
        BufferedWriter bufferedWriter = new BufferedWriter(arquivoWriter);
        bufferedWriter.write(novaLinha);
        bufferedWriter.newLine();

        bufferedWriter.close();
        arquivoWriter.close();

        System.out.println("Novo jogo adicionado ao arquivo.");
    } else {
        System.out.println("ID da equipe não encontrado.");
    }
}

    /**
     * Método para criar o arquivo .csv.
     * Os jogos serão arquivados no arquivo jogos.csv.
     * 
     * @throws IOException
     */
    public void escreverArquivo() throws IOException {
        try {
            List<String> linhas = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new FileReader(arquivojogos));
            String linha;

            while ((linha = reader.readLine()) != null) {
                linhas.add(linha);
            }

            reader.close();

            String linhaExistente = linhas.get(idJogos - 1);
            String[] partes = linhaExistente.split(",");

            for (int i = 4; i <= 13; i++) {
                partes[i] = placar[0][i - 4] + ", " + placar[1][i - 4];
            }

            StringBuilder linhaAtualizada = new StringBuilder();
            for (int i = 0; i < partes.length; i++) {
                linhaAtualizada.append(partes[i]);
                if (i < partes.length - 1) {
                    linhaAtualizada.append(", ");
                }
            }

            linhas.set(idJogos - 1, linhaAtualizada.toString());

            FileWriter arquivoWriter = new FileWriter(arquivojogos);
            BufferedWriter bufferedWriter = new BufferedWriter(arquivoWriter);

            for (String linhaAtual : linhas) {
                bufferedWriter.write(linhaAtual);
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
            arquivoWriter.close();

            System.out.println("Pontuação adicionada ao arquivo.");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
  
/**
 * Permite ao usuário registrar a pontuação de um jogo previamente cadastrado.
 * O método solicita ao usuário que insira o número do jogo a ser pontuado e,
 * em seguida, solicita a pontuação para cada equipe e tempo. Os dados da partida
 * são atualizados e armazenados no arquivo de jogos.
 *
 * Este método realiza as seguintes etapas:
 * 1. Exibe a lista de jogos cadastrados para ajudar o usuário a escolher um jogo.
 * 2. Solicita ao usuário o número do jogo que deseja pontuar.
 * 3. Verifica se o número do jogo é válido (dentro dos limites da lista de jogos).
 * 4. Recupera as informações do jogo selecionado.
 * 5. Solicita a pontuação para cada equipe e tempo, incluindo a prorrogação.
 * 6. Atualiza os dados da partida no arquivo de jogos.
 * 7. Exibe o resultado da partida e salva a pontuação no arquivo.
 *
 * @throws IOException Se ocorrer um erro ao ler/escrever no arquivo de jogos.
 */
    public void pontuarJogo() {
        scanner = new Scanner(System.in);

        System.out.println("Jogos:");
        try {
            historicoJogos();
            System.out.print("Digite o número do jogo: ");
            idJogos = scanner.nextInt();

            List<String> linhas = new ArrayList<>();
            FileReader arquivoReader = new FileReader(arquivojogos);
            BufferedReader bufferedLeitura = new BufferedReader(arquivoReader);
            String linha;

            while ((linha = bufferedLeitura.readLine()) != null) {
                linhas.add(linha);
            }

            bufferedLeitura.close();
            arquivoReader.close();

            if (idJogos >= 1 && idJogos <= linhas.size()) {
                String linhaDoJogo = linhas.get(idJogos - 1);
                String[] dadosDoJogo = linhaDoJogo.split(",");
                String datajogo;

                datajogo = dadosDoJogo[0];
                nomeEquipeA = dadosDoJogo[1].trim();
                nomeEquipeB = dadosDoJogo[2].trim();

                System.out.println("Pontuando o jogo: " + nomeEquipeA + " vs " + nomeEquipeB);

                for (int tempo = 1; tempo <= 4; tempo++) {
                    System.out.print("Digite os pontos para " + nomeEquipeA + " no tempo " + tempo + ": ");
                    placar[0][tempo - 1] = scanner.nextInt();

                    System.out.print("Digite os pontos para " + nomeEquipeB + " no tempo " + tempo + ": ");
                    placar[1][tempo - 1] = scanner.nextInt();
                }

                System.out.print("Digite os pontos de prorrogação para " + nomeEquipeA + ": ");
                placar[0][4] = scanner.nextInt();

                System.out.print("Digite os pontos de prorrogação para " + nomeEquipeB + ": ");
                placar[1][4] = scanner.nextInt();

                String novaLinha = String.format(" %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s",
                        datajogo, nomeEquipeA, nomeEquipeB,
                        placar[0][0], placar[0][1], placar[0][2], placar[0][3], placar[0][4],
                        placar[1][0], placar[1][1], placar[1][2], placar[1][3], placar[1][4]);
                linhas.set(idJogos - 1, novaLinha);

                FileWriter arquivoWriter = new FileWriter(arquivojogos);
                BufferedWriter bufferedWriter = new BufferedWriter(arquivoWriter);

                for (String linhaAtualizada : linhas) {
                    bufferedWriter.write(linhaAtualizada);
                    bufferedWriter.newLine();
                }

                bufferedWriter.close();
                arquivoWriter.close();

                exibirResultado();
                escreverArquivo();
            } else {
                System.out.println("ID do jogo não encontrado.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtém os detalhes de um jogo por ID.
     *
     * @param idJogo O ID do jogo desejado.
     * @return Uma string contendo os detalhes do jogo (equipeA vs equipeB), ou null
     *         se o ID não for encontrado.
     */

    public String obterJogos(int idJogos) {
        try {
            FileReader arquivoReader = new FileReader(arquivojogos);
            try (BufferedReader bufferedLeitura = new BufferedReader(arquivoReader)) {
                String linha;
                int idLinha = 1;

                while ((linha = bufferedLeitura.readLine()) != null) {
                    if (idLinha == idJogos) {
                        String[] dados = linha.split(",");
                        return "Jogo cadastrado: " + dados[0] + dados[1] + "X" + dados[2];
                    }
                    idLinha++;
                }

                bufferedLeitura.close();
            }

            arquivoReader.close();
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de equipes: " + e.getMessage());
        }

        return null;
    }

   
    /**
     * Método para inserir a pontuação do placar do jogo.
     * O usuário é solicitado a inserir os pontos para cada equipe e tempo.
     * Os pontos são armazenados no placar.
     */
    public void pontuacaoPlacar() {
        scanner = new Scanner(System.in);

        for (int i = 0; i < 4; i++) {
            placar[0][i] = lerPontos(equipeA, i + 1);
        }

        for (int i = 0; i < 4; i++) {
            placar[1][i] = lerPontos(equipeB, i + 1);
        }

        placar[0][4] = lerPontos(equipeA, 5);

        placar[1][4] = lerPontos(equipeB, 5);

        exibirResultado();
    }

    /**
     * Método privado para ler os pontos da equipe em um tempo específico.
     *
     * @param equipe A equipe para a qual os pontos estão sendo lidos.
     * @param tempo  O tempo para o qual os pontos estão sendo lidos.
     * @return A pontuação lida.
     */
    private int lerPontos(int equipe, int tempo) {
        scanner = new Scanner(System.in);
        System.out.print("Digite os pontos da equipe " + equipe + " no tempo " + tempo + ": ");
        return scanner.nextInt();
    }

    /**
     * Método privado para exibir o resultado final do jogo.
     * Calcula o total de pontos de cada equipe e exibe o vencedor ou empate.
     */
    private void exibirResultado() {
        int totalEquipeA = calcularTotalPontos(placar[0]);
        int totalEquipeB = calcularTotalPontos(placar[1]);

        System.out.println("Pontuação final da equipe " + nomeEquipeA + ": " + totalEquipeA);
        System.out.println("Pontuação final da equipe " + nomeEquipeB + ": " + totalEquipeB);

        if (totalEquipeA > totalEquipeB) {
            System.out.println("Equipe " + nomeEquipeA + " venceu!");
        } else if (totalEquipeA < totalEquipeB) {
            System.out.println("Equipe " + nomeEquipeB + " venceu!");
        } else {
            System.out.println("O jogo terminou em empate.");
        }
    }

    /**
     * Método privado para calcular o total de pontos de uma equipe.
     *
     * @param pontos O array de pontos da equipe.
     * @return O total de pontos da equipe.
     */
    private int calcularTotalPontos(int[] pontos) {
        int total = 0;
        for (int ponto : pontos) {
            total += ponto;
        }
        return total;
    }

    public void historicoJogos() throws IOException {
        try {
            FileReader arquivoReader = new FileReader(arquivojogos);
            BufferedReader bufferedLeitura = new BufferedReader(arquivoReader);

            String linha;
            int idlinha = 1;
            while ((linha = bufferedLeitura.readLine()) != null) {
                String[] dados = linha.split(",");
                String jogos = dados[0] + ":" + dados[1] + " X" + dados[2];
                System.out.println(jogos + "(" + idlinha + ")");
                idlinha++;
            }

            bufferedLeitura.close();
            arquivoReader.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static Date stringToDate(String data) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Date dataDoJogo = null;

        try {
            dataDoJogo = dateFormat.parse(data);

            System.out.println("Data lida: " + dateFormat.format(dataDoJogo));

        } catch (ParseException e) {
            System.out.println("Formato de data inválido. Certifique-se de usar o formato dd/MM/yyyy.");
        }

        return dataDoJogo;
    }

}
