package tagihanlistrik;

/**
 * Subclass: pelanggan golongan Rumah Tangga.
 * Mewarisi seluruh atribut dan perilaku dari Pelanggan (Inheritance).
 */
public class PelangganRumahTangga extends Pelanggan {

    private static final double TARIF_PER_KWH = 1444.70;
    private static final double BIAYA_BEBAN = 15000;

    public PelangganRumahTangga(String idPelanggan, String nama, String alamat, int dayaVA) {
        super(idPelanggan, nama, alamat, dayaVA);
    }

    @Override
    public String getGolongan() {
        return "Rumah Tangga";
    }

    @Override
    protected double getTarifPerKwh() {
        return TARIF_PER_KWH;
    }

    @Override
    protected double getBiayaBeban() {
        return BIAYA_BEBAN;
    }
}
