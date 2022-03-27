public class University extends Thread {

    //currentAccount
    private final BankAccount BankAccount;
    //Student name
    private final String UniversityName;
    //transaction
    private Transaction transaction ;

    //maximum sleep time
    private final int MaxSleepTime = 2000;
    //upper limit for deposit or withdrawal
    private final int MaxAmount = 6000;
    //lower limit for deposit or withdrawal
    private final int MinAmount = 4000;

    public University(BankAccount bankAccount, String universityName, ThreadGroup threadGroup) {
        super(threadGroup, universityName);
        BankAccount = bankAccount;
        UniversityName = universityName;
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++){
            transaction = new Transaction(UniversityName, (int) (Math.random() * (MaxAmount - MinAmount)) + MinAmount);

            BankAccount.withdrawal(transaction);

            try {
                sleep( ( int ) ( Math.random() * MaxSleepTime) ) ;
            }
            catch ( InterruptedException e ) {
                e.printStackTrace();
            }
        }
    }
}
