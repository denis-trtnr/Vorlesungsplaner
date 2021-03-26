package dhbw.vs.uniplaner.controller;

import dhbw.vs.uniplaner.domain.LectureDate;
import dhbw.vs.uniplaner.service.LectureDateService;
import dhbw.vs.uniplaner.exception.BadRequestException;
import dhbw.vs.uniplaner.exception.ResourceNotFoundException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LectureDateController {

    private final Logger log = LoggerFactory.getLogger(LectureDateController.class);


    @PostMapping("/lecturedates")
    public ResponseEntity<LectureDate> createLectureDate(@RequestBody LectureDate lecturedate) throws BadRequestException, URISyntaxException {

    }

    /**
     * {@code PUT  /lecturedates} : Updates an existing LectureDate.
     *
     * @param lecturedate the lecturedate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lecturedate,
     * or with status {@code 400 (Bad Request)} if the lecturedate is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lecturedate couldn't be updated.
     * @throws BadRequestException if the lecturedate ist not valid.
     */
    @PutMapping("/lecturedates")
    public ResponseEntity<LectureDate> updateLectureDate(@RequestBody LectureDate lecturedate) throws  BadRequestException {

    }

    @PutMapping("/lecturedates/{id}")
    public ResponseEntity<LectureDate> updateLectureDate(@PathVariable(value = "id") Long id,@Valid @RequestBody LectureDate lecturedateDetails) throws ResourceNotFoundException {

    }

    /**
     * {@code GET  /lecturedates} : get all the lecturedates.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lecturedates in body.
     */
    @GetMapping("/lecturedates")
    public List<LectureDate> getAlllecturedates() {

    }

    /**
     * {@code GET  /lecturedates/:id} : get the "id" lecturedate.
     *
     * @param id the id of the lecturedate to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lecturedate, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lecturedates/{id}")
    public ResponseEntity<LectureDate> getLectureDate(@PathVariable Long id) throws ResourceNotFoundException {

    }
        /**
         * {@code DELETE  /lecturedates/:id} : delete the "id" lecturedate.
         *
         * @param id the id of the lecturedate to delete.
         * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
         */
        @DeleteMapping("/lecturedates/{id}")
        public ResponseEntity<Void> deleteLectureDate(@PathVariable Long id) {

        }




}
