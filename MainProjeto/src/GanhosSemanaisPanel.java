import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.TextArea;

import javax.swing.JLabel;
import javax.swing.JPanel;

import java.util.ArrayList;
import java.util.List;

public class GanhosSemanaisPanel extends JPanel {
	
	private ArrayList<Transacao> extratoAtualizado;
	
	public GanhosSemanaisPanel(ArrayList<Transacao> extratoAtualizado) {
        this.extratoAtualizado = extratoAtualizado;
        initComponents();
        setLayout(null);
    }
	
	private void initComponents() {
    
		double saldoEntradaSemanal = Transacao.calcularSaldoEntradaSemanal(extratoAtualizado);
        double saldoSaidaSemanal = Transacao.calcularSaldoSaidaSemanal(extratoAtualizado);
        double lucroSemanal = Transacao.calcularLucroSemanal(extratoAtualizado);
        
        JLabel labelsemanalentrada = new JLabel("  Entrada: R$\n " + saldoEntradaSemanal);
        labelsemanalentrada.setFont(new Font("Segoe UI Black", Font.PLAIN, 15));
        labelsemanalentrada.setOpaque(true);
        labelsemanalentrada.setBackground(new Color(176, 224, 230));
        labelsemanalentrada.setBounds(0, 41, 280, 53);
        add(labelsemanalentrada);

        
        JLabel labelsemanalsaida = new JLabel("  Saída: R$\n " + saldoSaidaSemanal);
        labelsemanalsaida.setFont(new Font("Segoe UI Black", Font.PLAIN, 15));
        labelsemanalsaida.setOpaque(true);
        labelsemanalsaida.setBackground(new Color(255, 182, 193));
        labelsemanalsaida.setBounds(0, 94, 280, 53);
        add(labelsemanalsaida);
        
        JLabel labelsemanallucro = new JLabel("   Lucro: R$\n " + lucroSemanal);
        labelsemanallucro.setFont(new Font("Segoe UI Black", Font.PLAIN, 17));
        if (lucroSemanal > 0) {
            labelsemanallucro.setBackground(new Color(0, 255, 0)); // Define a cor verde para valores positivos
        } else if (lucroSemanal < 0) {
            labelsemanallucro.setBackground(new Color(255, 0, 43)); // Define a cor vermelha para valores negativos
        } else {
            labelsemanallucro.setBackground(new Color(192, 192, 192)); // Define outra cor para valores neutros (0)
        }
        labelsemanallucro.setOpaque(true);
        labelsemanallucro.setBounds(0, 147, 320, 110);
        add(labelsemanallucro);

        
        JLabel lblNewLabel_13 = new JLabel("  Última Semana");
        lblNewLabel_13.setFont(new Font("Segoe UI Variable", Font.ITALIC, 14));
        lblNewLabel_13.setBounds(188, -2, 231, 43);
        add(lblNewLabel_13);
        
        JLabel lblNewLabel_9 = new JLabel("    Exibindo Resultados Para: ");
        lblNewLabel_9.setOpaque(true);
        lblNewLabel_9.setBackground(new Color(128, 128, 128));
        lblNewLabel_9.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 18));
        lblNewLabel_9.setBounds(0, 0, 419, 41);
        add(lblNewLabel_9);
	}
	
	public void atualizarValoresPainel(ArrayList<Transacao> extratoAtualizado) {
	    this.extratoAtualizado = extratoAtualizado;
	    
	    double saldoEntradaSemanal = Transacao.calcularSaldoEntradaSemanal(extratoAtualizado);
	    double saldoSaidaSemanal = Transacao.calcularSaldoSaidaSemanal(extratoAtualizado);
	    double lucroSemanal = Transacao.calcularLucroSemanal(extratoAtualizado);
	    
	    // Atualizar os valores dos JLabels existentes
	    for (Component component : getComponents()) {
	        if (component instanceof JLabel) {
	            JLabel label = (JLabel) component;
	            String labelText = label.getText();

	            if (labelText.contains("Entrada: R$")) {
	                label.setText("  Entrada: R$\n " + saldoEntradaSemanal);
	            } else if (labelText.contains("Saída: R$")) {
	                label.setText("  Saída: R$\n " + saldoSaidaSemanal);
	            } else if (labelText.contains("Lucro: R$")) {
	                label.setText("   Lucro: R$\n " + lucroSemanal);
	                if (lucroSemanal > 0) {
	                    label.setBackground(new Color(0, 255, 0));
	                } else if (lucroSemanal < 0) {
	                    label.setBackground(new Color(255, 0, 43));
	                } else {
	                    label.setBackground(new Color(192, 192, 192));
	                }
	            }
	        }
	    }
	}
}
