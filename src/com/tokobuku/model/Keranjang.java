package com.tokobuku.model;

import java.util.ArrayList;
import java.util.List;

public class Keranjang {
    private String id;
    private List<ItemKeranjang> items;
    private double totalSementara;

    // Constructor
    public Keranjang(String id) {
        this.id = id;
        this.items = new ArrayList<>();
        this.totalSementara = 0.0;
    }

    // Method menambah item ke keranjang
    public void tambahItem(Buku buku, int quantity) {
        // Cek stok tersedia
        if (!buku.cekStokTersedia(quantity)) {
            throw new IllegalArgumentException("Stok tidak mencukupi! Stok tersedia: " + buku.getStok());
        }

        // Cek apakah buku sudah ada di keranjang
        for (ItemKeranjang item : items) {
            if (item.getBuku().getIsbn().equals(buku.getIsbn())) {
                item.setQuantity(item.getQuantity() + quantity);
                hitungTotal();
                return;
            }
        }

        // Jika buku belum ada, tambah item baru
        ItemKeranjang newItem = new ItemKeranjang(buku, quantity);
        items.add(newItem);
        hitungTotal();
    }

    // Method menghapus item dari keranjang
    public void hapusItem(int index) {
        if (index >= 0 && index < items.size()) {
            items.remove(index);
            hitungTotal();
        } else {
            throw new IllegalArgumentException("Index tidak valid");
        }
    }

    // Method menghitung total sementara
    public double hitungTotal() {
        this.totalSementara = 0.0;
        for (ItemKeranjang item : items) {
            this.totalSementara += item.getSubtotal();
        }
        return this.totalSementara;
    }

    // Method mengosongkan keranjang
    public void kosongkanKeranjang() {
        items.clear();
        totalSementara = 0.0;
    }

    // GETTERS
    public String getId() { return id; }
    public List<ItemKeranjang> getItems() { return new ArrayList<>(items); }
    public double getTotalSementara() { return totalSementara; }
    public boolean isEmpty() { return items.isEmpty(); }
}