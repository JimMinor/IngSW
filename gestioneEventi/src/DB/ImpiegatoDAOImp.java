package DB;

import Model.Impiegato;
import Model.MansioneEnum;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Collection;


public class ImpiegatoDAOImp implements ImpiegatoDAO {

    private Impiegato impiegatoDaInserire;
    private char mansioneDaInserire;

    public ImpiegatoDAOImp(){

    }

    public ImpiegatoDAOImp(Impiegato impiegatoDaInserire) {
        this.impiegatoDaInserire = impiegatoDaInserire;
    }

    @Override
    public boolean inserisciImpiegato() throws SQLException {

        if (impiegatoDaInserire.getAmministratore() == MansioneEnum.Amministratore)
            mansioneDaInserire = 'V';
        else if (impiegatoDaInserire.getAmministratore() == MansioneEnum.Utente)
            mansioneDaInserire = 'X';

        Connection connection = UtilityDB.getConnessioneDB();
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO ADMIN.PERSONA (CODICE_FISCALE,NOME,COGNOME,DATA_NASCITA)" +
                " VALUES ('" + impiegatoDaInserire.getCF() + "'," +
                "'" + impiegatoDaInserire.getNome() + "'," +
                "'" + impiegatoDaInserire.getCognome() + "'," +
                "TO_DATE('" + impiegatoDaInserire.getDataNascita() + "','YYYY-MM-DD'))");
        statement.executeUpdate("INSERT INTO ADMIN.IMPIEGATO (ID,CODICE_FISCALE,USERNAME,PASSWORD,DATA_ASSUNZIONE,STIPENDIO,EMAIL,TELEFONO,IBAN,ADMIN)" +
                " VALUES ('" + impiegatoDaInserire.getId() + "'," +
                "'" + impiegatoDaInserire.getCF() + "'," +
                "'" + impiegatoDaInserire.getUsername() + "'," +
                "'" + impiegatoDaInserire.getPassword() + "'," +
                "TO_DATE('" + impiegatoDaInserire.getDataAssunzione() + "','YYYY-MM-DD')," +
                "'" + impiegatoDaInserire.getStipendio().toString().replace('.',',') + "'," +
                "'" + impiegatoDaInserire.getEmail() + "'," +
                "'" + impiegatoDaInserire.getTelefono() + "'," +
                "'" + impiegatoDaInserire.getIban() + "'," +
                "'" + mansioneDaInserire + "')");
        connection.close();
        return true;
    }

    @Override
    public Collection<Impiegato> cercaImpiegato(String nome, String cognome, LocalDate dataNascita) throws Exception {
        return null;
    }

    @Override
    public boolean eliminaImpiegato(Impiegato impiegatoDaInserire)  {
        return true;
    }

}
