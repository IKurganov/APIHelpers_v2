package ru.otus.APIHelpers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import retrofit2.Response;
import ru.otus.APIHelpers.pojo.CreateUserRequest;
import ru.otus.APIHelpers.pojo.CreateUserResponse;
import ru.otus.APIHelpers.pojo.Resource;
import ru.otus.APIHelpers.pojo.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@SpringBootTest
class ApiHelpersUserTests {
	private Logger log = LogManager.getLogger(ApiHelpersUserTests.class);
	Response<User> response;
	//Endpoint service for send request
	APIInterface service = APIClientHelper.getClient().create(APIInterface.class);

	@Test
	@DisplayName("GET - SINGLE USER")
	void checkGettingSingleUser() throws IOException {
		response = service.getUserById().execute();

		if(response.isSuccessful()){
			log.info("Response SUCCESS");
		}else{
			log.info("Response ERROR");
		}
	}

	@Test
	@DisplayName("GET - UNKNOWN RESOURCE")
	void retrofitTestOfResources() throws IOException {

		Response<Resource> response;

		//Endpoint client for send request
		APIInterface service = APIClientHelper.getClient().create(APIInterface.class);


		//GET request for get request
		response = service.getResource().execute();

		// проверим, что запрос был успешен
		Assertions.assertTrue(response.isSuccessful());

		Integer expectedYear = 2001;
		Assertions.assertEquals(expectedYear, response.body().getData().getYear());

		String expectedName = "fuchsia rose";
		Assertions.assertEquals(expectedName, response.body().getData().getName());

	}

	@Test
	@DisplayName("POST - CREATE USER")
	void checkUserCreation() throws IOException {
		// создаем респонз, который получим
		Response<CreateUserResponse> responseCreateUser;

		// создаем бади, который отправим
		CreateUserRequest requestNewUserData = new CreateUserRequest();
		String expectedName = "Vasya";
		String expectedJob = "QA Tester";
		requestNewUserData.setName(expectedName);
		requestNewUserData.setJob(expectedJob);

		// создаем юзера с созданным бади
		responseCreateUser = service.createUser(requestNewUserData).execute();

		log.info("ID созданного пользователя: " + responseCreateUser.body().getId());
		log.info("Время создания пользователя: " + responseCreateUser.body().getCreatedAt());
		log.info("Body ответа от API : " + requestNewUserData);

		if(responseCreateUser.isSuccessful()){
			System.out.println("Response SUCCESS");
		}else{
			log.info("Response ERROR");
		}
		System.out.println(responseCreateUser.body());

		Assertions.assertEquals(201, responseCreateUser.code());
		Assertions.assertEquals("Vasya", responseCreateUser.body().getName());
		Assertions.assertEquals("QA Tester", responseCreateUser.body().getJob());
	}

}
