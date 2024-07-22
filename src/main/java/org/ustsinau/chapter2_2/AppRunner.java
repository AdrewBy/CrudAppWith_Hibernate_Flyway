package org.ustsinau.chapter2_2;


import org.ustsinau.chapter2_2.view.HeadConsole;

import java.io.IOException;


public class AppRunner {
    public static void main(String[] args) {

        HeadConsole headConsole = new HeadConsole();
        try {
            headConsole.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
