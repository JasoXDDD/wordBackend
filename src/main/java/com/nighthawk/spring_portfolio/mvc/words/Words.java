package com.nighthawk.spring_portfolio.mvc.words;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.io.File;
import java.util.Scanner;

@Data  // Annotations to simplify writing code (ie constructors, setters)
@NoArgsConstructor
@AllArgsConstructor
@Entity // Annotation to simplify creating an entity, which is a lightweight persistence domain object. Typically, an entity represents a table in a relational database, and each entity instance corresponds to a row in that table.
public class Words {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique=true)
    private String word;

    // starting words
    public static String[] init() {
        ArrayList<String> wordsList = new ArrayList<String>();
        File common = new File("./1-1000.txt");
        Scanner sc = new Scanner(common);
        while (sc.hasNextLine()){
            String commonWord = sc.nextLine();
            if (commonWord.length>=3) {
                wordsList.add(commonWord);
            }
        }
        final String[] wordsArray = wordsList.toArray();
        return wordsArray;
    }
}
