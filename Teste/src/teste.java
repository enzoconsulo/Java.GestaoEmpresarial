import java.awt.BorderLayout;



import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.Panel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import java.awt.Component;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;

import net.miginfocom.swing.MigLayout;
import javax.swing.JTextArea;
import java.awt.GridLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JButton;
import java.awt.SystemColor;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JMenu;

import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JList;
import javax.swing.ImageIcon;
import javax.swing.border.Border;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.border.CompoundBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.awt.Button;

import javax.swing.JOptionPane;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.TextArea;
import java.awt.ScrollPane;
import javax.swing.UIManager;

public class teste {

	
    private JFrame frame;
    private JTabbedPane tabbedPane;
    private SaldoCaixa saldoCaixa;
    private DebitoCaixa debitoCaixa;
    private ArrayList<Transacao> extratoTransacoes;
    private ArrayList<Transacao> extratoAtualizado;
    
    private JTextArea textareaExtrato;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private Panel ABAEstoque;
    private JList<String> estoqueList;
    private DefaultListModel<String> estoqueListModel;
    
    private Estoque estoque;
    JLabel lblQuantidade;

    private Cliente cliente;
    private JList<String> clientesList;
    private DefaultListModel<String> clientesListModel;
    private JTextField nomeClienteTxtF;
    private JTextField telefoneClienteTxtF;
    private JTextField enderecoClienteTxtF;
    private JTextArea infoClienteTextArea;
    
    Map<Integer, Integer> indexMapping = new HashMap<>();
    HashMap<Integer, Integer> indexMappingClientes = new HashMap<>();
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    teste window = new teste();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public teste() {
    	saldoCaixa = new SaldoCaixa();
    	debitoCaixa = new DebitoCaixa();
    	extratoTransacoes = new ArrayList<>();
        extratoAtualizado = Transacao.carregarTransacoes();
        
        estoque = new Estoque();
        
        cliente = new Cliente();
        
        
        
        
        initialize();
        carregarExtrato();
        atualizarClientesNaLista();
        carregarDadosDoEstoque();
    }
    
    private void exibirExtratoFiltradoPorDia() {
        extratoTransacoes.clear();
        extratoTransacoes = Transacao.carregarTransacoes();

        ArrayList<Transacao> extratoFiltrado = filtrarPorDia(extratoTransacoes);

        StringBuilder extratoTexto = new StringBuilder("<html>");
        for (Transacao transacao : extratoFiltrado) {
            String corFundo = "background-color: #C0C0C0;"; // Cor padrão cinza para outros tipos de transação

            if (transacao.getTipo().equals("Adição de dívida") || transacao.getTipo().equals("Retirada")) {
                corFundo = "background-color: #FF0000;"; // Cor vermelha para Adição de dívida e Retirada
            }

            extratoTexto.append(transacao.getDescricao())
                    .append(" - ")
                    .append(transacao.getTipo())
                    .append(" - <span style='" + corFundo + "'>R$")
                    .append(transacao.getValor())
                    .append("</span><br>");
        }
        extratoTexto.append("</html>");

        JTextPane textPaneExtratoFiltrado = new JTextPane();
        textPaneExtratoFiltrado.setContentType("text/html"); // Define o tipo de conteúdo como HTML
        textPaneExtratoFiltrado.setText(extratoTexto.toString());

        JFrame frameExtratoFiltrado = new JFrame("Extrato do Dia");
        frameExtratoFiltrado.setSize(700, 450);
        frameExtratoFiltrado.getContentPane().add(new JScrollPane(textPaneExtratoFiltrado));
        frameExtratoFiltrado.setVisible(true);
    }

    private ArrayList<Transacao> filtrarPorDia(ArrayList<Transacao> extrato) {
        ArrayList<Transacao> extratoFiltrado = new ArrayList<>();
        Date hoje = new Date();
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

        for (Transacao transacao : extrato) {
            if (formatador.format(transacao.getDataHora()).equals(formatador.format(hoje))) {
                extratoFiltrado.add(transacao);
            }
        }
        return extratoFiltrado;
    }




    
    private void exibirExtratoFiltradoPorSemana() {
        extratoTransacoes.clear();
        extratoTransacoes = Transacao.carregarTransacoes();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        ArrayList<Transacao> extratoFiltrado = filtrarPorSemana(extratoTransacoes);

        StringBuilder extratoTexto = new StringBuilder("<html>");
        for (Transacao transacao : extratoFiltrado) {
            String dataFormatada = formatter.format(transacao.getDataHora());
            String corFundo = "background-color: #C0C0C0;"; // Cor padrão cinza para outros tipos de transação

            if (transacao.getTipo().equals("Adição de dívida") || transacao.getTipo().equals("Retirada")) {
                corFundo = "background-color: #FF0000;"; // Cor vermelha para Adição de dívida e Retirada
            }

            extratoTexto.append(transacao.getDescricao())
                    .append(" - ")
                    .append(transacao.getTipo())
                    .append(" - ")
                    .append(dataFormatada)
                    .append(" - <span style='" + corFundo + "'>R$")
                    .append(transacao.getValor())
                    .append("</span><br>");
        }
        extratoTexto.append("</html>");

        JTextPane textPaneExtratoFiltrado = new JTextPane();
        textPaneExtratoFiltrado.setContentType("text/html"); // Define o tipo de conteúdo como HTML
        textPaneExtratoFiltrado.setText(extratoTexto.toString());

        JFrame frameExtratoFiltrado = new JFrame("Extrato da Semana");
        frameExtratoFiltrado.setSize(700, 450);
        frameExtratoFiltrado.getContentPane().add(new JScrollPane(textPaneExtratoFiltrado));
        frameExtratoFiltrado.setVisible(true);
    }

    private ArrayList<Transacao> filtrarPorSemana(ArrayList<Transacao> extrato) {
        ArrayList<Transacao> extratoFiltrado = new ArrayList<>();
        Date dataAtual = new Date();
        Date dataInicial = new Date(dataAtual.getTime() - (7 * 24 * 60 * 60 * 1000)); // 7 dias em milissegundos

        for (Transacao transacao : extrato) {
            if (transacao.getDataHora().after(dataInicial) || transacao.getDataHora().equals(dataInicial)) {
                extratoFiltrado.add(transacao);
            }
        }
        return extratoFiltrado;
    }



    
    private void exibirExtratoFiltradoPorMes() {
        extratoTransacoes.clear();
        extratoTransacoes = Transacao.carregarTransacoes();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        ArrayList<Transacao> extratoFiltrado = filtrarPorMes(extratoTransacoes);

        StringBuilder extratoTexto = new StringBuilder("<html>");
        for (Transacao transacao : extratoFiltrado) {
            String dataFormatada = formatter.format(transacao.getDataHora());
            String corFundo = "background-color: #C0C0C0;"; // Cor padrão cinza para outros tipos de transação

            if (transacao.getTipo().equals("Adição de dívida") || transacao.getTipo().equals("Retirada")) {
                corFundo = "background-color: #FF0000;"; // Cor vermelha para Adição de dívida e Retirada
            }

            extratoTexto.append(transacao.getDescricao())
                    .append(" - ")
                    .append(transacao.getTipo())
                    .append(" - ")
                    .append(dataFormatada)
                    .append(" - <span style='" + corFundo + "'>R$")
                    .append(transacao.getValor())
                    .append("</span><br>");
        }
        extratoTexto.append("</html>");

        JTextPane textPaneExtratoFiltrado = new JTextPane();
        textPaneExtratoFiltrado.setContentType("text/html"); // Define o tipo de conteúdo como HTML
        textPaneExtratoFiltrado.setText(extratoTexto.toString());

        JFrame frameExtratoFiltrado = new JFrame("Extrato do Mês");
        frameExtratoFiltrado.setSize(700, 450);
        frameExtratoFiltrado.getContentPane().add(new JScrollPane(textPaneExtratoFiltrado));
        frameExtratoFiltrado.setVisible(true);
    }

    private ArrayList<Transacao> filtrarPorMes(ArrayList<Transacao> extrato) {
        ArrayList<Transacao> extratoFiltrado = new ArrayList<>();
        Date dataAtual = new Date();
        Date dataInicial = new Date(dataAtual.getTime() - (30L * 24L * 60L * 60L * 1000L)); // 30 dias em milissegundos

        for (Transacao transacao : extrato) {
            if (transacao.getDataHora().after(dataInicial) || transacao.getDataHora().equals(dataInicial)) {
                extratoFiltrado.add(transacao);
            }
        }
        return extratoFiltrado;
    }



    
    private void atualizarLblQuantidade(String novaQuantidade) {
        lblQuantidade.setText("Quantidade: " + novaQuantidade);
    }
    
    private void atualizarListaEstoque() {
        if (estoqueListModel != null) {
            estoqueListModel.clear();
            List<EstoqueItem> itens = estoque.getItens();
            for (EstoqueItem item : itens) {
                estoqueListModel.addElement(item.toString());
            }
        } else {
            System.err.println("estoqueListModel é nulo");
        }
    }
    
    private void carregarDadosDoEstoque() {
        if (estoqueListModel != null) {
            estoqueListModel.clear();
            List<EstoqueItem> itensCarregados = estoque.carregarEstoqueDoArquivo();
            if (itensCarregados != null) {
                for (EstoqueItem item : itensCarregados) {
                    estoqueListModel.addElement(item.toString());
                }
            }
        } else {
            System.err.println("estoqueListModel é nulo");
        }
    }

    private void atualizarExtrato() {
        StringBuilder extrato = new StringBuilder();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        for (Transacao transacao : extratoTransacoes) {
            String tipo = transacao.getTipo();
            double valor = transacao.getValor();
            String sinal = "";

            if (tipo.equals("Adição")) {
                sinal = "+";
            } else if (tipo.equals("Retirada")) {
                sinal = "-";
            }
              else if (tipo.equals("Pagamento de dívida")) {
                sinal = "+";
            } else if (tipo.equals("Adição de dívida")) {
                sinal = "-";
            }

            String dataFormatada = formatter.format(transacao.getDataHora());

            extrato.append("").append(dataFormatada)
                    .append("\n  -Tipo: ").append(tipo)
                    .append(", Valor: ").append(sinal).append(valor)
                    .append("\n   -Info: ").append(transacao.getDescricao()).append("\n");
        }

        // Define o texto do JTextArea com o extrato atualizado
        textareaExtrato.setText(extrato.toString());
    }

    private void carregarExtrato() {
        extratoTransacoes.clear(); // Limpa as transações existentes antes de carregar
        extratoTransacoes = Transacao.carregarTransacoes();
        atualizarExtrato(); // Atualiza o extrato no JTextArea
    }
    
    private void atualizarClientesNaLista() {
        clientesListModel.clear(); // Limpa o modelo da lista

        // Carrega os clientes do arquivo
        List<ClienteItem> clientesCarregados = cliente.carregarClientesDoArquivo();

        // Atualiza a lista de clientes no objeto cliente
        cliente.getClientes().clear(); // Limpa a lista de clientes existente
        cliente.getClientes().addAll(clientesCarregados);

        // Popula novamente a lista na interface grafica com os clientes carregados
        for (ClienteItem cliente : cliente.getClientes()) {
            String clienteInfo = cliente.getNome() + " - " + cliente.getTelefone() + " - " + cliente.getEndereco();
            clientesListModel.addElement(clienteInfo);
        }
    }
    
    private void atualizarEstoqueNaLista() {
        estoqueListModel.clear(); // Limpa o modelo da lista de estoque

        // Carrega os itens do estoque do arquivo (ou da fonte de dados utilizada)
        List<EstoqueItem> itensCarregados = estoque.carregarEstoqueDoArquivo();

        // Atualiza a lista de itens do estoque no objeto 'estoque'
        estoque.getItens().clear(); // Limpa a lista de itens do estoque existente
        estoque.getItens().addAll(itensCarregados);

        // Popula novamente a lista na interface gráfica com os itens carregados do estoque
        for (EstoqueItem item : estoque.getItens()) {
            String itemInfo = item.getCodigo() + " - " + item.getNome() + " - Quantidade: " + item.getQuantidade() + " - Preço: R$" + item.getPreco();
            estoqueListModel.addElement(itemInfo);
        }
    }
    
    private void displayClientInformation(ClienteItem clienteSelecionado) {
        String infoDetalhada = " Informações Pessoais do Cliente: \n      Nome: " + clienteSelecionado.getNome() +
                "\n      Endereço: " + clienteSelecionado.getEndereco() +
                "\n      Telefone:" + clienteSelecionado.getTelefone() +
                "\n\n Info Financeiras do cliente:\n      Saldo Pago: " + clienteSelecionado.getTotalPago() +
                "\n      Dívida Pendente: " + clienteSelecionado.getDividaPendente();

        infoClienteTextArea.setText(infoDetalhada);
    }

    
    private void initialize() {

        frame = new JFrame();
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(0, 0, 786, 563);
        frame.getContentPane().add(tabbedPane);
        ImageIcon iniciologo = new ImageIcon("img/casa.png");
        ImageIcon logo = new ImageIcon("img/logo.png");
        int borderRadius = 50; // Raio do arredondamento da borda
        Border roundedBorder = BorderFactory.createLineBorder(SystemColor.textHighlight, 2);
        Border emptyBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10); // Espaço interno do JLabel
        CompoundBorder compoundBorder = new CompoundBorder(roundedBorder, emptyBorder);

        
//aba inicial----------------------------------------------------------------------------------------------------------------------
        
        JPanel ABAinicio = new JPanel();
        tabbedPane.addTab("  INÍCIO    ", iniciologo, ABAinicio, null);
        ABAinicio.setLayout(null);
        
        JLabel Logo = new JLabel("ECCS Gestão Empresarial");
        Logo.setIcon(logo);
        Logo.setForeground(new Color(0, 0, 0));
        Logo.setOpaque(true);
        Logo.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 0, 255), new Color(64, 64, 64), new Color(0, 255, 255), Color.BLACK));
        Logo.setBackground(SystemColor.textHighlight);
        Logo.setFont(new Font("Bookman Old Style", Font.BOLD | Font.ITALIC, 45));
        Logo.setBounds(10, 24, 761, 113);
        ABAinicio.add(Logo);
        
        JLabel Créditos = new JLabel("  SISTEMA DE GESTÂO EMPRESARIAL - Puc Campinas, PI: DESENVOLVIMENTO DE SISTEMAS ORIENTADOS A OBJETOS/ Projeto Final (2023)");
        Créditos.setFont(new Font("Segoe UI Emoji", Font.ITALIC, 10));
        Créditos.setBounds(63, 498, 761, 28);
        ABAinicio.add(Créditos);
        
        JLabel lblNewLabel_18 = new JLabel("Enzo Cesar Consulo Silva - 22024541");
        lblNewLabel_18.setFont(new Font("Segoe UI", Font.BOLD | Font.ITALIC, 22));
        lblNewLabel_18.setBounds(189, 215, 387, 39);
        ABAinicio.add(lblNewLabel_18);
        
        
        JPanel ABAFinanceiroContabilidade = new JPanel();
        ImageIcon caixa = new ImageIcon("img/caixa.png");
        tabbedPane.addTab("  Financeiro e Contabilidade    ", caixa, ABAFinanceiroContabilidade, null);
        ABAFinanceiroContabilidade.setLayout(null);
        
        JLabel SaldoCaixa = new JLabel("Saldo do Caixa: R$" + saldoCaixa.getsaldo());
        SaldoCaixa.setForeground(new Color(0, 0, 0));
        SaldoCaixa.setBackground(new Color(240, 240, 240));
        SaldoCaixa.setFont(new Font("Microsoft JhengHei Light", Font.BOLD, 18));
        SaldoCaixa.setBounds(10, 10, 296, 63);
        ABAFinanceiroContabilidade.add(SaldoCaixa);
        
        JLabel DebitosCaixa = new JLabel("Débitos do Caixa: R$-" + debitoCaixa.getDebito());
        DebitosCaixa.setForeground(new Color(0, 0, 0));
        DebitosCaixa.setBackground(new Color(255, 192, 203));
        DebitosCaixa.setFont(new Font("Microsoft JhengHei Light", Font.BOLD, 18));
        DebitosCaixa.setBounds(390, 10, 355, 63);
        ABAFinanceiroContabilidade.add(DebitosCaixa);
        
        textareaExtrato = new JTextArea();
        textareaExtrato.setEditable(false);
        textareaExtrato.setLineWrap(true);
        textareaExtrato.setBorder(new LineBorder(new Color(128,128,128), 3));
        textareaExtrato.setBounds(22, 146, 203, 380);
        ABAFinanceiroContabilidade.add(textareaExtrato);
        
        JScrollPane scrollPaneExtrato = new JScrollPane(textareaExtrato);
        scrollPaneExtrato.setBounds(10, 100, 296, 426); // Define os limites do JScrollPane
        scrollPaneExtrato.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Adiciona barra de rolagem vertical
        ABAFinanceiroContabilidade.add(scrollPaneExtrato);
        
        JLabel ExtratoCaixa = new JLabel("       Extrato do Caixa:");
        ExtratoCaixa.setOpaque(true);
        ExtratoCaixa.setFont(new Font("Tahoma", Font.BOLD, 12));
        ExtratoCaixa.setBackground(new Color(128, 128, 128));
        ExtratoCaixa.setBounds(10, 83, 190, 20);
        ABAFinanceiroContabilidade.add(ExtratoCaixa);
        
        
//sub-abaganhos------------------
        
        
        JTabbedPane ABA2Ganhos = new JTabbedPane(JTabbedPane.TOP);
        ABA2Ganhos.setBackground(new Color(192, 192, 192));
        ABA2Ganhos.setBounds(326, 114, 445, 284);
        ABAFinanceiroContabilidade.add(ABA2Ganhos);

        GanhosTotalPanel ganhosTotalPanel = new GanhosTotalPanel(extratoAtualizado);
        ABA2Ganhos.addTab("Ganho Total", new GanhosTotalPanel(extratoAtualizado));
        ganhosTotalPanel.setLayout(null);

        GanhosMensaisPanel ganhosMensaisPanel = new GanhosMensaisPanel(extratoAtualizado);
        GanhosMensaisPanel ganhosMensaisPanel_1 = new GanhosMensaisPanel(extratoAtualizado);
        ABA2Ganhos.addTab("Ganhos Mensais", ganhosMensaisPanel_1);
        
        JButton btnNewButton_2 = new JButton("Extrato Filtrado");
        btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnNewButton_2.setBounds(291, 57, 139, 65);
        btnNewButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	exibirExtratoFiltradoPorMes();
            }
        });
        ganhosMensaisPanel_1.add(btnNewButton_2);
        ganhosMensaisPanel.setLayout(null);

        GanhosSemanaisPanel ganhosSemanaisPanel = new GanhosSemanaisPanel(extratoAtualizado);
        GanhosSemanaisPanel ganhosSemanaisPanel_1 = new GanhosSemanaisPanel(extratoAtualizado);
        ABA2Ganhos.addTab("Ganhos Semanais", ganhosSemanaisPanel_1);
        
        JButton btnNewButton_3 = new JButton("Extrato Filtrado");
        btnNewButton_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnNewButton_3.setBounds(291, 57, 139, 65);
        btnNewButton_3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	exibirExtratoFiltradoPorSemana();
            }
        });
        ganhosSemanaisPanel_1.add(btnNewButton_3);
        ganhosSemanaisPanel.setLayout(null);
        
        GanhosDiariosPanel ganhosDiariosPanel = new GanhosDiariosPanel(extratoAtualizado);
        ABA2Ganhos.addTab("Ganho Diários", ganhosDiariosPanel);
        
        JButton btnNewButton_1 = new JButton("Extrato Filtrado");
        btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnNewButton_1.setBounds(291, 57, 139, 65);
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exibirExtratoFiltradoPorDia();
            }
        });
        ganhosDiariosPanel.add(btnNewButton_1);
        ganhosDiariosPanel.setLayout(null);

        //Botao para adicionar saldo.
        JButton btnAdicionarSaldo = new JButton("Adicionar Saldo");
        btnAdicionarSaldo.setBounds(326, 425, 157, 77);
        btnAdicionarSaldo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame framePopup = new JFrame("Adicionar Saldo");
                framePopup.setBounds(100, 100, 300, 200);
                framePopup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                
                JPanel panel = new JPanel();
                framePopup.getContentPane().add(panel, BorderLayout.CENTER);
                panel.setLayout(new BorderLayout(0, 0));
                
                JLabel lblNewLabel = new JLabel("Valor a Adicionar:");
                panel.add(lblNewLabel, BorderLayout.CENTER);
                
                JTextField textFieldAdicionarSaldo = new JTextField();
                panel.add(textFieldAdicionarSaldo, BorderLayout.CENTER);
                
                JButton btnConfirmar = new JButton("Confirmar");
                btnConfirmar.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String saldoStr = textFieldAdicionarSaldo.getText();
                        if (!saldoStr.isEmpty()) {
                            double valor = Double.parseDouble(saldoStr);
                            double saldoAtual = saldoCaixa.getsaldo();
                            saldoCaixa.setSaldo(saldoAtual + valor); 
                            SaldoCaixa.setText("Saldo do Caixa: R$" + saldoCaixa.getsaldo()); 
                            
                            String descricao = JOptionPane.showInputDialog(framePopup, "Insira uma descrição para a transação:");
                            
                            Transacao transacao = new Transacao(descricao, valor, "Adição", new Date());
                            extratoTransacoes.add(transacao);
                            
                            atualizarExtrato();

                            framePopup.dispose(); 
                        }
                    }
                });
                panel.add(btnConfirmar, BorderLayout.SOUTH);
                framePopup.setVisible(true);
            }
        });
        ABAFinanceiroContabilidade.add(btnAdicionarSaldo);

        // Botao para retirar saldo.
        JButton btnRetirarSaldo = new JButton("Retirar Saldo");
        btnRetirarSaldo.setBounds(502, 425, 148, 77);
        btnRetirarSaldo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame framePopup = new JFrame("Retirar Saldo");
                framePopup.setBounds(100, 100, 300, 200);
                framePopup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                JPanel panel = new JPanel();
                framePopup.getContentPane().add(panel, BorderLayout.CENTER);
                panel.setLayout(new BorderLayout(0, 0));

                JLabel lblNewLabel = new JLabel("Valor a Retirar:");
                panel.add(lblNewLabel, BorderLayout.NORTH);

                JTextField textFieldRetirarSaldo = new JTextField();
                panel.add(textFieldRetirarSaldo, BorderLayout.CENTER);

                JButton btnConfirmar = new JButton("Confirmar");
                btnConfirmar.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String saldoStr = textFieldRetirarSaldo.getText();
                        if (!saldoStr.isEmpty()) {
                            double valor = Double.parseDouble(saldoStr);
                            double saldoAtual = saldoCaixa.getsaldo();
                            if (valor <= saldoAtual) {
                                double novoSaldo = saldoAtual - valor;
                                saldoCaixa.setSaldo(novoSaldo);
                                debitoCaixa.setDebito(debitoCaixa.getDebito() + valor);
                                SaldoCaixa.setText("Saldo do Caixa: R$" + saldoCaixa.getsaldo());
                                DebitosCaixa.setText("Débitos do Caixa: R$-" + debitoCaixa.getDebito());
                                
                                String descricao = JOptionPane.showInputDialog(framePopup, "Insira uma descrição para a transação:");
                                
                                Transacao transacao = new Transacao(descricao, valor, "Retirada", new Date());
                                extratoTransacoes.add(transacao);
                                
                                atualizarExtrato();
                                framePopup.dispose();
                            } else {
                                JOptionPane.showMessageDialog(null, "Saldo insuficiente!");
                            }
                        }
                    }
                });
                panel.add(btnConfirmar, BorderLayout.SOUTH);

                framePopup.setVisible(true);
            }
        });
        ABAFinanceiroContabilidade.add(btnRetirarSaldo);

        JButton btnEditarSaldo = new JButton("Editar \n Saldo");
        btnEditarSaldo.setFont(new Font("Tahoma", Font.BOLD, 9));
        btnEditarSaldo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame framePopup = new JFrame("Editar Saldo");
                framePopup.setBounds(100, 100, 300, 200);
                framePopup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                
                JPanel panel = new JPanel();
                framePopup.getContentPane().add(panel, BorderLayout.CENTER);
                panel.setLayout(new BorderLayout(0, 0));
                
                JLabel lblNewLabel = new JLabel("Novo Saldo:");
                panel.add(lblNewLabel, BorderLayout.NORTH);
                
                JTextField textFieldNovoSaldo = new JTextField();
                panel.add(textFieldNovoSaldo, BorderLayout.CENTER);
                
                JButton btnConfirmar = new JButton("Confirmar");
                btnConfirmar.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String novoSaldoStr = textFieldNovoSaldo.getText();
                        if (!novoSaldoStr.isEmpty()) {
                            double novoSaldo = Double.parseDouble(novoSaldoStr);
                            saldoCaixa.setSaldo(novoSaldo); 
                            SaldoCaixa.setText("Saldo do Caixa: R$" + saldoCaixa.getsaldo()); 
                            framePopup.dispose(); 
                        }
                    }
                });
                panel.add(btnConfirmar, BorderLayout.SOUTH);
                
                framePopup.setVisible(true);
            }
        });
        btnEditarSaldo.setBounds(672, 423, 97, 44);
        ABAFinanceiroContabilidade.add(btnEditarSaldo);
        
        JButton btnEditarDebito = new JButton("Editar \n Débito");
        btnEditarDebito.setFont(new Font("Tahoma", Font.BOLD, 9));
        btnEditarDebito.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame framePopupDebito = new JFrame("Editar Débito");
                framePopupDebito.setBounds(100, 100, 300, 200);
                framePopupDebito.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                
                JPanel panelDebito = new JPanel();
                framePopupDebito.getContentPane().add(panelDebito, BorderLayout.CENTER);
                panelDebito.setLayout(new BorderLayout(0, 0));
                
                JLabel lblNewLabelDebito = new JLabel("Novo Débito:");
                panelDebito.add(lblNewLabelDebito, BorderLayout.NORTH);
                
                JTextField textFieldNovoDebito = new JTextField();
                panelDebito.add(textFieldNovoDebito, BorderLayout.CENTER);
                
                JButton btnConfirmarDebito = new JButton("Confirmar");
                btnConfirmarDebito.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String novoDebitoStr = textFieldNovoDebito.getText();
                        if (!novoDebitoStr.isEmpty()) {
                            double novoDebito = Double.parseDouble(novoDebitoStr);
                            debitoCaixa.setDebito(novoDebito); // Atualiza o débito na classe DebitoCaixa
                            DebitosCaixa.setText("Débitos do Caixa: R$-" + debitoCaixa.getDebito()); // Atualiza o JLabel na aba
                            framePopupDebito.dispose(); // Fecha o pop-up após confirmar
                        }
                    }
                });
                panelDebito.add(btnConfirmarDebito, BorderLayout.SOUTH);
                
                framePopupDebito.setVisible(true);
            }
        });
        btnEditarDebito.setBounds(672, 470, 97, 44);
        ABAFinanceiroContabilidade.add(btnEditarDebito);
        
        JLabel labelAzul = new JLabel("");
        labelAzul.setOpaque(true);
        labelAzul.setBackground(new Color(176, 224, 230));
        labelAzul.setBounds(10, 10, 355, 63);
        ABAFinanceiroContabilidade.add(labelAzul);
        
        JLabel labelRosa = new JLabel("");
        labelRosa.setOpaque(true);
        labelRosa.setBackground(new Color(255, 182, 193));
        labelRosa.setBounds(390, 11, 355, 62);
        ABAFinanceiroContabilidade.add(labelRosa);
        
//aba clientes----------------------------------------------------------------------------------------------------------------------
        
        JPanel ABAProjetosRH = new JPanel();
        ImageIcon projetosrh = new ImageIcon("img/projetosrh.png");
        tabbedPane.addTab("  Clientes    ", projetosrh, ABAProjetosRH, null);
        ABAProjetosRH.setLayout(null);

        JLabel lblNewLabel_10 = new JLabel("Cadastrar Cliente:");
        ImageIcon clientesicon = new ImageIcon("img/ClienteIcon.png");
        lblNewLabel_10.setIcon(clientesicon);
        lblNewLabel_10.setFont(new Font("Monospaced", Font.BOLD, 21));
        lblNewLabel_10.setBounds(373, 22, 286, 37);
        ABAProjetosRH.add(lblNewLabel_10);

        JLabel lblNewLabel_11 = new JLabel("Nome do Cliente:");
        lblNewLabel_11.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNewLabel_11.setBounds(389, 81, 129, 19);
        ABAProjetosRH.add(lblNewLabel_11);

        JLabel lblNewLabel_12 = new JLabel("Telefone de Contato:");
        lblNewLabel_12.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNewLabel_12.setBounds(373, 132, 145, 19);
        ABAProjetosRH.add(lblNewLabel_12);

        JLabel lblNewLabel_13 = new JLabel("Endereço:");
        lblNewLabel_13.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNewLabel_13.setBounds(438, 184, 143, 19);
        ABAProjetosRH.add(lblNewLabel_13);

        nomeClienteTxtF = new JTextField();
        nomeClienteTxtF.setBounds(507, 80, 150, 25);
        ABAProjetosRH.add(nomeClienteTxtF);

        telefoneClienteTxtF = new JTextField();
        telefoneClienteTxtF.setBounds(507, 131, 150, 25);
        ABAProjetosRH.add(telefoneClienteTxtF);

        enderecoClienteTxtF = new JTextField();
        enderecoClienteTxtF.setBounds(507, 183, 150, 25);
        ABAProjetosRH.add(enderecoClienteTxtF);

        JTextField searchBarClientes = new JTextField();
        searchBarClientes.setBounds(79, 35, 272, 20);
        ABAProjetosRH.add(searchBarClientes);


        clientesListModel = new DefaultListModel<>();
        clientesList = new JList<>(clientesListModel);

        JScrollPane scrollPane = new JScrollPane(clientesList);
        scrollPane.setBounds(10, 61, 341, 453);
        ABAProjetosRH.add(scrollPane);

        infoClienteTextArea = new JTextArea();
        infoClienteTextArea.setEditable(false);
        infoClienteTextArea.setLineWrap(true);
        infoClienteTextArea.setWrapStyleWord(true);
        infoClienteTextArea.setBounds(390, 330, 244, 184);
        ABAProjetosRH.add(infoClienteTextArea);

        // Lógica para filtrar os clientes conforme o texto digitado na barra de pesquisa
        searchBarClientes.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterClientes();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterClientes();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterClientes();
            }

            private void filterClientes() {
                String searchText = searchBarClientes.getText().toLowerCase();
                DefaultListModel<String> filteredClientesModel = new DefaultListModel<>();

                indexMappingClientes.clear();

                List<ClienteItem> clientes = cliente.getClientes();

                int filteredIndex = 0;

                for (int i = 0; i < clientes.size(); i++) {
                    ClienteItem cliente = clientes.get(i);

                    if (cliente.getNome().toLowerCase().contains(searchText)) {
                        String clienteInfo = cliente.getNome() + " - " + cliente.getTelefone() + " - " + cliente.getEndereco();
                        filteredClientesModel.addElement(clienteInfo);

                        indexMappingClientes.put(filteredIndex, i);
                        filteredIndex++;
                    }
                }

                clientesListModel.clear();
                for (int i = 0; i < filteredClientesModel.size(); i++) {
                    clientesListModel.addElement(filteredClientesModel.getElementAt(i));
                }
            }
        });

        clientesList.addListSelectionListener(e -> {
            int selectedClientIndex = clientesList.getSelectedIndex();

            if (selectedClientIndex != -1 && indexMappingClientes.containsKey(selectedClientIndex)) {
                int originalIndex = indexMappingClientes.get(selectedClientIndex);
                ClienteItem clienteSelecionado = cliente.getClientes().get(originalIndex);
                displayClientInformation(clienteSelecionado);
            }
        });

        clientesList.addListSelectionListener(e -> {
            int selectedClientIndex = clientesList.getSelectedIndex();

            if (selectedClientIndex != -1 && indexMappingClientes.containsKey(selectedClientIndex)) {
                int originalIndex = indexMappingClientes.get(selectedClientIndex);
                ClienteItem clienteSelecionado = cliente.getClientes().get(originalIndex);
                displayClientInformation(clienteSelecionado);
            }
        });

        
        


        


        JButton adicionarClienteBtn = new JButton("Adicionar Cliente");
        adicionarClienteBtn.setBounds(597, 243, 150, 45);
        ABAProjetosRH.add(adicionarClienteBtn);
        
        JButton btnAdicionarDivida = new JButton("Adcionar Divida");
        btnAdicionarDivida.setFont(new Font("Tahoma", Font.BOLD, 10));
        btnAdicionarDivida.setBounds(656, 372, 127, 68);
        ABAProjetosRH.add(btnAdicionarDivida);
        
        JButton btnPagarDivida = new JButton("Pagar Divida");
        btnPagarDivida.setFont(new Font("Tahoma", Font.BOLD, 10));
        btnPagarDivida.setBounds(656, 445, 125, 68);
        ABAProjetosRH.add(btnPagarDivida);
        
        JLabel lblNewLabel_14 = new JLabel("");
        
        lblNewLabel_14.setBackground(SystemColor.activeCaption);
        lblNewLabel_14.setOpaque(true);
        lblNewLabel_14.setBounds(361, 21, 410, 292);
        ABAProjetosRH.add(lblNewLabel_14);
        
        JLabel lblNewLabel_16 = new JLabel("  Cliente Selecionado:");
        lblNewLabel_16.setOpaque(true);
        lblNewLabel_16.setForeground(Color.WHITE);
        lblNewLabel_16.setBackground(Color.DARK_GRAY);
        lblNewLabel_16.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblNewLabel_16.setBounds(371, 317, 158, 13);
        ABAProjetosRH.add(lblNewLabel_16);
        
        JLabel lblNewLabel_15 = new JLabel("");
        lblNewLabel_15.setOpaque(true);
        lblNewLabel_15.setBackground(Color.DARK_GRAY);
        lblNewLabel_15.setBounds(373, 321, 276, 205);
        ABAProjetosRH.add(lblNewLabel_15);
        
        JLabel lblNewLabel_22 = new JLabel(" Pesquisar:");
        lblNewLabel_22.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNewLabel_22.setBounds(10, 38, 71, 13);
        ABAProjetosRH.add(lblNewLabel_22);
        
        JLabel lblNewLabel_23 = new JLabel("    Clientes:");
        lblNewLabel_23.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblNewLabel_23.setBounds(123, 10, 112, 24);
        ABAProjetosRH.add(lblNewLabel_23);
        
        JLabel lblNewLabel_17 = new JLabel("");
        lblNewLabel_17.setOpaque(true);
        lblNewLabel_17.setBackground(SystemColor.inactiveCaption);
        lblNewLabel_17.setBounds(5, 10, 353, 511);
        ABAProjetosRH.add(lblNewLabel_17);

        adicionarClienteBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nome = nomeClienteTxtF.getText();
                String telefone = telefoneClienteTxtF.getText();
                String endereco = enderecoClienteTxtF.getText();

                if (!nome.isEmpty() && !telefone.isEmpty() && !endereco.isEmpty()) {
                    // Adicionar o cliente ao modelo da lista de clientes
                    String novoCliente = nome + " - " + telefone + " - " + endereco;
                    clientesListModel.addElement(novoCliente);

                    // Adicionar o cliente ao arquivo com saldo e dívida zerados
                    cliente.adicionarCliente(nome, endereco, telefone, 0.0, 0.0);

                    // Limpar os campos após adicionar o cliente
                    nomeClienteTxtF.setText("");
                    telefoneClienteTxtF.setText("");
                    enderecoClienteTxtF.setText("");
                } else {
                    JOptionPane.showMessageDialog(frame, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        btnAdicionarDivida.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!clientesList.isSelectionEmpty()) {
                    int index = clientesList.getSelectedIndex();
                    List<ClienteItem> clientes = cliente.getClientes();

                    ClienteItem clienteSelecionado = clientes.get(index);

                    String valorDivida = JOptionPane.showInputDialog(frame, "Digite o valor a ser adicionado à dívida:");

                    try {
                        double valor = Double.parseDouble(valorDivida);
                        if (valor >= 0) {
                            clienteSelecionado.setDividaPendente(clienteSelecionado.getDividaPendente() + valor);
                            cliente.salvarClientesNoArquivo();
                            atualizarClientesNaLista();

                            // Atualizar o débito do caixa
                            debitoCaixa.setDebito(debitoCaixa.getDebito() + valor);
                            DebitosCaixa.setText("Débitos do Caixa: R$-" + debitoCaixa.getDebito());

                            // Adicionar ao extrato
                            String descricao = "Adição de dívida (" + clienteSelecionado.getNome() + ")";
                            Transacao transacao = new Transacao(descricao, valor, "Adição de dívida", new Date());
                            extratoTransacoes.add(transacao);
                            atualizarExtrato();
                        } else {
                            JOptionPane.showMessageDialog(frame, "O valor deve ser maior ou igual a zero.", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Insira um valor numérico válido.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Selecione um cliente para adicionar a dívida.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        
        btnPagarDivida.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!clientesList.isSelectionEmpty()) {
                    int index = clientesList.getSelectedIndex();
                    List<ClienteItem> clientes = cliente.getClientes();

                    ClienteItem clienteSelecionado = clientes.get(index);

                    String valorPagamento = JOptionPane.showInputDialog(frame, "Digite o valor a ser pago:");

                    try {
                        double valor = Double.parseDouble(valorPagamento);
                        if (valor >= 0) {
                            double dividaPendente = clienteSelecionado.getDividaPendente();
                            if (valor <= dividaPendente) {
                                clienteSelecionado.setDividaPendente(dividaPendente - valor);
                                clienteSelecionado.setTotalPago(clienteSelecionado.getTotalPago() + valor);
                                cliente.salvarClientesNoArquivo();
                                atualizarClientesNaLista();

                                // Atualizar o saldo do caixa
                                saldoCaixa.setSaldo(saldoCaixa.getsaldo() + valor);
                                SaldoCaixa.setText("Saldo do Caixa: R$" + saldoCaixa.getsaldo());

                                // Adicionar ao extrato
                                String descricao = "Pagamento de dívida (" + clienteSelecionado.getNome() + ")";
                                Transacao transacao = new Transacao(descricao, valor, "Pagamento de dívida", new Date());
                                extratoTransacoes.add(transacao);
                                atualizarExtrato();
                            } else {
                                JOptionPane.showMessageDialog(frame, "O valor excede a dívida pendente.", "Erro", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(frame, "O valor deve ser maior ou igual a zero.", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Insira um valor numérico válido.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Selecione um cliente para pagar a dívida.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        
        
        
//aba estoque----------------------------------------------------------------------------------------------------------------------
        
        
        ABAEstoque = new Panel();
        ImageIcon estoqueIcon = new ImageIcon("img/estoque.png");
        tabbedPane.addTab("  Estoque    ", estoqueIcon, ABAEstoque, null);
        ABAEstoque.setBackground(new Color(240, 240, 240));
        ABAEstoque.setLayout(null);

        textField_3 = new JTextField();
        textField_3.setBounds(30, 442, 120, 19);
        ABAEstoque.add(textField_3);
        textField_3.setColumns(10);

        JLabel lblNewLabel_6 = new JLabel("Código do Item:");
        lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNewLabel_6.setBounds(30, 415, 103, 19);
        ABAEstoque.add(lblNewLabel_6);
        

        estoqueListModel = new DefaultListModel<>();
        estoqueList = new JList<>(estoqueListModel);
        estoqueList.setBorder(new LineBorder(new Color(128, 128, 128), 5, true));
        estoqueList.setBounds(10, 36, 336, 258);
        ABAEstoque.add(estoqueList);
        
        
        JTextField searchBar = new JTextField();
        searchBar.setBounds(145, 39, 272, 20);
        ABAEstoque.add(searchBar);

        // Adicione um DocumentListener ao JTextField para monitorar as mudanças no texto
        searchBar.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterItems();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterItems();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterItems();
            }

            private void filterItems() {
                String searchText = searchBar.getText().toLowerCase();
                DefaultListModel<String> filteredModel = new DefaultListModel<>();
                
                indexMapping.clear(); // Limpa o mapeamento atual

                for (int i = 0; i < estoqueListModel.getSize(); i++) {
                    String item = estoqueListModel.getElementAt(i);
                    if (item.toLowerCase().contains(searchText)) {
                        filteredModel.addElement(item);
                        indexMapping.put(filteredModel.getSize() - 1, i); // Atualiza o mapeamento
                    }
                }

                estoqueList.setModel(filteredModel);
            }
        });

        JScrollPane scrollPane1 = new JScrollPane(estoqueList); // Cria um JScrollPane com a JList
        scrollPane1.setBounds(10, 60, 419, 293); // Define o tamanho e posição do JScrollPane
        ABAEstoque.add(scrollPane1);
        
        Map<Integer, Integer> indexMapping = new HashMap<>();
        
        Panel panelitem = new Panel();
        panelitem.setBackground(UIManager.getColor("Button.light"));      
        panelitem.setBounds(457, 36, 313, 251);
        ABAEstoque.add(panelitem);
        panelitem.setLayout(null);
        estoqueList.addListSelectionListener(e -> {
            if (!estoqueList.isSelectionEmpty()) {
                int selectedStockIndex = estoqueList.getSelectedIndex(); // Obtém o índice na lista filtrada
                Integer originalIndex = indexMapping.get(selectedStockIndex); // Obtém o índice correspondente na lista original

                String itemSelecionado = estoqueList.getSelectedValue();
                if (itemSelecionado != null) {
                    String[] partes = itemSelecionado.split(" - ");
                    if (partes.length >= 4) {
                        String codigo = partes[0];
                        String nome = partes[1];
                        String quantidade = partes[2].replace("Quantidade: ", "");
                        String precoStr = partes[3].replace("Preço: R$", "");

                        panelitem.removeAll(); // Remove componentes anteriores do panelitem

                        JLabel lblCodigo = new JLabel("Código: " + codigo);
                        lblCodigo.setBounds(35, 15, 200, 20);
                        panelitem.add(lblCodigo);

                        JLabel lblNome = new JLabel("Nome: " + nome);
                        lblNome.setBounds(35, 45, 200, 20);
                        panelitem.add(lblNome);

                        lblQuantidade = new JLabel("Quantidade: " + quantidade);
                        lblQuantidade.setBounds(35, 75, 200, 20);
                        panelitem.add(lblQuantidade);

                        JLabel lblPreco = new JLabel("Preço: R$" + precoStr);
                        lblPreco.setBounds(35, 105, 200, 20);
                        panelitem.add(lblPreco);

                        JButton btnEditarQuantidade = new JButton("Editar Quantidade");
                        btnEditarQuantidade.setBounds(35, 135, 250, 30);
                        btnEditarQuantidade.addActionListener(evt -> {
                            if (!estoqueList.isSelectionEmpty()) {
                            	
                                String itemSelecionado1 = estoqueList.getSelectedValue();

                                if (itemSelecionado1 != null && selectedStockIndex != -1) {
                                    String quantidadeAtualStr = itemSelecionado1.split(" - ")[2].replace("Quantidade: ", "");
                                    int quantidadeAtual = Integer.parseInt(quantidadeAtualStr);

                                    String novaQuantidadeStr = JOptionPane.showInputDialog(frame, "Nova Quantidade:", quantidadeAtual);

                                    if (novaQuantidadeStr != null && !novaQuantidadeStr.isEmpty()) {
                                        try {
                                            int novaQuantidade = Integer.parseInt(novaQuantidadeStr);
                                            if (novaQuantidade != quantidadeAtual) {
                                                // Atualiza a quantidade do item selecionado no estoque
                                                EstoqueItem item = estoque.getItens().get(selectedStockIndex);
                                                item.setQuantidade(String.valueOf(novaQuantidade)); // Define a nova quantidade
                                                estoque.salvarEstoqueEmArquivo(); // Salva as alterações no arquivo
                                                atualizarListaEstoque(); // Atualiza a lista de itens no estoque na interface
                                                atualizarLblQuantidade(String.valueOf(novaQuantidade));
                                            }
                                        } catch (NumberFormatException ex) {
                                            JOptionPane.showMessageDialog(frame, "Digite um valor numérico válido!", "Erro", JOptionPane.ERROR_MESSAGE);
                                        }
                                    }
                                }
                            }
                        });

                        panelitem.add(btnEditarQuantidade);
                        panelitem.revalidate();
                        btnEditarQuantidade.requestFocusInWindow();

                        JButton btnRemoverItem = new JButton("Remover Item");
                        btnRemoverItem.setBounds(35, 175, 250, 30);
                        btnRemoverItem.addActionListener(evt -> {
                            if (!estoqueList.isSelectionEmpty()) {
                            	
                                EstoqueItem itemSelecionado1 = estoque.getItens().get(selectedStockIndex);

                                // Remove o item do estoque
                                estoque.getItens().remove(selectedStockIndex);
                                estoque.salvarEstoqueEmArquivo(); // Salva as alterações no arquivo
                                atualizarListaEstoque(); // Atualiza a lista de itens no estoque na interface

                                // Exibe uma mensagem informando que o item foi removido com sucesso
                                JOptionPane.showMessageDialog(frame, "Item removido com sucesso!", "Remoção de Item", JOptionPane.INFORMATION_MESSAGE);

                                // Limpa os componentes do panelitem
                                panelitem.removeAll();
                                panelitem.revalidate();
                                panelitem.repaint();
                            }
                        });

                        panelitem.add(btnRemoverItem);
                        panelitem.revalidate();
                        btnRemoverItem.requestFocusInWindow();

                        
                        
                        JButton btnVenda = new JButton("Venda");
                        btnVenda.setBounds(35, 215, 250, 30);
                        btnVenda.addActionListener(evt -> {
                            if (!estoqueList.isSelectionEmpty()) {
                                
                                EstoqueItem itemSelecionado1 = estoque.getItens().get(selectedStockIndex);

                                String quantidadeAtualStr = itemSelecionado1.getQuantidade();
                                int quantidadeAtual = Integer.parseInt(quantidadeAtualStr);

                                String quantidadeVendaStr = JOptionPane.showInputDialog(frame, "Quantidade para venda:", quantidadeAtual);
                                
                                if (quantidadeVendaStr != null && !quantidadeVendaStr.isEmpty()) {
                                    try {
                                        int quantidadeVenda = Integer.parseInt(quantidadeVendaStr);
                                        double precoItem = itemSelecionado1.getPreco();

                                        // Cria um painel com um botão de alternar desconto em porcentagem ou valor bruto
                                        JPanel panel = new JPanel();
                                        JRadioButton percentRadioButton = new JRadioButton("Porcentagem");
                                        JRadioButton valueRadioButton = new JRadioButton("Valor Bruto");
                                        ButtonGroup group = new ButtonGroup();
                                        group.add(percentRadioButton);
                                        group.add(valueRadioButton);
                                        panel.add(percentRadioButton);
                                        panel.add(valueRadioButton);
                                        int option = JOptionPane.showOptionDialog(frame, panel, "Selecione o tipo de desconto",
                                                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

                                        if (option == JOptionPane.OK_OPTION) {
                                            double desconto = 0.0;

                                            if (percentRadioButton.isSelected()) {
                                                String descontoStr = JOptionPane.showInputDialog(frame, "Desconto (%):");
                                                if (descontoStr != null && !descontoStr.isEmpty()) {
                                                    desconto = Double.parseDouble(descontoStr);
                                                }
                                            } else if (valueRadioButton.isSelected()) {
                                                String descontoStr = JOptionPane.showInputDialog(frame, "Desconto (R$):");
                                                if (descontoStr != null && !descontoStr.isEmpty()) {
                                                    desconto = Double.parseDouble(descontoStr) / precoItem * 100; // Utilização da variável precoItem declarada acima
                                                }
                                            }

                                            // Calcula o preço total com base no desconto escolhido
                                            double precoTotal = precoItem * quantidadeVenda * (1 - (desconto / 100));
                                            precoTotal = Math.round(precoTotal * 100.0) / 100.0;

                                            int confirmacao = JOptionPane.showConfirmDialog(frame,
                                                    "Realizar a venda de " + quantidadeVenda + " unidades por R$" + precoTotal + "?",
                                                    "Confirmar Venda", JOptionPane.YES_NO_OPTION);

                                            if (confirmacao == JOptionPane.YES_OPTION) {
                                            	
                                                int novaQuantidade = quantidadeAtual - quantidadeVenda;
                                                itemSelecionado1.setQuantidade(String.valueOf(novaQuantidade));

                                                estoque.salvarEstoqueEmArquivo();

                                                atualizarListaEstoque();

                                                String descricaoVenda = "Venda de " + quantidadeVenda + " unidades do item " + itemSelecionado1.getNome();
                                                Transacao transacaoVenda = new Transacao(descricaoVenda, precoTotal, "Venda", new Date());
                                                extratoTransacoes.add(transacaoVenda);

                                                double saldoAtual = saldoCaixa.getsaldo();
                                                saldoCaixa.setSaldo(saldoAtual + precoTotal);

                                                saldoCaixa.salvarSaldoEmArquivo(saldoCaixa.getsaldo());

                                                Transacao.salvarTransacoes(extratoTransacoes);

                                                atualizarExtrato();

                                                SaldoCaixa.setText("Saldo do Caixa: R$" + saldoCaixa.getsaldo());
                                                
                                                ganhosTotalPanel.atualizarValoresPainel(extratoAtualizado);
                                                ganhosMensaisPanel.atualizarValoresPainel(extratoAtualizado);
                                                ganhosSemanaisPanel.atualizarValoresPainel(extratoAtualizado);
                                                ganhosDiariosPanel.atualizarValoresPainel(extratoAtualizado);
                                                
                                                atualizarLblQuantidade(String.valueOf(novaQuantidade));

                                                // Exibir mensagem de venda realizada com sucesso
                                                JOptionPane.showMessageDialog(frame, "Venda realizada com sucesso!", "Venda Realizada", JOptionPane.INFORMATION_MESSAGE);
                                            }
                                        }
                                    } catch (NumberFormatException ex) {
                                        JOptionPane.showMessageDialog(frame, "Digite valores numéricos válidos!", "Erro", JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                            }
                        });

                        panelitem.add(btnVenda);
                        panelitem.revalidate();
                        panelitem.repaint();
                        btnVenda.requestFocusInWindow();

                    }
                }
            }
        });
        
        
        JLabel lblNewLabel_1 = new JLabel(" Itens no Estoque:  ");
        ImageIcon logoitens = new ImageIcon("img/itens.png");
        lblNewLabel_1.setIcon(logoitens);
        lblNewLabel_1.setForeground(new Color(25, 25, 112));
        lblNewLabel_1.setBackground(new Color(128, 128, 128));
        lblNewLabel_1.setOpaque(true);
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblNewLabel_1.setBounds(10, 5, 219, 30);
        ABAEstoque.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Adicionar Item:");
        ImageIcon adicionaritemicon = new ImageIcon("img/AdicionarItemIcon.png");
        lblNewLabel_2.setIcon(adicionaritemicon);
        lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblNewLabel_2.setBounds(30, 363, 176, 42);
        ABAEstoque.add(lblNewLabel_2);

        textField_1 = new JTextField();
        textField_1.setBounds(173, 442, 120, 19);
        ABAEstoque.add(textField_1);
        textField_1.setColumns(10);

        JLabel lblNewLabel_3 = new JLabel("Nome do Item:");
        lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNewLabel_3.setBounds(173, 418, 96, 13);
        ABAEstoque.add(lblNewLabel_3);

        JLabel lblNewLabel_4 = new JLabel("Quantidade:");
        lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNewLabel_4.setBounds(316, 418, 87, 13);
        ABAEstoque.add(lblNewLabel_4);

        textField_2 = new JTextField();
        textField_2.setBounds(316, 442, 120, 19);
        ABAEstoque.add(textField_2);
        textField_2.setColumns(10);

        JLabel lblNewLabel_7 = new JLabel("Preço:");
        lblNewLabel_7.setFont(new Font("Tahoma", Font.PLAIN, 13));
        lblNewLabel_7.setBounds(459, 418, 96, 13);
        ABAEstoque.add(lblNewLabel_7);

        JTextField textField_4 = new JTextField();
        textField_4.setBounds(459, 442, 120, 19);
        ABAEstoque.add(textField_4);
        textField_4.setColumns(10);

        JButton btnNewButton = new JButton("Adicionar");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String codigo = textField_3.getText();
                String nome = textField_1.getText();
                String quantidade = textField_2.getText();
                String preco = textField_4.getText();

                if (!codigo.isEmpty() && !nome.isEmpty() && !quantidade.isEmpty() && !preco.isEmpty()) {
                    double precoDouble = Double.parseDouble(preco);
                    estoque.adicionarItem(codigo, nome, quantidade, precoDouble);
                    textField_3.setText("");
                    textField_1.setText("");
                    textField_2.setText("");
                    textField_4.setText("");

                    atualizarListaEstoque();
                    atualizarEstoqueNaLista();
                } else {
                    JOptionPane.showMessageDialog(frame, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnNewButton.setBounds(619, 430, 130, 43);
        ABAEstoque.add(btnNewButton);

        JLabel lblNewLabel_5 = new JLabel("");
        lblNewLabel_5.setOpaque(true);
        lblNewLabel_5.setBackground(SystemColor.controlHighlight);
        lblNewLabel_5.setBounds(10, 352, 760, 141);
        ABAEstoque.add(lblNewLabel_5);
        
        JLabel lblNewLabel_8 = new JLabel("");
        lblNewLabel_8.setOpaque(true);
        lblNewLabel_8.setBackground(new Color(128, 128, 128));
        lblNewLabel_8.setBounds(451, 30, 325, 263);
        ABAEstoque.add(lblNewLabel_8);
        
        JLabel lblNewLabel_9 = new JLabel("Item Selecionado:");
        ImageIcon itemselecionadoicon = new ImageIcon("img/ItemSelecionadoIcon.png");
        lblNewLabel_9.setIcon(itemselecionadoicon);
        lblNewLabel_9.setOpaque(true);
        lblNewLabel_9.setBackground(new Color(128, 128, 128));
        lblNewLabel_9.setForeground(new Color(25, 25, 112));
        lblNewLabel_9.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblNewLabel_9.setBounds(457, 6, 176, 30);
        ABAEstoque.add(lblNewLabel_9);
        
        JLabel lblNewLabel_21 = new JLabel("     Pesquisar Item:");
        lblNewLabel_21.setBackground(new Color(128, 128, 128));
        lblNewLabel_21.setOpaque(true);
        lblNewLabel_21.setFont(new Font("Verdana", Font.BOLD, 12));
        lblNewLabel_21.setBounds(10, 25, 419, 42);
        ABAEstoque.add(lblNewLabel_21);
        
        
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                Transacao.salvarTransacoes(extratoTransacoes);
                cliente.salvarClientesNoArquivo();
                System.exit(0); 
            }
        });

        
        
    }
}
