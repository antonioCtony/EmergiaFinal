package model;

public class EmergiaCalculator {

    // Fatores emergéticos fixos
    private static final double ELETRICIDADE_SEJ_POR_KWH = 536000;
    private static final double AGUA_INDUSTRIAL_SEJ_POR_M3 = 1.60E+12;

    public String gerarRelatorio(EmergiaInput input) {
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("🔍 Relatório de Sustentabilidade do Sistema\n");
        relatorio.append("--------------------------------------------\n");

        if (input.getUsuario() != null) {
            relatorio.append("Usuário: ").append(input.getUsuario().getNome()).append("\n");
        }

        relatorio.append("Tipo de Resfriamento: ").append(input.getTipoResfriamento()).append("\n");
        relatorio.append("Consumo (kWh): ").append(input.getConsumoResfriamento()).append("\n");
        relatorio.append("Eficiência (COP): ").append(input.getEficienciaCOP()).append("\n");
        relatorio.append("Horas por dia: ").append(input.getHorasOperacao()).append("\n");

        double energiaTotal = input.getConsumoResfriamento() * input.getHorasOperacao();
        double emergiaEletricidade = energiaTotal * ELETRICIDADE_SEJ_POR_KWH;

        relatorio.append("--------------------------------------------\n");
        relatorio.append("🔢 Energia Total Estimada por Dia: ").append(String.format("%.2f", energiaTotal)).append(" kWh\n");
        relatorio.append("⚡ Emergia (Eletricidade): ").append(String.format("%.2e", emergiaEletricidade)).append(" sej\n");

        if ("Ar".equals(input.getTipoResfriamento()) || "Evaporativo".equals(input.getTipoResfriamento())) {
            relatorio.append("Fluxo de Ar (m³/h): ").append(input.getFluxoAr()).append("\n");
        } else if ("Líquido".equals(input.getTipoResfriamento())) {
            relatorio.append("Temperatura de Entrada: ").append(input.getTemperaturaEntrada()).append(" °C\n");
            relatorio.append("Temperatura de Saída: ").append(input.getTemperaturaSaida()).append(" °C\n");
            relatorio.append("Vazão da Água: ").append(input.getVazaoAgua()).append(" L/min\n");

            // Conversão: L/min → m³/dia → (L/min * 60 min/h * 24 h/dia) / 1000
            double m3dia = (input.getVazaoAgua() * 60 * 24) / 1000.0;
            double emergiaAgua = m3dia * AGUA_INDUSTRIAL_SEJ_POR_M3;

            relatorio.append("🚿 Consumo Diário de Água: ").append(String.format("%.2f", m3dia)).append(" m³\n");
            relatorio.append("💧 Emergia (Água Industrial): ").append(String.format("%.2e", emergiaAgua)).append(" sej\n");
        }

        boolean sustentavel = avaliarSustentabilidade(energiaTotal, input);
        relatorio.append("--------------------------------------------\n");
        relatorio.append("💡 Status do Sistema: ").append(sustentavel ? "✅ Sustentável" : "❌ Não Sustentável").append("\n");

        return relatorio.toString();
    }

    private boolean avaliarSustentabilidade(double energiaTotal, EmergiaInput input) {
        if (energiaTotal > 1000) return false;
        if (input.getEficienciaCOP() < 2.5) return false;
        if ("Líquido".equals(input.getTipoResfriamento())) {
            double deltaTemp = input.getTemperaturaSaida() - input.getTemperaturaEntrada();
            if (deltaTemp < 3 || input.getVazaoAgua() > 200) return false;
        }
        return true;
    }
}