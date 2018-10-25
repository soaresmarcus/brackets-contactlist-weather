package com.bravi.prova.utils;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

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