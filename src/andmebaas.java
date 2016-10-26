import java.sql.*;

/**
 * Created by toomas on 26.10.16.
 */
public class andmebaas {
    private static Connection conn = null;
    private static boolean hasData = false;

    public ResultSet displayUsers() throws SQLException, ClassNotFoundException {
        if(conn ==null){
            getConnection();
        }
        Statement state = conn.createStatement();
        ResultSet res = state.executeQuery("SELECT fname, score FROM highscore");
        return res;
    }
    private void getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:highscore.db");
        initialise();
    }

    private void initialise() throws SQLException {
        if(!hasData){
            hasData = true;

            Statement state = conn.createStatement();
            ResultSet rs = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='highscore'");
            if(!rs.next()){
                System.out.println("building the highscore table with prepopulated values.");
                Statement state2 = conn.createStatement();
                state2.execute("CREATE TABLE highscore(id integer,"
                + "fname varchar(60)," + "score varchar(60);");

                PreparedStatement prep = conn.prepareStatement("INSERT INTO highscore values(?, ?, ?);");
                prep.setString(2, "Toomas");
                prep.setString(3, "96");
                prep.execute();

                PreparedStatement prep2 = conn.prepareStatement("INSERT INTO highscore values(?, ?, ?);");
                prep2.setString(2, "Mihkel");
                prep2.setString(3, "37");
                prep2.execute();


            }



        }
    }
    public void addUser(String firstname, String score) throws SQLException, ClassNotFoundException {
        if(conn == null){
            getConnection();
        }

        PreparedStatement prep = conn.prepareStatement("INSERT INTO highscore values(?,?,?);");
        prep.setString(2, firstname);
        prep.setString(3, score);
        prep.execute();

    }


}
