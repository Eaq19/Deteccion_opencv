/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author HECTOR
 */
public class Antecedente {
    
    private int idAntecedente;
    private int sentencias;
    private int prision;
    private boolean reinsidente;

    public Antecedente() {
    }

    public Antecedente(int idAntecedente, int sentencias, int prision, boolean reinsidente) {
        this.idAntecedente = idAntecedente;
        this.sentencias = sentencias;
        this.prision = prision;
        this.reinsidente = reinsidente;
    }

    public int getIdAntecedente() {
        return idAntecedente;
    }

    public void setIdAntecedente(int idAntecedente) {
        this.idAntecedente = idAntecedente;
    }

    public int getSentencias() {
        return sentencias;
    }

    public void setSentencias(int sentencias) {
        this.sentencias = sentencias;
    }

    public int getPrision() {
        return prision;
    }

    public void setPrision(int prision) {
        this.prision = prision;
    }

    public boolean isReinsidente() {
        return reinsidente;
    }

    public void setReinsidente(boolean reinsidente) {
        this.reinsidente = reinsidente;
    }
    
    
    
    
}
