package com.bravi.prova.utils.brackets;

import java.util.ArrayList;
import java.util.List;

public class BracketsValidation {

    public boolean isBalanced(String data) {
        if (data == null || (data.length()) % 2 != 0)
            return false;

        return setSiblings(data);
    }

    private boolean setSiblings(String data) {
        List<String> elements = new ArrayList<String>();

        for (char ch : data.toCharArray()) {
            String bracket = String.valueOf(ch);

            if (bracket.equals("{") || bracket.equals("[") || bracket.equals("(")) {
                elements.add(bracket);
            }else if (bracket.equals("}")) {
                if (elements.isEmpty() || !elements.remove("{")) {
                    return false;
                }
            } else if (bracket.equals("]")) {
                if (elements.isEmpty() || !elements.remove("[")) {
                    return false;
                }
            } else if (bracket.equals(")")) {
                if (elements.isEmpty() || !elements.remove("(")) {
                    return false;
                }
            }
        }

        return (elements.size() == 0);
    }

}
