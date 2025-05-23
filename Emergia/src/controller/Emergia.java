package controller;

import view.UsuarioGUI;

public class Emergia {
    public static void main(String[] args) {
        // Inicia a interface gráfica unificada com abas (Cadastro, Login, Cálculo)
        UsuarioGUI gui = new UsuarioGUI();
        gui.exibirInterface();
    }
}
