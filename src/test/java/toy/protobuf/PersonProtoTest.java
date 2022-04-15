package toy.protobuf;

import org.junit.jupiter.api.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PersonProtoTest {

    private static long beforeTime;
    private static long afterTime;
    private PersonProto.Person.Builder builder;
    private PersonProto.Person person;


    @BeforeAll
    static void timeSetting() {
	beforeTime = System.currentTimeMillis();
    }

    @BeforeEach
    void settings() {
	builder = PersonProto.Person.newBuilder();

	person = builder.setId(52)
		.setEmail("kwonby@mz.co.kr")
		.setName("kwonby")
		.addPhone(PersonProto.Person.PhoneNumber.newBuilder()
			.setNumber("0101112222")
			.setType("Galaxy"))
		.build();
    }


    @RepeatedTest(10000)
    @DisplayName("ProtoBuf 타입의 파일 생성 및 읽기")
    void ProtoBuf_타입_파일_생성_테스트() {

	try {
	    FileOutputStream outputStream = new FileOutputStream(
		    "protoPerson.bin");
	    person.writeTo(outputStream);
	    outputStream.close();
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}

	try {
	    FileInputStream fileInputStream = new FileInputStream(
		    "protoPerson.bin");
	    PersonProto.Person msgFromFile = PersonProto.Person.parseFrom(
		    fileInputStream);
	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}

    }

    @AfterAll
    static void finish() {
	afterTime = System.currentTimeMillis();
	System.out.println("Protobuf 사용시 파일 입출력 10000회 시간(ms) " + (afterTime
		- beforeTime));

    }

}
