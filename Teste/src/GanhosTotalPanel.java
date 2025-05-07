import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.TextArea;

import javax.swing.JLabel;
import javax.swing.JPanel;

import java.util.ArrayList;
import java.util.List;

public class GanhosTotalPanel extends JPanel {
	
	private ArrayList<Transacao> extratoAtualizado;
	
	public GanhosTotalPanel(ArrayList<Transacao> extratoAtualizado) {
        this.extratoAtualizado = extratoAtualizado;
        initComponents();
        setLayout(null);
    }
	
	private void initComponents() {
    
		double saldoEntradaTotal = Transacao.calcularSaldoEntradaTotal(extratoAtualizado);
        double saldoSaidaTotal = Transacao.calcularSaldoSaidaTotal(extratoAtualizado);
        double lucroTotal = Transacao.calcularLucroTotal(extratoAtualizado);
        
        JLabel labeltotalentrada = new JLabel("  Entrada: R$\n " + saldoEntradaTotal);
        labeltotalentrada.setFont(new Font("Segoe UI Black", Font.PLAIN, 15));
        labeltotalentrada.setOpaque(true);
        labeltotalentrada.setBackground(new Color(176, 224, 230));
        labeltotalentrada.setBounds(0, 41, 280, 53);
        add(labeltotalentrada);

        
        JLabel labeltotalsaida = new JLabel("  Saída: R$\n " + saldoSaidaTotal);
        labeltotalsaida.setFont(new Font("Segoe UI Black", Font.PLAIN, 15));
        labeltotalsaida.setOpaque(true);
        labeltotalsaida.setBackground(new Color(255, 182, 193));
        labeltotalsaida.setBounds(0, 94, 280, 53);
        add(labeltotalsaida);
        
        JLabel labeltotallucro = new JLabel("   Lucro: R$\n " + lucroTotal);
        labeltotallucro.setFont(new Font("Segoe UI Black", Font.PLAIN, 17));
        if (lucroTotal > 0) {
            labeltotallucro.setBackground(new Color(0, 255, 0)); // Define a cor verde para valores positivos
        } else if (lucroTotal < 0) {
            labeltotallucro.setBackground(new Color(255, 0, 43)); // Define a cor vermelha para valores negativos
        } else {
            labeltotallucro.setBackground(new Color(192, 192, 192)); // Define outra cor para valores neutros (0)
        }
        labeltotallucro.setOpaque(true);
        labeltotallucro.setBounds(0, 147, 320, 110);
        add(labeltotallucro);
        
        JLabel lblNewLabel_11 = new JLabel("  Tempo Total de Uso do Programa");
        lblNewLabel_11.setFont(new Font("Segoe UI Variable", Font.ITALIC, 12));
        lblNewLabel_11.setBounds(188, -2, 231, 43);
        add(lblNewLabel_11);
        
        JLabel lblNewLabel_7 = new JLabel("    Exibindo Resultados Para: ");
        lblNewLabel_7.setOpaque(true);
        lblNewLabel_7.setBackground(new Color(128, 128, 128));
        lblNewLabel_7.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 18));
        lblNewLabel_7.setBounds(0, 0, 419, 41);
        add(lblNewLabel_7);
	}
	
	public void atualizarValoresPainel(ArrayList<Transacao> extratoAtualizado) {
	    this.extratoAtualizado = extratoAtualizado;
	    
	    double saldoEntradaTotal = Transacao.calcularSaldoEntradaTotal(extratoAtualizado);
	    double saldoSaidaTotal = Transacao.calcularSaldoSaidaTotal(extratoAtualizado);
	    double lucroTotal = Transacao.calcularLucroTotal(extratoAtualizado);
	    
	    // Atualizar os valores dos JLabels existentes
	    for (Component component : getComponents()) {
	        if (component instanceof JLabel) {
	            JLabel label = (JLabel) component;
	            String labelText = label.getText();

	            if (labelText.contains("Entrada: R$")) {
	                label.setText("  Entrada: R$\n " + saldoEntradaTotal);
	            } else if (labelText.contains("Saída: R$")) {
	                label.setText("  Saída: R$\n " + saldoSaidaTotal);
	            } else if (labelText.contains("Lucro: R$")) {
	                label.setText("   Lucro: R$\n " + lucroTotal);
	                if (lucroTotal > 0) {
	                    label.setBackground(new Color(0, 255, 0));
	                } else if (lucroTotal < 0) {
	                    label.setBackground(new Color(255, 0, 43));
	                } else {
	                    label.setBackground(new Color(192, 192, 192));
	                }
	            }
	        }
	    }
	}
}
