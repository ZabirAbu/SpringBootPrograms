package edu.leicester.co2103.controller;
import edu.leicester.co2103.domain.Module;
import edu.leicester.co2103.domain.Session;
import edu.leicester.co2103.repo.ModuleRepository;
import edu.leicester.co2103.repo.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;
import java.util.Optional;

@RestController
public class ModuleRestController {
    @Autowired
    private ModuleRepository moduleRepo;
    @Autowired
    private SessionRepository sessionRepo;

    //list all modules (Endpoint 7)
    @GetMapping("/modules")
    public ResponseEntity<List<Module>> listAllModules() {
        List<Module> modules = (List<Module>) moduleRepo.findAll();
        if (modules.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(modules, HttpStatus.OK);
    }

    //create a module (Endpoint 8)
    @PostMapping("/modules")
    public ResponseEntity<?> createModule(@RequestBody Module module, UriComponentsBuilder ucBuilder) {

        if (moduleRepo.existsById(module.getCode())) {
            return new ResponseEntity<ErrorInfo>(new ErrorInfo("A module named " + module.getTitle() + " already exists."),
                    HttpStatus.CONFLICT);
        }
        moduleRepo.save(module);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/modules/{code}").buildAndExpand(module.getCode()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }


    //retrieve a module (Endpoint 9)
    @GetMapping("/modules/{code}")
    public ResponseEntity<?> getModule(@PathVariable("code") String code) {
        if (moduleRepo.findByCode(code).isPresent()) {
            Module module = moduleRepo.findByCode(code).get();
            return new ResponseEntity<Module>(module, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ErrorInfo("Module with code " + code + " not found"), HttpStatus.NOT_FOUND);
        }
    }


    //update a module (Endpoint 10)
    @PatchMapping("/modules/{code}")
    public ResponseEntity<?> updateModule(@PathVariable String code, @RequestBody Module updatedModule) {
        Optional<Module> optionalModule = moduleRepo.findById(code);

        if (!optionalModule.isPresent()) {
            return new ResponseEntity<>(new ErrorInfo("Module with code " + code + " not found"),
                    HttpStatus.NOT_FOUND);
        }
        Module module = optionalModule.get();
        if (updatedModule.getCode() != null) {
            module.setCode(updatedModule.getCode());
        }
        if (updatedModule.getTitle() != null) {
            module.setTitle(updatedModule.getTitle());
        }
        if (updatedModule.getLevel() != 0) {
            module.setLevel(updatedModule.getLevel());
        }
        if (updatedModule.isOptional() != module.isOptional()) {
            module.setOptional(updatedModule.isOptional());
        }

        moduleRepo.save(module);
        return new ResponseEntity<>(module, HttpStatus.OK);
    }

    //delete a module (Endpoint 11)
    @DeleteMapping("/modules/{code}")
    public ResponseEntity<?> deleteModule(@PathVariable String code) {
        if (moduleRepo.findByCode(code).isPresent()) {
            moduleRepo.deleteById(code);
            return ResponseEntity.ok(null);
        } else {
            return new ResponseEntity<>(new ErrorInfo("Module with code " + code + " not found"), HttpStatus.NOT_FOUND);
        }
    }


    //list all sessions in a module (Endpoint 12)
    @GetMapping("/modules/{code}/sessions")
    public ResponseEntity<?> getSessionsInModule(@PathVariable("code") String code) {
        Optional<Module> optionalModule = moduleRepo.findByCode(code);
        if (optionalModule.isPresent()) {
            Module module = optionalModule.get();
            List<Session> sessions = module.getSessions();
            return new ResponseEntity<List<Session>>(sessions, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ErrorInfo("Module with code " + code + " not found"), HttpStatus.NOT_FOUND);
        }
    }

    //create a session in a module (Endpoint 13)
    @PostMapping("modules/{code}/sessions")
    public ResponseEntity<?> createSessionInModule(@PathVariable("code") String code, @RequestBody Session session) {
        if (moduleRepo.findById(code).isPresent()) {
            moduleRepo.findById(code).get().getSessions().add(session);
            moduleRepo.save(moduleRepo.findById(code).get());
            return new ResponseEntity<>(session, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //retrieve a session in a module (Endpoint 14)
    @GetMapping("/modules/{code}/sessions/{id}")
    public ResponseEntity<?> getSession(@PathVariable("code") String code, @PathVariable("id") Long id) {
        if (moduleRepo.findById(code).isPresent()) {
            if (sessionRepo.findById(id).isPresent() && moduleRepo.findById(code).get().getSessions().contains(sessionRepo.findById(id).get())){
                return new ResponseEntity<Session>(sessionRepo.findById(id).get(), HttpStatus.OK);
            } else
                return new ResponseEntity<ErrorInfo>(new ErrorInfo("Session not found."), HttpStatus.NOT_FOUND);
        } else
            return new ResponseEntity<ErrorInfo>(new ErrorInfo("Module not found."), HttpStatus.NOT_FOUND);
    }

    //update (PUT) a session in a module (Endpoint 15)
    @PutMapping("/modules/{code}/sessions/{id}")
    public ResponseEntity<?> updateModuleSession(@PathVariable String code, @PathVariable Long id, @RequestBody Session updatedSession) {
        Optional<Module> optionalModule = moduleRepo.findById(code);
        if (optionalModule.isPresent()) {
            Module module = optionalModule.get();
            Optional<Session> optionalSession = module.getSessions().stream().filter(s -> s.getId() == id).findFirst();
            if (optionalSession.isPresent()) {
                Session session = optionalSession.get();
                session.setTopic(updatedSession.getTopic());
                session.setDatetime(updatedSession.getDatetime());
                session.setDuration(updatedSession.getDuration());
                moduleRepo.save(module);
                return ResponseEntity.ok(session);
            } else {
                ErrorInfo errorInfo = new ErrorInfo("Session with id " + id + " not found.");
                return new ResponseEntity<>(errorInfo, HttpStatus.NOT_FOUND);
            }
        } else {
            ErrorInfo errorInfo = new ErrorInfo("Module with code " + code + " not found.");
            return new ResponseEntity<>(errorInfo, HttpStatus.NOT_FOUND);
        }
    }


    //update (PATCH) a session in a module (Endpoint 16)
    @PatchMapping("/modules/{code}/sessions/{id}")
    public ResponseEntity<?> updateSession(@PathVariable String code, @PathVariable Long id, @RequestBody Session updatedSession) {
        Optional<Module> optionalModule = moduleRepo.findById(code);
        if (optionalModule.isPresent()) {
            Module module = optionalModule.get();
            Optional<Session> optionalSession = module.getSessions().stream().filter(s -> s.getId() == id).findFirst();
            if (optionalSession.isPresent()) {
                Session session = optionalSession.get();
                if (updatedSession.getTopic() != null) {
                    session.setTopic(updatedSession.getTopic());
                }
                if (updatedSession.getDatetime() != null) {
                    session.setDatetime(updatedSession.getDatetime());
                }
                if (updatedSession.getDuration() != 0) {
                    session.setDuration(updatedSession.getDuration());
                }
                moduleRepo.save(module);
                return ResponseEntity.ok(session);
            } else {
                ErrorInfo errorInfo = new ErrorInfo("Session with id " + id + " not found.");
                return new ResponseEntity<>(errorInfo, HttpStatus.NOT_FOUND);
            }
        } else {
            ErrorInfo errorInfo = new ErrorInfo("Module with code " + code + " not found.");
            return new ResponseEntity<>(errorInfo, HttpStatus.NOT_FOUND);
        }
    }


    //delete a session in a module (Endpoint 17)
    @DeleteMapping("/modules/{code}/sessions/{id}")
    public ResponseEntity<?> deleteModuleSession(@PathVariable String code, @PathVariable Long id) {
        Optional<Module> optionalModule = moduleRepo.findById(code);
        if (optionalModule.isPresent()) {
            Module module = optionalModule.get();
            Optional<Session> optionalSession = module.getSessions().stream().filter(s -> s.getId() == id).findFirst();
            if (optionalSession.isPresent()) {
                module.getSessions().remove(optionalSession.get());
                moduleRepo.save(module);
                return ResponseEntity.ok(null);
            } else {
                ErrorInfo errorInfo = new ErrorInfo("Session with id " + id + " not found.");
                return new ResponseEntity<>(errorInfo, HttpStatus.NOT_FOUND);
            }
        } else {
            ErrorInfo errorInfo = new ErrorInfo("Module with code " + code + " not found.");
            return new ResponseEntity<>(errorInfo, HttpStatus.NOT_FOUND);
        }
    }

}


