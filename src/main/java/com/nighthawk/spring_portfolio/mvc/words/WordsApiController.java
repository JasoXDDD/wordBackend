package com.nighthawk.spring_portfolio.mvc.words;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Math;

@RestController // annotation to simplify the creation of RESTful web services
@RequestMapping("/api/words")  // all requests in file begin with this URI
public class WordsApiController {

    // Autowired enables Control to connect URI request and POJO Object to easily for Database CRUD operations
    @Autowired
    private WordsJpaRepository repository;

    /* GET List of Jokes
     * @GetMapping annotation is used for mapping HTTP GET requests onto specific handler methods.
     */
    @GetMapping("/")
    public ResponseEntity<List<Words>> getWords() {
        // ResponseEntity returns List of Jokes provide by JPA findAll()
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/new")
    public ResponseEntity<int[]> getWords() {
        // ResponseEntity returns List of Jokes provide by JPA findAll()
        List<Words> words = repository.findAll();
        int id = (int)(1+words.size()*Math.random());
        int leng = words.get(id-1).getWord().length;
        int[] pack = {id,leng};
        return new ResponseEntity<>(pack, HttpStatus.OK);
    }
    
    @PostMapping("/guess/{id}/{guess}")
    public ResponseEntity<String> setLike(@PathVariable long id, @PathVariable String guess) {
        /* 
        * Optional (below) is a container object which helps determine if a result is present. 
        * If a value is present, isPresent() will return true
        * get() will return the value.
        */
        Optional<Words> optional = repository.findById(id);
        if (optional.isPresent()) {  // Good ID
            Words word = optional.get();  // value from findByID
            char[] wordArr = word.toCharArray();
            char[] guessArr = guess.toCharArray();
            char[] resArr = word.toCharArray();
            for (int i=0; i<guessArr.length;i++) {
                resArr[i] = '*';
                for (int j=0; j<wordArr.length;j++) {
                    if (guessArr[i]==wordArr[j]) {
                        resArr[i] = '+';
                    }
                }
                if (guessArr[i]==wordArr[i]) {
                    resArr[i] = guessArr[i];
                }
            }
            String res = new String(resArr);
            return new ResponseEntity<>(res, HttpStatus.OK);  // OK HTTP response: status code, headers, and body
        }
        // Bad ID
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  // Failed HTTP response: status code, headers, and body
    }
}
