package com.bravi.prova.utils;

import com.bravi.prova.utils.brackets.BracketsValidation;
import org.junit.Assert;
import org.junit.Test;

public class BracketsValidationTest {

    @Test
    public void isBalancedWithCommonBrackets() {
        BracketsValidation validation = new BracketsValidation();
        Assert.assertTrue(validation.isBalanced("(){}[]"));
    }

    @Test
    public void isBalancedNestedWithgroups() {
        BracketsValidation validation = new BracketsValidation();
        Assert.assertTrue(validation.isBalanced("[{()}](){}"));
    }

    @Test
    public void isBalancedWithOneUnclosedBracket() {
        BracketsValidation validation = new BracketsValidation();
        Assert.assertFalse(validation.isBalanced("[]{()"));
    }

    @Test
    public void isBalancedWithMultipleUnclosedBrackets() {
        BracketsValidation validation = new BracketsValidation();
        Assert.assertFalse(validation.isBalanced("[{)]"));
    }
}