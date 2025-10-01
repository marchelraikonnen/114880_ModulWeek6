package tugasweek06.marchel.id.ac.umn;

import java.util.ArrayList;
import java.util.Scanner;

public class Main{
    static ArrayList<Handphone> daftarHP = new ArrayList<>();
    static ArrayList<Voucher> daftarVoucher = new ArrayList<>();
    static ArrayList<Order> daftarOrder = new ArrayList<>();
    static Scanner in = new Scanner(System.in);

    public static void seedData() {
        daftarHP.add(new Handphone(1, "Samsung S9+ Hitam", 14499000, 10, "Hitam"));
        daftarHP.add(new Handphone(2, "iPhone X Hitam", 17999000, 10, "Hitam"));
        daftarVoucher.add(new Voucher(1, "Google Play 20000", 20000, 100, 0.10));
    }

    public static void mainMenu() {
        System.out.println("------------Menu Toko Voucher & HP------------");
        System.out.println("1. Pesan Barang");
        System.out.println("2. Lihat Pesanan");
        System.out.println("3. Barang Baru");
        System.out.print("Pilihan : ");
    }

    public static void listHP() {
        System.out.println("Daftar Barang Toko Voucher & HP");
        System.out.println("1. Handphone");
        System.out.println("2. Voucher");
    }

    public static Handphone findHPById(int id) {
        for (Handphone h : daftarHP) {
            if (h.getId() == id) return h;
        }
        return null;
    }

    public static Voucher findVoucherById(int id) {
        for (Voucher v : daftarVoucher) {
            if (v.getId() == id) return v;
        }
        return null;
    }

    public static void showHandphones() {
        System.out.println("Daftar Handphone Toko Voucher & HP");
        for (Handphone h : daftarHP) {
            System.out.println("ID    : " + h.getId());
            System.out.println("Nama  : " + h.getNama());
            System.out.println("Stok  : " + h.getStok());
            System.out.println("Harga : " + String.format("%.0f", h.getHarga()));
            System.out.println("-----------------------------------------");
        }
    }

    public static void showVouchers() {
        System.out.println("Daftar Voucher Toko Voucher & HP");
        for (Voucher v : daftarVoucher) {
            System.out.println("ID      : " + v.getId());
            System.out.println("Nama    : " + v.getNama());
            System.out.println("Nominal : " + String.format("%.0f", v.getHarga()));
            System.out.println("Stok    : " + v.getStok());
            System.out.println("Harga   : " + String.format("%.0f", v.getHargaJual()));
            System.out.println("-----------------------------------------");
        }
    }

    public static void pesanBarang() {
        listHP();
        System.out.print("Pilihan : ");
        int tipe = readInt();
        if (tipe != 1 && tipe != 2) {
            System.out.println("Pilihan tidak valid");
            return;
        }
        if (tipe == 1) {
            showHandphones();
            System.out.println("Ketik 0 untuk batal");
            System.out.print("Pesan Barang (ID) : ");
            int id = readInt();
            if (id == 0) return;
            Handphone hp = findHPById(id);
            if (hp == null) {
                System.out.println("Barang tidak tersedia");
                return;
            }
            int qty;
            while (true) {
                System.out.print("Masukkan Jumlah : ");
                qty = readInt();
                if (qty <= 0) {
                    System.out.println("Jumlah harus lebih dari 0");
                    continue;
                }
                if (qty > hp.getStok()) {
                    System.out.println("Stok tidak mencukupi jumlah pesanan");
                    return;
                }
                break;
            }
            double totalHarga = qty * hp.getHarga();
            System.out.println(qty + " @ " + hp.getNama() + " dengan total harga " + String.format("%.0f", totalHarga));
            System.out.print("Masukkan jumlah uang : ");
            double uang = readDouble();
            if (uang < totalHarga) {
                System.out.println("Jumlah uang tidak mencukupi");
                return;
            }
            hp.minusStok(qty);
            String oid = "O" + (daftarOrder.size() + 1);
            Order o = new Order(oid, hp, qty);
            daftarOrder.add(o);
            System.out.println("Berhasil dipesan");
        } else {
            showVouchers();
            System.out.println("Ketik 0 untuk batal");
            System.out.print("Pesan Barang (ID) : ");
            int id = readInt();
            if (id == 0) return;
            Voucher v = findVoucherById(id);
            if (v == null) {
                System.out.println("Barang tidak tersedia");
                return;
            }
            int qty;
            while (true) {
                System.out.print("Masukkan Jumlah : ");
                qty = readInt();
                if (qty <= 0) {
                    System.out.println("Jumlah harus lebih dari 0");
                    continue;
                }
                if (qty > v.getStok()) {
                    System.out.println("Stok tidak mencukupi jumlah pesanan");
                    return;
                }
                break;
            }
            double totalHarga = qty * v.getHargaJual();
            System.out.println(qty + " @ " + v.getNama() + " dengan harga per item " + String.format("%.0f", v.getHargaJual()) + " dan total harga " + String.format("%.0f", totalHarga));
            System.out.print("Masukkan jumlah uang : ");
            double uang = readDouble();
            if (uang < totalHarga) {
                System.out.println("Jumlah uang tidak mencukupi");
                return;
            }
            v.minusStok(qty);
            String oid = "O" + (daftarOrder.size() + 1);
            Order o = new Order(oid, v, qty);
            daftarOrder.add(o);
            System.out.println("Berhasil dipesan");
        }
    }

    public static void lihatPesanan() {
        if (daftarOrder.isEmpty()) {
            System.out.println("Belum ada pesanan");
            return;
        }
        System.out.println("Daftar Pesanan Toko Multiguna");
        for (Order o : daftarOrder) {
            System.out.println("ID     : " + o.getId());
            System.out.println("Nama   : " + o.getBarang().getNama());
            System.out.println("Jumlah : " + o.getJumlah());
            System.out.println("Total  : " + String.format("%.0f", o.getTotal()));
            System.out.println("-----------------------------------------");
        }
    }

    public static void barangBaru() {
        System.out.print("Voucher / Handphone (V/H): ");
        String t = in.nextLine().trim().toLowerCase();
        if (t.equals("v")) {
            System.out.print("ID : ");
            int id = readInt();
            System.out.print("Nama : ");
            String nama = in.nextLine();
            System.out.print("Harga : ");
            double harga = readDouble();
            System.out.print("Stok : ");
            int stok = readInt();
            System.out.print("PPN (contoh 0.1 untuk 10%) : ");
            double pajak = readDouble();
            Voucher v = new Voucher(id, nama, harga, stok, pajak);
            daftarVoucher.add(v);
            System.out.println("Voucher telah berhasil diinput");
        } else if (t.equals("h")) {
            System.out.print("ID : ");
            int id = readInt();
            System.out.print("Nama : ");
            String nama = in.nextLine();
            System.out.print("Harga : ");
            double harga = readDouble();
            System.out.print("Stok : ");
            int stok = readInt();
            System.out.print("Warna : ");
            String warna = in.nextLine();
            Handphone h = new Handphone(id, nama, harga, stok, warna);
            daftarHP.add(h);
            System.out.println("Handphone telah berhasil diinput");
        } else {
            System.out.println("Input tidak valid");
        }
    }

    public static int readInt() {
        while (true) {
            try {
                int v = Integer.parseInt(in.nextLine().trim());
                return v;
            } catch (Exception e) {
                System.out.print("Input harus angka, coba lagi: ");
            }
        }
    }

    public static double readDouble() {
        while (true) {
            try {
                double v = Double.parseDouble(in.nextLine().trim());
                return v;
            } catch (Exception e) {
                System.out.print("Input harus angka, coba lagi: ");
            }
        }
    }

    public static void main(String[] args) {
        seedData();
        for (;;) {
            mainMenu();
            int pilihan = readInt();
            switch (pilihan) {
                case 1:
                    pesanBarang();
                    break;
                case 2:
                    lihatPesanan();
                    break;
                case 3:
                    barangBaru();
                    break;
                default:
                    System.out.println("Input Tidak Valid");
            }
        }
    }
}
