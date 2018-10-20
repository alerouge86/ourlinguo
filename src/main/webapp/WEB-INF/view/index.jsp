<!DOCTYPE html>
<html lang="en">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<head>
<%@page pageEncoding="UTF-8" %>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Ourlinguo - Learning italian/russian))</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<style type="text/css">
    body{
    	padding-top: 70px;
    }
</style>
</head>
<body>
<nav id="myNavbar" class="navbar navbar-default navbar-inverse navbar-fixed-top" role="navigation">
	<!-- Brand and toggle get grouped for better mobile display -->
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbarCollapse">
				<span class="sr-only">Toggle navigation</span>
			</button>
			<a class="navbar-brand" href="http://localhost:8080">Ourlinguo)))</a>
			<a class="navbar-brand" href="/showHelper">Helper</a>
		</div>
	</div>
</nav>
<div class="container">
	<div class="jumbotron">
		<div class="row">
			<div class="col-sm-6">
				<h2>Impara il Russo</h2>
				<p><a href="/startOriginal?callDa=Russo&numeroParole=0" class="btn btn-success btn-lg">Get started</a></p>
			</div>
			<div class="col-sm-6">
				<h4>Carica il file&nbsp;<span class="label label-success">excel</span></h4>
					<c:url value="/uploadExcelFileTemp?callDa=Russo" var="uploadFileUrl" />
					<form method="post" enctype="multipart/form-data"
					  action="${uploadFileUrl}">
					    <input type="file" name="file" accept=".xls,.xlsx" class="btn btn-lg" /><input type="submit" value="Upload file" class="btn btn-primary btn-lg" />
					</form>
			</div>
		</div>
	</div>
	<div class="jumbotron">
		<div class="row">
			<div class="col-sm-6">
				<h2>Изучать Итальянский</h2>
				<p><a href="/startOriginal?callDa=Italiano&numeroParole=0" class="btn btn-success btn-lg">Начать</a></p>
			</div>
			<div class="col-sm-6">
				<h4>загрузить файл&nbsp;<span class="label label-success">excel</span></h4>
					<c:url value="/uploadExcelFileTemp?callDa=Italiano" var="uploadFileUrl" />
					<form method="post" enctype="multipart/form-data"
					  action="${uploadFileUrl}">
					    <input type="file" name="file" accept=".xls,.xlsx" class="btn btn-lg" /><input type="submit" value="загрузить файл" class="btn btn-primary btn-lg" />
					</form>
			</div>
		</div>
	</div>
	<div class="panel panel-default">
	    <div class="panel-heading">
			<div class="row">
				<div class="col-xs-8">
					<footer>
						Upload the original file&nbsp;<span class="label label-success">excel</span>
							<c:url value="/uploadExcelFileOriginal" var="uploadFileUrl" />
							<form method="post" enctype="multipart/form-data"
							  action="${uploadFileUrl}">
							    <input type="file" name="file" accept=".xls,.xlsx" class="btn" /><input type="submit" value="Upload file" class="btn btn-primary btn-lg" />
							</form>
							<c:if test = "${fileOriginalCaricato!=null}">
								<c:choose>
								  <c:when test="${fileOriginalCaricato=='Y'}">
								    <div class="alert alert-success fade in">
								        <span class="glyphicon glyphicon-ok-circle"></span>&nbsp;&nbsp;File uploaded correctly!
								    </div>
								  </c:when>
								  <c:otherwise>
								    <div class="alert alert-danger fade in">
								        <span class="glyphicon glyphicon-warning-sign"></span>&nbsp;&nbsp;An error occurred in the uploading file!
								    </div>
								  </c:otherwise>
								</c:choose>		
						    </c:if>
					</footer>
				</div>
				<div class="col-xs-4">
					<footer>
						<p>Download the original excel file <a href="<c:url value='/download' />">here</a>
						<br>
						<br>
						Restore the initial file&nbsp;<span class="label label-success">excel</span>&nbsp;<button id="btnRestoreFileExcel" class="btn btn-primary">Restore</button>
						</p>
							<c:if test = "${fileCampioneCaricato!=null}">
								<c:choose>
								  <c:when test="${fileCampioneCaricato=='Y'}">
								    <div class="alert alert-success fade in">
								        <span class="glyphicon glyphicon-ok-circle"></span>&nbsp;&nbsp;File restored correctly!
								    </div>
								  </c:when>
								  <c:otherwise>
								    <div class="alert alert-danger fade in">
								        <span class="glyphicon glyphicon-warning-sign"></span>&nbsp;&nbsp;An error occurred in the restoring file!
								    </div>
								  </c:otherwise>
								</c:choose>		
						    </c:if>
					</footer>
				</div>
			</div>
		</div>
	</div>
	
</div>
</body>

<script>

$("#btnRestoreFileExcel").click(function() {
    location.href = "/restoreFileExcel";
});

</script>

</html>                            
