import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



//



public class BigInteger
{
    public static final String QUIT_COMMAND = "quit";
    public static final String MSG_INVALID_INPUT = "Invalid Input.";

    // implement this
    public static final Pattern EXPRESSION_PATTERN = Pattern.compile("");

    public static final Pattern EXPRESSION_NUMBER = Pattern.compile("[0-9]+");
    public static final Pattern EXPRESSION_OPERATOR = Pattern.compile("[+*-]");

    byte[] bigInteger = new byte[200];
    int sign = 1;
    int digit = 0;//index value

    public BigInteger(int j)
    {
        sign = j<0 ? -1 : 1;
        int i = j<0 ? j*-1 : j; // abs value
        while(i>9) {
            bigInteger[digit++] = (byte)(i%10);
            i = i/10;
        }
        bigInteger[digit] = (byte)(i);

    }
  
    public BigInteger(byte[] num1)
    {
        for(int i = 0; i < num1.length; i++ ){
            bigInteger[i] = num1[i];
        }
        digit = num1.length-1;
    }
  
    public BigInteger(String s)
    {
        for(int i = 0; i<s.length(); i++) {
            bigInteger[s.length()-i-1] = (byte)Character.getNumericValue(s.charAt(i));
        }
        digit = s.length()-1;
        // System.out.println(this.toString());
    }


    public BigInteger add(BigInteger big)
    {
        if(this.sign == -1 && big.sign == 1) {
            return big.subtract(this);
        }
        if(this.sign == 1 && big.sign == -1) {
            return this.subtract(big);
        }

        BigInteger integer = new BigInteger(1);
        byte carry = 0;
        int digit = Math.max(this.digit, big.digit);
        byte[] result = new byte[digit+1];
        for(int i = 0; i<=digit; i++) {
            result[i] = (byte) (this.bigInteger[i] + big.bigInteger[i] + carry);
            carry = (byte) (result[i]/10);
            result[i] = (byte) (result[i] % 10);
        }
        if(carry == 0) {
            integer = new BigInteger(result);
            if(this.sign==-1) integer.multiplyMinusOne();
            return integer;
        }
        else {
            byte[] newResult = new byte[digit+2];
            for(int i = 0; i<=digit; i++) {
                newResult[i] = result[i];

            }
            newResult[digit+1] = carry;
            integer = new BigInteger(newResult);
            if(this.sign==-1) integer.multiplyMinusOne();
            return integer;
        }
    }
  
    public BigInteger subtract(BigInteger big) // only in bigger positive - smaller positive,
    {
        if(big.isBiggerThan(this)) { // if not, recursive call
            BigInteger integer = big.subtract(this);
            integer.multiplyMinusOne();
            return integer;
        }
        byte carry = 0;
        int i;
        int digit = Math.max(this.digit, big.digit);
        byte[] result = new byte[digit+1];
        for(i = 0; i<=digit; i++) {
            result[i] = (byte) (this.bigInteger[i] - big.bigInteger[i] + carry + 10 );
            carry = (byte) (result[i]/10 - 1);
            result[i] = (byte) (result[i] % 10);
        }
        for(i = digit; i>=1; i--) {
            if(result[i] == 0) continue;
            else{
                break;
            }
        }
        digit = i;
        byte[] newResult = new byte[digit+1];
        for(i = digit; i>=0; i--) {
            newResult[i] = result[i];
        }
        return new BigInteger(newResult);
    }
    public BigInteger multiplyZeroToNine(int n) {
        byte carry = 0;
        byte[] result = new byte[digit+1];
        for(int i = 0; i<=digit; i++) {
            result[i] = (byte) (this.bigInteger[i] * n + carry);
            carry = (byte) (result[i]/10);
            result[i] = (byte) (result[i] % 10);
        }
        if(carry == 0) {
            return new BigInteger(result);
        }
        else {
            byte[] newResult = new byte[digit+2];
            for(int i = 0; i<=digit; i++) {
                newResult[i] = result[i];
            }
            newResult[digit+1] = carry;

            return new BigInteger(newResult);
        }
    }
    public BigInteger multiplyTenExponential(int n) {
        byte[] newResult = new byte[this.digit+1+n];
        for(int i = this.digit + n; i>=n; i--) {
            newResult[i] = this.bigInteger[i-n];
        }
        for(int i =n-1; i>=0; i--) {
            newResult[i] = 0;
        }
        return new BigInteger(newResult);
    }

    public void multiplyMinusOne() {
        this.sign = this.sign * -1;
        //System.out.println(this.toString());
    }

    public BigInteger multiply(BigInteger big)
    {
       int digitB = big.digit;
       BigInteger tmp = new BigInteger(0);
       for(int i = 0; i<=digitB; i++) {
           tmp = tmp.add(this.multiplyZeroToNine(big.bigInteger[i]).multiplyTenExponential(i));
       }
       tmp.sign = this.sign * big.sign;
       while(tmp.bigInteger[tmp.digit]==0 && tmp.digit>0) {
           tmp.digit--;
       }
       return tmp;
    }

    public boolean isBiggerThan(BigInteger big) {
        if(this.digit == big.digit) {
            for(int i = digit; i>=0; i--) {
                if(this.bigInteger[i] == big.bigInteger[i]) continue;
                return this.bigInteger[i] > big.bigInteger[i];
            }
            return false;
        }
        else return this.digit > big.digit;
    }

    @Override
    public String toString()
    {
        String str = "";
        if(this.sign == -1) str += '-';
        for(int i = digit; i>=0; i--) {
            str = str + bigInteger[i];
        }
        return str;
    }
  
    static BigInteger evaluate(String input) throws IllegalArgumentException
    {
        int num1pos, num2pos;
        int operatorCount = 0;
        String[] operators = new String[3];
        int[] operatorPos = new int[3];
        BigInteger num1, num2;
        BigInteger result = new BigInteger(1);
        Matcher m = EXPRESSION_NUMBER.matcher(input);
        m.find();
        num1 = new BigInteger(m.group());
        num1pos = m.start();
        m.find();
        num2 = new BigInteger(m.group());
        num2pos = m.start();
        m = EXPRESSION_OPERATOR.matcher(input);
        while(m.find()) {
            operators[operatorCount] = m.group();
            operatorPos[operatorCount] = m.start();
            operatorCount++;
        }
        //System.out.println(operatorCount);
        if(operatorCount == 1) {

            switch (operators[0]) {
                case "+":
                    result = num1.add(num2);
                    break;
                case "-":
                    result = num1.subtract(num2);
                    break;
                case "*":
                    result = num1.multiply(num2);
                    break;
            }
        }
        else if(operatorCount == 2) {

            if(operatorPos[0] < num1pos) {
              //  System.out.println(operators[0]);
                if(operators[0].equals("-")) num1.multiplyMinusOne();

                switch (operators[1]) {
                    case "+":
                        result = num1.add(num2);
                        break;
                    case "-":
                        num2.multiplyMinusOne();
                        result = num1.add(num2);
                        break;
                    case "*":
                        result = num1.multiply(num2);
                        break;
                }
            }
            else {
                if(operators[1].equals("-")) num2.multiplyMinusOne();
                switch (operators[0]) {
                    case "+":
                        result = num1.add(num2);
                        break;
                    case "-":
                        num2.multiplyMinusOne();
                        result = num1.add(num2);
                        break;
                    case "*":
                        result = num1.multiply(num2);
                        break;
                }
            }
        }
        else {
            if(operators[0].equals("-")) num1.multiplyMinusOne();
            if(operators[2].equals("-")) num2.multiplyMinusOne();
            switch (operators[1]) {
                case "+":
                    result = num1.add(num2);
                    break;
                case "-":
                    num2.multiplyMinusOne();
                    result = num1.add(num2);
                    break;
                case "*":
                    result = num1.multiply(num2);
                    break;
            }
        }
        return result;

        // implement here
        // parse input
        // using regex is allowed

        // One possible implementation
        // BigInteger num1 = new BigInteger(arg1);
        // BigInteger num2 = new BigInteger(arg2);
        // BigInteger result = num1.add(num2);
        // return result;
    }
  
    public static void main(String[] args) throws Exception
    {

        try (InputStreamReader isr = new InputStreamReader(System.in))
        {
            try (BufferedReader reader = new BufferedReader(isr))
            {
                boolean done = false;
                while (!done)
                {
                    String input = reader.readLine();
  
                    try
                    {
                        done = processInput(input);
                    }
                    catch (IllegalArgumentException e)
                    {
                        System.err.println(MSG_INVALID_INPUT);
                    }
                }
            }
        }
    }
  
    static boolean processInput(String input) throws IllegalArgumentException
    {
        boolean quit = isQuitCmd(input);
  
        if (quit)
        {
            return true;
        }
        else
        {
            BigInteger result = evaluate(input);
            System.out.println(result.toString());
  
            return false;
        }
    }
  
    static boolean isQuitCmd(String input)
    {
        return input.equalsIgnoreCase(QUIT_COMMAND);
    }
}
