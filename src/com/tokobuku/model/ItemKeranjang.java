package com.tokobuku.model;

public class ItemKeranjang {
    private Buku buku;
    private int quantity;
    private double subtotal;

    // Constructor
    public ItemKeranjang(Buku buku, int quantity) {
        this.buku = buku;
        this.quantity = quantity;
        this.subtotal = hitungSubtotal();
    }

    // Method menghitung subtotal
    public double hitungSubtotal() {
        this.subtotal = buku.getHarga() * quantity;
        return this.subtotal;
    }

    // Method untuk mendapatkan info item
    public String getInfoItem() {
        return String.format("%-20s | %3d x %,.0f = %,.0f", 
                buku.getJudul(), quantity, buku.getHarga(), subtotal);
    }

    // GETTERS
    public Buku getBuku() { return buku; }
    public int getQuantity() { return quantity; }
    public double getSubtotal() { return subtotal; }

    // SETTERS
    public void setQuantity(int quantity) { 
        this.quantity = quantity; 
        this.subtotal = hitungSubtotal();
    }
}