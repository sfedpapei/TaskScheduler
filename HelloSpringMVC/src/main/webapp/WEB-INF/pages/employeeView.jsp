<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<title>Spring MVC Form Handling</title>
</head>
<body>

	<h2>Submitted Employee Information</h2>
	<table>
		<tr>
			<td>Name :</td>
			<td>${employee.name}</td>
		</tr>
		
		<tr>
			<td>Surname :</td>
			<td>${employee.surname}</td>
		</tr>
		
		 <tr>
                <td>Profession:</td>
                <td>${employee.profession}</td>
            </tr>
		
	</table>
</body>
</html>
