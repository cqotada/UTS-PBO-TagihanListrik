package tagihanlistrik;

/**
 * Subclass: pelanggan golongan Industri.
 * Memiliki aturan tambahan: biaya beban dihitung dari daya terpasang.
 */
public class PelangganIndustri extends Pelanggan {

    private static final double TARIF_PER_KWH = 1114.74;
    private static final double BIAYA_BEBAN_PER_KVA = 35000;

    public PelangganIndustri(String idPelanggan, String nama, String alamat, int dayaVA) {
        super(idPelanggan, nama, alamat, dayaVA);
    }

    @Override
    public String getGolongan() {
        return "Industri";
    }

    @Override
    protected double getTarifPerKwh() {
        return TARIF_PER_KWH;
    }

    // Biaya beban industri = Rp35.000 x daya dalam kVA
    @Override
    protected double getBiayaBeban() {
        double kva = getDayaVA() / 1000.0;
        return BIAYA_BEBAN_PER_KVA * kva;
    }
}
