package com.byp.payment.controller;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {
    //private final ThemeParkRideRepository themeParkRideRepository;
    //
    //public PaymentController(ThemeParkRideRepository themeParkRideRepository) {
    //    this.themeParkRideRepository = themeParkRideRepository;
    //}
    //
    //@GetMapping(value = "/ride", produces = MediaType.APPLICATION_JSON_VALUE)
    //public Iterable<ThemeParkRide> getRides() {
    //    return themeParkRideRepository.findAll();
    //}
    //
    //@GetMapping(value = "/ride/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    //public ThemeParkRide getRide(@PathVariable long id){
    //    return themeParkRideRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Invalid ride id %s", id)));
    //}
    //
    //@PostMapping(value = "/ride", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    //public ThemeParkRide createRide(@Valid @RequestBody ThemeParkRide themeParkRide) {
    //    return themeParkRideRepository.save(themeParkRide);
    //}
}
