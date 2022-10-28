<%@ page import="entity.User" %>
<%@ page import="entity.UserSkills" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Resume Web App</title>
</head>
<body>
<%User u = (User) request.getAttribute("user");%>
    <div>
        <form action="userdetail" method="POST">
            <input type="hidden" name="id" value="<%=u.getId()%>">
            
            <label for="name">Name:</label>
            <input type="text" id="name" name="name" value="<%=u.getName()%>">

            <label for="surname">Surname:</label>
            <input type="text" id="surname" name="surname" value="<%=u.getSurname()%>">
    
            <label for="birthdate">Birthdate:</label>
            <input type="text" id="birthdate" name="birthdate" value="<%=u.getBirthdate()%>">
    
            <label for="country">Country:</label>
            <input type="text" id="country" name="country" value="<%=String.valueOf(u.getBirthplace())%>">
    
            <label for="nationality">Nationality:</label>
            <input type="text" id="nationality" name="nationality" value="<%=u.getNationality()%>">
    
            <label for="address">Address:</label>
            <input type="text" id="address" name="address" value="<%=u.getAddress()%>">
    
            <label for="email">Email:</label>
            <input type="text" id="email" name="email" value="<%=u.getEmail()%>">
    
            <label for="phone">Phone number:</label>
            <input type="text" id="phone" name="phone" value="<%=u.getPhone()%>">
    
            <label for="profile_desc">Profile Description:</label>
            <textarea id="profile_desc" name="profile_desc"><%=u.getProfileDesc()%></textarea>
            
            <input type="submit" name="save" value="Save">
        </form>
    </div>
</body>
</html>