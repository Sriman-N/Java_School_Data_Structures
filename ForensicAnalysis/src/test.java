public class test {
    public static void main(String[] args) {
        factorial(5);

    }

    private static int factorial(int n) {
        if(n == 1) {
            return 1;
        }
      //  System.out.println(n);
        return n + factorial(n-1);
    }
    private static void counter() {

    }
}