<?php require_once 'db.php';

header('Content-Type: text/json');

	
	$cmd = $_GET[cmd];
	$x_value = $_GET[graph_id];
	
	if(($x_value < 1) || ($x_value > 9)) {
		echo "Invalid X values";
	}

	switch ($x_value) {
		case "1":
			$graph = array();
			$edge1 = array(1,2, 10);
			$edge2 = array(2,1, 33);
			$edge3 = array(3,2, 25);
			$edge4 = array(3,3, 40);
			array_push($graph, $edge1);
			array_push($graph, $edge2);
			array_push($graph, $edge3);
			array_push($graph, $edge4);
			$int_n = (int)3+1;
			$int_m = (int)3+1;
			break;

		case "2":
			$graph = array();
			$edge1 = array(1,1, 70);
			$edge2 = array(2,2, 36);
			$edge3 = array(2,3, 25);
			array_push($graph, $edge1);
			array_push($graph, $edge2);
			array_push($graph, $edge3);
			$int_n = (int)2+1;
			$int_m = (int)3+1;
			break;

		case "3":
			$graph = array();
			$edge1 = array(1,2, 45);
			$edge2 = array(2,1, 65);
			$edge3 = array(1,1, 25);
			$edge4 = array(2,2, 35);
			array_push($graph, $edge1);
			array_push($graph, $edge2);
			array_push($graph, $edge3);
			array_push($graph, $edge4);
			$int_n = (int)2+1;
			$int_m = (int)2+1;
			break;

		case "4":
			$graph = array();
			$edge1 = array(1,1, 23);
			$edge2 = array(2,1, 40);
			$edge3 = array(3,3, 67);
			$edge4 = array(4,2, 38);
			$edge5 = array(5,3, 89);
			array_push($graph, $edge1);
			array_push($graph, $edge2);
			array_push($graph, $edge3);
			array_push($graph, $edge4);
			array_push($graph, $edge5);
			$int_n = (int)5+1;
			$int_m = (int)3+1;
			break;

		case "5":
			$graph = array();
			$edge1 = array(1,2, 80);
			$edge2 = array(2,2, 57);
			$edge3 = array(3,1, 77);
			$edge4 = array(4,2, 76);
			array_push($graph, $edge1);
			array_push($graph, $edge2);
			array_push($graph, $edge3);
			array_push($graph, $edge4);
			$int_n = (int)4+1;
			$int_m = (int)2+1;
			break;

		case "6":
			$graph = array();
			$edge1 = array(1,1, 15);
			$edge2 = array(2,1, 10);
			$edge3 = array(2,2, 50);
			$edge4 = array(3,2, 35);
			$edge5 = array(3,3, 49);
			$edge6 = array(3,4, 17);
			array_push($graph, $edge1);
			array_push($graph, $edge2);
			array_push($graph, $edge3);
			array_push($graph, $edge4);
			array_push($graph, $edge5);
			array_push($graph, $edge6);
			$int_n = (int)3+1;
			$int_m = (int)4+1;
			break;

		case "7":
			$graph = array();
			$edge1 = array(1,4, 23);
			$edge2 = array(2,2, 45);
			$edge3 = array(3,1, 78);
			$edge4 = array(3,4, 96);
			$edge5 = array(4,2, 64);
			$edge6 = array(4,5, 51);
			$edge7 = array(5,3, 81);
			array_push($graph, $edge1);
			array_push($graph, $edge2);
			array_push($graph, $edge3);
			array_push($graph, $edge4);
			array_push($graph, $edge5);
			array_push($graph, $edge6);
			array_push($graph, $edge7);
			$int_n = (int)5+1;
			$int_m = (int)5+1;
			break;

		case "8":
			$graph = array();
			$edge1 = array(1,2, 10);
			$edge2 = array(2,3, 20);
			$edge3 = array(3,1, 30);
			$edge4 = array(4,4, 40);
			$edge5 = array(5,5, 50);
			$edge6 = array(5,6, 60);
			$edge7 = array(6,4, 70);
			$edge8 = array(6,7, 80);
			array_push($graph, $edge1);
			array_push($graph, $edge2);
			array_push($graph, $edge3);
			array_push($graph, $edge4);
			array_push($graph, $edge5);
			array_push($graph, $edge6);
			array_push($graph, $edge7);
			array_push($graph, $edge8);
			$int_n = (int)6+1;
			$int_m = (int)7+1;
			break;

		case "9":
			$graph = array();
			$edge1 = array(1,2, 47);
			$edge2 = array(1,3, 13);
			$edge3 = array(1,5, 29);
			$edge4 = array(2,2, 91);
			$edge5 = array(2,3, 79);
			$edge6 = array(2,4, 67);
			$edge7 = array(3,6, 46);
			$edge8 = array(4,5, 37);
			$edge9 = array(5,7, 31);
			$edge10 = array(6,6, 29);
			$edge11 = array(6,8, 23);
			$edge12 = array(7,7, 17);
			$edge13 = array(8,7, 7);
			array_push($graph, $edge1);
			array_push($graph, $edge2);
			array_push($graph, $edge3);
			array_push($graph, $edge4);
			array_push($graph, $edge5);
			array_push($graph, $edge6);
			array_push($graph, $edge7);
			array_push($graph, $edge8);
			array_push($graph, $edge9);
			array_push($graph, $edge10);
			array_push($graph, $edge11);
			array_push($graph, $edge12);
			array_push($graph, $edge13);
			$int_n = (int)8+1;
			$int_m = (int)8+1;
			break;
	}
	
	//For cmd 'generate'
	if($cmd == "generate") {
		echo json_encode(array('N' => $int_n, 'M' => $int_m, 'E' => $graph));	
	}
	
	//For cmd 'submit'
	if($cmd == "submit") {
		$matched_edges = $_GET[solution];
		$score = $_GET[score];
		//debug_to_console($matched_edges);
		$num_edges = count(json_decode($matched_edges));
		//debug_to_console($num_edges);

		$isValid = verifyGraph(json_decode($matched_edges) , $graph);

		if($isValid) {
			$new_best = isBetter($x_value, $num_edges, $score);
			if($new_best) {
				updateRow($x_value, $num_edges, $score);
				echo json_encode(array('graph_id' => $x_value, 'new_best' => $new_best, 'num_match' => $num_edges, 'match_score' => $score));
			} else {
				$high_score = getHighScore($x_value);
				$max_edges = getMaxEdges($x_value);
				echo json_encode(array('graph_id' => $x_value, 'new_best' => $new_best, 'num_match' => $max_edges, 'match_score' => $high_score));
			}
		} else {
			echo "Invalid";
		}
	}

?>