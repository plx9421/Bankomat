package org.javarush.bigtask.bankomat.command;

import org.javarush.bigtask.bankomat.CashMachine;
import org.javarush.bigtask.bankomat.ConsoleHelper;
import org.javarush.bigtask.bankomat.CurrencyManipulator;
import org.javarush.bigtask.bankomat.CurrencyManipulatorFactory;
import org.javarush.bigtask.bankomat.exception.InterruptOperationException;

import java.util.ResourceBundle;

/**
 * Created by Alexey on 10.02.2016.
 */
class DepositCommand implements Command
{
    private ResourceBundle res =
            ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "deposit");

    @Override
    public void execute() throws InterruptOperationException
    {
        ConsoleHelper.writeMessage(res.getString("before"));

        String cur = ConsoleHelper.askCurrencyCode();
        CurrencyManipulator currencyManipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(cur);
        String[] st;
        do
        {
            st = ConsoleHelper.getValidTwoDigits(cur);

            try
            {
                if (Integer.parseInt(st[0]) > 0)
                if (Integer.parseInt(st[1]) > 0) break;
            }
            catch (NumberFormatException e)
            {
            }
            ConsoleHelper.writeMessage(res.getString("invalid.data"));

        }while (true);

        currencyManipulator.addAmount(Integer.parseInt(st[0]), Integer.parseInt(st[1]));

        ConsoleHelper.writeMessage(String.format(res.getString("success.format"), Integer.parseInt(st[0]), st[1]));

    }
}
