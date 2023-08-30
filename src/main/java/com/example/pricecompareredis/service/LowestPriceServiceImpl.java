package com.example.pricecompareredis.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class LowestPriceServiceImpl implements LowestPriceService{

	@Autowired
	private RedisTemplate redisTemplate;

	public Set getZsetValue(String key) {
		return redisTemplate.opsForZSet().rangeWithScores(key, 0, 9);
	}
}
