import java.util.Stack;

/**
 * A naive reverse polish notation translator to infix.
 * NB: Places parentheses with whenever two "statements" are
 * combined.
 * Made by Olav Gjerde (ogj005)
 */
public class rpTranslator {
    /**
     * A method that will translate a reverse polish notation to infix
     * @param expression String input in form of reverse polish notation
     * @return infix expression
     */
    private static String translate(String expression){
        String[] rpExpression = expression.split(" ");
        Stack<String> work = new Stack<>();

        for (String value : rpExpression) {
            if (value.equals("*") || value.equals("/") ||
                    value.equals("+") || value.equals("-")) {
                String a = work.pop();
                String b = work.pop();
                //reversed parentheses because of the reverse operation during return
                work.push(")" + a + value + b + "(");
            } else {
                work.push(value);
            }
        }
        return new StringBuilder(work.pop()).reverse().toString();
    }
    
    public static void main(String args[]){
        String x = translate("1 3 + 2 4 2 * + *");
        System.out.println("Infix: " + x);
    }
}
