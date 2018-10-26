package com.bravi.prova.utils;

import com.bravi.prova.utils.brackets.BracketsValidation;
import org.junit.Assert;
import org.junit.Test;

public class BracketsValidationTest {

    @Test
    public void isBalanced() {
        BracketsValidation validation = new BracketsValidation();
        Assert.assertTrue(validation.isBalanced ("(){}[]"));
        Assert.assertTrue(validation.isBalanced("[{()}](){}"));
        Assert.assertFalse(validation.isBalanced("[]{()"));
        Assert.assertFalse(validation.isBalanced("[{)]"));
    }
}