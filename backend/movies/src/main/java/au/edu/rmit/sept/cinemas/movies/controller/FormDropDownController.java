package au.edu.rmit.sept.cinemas.movies.controller;

import au.edu.rmit.sept.cinemas.movies.DTO.DeliveryFormDropDownDTO;
import au.edu.rmit.sept.cinemas.movies.repository.EnumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/drop-down")
public class FormDropDownController {

    @Autowired
    private EnumRepository enumRepository;

    @GetMapping("/delivery")
    public DeliveryFormDropDownDTO getDeliveryFormDropDown()
    {
        return new DeliveryFormDropDownDTO(
                enumRepository.getAllDeliveryTimeSlots(),
                enumRepository.getAllDeliveryMethods()
        );
    }
}
