package airline_system;
import java.util.Scanner;

public class Airline {
	public static Scanner get = new Scanner(System.in);
	public static int seat[][] = new int[5][4];

	  public static void main(String[] args){
	    for(int i = 0; i < 5 ; i++){
	      for(int j = 0; j < 4 ; j++){
	        seat[i][j] = 0;
	      }
	    }
	    home();
	  }
	  public static void home(){
	    int choice = 0;
	      try{
	        System.out.println("+-----------------------------+");
	        System.out.println("| WELCOME TO 4D722E53 AIRLINE |");
	        System.out.println("+-----------------------------+");

	        System.out.println("(1) Reserve a seat");
	        System.out.println("(2) Exit");
	        System.out.print("Enter : ");
	        choice = get.nextInt();

	        if(choice != 1 && choice != 2){
	          System.out.println("\n\n! Error ! value out of bound ! Error !\n\n");
	          home();
	        }
	        if(choice == 2){
	          System.exit(0);
	        }else{
	          System.out.println("\n");
	          seat();
	        }
	      }catch(Exception e){
	        System.out.println("\n\n! Error ! value unrecognized ! Error !\n\n");
	      }
	  }
	  public static void seat(){
	    String alpha = "ABCDE";
	    char letter;
	    int num = 0,lnum = 0;
	    boolean error,error_1;
	    do{
	      error_1 = false;
	      System.out.println("         SEATS");
	      System.out.println("    -1- -2- -3- -4-");
	      for(int i = 0; i < 5 ; i++){
	        System.out.print(alpha.charAt(i)+" : ");
	        for(int j = 0; j < 4 ; j++){
	          if(seat[i][j] != 0){
	            System.out.print("[x] ");
	          }else{
	            System.out.print("[ ] ");
	          }
	        }
	        System.out.print("\n");
	      }
	      System.out.print("\nEnter the seat ");
	      // Letter
	      do{
	        error = false;
	        System.out.print("\nLetter: ");
	        letter = get.next().charAt(0);

	        if(letter == 'A' || letter == 'a'){
	          lnum = 0;
	        }else if(letter == 'B' || letter == 'b'){
	          lnum = 1;
	        }else if(letter == 'C' || letter == 'c'){
	          lnum = 2;
	        }else if(letter == 'D' || letter == 'd'){
	          lnum = 3;
	        }else if(letter == 'E' || letter == 'e'){
	          lnum = 4;
	        }else{
	          error = true;
	        }
	      }while(error);
	      // Number
	      do{
	        System.out.print("Number: ");
	        num = get.nextInt();
	        if(num > 4){
	          error_1 = true;
	        }
	      }while(error);
	      if(seat[lnum][num-1] != 0){
	        error_1 = true;
	        System.out.println("\n\nSomebody already reserved this seat.");
	        System.out.println("Please choose another seat.\n\n");
	      }
	    }while(error_1);
	    
	    
	    seat[lnum][num-1] = 1;

	    System.out.println("         SEATS");
	    System.out.println("    -1- -2- -3- -4-");
	    for(int i = 0; i < 5 ; i++){
	      System.out.print(alpha.charAt(i)+" : ");
	      for(int j = 0; j < 4 ; j++){
	        if(seat[i][j] != 0){
	          System.out.print("[x] ");
	        }else{
	          System.out.print("[ ] ");
	        }
	      }
	      System.out.print("\n");
	    }
	    System.out.println("\n\nSeat "+ letter + num +" is reserved for you.");

	    System.out.println("-- Transaction done --\n\n\n\n");

	    home();

	  }

}
