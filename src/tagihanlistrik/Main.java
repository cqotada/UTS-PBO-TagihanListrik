package tagihanlistrik;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Program utama: Aplikasi Penghitung Tagihan Listrik (berbasis konsol).
 */
public class Main {

    private static final List<Pelanggan> daftar = new ArrayList<>();
    private static final Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        boolean jalan = true;
        while (jalan) {
            tampilkanMenu();
            String pilihan = input.nextLine().trim();
            switch (pilihan) {
                case "1":
                    tambahPelanggan();
                    break;
                case "2":
                    lihatDaftar();
                    break;
                case "3":
                    cetakTagihan();
                    break;
                case "0":
                    jalan = false;
                    System.out.println("Terima kasih. Program selesai.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        }
    }

    private static void tampilkanMenu() {
        System.out.println();
        System.out.println("=== APLIKASI PENGHITUNG TAGIHAN LISTRIK ===");
        System.out.println("1. Tambah pelanggan & hitung tagihan");
        System.out.println("2. Lihat daftar pelanggan");
        System.out.println("3. Cetak tagihan pelanggan");
        System.out.println("0. Keluar");
        System.out.print("Pilih menu: ");
    }

    private static void tambahPelanggan() {
        try {
            System.out.println("Golongan: 1) Rumah Tangga  2) Bisnis  3) Industri");
            System.out.print("Pilih golongan: ");
            String gol = input.nextLine().trim();

            System.out.print("ID Pelanggan : ");
            String id = input.nextLine();
            System.out.print("Nama         : ");
            String nama = input.nextLine();
            System.out.print("Alamat       : ");
            String alamat = input.nextLine();
            System.out.print("Daya (VA)    : ");
            int daya = Integer.parseInt(input.nextLine().trim());

            Pelanggan p;
            switch (gol) {
                case "1":
                    p = new PelangganRumahTangga(id, nama, alamat, daya);
                    break;
                case "2":
                    System.out.print("Nama Usaha   : ");
                    String usaha = input.nextLine();
                    p = new PelangganBisnis(id, nama, alamat, daya, usaha);
                    break;
                case "3":
                    p = new PelangganIndustri(id, nama, alamat, daya);
                    break;
                default:
                    System.out.println("Golongan tidak valid.");
                    return;
            }

            System.out.print("Meter awal   : ");
            int awal = Integer.parseInt(input.nextLine().trim());
            System.out.print("Meter akhir  : ");
            int akhir = Integer.parseInt(input.nextLine().trim());
            p.setPembacaanMeter(awal, akhir);

            daftar.add(p);
            System.out.println("\nData tersimpan.\n");
            System.out.println(p.cetakTagihan()); // polymorphism
        } catch (NumberFormatException e) {
            System.out.println("Input harus berupa angka. Data dibatalkan.");
        } catch (IllegalArgumentException e) {
            System.out.println("Data tidak valid: " + e.getMessage());
        }
    }

    private static void lihatDaftar() {
        if (daftar.isEmpty()) {
            System.out.println("Belum ada data pelanggan.");
            return;
        }
        System.out.println();
        System.out.printf("%-4s %-10s %-18s %-14s %12s%n", "No", "ID", "Nama", "Golongan", "kWh");
        int no = 1;
        for (Pelanggan p : daftar) {
            System.out.printf("%-4d %-10s %-18s %-14s %12d%n",
                    no++, p.getIdPelanggan(), p.getNama(), p.getGolongan(), p.hitungPemakaianKwh());
        }
    }

    private static void cetakTagihan() {
        System.out.print("Masukkan ID pelanggan: ");
        String id = input.nextLine().trim();
        for (Pelanggan p : daftar) {
            if (p.getIdPelanggan().equalsIgnoreCase(id)) {
                System.out.println();
                System.out.println(p.cetakTagihan());
                return;
            }
        }
        System.out.println("Pelanggan dengan ID tersebut tidak ditemukan.");
    }
}
