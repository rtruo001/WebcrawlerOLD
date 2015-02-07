package org.randy.truong;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.*;

public class App {

    private static ArrayList<String> ReadFile(String textFile) {
        File file = new File(textFile);
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        DataInputStream dis = null;
        ArrayList<String> urls = new ArrayList<String>();

        try {
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            dis = new DataInputStream(bis);
            String currURL;

            while (dis.available() != 0) {
                currURL = dis.readLine();
                System.out.println("Reading from text file, URL: " + currURL);
                urls.add(currURL);
            }

            fis.close();
            bis.close();
            dis.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return urls;

    }

    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }

    private static void PrintLinks(Elements content) {
        for (Element link: content) {
            System.out.println("Links within base link: " + link.attr("abs:href"));
        }
    }

    private static void DownloadingLinks(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            Elements links = doc.select("a[href]");
            System.out.println("\nLinks: " + links.size());
            PrintLinks(links);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void Webcrawl(ArrayList<String> urls) {
        for (int i = 0; i < urls.size(); ++i) {
            DownloadingLinks(urls.get(i));
        }
    }

    public static void main(String[] args) throws IOException {
        Validate.isTrue(args.length == 1, "Add parameter: Text file including URLs");
        String textFile = args[0];
        System.out.println("Fetching textFile " + textFile);
        ArrayList<String> urls = ReadFile(textFile);

        for (int i = 0; i < urls.size(); ++i) {
            System.out.println(urls.get(i));
        }

        Webcrawl(urls);
    }
}

    //     Validate.isTrue(args.length == 1, "usage: supply url to fetch");
    //     String url = args[0];
    //     print("Fetching %s...", url);

    //     Document doc = Jsoup.connect(url).get();
    //     Elements links = doc.select("a[href]");
    //     Elements media = doc.select("[src]");
    //     Elements imports = doc.select("link[href]");

    //     print("\nMedia: (%d)", media.size());
    //     for (Element src : media) {
    //         if (src.tagName().equals("img"))
    //             print(" * %s: <%s> %sx%s (%s)",
    //                     src.tagName(), src.attr("abs:src"), src.attr("width"), src.attr("height"),
    //                     trim(src.attr("alt"), 20));
    //         else
    //             print(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
    //     }

    //     print("\nImports: (%d)", imports.size());
    //     for (Element link : imports) {
    //         print(" * %s <%s> (%s)", link.tagName(),link.attr("abs:href"), link.attr("rel"));
    //     }

    //     print("\nLinks: (%d)", links.size());
    //     for (Element link : links) {
    //         print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
    //     }
    // }

    // private static void print(String msg, Object... args) {
    //     System.out.println(String.format(msg, args));
    // }

    // private static String trim(String s, int width) {
    //     if (s.length() > width)
    //         return s.substring(0, width-1) + ".";
    //     else
    //         return s;
    // }
    //}