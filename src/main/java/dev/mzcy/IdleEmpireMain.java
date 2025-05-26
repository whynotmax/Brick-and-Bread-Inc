package dev.mzcy;

import dev.mzcy.bootstrap.IdleEmpireServer;

public class IdleEmpireMain {

    public static void main(String[] args) {
        Thread mainThread = Thread.currentThread();
        mainThread.setName("IdleEmpireServerMainThread");

        new IdleEmpireServer();
    }

}
