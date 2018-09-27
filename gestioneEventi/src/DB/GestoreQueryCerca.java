
package DB;

import Model.CittaEnum;
import Model.Cliente;
import Model.Evento;
import Model.LuogoEnum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import java.sql.*;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GestoreQueryCerca {//query generica per cercare tutti gli elementi appena apre la schermata

    public ArrayList<Cliente> cercaCliente(String username){
        ArrayList<Cliente> clienti =new ArrayList<Cliente>();
        Cliente row=null;
        try {
            Connection connection = UtilityDB.getConnessioneDB();
            // Create tutti i risultati in una tabella
            String selectSql =String.format("SELECT * FROM CLIENTE NATURAL JOIN PERSONA ") ;
            //{0}
            if (!username.equals("")) {
                selectSql += "WHERE CLIENTE.USERNAME ='"+username+"'";
            }
            //{1}

            try (Statement statement = connection.createStatement();
                 ResultSet rS = statement.executeQuery(selectSql)) {

                connection.close();
                while (rS.next()) {
                 row.setUsername(rS.getString("USERNAME"));
                 row.setNome(rS.getString("NOME"));
                 row.setCognome(rS.getString("COGNOME"));
                 row.setIndirizzo(rS.getString("INDIRIZZO"));
                 row.setMail(rS.getString("EMAIL"));
                 row.setCF(rS.getString("CODICE_FISCALE"));
                 clienti.add(row);
                }
                return clienti;

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ResultSet cercaImpiegato (String nome, String cognome , Date datanascita){
        try {
            Connection connection = UtilityDB.getConnessioneDB();
            // Create tutti i risultati in una tabella
            String selectSql = "SELECT NOME , COGNOME , CODICE_FISCALE FROM IMPIGATO NATURAL JOIN PERSONA";
            //{1,2,3}
            if (!nome.equals("") && !cognome.equals("") && !datanascita.equals(null)) {
                Format formatter = new SimpleDateFormat("dd-MM-yy");
                String dataRicerca = formatter.format(datanascita);
                selectSql += " WHERE " + "NOME" + "='" + nome+"'" + " AND " + "COGNOME" + "='" + cognome+"'" + " AND DATA=TO_DATE('" + dataRicerca + "','dd-MM-yy');";

                //{1,2}

            }else if (!nome.equals("") && !cognome.equals("") && datanascita.equals(null)) {
                selectSql +=" WHERE " + "NOME" + "='" + nome+"'" + " AND "+ "COGNOME" + "='" + cognome+"'" + ";";
            }
            //{1,3}
            else if (!nome.equals("") && cognome.equals("") && !datanascita.equals(null)) {
                Format formatter = new SimpleDateFormat("dd-MM-yy");
                String dataRicerca = formatter.format(datanascita);
                selectSql += " WHERE " + "NOME" + "='" + nome+"'" + " AND DATA=TO_DATE('" + dataRicerca + "','dd-MM-yy');";
            }
            //{2,3}
            else if (nome.equals("") && !cognome.equals("") && !datanascita.equals(null)) {
                Format formatter = new SimpleDateFormat("dd-MM-yy");
                String dataRicerca = formatter.format(datanascita);
                selectSql += " WHERE " + "COGNOME" + "='" + cognome+"'" + " AND DATA=TO_DATE('" + dataRicerca + "','dd-MM-yy');";
            }
            //{1}
            else if (!nome.equals("") && cognome.equals("") && datanascita.equals(null)) {

                selectSql += " WHERE " + "NOME" + "='" + nome+"'";
            }
            //{2}
            else if (nome.equals("") && !cognome.equals("") && datanascita.equals(null)) {

                selectSql += " WHERE " + "COGNOME" + "='" + cognome+"'" ;
            }
            //{3}
            else if (nome.equals("") && cognome.equals("") && !datanascita.equals(null)) {
                Format formatter = new SimpleDateFormat("dd-MM-yy");
                String dataRicerca = formatter.format(datanascita);
                selectSql += " WHERE " + " DATA=TO_DATE('" + dataRicerca + "','dd-MM-yy');";
            }
            try (Statement statement = connection.createStatement();
                 ResultSet rS = statement.executeQuery(selectSql)) {

                connection.close();
                return rS;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    public List<Evento> cercaEvento (String nomeEvento, LuogoEnum luogoEvento , LocalDate dataEvento){
        List<Evento> listaEventi =new ArrayList<>();
        Evento rigaEvento=null;
        String luogo = luogoEvento.name();
        String selectSql;
        String queryWhere;

        try {
            Connection connection = UtilityDB.getConnessioneDB();
            PreparedStatement preparedStatement=null;
            // Create tutti i risultati in una tabella
             selectSql="SELECT * FROM EVENTO";
            //{1,2,3}
            if ((!(nomeEvento==null) || !nomeEvento.equals("")) && (!luogo.equals("") || !(luogo==null)) && !(dataEvento==null)){
                queryWhere = " WHERE NOME=? AND LUOGO=? AND DATA=? ";
                preparedStatement = connection.prepareStatement(selectSql+queryWhere);
                preparedStatement.setString(1,nomeEvento);
                preparedStatement.setString(2,luogo);
                preparedStatement.setDate(3,Date.valueOf(dataEvento));

            }
            //{1,2}
            else if ((!(nomeEvento==null) || nomeEvento.equals("")) && (!luogo.equals("") || !(luogo==null)) && (dataEvento==null)){
                queryWhere = " WHERE NOME=? AND LUOGO=? ";
                preparedStatement = connection.prepareStatement(selectSql+queryWhere);
                preparedStatement.setString(1,nomeEvento);
                preparedStatement.setString(2,luogo);
            }
            //{1,3}
            else if ((!(nomeEvento==null) || !nomeEvento.equals("")) && (luogo.equals("") || (luogo==null)) && !(dataEvento==null)){
                queryWhere = " WHERE NOME =? AND DATA=? ";
                preparedStatement = connection.prepareStatement(selectSql+queryWhere);
                preparedStatement.setString(1,nomeEvento);
                preparedStatement.setDate(2,Date.valueOf(dataEvento));
            }

            //{2,3}
            else if (((nomeEvento==null) || nomeEvento.equals("")) && (!luogo.equals("") || !(luogo==null)) && !(dataEvento==null)) {
                queryWhere = " WHERE LUOGO = ? AND DATA = ? ";
                preparedStatement = connection.prepareStatement(selectSql+queryWhere);
                preparedStatement.setString(1,luogo);
                preparedStatement.setDate(2,Date.valueOf(dataEvento));
            }
            //{1}
            else if ((!(nomeEvento==null) || !nomeEvento.equals("")) && (luogo.equals("") || (luogo==null)) && (dataEvento==null)) {
                queryWhere = " WHERE NOME = ? ";
                preparedStatement = connection.prepareStatement(selectSql+queryWhere);
                preparedStatement.setString(1,nomeEvento);

            }
            //{2}
            else if (((nomeEvento==null) || nomeEvento.equals("")) && (!luogo.equals("") || !(luogo==null)) && (dataEvento==null)) {
                queryWhere= " WHERE LUOGO = ?";
                preparedStatement = connection.prepareStatement(selectSql+queryWhere);
                preparedStatement.setString(1,luogo);
            }
            //{3}
            else if (((nomeEvento==null) || nomeEvento.equals("")) && (luogo.equals("") || (luogo==null)) && !(dataEvento==null)) {
                queryWhere = " WHERE DATA = ?";
                preparedStatement = connection.prepareStatement(selectSql+queryWhere);
                preparedStatement.setDate(1,Date.valueOf(dataEvento));

            }

            ResultSet rS = preparedStatement.executeQuery(selectSql);


                while(rS.next()){
                    rigaEvento.setCitta(CittaEnum.valueOf(rS.getString("CITTA")));
                    rigaEvento.setCapienzaMassima(rS.getInt("CAPIENZA_EVENTO" ));
                    rigaEvento.setDataEvento(rS.getDate("DATA" ).toLocalDate());
                    rigaEvento.setNome(rS.getString("NOME" ));
                    rigaEvento.setLuogoEvento(LuogoEnum.valueOf(rS.getString("LUOGO" )));
                    rigaEvento.setPrezzoBiglietto(rS.getFloat("PREZZO" ));
                    rigaEvento.setDescrizione(rS.getString("DESCRIZIONE" ));
                    rigaEvento.setGenereEvento(rS.getString("TIPOLOGIA" ));
                    rigaEvento.setIdEvento(rS.getInt("ID"));
                    //non sono sicuro row.setPartecipantiEvento();
                    // Questa informazione è in un altra tabella,
                    // Possiamo settarlo a null e stica'

                    listaEventi.add(rigaEvento);

                }

                rS.close();
                preparedStatement.close();
                connection.close();
                return listaEventi;


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

/*
    public void getAllInfo(TableView tabella,String nomeTabella,String colonnaTW1 ,String colonnaTW2 , String colonnaTW3, String attributo1 ,String attributo2 , Date attributo3 ,ObservableList data) {
        Statement st = null;
        ResultSet rs;
        String driver = "org.h2.Driver";

        try {


            //rs = cerca(nomeTabella,colonnaTW1,colonnaTW2,colonnaTW3,attributo1,attributo2,attributo3);
            while (rs.next()) {
                ObservableList row = FXCollections.observableArrayList();

                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    row.add(rs.getString(i));
                    System.out.println(row);
                }

                data.add(row);

            }
            tabella.setItems(data);

        } catch (Exception e ) {
            // CATCH QUALCOSA
        }
    }
*/

}
