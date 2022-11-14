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
        <script src="assets/js/user.js"></script>
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    </head>
    <body>
        <%List<User> users = (List) request.getAttribute("users");%>
        <div class="container myContainer">
                <div>
                    <form action="users.jsp" method="GET">
                        <div class="form-group">
                            <label for="name">Name:</label>
                            <input class="form-control" type="text" id="name" name="name" value=""
                                   onkeyup="typedInput()">
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
                            <input type="hidden" name="id" value="<%=user.getId()%>">
                            <input type="hidden" name="action" value="delete">
                            <button class="btn btn-danger" type="submit" value="delete" data-toggle="modal"
                                    data-target="#exampleModal" onclick="setIdForDelete(<%=user.getId()%>)">
                                <i class="fa-solid fa-trash"></i>
                            </button>
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
        
        <!-- Modal -->
        <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Delete</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        This user will be deleted. Do you  want to proceed?
                    </div>
                    <div class="modal-footer">
                        <form action="userdetail" method="POST">
                            <input type="hidden" name="id" value="" id="id-delete">
                            <input type="hidden" name="action" value="delete">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            <input type="submit" class="btn btn-danger" value="Delete">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>