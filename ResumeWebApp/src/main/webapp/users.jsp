<%@ page import="dao.inter.UserDaoInter" %>
<%@ page import="dao.impl.UserDaoImpl" %>
<%@ page import="entity.User" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Resume Web App</title>
        <link rel="stylesheet" href="assets/css/users.css">
        <script src="https://kit.fontawesome.com/2522a98f9c.js" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    </head>
    <body>
        <%List<User> users = (List) request.getAttribute("users");%>
        <div class="container myContainer">
                <div>
                    <form action="users.jsp" method="GET">
                        <div class="form-group">
                            <label for="name">Name:</label>
                            <input class="form-control" type="text" id="name" name="name" value="">
                        </div>

                        <div class="form-group">
                            <label for="surname">Surname:</label>
                            <input class="form-control" type="text" id="surname" name="surname" value="">
                        </div>

                        <input class="btn btn-primary" type="submit" name="search" value="Search">
                    </form>
                </div>
            <div>
                <table class="table">
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Surname</th>
                        <th>Nationality</th>
                        <th></th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <%for(User user : users) {%>
                    <tr>
                        <td><%=user.getName()%></td>
                        <td><%=user.getSurname()%></td>
                        <td><%=user.getNationality() == null ? "N/A" : user.getNationality()%></td>
                        <td style="width: 5px">
                            <form action="userdetail" method="POST">
                                <input type="hidden" name="id" value="<%=user.getId()%>">
                                <input type="hidden" name="action" value="delete">
                                <button class="btn btn-danger" type="submit" value="delete">
                                    <i class="fa-solid fa-trash"></i>
                                </button>
                            </form>
                        </td>
                        <td style="width: 5px">
                            <form action="userdetail" method="GET">
                                <input type="hidden" name="id" value="<%=user.getId()%>">
                                <input type="hidden" name="action" value="update">
                                <button class="btn btn-secondary" type="submit" value="update">
                                    <i class="fas fa-pen-square"></i>
                                </button>
                            </form>
                        </td>
                    </tr>
                    <%}%>
                    </tbody>
                </table>
            </div>
        </div>

    </body>
</html>