package com.axonivy.utils.persistence.test.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.axonivy.utils.persistence.service.EnumService;
import com.axonivy.utils.persistence.test.DemoTestBase;
import com.axonivy.utils.persistence.test.util.LocaleSetter;

import ch.ivyteam.ivy.environment.AppFixture;
import ch.ivyteam.ivy.environment.IvyTest;

@IvyTest
public class EnumServiceTest extends DemoTestBase {
	private String AE = "\u00c4";
	private String AEs = "\u00e4";
	private String OE = "\u00d6";
	private String OEs = "\u00f6";
	private String UE = "\u00dc";
	private String UEs = "\u00fc";
	private String SS = "\u00df";

	@BeforeEach
	public void prepare(AppFixture fixture) throws Exception {
	}

	@Test
	public void testSortingEnum() {
		try (LocaleSetter localeSetter = LocaleSetter.set(Locale.ENGLISH)) {
			List<Entry<TestEnum, String>> sorted = EnumService.getSortedByCmsName(TestEnum.class);

			assertThat(sorted.stream().map(e -> e.getKey()).toList()).containsExactly(TestEnum.ONE, TestEnum.THREE, TestEnum.TWO);
		}
		try (LocaleSetter localeSetter = LocaleSetter.set(Locale.GERMAN)) {
			List<Entry<TestEnum, String>> sorted = EnumService.getSortedByCmsName(TestEnum.class);

			assertThat(sorted.stream().map(e -> e.getKey()).toList()).containsExactly(TestEnum.THREE, TestEnum.ONE, TestEnum.TWO);
		}
	}

	@Test
	public void testSortingUmlaut() {

		try (LocaleSetter localeSetter = LocaleSetter.set(Locale.GERMAN)) {
			List<String> list = List.of(SS, UEs, "d", OEs, "B", AEs + "B", "c", AE, AEs, OE, UE, "A");
			List<String> sorted = list.stream().sorted(EnumService.localeAwareCIComparator()).toList();
			assertThat(sorted).containsExactly("A", AEs, AE, AEs + "B", "B", "c", "d", OEs, OE, SS, UEs, UE);
		}
	}

}