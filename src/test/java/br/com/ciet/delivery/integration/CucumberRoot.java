package br.com.ciet.delivery.integration;

import br.com.ciet.delivery.DeliveryApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;


@SpringBootTest(classes = DeliveryApplication.class)
@ActiveProfiles("test")
@ContextConfiguration
public class CucumberRoot { }
