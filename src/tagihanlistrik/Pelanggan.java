package tagihanlistrik;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Superclass abstrak untuk semua jenis pelanggan listrik.
 *
 * Prinsip yang diterapkan:
 *  1. Access Modifier / Encapsulation
 *     - atribut dibuat private, hanya bisa diakses lewat getter/setter
 *     - setter memvalidasi data sebelum disimpan
 *     - method yang hanya dipakai subclass dibuat protected
 *  2. Inheritance
 *     - PelangganRumahTangga, PelangganBisnis, dan PelangganIndustri
 *       mewarisi class ini dengan "extends"
 */
public abstract class Pelanggan {

    // Konstanta tarif pajak penerangan jalan (PPJ) 10%
    private static final double PPJ = 0.10;

    // ===== Atribut private (Encapsulation) =====
    private String idPelanggan;
    private String nama;
    private String alamat;
    private int dayaVA;
    private int meterAwal;
    private int meterAkhir;

    // ===== Constructor =====
    public Pelanggan(String idPelanggan, String nama, String alamat, int dayaVA) {
        setIdPelanggan(idPelanggan);
        setNama(nama);
        setAlamat(alamat);
        setDayaVA(dayaVA);
    }

    // ===== Getter =====
    public String getIdPelanggan() {
        return idPelanggan;
    }

    public String getNama() {
        return nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public int getDayaVA() {
        return dayaVA;
    }

    public int getMeterAwal() {
        return meterAwal;
    }

    public int getMeterAkhir() {
        return meterAkhir;
    }

    // ===== Setter dengan validasi =====
    public void setIdPelanggan(String idPelanggan) {
        if (idPelanggan == null || idPelanggan.trim().isEmpty()) {
            throw new IllegalArgumentException("ID pelanggan tidak boleh kosong.");
        }
        this.idPelanggan = idPelanggan.trim();
    }

    public void setNama(String nama) {
        if (nama == null || nama.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama tidak boleh kosong.");
        }
        this.nama = nama.trim();
    }

    public void setAlamat(String alamat) {
        if (alamat == null || alamat.trim().isEmpty()) {
            throw new IllegalArgumentException("Alamat tidak boleh kosong.");
        }
        this.alamat = alamat.trim();
    }

    public void setDayaVA(int dayaVA) {
        if (dayaVA <= 0) {
            throw new IllegalArgumentException("Daya harus lebih dari 0 VA.");
        }
        this.dayaVA = dayaVA;
    }

    /**
     * Mengisi pembacaan meter bulan ini.
     * Meter akhir tidak boleh lebih kecil dari meter awal.
     */
    public void setPembacaanMeter(int meterAwal, int meterAkhir) {
        if (meterAwal < 0 || meterAkhir < 0) {
            throw new IllegalArgumentException("Angka meter tidak boleh negatif.");
        }
        if (meterAkhir < meterAwal) {
            throw new IllegalArgumentException("Meter akhir tidak boleh lebih kecil dari meter awal.");
        }
        this.meterAwal = meterAwal;
        this.meterAkhir = meterAkhir;
    }

    // ===== Method abstrak: wajib di-override oleh subclass =====

    /** Nama golongan tarif, contoh: "Rumah Tangga". */
    public abstract String getGolongan();

    /** Tarif per kWh (Rp) sesuai golongan. */
    protected abstract double getTarifPerKwh();

    /** Biaya beban tetap per bulan (Rp) sesuai golongan. */
    protected abstract double getBiayaBeban();

    // ===== Perhitungan tagihan =====

    public int hitungPemakaianKwh() {
        return meterAkhir - meterAwal;
    }

    public double hitungBiayaPemakaian() {
        return hitungPemakaianKwh() * getTarifPerKwh();
    }

    // protected: hanya perlu dipanggil di dalam class turunan / package
    protected double hitungPajak() {
        return (hitungBiayaPemakaian() + getBiayaBeban()) * PPJ;
    }

    public double hitungTotalTagihan() {
        return hitungBiayaPemakaian() + getBiayaBeban() + hitungPajak();
    }

    // ===== Utilitas tampilan =====

    protected static String rupiah(double nilai) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("id-ID"));
        nf.setMaximumFractionDigits(0);
        return nf.format(nilai);
    }

    // Tarif ditampilkan dengan 2 desimal agar sesuai tarif aslinya
    protected static String rupiahDesimal(double nilai) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("id-ID"));
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        return nf.format(nilai);
    }

    /** Hook: subclass boleh menambah baris informasi (default kosong). */
    protected String infoTambahan() {
        return "";
    }

    public String cetakTagihan() {
        StringBuilder sb = new StringBuilder();
        sb.append("==========================================\n");
        sb.append("           TAGIHAN LISTRIK BULANAN        \n");
        sb.append("==========================================\n");
        sb.append(String.format("ID Pelanggan   : %s%n", idPelanggan));
        sb.append(String.format("Nama           : %s%n", nama));
        sb.append(String.format("Alamat         : %s%n", alamat));
        sb.append(String.format("Golongan       : %s (%d VA)%n", getGolongan(), dayaVA));
        sb.append(infoTambahan());
        sb.append("------------------------------------------\n");
        sb.append(String.format("Meter Awal     : %d%n", meterAwal));
        sb.append(String.format("Meter Akhir    : %d%n", meterAkhir));
        sb.append(String.format("Pemakaian      : %d kWh%n", hitungPemakaianKwh()));
        sb.append(String.format("Tarif / kWh    : %s%n", rupiahDesimal(getTarifPerKwh())));
        sb.append("------------------------------------------\n");
        sb.append(String.format("Biaya Pemakaian: %s%n", rupiah(hitungBiayaPemakaian())));
        sb.append(String.format("Biaya Beban    : %s%n", rupiah(getBiayaBeban())));
        sb.append(String.format("PPJ (10%%)      : %s%n", rupiah(hitungPajak())));
        sb.append("==========================================\n");
        sb.append(String.format("TOTAL TAGIHAN  : %s%n", rupiah(hitungTotalTagihan())));
        sb.append("==========================================\n");
        return sb.toString();
    }
}
