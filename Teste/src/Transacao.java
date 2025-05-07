import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class Transacao {
    private String descricao; // valor descriçao
    private double valor;	//valor transação
    private Date dataHora; // Data e hora da transação
    private String tipo; // Tipo da transação

    public Transacao(String descricao, double valor, String tipo, Date dataHora) {
        this.descricao = descricao;
        this.valor = valor;
        this.dataHora = dataHora;
        this.tipo = tipo;
    }

    // Getters para os atributos da transação
    public String getDescricao() {
        return descricao;
    }

    public double getValor() {
        return valor;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public String getTipo() {
        return tipo;
    }

    // Método para salvar transações em um arquivo
    public static void salvarTransacoes(ArrayList<Transacao> transacoes) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/transacoes.txt", false))) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            for (Transacao transacao : transacoes) {
                String dataFormatada = formatter.format(transacao.getDataHora());
                String linha = transacao.getDescricao() + "," + transacao.getValor() + "," + dataFormatada + "," + transacao.getTipo() + "\n";
                writer.write(linha);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para carregar transações de um arquivo
    public static ArrayList<Transacao> carregarTransacoes() {
        ArrayList<Transacao> transacoes = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("data/transacoes.txt"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dadosTransacao = linha.split(",");
                String descricao = dadosTransacao[0];
                double valor = Double.parseDouble(dadosTransacao[1]);
                Date dataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(dadosTransacao[2]);
                String tipo = dadosTransacao[3];

                // Adiciona a transação lida do arquivo com a data e hora correspondente
                transacoes.add(new Transacao(descricao, valor, tipo, dataHora));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return transacoes;
    }
    
    public static double calcularSaldoEntrada(ArrayList<Transacao> extrato) {
        double saldoEntrada = 0.0;
        Date hoje = new Date();
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

        for (Transacao transacao : extrato) {
            if (formatador.format(transacao.getDataHora()).equals(formatador.format(hoje)) &&
                transacao.getTipo().equals("Adição")) {
                saldoEntrada += transacao.getValor();
            }
            if (formatador.format(transacao.getDataHora()).equals(formatador.format(hoje)) &&
                    transacao.getTipo().equals("Venda")) {
                    saldoEntrada += transacao.getValor();
                }
            if (formatador.format(transacao.getDataHora()).equals(formatador.format(hoje)) &&
                    transacao.getTipo().equals("Pagamento de dívida")) {
                    saldoEntrada += transacao.getValor();
                }
        }
        return saldoEntrada;
    }

    public static double calcularSaldoSaida(ArrayList<Transacao> extrato) {
        double saldoSaida = 0.0;
        Date hoje = new Date();
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

        for (Transacao transacao : extrato) {
            if (formatador.format(transacao.getDataHora()).equals(formatador.format(hoje)) &&
                transacao.getTipo().equals("Retirada")) {
                saldoSaida += transacao.getValor();
            }
            if (formatador.format(transacao.getDataHora()).equals(formatador.format(hoje)) &&
                    transacao.getTipo().equals("Adição de dívida")) {
                    saldoSaida += transacao.getValor();
                }
            
        }
        return saldoSaida;
    }

    public static double calcularLucroDiario(ArrayList<Transacao> extrato) {
        double saldoEntrada = calcularSaldoEntrada(extrato);
        double saldoSaida = calcularSaldoSaida(extrato);
        return saldoEntrada - saldoSaida;
    }
    
    
    public static double calcularSaldoEntradaSemanal(ArrayList<Transacao> extrato) {
        double saldoEntrada = 0.0;
        LocalDate dataAtual = LocalDate.now();
        LocalDate dataInicial = dataAtual.minusDays(7);
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

        for (Transacao transacao : extrato) {
            LocalDate dataTransacao = transacao.getDataHora().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if (!dataTransacao.isBefore(dataInicial) && transacao.getTipo().equals("Adição")) {
                saldoEntrada += transacao.getValor();
            }
            if (!dataTransacao.isBefore(dataInicial) && transacao.getTipo().equals("Venda")) {
                saldoEntrada += transacao.getValor();
            }
            if (!dataTransacao.isBefore(dataInicial) && transacao.getTipo().equals("Pagamento de dívida")) {
                saldoEntrada += transacao.getValor();
            }
        }

        return saldoEntrada;
    }

    // Função para calcular o saldo de saída em um período semanal
    public static double calcularSaldoSaidaSemanal(ArrayList<Transacao> extrato) {
        double saldoSaida = 0.0;
        LocalDate dataAtual = LocalDate.now();
        LocalDate dataInicial = dataAtual.minusDays(7);
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

        for (Transacao transacao : extrato) {
            LocalDate dataTransacao = transacao.getDataHora().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if (!dataTransacao.isBefore(dataInicial) && transacao.getTipo().equals("Retirada")) {
                saldoSaida += transacao.getValor();
            }
            if (!dataTransacao.isBefore(dataInicial) && transacao.getTipo().equals("Adição de dívida")) {
                saldoSaida += transacao.getValor();
            }
        }

        return saldoSaida;
    }

    // Função para calcular o lucro diário em um período semanal
    public static double calcularLucroSemanal(ArrayList<Transacao> extrato) {
        double saldoEntrada = calcularSaldoEntradaSemanal(extrato);
        double saldoSaida = calcularSaldoSaidaSemanal(extrato);
        return saldoEntrada - saldoSaida;
    }

    // Função para calcular o saldo de entrada em um período mensal
    public static double calcularSaldoEntradaMensal(ArrayList<Transacao> extrato) {
        double saldoEntrada = 0.0;
        LocalDate dataAtual = LocalDate.now();
        LocalDate dataInicial = dataAtual.minusDays(30);
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

        for (Transacao transacao : extrato) {
            LocalDate dataTransacao = transacao.getDataHora().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if (!dataTransacao.isBefore(dataInicial) && transacao.getTipo().equals("Adição")) {
                saldoEntrada += transacao.getValor();
            }
            
            if (!dataTransacao.isBefore(dataInicial) && transacao.getTipo().equals("Venda")) {
                saldoEntrada += transacao.getValor();
            }
            
            if (!dataTransacao.isBefore(dataInicial) && transacao.getTipo().equals("Pagamento de dívida")) {
                saldoEntrada += transacao.getValor();
            }
        }

        return saldoEntrada;
    }

    // Função para calcular o saldo de saída em um período mensal
    public static double calcularSaldoSaidaMensal(ArrayList<Transacao> extrato) {
        double saldoSaida = 0.0;
        LocalDate dataAtual = LocalDate.now();
        LocalDate dataInicial = dataAtual.minusDays(30);
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

        for (Transacao transacao : extrato) {
            LocalDate dataTransacao = transacao.getDataHora().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if (!dataTransacao.isBefore(dataInicial) && transacao.getTipo().equals("Retirada")) {
                saldoSaida += transacao.getValor();
            }
            
            if (!dataTransacao.isBefore(dataInicial) && transacao.getTipo().equals("Adição de dívida")) {
                saldoSaida += transacao.getValor();
            }
        }

        return saldoSaida;
    }

    // Função para calcular o lucro diário em um período mensal
    public static double calcularLucroMensal(ArrayList<Transacao> extrato) {
        double saldoEntrada = calcularSaldoEntradaMensal(extrato);
        double saldoSaida = calcularSaldoSaidaMensal(extrato);
        return saldoEntrada - saldoSaida;
    }

    // Função para calcular o saldo de entrada total
    public static double calcularSaldoEntradaTotal(ArrayList<Transacao> extrato) {
        double saldoEntrada = 0.0;
        for (Transacao transacao : extrato) {
            if (transacao.getTipo().equals("Adição")) {
                saldoEntrada += transacao.getValor();
            }
            
            if (transacao.getTipo().equals("Venda")) {
                saldoEntrada += transacao.getValor();
            }
            
            if (transacao.getTipo().equals("Pagamento de dívida")) {
                saldoEntrada += transacao.getValor();
            }

        }
        return saldoEntrada;
    }

    // Função para calcular o saldo de saída total
    public static double calcularSaldoSaidaTotal(ArrayList<Transacao> extrato) {
        double saldoSaida = 0.0;
        for (Transacao transacao : extrato) {
            if (transacao.getTipo().equals("Retirada")) {
                saldoSaida += transacao.getValor();
            }
            
            if (transacao.getTipo().equals("Adição de dívida")) {
                saldoSaida += transacao.getValor();
            }
        }
        return saldoSaida;
    }

    // Função para calcular o lucro total
    public static double calcularLucroTotal(ArrayList<Transacao> extrato) {
        double saldoEntrada = calcularSaldoEntradaTotal(extrato);
        double saldoSaida = calcularSaldoSaidaTotal(extrato);
        return saldoEntrada - saldoSaida;
    }
}
