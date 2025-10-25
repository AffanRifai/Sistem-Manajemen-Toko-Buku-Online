package com.tokobuku.main;

import com.tokobuku.dao.BukuRepository;
import com.tokobuku.model.Buku;
import com.tokobuku.model.ItemKeranjang;
import com.tokobuku.model.Keranjang;
import com.tokobuku.model.Pembelian;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class TokoBukuApp {
    private BukuRepository bukuRepository;
    private Keranjang keranjang;
    private Scanner scanner;

    public TokoBukuApp() {
        this.bukuRepository = new BukuRepository();
        this.keranjang = new Keranjang("K001");
        this.scanner = new Scanner(System.in);
    }

    public void tampilkanMenuUtama() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("       SISTEM TOKO BUKU ONLINE AffanSTORE");
        System.out.println("=".repeat(50));
        System.out.println("1. Lihat Daftar Buku");
        System.out.println("2. Tambah Buku ke Keranjang");
        System.out.println("3. Lihat Keranjang Belanja");
        System.out.println("4. Hapus dari Keranjang");
        System.out.println("5. Checkout");
        System.out.println("6. Filter Buku by Kategori");
        System.out.println("0. Keluar");
        System.out.println("=".repeat(50));
        System.out.print("Pilih menu [0-6]: ");
    }

    public void tampilkanDaftarBuku() {
        List<Buku> daftarBuku = bukuRepository.getDaftarBuku();

        System.out.println("\n" + "=".repeat(100));
        System.out.println("                                 DAFTAR BUKU TOKO AffanSTORE");
        System.out.println("=".repeat(100));
        System.out.printf("%-15s | %-20s | %-15s | %-12s | %-10s | %s\n",
                "ISBN", "Judul", "Penulis", "Kategori", "Harga", "Stok");
        System.out.println("-".repeat(100));

        for (Buku buku : daftarBuku) {
            System.out.println(buku.getInfoBuku());
        }
        System.out.println("=".repeat(100));
    }

    public void tambahKeKeranjang() {
        tampilkanDaftarBuku();
        System.out.print("\nMasukkan ISBN buku yang ingin dibeli: ");
        String isbn = scanner.nextLine();

        try {
            Buku buku = bukuRepository.getBukuById(isbn);
            System.out.print("Masukkan jumlah yang ingin dibeli: ");
            int quantity = scanner.nextInt();
            scanner.nextLine();

            keranjang.tambahItem(buku, quantity);
            System.out.println(" Buku '" + buku.getJudul() + "' berhasil ditambahkan ke keranjang!");

        } catch (Exception e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }

    public void lihatKeranjang() {
        if (keranjang.isEmpty()) {
            System.out.println("\n Keranjang belanja kosong!");
            return;
        }

        System.out.println("\n" + "=".repeat(60));
        System.out.println("                  KERANJANG BELANJA");
        System.out.println("=".repeat(60));

        List<ItemKeranjang> items = keranjang.getItems();
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i).getInfoItem());
        }

        System.out.println("-".repeat(60));
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(Locale.of("id", "ID"));
        System.out.printf("Total: %45s\n", formatRupiah.format(keranjang.getTotalSementara()));
        System.out.println("=".repeat(60));
    }

    public void hapusDariKeranjang() {
        lihatKeranjang();
        if (keranjang.isEmpty())
            return;

        System.out.print("Masukkan nomor item yang ingin dihapus: ");
        int index = scanner.nextInt();
        scanner.nextLine();

        try {
            keranjang.hapusItem(index - 1);
            System.out.println(" Item berhasil dihapus dari keranjang!");
        } catch (Exception e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }

    public void prosesCheckout() {
        if (keranjang.isEmpty()) {
            System.out.println("\n Keranjang kosong, tidak bisa checkout!");
            return;
        }

        lihatKeranjang();
        System.out.print("Apakah Anda yakin ingin checkout? (y/n): ");
        String konfirmasi = scanner.nextLine();

        if (konfirmasi.equalsIgnoreCase("y")) {
            Pembelian pembelian = new Pembelian("P" + System.currentTimeMillis(), keranjang);

            if (pembelian.prosesCheckout(bukuRepository)) {
                System.out.println("\n CHECKOUT BERHASIL!");
                pembelian.getStruk().cetakStruk();
                keranjang.kosongkanKeranjang();
            } else {
                System.out.println("Checkout gagal!");
            }
        } else {
            System.out.println("Checkout dibatalkan.");
        }
    }

    public void filterBukuByKategori() {
        System.out.println("\nKategori yang tersedia: Fiksi, Teknologi, Sejarah");
        System.out.print("Masukkan kategori: ");
        String kategori = scanner.nextLine();

        try {
            List<Buku> bukuFiltered = bukuRepository.filterByKategori(kategori);

            System.out.println("\n" + "=".repeat(100));
            System.out.println("          BUKU DENGAN KATEGORI: " + kategori.toUpperCase());
            System.out.println("=".repeat(100));
            System.out.printf("%-15s | %-20s | %-15s | %-12s | %-10s | %s\n",
                    "ISBN", "Judul", "Penulis", "Kategori", "Harga", "Stok");
            System.out.println("-".repeat(100));

            for (Buku buku : bukuFiltered) {
                System.out.println(buku.getInfoBuku());
            }
            System.out.println("=".repeat(100));

        } catch (Exception e) {
            System.out.println(" Error: " + e.getMessage());
        }
    }

    public void jalankanAplikasi() {
        System.out.println("Sistem Toko Buku Online AffanSTORE...");

        boolean isRunning = true;
        while (isRunning) {
            tampilkanMenuUtama();
            String pilihan = scanner.nextLine();

            switch (pilihan) {
                case "1":
                    tampilkanDaftarBuku();
                    break;
                case "2":
                    tambahKeKeranjang();
                    break;
                case "3":
                    lihatKeranjang();
                    break;
                case "4":
                    hapusDariKeranjang();
                    break;
                case "5":
                    prosesCheckout();
                    break;
                case "6":
                    filterBukuByKategori();
                    break;
                case "0":
                    isRunning = false;
                    System.out.println("\n Terima kasih telah berbelanja di AffanSTORE!");
                    break;
                default:
                    System.out.println(" Pilihan tidak valid!");
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        TokoBukuApp app = new TokoBukuApp();
        app.jalankanAplikasi();
    }
}