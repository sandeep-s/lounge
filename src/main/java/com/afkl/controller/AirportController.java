package com.afkl.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.afkl.service.AirportService;

@RestController
@RequestMapping("/")
public class AirportController {

	private final AirportService airportService;

	@Autowired
	public AirportController(AirportService airportService) {
		super();
		this.airportService = airportService;
	}

	@RequestMapping(value = "/airports", method = GET)
	public @ResponseBody String getAllAirports() {
		return airportService.getAirportDetails();
	}

	@RequestMapping(value = "/airports/{code}", method = GET)
	public @ResponseBody String getAirportsByCode(@PathVariable String code) {
		return airportService.getSpecificAiportDetail(code);
	}

	@RequestMapping(value = "/fares/{origin}/{destination}", method = GET)
	public @ResponseBody String getAFare(@PathVariable("origin") String origin, @PathVariable("destination") String destination) {
		return airportService.getAFare(origin, destination);
	}
}
