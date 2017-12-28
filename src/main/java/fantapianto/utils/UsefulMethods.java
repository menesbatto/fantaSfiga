package fantapianto.utils;


public class UsefulMethods {
    public static int factorial(int n) {
        int fact = 1; // this  will be the result
        for (int i = 1; i <= n; i++) {
            fact *= i;
        }
        return fact;
    }
    
    public static Double getNumber(String s){
    	return !s.equals("-") ? Double.valueOf(s.replace(",", ".")) : 0;
    }
    
    
}