package projectdefence.web;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import projectdefence.models.viewModels.TreatmentViewModel;
import projectdefence.service.TreatmentService;
import projectdefence.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/tr")
public class TreatmentRestController {
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final TreatmentService treatmentService;

    public TreatmentRestController(UserService userService, ModelMapper modelMapper, TreatmentService treatmentService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.treatmentService = treatmentService;
    }

    @GetMapping("/{criteria}")
    @PreAuthorize("hasRole('ROLE_KINESITHERAPIST')")
    public ResponseEntity<List<TreatmentViewModel>> allTreatmentByCriteria(@PathVariable String criteria) {

        List<TreatmentViewModel> treatments = this.treatmentService.findAllByCriteria(criteria);
        return ResponseEntity.ok().body(treatments);
    }

}