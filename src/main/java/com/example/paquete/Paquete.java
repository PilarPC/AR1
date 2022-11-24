package com.example.paquete;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Paquete implements Serializable {
    private String mensaje;
    private String emisor = "";
    private String tipiM = "";


    private int puertoE;
    private int puertoR;
    private String tiempo;

    private String Firma;
    private String llaveCifrada;

    private char codigoDeOperación;


    private int numeroDeCertificado;
    private String rutaCertificadoReceptor;

    private int llavePuclica;

    private int numeroDeCertificadoR;


    public Paquete(String mensaje, int puertoE, int getPuertoR, int numeroDeCertificadoR) {
        this.mensaje = mensaje;
        this.puertoE = puertoE;
        this.puertoR = getPuertoR;
        this.numeroDeCertificadoR = numeroDeCertificadoR;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getEmisor() {
        return emisor;
    }

    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    public int getPuertoE() {
        return puertoE;
    }

    public void setPuertoE(int puertoE) {
        this.puertoE = puertoE;
    }

    public int getpPuertoR() {
        return puertoR;
    }

    public void setPuertoR(int getPuertoR) {
        this.puertoR = getPuertoR;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo() {
        this.tiempo = DateTimeFormatter.ofPattern("dd-MM-yyyy | HH:mm").format(LocalDateTime.now());
    }

    public void setNuevoTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public String getTipiM() {
        return tipiM;
    }

    public void setTipiM(String tipiM) {
        this.tipiM = tipiM;
    }


    public String getFirma() {
        return Firma;
    }

    public void setFirma(String firma) {
        Firma = firma;
    }

    public String getLlaveCifrada() {
        return llaveCifrada;
    }

    public void setLlaveCifrada(String llaveCifrada) {
        this.llaveCifrada = llaveCifrada;
    }

    public int getNumeroDeCertificado() {
        return numeroDeCertificado;
    }

    public void setNumeroDeCertificado(int numeroDeCertificado) {
        this.numeroDeCertificado = numeroDeCertificado;
    }

    public String getRutaCertificadoReceptor() {
        return rutaCertificadoReceptor;
    }

    public void setRutaCertificadoReceptor(String rutaCertificadoReceptor) {
        this.rutaCertificadoReceptor = rutaCertificadoReceptor;
    }

    public int getLlavePuclica() {
        return llavePuclica;
    }

    public void setLlavePuclica(int llavePuclica) {
        this.llavePuclica = llavePuclica;
    }

    public int getNumeroDeCertificadoR() {
        return numeroDeCertificadoR;
    }

    public void setNumeroDeCertificadoR(int numeroDeCertificadoR) {
        this.numeroDeCertificadoR = numeroDeCertificadoR;
    }


    public char getCodigoDeOperación() {
        return codigoDeOperación;
    }

    public void setCodigoDeOperación(char codigoDeOperación) {
        this.codigoDeOperación = codigoDeOperación;
    }
}
