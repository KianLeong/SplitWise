public class Payment {

  private String payer;
  private String payee;
  private double amount;

  public Payment(String payer, String payee, double amount) {
    this.payer = payer;
    this.payee = payee;
    this.amount = amount;
  }

  public String getPayer() {
    return payer;
  }

  public void setPayer(String payer) {
    this.payer = payer;
  }

  public String getPayee() {
    return payee;
  }

  public void setPayee(String payee) {
    this.payee = payee;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public boolean equals(Object payment) {
    return this.payee.equals(((Payment) payment).getPayee()) &&
        this.payer.equals(((Payment) payment).getPayer()) &&
        this.amount == ((Payment) payment).getAmount();
  }
}
