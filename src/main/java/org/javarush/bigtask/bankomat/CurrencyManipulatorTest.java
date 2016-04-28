package org.javarush.bigtask.bankomat;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.*;

/**
 * Created by Alexey on 12.02.2016.
 */
public class CurrencyManipulatorTest
{
    Map<Integer, Integer> m = new TreeMap<Integer, Integer>();
    CurrencyManipulator c = new CurrencyManipulator("GRN");

    @org.junit.Before
    public void setUp() throws Exception
    {

        c.addAmount(500, 2);
        c.addAmount(100, 1);
        c.addAmount(200, 3);
        c.addAmount(50, 12);

        m.put(500, 1);
        m.put(100, 1);


    }

    @org.junit.Test
    public void testWithdrawAmount() throws Exception
    {
        String actual = "";
        for (Map.Entry m1 : c.withdrawAmount(600).entrySet()) actual += m1.getKey() + " - " + m1.getValue() + "; ";

        String expected = "";
        for (Map.Entry m2 : m.entrySet()) expected += m2.getKey() + " - " + m2.getValue() + "; ";

        assertEquals(expected, actual);

    }
}