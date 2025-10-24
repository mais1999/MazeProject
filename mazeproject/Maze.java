
package com.mycompany.mazeproject;
import java.util.*;
public class Maze {
    private int width,height, col_goal1,row_goal1,col_goal2,row_goal2,col_start,
                row_start,cost,heuristic,direction,number_goal;
    Maze parent;
    int [][] maze_matrix;
    //constructer
    Maze(int height, int width)
    {
        this.height = height;
        this.width = width;
        this.maze_matrix = new int[this.height][this.width];
        //this.col_goal1=0;
        //this.row_goal1=0;
        //this.col_goal2=0;
        //this.row_goal2=0;
        //this.col_start=0;
        //this.row_start=0;
        this.cost = 0;
        this.heuristic=0;
        this.direction = 0;
        this.number_goal=0;
        this.parent = null;
        for(int i=0; i<this.height; i++)//row
        {
            for(int j=0; j<this.width; j++)//col
            {
                maze_matrix[i][j] = 0;
            }
        }
    }
     public void set_perant(Maze parent)  // to know the parent of this maze, since we will remove the previous maze and consider the maze we are in right not
    {
        this.parent = parent;
    }
    public Maze get_perant()  // this is to know the parent of this node, so we will use it to print out the path
    {
        return this.parent;
    }
     public void set_start(int row, int col)  // set the start point of the maze
    {
        
        if((row>=0 && row<height) && (col>=0 && col<width))
        {
        this.row_start = row;
        this.col_start = col;
        maze_matrix[this.row_start][this.col_start] = 1; // 1 means the start
        
        }
    }
    public void set_goal1(int row, int col)  // set the goal point of this maze
    {
        
       
        this.row_goal1 = row;
        this.col_goal1 = col;
        maze_matrix[row_goal1][col_goal1] = 2; // 2 means the goal
        this.number_goal=this.number_goal+1;
        
     
    }
    public void set_goal2(int row, int col)  // set the goal point of this maze
    {
        
       
        this.row_goal2 = row;
        this.col_goal2 = col;
        maze_matrix[row_goal2][col_goal2] = 2; // 2 means the goal
        this.number_goal=this.number_goal+1;
        
        
    }
    public void set_obstacle(int i, int j)
    {
        maze_matrix[i][j] = -1; // obstacle is represented by "-1"
    }
    public void set_room(int i, int j)
    {
        maze_matrix[i][j] = 3; // room is represented by "3"
    }
    public void set_exit(int i, int j)
    {
        maze_matrix[i][j] = 4; // exit is represented by "4"
    }
    public void set_cost(int cost)
    {
        this.cost = cost; // each move we increase the cost by 1
    }
    public int calculate_heuristic1()
    {
       return (Math.abs(this.row_goal1 - this.row_start) + Math.abs(this.col_goal1 - this.col_start)); // manhaten distancce for goal1
    }
    public int calculate_heuristic2()
    {
        return (Math.abs(this.row_goal2 - this.row_start) + Math.abs(this.col_goal2 - this.col_start)); // manhaten distancce for goal2
    }
    public void calculate_heuristic(){ //for compare wich is the best h between nodes
        if(this.number_goal == 2){
            
        this.heuristic =Math.min(calculate_heuristic1(), calculate_heuristic2());
        }
        else 
         this.heuristic =calculate_heuristic1(); 
    }
    public void set_direction(int direction)  // to know the diretion of the node i am at.
    {
        this.direction = direction;
    }
    public int get_number_goal(){
        return this.number_goal;
    }
    public int get_maze_width() //to get the same width and height for the expanded nodes
    {
        return this.width;
    }
    public int get_maze_height()
    {
        return this.height;
    }
    public int get_row_start() //use when expand nodes for move
    {
        return this.row_start; // this is to know where the start of the maze
    }
    public int get_col_start()
    {
        return this.col_start; // this is to know where the start of the maze
    }
    
    public int get_row_goal1() //used when expand nodes to use the same goal for all maze
    {
        return this.row_goal1;
    }
    public int get_col_goal1()
    {
        return this.col_goal1;
    }

    public int get_row_goal2() //used when expand nodes to use the same goal for all maze
    {
        return this.row_goal2;
    }
    public int get_col_goal2()
    {
        return this.col_goal2;
    }
    public int get_cost()  // get cost only
    {
        return this.cost;
    }
    public int get_F()  // cost + huerestic
    {
        return this.cost + this.heuristic;
    }
    public int get_heuristic()  // get heuristic (manhaten only)
    {
        return this.heuristic;
    }
    public int get_block(int i, int j)  // return the block in the matrix that i am at right now.
    {
        return maze_matrix[i][j];
    }
     public int get_direction()  // to know the direction of the next node
    {
        return this.direction;
    }

    //#######################################
    public boolean is_equal_goal()   // used for A*, if we reach the node, and the start for it is the same as the one i am at, then this is the goal
    {                              // the way it works, is each time we expand and move to a next node, we will replace the nodes that we covered
                                   // by 1, which means that this a node that i already covered, and there is no need to go back to it, cuz it will
                                   // just increase the cost again by 1 if i expand back to it.
                                   // now that im at the end node, if i am at the same row and the same column of the goal node, then im at the goal.
        if(this.number_goal == 2){ //to check we can init row and goal for G2 to negative number
        if((this.get_row_goal1() == this.get_row_start() && this.get_col_goal1() == this.get_col_start())||(this.get_row_goal2() == this.get_row_start() && this.get_col_goal2() == this.get_col_start()))
        {
            return true;
        }
        else return false;
        }
        else {
            if((this.get_row_goal1() == this.get_row_start() && this.get_col_goal1() == this.get_col_start())){
            return true;
            }
            else return false;       
        }
    }
    //######################################
    public void print_maze()  // print out the maze on the console
    {
        System.out.println();
        for(int i=0; i<height; i++)
        {
            for(int j=0; j<width; j++)
            {
                System.out.print(maze_matrix[i][j]+ " ");
            }
            System.out.println();
        }
    }
    //########################################
    public static Maze compare_mazes_heuristics(ArrayList<Maze> list)  // we want to get the node that have the same hueristic of the ones that have the
    {                                                                // same BEST "F"
        Maze best = list.get(0); // the best is the first node.
        for(int i=1; i<list.size(); i++)  // this works the same as the function which is called "bestnode()"
        {
            if(list.get(i).get_heuristic() < best.get_heuristic())
            {
                best = list.get(i);
            }
            else continue;
        }

        ArrayList<Maze> temp = new ArrayList<Maze>(); // creat a new array list called "temp"
        for(int i=0; i<list.size(); i++) // here we do the same as before to get the best nodes, but here we want to find the best "hueristic" and them
        {                                // to the new "temp" list which cosists of the best nodes with the best hueritic of the ones with best "F".
            if(list.get(i).get_heuristic()==best.get_heuristic())
            {
                temp.add(list.get(i));
            }
            else continue;
        }

        if(temp.size() > 1)  // if the list "temp" of the best huericstic nodes is more than 1, then we have more than  one node with best "F" and
        {                    // and best "H"
            best = Maze.compare_mazes_directions(temp); // in that case, we need to decide the direction in which it came from and use the tie breaker
        }
        return best;
    }
    public static Maze compare_mazes_directions(ArrayList<Maze> list)  // tie breaker of the nodes, in case the have the best "F" and best "H".
    {
        Maze best = list.get(0); //best is the first 3onsor in the list
        for(int i=1; i<list.size(); i++) // we want to compare the node with other nodes, if it has the least direction, since we used tie breaker
        {                                // as numbers to represent (left, right, up down)

            if(list.get(i).get_direction() < best.get_direction()) // if the new node im comparing to has higher priority than the best, then we make
            {                                                       // the new node is the best

                best = list.get(i); // the list points to the bbest node, and we put it in the best, and we want to return it
            }
            //1->L 2->R 3->U 4->D
        }
        return best;
}
    
}
