import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;


public class SplitwiseTest {

  @Test
  public void testPayees() {
    List<Payee> payees = new ArrayList();

    Payee payee1 = new Payee("John", 5);

    Payee payee2 = new Payee("Peter", 10);

    Payee payee3 = new Payee("Keith", 15);

    Payee payee4 = new Payee("Bob", 14);

    payees.add(payee1);
    payees.add(payee2);
    payees.add(payee3);
    payees.add(payee4);

    List<Payment> expected = Arrays
        .asList(new Payment("John", "Keith", 4), new Payment("John", "Bob", 2),
            new Payment("Peter", "Bob", 1));

    assertThat(Splitwise.splitPayment(payees), containsInAnyOrder(expected.toArray()));

  }

  @Test
  public void testBalancedPayees() {
    List<Payee> payees = new ArrayList();

    Payee payee1 = new Payee("John", 5);

    Payee payee2 = new Payee("Peter", 5);

    Payee payee3 = new Payee("Bob", 5);

    payees.add(payee1);
    payees.add(payee2);
    payees.add(payee3);

    List<Payment> expected = Arrays
        .asList();

    assertThat(Splitwise.splitPayment(payees), containsInAnyOrder(expected.toArray()));

  }
}
