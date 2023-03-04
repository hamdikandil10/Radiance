/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import tn.esprit.ktebi.entities.Livre;
import tn.esprit.ktebi.entities.Promo;
import tn.esprit.ktebi.utils.MaConnexion;

/**
 *
 * @author MSI
 */
public class PromoService implements IPromoService<Promo> {
    
    private Connection cnx ;
    
    public PromoService()
    {
        cnx = MaConnexion.getInstance().getCnx();
    }

    @Override
    public void create(Promo t) throws SQLException {
        String req= " INSERT INTO`promo`(`date_expiration`)"
                + "VALUES(?)";
        
         PreparedStatement ps = cnx.prepareStatement(req);
         String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
         ps.setString(1, date);
         ps.executeUpdate();  
        
       
        
    }

    @Override
    public void update(Promo t) throws SQLException {
        String req= "UPDATE `promo` SET`date_expiration`=?"  ;
        
         PreparedStatement ps = cnx.prepareStatement(req);
         String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
         ps.setString(1, date);
         ps.executeUpdate();  
         System.out.println("Code Promo modifié !");
    }

    @Override
    public void delete(int id) throws SQLException {
        String req="DELETE FROM `promo` WHERE  code_promo = ?";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setInt(1,id);
        int row = ps.executeUpdate(); 
        if(row==1)
        {
            System.out.println("Code promo exprimé !");
        }
        else
        {
           System.out.println(row); 
        }
    }

    @Override
    public List<Promo> selectAll() throws SQLException {
       List<Promo> temp = new ArrayList<>();
        String req="SELECT * FROM `promo` ";
        
        PreparedStatement ps = cnx.prepareStatement(req);
        
        ResultSet rs = ps.executeQuery() ;
        while(rs.next())
        {
            Promo p = new Promo();
            p.setId(rs.getInt(1));
            p.setDate_fin(rs.getDate(2));
            p.setCode(rs.getString(3));
            p.setReduction(rs.getDouble(4));
            p.setDate_debut(rs.getDate(5));
            
                    
            temp.add(p);
        }
        return temp ;
    }

    @Override
    public Promo selectById(int id) throws SQLException {
        
        Promo promo = null;

        String req="SELECT * FROM `promo` WHERE id=?";
        
        PreparedStatement ps = cnx.prepareStatement(req);

        ps.setInt(1, id);
            
        ResultSet rs = ps.executeQuery() ;
        
        while(rs.next())
        {
            promo = new Promo();
                promo.setId(rs.getInt("id"));
                promo.setCode(rs.getString("code"));
                promo.setReduction(rs.getDouble("reduction"));
                promo.setDate_debut(rs.getDate("date_debut"));
                promo.setDate_fin(rs.getDate("date_fin"));
        }
        
             
        return promo;
    }

  



    }
    

