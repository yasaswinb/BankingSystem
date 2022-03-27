import java.text.SimpleDateFormat;
import java.util.Date;

public class CurrentAccount implements BankAccount{

    //account holder name
    private final String AccountHolderName;
    //account number
    private final int AccountNumber;
    //balance variable
    private int balance = 0;
    //account statement
    private Statement statement;
    //humans group
    private ThreadGroup HumansGroup;
    //companies group
    private ThreadGroup CompaniesGroup;
    //holding the withdrawing amount temporary
    private int temporaryAmount;

    public CurrentAccount(String accountHolderName, int accountNumber, ThreadGroup humansGroup, ThreadGroup companiesGroup) {
        this.AccountHolderName = accountHolderName;
        this.AccountNumber = accountNumber;
        this.statement = new Statement(accountHolderName,accountNumber);
        this.HumansGroup = humansGroup;
        this.CompaniesGroup = companiesGroup;
    }

    @Override
    public synchronized int getBalance() {
        notifyAll();
        return balance;
    }

    @Override
    public int getAccountNumber() {
        return AccountNumber;
    }

    @Override
    public String getAccountHolder() {
        return AccountHolderName;
    }

    @Override
    public synchronized void deposit(Transaction t) {

        System.out.println(t.getCID() + " acquire the monitor " + new SimpleDateFormat("HH:mm:ss.SSS").format(new Date()));
        System.out.println(t.getCID() + " reading the current balance " + getBalance() + "$ " + new SimpleDateFormat("HH:mm:ss.SSS").format(new Date()));
        System.out.println(t.getCID() + " starting a deposit of " + t.getAmount() + "$ " + new SimpleDateFormat("HH:mm:ss.SSS").format(new Date()));
        //increase the balance
        balance = balance + t.getAmount();
        statement.addTransaction(t.getCID(),t.getAmount(), balance);

        System.out.println (t.getCID() + " deposited " + t.getAmount() + "$ " + new SimpleDateFormat("HH:mm:ss.SSS").format(new Date()) ) ;

        System.out.println(t.getCID() + " finished the deposit, current balance is "+ balance +"$ "+ new SimpleDateFormat("HH:mm:ss.SSS").format(new Date()));
        System.out.println(t.getCID() + " release the monitor " + new SimpleDateFormat("HH:mm:ss.SSS").format(new Date()) + "\n");
        notifyAll();//signals to change states for the waiting threads
    }

    @Override
    public synchronized void withdrawal(Transaction t) {
        System.out.println(t.getCID() + " acquire the monitor " + new SimpleDateFormat("HH:mm:ss.SSS").format(new Date()));
        System.out.println(t.getCID() + " reading the current balance " + getBalance() + "$ " + new SimpleDateFormat("HH:mm:ss.SSS").format(new Date()));
        //used to manage the overdrawn issue
        temporaryAmount = t.getAmount();
        //if the balance is not sufficient the thread is placed in the waiting state until it get a chance to withdraw
        while (isOverdrawn()){

            try {
                System.out.println (t.getCID() + " can't withdraw "+ t.getAmount() +"$ since insufficient funds, need to wait "+  new SimpleDateFormat("HH:mm:ss.SSS").format(new Date()) + "\n") ;
                wait();
            }
            catch( InterruptedException e ){
                e.printStackTrace();
            }

        }
        //after the wait state thread get a chance to withdraw the amount it leaves the waiting state and execute the below code block
        System.out.println(t.getCID() + " starting a withdraw of " + t.getAmount() + "$ "  + new SimpleDateFormat("HH:mm:ss.SSS").format(new Date()) );
        //decrease the balance
        balance = balance - t.getAmount();
        statement.addTransaction(t.getCID(), t.getAmount(), balance);

        System.out.println(t.getCID() + " withdrew "+ t.getAmount() + "$ " + new SimpleDateFormat("HH:mm:ss.SSS").format(new Date()) );

        System.out.println(t.getCID() + " finished the withdrawal, current balance is "+ balance +"$ " + new SimpleDateFormat("HH:mm:ss.SSS").format(new Date()) );
        System.out.println(t.getCID() + " release the monitor " + new SimpleDateFormat("HH:mm:ss.SSS").format(new Date()) + "\n");
        notifyAll(); //signals to change states for the waiting threads

    }

    @Override
    public boolean isOverdrawn() {
        //returns the withdrawal capability of the account
        return  (getBalance() - temporaryAmount < 0);
    }

    @Override
    public void printStatement() {
        statement.print();
    }
}
