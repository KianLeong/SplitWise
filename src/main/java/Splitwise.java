import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class Splitwise {

  public static void main(String[] args) {
    List<Payee> payees = new ArrayList();
    Scanner userInput = new Scanner(System.in);
    while (true) {
      System.out.println(
          "Input a new payee with the following format.Payer:Amount (To start calculating, do not input)");

      String input = userInput.nextLine();

      if (!input.equals("")) {
        // Handle input
        String[] inputArr = input.split(":");
        if (inputArr.length == 2) {
          payees.add(new Payee(inputArr[0], Double.parseDouble(inputArr[1])));
        } else {
          System.out.println("Invalid Input. Ending program");
          System.exit(-1);
        }
      } else {
        break;
      }
    }

    List<Payment> payments = splitPayment(payees);
    printSplitPayment(payments);
  }

  public static void printSplitPayment(List<Payment> payments) {
    for (Payment payment : payments) {
      System.out
          .println(payment.getPayer() + " has to pay " + payment.getPayee() + " " + NumberFormat
              .getCurrencyInstance(new Locale("en", "US"))
              .format(payment.getAmount()));
    }
  }

  public static List<Payment> splitPayment(List<Payee> payees) {
    List<Payment> payments = new ArrayList();
    double total = calculateTotal(payees);

    Map<String, Double> amountOwedSummary = summarise(payees, total);
    List<String> debtors = getDebtors(amountOwedSummary);
    List<String> creditors = getCreditors(amountOwedSummary);

    for (String debtor : debtors) {
      for (String creditor : creditors) {

        double creditorCurrentAmount = amountOwedSummary.get(creditor);
        //Only compare with creditor who still has amount owing, else skip creditor
        if (creditorCurrentAmount > 0) {
          double debtorCurrentAmount = amountOwedSummary.get(debtor);
          if (debtorCurrentAmount < creditorCurrentAmount) {
            double creditorNewAmount = creditorCurrentAmount - debtorCurrentAmount;
            payments.add(new Payment(debtor, creditor, debtorCurrentAmount));

            amountOwedSummary.put(creditor, creditorNewAmount);
            amountOwedSummary.put(debtor, 0.0);

            break;
          } else if (debtorCurrentAmount >= creditorCurrentAmount) {
            double debtorNewAmount = debtorCurrentAmount - creditorCurrentAmount;
            amountOwedSummary.put(debtor, debtorNewAmount);
            amountOwedSummary.put(creditor, 0.0);

            payments.add(new Payment(debtor, creditor, creditorCurrentAmount));
          }
        }
      }
    }
    return payments;
  }

  public static double calculateTotal(List<Payee> payees) {
    double total = 0;
    for (Payee payee : payees) {
      total += payee.getAmount();
    }
    return total;
  }


  public static Map<String, Double> summarise(List<Payee> payees, double total) {
    Map<String, Double> nameAmount = new HashMap();

    // Create a list with the distinct elements using stream
    double dividedAmount = total / payees.size();

    //Initialise map with divided amount to pay for each payee
    for (Payee payee : payees) {
      String name = payee.getPayerName();
      nameAmount.put(name, dividedAmount);
    }

    //Reduce remaining balance to pay by the amount paid
    for (Payee payee : payees) {
      String name = payee.getPayerName();
      double amount = payee.getAmount();
      double currentAmount = nameAmount.get(name);
      currentAmount -= amount;
      nameAmount.put(name, currentAmount);

    }
    return nameAmount;
  }

  public static List<String> getDebtors(Map<String, Double> nameAmount) {
    List<String> debtors = new ArrayList();
    for (Map.Entry<String, Double> entry : nameAmount.entrySet()) {
      if (nameAmount.get(entry.getKey()) > 0.0) {
        debtors.add(entry.getKey());
      }
    }
    return debtors;

  }

  public static List<String> getCreditors(Map<String, Double> nameAmount) {
    List<String> creditors = new ArrayList();
    for (Map.Entry<String, Double> entry : nameAmount.entrySet()) {
      if (nameAmount.get(entry.getKey()) < 0.0) {
        creditors.add(entry.getKey());
        nameAmount.put(entry.getKey(), Math.abs(entry.getValue()));
      }
    }
    return creditors;

  }
}
