import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JOptionPane;

public class SaldoCaixa {
    private double saldo;
    private int programajainiciado;

    public double getsaldo() {
        double saldo = 0.0;
        try (BufferedReader reader = new BufferedReader(new FileReader("data/saldo.txt"))) {
            String saldoString = reader.readLine();
            if (saldoString != null && !saldoString.isEmpty()) {
                saldo = Double.parseDouble(saldoString.trim());
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return saldo;
    }

    public void setSaldo(double saldo) {
        if (programajainiciado == 1) {
            int resposta = JOptionPane.showConfirmDialog(
                null,
                "O programa já possui um saldo salvo!\nDeseja realmente alterar o valor do saldo?",
                "Confirmação",
                JOptionPane.YES_NO_OPTION
            );
            if (resposta == JOptionPane.YES_OPTION) {
                this.saldo = saldo;
                salvarSaldoEmArquivo(saldo); // Chama o método para salvar o saldo em arquivo
                JOptionPane.showMessageDialog(null, "Saldo alterado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Operação cancelada. O saldo permanece o mesmo.");
            }
        } else {
            this.saldo = saldo;
            salvarSaldoEmArquivo(saldo); // Chama o método para salvar o saldo em arquivo
            JOptionPane.showMessageDialog(null, "Saldo setado com sucesso!");
        }
    }

    // Método para salvar o saldo em arquivo
    void salvarSaldoEmArquivo(double saldo) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("data/saldo.txt"))) {
            writer.println(saldo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
