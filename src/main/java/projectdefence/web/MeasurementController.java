package projectdefence.web;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import projectdefence.models.binding.MeasurementAddBindingModel;
import projectdefence.models.serviceModels.MeasurementAddServiceModel;
import projectdefence.models.viewModels.MeasurementViewModel;

import projectdefence.security.rolesAuth.IsKinesitherapist;
import projectdefence.service.LogService;
import projectdefence.service.MeasurementService;
import projectdefence.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/measurement")
public class MeasurementController {
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final MeasurementService measurementService;
    private final LogService logService;

    public MeasurementController(UserService userService, ModelMapper modelMapper, MeasurementService measurementService, LogService logService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.measurementService = measurementService;
        this.logService = logService;
    }

    @GetMapping("/add")
    @IsKinesitherapist
    public String addMeasurement(@AuthenticationPrincipal UserDetails principal, Model model) {

        String name = principal.getUsername();
        model.addAttribute("name", name);

        if (!model.containsAttribute("measurementAddBindingModel")) {
            model.addAttribute("measurementAddBindingModel", new MeasurementAddBindingModel());
            model.addAttribute("userFound", false);
        }
        return "measurement_add";
    }

    @PostMapping("/add")
    @IsKinesitherapist
    public String addMeasurementConfirm(@RequestParam(name = "nameKt") String nameKt, @Valid @ModelAttribute MeasurementAddBindingModel measurementAddBindingModel,
                                        BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("measurementAddBindingModel", measurementAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.measurementAddBindingModel",
                    bindingResult);
            return "redirect:add";
        }

        boolean userFound = this.userService.findByUsername(measurementAddBindingModel.getUsername());
        if (!userFound) {
            redirectAttributes.addFlashAttribute("measurementAddBindingModel", measurementAddBindingModel);
            redirectAttributes.addFlashAttribute("userFound", true);
            return "redirect:add";
        }

        this.measurementService.addMeasurement(this.modelMapper.map(measurementAddBindingModel, MeasurementAddServiceModel.class),
                measurementAddBindingModel.getUsername(), nameKt);

        return "redirect:/home";
    }


    @GetMapping("/check")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String checkMeasurement(@RequestParam("username") String username, Model model) {

        List<MeasurementViewModel> allMeasurementsByUsername = this.measurementService.findAllMeasurementsByUsername(username);

        if (allMeasurementsByUsername.size() == 0) {
            model.addAttribute("no", true);
        } else {
            model.addAttribute("allMeasurements", allMeasurementsByUsername);
        }

        return "measurement-check";
    }

    @GetMapping("/logs")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String measurementLogs(Model model) {
        model.addAttribute("logs", this.logService.findAllLogs());

        return "measurements_log";
    }
}
