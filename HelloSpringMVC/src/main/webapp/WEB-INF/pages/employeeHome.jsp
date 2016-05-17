<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
    <head>
    <style>
.error {
color: #ff0000;
font-style: italic;
}
</style>
    </head>
    <body>
        <h3>Welcome, Enter The Employee Details</h3>
        <form:form method="POST" action="/HelloSpringMVC/addEmployee" modelAttribute="employee">
             <table>
             
                <tr>
                    <td><form:label path="name">Name</form:label></td>
                    <td><form:input path="name"/></td>
                    <td><form:errors path="name" cssClass="error" /></td>
                    
                </tr>
                
                <tr>
                    <td><form:label path="surname">Surname</form:label></td>
                    <td><form:input path="surname"/></td>
                </tr>
                
                <tr>
                    <td>Profession:</td>
                    <td><form:select path="profession" items="${professionList}" /></td>
                </tr>
                
                <tr>
                	<td>Hobby:</td>
                	<td><form:checkbox path="hobby"/></td>
                </tr>
                
                <tr>
                
                <tr>
                    <td><input type="submit" value="Submit"/></td>
                </tr>
            </table>
        </form:form>
    </body>
</html>