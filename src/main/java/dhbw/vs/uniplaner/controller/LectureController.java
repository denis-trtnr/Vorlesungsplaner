package dhbw.vs.uniplaner.controller;

import dhbw.vs.uniplaner.domain.Lecture;
import dhbw.vs.uniplaner.service.LectureService;
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
public class LectureController {

    private final Logger log = LoggerFactory.getLogger(LectureController.class);


    @PostMapping("/lectures")
    public ResponseEntity<Lecture> createLecture(@RequestBody Lecture lecture) throws BadRequestException, URISyntaxException {

    }

    /**
     * {@code PUT  /lectures} : Updates an existing Lecture.
     *
     * @param lecture the lecture to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lecture,
     * or with status {@code 400 (Bad Request)} if the lecture is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lecture couldn't be updated.
     * @throws BadRequestException if the lecture ist not valid.
     */
    @PutMapping("/lectures")
    public ResponseEntity<Lecture> updateLecture(@RequestBody Lecture lecture) throws  BadRequestException {

    }

    @PutMapping("/lectures/{id}")
    public ResponseEntity<Lecture> updateLecture(@PathVariable(value = "id") Long id,@Valid @RequestBody Lecture lectureDetails) throws ResourceNotFoundException {

    }

    /**
     * {@code GET  /lectures} : get all the lectures.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lectures in body.
     */
    @GetMapping("/lectures")
    public List<Lecture> getAlllectures() {

    }

    /**
     * {@code GET  /lectures/:id} : get the "id" lecture.
     *
     * @param id the id of the lecture to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lecture, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lectures/{id}")
    public ResponseEntity<Lecture> getLecture(@PathVariable Long id) throws ResourceNotFoundException {

    }
        /**
         * {@code DELETE  /lectures/:id} : delete the "id" lecture.
         *
         * @param id the id of the lecture to delete.
         * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
         */
        @DeleteMapping("/lectures/{id}")
        public ResponseEntity<Void> deleteLecture(@PathVariable Long id) {

        }




}
