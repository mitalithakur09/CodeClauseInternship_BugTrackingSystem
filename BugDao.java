import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BugDao {

    //db connection
    private static final String URL = "jdbc:mysql://localhost/bugtracking";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

   //Sql
    private static final String SELECT_ALL_BUGS = "SELECT * FROM bugs";
    private static final String INSERT_BUG = "INSERT INTO bugs (title, description, status) VALUES (?, ?, ?)";
    private static final String UPDATE_BUG = "UPDATE bugs SET title=?, description=?, status=? WHERE id=?";
    private static final String DELETE_BUG = "DELETE FROM bugs WHERE id=?";
    private static final String SELECT_BUG_BY_ID = "SELECT * FROM bugs WHERE id=?";

    public List<Bug> getAllBugs() {
        List<Bug> bugs = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_BUGS)) {

            while (resultSet.next()) {
                Bug bug = extractBugFromResultSet(resultSet);
                bugs.add(bug);
            }
        } catch (SQLException e) {
            e.printStackTrace(); //exceptions handling
        }
        return bugs;
    }

    public void addBug(Bug bug) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BUG)) {

            preparedStatement.setString(1, bug.getTitle());
            preparedStatement.setString(2, bug.getDescription());
            preparedStatement.setString(3, bug.getStatus());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); //exceptions handling
        }
    }

    public void updateBug(Bug bug) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BUG)) {

            preparedStatement.setString(1, bug.getTitle());
            preparedStatement.setString(2, bug.getDescription());
            preparedStatement.setString(3, bug.getStatus());
            preparedStatement.setInt(4, bug.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); //exceptions handling
        }
    }

    public void deleteBug(int bugId) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BUG)) {

            preparedStatement.setInt(1, bugId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); //exceptions handling
        }
    }

    public Bug getBugById(int bugId) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BUG_BY_ID)) {

            preparedStatement.setInt(1, bugId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return extractBugFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); //exceptions handling
        }
        return null;
    }

    private Bug extractBugFromResultSet(ResultSet resultSet) throws SQLException {
        Bug bug = new Bug();
        bug.setId(resultSet.getInt("id"));
        bug.setTitle(resultSet.getString("title"));
        bug.setDescription(resultSet.getString("description"));
        bug.setStatus(resultSet.getString("status"));
        return bug;
    }
}


