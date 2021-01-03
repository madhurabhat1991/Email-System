
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author madhu
 */
public class SQL_Database implements DataStorage {

    private String DATABASE_URL;
    private String db_id;
    private String db_psw;
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    @Override
    public void initializeDatabase() {
        DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/java_db?useSSL=false";
        db_id = "root";
        db_psw = "madhu123";

        connection = null;
        statement = null;
        resultSet = null;
    }

    @Override
    public boolean uniqueEmail(String email) {
        initializeDatabase();

        try {
            connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
            statement = connection.createStatement();

            resultSet = statement.executeQuery("select email from user "
                    + "where email = '" + email + "'");

            while (resultSet.next()) {
                return false;
            }
            return true;

        } catch (SQLException e) {
            System.out.println("Error while checking if email id is unique");
            e.printStackTrace();
            return false;

        } finally {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String createAccount(String email, String name, String password) {
        initializeDatabase();

        try {
            connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
            statement = connection.createStatement();

            connection.setAutoCommit(false);

            int r = statement.executeUpdate("insert into user values "
                    + "('" + email + "', '" + name + "', '" + password + "')");

            connection.commit();
            connection.setAutoCommit(true);
            return ("Account creation is successful");

        } catch (SQLException e) {
            e.printStackTrace();
            return ("Error during account creation");
        } finally {
            try {
                //resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String login(String email, String password) {
        initializeDatabase();

        try {
            connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
            statement = connection.createStatement();

            resultSet = statement.executeQuery("select * from user "
                    + "where email = '" + email + "'");

            if (resultSet.next()) {
                //email is present - compare password
                if (password.equals(resultSet.getString("password"))) {
                    return "inbox";
                } else {
                    return "loginFailed";
                }
            } else {
                return "loginFailed";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "internalError";

        } finally {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getUser(String email) {
        initializeDatabase();

        try {
            connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
            statement = connection.createStatement();

            resultSet = statement.executeQuery("select name from user "
                    + "where email = '" + email + "'");

            if (resultSet.next()) {
                //email is present - get name
                return resultSet.getString("name");
            } else {
                return "loginFailed";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "internalError";

        } finally {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String sendEmail(String to, String title, String body, String from, int replyFor) {
        initializeDatabase();

        try {
            connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
            statement = connection.createStatement();

            int id = 0;
            resultSet = statement.executeQuery("select MAX(id) from email ");

            if (resultSet.next()) {
                //id is present
                id = resultSet.getInt(1) + 1;
            } else {
                id = 1;
            }

            connection.setAutoCommit(false);
            int r = statement.executeUpdate("insert into email values \n"
                    + "(" + id + ", '" + title.replace("'", "\\'") + "', '" + body.replace("'", "\\'") + "', "
                    + "'" + from + "', '" + to + "', '" + DateAndTime.DateTime() + "', " + replyFor + ", "
                    + "'new')");

            connection.commit();
            connection.setAutoCommit(true);
            return ("sendConfirmation");

        } catch (SQLException e) {
            e.printStackTrace();
            return ("internalError");
        } finally {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ArrayList<EmailBody> getInboxEmails(String email) {
        initializeDatabase();
        ArrayList<EmailBody> emails = new ArrayList<>();

        try {
            connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
            statement = connection.createStatement();

            resultSet = statement.executeQuery("select * from email \n"
                    + "where receiver = '" + email + "'\n"
                    + "and status != 'delete'\n"
                    + "order by id desc ");

            while (resultSet.next()) {
                emails.add(new EmailBody(resultSet.getInt("id"), resultSet.getString("title"),
                        resultSet.getString("body"), resultSet.getString("sender"),
                        resultSet.getString("receiver"), resultSet.getString("date"),
                        resultSet.getInt("reply_for"), resultSet.getString("status")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("internalError");

        } finally {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return emails;
    }

    @Override
    public EmailBody getOriginalEmail(int replyFor) {
        initializeDatabase();
        EmailBody originalEmail = null;

        try {
            connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
            statement = connection.createStatement();

            resultSet = statement.executeQuery("select * from email \n"
                    + "where id = " + replyFor + "");

            if (resultSet.next()) {
                originalEmail = new EmailBody(resultSet.getInt("id"), resultSet.getString("title"),
                        resultSet.getString("body"), resultSet.getString("sender"),
                        resultSet.getString("receiver"), resultSet.getString("date"),
                        resultSet.getInt("reply_for"), resultSet.getString("status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("internalError");

        } finally {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return originalEmail;
    }

    @Override
    public ArrayList<EmailBody> getSentEmails(String email) {
        initializeDatabase();
        ArrayList<EmailBody> emails = new ArrayList<>();

        try {
            connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
            statement = connection.createStatement();

            resultSet = statement.executeQuery("select * from email \n"
                    + "where sender = '" + email + "'\n"
                    + "order by id desc ");

            while (resultSet.next()) {
                emails.add(new EmailBody(resultSet.getInt("id"), resultSet.getString("title"),
                        resultSet.getString("body"), resultSet.getString("sender"),
                        resultSet.getString("receiver"), resultSet.getString("date"),
                        resultSet.getInt("reply_for"), resultSet.getString("status")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("internalError");

        } finally {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return emails;
    }

    @Override
    public String deleteEmail(int id) {
        initializeDatabase();

        try {
            connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
            statement = connection.createStatement();

            connection.setAutoCommit(false);
            int r = statement.executeUpdate("update email \n"
                    + "set status = 'delete' \n"
                    + "where id = " + id);

            connection.commit();
            connection.setAutoCommit(true);
            return ("inbox");

        } catch (SQLException e) {
            e.printStackTrace();
            return ("internalError");
        } finally {
            try {
//                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ArrayList<EmailBody> getTrashEmails(String email) {
        initializeDatabase();
        ArrayList<EmailBody> emails = new ArrayList<>();

        try {
            connection = DriverManager.getConnection(DATABASE_URL, db_id, db_psw);
            statement = connection.createStatement();

            resultSet = statement.executeQuery("select * from email \n"
                    + "where receiver = '" + email + "'\n"
                    + "and status = 'delete'\n"
                    + "order by id desc ");

            while (resultSet.next()) {
                emails.add(new EmailBody(resultSet.getInt("id"), resultSet.getString("title"),
                        resultSet.getString("body"), resultSet.getString("sender"),
                        resultSet.getString("receiver"), resultSet.getString("date"),
                        resultSet.getInt("reply_for"), resultSet.getString("status")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("internalError");

        } finally {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return emails;
    }
}
