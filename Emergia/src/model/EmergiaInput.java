package model;

public class EmergiaInput {
    private String tipoResfriamento;
    private double consumoResfriamento;
    private double eficienciaCOP;
    private double fluxoAr;
    private double temperaturaEntrada;
    private double temperaturaSaida;
    private double vazaoAgua;
    private double horasOperacao;
    private boolean fonteRedundante;
    private double eficienciaFonte;
    private Usuario usuario;

    // Getters e setters
    public String getTipoResfriamento() { return tipoResfriamento; }
    public void setTipoResfriamento(String tipoResfriamento) { this.tipoResfriamento = tipoResfriamento; }

    public double getConsumoResfriamento() { return consumoResfriamento; }
    public void setConsumoResfriamento(double consumoResfriamento) { this.consumoResfriamento = consumoResfriamento; }

    public double getEficienciaCOP() { return eficienciaCOP; }
    public void setEficienciaCOP(double eficienciaCOP) { this.eficienciaCOP = eficienciaCOP; }

    public double getFluxoAr() { return fluxoAr; }
    public void setFluxoAr(double fluxoAr) { this.fluxoAr = fluxoAr; }

    public double getTemperaturaEntrada() { return temperaturaEntrada; }
    public void setTemperaturaEntrada(double temperaturaEntrada) { this.temperaturaEntrada = temperaturaEntrada; }

    public double getTemperaturaSaida() { return temperaturaSaida; }
    public void setTemperaturaSaida(double temperaturaSaida) { this.temperaturaSaida = temperaturaSaida; }

    public double getVazaoAgua() { return vazaoAgua; }
    public void setVazaoAgua(double vazaoAgua) { this.vazaoAgua = vazaoAgua; }

    public double getHorasOperacao() { return horasOperacao; }
    public void setHorasOperacao(double horasOperacao) { this.horasOperacao = horasOperacao; }

    public boolean isFonteRedundante() { return fonteRedundante; }
    public void setFonteRedundante(boolean fonteRedundante) { this.fonteRedundante = fonteRedundante; }

    public double getEficienciaFonte() { return eficienciaFonte; }
    public void setEficienciaFonte(double eficienciaFonte) { this.eficienciaFonte = eficienciaFonte; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}