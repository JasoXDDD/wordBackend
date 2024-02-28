package com.nighthawk.spring_portfolio.mvc.words;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<List<Words>> getAllWords() {
        // ResponseEntity returns List of Jokes provide by JPA findAll()
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/new")
    public ResponseEntity<int[]> getNewWords() {
        // ResponseEntity returns List of Jokes provide by JPA findAll()
        List<Words> words = repository.findAll();
        int index = (int)(1+words.size()*Math.random());
        int id = Math.toIntExact(words.get(index).getId());
        int leng = words.get(index).getWord().length();
        int[] pack = {id,leng};
        return new ResponseEntity<>(pack, HttpStatus.OK);
    }
    
    @GetMapping("/guess/{id}/{guess}")
    public ResponseEntity<String> guessWord(@PathVariable long id, @PathVariable String guess) {
        /* 
        * Optional (below) is a container object which helps determine if a result is present. 
        * If a value is present, isPresent() will return true
        * get() will return the value.
        */
        Optional<Words> optional = repository.findById(id);
        if (optional.isPresent()) {  // Good ID
            String word = optional.get().getWord();  // value from findByID
            String res = "";
            for (int i=0; i<guess.length();i++) {
                if (guess.substring(i, i+1).equals(word.substring(i,i+1))) {
                    res += word.substring(i,i+1);
                } else if (word.contains(guess.substring(i, i+1))) {
                    res += "+";
                } else {
                    res += "*";
                }
            }
            return new ResponseEntity<>(res, HttpStatus.OK);  // OK HTTP response: status code, headers, and body
        }
        // Bad ID
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  // Failed HTTP response: status code, headers, and body
    }
}
