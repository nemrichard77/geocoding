package hu.uni.eke.b999fa.geocoding;

import hu.uni.eke.b999fa.geocoding.dao.LocationResultDao;
import hu.uni.eke.b999fa.geocoding.service.LocationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Files;


/*@SpringBootApplication
public class GeocodingApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeocodingApplication.class, args);
	}
}*/

@SpringBootApplication
//public class GeocodingApplication implements ApplicationRunner {
public class GeocodingApplication implements CommandLineRunner {
	@Autowired
	ApplicationContext context;

	public static void main(String[] args) {
		SpringApplication.run(GeocodingApplication.class, args);
		/*new SpringApplicationBuilder(GeocodingApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);*/
	}

	@Override
	//public void run(ApplicationArguments args) throws Exception {
	public void run(String... args) throws Exception {
		LocationResultDao dao = context.getBean(LocationResultDao.class);
		LocationsService locations = context.getBean(LocationsService.class);
		System.out.println("Arguments: ");
		String addresses = "";
		for (String arg : args){
			System.out.println(arg);
			String[]commandOptions = arg.split("=");
			if (commandOptions.length > 1 && commandOptions[0].toLowerCase().contentEquals("addresses")) {
				System.out.println("Cities: " + commandOptions[1]);
				addresses = commandOptions[1];
			}
		}
		if (addresses.trim().equals("")) {
			File file = ResourceUtils.getFile("classpath:static/city_list.txt");
			locations.printLocations(new String(Files.readAllBytes(file.toPath())).split(","));
		} else
			locations.printLocations(addresses.split(","));
	}
}

