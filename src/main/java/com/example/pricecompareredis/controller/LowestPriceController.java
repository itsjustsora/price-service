package com.example.pricecompareredis.controller;

import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pricecompareredis.service.LowestPriceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class LowestPriceController {

	private final LowestPriceService lowestPriceService;

	@GetMapping("/getZsetValue/{key}")
	public Set GetZsetValue(@PathVariable String key) {
		return lowestPriceService.getZsetValue(key);
	}
}
