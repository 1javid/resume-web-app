package dao.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import dao.inter.ConnectionAbstract;
import dao.inter.UserDaoInter;
import entity.Country;
import entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class UserDaoImpl extends ConnectionAbstract implements UserDaoInter {

    private User getUser(ResultSet resultSet) throws Exception {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String surname = resultSet.getString("surname");
            String email = resultSet.getString("email");
            String phone = resultSet.getString("phone");
            String profileDesc = resultSet.getString("profile_description");
            String address = resultSet.getString("address");
            Date birthdate = resultSet.getDate("birthdate");
            int birthplaceId = resultSet.getInt("birthplace_id");
            int nationalityId = resultSet.getInt("nationality_id");
            String birthplaceStr = resultSet.getString("birthplace");
            String nationalityStr = resultSet.getString("nationality");

            Country nationality = new Country(nationalityId, null, nationalityStr);
            Country birthplace = new Country(birthplaceId, birthplaceStr, null);

            return new User(id, name, surname, email, phone, profileDesc, address, birthdate, birthplace, nationality);
    }


    private User getUserBrief(ResultSet resultSet) throws Exception {

        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String surname = resultSet.getString("surname");
        String email = resultSet.getString("email");
        String phone = resultSet.getString("phone");
        String profileDesc = resultSet.getString("profile_description");
        String address = resultSet.getString("address");
        Date birthdate = resultSet.getDate("birthdate");

        User user = new User(id, name, surname, email, phone, profileDesc, address, birthdate, null, null);
        user.setPassword(resultSet.getString("password"));

        return user;
    }

    private BCrypt.Hasher crypt = BCrypt.withDefaults();
    @Override
    public boolean addUser(User u) {
        try (Connection c = connect()){
            PreparedStatement statement = c.prepareStatement("insert into user(name, surname, email, password, phone, profile_description) values(?,?,?,?,?,?)");
            statement.setString(1, u.getName());
            statement.setString(2, u.getSurname());
            statement.setString(3, u.getEmail());
            statement.setString(4, crypt.hashToString(4, u.getPassword().toCharArray()));
            statement.setString(5, u.getPhone());
            statement.setString(6, u.getProfileDesc());

            return statement.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public List<User> getAllUsers(String name, String surname, Integer nationalityId) {

        List<User> userList = new ArrayList<>();
        try(Connection c = connect()){
            String sql = "select "
                    + "u.*, "
                    + "n.nationality, "
                    + "c.name as birthplace "
                    + "from user u "
                    + "left join country n on u.nationality_id = n.id "
                    + "left join country c on u.birthplace_id = c.id where 1=1 ";

            if(name != null && !name.trim().isEmpty()) sql += "and u.name=?";
            if(surname != null && !surname.trim().isEmpty()) sql += "and u.surname=?";
            if(nationalityId != null) sql += "and u.nationality_id=?";

            PreparedStatement statement = c.prepareStatement(sql);

            int i = 1;
            if(name != null && !name.trim().isEmpty()) {
                statement.setString(i, name);
                i++;
            }
            if(surname != null && !surname.trim().isEmpty()) {
                statement.setString(i, surname);
                i++;
            }
            if(nationalityId != null) {
                statement.setInt(i, nationalityId);
            }

            statement.execute();
            ResultSet resultSet = statement.getResultSet();

            while (resultSet.next()) {
                User user = getUser(resultSet);
                userList.add(user);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return userList;
    }

    @Override
    public User findByEmail(String email) {
        User result = null;
        try(Connection c = connect()) {
            PreparedStatement statement = c.prepareStatement("select * from user where email=?");
            statement.setString(1, email);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                result = getUserBrief(rs);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public User getById(int userId) {
        User user = null;
        try(Connection c = connect()){
            Statement statement = c.createStatement();
            statement.execute("select "
                    + "u.*, "
                    + "n.nationality, "
                    + "c.name as birthplace "
                    + "from user u "
                    + "left join country n on u.nationality_id = n.id "
                    + "left join country c on u.birthplace_id = c.id where u.id = " + userId);
            ResultSet resultSet = statement.getResultSet();

            while (resultSet.next()) {
                user = getUser(resultSet);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return user;
    }

    @Override
    public boolean updateUser(User u){
        try (Connection c = connect()){
            PreparedStatement statement = c.prepareStatement("update user set name=?, surname=?, email=?, phone=?, profile_description=?, address=?, birthdate=?, birthplace_id=?, nationality_id=? where id=?");
            statement.setString(1, u.getName());
            statement.setString(2, u.getSurname());
            statement.setString(3, u.getEmail());
            statement.setString(4, u.getPhone());
            statement.setString(5, u.getProfileDesc());
            statement.setString(6, u.getAddress());
            statement.setString(7, u.getBirthdate().toString());
            statement.setInt(8, u.getBirthplace().getId());
            statement.setInt(9, u.getNationality().getId());
            statement.setInt(10, u.getId());

            return statement.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteUser(int userId) {
        try (Connection c = connect()){
            Statement statement = c.createStatement();
            return statement.execute("delete from user where id = " + userId);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
