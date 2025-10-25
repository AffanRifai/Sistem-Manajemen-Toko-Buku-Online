package com.tokobuku.model;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Struk {
    private String id;
    private Pembelian pembelian;
    private String kontenStruk;

    public Struk(String id, Pembelian pembelian) {
        this.id = id;
        this.pembelian = pembelian;
        this.kontenStruk = generateStruk();
    }

    public String generateStruk() {
        StringBuilder strukBuilder = new StringBuilder();
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(Locale.of("id", "ID"));
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        // Header Struk
        strukBuilder.append("=====================================\n");
        strukBuilder.append("          TOKO BUKU ONLINE          \n");
        strukBuilder.append("           Affan STORE            \n");
        strukBuilder.append("=====================================\n");
        strukBuilder.append("Struk ID: ").append(id).append("\n");
        strukBuilder.append("Tanggal : ").append(pembelian.getWaktuPembelian().format(dateFormat)).append("\n");
        strukBuilder.append("-------------------------------------\n");

        // Detail Item
        strukBuilder.append("Item yang dibeli:\n");
        int counter = 1;
        for (ItemKeranjang item : pembelian.getKeranjang().getItems()) {
            strukBuilder.append(String.format("%d. %-20s %2d x %10s = %10s\n",
                    counter++,
                    item.getBuku().getJudul(),
                    item.getQuantity(),
                    formatRupiah.format(item.getBuku().getHarga()),
                    formatRupiah.format(item.getSubtotal())));
        }

        // Footer Struk
        strukBuilder.append("-------------------------------------\n");
        strukBuilder.append(String.format("Total Belanja: %25s\n",
                formatRupiah.format(pembelian.getTotalHarga())));
        strukBuilder.append("=====================================\n");
        strukBuilder.append("     Terima kasih atas pembelian     \n");
        strukBuilder.append("          Anda di AffanSTORE!          \n");
        strukBuilder.append("=====================================\n");

        this.kontenStruk = strukBuilder.toString();
        return this.kontenStruk;
    }

    public void cetakStruk() {
        System.out.println(kontenStruk);
    }

    // GETTERS
    public String getId() {
        return id;
    }

    public Pembelian getPembelian() {
        return pembelian;
    }

    public String getKontenStruk() {
        return kontenStruk;
    }
}