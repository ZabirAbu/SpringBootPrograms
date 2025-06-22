package edu.leicester.co2103.controller;
import edu.leicester.co2103.domain.Session;
import edu.leicester.co2103.repo.ConvenorRepository;
import edu.leicester.co2103.repo.ModuleRepository;
import edu.leicester.co2103.repo.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SessionRestController {
    @Autowired
    ConvenorRepository convenorRepo;
    @Autowired
    ModuleRepository moduleRepo;
    @Autowired
    SessionRepository sessionRepo;

    //Delete all sessions (Endpoint 18)
    @DeleteMapping("/sessions")
    public ResponseEntity<?> deleteSessions() {
        List<Session> sessions = (List<Session>) sessionRepo.findAll();

        if (sessions.isEmpty()) {
            return ResponseEntity.badRequest().body("No sessions to delete.");
        }

        sessionRepo.deleteAll();
        return ResponseEntity.ok().body("All sessions have been deleted.");
    }


    //List all sessions (Endpoint 19 and 20)
    @GetMapping("/sessions")
    public ResponseEntity<?> getSessions(@RequestParam(required=false) Long convenor, @RequestParam(required=false) String module) {

        List<Session> sessions = new ArrayList<>();
        try {
            if (convenor == null && module == null) {
                sessions = (List<Session>) sessionRepo.findAll();
            }

            if (convenor != null && module == null) {
                for(int i=0; i<convenorRepo.findById(convenor).get().getModules().size(); i++) {
                    sessions.addAll(convenorRepo.findById(convenor).get().getModules().get(i).getSessions());
                }
            }

            if (convenor == null && module != null) {
                sessions = moduleRepo.findById(module).get().getSessions();
            }


            if (convenor != null && convenorRepo.findById(convenor).isEmpty()){
                return new ResponseEntity<>(new ErrorInfo("Convenor does not exist"), HttpStatus.NOT_FOUND);
            }

            if (module != null && moduleRepo.findById(module).isEmpty()){
                return new ResponseEntity<>(new ErrorInfo("Module does not exist"), HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(sessions, HttpStatus.OK);

        } catch(NoSuchElementException e) {
            return new ResponseEntity<List<Session>>(HttpStatus.NO_CONTENT);

        }}

}

