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
			<a id="anchorHome" class="navbar-brand" href="#">Ourlinguo)))</a>
		</div>
	</div>
</nav>
<div class="container">
		<div class="row">
			<div class="col-sm-1"></div>
			<div class="col-sm-10">
			    <div id="divNotaSelez" class="alert alert-info fade in">
			        <span class="glyphicon glyphicon-exclamation-sign"></span>&nbsp;&nbsp;You can select the answers pressing the numbers (1,2,3,4) on your keyboard, "Enter" to confirm and "ESC" to exit;)
			    </div>
			</div>
			<div class="col-sm-1"></div>
		</div>

		<div class="row">
			<div class="col-sm-3"></div>
			<div class="col-sm-6">
			    <div id="divRisultato" style="display: none;" class="alert alert-success fade in">
			        <p id="pRisultato"></p>
			    </div>
			</div>
			<div class="col-sm-3"><p id="pViewResult" style="display: none;">Look at the result <a href="<c:url value='/viewResult' />">here</a></p></div>
		</div>

		<div class="jumbotron">

	        <div class="row">
	
				<c:if test="${richiediNumeroParole=='Y'}">
	
	            <div class="col-sm-2"></div>
	            <div class="col-sm-8">
					<div class="form-inline">
						<div class="form-group">
							<label class="control-label" for="inputLarge">How many questions?</label>
			                <input id="txtNumeroQuestions" type="text" size="3" maxlength="3" class="form-control">						
							<button id="btnConfirmNumberQuestion" class="btn btn-success">Get started</button>
							<br>
							<div class="checkbox">
			                    <label><input type="checkbox" id="chkAllQuestion" value="Y">All questions</label>
			                </div>
							<br>
							<br>
							<br>
						    <div id="divObblAnswerNumeroQuestions" style="display: none;" class="alert alert-danger fade in">
						        <span class="glyphicon glyphicon-warning-sign"></span>&nbsp;&nbsp;Please chose the numbero of questions or select all!))
						    </div>
						</div>
					</div>
				</div>
	            <div class="col-sm-2"></div>
	
				</c:if>            
	
				<c:if test="${richiediNumeroParole!='Y'}">
		            <div class="col-sm-4"></div>
		            <div class="col-sm-4">
	            	  <p>Question: <b><span id="questionNumber"></span></b> of <span id="questionTotal"></span></p>
					</div>
		            <div class="col-sm-4"></div>
				</c:if>            
	
			</div>
		</div>

		<c:if test="${richiediNumeroParole!='Y'}">

        <div class="row">
            <div class="col-sm-6">
                <div class="demo-content">
                    <center>What does that mean?)</center>
                    <br>
					<center><h3><span id="spanQuestion"></span></h3></center>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="demo-content">
                	<div id="divAnswers"></div>
                </div>
            </div>
        </div>
		<div class="row">
			<div class="col-sm-4">
			</div>
			<div class="col-sm-4">
				<button id="btnConfirm" class="btn btn-success btn-lg">Confirm</button>
				<button id="btnNext" class="btn btn-primary btn-lg" style="display: none;">Next</button>
				<button id="btnFinish" class="btn btn-success btn-lg" style="display: none;">Finish</button>
				<button id="btnRestart" class="btn btn-warning btn-lg" style="display: none;">Restart</button>
				<button id="btnNewOne" class="btn btn-info btn-lg" style="display: none;">New one</button>
			</div>
			<div class="col-sm-4">
				<h3><span id="spanIcoAnser" class="glyphicon"></span>&nbsp;<span id="spanAnswer" class="label"></span></h3>
			</div>
		</div>
        <div class="row">
            <div class="col-sm-12">
			    <div id="divObblAnswer" style="display: none;" class="alert alert-danger fade in">
			        <span class="glyphicon glyphicon-warning-sign"></span>&nbsp;&nbsp;Please select an answer))
			    </div>
            </div>
        </div>
		</c:if>            
        
	</div>
	<div class="row">
		<div class="col-sm-11"></div>
		<div class="col-sm-1">
			<button id="btnExit" class="btn btn-danger btn-lg">Exit</button>
		</div>
	</div>
</div>

</body>

<script>
var numeroDomanda = 0;
var domandeTotali = ${numeroDomande};
$(document).ready(function() {
	
	<c:if test="${richiediNumeroParole=='Y'}">
		$("#txtNumeroQuestions").focus();
	</c:if>            

	
    $(document).keypress(function(e) {
    	if(e.which == 13) {
        	// simula click sui pulsanti "confirm" o "next" o "finish" (a seconda della situazione)
 			if ($("#btnConfirm").is(":visible")){
 	        	$("#btnConfirm").click();
 			} else if ($("#btnNext").is(":visible")){
 	        	$("#btnNext").click();
 			} else if ($("#btnFinish").is(":visible")){
 	        	$("#btnFinish").click();
 			}
    	} else if(e.keyCode == 27) {
	        $("#btnExit").click();
    	} else if(e.which == 49 || e.which == 50 || e.which == 51 || e.which == 52) {
        	selezionaRisposta(e.which);
        }
    });
	
	$("#questionNumber").html(numeroDomanda);
	$("#questionTotal").html(domandeTotali);
	callNextQuestion();
});

function selezionaRisposta(asciiNumero){
	var idRadio = 0;
	if (asciiNumero==49){
		idRadio = 1;
	} else if (asciiNumero==50){
		idRadio = 2;
	} else if (asciiNumero==51){
		idRadio = 3;
	} else if (asciiNumero==52){
		idRadio = 4;
	}
	$("#rdbAnswer"+idRadio).prop("checked", true);
}


var domandeFinite = false;
function callNextQuestion(){
    $.ajax({
        url: "/nextQuestion"
    }).then(function(data) {
        domanda = data.question;
    	$("#questionNumber").html(++numeroDomanda);
    	$("#questionTotal").html(domandeTotali);

    	$('#spanQuestion').html(domanda);
    	$('#divAnswersInner').remove();
        for (i = 0; i < data.answers.length; i++) {
        	$('#divAnswers').append('<div id="divAnswersInner"></div>');
        	
        	var radioBtn = $('<div class="radio"><label><input type="radio" value="'+data.answers[i]+'" id="rdbAnswer'+(i+1)+'" name="rdbAnswer"><b>&nbsp;'+(i+1)+')&nbsp;'+data.answers[i]+'</b></label></div>');
    	  	radioBtn.appendTo('#divAnswersInner');
    	}
        if (!data.presenteAltraDomanda){
        	domandeFinite = true;
        }
    });
}

var domanda = "";
var risposteGiuste = 0;
var risposteTotali = 0;
$("#btnConfirm").click(function() {
	  var risposta = $('input[name=rdbAnswer]:checked').val();
	  if (risposta!=null && risposta!=""){
	    $.ajax({
	        url: "/verifyAnswer?question="+domanda+"&answer="+risposta
	    }).then(function(data) {
	        risposteTotali++;
	        
        	$('#spanIcoAnser').removeClass("glyphicon-ok-circle colorGreen");
        	$('#spanIcoAnser').removeClass("glyphicon-remove-circle colorRed");
	        
        	$('#spanAnswer').removeClass("label-success");
        	$('#spanAnswer').removeClass("label-danger");
	        if (data=="OK"){
	        	$('#spanIcoAnser').addClass("glyphicon-ok-circle colorGreen");
	        	$('#spanAnswer').addClass("label-success");
	            $('#spanAnswer').html("CORRECT ANSWER");
	            risposteGiuste++;
	        } else {
	        	$('#spanIcoAnser').addClass("glyphicon-remove-circle colorRed");
	        	$('#spanAnswer').addClass("label-danger");
	        	$('#spanAnswer').html("WRONG ANSWER");
	        }
	        $("#btnConfirm").hide();
	        $("#divObblAnswer").hide();
	        if (domandeFinite){
		        $("#btnFinish").show();
	        } else {
		        $("#btnNext").show();
	        }
	    });
	  } else {
		$("#divObblAnswer").show();
	  }
});

$("#btnNext").click(function() {
      $("#btnConfirm").show();
      $("#btnNext").hide();
      $('#spanAnswer').html("");
  	  $('#spanIcoAnser').removeClass("glyphicon-ok-circle colorGreen");
  	  $('#spanIcoAnser').removeClass("glyphicon-remove-circle colorRed");
      callNextQuestion();
});

$("#btnFinish").click(function() {
	
	var percentualeCorrette = (risposteGiuste*100)/risposteTotali;
	var complimenti = "";
	if (percentualeCorrette>0){
		if (percentualeCorrette==100.0){
			complimenti = "Great";
		} if (percentualeCorrette>=80.0){
			complimenti = "Compliments";
		} if (percentualeCorrette>=60.0){
			complimenti = "Not that bad";
		} else {
			complimenti = "Not that good";
		}
	} else {
		complimenti = "Bad";
	}

	$("#divNotaSelez").hide();
	$("#pRisultato").html("<span class=\"glyphicon glyphicon-ok\"></span>&nbsp;&nbsp;<strong>Finished!</strong>&nbsp;" + complimenti + ", you answered " + risposteGiuste + " questions out of " + risposteTotali);
	$("#divRisultato").show();
	$("#pViewResult").show();
	
	$("#btnFinish").hide();
    $("#btnRestart").show();
    $("#btnNewOne").show();
    $('#spanAnswer').html("");
 	$('#spanIcoAnser').removeClass("glyphicon-ok-circle colorGreen");
 	$('#spanIcoAnser').removeClass("glyphicon-remove-circle colorRed");
});

$("#btnRestart").click(function() {
    $("#btnRestart").hide();
    $("#btnNewOne").hide();
    location.href = "/restart";
});

$("#btnExit,#anchorHome").click(function() {
	if (confirm("Do you really want to finish?")){
		goHomePage();
	}
});

// $("#anchorHome").click(function(e)
// 		{e.preventDefault(); /*your_code_here;*/ return false; }

function goHomePage(){
	console.log("goHomePage");
    $("#btnRestart").hide();
    $("#btnNewOne").hide();
    location.href = "/";
}

$("#btnNewOne").click(function() {
	goHomePage();
});

$("input[name='rdbAnswer']").change(function() {
	$("#divObblAnswer").hide();
});

$("#btnConfirmNumberQuestion").click(function() {
	var flgAllQuestion = $('#chkAllQuestion').is(":checked");
	if (flgAllQuestion || ($("#txtNumeroQuestions").val()!="" && $("#txtNumeroQuestions").val()!="0")){
		var callDa = "${callDa}";
		var numQuestion = $("#txtNumeroQuestions").val();
		if (flgAllQuestion){
			numQuestion = 999;	// in case checkbox "all questions" selected
		}
	    location.href = "/startOriginal?callDa="+callDa+"&numeroParole="+numQuestion;
	} else {
		$("#divObblAnswerNumeroQuestions").show();
	}

});

</script>    

</html>