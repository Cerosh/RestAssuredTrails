package spotify.outh2.tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class PlaylistTests {
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    String access_token="BQBa_MDwTLNWGXrqelN9uI0GdpYGvg1ihJyXug4QKSfvOip9TNY_4FSWCrSPmd2qz95CIhpe9iotSQJr8NY0amIKteut_GXAhiXA4I7AMcHcYBkUxx7kQUREFzs6YEd2bDBJzqUZhxfGLZDdAE-HHzE3Vk38Rk1VWDWIJth5UH8GcfJRNioCSXLNMz64ReHRntfWwBAYB2TJ1OSB8jkeBVvGuwrJwRGO0re_4zXGAUgv8_9ekHj6UknnHQ";
    String payload = "{\n" +
            "  \"name\": \"Playlist name\",\n" +
            "  \"description\": \"Playlist description\",\n" +
            "  \"public\": false\n" +
            "}";
//    String payload = MessageFormat.format(template, dtf.format(now));
    String user_id = "ovvhvt6g6sf9ci36147nhjnf4";

    @BeforeClass
    public void beforeClass(){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://api.spotify.com").
                setBasePath("/v1").
                addHeader("Authorization","Bearer "+ access_token).
                setContentType(ContentType.JSON).log(LogDetail.ALL);
        requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
               log(LogDetail.ALL);
        responseSpecification = responseSpecBuilder.build();
    }
    @Test
    public void shouldBeAbleToCreatePlaylist(){
        given(requestSpecification).body(payload).
                when().post("/users/"+user_id+"/playlists").
                then().spec(responseSpecification).
                assertThat().statusCode(201).
                contentType(ContentType.JSON).
                body("name",equalTo("Playlist name"),
                        "description",equalTo("Playlist description"),
                        "public",equalTo(false));
    }
    @Test
    public void shouldBeAbleToGetPlaylist(){
        given(requestSpecification).
                when().get("/playlists/0MtI3PK5jOMQPNcGYBvLZr").
                then().spec(responseSpecification).
                assertThat().statusCode(200).
                contentType(ContentType.JSON).
                body("name",equalTo("Playlist name"),
                        "description", equalTo("Playlist description"),
                        "public",equalTo(false));
    }
    @Test
    public void shouldBeAbleToUpdatePlaylist(){
        given(requestSpecification).body(payload).
                when().put("/playlists/7hZSEDITUtd56Tdw1OcIpH").
                then().spec(responseSpecification).
                assertThat().statusCode(200);
    }
    @Test
    public void shouldNotBeAbleToCreatePlaylistWithWrongAccessToken(){
        given().baseUri("https://api.spotify.com").
                basePath("/v1").
                header("Authorization","Bearer "+ 123).
                contentType(ContentType.JSON).log().all().
                when().post("/users/"+user_id+"/playlists").
                then().spec(responseSpecification).
                assertThat().statusCode(401).
                body("error.status", equalTo(401),
                        "error.message", equalTo("Invalid access token"));
    }
}
