package com.tucine.cineclubadministration;

import com.tucine.cineclubadministration.Film.helpers.TheMovieDatabaseHelper;
import com.tucine.cineclubadministration.Film.service.impl.FilmServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CineclubAdministrationApplicationTests {

	@Test
	void Test1StringDatabase() {
		System.out.println(TheMovieDatabaseHelper.getMovieTrailerSrcVideo("507089"));
	}

	@Test
	void Test2ConvertDataYear(){
		System.out.println(TheMovieDatabaseHelper.DateTimeFormatterGetYears("2021-03-31"));
	}

	@Test
	void Test3GetDurationFilmById(){
		System.out.println(TheMovieDatabaseHelper.getDurationExternalMovie("507089"));
	}

	@Test
	void Test4GetRatingFilmById(){
		System.out.println(TheMovieDatabaseHelper.getContentRatingExternalMovie("507089"));
	}

}
