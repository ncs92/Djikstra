/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classe;

/**
 *
 * @author nani
 */
public class Vertice {
    private String id;
    private int numero;
    private boolean status = true;
    private int cor; //0 - branco; 1 - cinza; 2 - preto
    private int peso;
    private Vertice pai;

    public Vertice getPai() {
        return pai;
    }

    public void setPai(Vertice pai) {
        this.pai = pai;
    }
    

    

    public Vertice() {
       this.id = String.valueOf(getClass().hashCode());
       this.peso = -1;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getCor() {
        return cor;
    }

    public void setCor(int cor) {
        this.cor = cor;
    }

    
    @Override
    public String toString() {
        return "Vertice{" + "numero=" + numero + '}';
    }

    
}