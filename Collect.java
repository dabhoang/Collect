import java.io.*;
import java.util.Scanner;

class Collect{
	public static void main(String[] args) {
		System.out.println(".\n--------------------------------------------------------------------------- \n.");
		System.out.println("Welcome to the text-based game Collect!");
		

		//setup 
		int cities = 20;
		
		City[] map = new City[cities];

		int score = 0;
		int hints = cities;
		int peeks = 10;
		int collects = cities*2;
		boolean connected = false;
		while (connected == false){
			for(int i = 0; i < cities; i++){
				map[i] = new City();
				

				boolean uniqueNeighbors = false;
				for(int j = 0; j < 4; j++){
					uniqueNeighbors = false;
					while(uniqueNeighbors == false){
						map[i].neighbors[j] = ((int)(Math.random()*cities*5))%cities;//for city i, assign jth neighbor
						
						uniqueNeighbors = true;
						for(int k = 0; k < j; k++){
							if(map[i].neighbors[j] == map[i].neighbors[k]){
								uniqueNeighbors = false;
								
							}
						}
					}
				}
			}
			connected = true;
			for(int i = 1; i < cities; i++){

				if(BFSconnected(map, i,cities) == false)
					connected = false;
			}
		}

		for(int i = 0; i < 2; i++)
			map[i].value = -1;
		for(int i = 2; i < 6; i++)
			map[i].value = 1;
		for(int i = 6; i < 10; i++)
			map[i].value = 3;
		for(int i = 10; i < 14; i++)
			map[i].value = 5;
		for(int i = 14; i < 18; i++)
			map[i].value = 10;
		for(int i = 18; i < 20; i++)
			map[i].value = 50;

	


		int current = ((int)(Math.random()*cities*5))%cities;
		
		System.out.println("Game has been set up! ");

		System.out.println("Here are the rules: \nThere are 20 cities. There are 4 highways coming out of each city.");
		System.out.println("Highways are one way streets. You will start at a random city.");
		System.out.println("You start off with 40 collects, 20 hints, and 10 peeks. The game ends when you run out of collects.");
		System.out.println("Collect as many points as you can!");

		//start play
		boolean lastMoveCollect = false;
		while(collects != 0){
			System.out.println(".\n--------------------------------------------------------------------------- \n.");
			System.out.println("Your current score is " + score);
			System.out.println("You have " + collects + " collects left.");
			System.out.println("You have " + peeks + " peeks left.");
			System.out.println("You have " + hints + " hints left.");
			System.out.println(".\n. \n.");
			
			System.out.println("Choose an action. Here is the list of possible actions: \n");
			System.out.println("collect - you will collect the item at your current city and obtain as many points as the item is worth");
			System.out.println("note: you cannot collect from the same city twice in a row\n");
			System.out.println("h1 - take the first highway option and end at the city that it leads to\n");
			System.out.println("h2 - take the second highway option and end at the city that it leads to\n");
			System.out.println("h3 - take the third highway option and end at the city that it leads to\n");
			System.out.println("h4 - take the fourth highway option and end at the city that it leads to\n");
			System.out.println("hint - you will be given the length of the shortest path to each of the items\n");
			System.out.println("peek - the item at your current city will be revealed as well as the items at the cities that are a highway away from your current city\n");
			System.out.println("exit - exit the game\n");
			System.out.println(".\n------------ \n.");

			Scanner choose = new Scanner(System.in);
			String action = choose.nextLine();
			action = action.toLowerCase();

			System.out.println(".\n------------ \n.");

			if(action.equals("collect") && lastMoveCollect == true){
				System.out.println("cannot collect from the same city twice in a row");
			}
			if(action.equals("collect") && lastMoveCollect == false){
				if(map[current].quantity>0){
					
					map[current].quantity--;
					
					if(map[current].value > 0){
						System.out.println("You've collected " + map[current].value + " points");
						score += map[current].value;
					}
					else if(map[current].value == -1){
						collects += 5; 
						System.out.println("You've collected 5 bonus collects");
					}				
					lastMoveCollect = true;
				}
				else
					System.out.println("no more items left at this city");
				collects--;
			}
			else if(action.equals("h1")){
				current = map[current].neighbors[0];
				System.out.println("Taking highway 1");
				lastMoveCollect = false;
			}
			else if(action.equals("h2")){
				current = map[current].neighbors[1];
				System.out.println("Taking highway 2");
				lastMoveCollect = false;
			}
			else if(action.equals("h3")){
				current = map[current].neighbors[2];
				System.out.println("Taking highway 3");
				lastMoveCollect = false;
			}
			else if(action.equals("h4")){
				current = map[current].neighbors[3];
				System.out.println("Taking highway 4");
				lastMoveCollect = false;
			}
			else if(action.equals("hint")){
				if(hints > 0){
					int[] dist = BFS(map, current, cities);
					int minDist;
				

					if(dist[0] < dist[1]){
						minDist = dist[0];
					}
					else minDist = dist[1];
					System.out.println("Nearest bonus collects is " + minDist + " cities away.");
					minDist = dist[2];
					for(int i = 3; i < 6; i++){
						if(dist[i] < minDist)
							minDist = dist[i];
					
					}
					System.out.println("Nearest 1-point item  is " + minDist + " cities away.");
					minDist = dist[6];
					for(int i = 7; i < 10; i++){
						if(dist[i] < minDist)
							minDist = dist[i];
					
					}
					System.out.println("Nearest 3-point item is " + minDist + " cities away.");
					minDist = dist[10];
					for(int i = 11; i < 14; i++){
						if(dist[i] < minDist)
							minDist = dist[i];
					
					}
					System.out.println("Nearest 5-point item is " + minDist + " cities away.");
					minDist = dist[14];
					for(int i = 15; i < 18; i++){
						if(dist[i] < minDist)
							minDist = dist[i];
					
					}
					System.out.println("Nearest 10-point item is " + minDist + " cities away.");
					if(dist[18] < dist[19]){
						minDist = dist[18];
					}
					else minDist = dist[19];
					System.out.println("Nearest 50-point item is " + minDist + " cities away.");

					

					//System.out.println(n1 + " " + n2 + " " + n3 + " " + n4);

					


					hints--;
				}
				else System.out.println("No more hints left");

			}
			else if(action.equals("score")){
				System.out.println("Your current score is:" + score);
			}
			else if(action.equals("peek")){
				if(peeks > 0){
					if(map[current].value>0)
						System.out.println("The city you are currently in contains " + map[current].value + "-point items");
					else System.out.println("The city you are currently in has a bonus 5 collects");

					int n1 = map[current].neighbors[0];
					int n2 = map[current].neighbors[1];
					int n3 = map[current].neighbors[2];
					int n4 = map[current].neighbors[3];

					if (map[n1].value == -1) 
						System.out.println("City at highway 1 has 5 bonus collects");
					else System.out.println("City at highway 1 contains " + map[n1].value + "-point items");

					if (map[n2].value == -1) 
						System.out.println("City at highway 2 has 5 bonus collects");
					else System.out.println("City at highway 2 contains " + map[n2].value + "-point items");

					if (map[n3].value == -1) 
						System.out.println("City at highway 3 has 5 bonus collects");
					else System.out.println("City at highway 3 contains " + map[n3].value + "-point items");

					if (map[n4].value == -1) 
						System.out.println("City at highway 4 has 5 bonus collects");
					else System.out.println("City at highway 4 contains " + map[n4].value + "-point items");

					peeks--;
				}
				else{
					System.out.println("You have no more peeks left.");
				}
			}
			else if(action.equals("exit")){
				collects = 0;
				System.out.println("Thank you for playing!");
				System.out.println("Your score is: " + score);
			}
			else{
				System.out.println("Invalid action");
			}
		}


		
		
	}

	public static int[] BFS(City[] island, int source, int size){
		int[] colors = new int[size];
		int[] distances = new int[size];
		for(int i = 0; i < size; i++){
			colors[i] = 0; 
			//white = 0, gray = 1, black = 2
			distances[i] = 999;
		}
		colors[source] = 1;
		distances[source] = 0;
		Queue bfs = new Queue();
		bfs.enqueue(source);
		while(bfs.isEmpty() == false){
			int x = bfs.dequeue();
			for(int i = 0; i < 4; i++){
				int nb = island[x].neighbors[i];
				if (colors[nb] == 0){
					colors[nb] = 1;
					distances[nb] = distances[x] + 1;
					bfs.enqueue(nb);
				}
			}
			colors[x] = 2;
		}
		return distances;

	}
	public static boolean BFSconnected(City[] island, int start, int cities){
		int[] dist = BFS(island, start, cities);
		for(int i = 0; i < cities; i++){
			if(dist[i] == 999)
				return false;
		}
		return true;
	}

}
class City{
	int[] neighbors;
	int quantity;
	int value;
	City(){
		neighbors = new int[4];
		quantity = 4;
		value = 0; 
	}

}
class Queue{
	QueueNode head;
	QueueNode tail;
	Queue(){
		head = null;
		tail = null;

	}
	boolean isEmpty(){
		return (head == null);
	}
	void enqueue(int data){
		QueueNode add = new QueueNode(data);
		if(isEmpty() == true){
			head = add;
			tail = add;
		}
		else{
			tail.next = add;
			tail = add;
		}
	}
	int dequeue(){
		if(isEmpty() == true)
			return -1;
		int data = head.id;
		head = head.next;
		if(head == null)
			tail = null;
		return data;
	}

}
class QueueNode{
	int id;
	QueueNode next;

	QueueNode(int data){
		id = data;
		next = null;
	} 

}