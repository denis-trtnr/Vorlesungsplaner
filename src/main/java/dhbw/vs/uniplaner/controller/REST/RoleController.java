package dhbw.vs.uniplaner.controller.REST;

import dhbw.vs.uniplaner.domain.Role;
import dhbw.vs.uniplaner.interfaces.IRoleService;
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
public class RoleController {

    private final Logger log = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private IRoleService roleService;

    @PostMapping("/roles")
    public ResponseEntity<Role> createRole(@RequestBody Role role) throws BadRequestException, URISyntaxException {
        roleService.save(role);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * {@code PUT  /roles} : Updates an existing Role.
     *
     * @param role the role to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated role,
     * or with status {@code 400 (Bad Request)} if the role is not valid,
     * or with status {@code 500 (Internal Server Error)} if the role couldn't be updated.
     * @throws BadRequestException if the role ist not valid.
     */
    @PutMapping("/roles")
    public ResponseEntity<Role> updateRole(@RequestBody Role role) throws  BadRequestException {
        return ResponseEntity.ok(roleService.update(role));
    }

    /**
     * {@code GET  /roles} : get all the roles.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of roles in body.
     */
    @GetMapping("/roles")
    public ResponseEntity<List<Role>> getAllroles() {
        return ResponseEntity.ok(roleService.findAll());
    }

    /**
     * {@code GET  /roles/:id} : get the "id" role.
     *
     * @param id the id of the role to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the role, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/roles/{id}")
    public ResponseEntity<Optional<Role>> getRole(@PathVariable Long id) throws ResourceNotFoundException {
        return ResponseEntity.ok(roleService.findOne(id));
    }
        /**
         * {@code DELETE  /roles/:id} : delete the "id" role.
         *
         * @param id the id of the role to delete.
         * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
         */
        @DeleteMapping("/roles/{id}")
        public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
            roleService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }




}
