package com.d43.tbs.utils;

import java.util.Random;

public class Rnd {
	
	public static int generate(int a, int b) {
		Random random = new Random();
		return random.nextInt(b-a+1) + a;
	}
}
