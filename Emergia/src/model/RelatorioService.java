package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

public class RelatorioService {

    // Fatores emergéticos
    private static final double ELETRICIDADE_SEJ_POR_KWH = 536000;
    private static final double AGUA_INDUSTRIAL_SEJ_POR_M3 = 1.60E+12;

    public String gerarResumo(EmergiaInput input) {
        StringBuilder resumo = new StringBuilder();

        // Dados do usuário
        resumo.append("Usuário: ").append(input.getUsuario().getNome()).append("\n");
        resumo.append("Email: ").append(input.getUsuario().getEmail()).append("\n");

        // Dados do resfriamento
        resumo.append("\nTipo de Resfriamento: ").append(input.getTipoResfriamento()).append("\n");
        resumo.append("Consumo (kWh): ").append(input.getConsumoResfriamento()).append("\n");
        resumo.append("Eficiência (COP): ").append(input.getEficienciaCOP()).append("\n");
        resumo.append("Horas de operação por dia: ").append(input.getHorasOperacao()).append("\n");

        if ("Ar".equals(input.getTipoResfriamento()) || "Evaporativo".equals(input.getTipoResfriamento())) {
            resumo.append("Fluxo de Ar (m³/h): ").append(input.getFluxoAr()).append("\n");
        }

        if ("Líquido".equals(input.getTipoResfriamento())) {
            resumo.append("Temperatura de Entrada (°C): ").append(input.getTemperaturaEntrada()).append("\n");
            resumo.append("Temperatura de Saída (°C): ").append(input.getTemperaturaSaida()).append("\n");
            resumo.append("Vazão da Água (L/min): ").append(input.getVazaoAgua()).append("\n");
        }

        // Novas perguntas
        resumo.append("\nFontes de alimentação redundantes: ").append(input.isFonteRedundante() ? "Sim" : "Não").append("\n");
        resumo.append("Eficiência da fonte de alimentação (%): ").append(input.getEficienciaFonte()).append("\n");

        // Cálculo do consumo anual estimado
        double consumoAnual = calcularConsumoAnual(input);
        DecimalFormat df = new DecimalFormat("#.##");
        resumo.append("\nConsumo Anual Estimado (kWh): ").append(df.format(consumoAnual)).append("\n");

        // Cálculo da emergia da eletricidade
        double emergiaEletricidade = consumoAnual * ELETRICIDADE_SEJ_POR_KWH;
        resumo.append("⚡ Emergia Anual (Eletricidade): ").append(String.format("%.2e", emergiaEletricidade)).append(" sej\n");

        // Cálculo da emergia da água, se aplicável
        if ("Líquido".equals(input.getTipoResfriamento())) {
            // Conversão da vazão de água: L/min → m³/dia → m³/ano
            double m3dia = (input.getVazaoAgua() * 60 * 24) / 1000.0;
            double m3ano = m3dia * 365;
            double emergiaAgua = m3ano * AGUA_INDUSTRIAL_SEJ_POR_M3;

            resumo.append("🚿 Consumo Anual de Água: ").append(df.format(m3ano)).append(" m³\n");
            resumo.append("💧 Emergia Anual (Água Industrial): ").append(String.format("%.2e", emergiaAgua)).append(" sej\n");
        }

        // Salvar relatório em arquivo .txt
        try {
            File caminho = new File(System.getProperty("user.home") + "/Documents/relatorio_emergia.txt");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminho))) {
                writer.write(resumo.toString());
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar o relatório: " + e.getMessage());
        }

        return resumo.toString();
    }

    // Método que calcula o consumo anual estimado
    private double calcularConsumoAnual(EmergiaInput input) {
        double consumoDiario = input.getConsumoResfriamento() * input.getHorasOperacao();
        return consumoDiario * 365; // Consumo anual
    }
}