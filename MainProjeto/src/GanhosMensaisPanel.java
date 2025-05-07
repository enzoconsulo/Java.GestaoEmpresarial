import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.util.ArrayList;
import java.util.List;

public class GanhosMensaisPanel extends JPanel {
	
	private ArrayList<Transacao> extratoAtualizado;
	
	public GanhosMensaisPanel(ArrayList<Transacao> extratoAtualizado) {
        this.extratoAtualizado = extratoAtualizado;
        initComponents();
        setLayout(null);
    }
	
	private void initComponents() {
    
    double saldoEntradaMensal = Transacao.calcularSaldoEntradaMensal(extratoAtualizado);
    double saldoSaidaMensal = Transacao.calcularSaldoSaidaMensal(extratoAtualizado);
    double lucroMensal = Transacao.calcularLucroMensal(extratoAtualizado);
    
    JLabel labelmensalentrada = new JLabel("  Entrada: R$\n " + saldoEntradaMensal);
    labelmensalentrada.setFont(new Font("Segoe UI Black", Font.PLAIN, 15));
    labelmensalentrada.setOpaque(true);
    labelmensalentrada.setBackground(new Color(176, 224, 230));
    labelmensalentrada.setBounds(0, 41, 280, 53);
    add(labelmensalentrada);

    JLabel labelmensalsaida = new JLabel("  Saída: R$\n " + saldoSaidaMensal);
    labelmensalsaida.setFont(new Font("Segoe UI Black", Font.PLAIN, 15));
    labelmensalsaida.setOpaque(true);
    labelmensalsaida.setBackground(new Color(255, 182, 193));
    labelmensalsaida.setBounds(0, 94, 280, 53);
    add(labelmensalsaida);

    JLabel labelmensallucro = new JLabel("   Lucro: R$\n  " + lucroMensal);
    labelmensallucro.setFont(new Font("Segoe UI Black", Font.PLAIN, 17));
    if (lucroMensal > 0) {
        labelmensallucro.setBackground(new Color(0, 255, 0));
    } else if (lucroMensal < 0) {
        labelmensallucro.setBackground(new Color(255, 0, 43));
    } else {
        labelmensallucro.setBackground(new Color(192, 192, 192));
    }
    labelmensallucro.setOpaque(true);
    labelmensallucro.setBounds(0, 147, 320, 110);
    add(labelmensallucro);

    JLabel lblNewLabel_12 = new JLabel("  Último Mês");
    lblNewLabel_12.setFont(new Font("Segoe UI Variable", Font.ITALIC, 14));
    lblNewLabel_12.setBounds(188, -2, 231, 43);
    add(lblNewLabel_12);

    JLabel lblNewLabel_8 = new JLabel("    Exibindo Resultados Para: ");
    lblNewLabel_8.setOpaque(true);
    lblNewLabel_8.setBackground(new Color(128, 128, 128));
    lblNewLabel_8.setFont(new Font("Tw Cen MT Condensed", Font.BOLD, 18));
    lblNewLabel_8.setBounds(0, 0, 419, 41);
    add(lblNewLabel_8);
	}
	
	public void atualizarValoresPainel(ArrayList<Transacao> extratoAtualizado) {
	    this.extratoAtualizado = extratoAtualizado;
	    
	    double saldoEntradaMensal = Transacao.calcularSaldoEntradaMensal(extratoAtualizado);
	    double saldoSaidaMensal = Transacao.calcularSaldoSaidaMensal(extratoAtualizado);
	    double lucroMensal = Transacao.calcularLucroMensal(extratoAtualizado);
	    
	    // Atualizar os valores dos JLabels existentes
	    for (Component component : getComponents()) {
	        if (component instanceof JLabel) {
	            JLabel label = (JLabel) component;
	            String labelText = label.getText();

	            if (labelText.contains("Entrada: R$")) {
	                label.setText("  Entrada: R$\n " + saldoEntradaMensal);
	            } else if (labelText.contains("Saída: R$")) {
	                label.setText("  Saída: R$\n " + saldoSaidaMensal);
	            } else if (labelText.contains("Lucro: R$")) {
	                label.setText("   Lucro: R$\n " + lucroMensal);
	                if (lucroMensal > 0) {
	                    label.setBackground(new Color(0, 255, 0));
	                } else if (lucroMensal < 0) {
	                    label.setBackground(new Color(255, 0, 43));
	                } else {
	                    label.setBackground(new Color(192, 192, 192));
	                }
	            }
	        }
	    }
	}
}
