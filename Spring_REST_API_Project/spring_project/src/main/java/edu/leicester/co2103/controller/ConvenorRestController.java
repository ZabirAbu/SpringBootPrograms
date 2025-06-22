package edu.leicester.co2103.controller;
import edu.leicester.co2103.domain.Convenor;
import edu.leicester.co2103.domain.Module;
import edu.leicester.co2103.repo.ConvenorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;
import java.util.Optional;

@RestController
public class ConvenorRestController {
    @Autowired
    private ConvenorRepository convenorRepo;

    //list all convenors (Endpoint 1)
    @GetMapping("/convenors")
    public ResponseEntity<List<Convenor>> listAllConvenors() {
        List<Convenor> convenors = (List<Convenor>) convenorRepo.findAll();
        if (convenors.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(convenors, HttpStatus.OK);
    }

    //create a specific convenor (Endpoint 2)
    @PostMapping("/convenors")
    public ResponseEntity<?> createConvenor(@RequestBody Convenor convenor, UriComponentsBuilder ucBuilder) {
        if (convenorRepo.existsById(convenor.getId())) {
            return new ResponseEntity<ErrorInfo>(new ErrorInfo("A convenor named " + convenor.getName() + " already exists."),
                    HttpStatus.CONFLICT);
        }
        convenorRepo.save(convenor);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/convenors/{id}").buildAndExpand(convenor.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    //retrieve a specific convenor (Endpoint 3)
    @GetMapping("/convenors/{id}")
    public ResponseEntity<?> getConvenor(@PathVariable("id") Long id) {
        if (convenorRepo.findById(id).isPresent()) {
            Convenor convenor = convenorRepo.findById(id).get();
            return new ResponseEntity<Convenor>(convenor, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ErrorInfo("Convenor with id " + id + " not found"), HttpStatus.NOT_FOUND);
        }
    }

    //update a specific convenor (Endpoint 4)
    @PutMapping("/convenors/{id}")
    public ResponseEntity<?> updateConvenor(@PathVariable("id") Long id, @RequestBody Convenor updatedConvenor) {
        Optional<Convenor> optionalConvenor = convenorRepo.findById(id);
        if (optionalConvenor.isPresent()) {
            Convenor convenor = optionalConvenor.get();
            convenor.setName(updatedConvenor.getName());
            convenor.setPosition(updatedConvenor.getPosition());
            convenorRepo.save(convenor);
            return ResponseEntity.ok(convenor);
        } else {
            ErrorInfo errorInfo = new ErrorInfo("Convenor with id " + id + " not found.");
            return new ResponseEntity<>(errorInfo, HttpStatus.NOT_FOUND);
        }
    }

    //delete a specific convenor (Endpoint 5)

    @DeleteMapping("/convenors/{id}")
    public ResponseEntity<?> deleteConvenor(@PathVariable("id") Long id) {
        Optional<Convenor> optionalConvenor = convenorRepo.findById(id);
        if (optionalConvenor.isPresent()) {
            Convenor convenor = optionalConvenor.get();
            convenorRepo .delete(convenor);
            return ResponseEntity.ok(null);
        } else {
            ErrorInfo errorInfo = new ErrorInfo("Convenor with id " + id + " not found.");
            return new ResponseEntity<>(errorInfo, HttpStatus.NOT_FOUND);
        }
    }

    //list all modules taught by a convenor (Endpoint 6)
    @GetMapping("/convenors/{id}/modules")
    public ResponseEntity<List<Module>> getModulesByConvenor(@PathVariable long id) {
        Optional<Convenor> optionalConvenor = convenorRepo.findById(id);
        if (optionalConvenor.isPresent()) {
            Convenor convenor = optionalConvenor.get();
            List<Module> modules = convenor.getModules();
            return ResponseEntity.ok(modules);
        } else {
            ErrorInfo errorInfo = new ErrorInfo("Convenor with id " + id + " not found.");
            return new ResponseEntity(errorInfo, HttpStatus.NOT_FOUND);
        }
    }

}
