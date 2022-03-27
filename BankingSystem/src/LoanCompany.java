public class LoanCompany extends Thread {

    //currentAccount
    private final BankAccount BankAccount;
    //Company name
    private final String CompanyName;
    //transaction
    private Transaction transaction ;

    //maximum sleep time
    private final int MaxSleepTime = 2000;
    //upper limit for deposit or withdrawal
    private final int MaxAmount = 6000;
    //lower limit for deposit or withdrawal
    private final int MinAmount = 3000;

    public LoanCompany(BankAccount bankAccount, String companyName, ThreadGroup threadGroup) {
        super(threadGroup, companyName);
        BankAccount = bankAccount;
        CompanyName = companyName;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++){
            transaction = new Transaction( CompanyName, (int) (Math.random() * (MaxAmount - MinAmount)) + MinAmount);

            BankAccount.deposit(transaction);

            try {
                sleep( ( int ) ( Math.random() * MaxSleepTime) ) ;
            }
            catch ( InterruptedException e ) {
                e.printStackTrace();
            }
        }
    }

}
