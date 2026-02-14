import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Cliente {
    private int id;
    private String nome;
    private String email;
    private String cpf;
    private String genero;
    private boolean receberNotificacao;
    private boolean receberEmail;

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getGenero(){
        return genero;
    }

    public void setGenero(String genero){
        this.genero = genero;
    }

    public boolean isReceberEmail() {
        return receberEmail;
    }

    public void setReceberEmail(boolean receberEmail) {
        this.receberEmail = receberEmail;
    }

    public boolean isReceberNotificacao() {
        return receberNotificacao;
    }

    public void setReceberNotificacao(boolean receberNotificacao) {
        this.receberNotificacao = receberNotificacao;
    }
}
