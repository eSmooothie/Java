package Bouncy;
import java.util.*;
public class Program101 {

	public static void main(String[] args) {
		Scanner get = new Scanner(System.in);
		int num;
		boolean bouncy;
		String state;
		
		System.out.print("Enter a number: ");
		num = get.nextInt();
		bouncy = BouncyDigit(num);
		state = (bouncy == true) ? (num + " is a bouncy number.") : (num +" is not a bouncy number");
		System.out.println(state);
	}
	
	public static boolean BouncyDigit(int digit) {
		int digit_1,digit_2,digit_3;
		boolean p,q,r;
		
		digit_1 = digit % 10;
		digit = digit - digit_1;
		digit_2 = (digit % 100) / 10;
		digit = digit - digit_2;
		digit_3 = (digit % 1000) / 100;
			
		p = ((digit_1 >= digit_2 && digit_2 > digit_3) || (digit_1 > digit_2 && digit_2 >= digit_3)) ? (true) : (false);// Increasing 
		q = ((digit_1 < digit_2 && digit_2 <= digit_3) || (digit_1 <= digit_2 && digit_2 < digit_3)) ? (true) : (false);// Decreasing
		r = (digit_1 == digit_2 && digit_2 == digit_3) ? (true) : (false);// Plain
		
		return (!p && !q && !r);
	}
}
