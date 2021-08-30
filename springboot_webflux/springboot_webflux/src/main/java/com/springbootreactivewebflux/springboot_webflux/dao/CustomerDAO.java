package com.springbootreactivewebflux.springboot_webflux.dao;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;
import com.springbootreactivewebflux.springboot_webflux.dto.Customer;

import reactor.core.publisher.Flux;


@Component
public class CustomerDAO {
	
	
	private static void sleepExecution(int i){
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Using the traditional approach of Java 8
	
	public List<Customer> getCustomers(){
		return IntStream.rangeClosed(1, 10)
				.peek(CustomerDAO::sleepExecution)
				.peek(i->System.out.println("Processing count:"+i))
				.mapToObj(i->new Customer(i,"Customer"+i))
				.collect(Collectors.toList());
	}
	
	// Using flux syntax below
	
	public Flux<Customer> getCustomersStream(){
		return Flux.range(1, 10)
				.delayElements(Duration.ofSeconds(1))
				.doOnNext(i->System.out.println("Processing count using Flux:"+i))
				.map(i->new Customer(i,"Customer:"+i));
	}

}
