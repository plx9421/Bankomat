package org.javarush.bigtask.bankomat.command;

import org.javarush.bigtask.bankomat.CashMachine;
import org.javarush.bigtask.bankomat.ConsoleHelper;
import org.javarush.bigtask.bankomat.CurrencyManipulator;
import org.javarush.bigtask.bankomat.CurrencyManipulatorFactory;
import org.javarush.bigtask.bankomat.exception.InterruptOperationException;
import org.javarush.bigtask.bankomat.exception.NotEnoughMoneyException;

import java.util.*;

/**
 * Created by Alexey on 10.02.2016.
 */
class WithdrawCommand implements Command
{
    private ResourceBundle res =
            ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "withdraw");

    @Override
    public void execute() throws InterruptOperationException
    {
        ConsoleHelper.writeMessage(res.getString("before"));
        CurrencyManipulator c = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(ConsoleHelper.askCurrencyCode());
        int userRequestAmount = 0;
        Map<Integer, Integer> m = new HashMap<>();
        do
        {
            ConsoleHelper.writeMessage(res.getString("specify.amount"));
            do
            {
                try
                {
                    userRequestAmount = Integer.parseInt(ConsoleHelper.readString());
                    if (userRequestAmount <= 0)
                    {
                        ConsoleHelper.writeMessage(res.getString("specify.not.empty.amount"));
                        continue;
                    }
                }
                catch (NumberFormatException e)
                {
                    ConsoleHelper.writeMessage(res.getString("specify.not.empty.amount"));
                    continue;
                }

                if (c.isAmountAvailable(userRequestAmount)) break;
                else {
                    ConsoleHelper.writeMessage(res.getString("not.enough.money"));
                    ConsoleHelper.writeMessage(res.getString("specify.amount"));
                }
            }
            while (true);

            try
            {
                m = c.withdrawAmount(userRequestAmount);
            }
            catch (NotEnoughMoneyException e)
            {
                ConsoleHelper.writeMessage(res.getString("exact.amount.not.available"));
                continue;
            }
        }
        while (m.size() == 0);

//todo переделать по TreeMap.descendingorder
        Map<Integer, Integer> desOrderOutputTreeMap = new TreeMap<Integer, Integer>(new Comparator<Integer>()
        {
            @Override
            public int compare(Integer o1, Integer o2)
            {
                return o2 - o1;
            }
        });
        desOrderOutputTreeMap.putAll(m);


        int sumWithdraw = 0;
        for (Map.Entry<Integer, Integer> me : desOrderOutputTreeMap.entrySet())
        {
            sumWithdraw += me.getKey() * me.getValue();
        }
        ConsoleHelper.writeMessage(String.format(res.getString("success.format"), sumWithdraw, c.getCurrencyCode()));


    }
}
