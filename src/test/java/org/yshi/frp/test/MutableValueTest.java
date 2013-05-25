package org.yshi.frp.test;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.yshi.frp.MutableInteger;
import org.yshi.frp.MutableValue;
import org.yshi.frp.func.UnaryFunction;

/**
 * Author: Xiaonuo Gantan <xiaonuo.gantan@gmail.com>
 * Date: 2013-05-25
 */
public class MutableValueTest {
    private MutableValue<String> ms0;
    private MutableValue<String> ms1;
    private MutableValue<String> ms2;

    @Before
    public void setUp() throws Exception {
        ms0 = new MutableValue<String>("a");
        ms1 = ms0.transform(new UnaryFunction<String, String>() {
            @Override
            public String evaluate(String input) {
                return input.concat("b");
            }
        });
        ms2 = ms1.transform(new UnaryFunction<String, String>() {
            @Override
            public String evaluate(String input) {
                return input.concat("c");
            }
        });
    }

    @Test
    public void testInitialValues() {
        assertEquals("ms1 should be ab", ms1.get(), "ab");
        assertEquals("ms2 should be abc", ms2.get(), "abc");
    }

    @Test
    public void testValueChanges() {
        ms0.set("d");
        assertEquals("ms1 should be db", ms1.get(), "db");
        assertEquals("ms2 should be dbc", ms2.get(), "dbc");
    }
}
