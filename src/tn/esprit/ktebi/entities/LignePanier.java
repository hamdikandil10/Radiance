/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.entities;

import java.util.Objects;

/**
 *
 * @author MSI
 */
public class LignePanier {
    private int id;
    private int livre ;
    private int panier ;
    private int quantite ;
    
    public LignePanier()
    {
        
    }
    
     public LignePanier(int id,int livre, int panier, int quantite) {
         this.id=id;
        this.livre = livre;
        this.panier = panier;
        this.quantite = quantite;
    }


    public LignePanier(int livre, int panier, int quantite) {
        this.livre = livre;
        this.panier = panier;
        this.quantite = quantite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    

    public int getLivre() {
        return livre;
    }

    public void setLivre(int livre) {
        this.livre = livre;
    }

    public int getPanier() {
        return panier;
    }

    public void setPanier(int panier) {
        this.panier = panier;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    @Override
    public String toString() {
        return "LignePanier{" + "livre=" 
                + livre + ", panier=" + panier 
                + ", quantite=" + quantite + '}';
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LignePanier other = (LignePanier) obj;
        if (!Objects.equals(this.livre, other.livre)) {
            return false;
        }
        if (!Objects.equals(this.panier, other.panier)) {
            return false;
        }
        return true;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
}