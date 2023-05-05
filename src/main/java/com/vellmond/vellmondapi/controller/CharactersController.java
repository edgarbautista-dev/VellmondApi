package com.vellmond.vellmondapi.controller;

import com.github.javafaker.Faker;
import com.vellmond.vellmondapi.exception.CharacterNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by vellmond on 12/04/20.
 * Los controladores son los encargados de recibir las peticiones y devolver las respuestas.
 * En este caso, el controlador CharactersController, recibe las peticiones que llegan a la ruta /characters
 * y devuelve una respuesta.
 * @RestController: Indica que esta clase es un controlador.
 * @RequestMapping: Indica la ruta que se debe seguir para llegar a este controlador.
 * @GetMapping: Indica que este método se ejecutará cuando se haga una petición GET a la ruta indicada.
 *
 *
 */

@RestController
@RequestMapping("/characters")
public class CharactersController {

    private List<String> caracteres = new ArrayList<>();
    Faker faker = new Faker();

    @PostConstruct
    public void init() {
        for (int i = 0; i < 10; i++) {
            caracteres.add(faker.dragonBall().character());
        }
    }


    @RequestMapping("/hello")
    public String hello() {
        return "Hello, Vellmond!";
    }

    @GetMapping("/dragonball")
    public List<String> dragonball() {
        return caracteres;
    }
/*Para las excepciones cuando es un producto nuevo se recomienda usar ResponseStatusException
 * y cuando es una excepcion personalizada se recomienda usar la clase de excepcion personalizada
 * que se encuentra en el paquete exception  CharacterNotFound.java que se usa la notacion @ResponseStatus
 */
    @GetMapping("/dragonball/{name}")
    public String getCharacterByName(@PathVariable("name") String name) {
        return caracteres.stream().filter(c->c.equals(name)).findAny().orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("%s no encontrado",name)));
    }

    /**
     ** @param prefix
     * @return
     *  search?param1=value1&param2=value2&param3=value3
     */
    @GetMapping(value = "/dragonball/search")
    public List<String> getCaracterbyPrefix(@RequestParam("prefix") String prefix){
        List<String> result = caracteres.stream().filter(c->c.startsWith(prefix)).collect(Collectors.toList());
        if(result.isEmpty()){
            throw new CharacterNotFound();
        }
        return result;
    }
}
