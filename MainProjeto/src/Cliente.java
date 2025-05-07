import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Cliente {
    private static final String FILE_PATH = "data/clientes.txt";
    private List<ClienteItem> clientes;

    public List<ClienteItem> getClientesList() {
        return clientes;
    }

    public Cliente() {
        this.clientes = new ArrayList<>();
        this.clientes.addAll(carregarClientesDoArquivo());
    }

    public void adicionarCliente(String nome, String endereco, String telefone, double totalPago, double dividaPendente) {
        ClienteItem novoCliente = new ClienteItem(nome, endereco, telefone, totalPago, dividaPendente);
        clientes.add(novoCliente);
        salvarClientesNoArquivo();
    }

    public List<ClienteItem> getClientes() {
        return clientes;
    }

    public List<ClienteItem> carregarClientesDoArquivo() {
        List<ClienteItem> clientesCarregados = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(" - ");
                if (partes.length >= 5) {
                    String nome = partes[0];
                    String endereco = partes[1];
                    String telefone = partes[2];
                    double totalPago = Double.parseDouble(partes[3].replace("Saldo Pago: ", ""));
                    double dividaPendente = Double.parseDouble(partes[4].replace("Dívida Pendente: ", ""));
                    ClienteItem cliente = new ClienteItem(nome, endereco, telefone, totalPago, dividaPendente);
                    clientesCarregados.add(cliente);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return clientesCarregados;
    }

    public void salvarClientesNoArquivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (ClienteItem cliente : clientes) {
                writer.write(cliente.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClienteItem {
    private String nome;
    private String endereco;
    private String telefone;
    private double totalPago;
    private double dividaPendente;

    public ClienteItem(String nome, String endereco, String telefone, double totalPago, double dividaPendente) {
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.totalPago = totalPago;
        this.dividaPendente = dividaPendente;
    }

    // Getters e Setters para os campos

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public double getTotalPago() {
        return totalPago;
    }

    public void setTotalPago(double totalPago) {
        this.totalPago = totalPago;
    }

    public double getDividaPendente() {
        return dividaPendente;
    }

    public void setDividaPendente(double dividaPendente) {
        this.dividaPendente = dividaPendente;
    }

    @Override
    public String toString() {
        return nome + " - " + endereco + " - " + telefone + " - Saldo Pago: " + totalPago + " - Dívida Pendente: " + dividaPendente;
    }
}