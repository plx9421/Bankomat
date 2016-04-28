package org.javarush.bigtask.bankomat.command;

import org.javarush.bigtask.bankomat.CashMachine;
import org.javarush.bigtask.bankomat.ConsoleHelper;
import org.javarush.bigtask.bankomat.exception.InterruptOperationException;

import java.util.ResourceBundle;

/**
 * Created by Alexey on 12.02.2016.
 */
public class LoginCommand implements Command
{

    private ResourceBundle validCreditCards =
            ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "verifiedCards");
    private ResourceBundle res =
            ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "login");

    @Override
    public void execute() throws InterruptOperationException
    {
        String cardNumberString = "";
        String cardPinString = "";

        ConsoleHelper.writeMessage(res.getString("before"));

        while (true)
        {
            ConsoleHelper.writeMessage(res.getString("specify.data"));
            while (true)
            {
                cardNumberString = ConsoleHelper.readString();
                if (cardNumberString != null)
                    if (!("".equals(cardNumberString)))
                        if (cardNumberString.length() == 12) break;
                ConsoleHelper.writeMessage(res.getString("try.again.with.details"));
            }

            while (true)
            {
                cardPinString = ConsoleHelper.readString();
                if (!(cardPinString == null))
                    if (!("".equals(cardPinString)))
                        if (cardPinString.length() == 4) break;
                ConsoleHelper.writeMessage(res.getString("try.again.with.details"));
            }
            if (!(cardPinString.equals(validCreditCards.getString(cardNumberString))))
            {
                ConsoleHelper.writeMessage(String.format(res.getString("not.verified.format"), cardNumberString));
            } else break;
            ConsoleHelper.writeMessage(res.getString("try.again.or.exit"));
        }
        ConsoleHelper.writeMessage(String.format(res.getString("success.format"), cardNumberString));
    }
}
