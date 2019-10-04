public class Payee {

  private String payerName;
  private double amount;

  public Payee(String payerName, double amount) {
    this.payerName = payerName;
    this.amount = amount;
  }

  public String getPayerName() {
    return payerName;
  }

  public void setPayerName(String payerName) {
    this.payerName = payerName;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }
}
