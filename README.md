Java.GestãoEmpresarial

Sistema de Gestão Empresarial em Java  
Projeto acadêmico desenvolvido para praticar os conceitos de Programação Orientada a Objetos em um aplicativo completo de gerenciamento de clientes, estoque e transações.

Estrutura do repositório:

* src/  
  • Código-fonte Java organizado em pacotes  
* data/  
  • Arquivos de dados utilizados pela aplicação (ex.: .txt)  
* img/  
  • Recursos de imagem (ícones, logos)  
* lib/  
  • Bibliotecas externas (MigLayout, JGoodies)  
* .project  
  • Configuração de projeto do Eclipse  
* .classpath  
  • Configuração de caminho de classes do Eclipse  
* .gitignore  
  • Lista de arquivos e pastas a serem ignorados pelo Git  

Tecnologias e bibliotecas:

* Java SE 11 ou superior  
* Eclipse IDE (2023-09 ou superior)  
* MigLayout (layout manager para Swing)  
* JGoodies (utilizado no frontend para melhorar componentes Swing e binding)  

Como importar e executar no Eclipse:

1. Clone este repositório:  
   git clone https://github.com/enzoconsulo/Java.GestaoEmpresarial.git  
2. Abra o Eclipse e selecione File → Import…  
3. Escolha General → Existing Projects into Workspace  
4. Aponte para a pasta Java.GestaoEmpresarial e clique em Finish  
5. Se necessário, ajuste o JDK:  
   • Botão direito no projeto → Build Path → Configure Build Path  
   • Na aba Libraries, valide o JRE System Library apontando para Java 11+  
6. Para rodar a aplicação:  
   • No Package Explorer, expanda src/ e localize a classe com método main (nomeado "teste") 
   • Botão direito na classe → Run As → Java Application  

Funcionalidades principais:

* Cadastro e gerenciamento de clientes  
* Controle de estoque (inclusão, alteração e remoção de produtos)  
* Registro de transações (vendas, entradas e saídas de estoque)  
* Interface gráfica em Swing, com frontend construído com JGoodies e MigLayout  

Autor:  
Enzo Consulo  
https://github.com/enzoconsulo  

Licença:  
Sem Licença 

## Capturas de Tela

<table>
  <tr>
    <td align="center">
      <img src="MainProjeto/img_RunningProject/PaginaInicial.jpg" width="250px"/><br/>
      Página Inicial
    </td>
    <td align="center">
      <img src="MainProjeto/img_RunningProject/AbaClientes.jpg" width="250px"/><br/>
      Aba Clientes
    </td>
    <td align="center">
      <img src="MainProjeto/img_RunningProject/AbaClientesAdicionarDivida.jpg" width="250px"/><br/>
      Adicionar Dívida
    </td>
    <td align="center">
      <img src="MainProjeto/img_RunningProject/AbaClientesPagarDivida.jpg" width="250px"/><br/>
      Pagar Dívida
    </td>
  </tr>
  <tr>
    <td align="center">
      <img src="MainProjeto/img_RunningProject/AbaEstoque.jpg" width="250px"/><br/>
      Aba Estoque
    </td>
    <td align="center">
      <img src="MainProjeto/img_RunningProject/AbaEstoqueEditarQuantidade.jpg" width="250px"/><br/>
      Editar Quantidade
    </td>
    <td align="center">
      <img src="MainProjeto/img_RunningProject/AbaEstoqueVenda1.jpg" width="250px"/><br/>
      Venda 1
    </td>
    <td align="center">
      <img src="MainProjeto/img_RunningProject/AbaEstoqueVenda2.jpg" width="250px"/><br/>
      Venda 2
    </td>
  </tr>
  <tr>
    <td align="center">
      <img src="MainProjeto/img_RunningProject/AbaEstoqueVenda3.jpg" width="250px"/><br/>
      Venda 3
    </td>
    <td align="center">
      <img src="MainProjeto/img_RunningProject/AbaEstoqueVenda4.jpg" width="250px"/><br/>
      Venda 4
    </td>
    <td align="center">
      <img src="MainProjeto/img_RunningProject/AbaFinanceiro.jpg" width="250px"/><br/>
      Aba Financeiro
    </td>
    <td align="center">
      <img src="MainProjeto/img_RunningProject/AbaFinanceiroFinal1.jpg" width="250px"/><br/>
      Financeiro – Final 1
    </td>
  </tr>
  <tr>
    <td align="center" colspan="4">
      <img src="MainProjeto/img_RunningProject/AbaFinanceiroFinal1Extratofiltrado.jpg" width="500px"/><br/>
      Financeiro – Extrato Filtrado
    </td>
  </tr>
</table>
