package toy.protobuf;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PersonJsonTest {
    private static long beforeTime;
    private static long afterTime;

    private PersonJson personJson;
    private ObjectMapper mapper;

    @BeforeAll
    static void timeSetting() {
	beforeTime = System.currentTimeMillis();
    }

    @BeforeEach
    void settings() {

	mapper = new ObjectMapper();

	personJson = PersonJson.builder()
		.id(52)
		.email("kwonby@mz.co.kr")
		.name("kwonby")
		.phoneNumberJson(PhoneNumberJson.builder()
			.number("01022223333")
			.type("Galaxy").build()).build();

    }


    @RepeatedTest(10000)
    @DisplayName("Json 타입의 파일 생성 및 읽기")
    void Json_타입_파일_생성_테스트() {

	try {
	    FileOutputStream outputStream = new FileOutputStream(
		    "jsonPerson.json");
	    mapper.writeValue(outputStream, personJson);
	    outputStream.close();
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}

	try {
	    FileInputStream fileInputStream = new FileInputStream(
		    "jsonPerson.json");
	    PersonJson msgFromFile = mapper.readValue(fileInputStream,
		    PersonJson.class);
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

    @AfterAll
    static void finish() {
	afterTime = System.currentTimeMillis();
	System.out.println("json 사용시 파일 입출력 10000회 시간(ms) " + (afterTime
		- beforeTime));

    }

}
