package com.insumoskeij.appaksu.model;

import android.graphics.Bitmap;

/**
 * Created by KUNCORO on 09/08/2017.
 */

public class Catalogo {
    private String txtMarca;
    private String txtModelo;
    private String txtserie;
    private String txtDetalle;
    private String txtAnno;
    private String txtCodigoProd;
    private String txtTipoProd;
    private Bitmap imgProd;
    private String rutaImg;

    public Catalogo(String txtMarca, String txtModelo, String txtserie, String txtDetalle, String txtAnno, String txtCodigoProd, String txtTipoProd, Bitmap imgProd, String rutaImg) {
        this.txtMarca = txtMarca;
        this.txtModelo = txtModelo;
        this.txtserie = txtserie;
        this.txtDetalle = txtDetalle;
        this.txtAnno = txtAnno;
        this.txtCodigoProd = txtCodigoProd;
        this.txtTipoProd = txtTipoProd;
        this.imgProd = imgProd;
        this.rutaImg = rutaImg;
    }

    public Catalogo() {
    }

    public String getTxtSerie() {
        return txtserie;
    }

    public void setTxtserie(String txtserie) {
        this.txtserie = txtserie;
    }

    public String getTxtDetalle() {
        return txtDetalle;
    }

    public void setTxtDetalle(String txtDetalle) {
        this.txtDetalle = txtDetalle;
    }

    public String getTxtMarca() {
        return txtMarca;
    }

    public void setTxtMarca(String txtMarca) {
        this.txtMarca = txtMarca;
    }

    public String getTxtModelo() {
        return txtModelo;
    }

    public void setTxtModelo(String txtModelo) {
        this.txtModelo = txtModelo;
    }

    public String getTxtAnno() {
        return txtAnno;
    }

    public void setTxtAnno(String txtAnno) {
        this.txtAnno = txtAnno;
    }

    public String getTxtCodigoProd() {return txtCodigoProd;
    }

    public void setTxtCodigoProd(String txtCodigoProd) {this.txtCodigoProd = txtCodigoProd; }

    public String getTxtTipoProd() {
        return txtTipoProd;
    }

    public void setTxtTipoProd(String txtTipoProd) {
        this.txtTipoProd = txtTipoProd;
    }

    public Bitmap getImgProd() {
        return imgProd;
    }

    public void setImgProd(Bitmap imgProd) {
        this.imgProd = imgProd;
    }

    public String getRutaImg() {
        return rutaImg;
    }

    public void setRutaImg(String rutaImg) {
        this.rutaImg = rutaImg;
    }
}
