import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class SyntaxCheck {
	
	static ArrayList<Integer> tokenList;
	static int curr = 0;
	private static int token;
	final static int intg = 10;								//this is so i can compare the given id's to the value
	final static int floatp = 11;
	final static int ident = 12;
	final static int leftBrace = 13;
	final static int rightBrace = 14;
	final static int leftPar = 15;
	final static int rightPar = 16;
	final static int equOp = 17;
	final static int mulOp = 18;
	final static int divOp = 19;
	final static int greOp = 20;
	final static int lesOp = 21;
	final static int subOp = 22;
	final static int addOp = 23;
	final static int semic = 24;
	final static int modOp = 25;
	final static int notEq = 26;

	public static void main(String[] args) throws FileNotFoundException, IOException {
		tokenList = new ArrayList<>();		//store token id's in array list
		
		//reading the given token id's into the array list
		File input = new File("C:\\Users\\Alex\\eclipse-workspace\\PLCHW2\\ForSyntax.txt"); 	//using the path of the output from token analyzer  
		Scanner scan = new Scanner(input);
		while(scan.hasNextInt()) {
			tokenList.add(scan.nextInt());
		}
		System.out.println("The tokens recieved are:");
		tokenList.forEach(System.out::println);								// this is to check if the tokens show up correctly

		scan.close();
		
		
		System.out.println("--------------------------");					//This is just to break up the console window for easy testing
		
		getToken();
		System.out.println(token + " is the starting token.");
		System.out.println("Start of Syntax Anaylsis:");
		System.out.println("--------------------------");	
		
		start();
		
		System.out.println("The program contains no errors!");									//only prints if error is not thrown. 
		
	}
	
	private static void getToken() {
		token = tokenList.get(curr);
	}
	private static void nextToken() {
		curr++;
		token = tokenList.get(curr);
		System.out.println("The nextToken() method has been called " + curr + " times, the current token is " + token);
	}
	
	static void error() {													//Called to stop the program when there is a flaw in syntax
		System.out.print("This program contains errors.");
		System.exit(0);
	}
	
	static void start() {
		System.out.println("Called start");
		if (token != leftBrace)
			error();
		if (tokenList.get(tokenList.size() - 1) != rightBrace) {
			error();
			}
		if (tokenList.get(tokenList.size() - 2) != semic) {
			error();
		}
		else {
			nextToken();
			expr();
		}		
	}
	
	static void expr() {
		System.out.println("Called expr");
		System.out.println(token);
		term();
		if(token == addOp || token == subOp || 
				token == greOp || token == lesOp ||
				token == equOp || token == notEq) {
			termT();
		}
		else 
			return;
	}
	
	static void term() {
		System.out.println("Called term");
		System.out.println(token);
		factor();
		if(token == mulOp || token == divOp || token == modOp) {
			factorT();
		}
	}
	static void termT() {
		System.out.println("Called termT");
		System.out.println(token);
		if(token == addOp || token == subOp || 
				token == greOp || token == lesOp ||
				token == equOp || token == notEq) {
			addSub();
			term();
			if(token == addOp || token == subOp || 
					token == greOp || token == lesOp ||
					token == equOp || token == notEq) {
				termT();
			}
		}
		else {
			System.out.println("Error @ termT");
			System.out.println(token);
			error();
		}
	}
	
	static void factor() {
		System.out.println("Called factor");
		System.out.println(token);
		if(token == leftPar) {
			nextToken();
			expr();
			if(token == rightPar) {
				nextToken();
				return;
			}
			else {
				System.out.println("Error, unclosed parentheses.");
				System.out.println(token);
				error();
			}
		}
		else if(token == subOp) {
			nextToken();
			factor();
		}
		else if(token == intg) {
			nextToken();
		}
		else if(token == floatp) {
			nextToken();
		}
		else if(token == ident) {
			nextToken();
		}
		if(token == semic) {
			 nextToken();
			 if (token == rightBrace) {
				 return;
			 }
			 term();
		 }
		 
		else if (token == intg || token == ident || token == floatp) {
			System.out.println("Error @ factor");
			System.out.println(token);
			error();
		}
		
	}
	
	static void factorT() {
		System.out.println("Called factorT");
		System.out.println(token);
		
		
		if(token == mulOp || token == divOp || token == modOp) {
			mulDivMod();
			factor();
			if(token == mulOp || token == divOp || token == modOp) {
			factorT();
			}
		}
		else
			error();	
	}
	
	static void addSub() {
		System.out.println("Called addSub");
		System.out.println(token);
		if(token == addOp || token == subOp || 
				token == greOp || token == lesOp ||
				token == equOp || token == notEq) {
						nextToken();
		}
		else {
			System.out.println("Error @ addSub");
			error();
		}
	}
	
	static void mulDivMod() {
		System.out.println("Called mulDivMod");
		System.out.println(token);
		if(token == mulOp || token == divOp || token == modOp) {
			nextToken();
		}
		else {
			System.out.println("Error @ mulDivMod");
			error();
		}
		
	}
	
}