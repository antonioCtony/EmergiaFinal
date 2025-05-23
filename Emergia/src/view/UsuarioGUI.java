package view;

import model.*;
import utilatery.Criptografia;
import utilatery.Validador;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class UsuarioGUI {
    private Usuario usuarioLogado;
    private JPanel painelEmergia;
    private JTabbedPane abas;
    private JComboBox<String> comboTipoResfriamento;
    private JTextField campoConsumoResfriamento;
    private JTextField campoEficienciaCOP;
    private JTextField campoFluxoAr;
    private JTextField campoTemperaturaEntrada;
    private JTextField campoTemperaturaSaida;
    private JTextField campoVazaoAgua;
    private JTextField campoHorasOperacao;
    private JCheckBox checkFonteRedundante;
    private JTextField campoEficienciaFonte;
    private JPanel painelCamposResfriamento;

    public void exibirInterface() {
        JFrame frame = new JFrame("Sistema de Sustentabilidade");
        frame.setSize(650, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        abas = new JTabbedPane();
        abas.addTab("Cadastro", criarPainelCadastro());
        abas.addTab("Login", criarPainelLogin());
        painelEmergia = criarPainelEmergia();
        abas.addTab("Emergia", painelEmergia);
        abas.setEnabledAt(2, false);

        frame.add(abas);
        frame.setVisible(true);
    }

    private void padronizarCampoTexto(JComponent campo) {
        campo.setPreferredSize(new Dimension(400, 28));
    }

    private JPanel criarPainelCadastro() {
        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        

        JTextField nomeField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField senhaField = new JPasswordField();

        padronizarCampoTexto(nomeField);
        padronizarCampoTexto(emailField);
        padronizarCampoTexto(senhaField);

        JLabel mensagem = new JLabel();
        JButton cadastrarButton = new JButton("Cadastrar");

        gbc.gridx = 0; gbc.gridy = 0;
        painel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        painel.add(nomeField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        painel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        painel.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        painel.add(new JLabel("Senha:"), gbc);
        gbc.gridx = 1;
        painel.add(senhaField, gbc);

        gbc.gridx = 1; gbc.gridy = 3;
        painel.add(cadastrarButton, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        painel.add(mensagem, gbc);

        cadastrarButton.addActionListener(e -> {
            String nome = nomeField.getText().trim();
            String email = emailField.getText().trim();
            String senha = new String(senhaField.getPassword());

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                mensagem.setText("❌ Preencha todos os campos.");
                return;
            }
            if (!Validador.validarEmail(email)) {
                mensagem.setText("❌ Email inválido.");
                return;
            }
            if (!Validador.validarSenha(senha)) {
                mensagem.setText("❌ Senha fraca. Use pelo menos 8 caracteres, 1 letra maiúscula e 1 número.");
                return;
            }

            String hash = Criptografia.gerarHash(senha);

            try (FileWriter fw = new FileWriter("usuarios.txt", true)) {
                fw.write(nome + "," + email + "," + hash + "\n");
                mensagem.setText("✅ Cadastro realizado.");
            } catch (IOException ex) {
                mensagem.setText("❌ Erro: " + ex.getMessage());
            }
        });

        return painel;
    }

    private JPanel criarPainelLogin() {
        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField emailField = new JTextField();
        JPasswordField senhaField = new JPasswordField();

        padronizarCampoTexto(emailField);
        padronizarCampoTexto(senhaField);

        JButton loginButton = new JButton("Login");
        JLabel mensagem = new JLabel();

        gbc.gridx = 0; gbc.gridy = 0;
        painel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        painel.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        painel.add(new JLabel("Senha:"), gbc);
        gbc.gridx = 1;
        painel.add(senhaField, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        painel.add(loginButton, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        painel.add(mensagem, gbc);

        loginButton.addActionListener(e -> {
            String email = emailField.getText().trim();
            String senha = new String(senhaField.getPassword());

            try (BufferedReader reader = new BufferedReader(new FileReader("usuarios.txt"))) {
                String linha;
                while ((linha = reader.readLine()) != null) {
                    String[] partes = linha.split(",");
                    if (partes.length == 3 && partes[1].equals(email)) {
                        if (Criptografia.verificarSenha(senha, partes[2])) {
                            usuarioLogado = new Usuario(partes[0], partes[1], partes[2]);
                            mensagem.setText("✅ Bem-vindo, " + partes[0] + "!");
                            abas.setEnabledAt(2, true);
                            abas.setSelectedIndex(2);
                        } else {
                            mensagem.setText("❌ Senha incorreta.");
                        }
                        return;
                    }
                }
                mensagem.setText("❌ Usuário não encontrado.");
            } catch (IOException ex) {
                mensagem.setText("❌ Erro: " + ex.getMessage());
            }
        });

        return painel;
    }

    private JPanel criarPainelEmergia() {
        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel labelTipo = new JLabel("Tipo de Resfriamento:");
        comboTipoResfriamento = new JComboBox<>(new String[]{"Ar", "Líquido", "Evaporativo"});
        campoConsumoResfriamento = new JTextField();
        campoEficienciaCOP = new JTextField();
        campoFluxoAr = new JTextField();
        campoTemperaturaEntrada = new JTextField();
        campoTemperaturaSaida = new JTextField();
        campoVazaoAgua = new JTextField();
        campoHorasOperacao = new JTextField();
        campoEficienciaFonte = new JTextField();
        checkFonteRedundante = new JCheckBox("O servidor possui fontes de alimentação redundantes?");

        padronizarCampoTexto(campoConsumoResfriamento);
        padronizarCampoTexto(campoEficienciaCOP);
        padronizarCampoTexto(campoFluxoAr);
        padronizarCampoTexto(campoTemperaturaEntrada);
        padronizarCampoTexto(campoTemperaturaSaida);
        padronizarCampoTexto(campoVazaoAgua);
        padronizarCampoTexto(campoHorasOperacao);
        padronizarCampoTexto(campoEficienciaFonte);

        gbc.gridx = 0; gbc.gridy = 0;
        painel.add(labelTipo, gbc);
        gbc.gridx = 1;
        painel.add(comboTipoResfriamento, gbc);

        painelCamposResfriamento = new JPanel(new GridBagLayout());
        painelCamposResfriamento.setBorder(BorderFactory.createTitledBorder("Dados do Resfriamento"));

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        painel.add(painelCamposResfriamento, gbc);

        comboTipoResfriamento.addActionListener(e -> atualizarCamposResfriamento());
        atualizarCamposResfriamento();

        JPanel painelFonte = new JPanel(new GridBagLayout());
        painelFonte.setBorder(BorderFactory.createTitledBorder("Fonte de Alimentação"));
        GridBagConstraints gbcFonte = new GridBagConstraints();
        gbcFonte.insets = new Insets(5, 5, 5, 5);
        gbcFonte.gridx = 0; gbcFonte.gridy = 0; gbcFonte.gridwidth = 2;
        gbcFonte.fill = GridBagConstraints.HORIZONTAL;

        painelFonte.add(checkFonteRedundante, gbcFonte);

        gbcFonte.gridy = 1; gbcFonte.gridwidth = 1;
        painelFonte.add(new JLabel("Eficiência da fonte (%):"), gbcFonte);
        gbcFonte.gridx = 1;
        painelFonte.add(campoEficienciaFonte, gbcFonte);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        painel.add(painelFonte, gbc);

        JButton botao = new JButton("Calcular");
        botao.addActionListener(e -> realizarCalculo());
        gbc.gridy = 3;
        painel.add(botao, gbc);

        return painel;
    }

    private void atualizarCamposResfriamento() {
        painelCamposResfriamento.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;

        int y = 0;
        gbc.gridx = 0; gbc.gridy = y;
        painelCamposResfriamento.add(new JLabel("Consumo (kWh):"), gbc);
        gbc.gridx = 1;
        painelCamposResfriamento.add(campoConsumoResfriamento, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        painelCamposResfriamento.add(new JLabel("Eficiência (COP):"), gbc);
        gbc.gridx = 1;
        painelCamposResfriamento.add(campoEficienciaCOP, gbc);

        y++;
        gbc.gridx = 0; gbc.gridy = y;
        painelCamposResfriamento.add(new JLabel("Horas por dia:"), gbc);
        gbc.gridx = 1;
        painelCamposResfriamento.add(campoHorasOperacao, gbc);

        String tipo = (String) comboTipoResfriamento.getSelectedItem();
        if ("Ar".equals(tipo) || "Evaporativo".equals(tipo)) {
            y++;
            gbc.gridx = 0; gbc.gridy = y;
            painelCamposResfriamento.add(new JLabel("Fluxo de Ar (m³/h):"), gbc);
            gbc.gridx = 1;
            painelCamposResfriamento.add(campoFluxoAr, gbc);
        } else if ("Líquido".equals(tipo)) {
            y++;
            gbc.gridx = 0; gbc.gridy = y;
            painelCamposResfriamento.add(new JLabel("Temperatura Entrada (°C):"), gbc);
            gbc.gridx = 1;
            painelCamposResfriamento.add(campoTemperaturaEntrada, gbc);

            y++;
            gbc.gridx = 0; gbc.gridy = y;
            painelCamposResfriamento.add(new JLabel("Temperatura Saída (°C):"), gbc);
            gbc.gridx = 1;
            painelCamposResfriamento.add(campoTemperaturaSaida, gbc);

            y++;
            gbc.gridx = 0; gbc.gridy = y;
            painelCamposResfriamento.add(new JLabel("Vazão da Água (L/min):"), gbc);
            gbc.gridx = 1;
            painelCamposResfriamento.add(campoVazaoAgua, gbc);
        }

        painelCamposResfriamento.revalidate();
        painelCamposResfriamento.repaint();
    }

    private void realizarCalculo() {
        try {
            EmergiaInput input = new EmergiaInput();
            input.setTipoResfriamento((String) comboTipoResfriamento.getSelectedItem());
            input.setConsumoResfriamento(Double.parseDouble(campoConsumoResfriamento.getText()));
            input.setEficienciaCOP(Double.parseDouble(campoEficienciaCOP.getText()));
            input.setHorasOperacao(Double.parseDouble(campoHorasOperacao.getText()));

            String tipo = input.getTipoResfriamento();
            if ("Ar".equals(tipo) || "Evaporativo".equals(tipo)) {
                input.setFluxoAr(Double.parseDouble(campoFluxoAr.getText()));
            } else if ("Líquido".equals(tipo)) {
                input.setTemperaturaEntrada(Double.parseDouble(campoTemperaturaEntrada.getText()));
                input.setTemperaturaSaida(Double.parseDouble(campoTemperaturaSaida.getText()));
                input.setVazaoAgua(Double.parseDouble(campoVazaoAgua.getText()));
            }

            input.setFonteRedundante(checkFonteRedundante.isSelected());
            input.setEficienciaFonte(Double.parseDouble(campoEficienciaFonte.getText()));

            if (usuarioLogado == null) {
                JOptionPane.showMessageDialog(null, "Erro: usuário não logado!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            input.setUsuario(usuarioLogado);

            RelatorioService service = new RelatorioService();
            String relatorio = service.gerarResumo(input);

            JOptionPane.showMessageDialog(null, relatorio, "Relatório", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Preencha todos os campos com valores válidos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UsuarioGUI().exibirInterface());
    }
}
