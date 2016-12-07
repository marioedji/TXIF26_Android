package fr.utt.adrien.helloworld.model;

/**
 * Created by mario on 06/12/2016.
 */

public class Livraison {
    private int idLivraison;
    private String libelleLivraison;
    private String lieu;
    private String dateDeFin;
    private String statutActuel;

    public Livraison() {
    }

    public int getIdLivraison() {
        return idLivraison;
    }

    public void setIdLivraison(int idLivraison) {
        this.idLivraison = idLivraison;
    }

    public String getLibelleLivraison() {
        return libelleLivraison;
    }

    public void setLibelleLivraison(String libelleLivraison) {
        this.libelleLivraison = libelleLivraison;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getDateDeFin() {
        return dateDeFin;
    }

    public void setDateDeFin(String dateDeFin) {
        this.dateDeFin = dateDeFin;
    }

    public String getStatutActuel() {
        return statutActuel;
    }

    public void setStatutActuel(String statutActuel) {
        this.statutActuel = statutActuel;
    }

    @Override
    public String toString() {
        return dateDeFin;
    }
}
