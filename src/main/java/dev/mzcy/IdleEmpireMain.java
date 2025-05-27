package dev.mzcy;

public class IdleEmpireMain {

    public static void main(String[] args) {
        Thread mainThread = Thread.currentThread();
        mainThread.setName("IdleEmpireServerMainThread");

        // Your real application logic here, no dependency loading!
        new dev.mzcy.bootstrap.IdleEmpireServer();
    }

}
