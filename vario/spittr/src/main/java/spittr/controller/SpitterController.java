package spittr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.servlet.ModelAndView;
import spittr.model.Spitter;
import spittr.dao.SpitterRepository;

import java.util.Map;

/**
 * Created by drugo on 19/05/2017.
 */
@Controller
@RequestMapping("/spitter")
public class SpitterController {

    private SpitterRepository spitterRepository;

    @Autowired
    public SpitterController(SpitterRepository spitterRepository) {
        this.spitterRepository = spitterRepository;
    }


    @RequestMapping(value="/register", method=RequestMethod.GET)
    public ModelAndView showRegistrationForm() {
        ModelAndView mav = new ModelAndView("registerForm");
        return mav;
    }

    /*
    The new processRegistration() method: it’s given a Spitter object as a parameter.
    This object has firstName, lastName, username, and password properties that will be populated from
    the request parameters of the same name.
    Once it’s called with the Spitter object, processRegistration() calls the save()
    method on the SpitterRepository that is now injected into SpitterController in
    the constructor.
     */
    @RequestMapping(value="/register", method=RequestMethod.POST)
    public String processRegistration(Spitter spitter) {
        System.out.println("\nTEST relativo al processamento del form di inserimento dati da parte dell'utente!");
        System.out.println("\nValori ricevuti:\t"+ spitter.getFirstName() + "\t"+ spitter.getLastName());
        spitterRepository.save(spitter);
        /*
          When InternalResourceViewResolver sees the redirect: prefix on the view specification,
          it knows to interpret it as a redirect specification instead of as a view name
        */
        return "redirect:/spitter/" + spitter.getUsername();
    }

    /*
    * Viene azionato quando viene eseguito il redirect da parte di "processRegistration()".
    * Ha il compito di andare a recuperare dal DB i valori dell'utente richiesto e
    * visualizzare la relativa pagina profilo utente.*/
    @RequestMapping(value="/{username}", method=RequestMethod.GET)
    public ModelAndView showSpitterProfile(@PathVariable String username) {
        ModelAndView mav = new ModelAndView("profile");
        Spitter spitter = spitterRepository.findByUsername(username);
        mav.addObject("spitter",spitter);
        return mav;
    }
}

