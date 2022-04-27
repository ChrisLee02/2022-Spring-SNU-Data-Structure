import java.io.*;
import java.nio.BufferOverflowException;
import java.util.EmptyStackException;
import java.util.Stack;
import java.util.regex.*;

public class CalculatorTest {
    static String Result;

    static class longWrapper {
        long value;
    }

    public static void main(String args[]) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            try {
                String input = br.readLine();
                if (input.compareTo("q") == 0)
                    break;

                command(input);
            } catch (IOException e) { // 바꿔야됨.
                System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
            }
        }
    }

    static boolean isNumber(char a) {
        return a <= '9' && a >= '0';
    }

    static boolean isBracket(char a) {
        return a == '(' || a == ')';
    }


    static boolean APriorThanB(char a, char b) {
        if (a == '^') return true;
        else if (a == '~' && b != '^') return true;
        else if ((a == '*' || a == '/' || a == '%') && (b == '+' || b == '-')) return true;
        else return false;
    }

    static boolean handleBracketInput(char ithChar, Stack<Character> bracketStack, Stack<Character> operatorStack, Stack<Long> numberStack) {
        // 괄호 오류 처리해야됨.
        if (ithChar == '(') {
            bracketStack.push('(');
            operatorStack.push('S');
            numberStack.push(null);
            return true;
        } else {
            try {
                bracketStack.pop();
                return popAllOperatorStackForBracket(operatorStack, numberStack);
            } catch (EmptyStackException e) {
                //System.out.println("잘못된 수식: 괄호"); //주석처리 해야됨.
                return false;
            }
            // try-catch 로 수정


            /*if( !bracketStack.isEmpty()  ) {
                bracketStack.pop();
                return handleOperate(numberStack, operatorStack);
            }
            else {
                System.out.println("잘못된 수식: 괄호"); //주석처리 해야됨.
                return false;
            }*/
        }


    }

    static boolean handleOperate(Stack<Long> numberStack, Stack<Character> operatorStack) {
        // 연산에서 오류 발생하는 경우 처리해야됨. -> 연산이 성립하지 않는 경우랑, 숫자 스택이 빈 경우.
        if (operatorStack.isEmpty()) {
            return true;
        } else {
            char operator = operatorStack.pop();
            longWrapper result = new longWrapper();
            if (operator == '~') {
                try {
                    Long a = numberStack.pop();
                    if (a == null) {
                        return false;
                    }
                    unaryMinus(a, result);
                    //System.out.print(operator + " "); //주석처리 해야됨.
                    Result = Result + operator + " ";
                    numberStack.push(result.value);
                    return true;
                } catch (EmptyStackException e) { //numStack underflow case
                    //System.out.println("잘못된 수식: 숫자가 부족함."); //주석처리 해야됨.
                    return false;
                }

            } else {
                try {
                    Long b = numberStack.pop();
                    Long a = numberStack.pop();
                    if (a == null || b == null) {
                        return false;
                    }
                    boolean success;
                    switch (operator) {
                        case '+':
                            //System.out.print(operator + " "); //주석처리 해야됨.
                            Result = Result + operator + " ";
                            plus(a, b, result);
                            numberStack.push(result.value);
                            return true;

                        case '-':
                            //System.out.print(operator + " "); //주석처리 해야됨.
                            Result = Result + operator + " ";
                            minus(a, b, result);
                            numberStack.push(result.value);
                            return true;

                        case '*':
                            //System.out.print(operator + " "); //주석처리 해야됨.
                            Result = Result + operator + " ";
                            multiply(a, b, result);
                            numberStack.push(result.value);
                            return true;

                        case '/':
                            //System.out.print(operator + " "); //주석처리 해야됨.
                            Result = Result + operator + " ";
                            success = quotient(a, b, result);
                            if (success) {
                                numberStack.push(result.value);
                                return true;
                            } else {
                                //System.out.println("잘못된 수식: 정의되지 않는 연산."); //주석처리 해야됨.
                                return false;
                            }
                        case '%':
                            //System.out.print(operator + " "); //주석처리 해야됨.
                            Result = Result + operator + " ";
                            success = remainder(a, b, result);
                            if (success) {
                                numberStack.push(result.value);
                                return true;
                            } else {
                                //System.out.println("잘못된 수식: 정의되지 않는 연산."); //주석처리 해야됨.
                                return false;
                            }
                        case '^':
                            //System.out.print(operator + " "); //주석처리 해야됨.
                            Result = Result + operator + " ";
                            success = pow(a, b, result);
                            if (success) {
                                numberStack.push(result.value);
                                return true;
                            } else {
                                //System.out.println("잘못된 수식: 정의되지 않는 연산."); //주석처리 해야됨.
                                return false;
                            }
                        default:
                            //System.out.println("잘못된 인풋인데?"); //주석처리 해야됨.
                            return false;
                    }

                } catch (EmptyStackException e) { //numStack underflow case
                    //System.out.println("잘못된 수식: 숫자가 부족함."); //주석처리 해야됨.
                    return false;
                }

            }
        }
    }

    static boolean popAllOperatorStack(Stack<Character> operatorStack, Stack<Long> numberStack, char pivotOperator) {
        //pivot이 더 우선순위일 때 break
        boolean success;

        /*if(!operatorStack.isEmpty() && !(operatorStack.peek()=='S')) {
            previousOperator = operatorStack.peek();
            success = handleOperate(numberStack,operatorStack);
            if(!success) return false;
            while (!operatorStack.isEmpty() && !(operatorStack.peek()=='S') && !APriorThanB(previousOperator, operatorStack.peek()) ) {
                previousOperator = operatorStack.peek();
                success = handleOperate(numberStack,operatorStack);
                if(!success) return false;
            }
        }*/ //일단 임시저장
        while (!operatorStack.isEmpty() && !(operatorStack.peek() == 'S') && !(APriorThanB(pivotOperator, operatorStack.peek())) /*&& !(operatorStack.peek()=='P')*/) {

            success = handleOperate(numberStack, operatorStack);
            if (!success) {

                return false;
            }
        }

        /*if(operatorStack.peek()=='P') operatorStack.pop();*/
        return true;
    }

    static boolean popAllOperatorStackForBracket(Stack<Character> operatorStack, Stack<Long> numberStack) {
        boolean success;
        while (!operatorStack.isEmpty() && !(operatorStack.peek() == 'S')) {
            /*if(operatorStack.peek()=='P') {
                operatorStack.pop();
                continue;
            }*/
            success = handleOperate(numberStack, operatorStack);
            if (!success) return false;
        }
        operatorStack.pop();
        long tmp = numberStack.pop();
        numberStack.pop();
        numberStack.push(tmp);
        return true;
    }

    static boolean handleOperatorInput(boolean preIsOperator, char ithChar, Stack<Character> operatorStack, Stack<Long> numberStack) {
        //EMPTY이슈있슈

        // 연산자 연달아오는 경우 오류 처리해야됨. unary 는 괜찮음.
        // 우선순위 판정해서 operate 출력하는 구문 있어야 함.
        // top 보다 prior 이면 push, 아니면 pop all
        // prior 이면 방지턱 하나 만들기..
        if (preIsOperator) { // unary minus case
            if (ithChar == '-') {
                if (operatorStack.isEmpty() || operatorStack.peek() == 'S') {
                    operatorStack.push('~');
                } else if (APriorThanB('~', operatorStack.peek())) {
                    operatorStack.push('~');
                } else { //아니면 싹다 뺀 다음에 넣어야됨.

                    popAllOperatorStack(operatorStack, numberStack, '~');
                    operatorStack.push('~');
                }
                return true;

            } else {
                // System.out.println("잘못된 수식: 연산자 배치"); //주석처리 해야됨.
                return false;
            }
        } else if (numberStack.peek() == null && ithChar == '-') {
            operatorStack.push('~');
            return true;
        } else {

            if ( operatorStack.isEmpty() || operatorStack.peek() == 'S') { //처음이면 그냥 넣고 **여기서 empty이슈 생김.
                operatorStack.push(ithChar);
            } else if (APriorThanB(ithChar, operatorStack.peek())) { //우선순위 다르면 괄호 치기
                operatorStack.push(ithChar);
            } else { //아니면 싹다 뺀 다음에 넣어야됨.
                /*System.out.println(ithChar + " " + operatorStack.peek()); //주석처리
                System.out.println(APriorThanB(ithChar, operatorStack.peek())); //주석처리*/
                popAllOperatorStack(operatorStack, numberStack, ithChar);
                operatorStack.push(ithChar);
            }
            return true;
        }
    }


    static boolean plus(long a, long b, longWrapper result) {
        result.value = a + b;
        return true;
    }

    static boolean minus(long a, long b, longWrapper result) {
        result.value = a - b;
        return true;
    }

    static boolean multiply(long a, long b, longWrapper result) {
        result.value = a * b;
        return true;
    }

    static boolean quotient(long a, long b, longWrapper result) {
        if (b == 0) {
            return false;
        }
        result.value = (long) (a / b);
        return true;
    }

    static boolean remainder(long a, long b, longWrapper result) {
        if (b == 0) {
            return false;
        }
        result.value = a % b;
        return true;
    }

    static boolean pow(long a, long b, longWrapper result) {
        if (a == 0 && b < 0) {
            return false;
        }
        result.value = (long) Math.pow(a, b);
        return true;
    }

    static boolean unaryMinus(long a, longWrapper result) {
        result.value = -1 * a;
        return true;
    }


    private static void command(String input) {
        // TODO : 아래 문장을 삭제하고 구현해라.
        //System.out.println("<< command 함수에서 " + input + " 명령을 처리할 예정입니다 >>");
        Pattern Number = Pattern.compile("[0-9]+");
        Pattern AttachedNumber1 = Pattern.compile("[0-9]+[\\s]+[0-9]+");
        Pattern AttachedNumber2 = Pattern.compile("[0-9]+[\\t]+[0-9]+");

        Matcher attached1 = AttachedNumber1.matcher(input);
        Matcher attached2 = AttachedNumber2.matcher(input);
        if (attached1.find() || attached2.find()) {
            System.out.println("ERROR");
            return;
        }


        String processedInput = input.replace(" ", "").replace("\t", "");
        processedInput = "(" + processedInput + ")";
        //System.out.println(processedInput);
        Stack<Long> numberStack = new Stack<>();
        Stack<Character> operatorStack = new Stack<>();
        Stack<Character> bracketStack = new Stack<>();
        Matcher numberMatcher = Number.matcher(processedInput);
        boolean preIsOperator = false;
        Result = "";
        for (int i = 0; i < processedInput.length(); i++) {
            char ithChar = processedInput.charAt(i);
            if (isNumber(ithChar)) {
                numberMatcher.find();
                //System.out.print(numberMatcher.group() + " "); //주석처리 해야됨.
                Result = Result + numberMatcher.group() + " ";
                numberStack.push(Long.parseLong(numberMatcher.group()));
                i = numberMatcher.end() - 1;
                preIsOperator = false;
            } else {
                if (isBracket(ithChar)) { //괄호일 때 처리
                    boolean success = handleBracketInput(ithChar, bracketStack, operatorStack, numberStack);
                    if (!success) {
                        System.out.println("ERROR");
                        return;
                    }
                } else { //연산자일 때 처리
                    boolean success = handleOperatorInput(preIsOperator, ithChar, operatorStack, numberStack);
                    if (!success) {
                        System.out.println("ERROR");
                        return;
                    } else {
                        preIsOperator = true;
                    }
                }
            }
            /*System.out.println("num: " + numberStack);
            System.out.println("oper: " + operatorStack);
            System.out.println("---------------------");*/
        }

        if (numberStack.size() == 1) {
            System.out.println(Result.substring(0, Result.length() - 1));
            System.out.println(numberStack.pop());
        } else {
            System.out.println("ERROR");
        }
    }


    // 54, 55, 61, 63, 65, 67, 69 에서 EmptyStack이슈

    // 맨 앞에 unary -가 오는거.. 처리 해야됨
    // -> 숫자가 비어있을 때만 처리해주면 된다. 이건 문제없음. 수정해야됨..

    //무조건 괄호를 치는게 맞았고, 괄호를 연산자 스택에 넣는게 맞았다..

    // try-catch문으로 해줘야할듯?


}
