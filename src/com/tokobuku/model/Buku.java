package com.tokobuku.model;

import java.text.NumberFormat;
import java.util.Locale;

public class Buku {
    private String isbn;
    private String judul;
    private String penulis;
    private String kategori;
    private double harga;
    private int stok;

    public Buku(String isbn, String judul, String penulis, String kategori, double harga, int stok) {
        this.isbn = isbn;
        this.judul = judul;
        this.penulis = penulis;
        this.kategori = kategori;
        this.harga = harga;
        this.stok = stok;
    }

    public String getInfoBuku() {
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(Locale.of("id", "ID"));
        return String.format("%-15s | %-20s | %-15s | %-12s | %-10s | %3d", 
                isbn, judul, penulis, kategori, formatRupiah.format(harga), stok);
    }

    public void kurangiStok(int jumlah) {
        if (jumlah <= stok) {
            this.stok -= jumlah;
        } else {
            throw new IllegalArgumentException("Stok tidak mencukupi");
        }
    }

    public boolean cekStokTersedia(int jumlah) {
        return jumlah <= stok;
    }

    // GETTERS
    public String getIsbn() { return isbn; }
    public String getJudul() { return judul; }
    public String getPenulis() { return penulis; }
    public String getKategori() { return kategori; }
    public double getHarga() { return harga; }
    public int getStok() { return stok; }

    // SETTERS
    public void setStok(int stok) { this.stok = stok; }
}