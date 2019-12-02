package br.com.ciet.delivery.steps;

import br.com.ciet.delivery.integration.LoggerPrintStream;
import com.jayway.restassured.filter.log.RequestLoggingFilter;
import com.jayway.restassured.filter.log.ResponseLoggingFilter;
import com.jayway.restassured.response.ValidatableResponse;
import com.jayway.restassured.specification.RequestSpecification;
import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.destination.Destination;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import com.ninja_squad.dbsetup.operation.Insert;
import com.ninja_squad.dbsetup.operation.Operation;
import cucumber.api.DataTable;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gherkin.formatter.model.DataTableRow;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.*;

import static com.google.common.truth.Truth.assertThat;
import static com.jayway.restassured.RestAssured.given;
import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;
import static com.ninja_squad.dbsetup.operation.CompositeOperation.sequenceOf;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

@Slf4j
public class DatabaseSteps {
  private static final Properties properties = new Properties();

  static {
    try (InputStream resource =
                 Thread.currentThread().getContextClassLoader().getResourceAsStream("test-db.yaml")) {
      properties.load(resource);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }


  @LocalServerPort
  private int port = 9001;

  private Map<String, String> defaultHeaders;

  private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseSteps.class);

  private ValidatableResponse response;
  private String responseValue;
  private LoggerPrintStream loggerPrintStream = new LoggerPrintStream(LOGGER, Level.DEBUG);
  private RequestLoggingFilter requestLoggingFilter = new RequestLoggingFilter(loggerPrintStream);
  private ResponseLoggingFilter responseLoggingFilter = new ResponseLoggingFilter(loggerPrintStream);

  private RequestSpecification create() {
    return given()
            .filters(requestLoggingFilter, responseLoggingFilter)
            .baseUri("http://localhost")
            .port(this.port)
            .headers(this.defaultHeaders);
  }

  private final Destination destination = new DriverManagerDestination(
          properties.getProperty("database.url"), properties.getProperty("database.user"),
          properties.getProperty("database.password"));

  public DatabaseSteps() {

    this.defaultHeaders = new HashMap<>();
    this.defaultHeaders.put("Content-Type", "application/json");
  }

  @Given("^I have only the following rows in the \"([^\"]*)\" table:$")
  public void iHaveOnlyTheFollowingRowsInTheTable(final String tableName,
                                                  final DataTable data) {
    this.deleteAll(tableName);
    this.insert(tableName, data);
  }


  private void insert(final String tableName, final DataTable data) {
    final List<DataTableRow> rows = data.getGherkinRows();
    final List<String> columns = rows.get(0).getCells();


    final List<Operation> operations = new ArrayList<>();

    for (DataTableRow row : rows.subList(1, rows.size())) {
      final Insert.Builder builder = Insert.into(tableName);
      List<String> cells = row.getCells();
      Insert.RowBuilder rowBuilder = builder.row();
      for (int i = 0; i < cells.size(); i++) {
        if(!("null".equals(cells.get(i)) || "NULL".equals(cells.get(i))))
          rowBuilder.column(columns.get(i), cells.get(i));
      }
      rowBuilder.end();

      operations.add(builder.build());
    }

    this.apply(sequenceOf(operations));
  }

  @Then("^response should be json:$")
  public final void responseShouldBeJson(final String jsonResponseString) throws Throwable {
    assertThatJson(this.responseValue)
            .isEqualTo(jsonResponseString);
  }


  @Given("^I have (\\d+) macros$")
  public void i_have_macros(int arg1) throws Throwable {
    // Write code here that turns the phrase above into concrete actions

  }

  @When("^I filter out errors and warnings for Macros$")
  public void i_filter_out_errors_and_warnings_for_Macros() throws Throwable {
    // Write code here that turns the phrase above into concrete actions

  }

  @Then("^I need to have (\\d+) errors$")
  public void i_need_to_have_errors(int arg1) throws Throwable {
    // Write code here that turns the phrase above into concrete actions

  }

  @Then("^I need to have (\\d+) warnings$")
  public void i_need_to_have_warnings(int arg1) throws Throwable {
    // Write code here that turns the phrase above into concrete actions

  }

  @When("^I make a PATCH call to \"(.*?)\" endpoint with post body:$")
  public final void iMakeAPatchCallToEndpointWithPostBody(String endpointUrl,
                                                          final String postBody) {
    this.response = create().body(postBody).patch(endpointUrl).then();
    this.responseValue = this.response.extract().body().asString();
  }

  @When("^I make a DELETE call to \"(.*?)\" endpoint$")
  public final void iMakeADeleteCallToEndpoint(final String endpointUrl) throws Throwable {
    this.response = create().delete(endpointUrl).then();
    this.responseValue = this.response.extract().body().asString();
  }

  @When("^I make a (POST|PUT) call to \"(.*?)\" endpoint with post body:$")
  public final void iMakeAPostPutCallToEndpointWithPostBody(String method,
                                                            String endpointUrl,
                                                            final String postBody) throws Throwable {
    this.response =
            (method.equals("POST")) ? create()
                    .body(postBody).post(endpointUrl).then() : create()
                    .body(postBody).put(endpointUrl).then();
    this.responseValue = this.response.extract().body().asString();
  }

  @Then("^response status code should be (\\d+)$")
  public final void responseStatusCodeShouldBe(final int statusCode) throws Throwable {
    assertThat(this.response.extract().statusCode()).isEqualTo(statusCode);
  }
  private void deleteAll(final String tableName) {
    this.apply(deleteAllFrom(tableName));
  }

  private void apply(final Operation operation) {
    new DbSetup(destination, operation).launch();
  }

  private void resetTable(String tableName){
    this.deleteAll(tableName);
  }

  @Before
  public void cleanDb() throws SQLException, ClassNotFoundException {
    this.resetTable("entregas");

  }



}
