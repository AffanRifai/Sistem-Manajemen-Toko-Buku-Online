package com.tokobuku.model;

import com.tokobuku.dao.BukuRepository;
import java.time.LocalDateTime;

public class Pembelian {
    private String id;
    private Keranjang keranjang;
    private double totalHarga;
    private LocalDateTime waktuPembelian;
    private Struk struk;

    public Pembelian(String id, Keranjang keranjang) {
        this.id = id;
        this.keranjang = keranjang;
        this.waktuPembelian = LocalDateTime.now();
        this.totalHarga = hitungTotalHarga();
    }

    public boolean prosesCheckout(BukuRepository bukuRepository) {
        try {
            if (keranjang.isEmpty()) {
                throw new IllegalStateException("Keranjang kosong, tidak bisa checkout");
            }

            for (ItemKeranjang item : keranjang.getItems()) {
                bukuRepository.updateStok(item.getBuku().getIsbn(), item.getQuantity());
            }

            this.struk = new Struk("STRUK-" + id, this);
            return true;
        } catch (Exception e) {
            System.out.println("Error saat checkout: " + e.getMessage());
            return false;
        }
    }

    public double hitungTotalHarga() {
        this.totalHarga = keranjang.hitungTotal();
        return this.totalHarga;
    }

    public String getDetailPembelian() {
        return String.format("Pembelian ID: %s | Total: %,.0f | Tanggal: %s", 
                id, totalHarga, waktuPembelian.toString());
    }

    // GETTERS
    public String getId() { return id; }
    public Keranjang getKeranjang() { return keranjang; }
    public double getTotalHarga() { return totalHarga; }
    public LocalDateTime getWaktuPembelian() { return waktuPembelian; }
    public Struk getStruk() { return struk; }
}