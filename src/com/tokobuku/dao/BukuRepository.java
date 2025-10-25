package com.tokobuku.dao;

import com.tokobuku.model.Buku;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BukuRepository {
    private List<Buku> daftarBuku;

    // Constructor
    public BukuRepository() {
        this.daftarBuku = new ArrayList<>();
        inisialisasiData();
    }

    // Method inisialisasi data dummy
    private void inisialisasiData() {
        daftarBuku.add(new Buku("ISBN001", "Harry Potter", "J.K. Rowling", "Fiksi", 120000, 10));
        daftarBuku.add(new Buku("ISBN002", "Clean Code", "Robert C. Martin", "Teknologi", 250000, 5));
        daftarBuku.add(new Buku("ISBN003", "Sapiens", "Yuval Noah Harari", "Sejarah", 180000, 8));
        daftarBuku.add(new Buku("ISBN004", "Laskar Pelangi", "Andrea Hirata", "Fiksi", 90000, 15));
        daftarBuku.add(new Buku("ISBN005", "Bumi Manusia", "Pramoedya Ananta Toer", "Sejarah", 110000, 7));
    }

    // Method mendapatkan daftar semua buku
    public List<Buku> getDaftarBuku() {
        return new ArrayList<>(daftarBuku);
    }

    // Method mendapatkan buku by ISBN
    public Buku getBukuById(String isbn) {
        return daftarBuku.stream()
                .filter(buku -> buku.getIsbn().equals(isbn))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Buku dengan ISBN " + isbn + " tidak ditemukan"));
    }

    // Method update stok buku
    public void updateStok(String isbn, int jumlah) {
        Buku buku = getBukuById(isbn);
        buku.kurangiStok(jumlah);
    }

    // Method filter buku by kategori
    public List<Buku> filterByKategori(String kategori) {
        return daftarBuku.stream()
                .filter(buku -> buku.getKategori().equalsIgnoreCase(kategori))
                .collect(Collectors.toList());
    }

    // Method menambah buku baru
    public void tambahBuku(Buku buku) {
        daftarBuku.add(buku);
    }
}