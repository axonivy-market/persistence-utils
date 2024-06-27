package com.axonivy.utils.persistence.webtest;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import com.axonivy.ivy.webtest.IvyWebTest;
import com.axonivy.ivy.webtest.engine.EngineUrl;
import com.axonivy.ivy.webtest.engine.WebAppFixture;
import com.axonivy.ivy.webtest.primeui.PrimeUi;
import com.axonivy.ivy.webtest.primeui.widget.InputNumber;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;


/**
 * This WebTest will add a new department and a new person to that department.
 * Next step is to verify that the previously added entities are listed in the table.
 * 
 * NOTE: WebTests must follow this name scheme: WebTest*IT
 */
@IvyWebTest(headless = true)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WebTestDemoIT {
	private static String marketingDepartmentName;
	private static String productionDepartmentName;
	private static String firstName;
	private static String lastName;
	private static String ivyUserName;


	@BeforeAll
	public static void setup() {
		int randomNumber = new Random().nextInt(999999999);
		marketingDepartmentName = "Marketing" + randomNumber;
		productionDepartmentName = "Production" + randomNumber;
		firstName = "Peter" + randomNumber;
		lastName = "S" + randomNumber;
		ivyUserName = firstName.toLowerCase() + "." + lastName.toLowerCase();
	}


	@Test
	@Order(1)
	public void testAddNewDepartment(WebAppFixture fixture) {
		startLoginAsAdmin(fixture); //re-login
		addNewDepartment(marketingDepartmentName);

		$(By.id("mainForm:departmentTable_data")).shouldHave(text(marketingDepartmentName));
	}

	@Test
	@Order(2)
	public void testEditDepartment() {
		startProcessDepartmentSearch();

		$(By.id("mainForm:departmentTable_data")).shouldHave(text(marketingDepartmentName));

		int index = indexOf("mainForm:departmentTable", marketingDepartmentName);
		$(By.id("mainForm:departmentTable:" + index + ":editButton")).shouldBe(enabled).click();

		SelenideElement depNameField = $(By.id("departmentForm:departmentName"));
		depNameField.shouldBe(enabled).click();
		depNameField.sendKeys(Keys.END, "2");
		marketingDepartmentName += "2";

		$(By.id("departmentForm:saveButton")).shouldBe(enabled).click();

		$(By.id("mainForm:departmentTable_data")).shouldHave(text(marketingDepartmentName));
	}

	@Test
	@Order(3)
	public void testDeleteDepartment() {
		startProcessDepartmentSearch();

		$(By.id("mainForm:departmentTable_data")).shouldHave(text(marketingDepartmentName));

		int index = indexOf("mainForm:departmentTable", marketingDepartmentName);
		$(By.id("mainForm:departmentTable:" + index + ":deleteButton")).shouldBe(enabled).click();

		$(By.id("okButton")).shouldBe(enabled).click();

		$(By.id("mainForm:departmentTable:" + index + ":deleteButton")).shouldBe(hidden);
	}

	@Test
	@Order(4)
	public void testAddNewPerson() {
		addNewDepartment(productionDepartmentName);

		startProcessPersonSearch();

		$(By.id("mainForm:addButton")).shouldBe(enabled).click();

		$(By.id("personForm:ivyUserName")).shouldBe(enabled).sendKeys(ivyUserName);

		$(By.id("personForm:firstName")).shouldBe(enabled).sendKeys(firstName);

		$(By.id("personForm:lastName")).shouldBe(enabled).sendKeys(lastName);

		$(By.id("personForm:birthdate_input")).shouldBe(enabled).sendKeys("01.11.1981", Keys.TAB);

		$(By.id("personForm:maritalStatus")).click();
		$(By.id("personForm:maritalStatus_items")).$$(By.tagName("li")).find(text("married")).click();

		$(By.id("personForm:salary_input")).shouldBe(enabled).sendKeys("1111900");

		$(By.id("personForm:department")).click();
		$(By.id("personForm:department_items")).$$(By.tagName("li")).find(text(productionDepartmentName)).click();


		$(By.id("personForm:saveButton")).shouldBe(enabled).click();

		$(By.id("mainForm:personTable_data")).shouldHave(text(ivyUserName));
	}

	@Test
	@Order(5)
	public void testEditPerson() {
		startProcessPersonSearch();

		$(By.id("mainForm:personTable_data")).shouldHave(text(ivyUserName));

		int index = indexOf("mainForm:personTable", ivyUserName);
		$(By.id("mainForm:personTable:" + index + ":editButton")).shouldBe(enabled).click();

		InputNumber inputNumber = PrimeUi.inputNumber(By.id("personForm:salary"));
		inputNumber.setValue("1112000");

		$(By.id("personForm:maritalStatus")).click();
		$(By.id("personForm:maritalStatus_items")).$$(By.tagName("li")).find(text("widowed")).click();
		$(By.id("personForm:maritalStatus")).click();

		$(By.id("personForm:birthdate_input")).shouldBe(enabled).clear();
		$(By.id("personForm:birthdate_input")).shouldBe(enabled).sendKeys("02.12.1982", Keys.TAB);

		$(By.id("personForm:saveButton")).shouldBe(enabled).click();
	}

	@Test
	@Order(6)
	public void testDeletePerson() {
		startProcessPersonSearch();

		$(By.id("mainForm:personTable_data")).shouldHave(text(ivyUserName));

		int index = indexOf("mainForm:personTable", ivyUserName);
		$(By.id("mainForm:personTable:" + index + ":deleteButton")).shouldBe(enabled).click();

		$(By.id("okButton")).shouldBe(enabled).click();

		$(By.id("mainForm:personTable_data")).shouldNotHave(text(ivyUserName));
	}

	private void addNewDepartment(String departmentName) {
		startProcessDepartmentSearch();

		$(By.id("mainForm:addButton")).shouldBe(enabled).click();

		$(By.id("departmentForm:departmentName")).shouldBe(enabled).sendKeys(departmentName);

		$(By.id("departmentForm:saveButton")).shouldBe(enabled).click();
	}

	private void startProcessDepartmentSearch() {
		open(EngineUrl.createProcessUrl("persistence-utils-demo/173A4BC5D38BAD52/departmentSearch.ivp"));
	}

	private void startProcessPersonSearch() {
		open(EngineUrl.createProcessUrl("persistence-utils-demo/173A4BC5D38BAD52/personSearch.ivp"));
	}

	private static void startLoginAsAdmin(WebAppFixture fixture) {
		fixture.login("jpa_admin", "jpa_admin");

		open(EngineUrl.base() + "default-workflow/faces/profile.xhtml");
		SelenideElement lang = $(By.name("profileForm:contentLanguage_editableInput")).shouldBe(enabled);
		lang.clear();
		lang.sendKeys("en");

		$(By.id("profileForm:saveBtn")).shouldBe(enabled).click();
	}

	private static int indexOf(String tableId, String text) {
		//Get all rows
		ElementsCollection rows = $(By.id(tableId)).$$(By.tagName("tr"));
		int index = -1;
		for (SelenideElement row: rows) {
			if(StringUtils.isNotBlank(row.getText()) && row.getText().contains(text)) {
				return index;
			}
			index++;
		}
		return -1;
	}
}
