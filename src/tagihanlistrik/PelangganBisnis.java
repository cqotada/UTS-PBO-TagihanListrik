package tagihanlistrik;

/**
 * Subclass: pelanggan golongan Bisnis (toko, kantor, warung).
 * Selain mewarisi Pelanggan, class ini menambah atribut sendiri: namaUsaha.
 */
public class PelangganBisnis extends Pelanggan {

    private static final double TARIF_PER_KWH = 1699.53;
    private static final double BIAYA_BEBAN = 50000;

    private String namaUsaha;

    public PelangganBisnis(String idPelanggan, String nama, String alamat, int dayaVA, String namaUsaha) {
        super(idPelanggan, nama, alamat, dayaVA);
        setNamaUsaha(namaUsaha);
    }

    public String getNamaUsaha() {
        return namaUsaha;
    }

    public void setNamaUsaha(String namaUsaha) {
        if (namaUsaha == null || namaUsaha.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama usaha tidak boleh kosong.");
        }
        this.namaUsaha = namaUsaha.trim();
    }

    @Override
    public String getGolongan() {
        return "Bisnis";
    }

    @Override
    protected double getTarifPerKwh() {
        return TARIF_PER_KWH;
    }

    @Override
    protected double getBiayaBeban() {
        return BIAYA_BEBAN;
    }

    // Override hook untuk menambahkan informasi nama usaha pada tagihan
    @Override
    protected String infoTambahan() {
        return "Nama Usaha     : " + namaUsaha + "\n";
    }
}
