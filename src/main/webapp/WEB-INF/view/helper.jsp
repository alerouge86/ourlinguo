<%@ taglib
    prefix="c"
    uri="http://java.sun.com/jsp/jstl/core" 
%>
<!DOCTYPE html>
<html lang="en">
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<head>
<%@page pageEncoding="UTF-8" %>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Ourlinguo - Learning italian/russian))</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="https:rawgithub.com/indrimuska/jquery-editable-select/master/dist/jquery-editable-select.min.css">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<style type="text/css">
body {
	padding-top: 70px;
}

.title-content {
	padding: 5px;
	font-size: 25px;
	min-height: 100px;
	background: #dbdfe5;
	margin-bottom: 10px;
}

.demo-content {
	padding: 15px;
	font-size: 18px;
	min-height: 180px;
	background: #dbdfe5;
	margin-bottom: 10px;
}

span {
	font-size: 30px;
}

.colorRed{
	color: #d9534f;
}

.colorGreen{
	color: #5cb85c;
}

#divNotaSelez{
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
			<a id="anchorHome" class="navbar-brand" href="http://localhost:8080">Ourlinguo)))</a>
		</div>
	</div>
</nav>
<div class="container">

		<div class="row">
			<div class="col-sm-1"></div>
			<div class="col-sm-10">
			    <h1>Helper Words</h1>
			</div>
			<div class="col-sm-1"></div>
		</div>

		<c:if test="${flgShowHelper=='Y' }">
			<div class="row">
				<div class="col-sm-1"></div>
				<div class="col-sm-10">
				    <div class="alert alert-info fade in">
				        <span class="glyphicon glyphicon-exclamation-sign"></span>&nbsp;&nbsp;Choose the main language you want to use.
				    </div>
				</div>
				<div class="col-sm-1"></div>
			</div>
	        <div class="row">
	            <div class="col-sm-6">
	                <div class="demo-content">
						<div class="col-sm-12">
							<h2>Russian</h2>
							<p><a href="javascript:startHelper('Russo');" class="btn btn-success btn-lg">Get started</a></p>
						</div>
	                </div>
	            </div>
	            <div class="col-sm-6">
	                <div class="demo-content">
						<div class="col-sm-12">
							<h2>Итальянский</h2>
							<p><a href="javascript:startHelper('Italiano');" class="btn btn-success btn-lg">Get started</a></p>
						</div>
	                </div>
	            </div>
	        </div>
	        
		    <form>
		        <div class="row">
		          <div class="col-xs-4">
		          </div>
		          <div class="col-xs-4">
		            <div class="input-group">
		              <h4 class="form-control">
		              	<input type="checkbox" value="Y" name="chkTranslation" id="chkTranslation">&nbsp;Show also translation</h4>
		            </div>
		          </div>
		          <div class="col-xs-4">
		          </div>
		        </div>
		    </form>
	        
		</c:if>        


		<c:if test="${flgStartHelper=='Y' }">
			<div class="row">
				<div class="col-sm-1"></div>
				<div class="col-sm-10">
				    <div class="alert alert-info fade in">
				        <span class="glyphicon glyphicon-exclamation-sign"></span>&nbsp;&nbsp;Type some digit and the program will show you the list of
				        words which contain them.
				    </div>
				</div>
				<div class="col-sm-1"></div>
			</div>

	        <div class="row">
				<div class="col-sm-1"></div>
				<div class="col-sm-10">
					<p><a href="/showHelper" class="btn btn-success btn-lg">Change language</a></p>
				</div>
				<div class="col-sm-1"></div>
	        </div>

	        <div class="row">
	            <div class="col-sm-1">
				</div>
	            <div class="col-sm-10">
	                <div class="demo-content">
				        <div class="row" style="height: 300px">
				            <div class="col-sm-12">
								<select class="form-control" id="editable" name="editable">
					                <c:forEach items="${elencoWords}" var="word">
					                    <option>${word}</option>
					                </c:forEach>
					            </select>             
			                </div>
		                </div>
	                </div>
	            </div>
	            <div class="col-sm-1">
				</div>
	        </div>
		</c:if>        
        
	</div>

</body>

<script src="https://rawgithub.com/indrimuska/jquery-editable-select/master/dist/jquery-editable-select.min.js"></script>
<script>

// 	$("#editable").editableSelect();
	$("#editable").editableSelect({
	  warpClass: 'ui-select-wrap',
	  editable: true
	});
	
	function startHelper(lingua){
		var showTranslation = "N";
		if ($('#chkTranslation').is(":checked")){
			showTranslation = "Y";
		}
		location.href = "/startHelper?callDa="+lingua+"&showTranslation="+showTranslation;
	}
	
</script>    

</html>