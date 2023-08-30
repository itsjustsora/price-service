package com.example.pricecompareredis.vo;

import java.util.List;

import lombok.Data;

@Data
public class Keyword {
	private String keyword; // 유아용품 - 하귀스 기저기(FPG0001), A사 딸랑이(FPG0002)
	private List<ProductGroup> productGrpList; // {"FPG0001","FPG0002"}
}
