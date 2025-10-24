
package com.mycompany.mazeproject;
import java.util.*;
public class Solver {
    ArrayList<Maze> closed_list, open_list;
    Solver()
    {
        closed_list = new ArrayList<Maze>();
        open_list = new ArrayList<Maze>();
    }
    private void print_path_cost(Maze maze)
    {
        System.out.print(maze.get_cost());
    }
    //#############################################
    public Maze A_star_search(Maze maze) // A* algorithm
    {
        closed_list.clear();          // clear the closed list first
        open_list.add(maze);          // add the maze that u sent to it to the open list
        do {
            if(open_list.size() == 0) // this is useless, but it is in the slides code, and the professor had it, so dont wannt remove it in case it is sth critical
            {
                return null;
            }
            maze = this.best_node();       // we want the node, that has the best "F" funciton
            if(maze.is_equal_goal())   // we go back to the function to check the node is the goal or not. ( the fucniton in the maze.java) , and in the begining
            {                        // it is the first maze, and we want to check it we are already at the goal or not.
                this.print_path_cost(maze);
                closed_list.add(maze);
                return maze;
            }
            expand_node(maze);       // go the function which is required to do
            open_list.remove(maze);
            closed_list.add(maze);
        } while(open_list.size() != 0);
        return null;
    }
    //#############################################
    
    private Maze best_node()  // there is a chance that u r in an openlist and got to a point of which u have multiple nodes with the same "F" function
    {                        // and u dont know the number of nodes that have the same "F" function
        Maze best = open_list.get(0); // best equals to the first 3onsor in the open list
        for(int i=1; i<open_list.size(); i++) {  // for loop to find the least "F" function, (( the node that has the least "F" fucniton )) , but since we might have more
                                                // than 1 node that have the same "F" function, we used this "FOR LOOP" only to determine the value of this "F" function.
            if(open_list.get(i).get_F() < best.get_F())  // best equals to the first 3onsor in the open list
            {
                best = open_list.get(i);
            }
        }

        ArrayList<Maze> temp = new ArrayList<Maze>();
        for(int i=0; i<open_list.size(); i++)    // this for loop is takmele for the previous "FOR LOOP" in which we use it like this :
        {                                       // we created a NEW array list  and we want to store the nodes that have the best "F" Function that we calculated before.
            if(open_list.get(i).get_F() == best.get_F())  // comparing the best "F" funciton
            {
                temp.add(open_list.get(i)); // adding the nodes that equal to the "F" function to the "temp" list that i just created.
            }
        }

        if(temp.size() > 1)  // if the size of the "temp" (temp is the list that has the nodes of the best "f" function") is bigger than 1, then we have more than 1 node
                            // that has the best "F" function.
        {
            best = Maze.compare_mazes_heuristics(temp); // if we have more than node with the same "F", then we need to compare the hueristics.
        }
        
        
        return best; // if it the size of temp is "1", then we return it immediatly cuz this is the BEST "F".
    }
    //##############################################
    private int get_index_of_matched_maze_closed(Maze maze){
         
            for(int i=0; i<closed_list.size(); i++)
            {
                if(maze.get_row_start() == closed_list.get(i).get_row_start() && maze.get_col_start() == closed_list.get(i).get_col_start()) // lef 3ala kol el closed list, and ef7as
                {                                                                                                                  // if we have a node which is in the
                                                                                                                                    // closed list and has the same start
                                                                                                                                    // (same state) (( cuz same start means
                                                                                                                                    // that they are at the  same location ))
                    return i; //return the index of that node that has the same row and the same column, (return index of the node that has the same location of the left)
                }
                else continue;
            }
            return -1; // if no nodes at all in the list, we need to return "-1" (no nodes has the same start as the 'left" )
                        // we do that, cuz when we go back, we want to compare if the node we found of index "i" is better than left itself, or not, and which one should we keep
                        // between the left, and the one that has the same location
       
    }
    //###############################################
    private int get_index_of_matched_maze_open(Maze maze){
         
             for(int i=0; i<open_list.size(); i++)
            {
                if(maze.get_row_start() == open_list.get(i).get_row_start() && maze.get_col_start() == open_list.get(i).get_col_start())
                {
                    return i;
                }
            }
            return -1;
        }
       
    
    //##############################################
    private void expand_node(Maze maze)
    {
        Maze left, right, up, down; // we have 4 mazes that we can expand to , left, right,up down

        /*
         1- First Case : [maze.getcolstart() = 0] means that we are not gonna expand a node that is outside of out maze to the left, but if it is not "0" then it will expand to the left

         [(maze.getBlock(maze.getRowStart()] {{ getBlock will take from the matrix that is inside the maze}} , it we want to expand to

         2- SECOND CASE :[maze.getColStart()-1) != -1]  ,"" means that it is not an obsticale. "" means the same row, the row will stay the same, but the column will be decremented by "1". if we expand to the left by 1, if it
         "-1", then it we wont expand to the left, cuz that is an obstacle
        
        3- third case: not expand to the node that is exit
          
         4- fourth Case : NOT EXPANDING TO THE NODE THAT WE INITIALLY CAME FROM (PARENT) (( not the same path ))
         maze.getColStart()-1) != 1 , means that im not coming from the previous node that i jsut expanded from cuz it the parent for this node.
        */

        if((maze.get_col_start() != 0) && (maze.get_block(maze.get_row_start(), maze.get_col_start()-1) != -1) && (maze.get_block(maze.get_row_start(), maze.get_col_start()-1) != 4) && (maze.get_block(maze.get_row_start(), maze.get_col_start()-1) != 1))
        {
            left = new Maze(maze.get_maze_height(), maze.get_maze_width()); // new object of maze with same width and hight
            if(maze.get_number_goal()==2){
            left.set_goal1(maze.get_row_goal1(), maze.get_col_goal1()); // all the mazes we creat will have the same goal1
            left.set_goal2(maze.get_row_goal2(), maze.get_col_goal2()); // all the mazes we creat will have the same goal2
            }
            else left.set_goal1(maze.get_row_goal1(), maze.get_col_goal1());// all the mazes we creat will have the same goal1
            
            for(int i=0; i<maze.get_maze_height(); i++)  // we want to put the whole maze that we have and put in the "left", and the maze right now is the parent
            {
                for(int j=0; j<maze.get_maze_width(); j++)
                {
                    left.maze_matrix[i][j] = maze.maze_matrix[i][j];
                }
            }
            
            left.set_start(maze.get_row_start(), maze.get_col_start()-1); // we make a new start for the maze that we just put in the left, it has the same row, but
                                                                        // decrement the column by 1
            left.set_cost(maze.get_cost()+1); // the previous cost + 1 , and put it the new cost
            left.set_direction(1); // the direction is "1" for the "LEFT"
            left.calculate_heuristic(); // find the huereistic for the node that we at righn now at the left.
            
            // here we want to compare the state (left) with the states that we have in the closed list and open
            // this is the else in the code, if it is not in the clsoed or the open, add it
            int index1 = this.get_index_of_matched_maze_closed(left); // we want to compare with the clsoed list
            int index2 = this.get_index_of_matched_maze_open(left); //  we want to compare with the open list
            if(index1 != -1)  // if the node is not in the open list or the closed list, we want to add it to the open list. , ELSE, we want to do some stuff, we need to c
            {                   // if it is in the open, then we want to compare  the cost of the new node to the old one, if the new node is better cost, then
                                // we need to remove the old and put the new one in the open list.
                                // but if the old is better that the new, we want to ignore the new one.

                                // it is impossible to get to the state with more than 2 roads, u can NEVER FIND IT 3 TIMES IN THE OPEN LIST.

                                // it is impossible for the node to be in the closed list and the open list at the same.


                                // if it is the closed list ( here) , we neeed to check the cost, and the lower cost is the one that we want to keep.

                Maze m = closed_list.get(index1); // we take the node of the index that we got from the function and put in an object "m"
                                                    // it contains the nodes of the closed list
                if(left.get_cost() < m.get_cost()) // if the cost for the left node is less than the cost for the node of the index
                {
                    closed_list.remove(index1); // remove the node of the index1, cuz the cost of it is bigger than the cost of the left
                    left.set_perant(maze); // make the original maze that we expanded the parent for the "left" maze.
                    left.print_maze();
                    open_list.add(left); // add it to the open list since we removed the node of index from the closed list, and the "left" is added to the open list
                }
                else {
                    m.print_maze();
                }
            }
            else if(index2 != -1) // we have a node that equals to it from the open list and we enter the "IF Statement"
            {
                Maze m = open_list.get(index2);
                if(left.get_cost() < m.get_cost())
                {
                    open_list.remove(index2);
                    left.set_perant(maze);
                    left.print_maze();
                    open_list.add(left);    // add the 'left' to the open list.
                }
                else
                {
                    m.print_maze();
                }
            }

            else // if it is not an old node, and a new one, then we need to add it immediatly to the open list.
            {
                left.set_perant(maze);
                open_list.add(left);
                left.print_maze();
            }
        }



        if((maze.get_col_start() != maze.get_maze_width()-1) && (maze.get_block(maze.get_row_start(), maze.get_col_start()+1) != -1) && (maze.get_block(maze.get_row_start(), maze.get_col_start()+1) != 4) && (maze.get_block(maze.get_row_start(), maze.get_col_start()+1) != 1)) {
            right = new Maze(maze.get_maze_height(), maze.get_maze_width());
            if(maze.get_number_goal()==2){
            right.set_goal1(maze.get_row_goal1(), maze.get_col_goal1()); 
            right.set_goal2(maze.get_row_goal2(), maze.get_col_goal2()); 
            }
            else right.set_goal1(maze.get_row_goal1(), maze.get_col_goal1());
            for(int i=0; i<maze.get_maze_height(); i++) {
                for(int j=0; j<maze.get_maze_width(); j++) {
                    right.maze_matrix[i][j] = maze.maze_matrix[i][j];
                }
            }
            right.set_start(maze.get_row_start(), maze.get_col_start()+1);
            right.set_cost(maze.get_cost()+1);
            right.set_direction(2);
            right.calculate_heuristic();
            int index1 = this.get_index_of_matched_maze_closed(right);
            int index2 = this.get_index_of_matched_maze_open(right);
            if(index1 != -1) {
                Maze m = closed_list.get(index1);
                if(right.get_cost() < m.get_cost()) { //get_f??
                    closed_list.remove(index1);
                    right.set_perant(maze);
                    right.print_maze();
                    open_list.add(right);
                }
                else {
                    m.print_maze();
                }
            }
            else if(index2 != -1){
                Maze m = open_list.get(index2);
                if(right.get_cost() < m.get_cost()) {
                    open_list.remove(index2);
                    right.set_perant(maze);
                    right.print_maze();
                    open_list.add(right);
                }
                else {
                    m.print_maze();
                }
            }
            else {
                right.set_perant(maze);
                open_list.add(right);
                right.print_maze();
            }
        }



        if((maze.get_row_start() != 0) && (maze.get_block(maze.get_row_start()-1, maze.get_col_start()) != -1)&& (maze.get_block(maze.get_row_start()-1, maze.get_col_start()) != 4) && (maze.get_block(maze.get_row_start()-1, maze.get_col_start()) != 1)) {
            up = new Maze(maze.get_maze_height(), maze.get_maze_width());
            if(maze.get_number_goal()==2){
            up.set_goal1(maze.get_row_goal1(), maze.get_col_goal1()); 
            up.set_goal2(maze.get_row_goal2(), maze.get_col_goal2()); 
            }
            else up.set_goal1(maze.get_row_goal1(), maze.get_col_goal1());
            for(int i=0; i<maze.get_maze_height(); i++) {
                for(int j=0; j<maze.get_maze_width(); j++) {
                    up.maze_matrix[i][j] = maze.maze_matrix[i][j];
                }
            }
            
            up.set_start(maze.get_row_start()-1, maze.get_col_start());
            up.set_cost(maze.get_cost()+1);
            up.set_direction(3);
            up.calculate_heuristic();
            int index1 = this.get_index_of_matched_maze_closed(up);
            int index2 = this.get_index_of_matched_maze_open(up);
            if(index1 != -1) {
                Maze m = closed_list.get(index1);
                if(up.get_cost() < m.get_cost()) {
                    closed_list.remove(index1);
                    up.set_perant(maze);
                    up.print_maze();
                    open_list.add(up);
                }
                else {
                    m.print_maze();
                }
            }
            else if(index2 != -1){
                Maze m = open_list.get(index2);
                if(up.get_cost() < m.get_cost()) {
                    open_list.remove(index2);
                    up.set_perant(maze);
                    up.print_maze();
                    open_list.add(up);
                }
                else {
                    m.print_maze();
                }
            }
            else {
                up.set_perant(maze);
                open_list.add(up);
                up.print_maze();
            }
        }



        if((maze.get_row_start() != maze.get_maze_height()-1) && (maze.get_block(maze.get_row_start()+1, maze.get_col_start()) != -1)&& (maze.get_block(maze.get_row_start()+1, maze.get_col_start()) != 4) && (maze.get_block(maze.get_row_start()+1, maze.get_col_start()) != 1)) {
            down = new Maze(maze.get_maze_height(), maze.get_maze_width());
            if(maze.get_number_goal()==2){
            down.set_goal1(maze.get_row_goal1(), maze.get_col_goal1()); 
            down.set_goal2(maze.get_row_goal2(), maze.get_col_goal2()); 
            }
            else down.set_goal1(maze.get_row_goal1(), maze.get_col_goal1());
            for(int i=0; i<maze.get_maze_height(); i++) {
                for(int j=0; j<maze.get_maze_width(); j++) {
                    down.maze_matrix[i][j] = maze.maze_matrix[i][j];
                }
            }
            down.set_start(maze.get_row_start()+1, maze.get_col_start());
            down.set_cost(maze.get_cost()+1);
            down.set_direction(4);
            down.calculate_heuristic();
            int index1 = this.get_index_of_matched_maze_closed(down);
            int index2 = this.get_index_of_matched_maze_open(down);
            if(index1 != -1) {
                Maze m = closed_list.get(index1);
                if(down.get_cost() < m.get_cost()) {
                    closed_list.remove(index1);
                    down.set_perant(maze);
                    down.print_maze();
                    open_list.add(down);
                }
                else {
                    m.print_maze();
                }
            }
            else if(index2 != -1){
                Maze m = open_list.get(index2);
                if(down.get_cost() < m.get_cost()) {
                    open_list.remove(index2);
                    down.set_perant(maze);
                    down.print_maze();
                    open_list.add(down);
                }
                else {
                    m.print_maze();
                }
            }
            else {
                down.set_perant(maze);
                open_list.add(down);
                down.print_maze();
            }
        }
    }
    public static void main(String[] args) {
		Maze maze = new Maze(5, 5);
		maze.print_maze();
		maze.set_exit(0, 0);
		maze.set_exit(0, 4);
		maze.set_room(1, 1);
		maze.set_room(1, 2);
		maze.set_room(2, 1);
		maze.set_start(2, 0);
		maze.set_goal1(0, 4);
		//maze.set_goal2(1, 2);
		//maze.set_goal1(2, 3);
		// maze.setObstacle(1, 0);
		maze.set_obstacle(2, 2);
		//maze.setObstacle(2, 3);
		maze.set_obstacle(2, 3);
		maze.set_obstacle(3, 3);
		// maze.setObstacle(2, 2);
		maze.set_obstacle(3, 4);
		Solver solver = new Solver();
		Maze m = solver.A_star_search(maze);
		System.out.println();
		System.out.println();
		System.out.println();
		if(m == null) {
			System.out.print("no solution");
			return;
		}
		String str = " (Goal)";
		do {
			if(m.get_direction() == 1) {
				str = " -> Left" + str;
			}
			else if(m.get_direction() == 2) {
				str = " -> Right" + str;
			}
			else if(m.get_direction() == 3) {
				str = " -> Up" + str;
			}
			else if(m.get_direction() == 4) {
				str = " -> Down" + str;
			}
			m = m.get_perant();
		} while( m.get_direction() != 0);
		str = "Start" + str;
		System.out.print(str);
	}
}
