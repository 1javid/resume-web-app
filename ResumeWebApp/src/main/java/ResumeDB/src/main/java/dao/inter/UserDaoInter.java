package dao.inter;

import entity.User;

import java.util.List;

public interface UserDaoInter {

    List<User> getAllUsers(String name, String surname, Integer nationalityId);

    User getById(int userId);

    boolean addUser(User u);

    boolean updateUser(User u);

    boolean deleteUser(int userId);
}
