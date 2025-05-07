import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class EstoqueItem {
    private String codigo;
    private String nome;
    private String quantidade;
    private double preco;

    public EstoqueItem(String codigo, String nome, String quantidade, double preco) {
        this.codigo = codigo;
        this.nome = nome;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setQuantidade(String novaQuantidade) {
        this.quantidade = novaQuantidade;
    }
    
    @Override
    public String toString() {
        return codigo + " - " + nome + " - Quantidade: " + quantidade + " - Preço: R$" + preco;
    }
}

class Estoque {
    private static final String FILE_PATH = "data/estoque.txt";
    private List<EstoqueItem> itens;

    public Estoque() {
        this.itens = new ArrayList<>();
        this.itens.addAll(carregarEstoqueDoArquivo());
    }

    public void adicionarItem(String codigo, String nome, String quantidade, double preco) {
        EstoqueItem novoItem = new EstoqueItem(codigo, nome, quantidade, preco);
        itens.add(novoItem);
        salvarEstoqueEmArquivo();
    }

    public List<EstoqueItem> getItens() {
        return itens;
    }

    public void salvarEstoqueEmArquivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (EstoqueItem item : itens) {
                writer.write(item.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<EstoqueItem> carregarEstoqueDoArquivo() {
        List<EstoqueItem> itensCarregados = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(" - ");
                if (partes.length >= 4) {
                    String codigo = partes[0];
                    String nome = partes[1];
                    String quantidade = partes[2].replace("Quantidade: ", "");
                    double preco = Double.parseDouble(partes[3].replace("Preço: R$", ""));
                    EstoqueItem item = new EstoqueItem(codigo, nome, quantidade, preco);
                    itensCarregados.add(item);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return itensCarregados;
    }
}
