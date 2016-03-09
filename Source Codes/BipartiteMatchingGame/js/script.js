var xmlHttp = createXmlHttpRequestObject();
var edgeList = [];
var toWin = 0;
var matchedEdges =[];

function createXmlHttpRequestObject() {
	var xmlHttp;

	//Only true if running IE
	if(window.ActiveXObject) {
		try{
			xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
		}catch(e) {
			xmlHttp = false;
		}
	} else {
		try{
			xmlHttp = new XMLHttpRequest();
		}catch(e) {
			xmlHttp = false;
		}
	}

	if(!xmlHttp)
		alert("Error creating xmlHttp object");
	else
		return xmlHttp;
}

function process() {
	//Check if object is rdy to communicate
	if(xmlHttp.readyState==0 || xmlHttp.readyState==4) {
		x_value = encodeURIComponent(document.getElementById("x").value);

		//Prepare request
		xmlHttp.open("GET", "matching.php?cmd=generate&graph_id=" + x_value, true);
		xmlHttp.onreadystatechange = handleServerResponse;
		
		//Send Request
		xmlHttp.send(null);
	}
	/*else {
		setTimeout('process()',1000);
	}*/		
}

function handleServerResponse() {
	//server is going to send an xml file in between <response> tag
	var graph;
	//if server is done communicating
	if(xmlHttp.readyState==4) {
		//200 on object means communication is ok (corrupt data, srv down will not output 200)
		if(xmlHttp.status==200) {
			
			message =this.responseText;
			graph = JSON.parse(message);

			var num = (graph.E).length;
			edgeList = [];
			for(i=0; i<num; i++) {
				//if (Math.abs(graph.E[count][0] - graph.E[count][1]) <= 1) {
				//	drawCurvyLine(graph.E[count][0], graph.E[count][1], graph.E[count][2]);
				//} else { 
					drawStraightLine(graph.E[i][0], graph.E[i][1], graph.E[i][2]);
				//}
				var edge = [];
				edge[0] = graph.E[i][0];
				edge[1] = graph.E[i][1];
				edge[2] = graph.E[i][2];
				edgeList[i] = edge;
			}

			countCatsNeededToWin();
			//debugger;
			//document.getElementById("status").innerHTML='<span>' + message + '</span>';
			//setTimeout('process()', 1000);

		} else {
			document.getElementById("status").innerHTML='<span>error</span>';
			//setTimeout('process()', 1000);
		}

	}
	//console.log(obj);
}

//from script.js

$(window).load( function() {
	$('#myModal').modal('show');
});

$(document).ready( function() {
	console.log("Document Loaded");

	$('#btn').click( function() {
		$('#myModal').modal('hide');
		$('#tbl tr').remove();

		x_val = $('#x').val();
		leftRows = getRowValue(x_val,1);
		rightRows = getRowValue(x_val,2);
		height = Math.max(leftRows, rightRows);

		validateX(x_val);

		var randArray1 = randomNumbers(leftRows);
		var randArray2 = randomNumbers(rightRows);
		
		var table;
		var height_input = 100 * height;
		table += '<tr><td id="img_'+ randArray1[1] + '" class="left col-md-2 col-xs-2">' + '<img src="img/l' + randArray1[1] + '.png"></td><td id="col2" class="col-md-8 col-xs-8" rowspan="' + height + '"><svg id="svg_lines" height="' +height_input+ 'px" width="100%"></svg></td><td id="img_' + randArray2[1] + '" class="right col-md-2 col-xs-2">' + '<img src="img/r' + randArray2[1] + '.png"></td></tr>';
		
		for(i=2; i<=height; i++) {
			if(i<=leftRows) {
				leftTd = '<td id="img_'+ randArray1[i] + '" class="left col-md-2 col-xs-2">' + '<img src="img/l' + randArray1[i] + '.png"></td>';
			} else {
				leftTd = '<td></td>';
			}
			if(i<=rightRows) {
				rightTd = '<td id="img_' + randArray2[i] + '" class="right col-md-2 col-xs-2">' + '<img src="img/r' + randArray2[i] + '.png"></td>';
			} else {
				rightTd = '<td></td>';
			}
			table += '<tr>' + leftTd + rightTd + '</tr>';
		}
		$("#tbl").append(table);

		document.getElementById('msg').innerHTML='Make as many animals eat + get highest total score';
		matchImage();
	});

});

function getRowValue(x, column) {
	switch (x) {
    case "1":
        if(column==1) {
        	return 3;
        } else {
        	return 3;
        }
        break;
    case "2":
        if(column==1) {
        	return 2;
        } else {
        	return 3;
        }
        break;
    case "3":
        if(column==1) {
        	return 2;
        } else {
        	return 2;
        }
        break;
    case "4":
        if(column==1) {
        	return 5;
        } else {
        	return 3;
        }
        break;
    case "5":
        if(column==1) {
        	return 4;
        } else {
        	return 2;
        }
        break;
    case "6":
        if(column==1) {
        	return 3;
        } else {
        	return 4;
        }
        break;
    case "7":
        if(column==1) {
        	return 5;
        } else {
        	return 5;
        }        break;
    case "8":
        if(column==1) {
        	return 6;
        } else {
        	return 7;
        }
        break;
    case "9":
        if(column==1) {
        	return 8;
        } else {
        	return 8;
        }
        break;
	}
}

function countCatsNeededToWin() {
	var array = [];
	for (i=0; i<edgeList.length; i++) {
		var exist = $.inArray(edgeList[i][0], array);
		//console.log(exist);
		if (exist == -1) {
			toWin++;
			//console.log(edgeList[i][0]);
			array.push(edgeList[i][0]);
		}
	}
	//console.log("toWin:" + toWin);
}

function countMaxEdibleFood() {
	var array=[];
	var numEdibleFood=0;
	for (i=0; i<edgeList.length; i++) {
		var exist = $.inArray(edgeList[i][1], array);
		//console.log(exist);
		if (exist == -1) {
			numEdibleFood++;
			//console.log(edgeList[i][0]);
			array.push(edgeList[i][1]);
		}
	}
	return numEdibleFood;
}

function matchImage() {
	leftElementMoved = false;
	rightElementMoved = false;
	var leftImg, rightImg;
	count=0;
	score=0;
	catsThatAte = [];

	$('.left').click( function() {
		leftRowNum = parseInt($(this).parent().index() ) +1;
		//console.log("L"+leftRowNum);
		if($(this).hasClass('matched')) {
			document.getElementById('msg').innerHTML='This cat has eaten...';
		} else if(!leftElementMoved) {
			$(this).addClass('clickedLeft');
			leftElementMoved = true;
			leftSelected = this;
			//console.log(leftSelected);
			//leftImg = $(this).attr('id');
			if(leftElementMoved && rightElementMoved) {
				weight = edgeExist(leftRowNum, rightRowNum);
				if(weight != -1) {
					//console.log(weight);
					count++;
					score += weight;
					document.getElementById('msg').innerHTML=count+' cat(s) have eaten, current total score '+score;
					$(this).addClass('matched').removeClass('clickedLeft');
					$(rightSelected).addClass('matched').removeClass('clickedRight');
					leftElementMoved = false;
					rightElementMoved = false;
					//match[leftRowNum] = rightRowNum;
					drawBoldLine(leftRowNum, rightRowNum, "green");
					matchedEdges.push(new Array(leftRowNum, rightRowNum, weight));
					catsThatAte.push(leftRowNum);
					//checkGame(score);
				} else {
					document.getElementById('msg').innerHTML='Try again';
					$(this).removeClass('clickedLeft');
					$(rightSelected).removeClass('clickedRight');
					leftElementMoved = false;
					rightElementMoved = false;
					//checkGame(score);
				}
			} else {
				document.getElementById('msg').innerHTML='Now match this with...';
			}
		} else {
			if($(this).hasClass('clickedLeft')) {
				$(this).removeClass('clickedLeft');
				leftElementMoved = false;
				document.getElementById('msg').innerHTML=count+' cat(s) have eaten, current total score '+score;
			}

		}

	});

	$('.right').click( function() {
		rightRowNum = parseInt($(this).parent().index() ) +1;
		//console.log("R"+rightRowNum);
		if($(this).hasClass('matched')) {
			document.getElementById('msg').innerHTML='This food has been eaten...';
		} else if(!rightElementMoved) {
			$(this).addClass('clickedRight');
			rightElementMoved = true;
			rightSelected = this;
			//rightImg = $(this).attr('id');
			if(leftElementMoved && rightElementMoved) {
				weight = edgeExist(leftRowNum, rightRowNum);
				if(weight != -1) {
					//console.log(weight);
					count++;
					score += weight;
					document.getElementById('msg').innerHTML=count+' cat(s) have eaten, current total score '+score;
					$(this).addClass('matched').removeClass('clickedRight');
					$(leftSelected).addClass('matched').removeClass('clickedLeft');
					leftElementMoved = false;
					rightElementMoved = false;
					//match[leftRowNum] = rightRowNum;
					drawBoldLine(leftRowNum, rightRowNum, "green");
					matchedEdges.push(new Array(leftRowNum, rightRowNum, weight));
					//checkGame(score);
				} else {
					document.getElementById('msg').innerHTML='Try again';
					$(this).removeClass('clickedRight');
					$(leftSelected).removeClass('clickedLeft');
					leftElementMoved = false;
					rightElementMoved = false;
					//checkGame(score);
				}
			} else {
				document.getElementById('msg').innerHTML='Now match this with..';
			}
		} else {
			if($(this).hasClass('clickedRight')) {
				$(this).removeClass('clickedRight');
				rightElementMoved = false;
				document.getElementById('msg').innerHTML=count+' cat(s) have eaten, current total score '+score;
			}
		}

	});
}

function edgeExist(left, right) {
	var weight=-1;
	for(i in edgeList) {
		if(edgeList[i][0]== left && edgeList[i][1] == right) {
			weight = edgeList[i][2];
		}
	}
	return weight;
}

function validateX(x) {
	if(x<1 || x >9) {
		alert("Please enter a value X from 1 to 9");
		window.location.reload();
	}
}

function checkGame(score) {
	if(count==toWin) {
		document.getElementById('msg').innerHTML='All cats have eaten with current total score '+score;
		toWin=0;
		$('#myModal').modal('show');
	}
}

function reset() {
	toWin=0;
	matchedEdges=[];
	$('#myModal').modal('show');
}

function randomNumbers(n) {
	var numArray = [];
	for(i=1; i<=n; i++) {
		numArray[i] = i;
	}

	//console.log(numArray);
	var randArray = shuffle(numArray);

	return randArray;
}

function solve() {
	var solver = new XMLHttpRequest();
	var answer;

	if(solver.readyState==0 || solver.readyState==4) {
		//Prepare request
		solver.open("GET", "http://cs3226.comp.nus.edu.sg/matching.php?cmd=solve&graph=" + message, true);
		solver.onreadystatechange = function()
		{
			if (solver.readyState == XMLHttpRequest.DONE) {
				if (solver.status == 200) {
					message2 = solver.responseText;
					answer = JSON.parse(message2);

					var num = (answer.match).length;
					for(i=0; i<num; i++) {
						drawBoldLine(answer.match[i][0], answer.match[i][1], "blue");
					}
					document.getElementById("msg").innerHTML='More cats ('+answer.num_match+') eat food in the <span style="color: blue; font-weight: bold;">optimal answer(s)</span> :(, reset and try again.';
				} else {
					document.getElementById("status").innerHTML='<span>error</span>';
				}
			}
		}
		//Send Request
		solver.send(null);
	}
}

function submit() {
	sol_score = score;
	graph_id = x_val;
	var submit = new XMLHttpRequest();
	var isValid;
	//console.log("matching.php?cmd=submit&graph_id=" +graph_id+ "&solution="+JSON.stringify(matchedEdges)+"&score="+score);
	if(submit.readyState==0 || submit.readyState==4) {
		//Prepare request
		submit.open("GET", "matching.php?cmd=submit&graph_id=" +graph_id+ "&solution="+JSON.stringify(matchedEdges)+"&score="+score, true);
		submit.onreadystatechange = function()
		{
			if (submit.readyState == XMLHttpRequest.DONE) {
				if (submit.status == 200) {
					
					message3 = submit.responseText;
					msg = JSON.parse(message3);
					
					if(msg == "Invalid") {
						document.getElementById("msg").innerHTML='Please do not input invalid solution through php scripts directly';
					} else if (Boolean(msg.new_best)) {	
						document.getElementById("msg").innerHTML='Congrats, '+msg.num_match+ ' cats eat food with total score of ' +msg.match_score+ ' is already the optimal answer, reset and try another weighted bipartite graph';
					} else {
						document.getElementById("msg").innerHTML='Well, as recorded in the database, someone can make ' + msg.num_match + ' cats eat food with total score of ' +msg.match_score+', reset and try again';
					}
					
					/*
					if(submit.status == 200) {
						alert("Insert done");
					} else if(submit.status == 400) {
						alert("This is not a valid solution");
					} else {
						alert("not sure what code status is this");
					}*/
						
				} 
				//else submit.status == 400, error
				else {
					document.getElementById("status").innerHTML='<span>error</span>';
				}
			}
		}
		//Send Request
		submit.send(null);
	}
}



function shuffle(array) {
	var i = array.length;
	//console.log(i);
	var temp;
	var rand;
	var newArray = [];

	while (i--) {
		rand = Math.floor(Math.random() * (i-1) + 1);

		temp = array[i];
		array[i] = array[rand];
		array[rand] = temp;

	}

	return array;
}

function drawStraightLine(l,r,w) {
	var x1 = 0;
	var y1 = ($('#svg_lines').height() / height) * l - ($('#svg_lines').height() / (height*2));

	var x2 = $('#svg_lines').width();
	var y2 = ($('#svg_lines').height() / height) * r - ($('#svg_lines').height() / (height*2));

	var svg = document.getElementsByTagName('svg')[0];
	var path = document.createElementNS("http://www.w3.org/2000/svg", 'path');
	var attr = "M "+x1+ " " +y1+ " " +x2+ " "+y2;
	//console.log(attr);
	path.setAttribute("d", attr);
	path.style.stroke="blue";
	path.style.strokeWidth = "1px";
	//var weight = "<text x=\""+(x1+x2)/2+"\" y=\""+(y1+y2)/2+"\">"+w+"</text>";
	svg.appendChild(path);

	var weight = document.createElementNS("http://www.w3.org/2000/svg", 'text');
	/*var distance = Math.sqrt(Math.pow((x2-x1),2) + Math.pow((y2-y1),2));
	var gradient = (y2-y1)/(x2-x1);
	x_point = x1 + (4/6*distance)/ Math.sqrt(1+Math.pow(gradient,2));
	y_point = y1 + (4/6*distance)/ Math.sqrt(1+Math.pow(gradient,2));
	weight.setAttribute('x', x_point);
	weight.setAttribute('y', y_point);*/
	weight.setAttribute('x', (x1+x2)/2);
	weight.setAttribute('y', (y1+y2)/2);
	weight.textContent=w;
	svg.appendChild(weight);
}

function drawBoldLine(l,r, colour) {
	var x1 = 0;
	var y1 = ($('#svg_lines').height() / height) * l - ($('#svg_lines').height() / (height*2));

	var x2 = $('#svg_lines').width();
	var y2 = ($('#svg_lines').height() / height) * r - ($('#svg_lines').height() / (height*2));

	var svg = document.getElementsByTagName('svg')[0];
	var path = document.createElementNS("http://www.w3.org/2000/svg", 'path');
	var attr = "M "+x1+ " " +y1+ " " +x2+ " "+y2;
	//console.log(attr);
	path.setAttribute("d", attr);
	path.style.stroke=colour;
	path.style.strokeWidth = "5px";
	//var weight = "<text x=\""+(x1+x2)/2+"\" y=\""+(y1+y2)/2+"\">"+w+"</text>";
	svg.appendChild(path);
/*
	var weight = document.createElementNS("http://www.w3.org/2000/svg", 'text');
	weight.setAttribute('x', (x1+x2)/2);
	weight.setAttribute('y', (y1+y2)/2);
	weight.textContent=w;
	svg.appendChild(weight);*/
}

function drawCurvyLine(l, r, w) {

	var x1 = 0;
	var y1 = ($('#svg_lines').height() / height) * l - ($('#svg_lines').height() / (height*2));

	var x2 = $('#svg_lines').width();
	var y2 = ($('#svg_lines').height() / height) * r - ($('#svg_lines').height() / (height*2));

	var cpx1 = $('#svg_lines').width()/4;
	var cpy1 = $('#svg_lines').height()/4;
	var cpx2 = $('#svg_lines').width()/4*3;
	var cpy2 = $('#svg_lines').height()/4*3;

	var svg = document.getElementsByTagName('svg')[0];
	var path = document.createElementNS("http://www.w3.org/2000/svg", 'path');
	var curveAttr = "M "+x1+ "," +y1+ " C" +cpx1+ ","+cpy1 +" "+cpx2+","+cpy2+" "+x2+","+y2;
	//console.log(curveAttr);
	path.setAttribute("d", curveAttr);
	path.style.fill="transparent";
	path.style.stroke="blue";
	path.style.strokeWidth = "1px";
	svg.appendChild(path);

	var weight = document.createElementNS("http://www.w3.org/2000/svg", 'text');
	weight.setAttribute('x', (x1+x2)/2);
	weight.setAttribute('y', (y1+y2)/2);
	weight.textContent=w;
	svg.appendChild(weight);
}