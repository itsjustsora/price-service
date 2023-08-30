package com.example.pricecompareredis.controller;

import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pricecompareredis.service.LowestPriceService;
import com.example.pricecompareredis.service.LowestPriceServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class LowestPriceController {

	private final LowestPriceService lowestPriceService;

	@GetMapping("/product")
	public Set GetZsetValue(String key) {
		return lowestPriceService.getZsetValue(key);
	}
}
