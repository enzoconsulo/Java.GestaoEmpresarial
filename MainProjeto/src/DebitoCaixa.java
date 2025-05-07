import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JOptionPane;

public class DebitoCaixa {
    private double debito;
    private int programajainiciado;

    public double getDebito() {
        double debito = 0.0;
        try (BufferedReader reader = new BufferedReader(new FileReader("data/debito.txt"))) {
            String debitoString = reader.readLine();
            if (debitoString != null && !debitoString.isEmpty()) {
                debito = Double.parseDouble(debitoString.trim());
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return debito;
    }

    public void setDebito(double debito) {
        if (programajainiciado == 1) {
            int resposta = JOptionPane.showConfirmDialog(
                null,
                "O programa já possui um débito salvo!\nDeseja realmente alterar o valor do débito?",
                "Confirmação",
                JOptionPane.YES_NO_OPTION
            );
            if (resposta == JOptionPane.YES_OPTION) {
                this.debito = debito;
                salvarDebitoEmArquivo(debito); // Chama o método para salvar o débito em arquivo
                JOptionPane.showMessageDialog(null, "Débito alterado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Operação cancelada. O débito permanece o mesmo.");
            }
        } else {
            this.debito = debito;
            salvarDebitoEmArquivo(debito); // Chama o método para salvar o débito em arquivo
            JOptionPane.showMessageDialog(null, "Débito setado com sucesso!");
        }
    }

    // Método para salvar o débito em arquivo
    private void salvarDebitoEmArquivo(double debito) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("data/debito.txt"))) {
            writer.println(debito);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
