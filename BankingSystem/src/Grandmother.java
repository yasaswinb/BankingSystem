public class Grandmother extends Thread{

    //currentAccount
    private final BankAccount BankAccount;
    //Student name
    private final String GrandMotherName;
    //transaction
    private Transaction transaction ;

    //maximum sleep time
    private final int MaxSleepTime = 2000;
    //upper limit for deposit or withdrawal
    private final int MaxAmount = 5000;
    //lower limit for deposit or withdrawal
    private final int MinAmount = 3000;

    public Grandmother(BankAccount bankAccount, String grandMotherName, ThreadGroup threadGroup) {
        super(threadGroup, grandMotherName);
        BankAccount = bankAccount;
        GrandMotherName = grandMotherName;
    }

    @Override
    public void run() {
        for (int i = 0; i < 2; i++){

            transaction = new Transaction( GrandMotherName, (int) (Math.random() * (MaxAmount - MinAmount)) + MinAmount);

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
