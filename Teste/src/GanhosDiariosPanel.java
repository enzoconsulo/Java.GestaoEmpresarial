import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.TextArea;

import javax.swing.JLabel;
import javax.swing.JPanel;

import java.util.ArrayList;
import java.util.List;

public class GanhosDiariosPanel extends JPanel {
	
	private ArrayList<Transacao> extratoAtualizado;
	
	public GanhosDiariosPanel(ArrayList<Transacao> extratoAtualizado) {
        this.extratoAtualizado = extratoAtualizado;
        initComponents();
        setLayout(null);
    }
	
	private void initComponents() {
    
		double saldoEntrada = Transacao.calcularSaldoEntrada(extratoAtualizado);
        double saldoSaida = Transacao.calcularSaldoSaida(extratoAtualizado);
        double lucroDiario = Transacao.calcularLucroDiario(extratoAtualizado);

        JLabel labeldiarioentrada = new JLabel("  Entrada: R$\n " + saldoEntrada);
        labeldiarioentrada.setFont(new Font("Segoe UI Black", Font.PLAIN, 15));
        labeldiarioentrada.setOpaque(true);
        labeldiarioentrada.setBackground(new Color(176, 224, 230));
        labeldiarioentrada.setBounds(0, 41, 280, 53);
        add(labeldiarioentrada);

        JLabel labeldiariosaida = new JLabel("  Saída: R$\n " + saldoSaida);
        labeldiariosaida.setFont(new Font("Segoe UI Black", Font.PLAIN, 15));
        labeldiariosaida.setOpaque(true);
        labeldiariosaida.setBackground(new Color(255, 182, 193));
        labeldiariosaida.setBounds(0, 94, 280, 53);
        add(labeldiariosaida);

        JLabel labeldiariolucro = new JLabel("   Lucro: R$\n " + lucroDiario);
        labeldiariolucro.setFont(new Font("Segoe UI Black", Font.PLAIN, 17));
        if (lucroDiario > 0) {
            labeldiariolucro.setBackground(new Color(0, 255, 0)); 
        } else if (lucroDiario < 0) {
            labeldiariolucro.setBackground(new Color(255, 0, 43)); 
        } else {
            labeldiariolucro.setBackground(new Color(192, 192, 192)); 
        }
        labeldiariolucro.setOpaque(true);
        labeldiariolucro.setBounds(0, 147, 320, 110);
        add(labeldiariolucro);
        
        JLabel lblNewLabel_14 = new JLabel("  Hoje");
        lblNewLabel_14.setFont(new Font("Segoe UI Variable", Font.ITALIC, 14));
        lblNewLabel_14.setBounds(188, -2, 231, 43);
        add(lblNewLabel_14);
        
        JLabel lblNewLabel_10 = new JLabel("    Exibindo Resultados Para: ");
        lblNewLabel_10.setOpaque(true);
        lblNewLabel_10.setBackground(new Color(128, 128, 128));
        lblNewLabel_10.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 18));
        lblNewLabel_10.setBounds(0, 0, 419, 41);
        add(lblNewLabel_10);
	}
	
	public void atualizarValoresPainel(ArrayList<Transacao> extratoAtualizado) {
	    this.extratoAtualizado = extratoAtualizado;
	    
	    double saldoEntradaDiario = Transacao.calcularSaldoEntrada(extratoAtualizado);
	    double saldoSaidaDiario = Transacao.calcularSaldoSaida(extratoAtualizado);
	    double lucroDiario = Transacao.calcularLucroDiario(extratoAtualizado);
	    
	    // Atualizar os valores dos JLabels existentes
	    for (Component component : getComponents()) {
	        if (component instanceof JLabel) {
	            JLabel label = (JLabel) component;
	            String labelText = label.getText();

	            if (labelText.contains("Entrada: R$")) {
	                label.setText("  Entrada: R$\n " + saldoEntradaDiario);
	            } else if (labelText.contains("Saída: R$")) {
	                label.setText("  Saída: R$\n " + saldoSaidaDiario);
	            } else if (labelText.contains("Lucro: R$")) {
	                label.setText("   Lucro: R$\n " + lucroDiario);
	                if (lucroDiario > 0) {
	                    label.setBackground(new Color(0, 255, 0));
	                } else if (lucroDiario < 0) {
	                    label.setBackground(new Color(255, 0, 43));
	                } else {
	                    label.setBackground(new Color(192, 192, 192));
	                }
	            }
	        }
	    }
	}
}
