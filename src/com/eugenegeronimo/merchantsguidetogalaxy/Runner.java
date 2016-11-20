package com.eugenegeronimo.merchantsguidetogalaxy;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by esgeronimo on 9/20/2015.
 */
public class Runner {
    public static void main(String args[]) {
        if (args.length < 1 || null == args[0])
            System.out.println("Sorry. Can't do transactions without the file.");

        try {
            Merchant merchant = new Merchant();
            List<String> responses = merchant.interact(
                    Files.readAllLines(Paths.get(args[0]), Charset.defaultCharset()));
            for(String response : responses) {
                System.out.println(response);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
