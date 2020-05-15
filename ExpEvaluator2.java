/*

    There are two versions. ExpEvaluator.java is reads from a specific file path which the program prompts the user to enter.
    ExpEvaluator2 uses FileInputStream(args[0])). I was having difficulty running it on my machine so I included both
    programs in case one doesn't compile.

*/
import java.io.*;
import java.util.*;


public class ExpEvaluator2 {


    private String s;
    private int currIndex;
    private char inputToken;
    private char c;
    private int n;
    private HashMap<String, Integer> hMap = new HashMap<>(); //Used to store data with variables


    //Tested

    void expEvaluator2(String s)
    {
        this.s = s;
        currIndex = 0;
        n = s.length();
        nextToken();

    }

    //Tested
    //Taken from class example

    void nextToken(){
        char c;
        do {
            if (currIndex == n){
                inputToken = ';';
                return;
            }
            c = s.charAt(currIndex++);
        } while (Character.isWhitespace(c));
        inputToken = c;
    }

    public void fileEval(Scanner ee) {


        do
        {
            expEvaluator2(ee.nextLine());  //Evaluates next line of file
            split(); //Takes current line through evaluation

        } while (ee.hasNextLine());

    }

    //Tested
    //Taken from class example

    void match(char token) {
        if (inputToken == token) {
            nextToken();
        } else {
            throw new RuntimeException("syntax error");
        }
    }

    //Tested
    //Taken from class example but modified

    int eval() {
        int x = exp();
        if (inputToken == ';') {  //assume statement is complete and return x
            return x;
        } else {
            throw new RuntimeException("syntax error");
        }
    }



    //Tested
    //Taken from class example

    int exp() {
        int x = term();

        while (inputToken == '+' || inputToken == '-') {
            char op = inputToken;
            nextToken();
            int y = term();
            x = apply(op, x, y);
        }
        return x;
    }

    //Tested
    //Taken from class example

    int term() {
        int x = factor();
        while (inputToken == '*' || inputToken == '/') {
            char op = inputToken;
            nextToken();
            int y = factor();
            x = apply(op, x, y);
        }
        return x;
    }


    //Tested
    int factor() {

        int x;
        String keyString = Character.toString(inputToken);

        if (hMap.containsKey(keyString)) { //Use hashmap hMap to check if it contains our key "keyString"
            x = hMap.get(keyString).intValue(); //If it does contain it  we get the value using intValue

            nextToken(); //Move onto the next token

            return x;

        }else{
            switch (inputToken) {

                case '+':
                    nextToken();
                    x = factor();
                    return x;

                case '-':

                    nextToken();
                    x = factor();
                    return 0 - x; //negative x

                case '(':
                    nextToken();
                    x = exp(); //Not a factor because (
                    match(')'); //Match with closing parenthesis
                    return x;


                default:

            } //end switch
        } //end else


        /*
        switch (inputToken){
            case '(':
                nextToken();
                x = exp();
                match(')');
                return x;
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                x = inputToken - '0';
                nextToken();
                return x;
            default:
                throw new RuntimeException("syntax error");
        */

        boolean digitCheck = true;

        do{

            keyString = ""; //empty the string so the number isn't appended again
            keyString =  keyString + inputToken;
            nextToken();
        }
        while (digitCheck = Character.isDigit(inputToken));

        x = Integer.parseInt(keyString);

        return x;

    }



    //Tested
    //Taken From Class Example

    static int apply(char op, int x, int y){
        int z = 0;
        switch (op){
            case '+': z = x + y; break;
            case '-': z = x - y; break;
            case '*': z = x * y; break;
            case '/': z = x / y; break;
        }
        return z;
    }

    //done
    String checkVar() {
        StringBuilder nStr = new StringBuilder();
        String nStrCon ="";

        int asciiComp = (char)inputToken; //Going to use this to compare inputToken int value to ascii letter values


        if (asciiComp >=65 && asciiComp <=122)  //compares ascii values between A-Z & a-z

            nStr.append(inputToken); //Append inputToken if we know it's a letter

        else
            System.out.println("Error"); //Error
        nextToken();


        if (inputToken != '=')
            System.out.println("Error");

        nextToken();
        nStrCon = nStr.toString(); //convert nStr from a StringBuilder object to a string.

        return nStrCon;
    }

    //Tested

    void split() {

        String var = checkVar(); //Determines if charAtString is a letter or number. Sets variable to var
        int numVal = eval(); //Determines if charAtString is a letter or number. Sets data to numVal
        hMap.put(var, numVal); // places var and numVal in hashmap
        display(var, numVal); //displays variables and values
    }

    //Tested
    //Prints out variable and data after string is evaluated

    void display(String var, int numVal) {
        String varFinal = var;
        int numValFinal = numVal;
        System.out.println(varFinal + "=" + numValFinal); //
    }


    public static void main(String[] args) throws IOException {


        Scanner pathScanner = new Scanner(new FileInputStream(args[0]));
        ExpEvaluator2 ee = new ExpEvaluator2();
        ee.fileEval(pathScanner);




    } //end main
}