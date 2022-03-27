import java.text.SimpleDateFormat;
import java.util.Date;

public class BankingSystem {

    public static void main(String[] args) {

        // The thread groups for Humans and companies.
        System.out.println( "================================================");
        ThreadGroup humans = new ThreadGroup("Humans" );
        System.out.println( "Created "+ humans.getName() +" thread group " + new SimpleDateFormat("HH:mm:ss.SSS").format(new Date()));
        ThreadGroup companies = new ThreadGroup("Companies");
        System.out.println( "Created "+ companies.getName() +" thread group " + new SimpleDateFormat("HH:mm:ss.SSS").format(new Date()));
        System.out.println( "================================================" + "\n");

        //Current account constructor
        BankAccount currentAccount = new CurrentAccount("Rik",1, humans, companies);

        System.out.println( "================================================");
        System.out.println("Creating the student thread " + new SimpleDateFormat("HH:mm:ss.SSS").format(new Date()) );
        Student student = new Student(currentAccount, "Rik", humans);
        System.out.println("Creating the grandmother thread " + new SimpleDateFormat("HH:mm:ss.SSS").format(new Date()) );
        Grandmother grandmother = new Grandmother(currentAccount,"grandmother",humans);
        System.out.println("Creating the loan company thread " + new SimpleDateFormat("HH:mm:ss.SSS").format(new Date()));
        LoanCompany loanCompany = new LoanCompany(currentAccount,"loan company",companies);
        System.out.println("Creating the university thread " + new SimpleDateFormat("HH:mm:ss.SSS").format(new Date()) + "\n" +  "================================================" + "\n");
        University university = new University(currentAccount,"university",companies);

        System.out.println( "================================================" );
        System.out.println("Starting the student thread " + new SimpleDateFormat("HH:mm:ss.SSS").format(new Date()));
        student.start();
        System.out.println("Starting the grandmother thread " + new SimpleDateFormat("HH:mm:ss.SSS").format(new Date()));
        grandmother.start();
        System.out.println("Starting the loan company thread " + new SimpleDateFormat("HH:mm:ss.SSS").format(new Date()));
        loanCompany.start();
        System.out.println("Starting the student thread " + new SimpleDateFormat("HH:mm:ss.SSS").format(new Date()));
        university.start();
        System.out.println("Starting the university thread " + new SimpleDateFormat("HH:mm:ss.SSS").format(new Date()) + "\n" + "================================================" + "\n");

        try {
            student.join() ;
            System.out.println("================================================" );
            System.out.println("student thread got " + student.getState());
            grandmother.join() ;
            System.out.println("grandmother thread got " + grandmother.getState());
            loanCompany.join();
            System.out.println("loan company thread got " + loanCompany.getState());
            university.join();
            System.out.println("university thread got " + university.getState());
            System.out.println("================================================" + "\n");
        } catch( InterruptedException e ){
            e.printStackTrace();
        }

        //printing the bank statement after the termination of all the threads
        currentAccount.printStatement();
    }
}