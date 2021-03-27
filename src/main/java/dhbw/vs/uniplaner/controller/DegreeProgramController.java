package dhbw.vs.uniplaner.controller;

import dhbw.vs.uniplaner.domain.DegreeProgram;
import dhbw.vs.uniplaner.interfaces.IDegreeProgramService;
import dhbw.vs.uniplaner.exception.BadRequestException;
import dhbw.vs.uniplaner.exception.ResourceNotFoundException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class DegreeProgramController {

    private final Logger log = LoggerFactory.getLogger(DegreeProgramController.class);

    @Autowired
    private IDegreeProgramService degreeProgramService;

    @PostMapping("/degreeprograms")
    public ResponseEntity<DegreeProgram> createDegreeProgram(@RequestBody DegreeProgram degreeprogram) throws BadRequestException, URISyntaxException {
        degreeProgramService.save(degreeprogram);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * {@code PUT  /degreeprograms} : Updates an existing DegreeProgram.
     *
     * @param degreeprogram the degreeprogram to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated degreeprogram,
     * or with status {@code 400 (Bad Request)} if the degreeprogram is not valid,
     * or with status {@code 500 (Internal Server Error)} if the degreeprogram couldn't be updated.
     * @throws BadRequestException if the degreeprogram ist not valid.
     */
    @PutMapping("/degreeprograms")
    public ResponseEntity<DegreeProgram> updateDegreeProgram(@RequestBody DegreeProgram degreeprogram) throws  BadRequestException {
        return ResponseEntity.ok(degreeProgramService.update(degreeprogram));
    }

    /**
     * {@code GET  /degreeprograms} : get all the degreeprograms.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of degreeprograms in body.
     */
    @GetMapping("/degreeprograms")
    public ResponseEntity<List<DegreeProgram>> getAlldegreeprograms() {
        return ResponseEntity.ok(degreeProgramService.findAll());
    }

    /**
     * {@code GET  /degreeprograms/:id} : get the "id" degreeprogram.
     *
     * @param id the id of the degreeprogram to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the degreeprogram, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/degreeprograms/{id}")
    public ResponseEntity<Optional<DegreeProgram>> getDegreeProgram(@PathVariable Long id) throws ResourceNotFoundException {
        return  ResponseEntity.ok(degreeProgramService.findOne(id));
    }
        /**
         * {@code DELETE  /degreeprograms/:id} : delete the "id" degreeprogram.
         *
         * @param id the id of the degreeprogram to delete.
         * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
         */
        @DeleteMapping("/degreeprograms/{id}")
        public ResponseEntity<Void> deleteDegreeProgram(@PathVariable Long id) {
            degreeProgramService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }




}
