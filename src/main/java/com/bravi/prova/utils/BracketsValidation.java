package com.bravi.prova.utils;

import java.util.ArrayList;
import java.util.List;

public class BracketsValidation {

    List<String> elements = new ArrayList<String>();

    public boolean isBalanced(String data) {
        if (data == null || (data.length()) % 2 != 0)
            return false;

        setSiblings(data);
        return (elements.size() == 0);
    }

    private void setSiblings(String data) {
        for (char ch : data.toCharArray()) {
            String bracket = String.valueOf(ch);

            if (bracket.equals("{") || bracket.equals("[") || bracket.equals("("))
                elements.add(bracket);

            if (bracket.equals("}"))
                elements.remove("{");
            else if (bracket.equals("]"))
                elements.remove("[");
            else if (bracket.equals("("))
                elements.remove(bracket);
        }
    }
}
