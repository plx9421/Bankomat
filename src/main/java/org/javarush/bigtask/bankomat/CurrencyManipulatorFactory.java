package org.javarush.bigtask.bankomat;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alexey on 10.02.2016.
 */
public final class CurrencyManipulatorFactory
{
    private CurrencyManipulatorFactory()
    {
    }

    private static Map<String, CurrencyManipulator> manipulatorHashMap = new HashMap<>();

    public static CurrencyManipulator getManipulatorByCurrencyCode(String currencyCode)
    {

        if (currencyCode == null) return null;
        if ("".equals(currencyCode)) return null;

        for (Map.Entry<String, CurrencyManipulator> pair : manipulatorHashMap.entrySet())
        {
            if (currencyCode.equals(pair.getKey())) return pair.getValue();
        }

        CurrencyManipulator c = new CurrencyManipulator(currencyCode);
        manipulatorHashMap.put(currencyCode, c);
        return c;
    }

    public static Collection getAllCurrencyManipulators(){
        return manipulatorHashMap.values();
    }

}
