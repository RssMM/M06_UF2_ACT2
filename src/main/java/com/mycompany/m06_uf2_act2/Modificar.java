package com.mycompany.m06_uf2_act2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Modificar {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/cinema";
    static final String USER = "root";
    static final String PASS = "1234";
    
    
    public static void main(String[] args) {
        
         //getFilmsInterval();
        //getFilmsByDirector();
        insertNewFilm();
    }
    
    public static int getColumnNames(ResultSet rs) throws SQLException {
        
        int numberOfColumns = 0;
        if (rs != null) {
            ResultSetMetaData rsMetaData = rs.getMetaData();
            numberOfColumns = rsMetaData.getColumnCount();
            for (int i = 1; i < numberOfColumns + 1; i++) {
                String columnName = rsMetaData.getColumnName(i);
                System.out.print(columnName + ", ");
            }//endfor
        }//endif
        System.out.println();
        return numberOfColumns;
    }
    
    public static void getFilmsInterval(){
        System.out.println("Selecciona inici: ");  
        Scanner input = new Scanner(System.in); 
        int inici = input.nextInt();  
        System.out.println("Selecciona fi: ");  
        int fi = input.nextInt();

        
        try(Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement st = con.createStatement();) {

            String Sql = "SELECT * FROM FILMS WHERE YEAR(dataEstrena) BETWEEN " + inici + " AND " + fi ;
            ResultSet rs = st.executeQuery(Sql);
            int colNum = getColumnNames(rs);
            if(colNum>0) {
                while(rs.next()) {
                    for(int i =0; i<colNum; i++) {
                        if(i+1 == colNum) {
                            System.out.println(rs.getString(i+1));
                        } else {
                            System.out.print(rs.getString(i+1)+ ", ");
                        }
                    }//endfor
                }//endwhile
            }
            
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    
    public static void getFilmsByDirector(){
        
        System.out.println("Selecciona el nombre del director: ");  
        Scanner input = new Scanner(System.in); 
        String Nom = input.next();  
        
        try(Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement st = con.createStatement();) {
           
            

            String checkSql = "SELECT * FROM FILMS f INNER JOIN DIRECTOR d ON f.idDirector = d.idDirector WHERE d.Nom like \"%" + Nom +  "%\"" ;
            ResultSet rs = st.executeQuery(checkSql);
            int colNum = getColumnNames(rs);
            if(colNum>0) {
                while(rs.next()) {
                    for(int i =0; i<colNum; i++) {
                        if(i+1 == colNum) {
                            System.out.println(rs.getString(i+1));
                        } else {
                            System.out.print(rs.getString(i+1)+ ", ");
                        }
                    }//endfor
                }//endwhile
            }
            
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public static void insertNewFilm(){
        
         System.out.println("inserta nueva fila en Films: ");  
        Scanner input = new Scanner(System.in); 
        System.out.println("Titol: ");  
        String Titol = input.next();  
        System.out.println("DataEstrena: ");  
        String DataEstrena = input.next();
        System.out.println("pais: ");  
        String pais = input.next();
        System.out.println("idDirector: ");  
        int idDirector = input.nextInt();
        System.out.println("insertando");
        try(Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement st = con.createStatement();) {
           
            
            String sql = "INSERT INTO Films(Titol , DataEstrena, Pais, idDirector) " +
                   "VALUES ('" + Titol +  "','" + DataEstrena + "','" + pais+ "','" + idDirector + "')";
            
            
            
            String checkSql = "SELECT Titol ,DataEstrena, Pais, idDirector FROM Films " +
                        "WHERE Titol = '" +  Titol + "' AND DataEstrena = '" + DataEstrena + "' AND Pais = '" + pais   + "' AND idDirector = '" + idDirector + "'" ;
            ResultSet rs = st.executeQuery(checkSql);
            System.out.println("comprobando");
            if (rs.next()) {
                System.out.println("ya existe!");
            } else {
                st.executeUpdate(sql);
                System.out.println("nueva fila en Films");
            }       
            
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
