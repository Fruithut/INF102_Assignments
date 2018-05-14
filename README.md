#### *NB: This repository has been cloned from a GitLab server that was used for assignment delivery*

INF102 assignment 3
=======================================

## Task 1 Printing tree-structured directories
In this exercise you have to make a program that takes in a file containing information
about a folder system and prints out the files and the folders in a specific way. We’ll call
both files and folders items. Each item should be printed as "'-itemname" after the correct
amount of indentation. The items inside a folder should be indented by 2 spaces more than
their parent folder, and the root folder should have zero indentation. Hint: a file can be
treated as an empty folder.  

As an optional refinement (not compulsory, only for those students who want some extra
challenge): print the content of each folder in alphabetical order.  

The input file will consists of three parts:  

• The first line is an integer telling how many items there are in total.  

• The next line contains all the item names separated by spaces. The first item has id  
0, the next item has id 1, etc. Item 0 is always the root folder.  

• The rest of the lines are in the format "folderX : itemY ...itemZ" which means  
that the folder with id folderX contains the items with ids itemY, ..., itemZ.  

Here is an example of an input file and output file:

##### Input
7  
root folder1 folder2 folder3 file1.txt file2.txt file3.txt  
0 : 1 2  
1 : 4  
2 : 3 5  
3 : 6

##### Output
'-root  
&nbsp;&nbsp; '-folder1  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; '-file1.txt  
&nbsp;&nbsp; '-folder2  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; '-file2.txt  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; '-folder3  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; '-file3.txt  

You are provided with a base class PrintTree with a main method and an unfinished  
method formatTree that takes a filename and returns a string containing the formatted  
file structure.  

## Task 2 Finding the shortest path in a maze
A maze consist of paths, a start point and an endpoint. For this exercise you are given two classes:  

+ Point with the integer fields x and y.  

+ Maze with the width and height for the maze (as integers), a point for the start  
position, the end position, and a set of points representing the walls in the maze.  
Origin (0, 0) is the upper left point. This class also reads a text file into a maze object.  

In the class Maze, implement the solve method to find the shortest path between the start  
position and the end position. Only horizontal or vertical steps within the boundaries of  
the maze are allowed, and of course only if the endpoint is not a wall. The method should  
return a list of points starting from the first position after the start, ending with the position  
at the goal. The list of points must be in the same order as the player would walk.  

I.e. if a maze has width=2, height=3, start=(0,2), end=(0,0), walls=\[(0,1)\], then the solution is \[(1,2), (1,1), (1,0), (0,0)\]. 
 
You may use data structures from the course repository or algs4, but you are to implement the algorithm yourself.  

If you solve the problem correctly you should see an image of the path you found in your default browser.  

## Task 3 Renewing the sewage system
Bergen is renewing parts of their sewage system, mainly the pipes between the public toilets.  
They need to connect all the toilets together, and they want to use as few meters pipe as  
possible. You have been given the responsibilty to find the best way to connect the toilets.  

You’ll use the classes provided in the package connectingToilets. You are to implement the  
method connectToilets. This method gets a set of toilets, where a toilet has a name, an x  
coordinate and an y coordinate. A pipe between two toilets is represented by an object of  
the class Edge, which holds the two toilets and the euclidean distance between them. The  
method should return a set of these edges, where the total length of pipes are the smallest  
possible - while all the toilets are connected.  

Run the program, and you’ll see a map of the connections you chose. Your solution should  
also work for the map of toilets in Australia and the map of random toilets.  

Tips: Does this seem like a common graph problem? If you don’t know where to start:  
What if all toilets where connected to each other in the first place?  

Source:  
+ https://data.norge.no/data/bergen-kommune/dokart-bergen-sentrum  
+ http://www.civicdata.com/dataset/national-public-toilet-map-data