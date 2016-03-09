<?php require_once 'connect.php';

function insertRow($graph_id, $num_edges, $score) {
	$insert = "INSERT INTO record VALUES ($graph_id, $num_edges, $score)";
	global $db;
	
	if($db -> query($insert) == TRUE) {
		$isInserted = 1;
	} else {
		$isInserted = 0;
	}
	//return $isInserted;
}

function updateRow($graph_id, $num_edges, $score) {
	$update = "UPDATE record SET matched=$num_edges, score=$score WHERE graph_id=$graph_id";
	global $db;
	
	if($db -> query($update) == TRUE) {
		$isUpdated = 1;
	} else {
		$isUpdated= 0;
	}
	//return $isUpdated;
}

function isBetter($graph_id, $num_edges, $score) {
	global $db;
	$query1 = $db -> query("SELECT matched FROM record WHERE graph_id=$graph_id");
	$max_edges = $query1 -> fetch_object();
	$query2 = $db -> query("SELECT score FROM record WHERE graph_id=$graph_id");
	$high_score = $query2 -> fetch_object();

	//debug_to_console($max_edges->matched);
	//debug_to_console($high_score->score);

	$new_best;
	if((int)$num_edges > $max_edges->matched) {
		$new_best = 1;
	} else if ((int)$num_edges == $max_edges->matched) {
		if((int)$score >= $high_score->score) {
			$new_best = 1;
		} else {
			$new_best = 0;
		}
	} else {
		$new_best = 0;
	}
	return $new_best;
}

function getHighScore($graph_id) {	
	global $db;
	$query2 = $db -> query("SELECT score FROM record WHERE graph_id=$graph_id");
	$high_score = $query2 -> fetch_object();
	return $high_score->score;
}

function getMaxEdges($graph_id) {	
	global $db;
	$query1 = $db -> query("SELECT matched FROM record WHERE graph_id=$graph_id");
	$max_edges = $query1 -> fetch_object();
	return $max_edges->matched;
}

function debug_to_console( $data ) {

    if ( is_array( $data ) )
        $output = "<script>console.log( 'Debug Objects: " . implode( ',', $data) . "' );</script>";
    else
        $output = "<script>console.log( 'Debug Objects: " . $data . "' );</script>";

    echo $output;
}

function verifyGraph( $sol , $graph) {

	$found=0;
	foreach ($sol as $sol_edge) {
		foreach ($graph as $graph_edge) {
			if ($sol_edge == $graph_edge) {
				$found=1;
				break;
			}
		}
		if($found) {
			return 0;
		} else {
			$found=0;
		}
	}
	return 1;
}

?>

