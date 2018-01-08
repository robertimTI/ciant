package com.robertim.ciandt.api;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.format.Formatter;

@SpringBootApplication
public class CiantApplication {

	public static void main(String[] args) {
		SpringApplication.run(CiantApplication.class, args);
	}
	@Bean
	 public Formatter<LocalDate> localDateFormatter() {
	    return new Formatter<LocalDate>() {
	      @Override
	      public LocalDate parse(String text, Locale locale) throws ParseException {
	        return LocalDate.parse(text);
	      }

	      @Override
	      public String print(LocalDate object, Locale locale) {
	        return DateTimeFormatter.ISO_DATE.format(object);
	      }
	    };
	}
}
