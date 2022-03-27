public class Student extends Thread {

    //currentAccount
    private final BankAccount BankAccount;
    //Student name
    private final String StudentName;

    //transaction
    private Transaction transaction ;

    //maximum sleep time
    private final int MaxSleepTime = 2000;
    //upper limit for deposit or withdrawal
    private final int MaxAmount = 1000;
    //lower limit for deposit or withdrawal
    private final int MinAmount = 400;

    public Student(BankAccount bankAccount, String studentName, ThreadGroup threadGroup) {

        super(threadGroup, studentName);
        this.BankAccount = bankAccount;
        StudentName = studentName;
    }

    @Override
    public void run() {

            for (int i = 0; i < 6; i++){

                 transaction = new Transaction( StudentName, (int) (Math.random() * (MaxAmount - MinAmount)) + MinAmount);

                if(i >= 3){

                    BankAccount.withdrawal(transaction);

                }else {

                    BankAccount.deposit(transaction);

                }
                try {
                    sleep( ( int ) ( Math.random() * MaxSleepTime) ) ;
                }
                catch ( InterruptedException e ) {
                    e.printStackTrace();
                }
            }

    }
}
