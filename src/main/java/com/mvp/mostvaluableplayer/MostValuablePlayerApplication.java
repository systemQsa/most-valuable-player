package com.mvp.mostvaluableplayer;

import com.mvp.mostvaluableplayer.service.MainService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

public class MostValuablePlayerApplication {
	private final MainService mainService = new MainService();

	public static void main(String[] args) {
		MostValuablePlayerApplication app = new MostValuablePlayerApplication();
		app.run();
	}

	public void run() {
		mainService.playGames();
	}
}
