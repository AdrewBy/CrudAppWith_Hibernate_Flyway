package org.ustsinau.chapter2_2;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;




@Suite
@SelectPackages("com.ustsinau.chapter2_2")
public class TestApp {

    public static void main(String[] args) {
        org.junit.platform.console.ConsoleLauncher.main(new String[]{
                "--select-package", "com.ustsinau.chapter2_2"
        });
    }
}

