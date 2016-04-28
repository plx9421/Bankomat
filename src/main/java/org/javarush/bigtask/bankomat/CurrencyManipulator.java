package org.javarush.bigtask.bankomat;

import org.javarush.bigtask.bankomat.exception.NotEnoughMoneyException;

import java.util.*;

/**
 * Created by Alexey on 10.02.2016.
 */
public class CurrencyManipulator
{
    private String currencyCode;
    private Map<Integer, Integer> denominations = new HashMap<>(); //это Map<номинал, количество>

    public CurrencyManipulator(String currencyCode)
    {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode()
    {
        return currencyCode;
    }

    public void addAmount(int denomination, int count)
    {
        //todo переделать !!!
        boolean isAmountEnable = false;
        for (Map.Entry<Integer, Integer> pair : denominations.entrySet())
        {
            if (pair.getKey() == denomination)
            {
                pair.setValue(pair.getValue() + count);
                isAmountEnable = true;
                break;
            }
        }

        if (!isAmountEnable) denominations.put(denomination, count);
    }

    public int getTotalAmount()
    {
        int result = 0;
        for (Map.Entry<Integer, Integer> pair : denominations.entrySet())
        {
            result = result + (pair.getKey() * pair.getValue());
        }
        return result;
    }

    public boolean hasMoney()
    {
        return getTotalAmount() != 0;
    }

    public boolean isAmountAvailable(int expectedAmount)
    {

        return expectedAmount <= getTotalAmount();
    }

    public Map<Integer, Integer> withdrawAmount(int expectedAmount) throws NotEnoughMoneyException
    {

        Map<Integer, Integer> desOrderTreeMap = new TreeMap<Integer, Integer>(new Comparator<Integer>()
        {
            @Override
            public int compare(Integer o1, Integer o2)
            {
                return o2 - o1;
            }
        });
        desOrderTreeMap.putAll(denominations);

        Map<Integer, Integer> treeSol = new TreeMap<Integer, Integer>();//это Map<номинал, количество>

        for (Map.Entry<Integer, Integer> t1 : desOrderTreeMap.entrySet()) treeSol.put(t1.getKey(), 0);

        for (Map.Entry<Integer, Integer> t2 : desOrderTreeMap.entrySet())
        {
            int nominal = t2.getKey();

            int solSumInit = 0;
            for (Map.Entry<Integer, Integer> t0 : treeSol.entrySet()) solSumInit += t0.getKey() * t0.getValue();

            int countAmount = t2.getValue();
            if (((expectedAmount - solSumInit) / nominal) < countAmount)
                countAmount = (expectedAmount - solSumInit) / nominal;

            treeSol.put(nominal, countAmount);
            int solSum = 0;
            for (Map.Entry<Integer, Integer> t3 : treeSol.entrySet()) solSum += t3.getKey() * t3.getValue();

            if (solSum == expectedAmount)
            {
                for (Map.Entry<Integer, Integer> r : new HashMap<>(treeSol).entrySet())
                {
                    if (r.getValue() == 0) treeSol.remove(r.getKey());
                    else denominations.put(r.getKey(), denominations.get(r.getKey()) - r.getValue());
                }
                if (treeSol.size() == 0) throw new NotEnoughMoneyException();
                return treeSol;
            }

            if (solSum == 0) continue;
            if (solSum < expectedAmount)
            {

                for (Map.Entry<Integer, Integer> t4 : desOrderTreeMap.entrySet())
                {
                    if (t4.getKey() >= t2.getKey()) continue;
                    int nominal4 = t4.getKey();

                    Map<Integer, Integer> treeSolTemp = new TreeMap<>(treeSol);//это Map<номинал, количество>

                    int countAmount4 = t4.getValue();
                    if (((expectedAmount - solSum) / nominal4) < countAmount4)
                        countAmount4 = (expectedAmount - solSum) / nominal4;

                    treeSolTemp.put(nominal4, countAmount4);

                    int solSum4 = 0;
                    for (Map.Entry<Integer, Integer> t5 : treeSolTemp.entrySet())
                        solSum4 += t5.getKey() * t5.getValue();
                    if (solSum4 == expectedAmount)
                    {
                        for (Map.Entry<Integer, Integer> r : new HashMap<>(treeSolTemp).entrySet())
                            if (r.getValue() == 0) treeSolTemp.remove(r.getKey());
                            else denominations.put(r.getKey(), denominations.get(r.getKey()) - r.getValue());

                        if (treeSolTemp.size() == 0) throw new NotEnoughMoneyException();
                        return treeSolTemp;
                    }
                    if (solSum4 < expectedAmount) continue;
                    if (solSum4 > expectedAmount) break;
                }

                treeSol.put(nominal, treeSol.get(nominal) - 1);
            }
        }

        throw new NotEnoughMoneyException();
//        for (Map.Entry<Integer, Integer> r : new HashMap<>(treeSol).entrySet())
//            if (r.getValue() == 0) treeSol.remove(r.getKey());
//        return treeSol;

    }
}
